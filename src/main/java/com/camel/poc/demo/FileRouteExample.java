package com.camel.poc.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import io.hawt.embedded.Main;

public class FileRouteExample {
    public static void main(String[] args) throws Exception {
        // Create Camel context
        CamelContext context = new DefaultCamelContext();
        System.setProperty("hawtio.authenticationEnabled", "false");

        // Add our route to the Camel context
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                // Define the route
                from("file:input?noop=true")
                        .process(exchange -> {
                            // Get the file content
                            String fileContent = exchange.getIn().getBody(String.class);
                            // Process the file content (e.g., convert to uppercase)
                            String processedContent = fileContent.toUpperCase();
                            // Set the processed content back to the message body
                            exchange.getIn().setBody(processedContent);
                        })
                        .to("file:output");
            }
        });
        Main hawtio = new Main();
        hawtio.setPort(8081); // Set the port for Hawtio
        hawtio.setWar("src/main/resources/hawtio-default-2.13.5.war");
        //  hawtio.setOpts(new String[]{"--hawtio.authenticationEnabled=false"});
        hawtio.run();

        // Start the Camel context
        context.start();

        // Keep the application running for a while to process files
        Thread.sleep(1000000);

        // Stop the Camel context
        context.stop();
    }
}
