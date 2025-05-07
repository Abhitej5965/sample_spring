-- Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone_number VARCHAR(20)
);

-- Products table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL,
    stock_quantity INTEGER,
    metadata LONGBLOB
);

-- Orders table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    order_date DATETIME NOT NULL,
    total_amount DOUBLE NOT NULL,
    order_status VARCHAR(50),
    shipping_address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Order Products mapping table
CREATE TABLE order_products (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
); 