go 
use EatandGo_Restaurant
go

-- INSERT Statements

INSERT INTO Supplier values (1, 'Meat&Grill'), (7, 'Coca-Cola Company'), (3, 'Local Grocery Store'), (4, 'Metro Market')
SELECT * from Supplier

INSERT INTO Address values (15, 'Mehedinti', 23, 'O3', 3, 36), (2, 'Eroilor', 8, NULL, NULL, NULL), (1, 'Plopilor', 7, 'C7', NULL, 18), (5, 'Buna Ziua', 10, NULL, NULL, NULL),  (22, 'Motilor', 7, NULL, NULL, 1), (11, 'Edgar Quinet', 3, 'Q2', 5, 69)
SELECT * from Address

INSERT INTO Employee values (1, 'Dan', 'Mihai', '0743912393', 'dan.mihai@gmail.com', 2, 'cook'), (5, 'Georgian', 'Anghel', '+40369029943', 'ianbarz@gmail.com', 5, 'manager')
INSERT INTO Employee(Eid, FirstName, LastName, PhoneNumber, Aid, Role) values (10, 'Alex', 'Arnold', '0723103843', 22, 'waiter'), (3, 'Maria', 'Pop', '0040123999099', 2, 'cook')
INSERT INTO Employee values (5, 'Narcis', 'Rom', '07432122', NULL, 2, 'waiter') -- insert violates the primary key
INSERT INTO Employee values (2, 'Marius', 'Stan', '+40943122334', 'stanmarius77@yahoo.com', 1, 'boxkeeper'), (11, 'Anne', 'Dima', '0796743987', 'princessanne@gmail.com', 5, 'waiter')
INSERT INTO Employee values (27, 'Max', 'Barmin', '+31302034298', NULL, 15, 'waiter'), (8, 'Roxana', 'Gal', '0745140076', 'roxygal@yahoo.com', 11, 'cook'), (19, 'Victor', 'Mihai', '0493491232', 'mihai.victor23@gmail.com', 2, 'waiter')
INSERT INTO Employee values (323, 'Dorian', 'Ham', '043321', NULL, 70, 'cook') -- insert conflicts with the FK constraint
SELECT * FROM Employee

INSERT INTO Customer(Cid, FirstName, LastName) values (7, 'Edi', 'Nedelcu'), (72, 'Dana', 'Lola'), (38, 'Cosmin', 'Nedelcu'), (6, 'Sam', 'John'), (66, 'Edward', 'D'), (9, 'Paul', 'Maier'), (25, 'Daria', 'Muresan')
INSERT INTO Customer values (4, 'Cristina', 'Pop', '0232433987'), (31, 'Stefan', 'Avram', '0709225200'),  (1, 'David', 'Ion', '0431233908'),  (45, 'Alberto', 'Radu', '0123456789'), (54, 'Amil', 'Hamed', '+4077123845')
SELECT * FROM Customer

INSERT INTO _Order values (7, 19, '2022-11-4', '15:23'), (45, 19, '2022-11-10', '19:45'), (66, 19, '2022-10-29', '12:06'), (7, 27, '2022-11-2', '20:18'), (31, 27, '2022-11-4', '15:33')
INSERT INTO _Order values (4, 11, '2022-11-8', '18:56'), (54, 11, '2022-11-11', '09:50'), (25, 11, '2022-10-31', '17:21'), (38, 10, '2022-11-9', '20:30'), (66, 10, '2022-11-7', '13:00')
SELECT * FROM _Order

INSERT INTO ProductType(PTid, Type) values (3, 'Dessert'), (4, 'Drink')
INSERT INTO ProductType values (2, 'Breakfast', 'recommended to be served in the morning'), (1, 'Main Course', 'dishes that are cooked to ward off your hunger')  
SELECT * FROM ProductType

INSERT INTO Product values (10, 'Cheeseburger', 25.99, 'Cheeseburger with Black Angus Meat, Cheddar cheese slice and fresh lettuce', 1)
INSERT INTO Product values (11, 'Hamburger', 23.50, 'Hamburger with Black Angus Meat, fresh lettuce and tomatoes', 1)
INSERT INTO Product values (12, 'French Fries', 7.79, 'Freshly fried potatoes', 1)
INSERT INTO Product values (30, 'Cheesecake', 13, 'Sweet cheesecake with raspberry topping', 3)
INSERT INTO Product values (31, 'Apple Pie', 11.99, 'Traditional delicios pie', 3)
INSERT INTO Product values (13, 'Pulled Pork', 33.35, 'Pork slowly cooked in the oven with vegetables', 1)
INSERT INTO Product values (14, 'Tacos', 27, '2 tacos filled with beef, sour-cream, tomatoes and hot sauce', 1)
INSERT INTO Product values (40, 'Coca-Cola', 6.50, '330ml Coca-Cola Bottle', 4)
INSERT INTO Product values (41, 'Fanta', 6.50, '330ml Fanta Bottle', 4)
INSERT INTO Product values (20, 'Omelette', 16.99, 'omelette with cheese and bacon', 2), (21, 'Toast Sandwich', 15.99, 'contains ham, cheese and lettuce', 2)
INSERT INTO Product values (15, 'T-Bone', 65, 'Beef with asparagus and chilli', 1)
INSERT INTO Product values (42, 'Sparkling Water', 5.3, '500ml sparkling water bottle', 4)
INSERT INTO Product values (43, 'Orange Juice', 12.75, '500ml juice of freshly squeezed oranges', 4)
SELECT * FROM Product

INSERT INTO Ingredient values (1, 'Spice', 'Salt'), (2, 'Spice', 'Pepper'), (3, 'Spice', 'Chilli'), (10, 'Meat', 'Beef'), (11, 'Meat', 'Pork')
INSERT INTO Ingredient values (12, 'Meat', 'Chicken'), (13, 'Meat', 'Ham'), (50, 'Cheese', 'Cheddar'), (51, 'Cheese', 'Gouda')
INSERT INTO Ingredient values (71, 'Vegetable', 'Tomato'), (72, 'Vegetable', 'Potato'), (73, 'Vegetable', 'Lettuce'), (74, 'Vegetable', 'Asparagus'), (75, 'Vegetable', 'Cucumber')
INSERT INTO Ingredient values (80, 'Liquid', 'Water'), (81, 'Liquid', 'Juice'), (91, 'Sweet', 'Sugar'), (92, 'Fruit','Orange'), (93, 'Fruit', 'Raspberry')
INSERT INTO Ingredient values (1239, 'Meat', 'Lamb'), (321, 'Fruit', 'Apple'), (94, 'Fruit', 'Apple'), (100, 'Sweet', 'Chocolate')
SELECT * FROM Ingredient

INSERT INTO DeliveryMan values (16, 'Marius Fit', '0491340914', 4), (6, 'Andrew Smith', '+390132387412', 7), (27, 'Rares Sava', '0743993013', 3) 
INSERT INTO DeliveryMan values (187, 'Cristian', '0848398323', 4), (56, 'Alex Martin', '0384992123', 7), (3, 'Paul', '0483183976', 1), (8, 'Mike Long', '+57041498643', 3)
SELECT * FROM DeliveryMan

INSERT INTO Delivery values (16, 1, 10, '2022-08-17', NULL), (16, 3, 5.5, '2022-09-02', '2023-10-10'), (3, 12, 45, '2022-10-18', '2022-12-07'), (27, 75, 23.25, '2022-11-03', '2022-12-07'), (187, 80, 23.25, '2022-05-28', NULL)
INSERT INTO Delivery values (3, 13, 8, '2022-11-17', '2022-12-15'), (187, 1, 15.5, '2022-09-30', NULL), (56, 81, 80, '2022-10-18', '2024-02-19'), (27, 72, 57.78, '2022-11-07', '2023-02-11'), (16, 92, 16.25, '2022-11-24', '2023-01-19')
INSERT INTO Delivery values (187, 93, 10, '2022-10-08', '2022-11-29'), (27, 93, 6.57, '2022-11-03', '2022-12-10'), (3, 10, 38, '2022-11-11', '2023-01-07'), (56, 80, 23.25, '2022-07-13', NULL), (187, 91, 43, '2022-08-28', NULL)
INSERT INTO Delivery values (16, 73, 15.5, '2022-10-21', '2022-11-22'), (27, 50, 12.23, '2022-10-27', '2023-01-09'), (3, 11, 32.96, '2022-11-18', '2023-02-12'), (3, 3, 11.45, '2022-11-03', '2022-11-23'), (187, 51, 8.9, '2022-11-28', '2022-12-31')
INSERT INTO Delivery values (3, 92, 23, '2021-09-12', NULL), (187, 2, 13, '2022-10-01', '2022-10-28'), (56, 12, 19.3, '2021-12-03', '2022-03-05')
SELECT * FROM Delivery

INSERT INTO OrderProduct values (4, 11, 11), (4, 11, 12), (4, 11, 43), (7, 19, 20), (7, 19, 42), (31, 27, 15), (31, 27, 43), (31, 27, 30), (7, 27, 31), (7, 27, 20)
INSERT INTO OrderProduct values (25, 11, 10), (38, 10, 14), (38, 10, 13), (38, 10, 31), (38, 10, 40), (38, 10, 42), (45, 19, 10), (45, 19, 11), (45, 19, 12), (54, 11, 15)
INSERT INTO OrderProduct values (54, 11, 12), (54, 11, 30), (54, 11, 31), (66, 10, 21), (66, 10, 43), (66, 10, 42), (66, 19, 15), (66, 19, 11), (66, 19, 40)
SELECT * FROM OrderProduct

INSERT INTO Preparation values (10, 1, NULL, '25 min'), (10, 10, 'Beef is fried 5 minutes on each side, then it is put togheter with a slice of cheese and lettuce', '25 min'), (10, 50, NULL, '25 min'), (10, 73, NULL, '25 min')
INSERT INTO Preparation values (12, 72, NULL, '15 min'), (12, 1, NULL, '15 min'), (12, 3, NULL, '15 min')
INSERT INTO Preparation values (13, 11, 'Pork is cut in strips and cooked in the oven, then is served with a vegetables salad', '1h 10 min'), (13, 72, NULL, '1h 10 min'), (13, 71, NULL, '1h 10 min'), (13, 2, NULL, '1h 10 min')
INSERT INTO Preparation values (15, 10, ' T-Bone steak placed over the hottest part of the grill, and sear both sides for 1–2 minutes', '40 min'), (15, 1, NULL, '40 min'), (15, 74, NULL, '40 min'), (15, 3, NULL, '40 min')
INSERT INTO Preparation values (21, 13, NULL, '7 min'), (21, 51, NULL, '7 min'), (21, 73, NULL, '7 min')
INSERT INTO Preparation values (31, 94, NULL, '50 min'), (31, 91, NULL, '50 min'), (31, 80, NULL, '50 min')
INSERT INTO Preparation values (43, 92, '2 oranges are squeezed, ice is added to the glass and stir carefully', '3 min'), (43, 81, NULL, '3 min')
SELECT * FROM Preparation

-- UPDATE Statements

-- add the email employee@eat_go.com for the employees that have the email as NULL
UPDATE Employee
SET Email = 'employee@eat_go.com'
WHERE Email is NULL
SELECT * FROM Employee

-- increase the price with 10% of the products with price between 15 and 25
UPDATE Product
SET Price = Price + 0.1 * Price
WHERE Price BETWEEN 15 AND 25
SELECT * FROM Product

-- decrease 0.25 from quantity of each ingredient with quantity 23.25 or 16.25, having an expiring date
UPDATE Delivery
SET Quantity = Quantity - 0.25
Where Quantity IN (23.25, 16.25) AND ExpiringDate IS NOT NULL
SELECT * FROM Delivery

-- DELETE Statements

-- Delete the delivery men whose number starts with +
DELETE FROM DeliveryMan
WHERE PhoneNumber LIKE '+%'
SELECT * FROM DeliveryMan

-- Delete the deliveries that were made before 2022 or the ingredient delivered had expired 
DELETE FROM Delivery
WHERE DateOfDelivery < '2022-01-01' OR ExpiringDate < GETDATE()
SELECT * FROM Delivery

-- Delete the ingredients with id greater than 100
DELETE FROM Ingredient
WHERE Ingredient.Iid >= 100
SELECT * FROM Ingredient


-- 2 queries with the union operation; use UNION [ALL] and OR

-- employees whose first name starts with M and has at least 4 letters or have the last name starting with M
SELECT * FROM Employee
WHERE FirstName LIKE 'M___%'
UNION
SELECT * FROM Employee
WHERE LastName LIKE 'M%'

-- products of price less than 15 or products of type 'Breakfast' 
SELECT p1.Pid, p1.Name, p1.Description, p1.Price 
FROM Product p1
WHERE p1.Price < 15
UNION ALL -- OR
SELECT p2.Pid, p2.Name, p2.Description, p2.Price 
FROM Product p2
WHERE p2.PTid = 2


-- 2 queries with the intersection operation; use INTERSECT and IN

-- ingredients in category Meat or Vegetable which does not contain letter 'o' in their name
SELECT * FROM Ingredient
WHERE Category = 'Meat' OR Category = 'Vegetable'
INTERSECT
SELECT * FROM Ingredient
WHERE Ingredient.NameOfIngredient NOT LIKE '%o%'

-- addresses for which Flat is not null and the apartment number is over 30
SELECT * FROM Address
WHERE Flat is NOT NULL AND Apartment IN ( SELECT Apartment FROM Address
										WHERE Apartment > 30 )


-- 2 queries with the difference operation; use EXCEPT and NOT IN

-- products with price between 20 and 30, except those who have a description of length under 60
SELECT Name, Price, Description 
FROM Product
WHERE Price BETWEEN 20 AND 30
EXCEPT
SELECT Name, Price, Description 
FROM Product
WHERE LEN(Description) < 60

-- deliveries completed in month of november, excepting those of ingredients expiring before 2023
SELECT * FROM Delivery
WHERE MONTH(DateOfDelivery) = 11 AND YEAR(DateOfDelivery) = 2022 AND ExpiringDate NOT IN ( SELECT ExpiringDate 
																						   FROM Delivery 
																						   WHERE YEAR(ExpiringDate) < 2023 )

-- 4 queries with INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN (one query per operator); 
-- one query will join at least 3 tables, while another one will join at least two many-to-many relationships

-- display for each product its id, name, price, description and type, ordered by price
SELECT p.Pid, p.Name, p.Price, p.Description, pt.Type
FROM Product p INNER JOIN ProductType pt ON p.PTid = pt.PTid
ORDER BY p.Price

-- display all the data for each ingredient with their deliveries, including the ingredients which were not delivered
SELECT *
FROM Ingredient i LEFT OUTER JOIN Delivery d ON i.Iid = d.Iid 
LEFT OUTER JOIN DeliveryMan dm ON dm.DMid = d.DMid

-- display all the clients and their orders, including those who haven't placed an order
SELECT *
FROM _Order o RIGHT OUTER JOIN Customer c ON c.Cid = o.Cid

-- display the products and the necessary ingredients, including ingredients those which are not included in a preparation 
SELECT p.Pid, p.Name, i.Iid, i.NameOfIngredient, i.Category
FROM Product p FULL OUTER JOIN Preparation pr ON p.Pid = pr.Pid
FULL OUTER JOIN Ingredient i ON pr.Iid = i.Iid


-- 2 queries with the IN operator and a subquery in the WHERE clause; in at least one case, 
-- the subquery must include a subquery in its own WHERE clause;

-- select the ingredients delivered before 2022-11-01 with a quantity over 20
SELECT DISTINCT i.Iid, i.NameOfIngredient, i.Category, d.Quantity
FROM Ingredient i, Delivery d
WHERE d.Quantity > 20 AND i.Iid = d.Iid AND d.DateOfDelivery IN ( SELECT DateOfDelivery FROM Delivery
															      WHERE DateOfDelivery < '2022-11-01')

-- find the customers that ordered Cheeseburger or Hamburger
SELECT c.FirstName, c.LastName 
FROM Customer c
WHERE c.Cid IN ( SELECT o.Cid FROM OrderProduct o
				 WHERE o.Pid IN ( SELECT p.Pid FROM Product p
								  WHERE p.Name = 'Cheeseburger' OR p.Name = 'Hamburger')
				)


-- 2 queries with the EXISTS operator and a subquery in the WHERE clause;

-- Products with price over 20 which have preparation and there is a 25% sale of their initial price, ordered descending
SELECT p.Pid, p.Name, p.Price, p.Price-0.25*p.Price AS Sale
FROM Product p
WHERE p.Price > 20 AND EXISTS ( SELECT * FROM Preparation i WHERE i.Pid = p.Pid)
ORDER BY Sale DESC

-- Id of the employees which have taken at least 3 orders
SELECT o.Eid, COUNT(*) AS TakenOrders
FROM _Order o
WHERE EXISTS ( SELECT * FROM Employee e WHERE o.Eid = e.Eid)
GROUP BY o.Eid
HAVING COUNT(*) >= 3


-- 2 queries with a subquery in the FROM clause; 

-- delivered ingredients with a quantity less than 20 
SELECT A.Iid, A.NameOfIngredient, A.Quantity
FROM (SELECT i.Iid, I.NameOfIngredient, d.Quantity
		FROM Ingredient i INNER JOIN Delivery d ON i.Iid = d.Iid
		WHERE d.Quantity < 20) A

-- display delivery men which delivered spices
SELECT DISTINCT B.DMid, B.Name
FROM (SELECT d.DMid, dm.Name
		FROM Ingredient i INNER JOIN Delivery d ON i.Iid = d.Iid INNER JOIN DeliveryMan dm ON dm.DMid = d.DMid
		WHERE i.Category = 'Spice') B



-- 4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause; 
-- 2 of the latter will also have a subquery in the HAVING clause; use the aggregation operators: COUNT, SUM, AVG, MIN, MAX;

-- display the top 2 most sold products (sold at least 3 times), the number of their sales and the amount of money obtained from their sales
SELECT TOP 2 p.Pid, p.Name, COUNT (*) AS NumberOfSales, SUM(p.Price) AS Amount
FROM Product p INNER JOIN OrderProduct op ON p.Pid = op.Pid
GROUP BY p.Pid, P.Name
HAVING COUNT(*) > 2
ORDER BY NumberOfSales DESC

-- display the average quantity for each delivered ingredient if it is greater than the minimum quantity of a product
SELECT d.Iid, i.NameOfIngredient, AVG(d.Quantity) AS 'Average Quantity'
FROM Delivery d INNER JOIN Ingredient i ON d.Iid = i.Iid
GROUP BY d.Iid, i.NameOfIngredient
HAVING AVG(d.Quantity) > (SELECT MIN(Quantity) FROM Delivery)

-- display the maximum price of a product from each type
SELECT pt.PTid, pt.Type, MAX(p.Price) AS 'Most Expensive'
FROM ProductType pt INNER JOIN Product p ON p.PTid = pt.PTid
GROUP BY pt.PTid, pt.Type

-- display the orders with a total price over the average price of ordered products
SELECT op.Cid, op.Eid, SUM(p.Price) AS Total
FROM OrderProduct op INNER JOIN Product p ON p.Pid = op.Pid
GROUP BY op.Cid, op.Eid
HAVING SUM(p.Price) > (SELECT AVG(p.Price)
						FROM OrderProduct op INNER JOIN Product p ON p.Pid = op.Pid)

-- 4 queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator); 
-- rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT] IN

-- display the products that are more expensive than all desserts 
SELECT p.Pid, Name, p.Price
FROM Product p
WHERE p.Price > ALL(SELECT p1.Price FROM Product p1 WHERE p1.PTid = 3)

SELECT p.Pid, Name, p.Price
FROM Product p
WHERE p.Price > (SELECT MAX(p1.Price) FROM Product p1 WHERE p1.PTid = 3)

-- display the ingredients with a quantity lower than any quantity of a meat ingredient
SELECT d.Iid, i.NameOfIngredient, d.Quantity
FROM Delivery d, Ingredient i
WHERE d.Iid = i.Iid AND d.Quantity < ANY(SELECT d1.Quantity FROM Delivery d1, Ingredient i1 WHERE d1.Iid = i1.Iid and i1.Category = 'meat')

SELECT d.Iid, i.NameOfIngredient, d.Quantity
FROM Delivery d, Ingredient i
WHERE d.Iid = i.Iid AND d.Quantity < (SELECT MAX(Quantity) FROM Delivery d1, Ingredient i1 WHERE d1.Iid = i1.Iid and i1.Category = 'meat')

-- display the orders that were not placed in november
SELECT *
FROM _Order
WHERE DateOfOrder <> ALL (SELECT DateOfOrder FROM _Order WHERE MONTH(DateOfOrder) = 11)

SELECT *
FROM _Order
WHERE DateOfOrder NOT IN (SELECT DateOfOrder FROM _Order WHERE MONTH(DateOfOrder) = 11)

-- display the cheapest 2 products which have a description of preparation of length greater than 50
SELECT TOP 2 p1.Pid, p.Name, P.Price, p1.Description, p1.Duration
FROM Product p INNER JOIN Preparation p1 ON p.Pid = p1.Pid
WHERE LEN(p1.Description) = ANY (SELECT LEN(p2.Description) FROM Preparation p2 WHERE LEN(p2.Description) > 50 )
ORDER BY p.Price

SELECT TOP 2 p1.Pid, p.Name, P.Price, p1.Description, p1.Duration
FROM Product p INNER JOIN Preparation p1 ON p.Pid = p1.Pid
WHERE p1.Description IN (SELECT p2.Description FROM Preparation p2 WHERE LEN(p2.Description) > 50 )
ORDER BY p.Price


