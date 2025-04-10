INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, currency)
SELECT * FROM (VALUES
                   (1, TIMESTAMP '2020-06-14 00:00:00', TIMESTAMP '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
                   (1, TIMESTAMP '2020-06-14 15:00:00', TIMESTAMP '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'),
                   (1, TIMESTAMP '2020-06-15 00:00:00', TIMESTAMP '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR'),
                   (1, TIMESTAMP '2020-06-15 16:00:00', TIMESTAMP '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR')
              ) AS new_rows (brand_id, start_date, end_date, price_list, product_id, priority, price, currency)
WHERE NOT EXISTS (
    SELECT 1 FROM prices WHERE product_id = new_rows.product_id AND start_date = new_rows.start_date
);