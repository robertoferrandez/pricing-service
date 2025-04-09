CREATE TABLE IF NOT EXISTS prices (
    brand_id INT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    price_list INT,
    product_id INT,
    priority INT,
    price DECIMAL(10,2),
    currency VARCHAR(3),
    PRIMARY KEY (brand_id, product_id, start_date, end_date)
);