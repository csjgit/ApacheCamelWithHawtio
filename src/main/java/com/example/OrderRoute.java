package com.example;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;


@Component
public class OrderRoute extends RouteBuilder {

    @Override
    public void configure() {
        // Flow 1: Read CSV file and unmarshal to Order objects
        from("file:input").log("Reading file: ${header.CamelFileName}")
                .unmarshal().bindy(BindyType.Csv, Order.class)
                .split(body().tokenize("\n")).streaming()
                .to("direct:processOrder");

        // Flow 2: Process orders and route based on conditions
        from("direct:processOrder")
                .process(exchange -> {
                    Order order = exchange.getIn().getBody(Order.class);
                    if ("DB1".equalsIgnoreCase(order.getDestination())) {
                        exchange.getIn().setHeader("destination", "db1");
                    } else if ("DB2".equalsIgnoreCase(order.getDestination())) {
                        exchange.getIn().setHeader("destination", "db2");
                    } else {
                        exchange.getIn().setHeader("destination", "rejected");
                    }
                })
                .choice()
                .when(header("destination").isEqualTo("db1"))
                .to("direct:db1")
                .when(header("destination").isEqualTo("db2"))
                .to("direct:db2")
                .otherwise()
                .to("direct:rejected");

        // Flow 3: Route to DB1
        from("direct:db1")
                .process(exchange -> {
                    Order order = exchange.getIn().getBody(Order.class);
                    String insertQuery = String.format(
                            "INSERT INTO orders_db1 (order_id, customer_name, product, quantity, price) VALUES ('%s', '%s', '%s', %d, %f)",
                            order.getOrderId(), order.getCustomerName(), order.getProduct(), order.getQuantity(), order.getPrice()
                    );
                    exchange.getIn().setBody(insertQuery);
                })
                .to("jdbc:dataSource");

        // Flow 4: Route to DB2
        from("direct:db2")
                .process(exchange -> {
                    Order order = exchange.getIn().getBody(Order.class);
                    String insertQuery = String.format(
                            "INSERT INTO orders_db2 (order_id, customer_name, product, quantity, price) VALUES ('%s', '%s', '%s', %d, %f)",
                            order.getOrderId(), order.getCustomerName(), order.getProduct(), order.getQuantity(), order.getPrice()
                    );
                    exchange.getIn().setBody(insertQuery);
                })
                .to("jdbc:dataSource");

        // Flow 5: Handle rejected orders
        from("direct:rejected")
                .to("file:src/output/rejected?fileName=rejected-orders.txt&fileExist=Append");
    }
}
