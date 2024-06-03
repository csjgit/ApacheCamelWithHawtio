package com.example;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",")
public class Order {

    @DataField(pos = 1)
    private String orderId;

    @DataField(pos = 2)
    private String customerName;

    @DataField(pos = 3)
    private String product;

    @DataField(pos = 4)
    private int quantity;

    @DataField(pos = 5)
    private double price;

    @DataField(pos = 6)
    private String destination;

    // Getters and setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
}
