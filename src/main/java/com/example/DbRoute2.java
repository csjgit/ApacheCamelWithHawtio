package com.example;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DbRoute2 extends RouteBuilder {
    @Override
    public void configure() throws Exception {
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

    }
}
