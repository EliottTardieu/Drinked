INSERT INTO drinked.Beverages (id, name, description, price_35, price_75, quantity_available, water_percentage) VALUES (1, 'Espresso', 'Short', 0.8, 1.2, 50, 80);
INSERT INTO drinked.Beverages (id, name, description, price_35, price_75, quantity_available, water_percentage) VALUES (2, 'Cappuccino', 'Long', 1.2, 2, 70, 80);
INSERT INTO drinked.Beverages (id, name, description, price_35, price_75, quantity_available, water_percentage) VALUES (3, 'Macchiato', 'Classic', 1.5, 2.5, 65, 80);
INSERT INTO drinked.Beverages (id, name, description, price_35, price_75, quantity_available, water_percentage) VALUES (4, 'Latte Macchiato', 'Latte', 2, 3, 100, 80);

INSERT INTO drinked.Orders (id, beverage_quantity, beverage_id, sugar_quantity, cup_selection, price, validity) VALUES (1, 35, 2, 5, 'Personal Cup', 1.2, 'Validated');
INSERT INTO drinked.Orders (id, beverage_quantity, beverage_id, sugar_quantity, cup_selection, price, validity) VALUES (2, 75, 4, 10, 'Cup 75cl', 2.85, 'Validated');
INSERT INTO drinked.Orders (id, beverage_quantity, beverage_id, sugar_quantity, cup_selection, price, validity) VALUES (3, 35, 2, 5, 'Personal Cup', 1.2, 'Canceled');
INSERT INTO drinked.Orders (id, beverage_quantity, beverage_id, sugar_quantity, cup_selection, price, validity) VALUES (4, 75, 2, 0, 'Personal Cup', 2, 'Validated');

INSERT INTO drinked.Resources (id, description, quantity_available) VALUES (1, 'Water', 150);
INSERT INTO drinked.Resources (id, description, quantity_available) VALUES (2, 'Sugar', 150);
INSERT INTO drinked.Resources (id, description, quantity_available) VALUES (3, 'Cup 35cl', 10);
INSERT INTO drinked.Resources (id, description, quantity_available) VALUES (4, 'Cup 75cl', 15);
