CREATE DATABASE stationery;
USE stationery; 

CREATE TABLE login(
user_id VARCHAR(30),
user_pass VARCHAR(30)
);

CREATE TABLE Product(
product_id INT PRIMARY KEY,
product_name VARCHAR(30) ,
product_type VARCHAR(30),
buy_price INT,
sell_price INT,
supplier_id INT,
FOREIGN KEY(supplier_id) REFERENCES Supplier(supplier_id)
);

CREATE TABLE Supplier(
supplier_id INT PRIMARY KEY,
supplier_name VARCHAR(30),
phone VARCHAR(30),
payment INT
);

CREATE TABLE Customer(
customer_id INT PRIMARY KEY,
customer_name VARCHAR(30),
phone VARCHAR(30),
payment INT
);

CREATE TABLE Inventory(
product_id INT,
quantity INT,
updated_dated DATE,
FOREIGN KEY(product_id) REFERENCES Product(product_id)
);


CREATE TABLE Sales(
sale_id INT ,
customer_id INT,
product_id INT,
quantity INT,
sale_date DATE,
amount INT,
FOREIGN KEY(customer_id) REFERENCES Customer(customer_id),
FOREIGN KEY(product_id) REFERENCES Product(product_id)
);



CREATE TABLE SupplierPayment(
supplier_id INT,
amount INT,
payment_date DATE,
FOREIGN KEY(supplier_id) REFERENCES Supplier(supplier_id)
);

CREATE TABLE CustomerPayment(
customer_id INT,
amount INT,
payment_date DATE,
FOREIGN KEY(customer_id) REFERENCES Customer(customer_id)
);


SELECT * FROM Login;

DELETE FROM Login;

SELECT * FROM Customer;

SELECT * FROM Product;

SELECT * FROM Supplier;

SELECT * FROM Sales;

SELECT * FROM Inventory;


DELIMITER //
CREATE PROCEDURE add_login(IN p_id VARCHAR(30), IN p_pass VARCHAR(30))
BEGIN
    INSERT INTO login VALUES (p_id, p_pass);
END //
DELIMITER ;

CALL add_login('sarim',1234);

DELETE FROM login where user_id='sarim';

SELECT * FROM Login;



-- DELIMITER //
-- CREATE PROCEDURE insert_supplier(IN s_name VARCHAR(30), IN s_phone VARCHAR(30))
-- BEGIN
--     DECLARE s_id INT;
--     DECLARE payment INT;
--     SET s_id = (SELECT COUNT(supplier_id) FROM Supplier) + 1;
--     SET payment = 0;

--         INSERT INTO Supplier VALUES (
--             s_id,s_name,s_phone,payment
--         );
-- END //
-- DELIMITER ;

DELIMITER //
CREATE PROCEDURE insert_supplier(IN s_name VARCHAR(30), IN s_phone VARCHAR(30))
BEGIN
    DECLARE s_id INT;
    DECLARE payment INT;
    
    -- Check if a supplier with the same name and phone already exists
    IF (SELECT COUNT(*) FROM Supplier WHERE supplier_name = s_name AND phone = s_phone) > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Supplier with the same details already exists';
    ELSE
        SET s_id = (SELECT IFNULL(MAX(supplier_id), 0) + 1 FROM Supplier);
        SET payment = 0;

        INSERT INTO Supplier VALUES (
            s_id, s_name, s_phone, payment
        );
    END IF;
END //
DELIMITER ;


DROP Procedure insert_supplier;
DELETE FROM supplier;

CALL insert_supplier('Zain','0414-4576123');

SELECT CONCAT(supplier_id,' : ',supplier_name) AS supplier_info FROM Supplier;

SELECT * FROM supplier;




-- DELIMITER //
-- CREATE PROCEDURE insert_customer(IN c_name VARCHAR(30), IN c_phone VARCHAR(30))
-- BEGIN
--     DECLARE c_id INT;
--     DECLARE payment INT;
--     SET c_id = (SELECT COUNT(customer_id) FROM Customer) + 1;
--     SET payment = 0;

--         INSERT INTO Customer VALUES (
--             c_id,c_name,c_phone,payment
--         );
-- END //
-- DELIMITER ;

DELIMITER //
CREATE PROCEDURE insert_customer(IN c_name VARCHAR(30), IN c_phone VARCHAR(30))
BEGIN
    DECLARE c_id INT;
    DECLARE payment INT;

    -- Check if a customer with the same name and phone already exists
    IF (SELECT COUNT(*) FROM Customer WHERE customer_name = c_name AND phone = c_phone) > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Customer with the same details already exists';
    ELSE
        SET c_id = (SELECT IFNULL(MAX(customer_id), 0) + 1 FROM Customer);
        SET payment = 0;

        INSERT INTO Customer VALUES (
            c_id, c_name, c_phone, payment
        );
    END IF;
END //
DELIMITER ;


DROP Procedure insert_customer;
DELETE FROM Customer;

CALL insert_customer('Ahmed','0312-4567890');


SELECT * FROM Customer;






-- DELIMITER //
-- CREATE PROCEDURE insert_product(IN p_name VARCHAR(30), IN p_type VARCHAR(30),IN p_price INT,IN p_supplier INT)
-- BEGIN
--     DECLARE p_id INT;
--     SET p_id = (SELECT COUNT(product_id) FROM Product) + 1;
--     
--         INSERT INTO Product VALUES (
--             p_id,p_name,p_type,p_price,p_supplier
--         );
-- END //
-- DELIMITER ;

-- DROP Procedure insert_product;

-- DELETE FROM Product;

-- CALL insert_product('Piano Fineliner','Pen',30,2);

-- SELECT * FROM Product;




DELIMITER //
CREATE PROCEDURE insert_product(IN p_name VARCHAR(30), IN p_type VARCHAR(30),IN p_buy_price INT, IN p_sell_price INT, IN p_supplier INT)
BEGIN
    DECLARE p_id INT;

    IF (SELECT COUNT(*) FROM Product WHERE product_name = p_name AND product_type = p_type AND buy_price = p_buy_price AND sell_price = p_sell_price AND supplier_id = p_supplier) > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Product with the same details already exists';
    ELSE

        SET p_id = (SELECT IFNULL(MAX(product_id), 0) + 1 FROM Product);

        INSERT INTO Product (product_id, product_name, product_type, buy_price, sell_price, supplier_id) VALUES (p_id, p_name, p_type, p_buy_price, p_sell_price, p_supplier);
		INSERT INTO Inventory VALUES (p_id,0,CURDATE());
    END IF;
END //
DELIMITER ;

CALL insert_product('My Pencil','Pencil',15,20,1);

DROP Procedure insert_product;

DELETE FROM Product;

DELETE FROM Inventory;

SELECT * FROM Product;

SELECT * FROM Inventory;


SELECT * FROM Customer;
SELECT * FROM Supplier;
SELECT * FROM Product;
SELECT * FROM Inventory;
SELECT * FROM Sales;
SELECT * FROM SupplierPayment;
SELECT * FROM CustomerPayment;



SELECT Distinct product_name FROM Product;

SELECT CONCAT((SELECT supplier_name FROM Supplier S where S.supplier_id=P.supplier_id),'  @Rs. ',P.buy_price)AS SName FROM Product P where product_name = 'Piano Fineliner' order by buy_price asc;


DELIMITER //
CREATE PROCEDURE order_product(IN p_id INT, IN p_qty INT,IN s_id INT, IN total INT)
BEGIN
DECLARE newtotal INT;
SET newtotal = (SELECT payment FROM Supplier WHERE supplier_id=s_id)+total;
UPDATE Inventory SET quantity=(quantity+p_qty), updated_dated=curdate() WHERE product_id=p_id;
UPDATE Supplier SET payment=newtotal WHERE supplier_id=s_id;
END //
DELIMITER ;

DROP procedure order_product;

SELECT * FROM Inventory;

SELECT * FROM Supplier;


SELECT MAX(sale_id)AS s_id FROM Sales;


DELIMITER //
CREATE PROCEDURE proc_sale(IN sale_id INT, IN cust_id INT,IN prod_id INT, IN qty INT,IN total INT)
BEGIN
INSERT INTO Sales VALUES(sale_id,cust_id,prod_id,qty,curdate(),total);
UPDATE Inventory SET quantity=(quantity-qty) WHERE product_id=prod_id;
UPDATE Customer SET payment=(payment+total) WHERE customer_id=cust_id;
END //
DELIMITER ;

drop procedure proc_sale;

SELECT * FROM Sales;
SELECT * FROM Inventory;
SELECT * FROM Customer;

DELETE FROM Sales;



DELIMITER //
CREATE PROCEDURE supplier_pay(IN s_id INT, IN amount INT)
BEGIN
INSERT INTO SupplierPayment VALUES(s_id,amount,CURDATE());
UPDATE Supplier SET payment=(payment-amount) WHERE supplier_id=s_id;
END //
DELIMITER ;
CALL supplier_pay();


DELIMITER //
CREATE PROCEDURE customer_pay(IN c_id INT, IN amount INT)
BEGIN
INSERT INTO CustomerPayment VALUES(c_id,amount,CURDATE());
UPDATE Customer SET payment=(payment-amount) WHERE customer_id=c_id;
END //
DELIMITER ;
CALL customer_pay();

SELECT * FROM supplier;
SELECT * FROM supplierpayment;
SELECT * FROM customer;
SELECT * FROM customerpayment;

SELECT supplier_id AS "ID", supplier_name AS "Name", phone AS "Phone Number", payment AS "Supplier Payment" FROM Supplier;

SELECT customer_id AS "ID", customer_name AS "Name", phone AS "Phone Number", payment AS "Customer Outstanding" FROM Customer;

SELECT product_id AS "ID",product_name AS "Name",product_type AS"Type",buy_price AS "Buy Price",sell_price AS "Sell Price",(sell_price-buy_price) AS "Profit Margin",(SELECT supplier_name FROM Supplier S WHERE S.supplier_id=P.supplier_id ) AS "Supplier",(Select quantity FROM Inventory I WHERE I.product_id=P.product_id )AS"Quantity" FROM Product P;

CREATE VIEW ProductView AS
SELECT
    product_id AS ID,
    product_name AS Name,
    product_type AS Type,
    buy_price AS `Buy Price`,
    sell_price AS `Sell Price`,
    (sell_price - buy_price) AS `Profit Margin`,
    (SELECT supplier_name FROM Supplier S WHERE S.supplier_id = P.supplier_id) AS Supplier,
    (SELECT quantity FROM Inventory I WHERE I.product_id = P.product_id) AS Quantity
FROM
    Product P;
    
SELECT * FROM productView;



CREATE VIEW InventoryCost AS
SELECT 
    P.product_id,
    quantity * buy_price AS "Buy Value",
    quantity * sell_price AS "Sell Value"
FROM
    Product P
        JOIN
    Inventory I ON P.product_id = I.product_id;
    
SELECT * FROM InventoryCost;

SELECT SUM(`Buy Value`) AS "Total Buy Value" ,SUM(`Sell Value`) AS "Total Sell Value",(SUM(`Sell Value`)-SUM(`Buy Value`)) AS "Total Profit" FROM InventoryCost;


CREATE VIEW InventoryCostDetail AS
SELECT
product_id AS "ID",
(Select quantity From Inventory I WHERE I.product_id=V.product_id) AS Quantity,
(Select product_name From Product P WHERE P.product_id=V.product_id) AS "Name",
(Select buy_price From Product P WHERE P.product_id=V.product_id) AS "Unit Buy Price",
(Select sell_price From Product P WHERE P.product_id=V.product_id) AS "Unit Sell Price",
`Buy Value` AS "Total Buy Value",
`Sell Value` AS "Total Sell Value",
(`Sell Value`-`Buy Value`) AS Profit
FROM InventoryCost V;


DROP view InventoryCostDetail;

SELECT *  FROM InventoryCostDetail;

CREATE VIEW CostProfitQty AS
SELECT 
    product_id,
    ((sell_price - buy_price) / buy_price) AS profit_margin,
    (SELECT SUM(quantity) FROM Sales S WHERE S.product_id = P.product_id) AS sale_quantity
FROM 
    Product P;

SELECT * FROM CostProfitQty;
SELECT * FROM sales;


CREATE VIEW PopularProduct AS    
SELECT product_id AS ID,(Select product_name From Product P WHERE P.product_id=V.product_id)AS "Product Name",ROUND((profit_margin * sale_quantity), 2) AS Score FROM CostProfitQty V ORDER BY Score DESC;

SELECT * FROM PopularProduct;

SELECT count(DISTINCT(sale_id))AS salecount,sum(quantity)AS tquantity,sum(amount) AS tsales FROM Sales;

SELECT MONTHNAME(sale_date)AS Months,sum(amount) AS TotalSales FROM Sales GROUP BY  MONTHNAME(sale_date);

SELECT * FROM Customer;
INSERT INTO Customer Value(0,"No Name","xxxx-xxxxxxx",0);










