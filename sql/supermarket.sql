CREATE DATABASE IF NOT EXISTS supermarket;
USE supermarket;

CREATE TABLE IF NOT EXISTS products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  quantity INT NOT NULL DEFAULT 0,
  description VARCHAR(255)
);

-- seed data
INSERT INTO products (name, price, quantity, description) VALUES
('Milk 1L', 1.20, 100, 'Fresh milk 1 litre'),
('Bread', 0.80, 200, 'Whole wheat bread'),
('Eggs (12)', 2.50, 150, 'Free range eggs - dozen'),
('Apple (1 kg)', 3.00, 80, 'Fresh red apples');