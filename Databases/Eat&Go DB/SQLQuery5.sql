
-- ALTER TABLE Ingredient
-- ADD SerialNr int not null UNIQUE

-- a. Write queries on Ta such that their execution plans contain the following operators

-- clustered index scan
SELECT * FROM Ingredient
ORDER BY Iid

-- clustered index seek
SELECT * FROM Ingredient
WHERE Iid = 10

-- nonclustered index scan
IF EXISTS (SELECT name FROM sys.indexes WHERE name = 'N_idx_Ingredient_Name')
DROP INDEX N_idx_Ingredient_Name ON Ingredient
go

CREATE NONCLUSTERED INDEX N_idx_Ingredient_Name ON Ingredient(NameOfIngredient)
go

SELECT NameOfIngredient FROM Ingredient
ORDER BY NameOfIngredient

-- nonclustered index seek
SELECT SerialNr FROM Ingredient
WHERE SerialNr > 7

-- key lookup
SELECT NameOfIngredient FROM Ingredient
WHERE SerialNr = 3


-- b. Write a query on table Tb with a WHERE clause of the form WHERE b2 = value and analyze its execution plan. 
-- Create a nonclustered index that can speed up the query. Examine the execution plan again.

SELECT Pid, Price FROM Product
WHERE Price = 6

IF EXISTS (SELECT name FROM sys.indexes WHERE name = 'N_idx_Product_Price')
DROP INDEX N_idx_Product_Price ON Product
go
-- with clustered index -> estimated operator cost: 0.0032974

CREATE NONCLUSTERED INDEX N_idx_Product_Price ON Product(Price)
go

SELECT Pid, Price FROM Product
WHERE Price = 6
-- with nonclustered index -> estimated operator cost: 0.0032842

-- c. Create a view that joins at least 2 tables. Check whether existing indexes are helpful; 
-- if not, reassess existing indexes / examine the cardinality of the tables.
go

CREATE OR ALTER VIEW View_idx AS
	SELECT TOP 50 p.Name, p.Price, i.NameOfIngredient
	FROM Ingredient i INNER JOIN Preparation pr ON pr.Iid = i.Iid INNER JOIN Product p ON p.Pid = pr.Pid
	ORDER BY p.Price

go
SELECT * FROM View_idx


------
IF EXISTS (SELECT name FROM sys.indexes WHERE name = 'N_idx_Ingredient_ID')
DROP INDEX N_idx_Ingredient_ID ON Preparation
go
CREATE NONCLUSTERED INDEX N_idx_Ingredient_ID ON Preparation(Iid)

IF EXISTS (SELECT name FROM sys.indexes WHERE name = 'N_idx_Product_ID')
DROP INDEX N_idx_Product_ID ON Preparation
go
CREATE NONCLUSTERED INDEX N_idx_Product_ID ON Preparation(Pid)

exec sp_helpindex 'Ingredient'
exec sp_helpindex 'Product'