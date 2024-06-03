CREATE TABLE orders_db1 (
    order_id VARCHAR(255) PRIMARY KEY,
    customer_name VARCHAR(255),
    product VARCHAR(255),
    quantity INT,
    price DOUBLE
);

CREATE TABLE orders_db2 (
    order_id VARCHAR(255) PRIMARY KEY,
    customer_name VARCHAR(255),
    product VARCHAR(255),
    quantity INT,
    price DOUBLE
);
