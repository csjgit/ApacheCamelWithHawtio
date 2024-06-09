package com.example;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DbRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
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
    }
}
