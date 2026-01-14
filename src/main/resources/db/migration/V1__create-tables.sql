CREATE TABLE User_tb (
     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     email VARCHAR(150) NOT NULL UNIQUE,
     password VARCHAR(255) NOT NULL,

     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Product_tb (
     product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(150) NOT NULL,
     url VARCHAR(500) NOT NULL,
     target_price DECIMAL(10,2),
     user_id BIGINT NOT NULL,

     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT fk_product_user
         FOREIGN KEY (user_id)
             REFERENCES User_tb(user_id)
             ON DELETE CASCADE
);

CREATE TABLE Price_tb (
   price_id BIGINT AUTO_INCREMENT PRIMARY KEY,
   price DECIMAL(10,2) NOT NULL,
   scraping_data TIMESTAMP NOT NULL,
   product_id BIGINT NOT NULL,

   CONSTRAINT fk_price_product
       FOREIGN KEY (product_id)
           REFERENCES Product_tb(product_id)
           ON DELETE CASCADE
);