-- creating the procedure addTable and adding 3 table names to Tables
 
CREATE PROCEDURE addTable (@tableName varchar(20)) AS
	IF @tableName in (SELECT Name From Tables)
	BEGIN
		PRINT 'Table already added in Tables'
		return
	END
	INSERT INTO Tables (Name) values (@tableName)

EXEC addTable 'Ingredient'
EXEC addTable 'Product'
EXEC addTable 'Preparation'
-- SELECT * FROM Tables

go

-- creating and adding views in table Views

CREATE PROCEDURE addView (@viewName varchar(20)) AS
	IF @viewName in (SELECT Name From Views)
	BEGIN
		PRINT 'View already added in Views'
		return
	END
	INSERT INTO Views(Name) values (@viewName)

go

-- view that displays the id, name and price of the products with price greater than 20
CREATE VIEW View_1 AS
	SELECT Pid, Name, Price
	FROM Product 
	WHERE Price > 20
go

-- view that displays the product id, product name and the ingredients used for each product
CREATE VIEW View_2 AS
	SELECT p.Pid, p.Name, i.Iid, i.NameOfIngredient, i.Category
	FROM Product p INNER JOIN Preparation pr ON p.Pid = pr.Pid
	INNER JOIN Ingredient i ON pr.Iid = i.Iid 

go

-- view that displays the max price of a product of each type
CREATE VIEW View_3 AS
	SELECT pt.PTid, pt.Type, MAX(p.Price) AS 'Most Expensive'
	FROM ProductType pt INNER JOIN Product p ON p.PTid = pt.PTid
	GROUP BY pt.PTid, pt.Type

go

EXEC addView 'View_1'
EXEC addView 'View_2'
EXEC addView 'View_3'
-- SELECT * FROM Views

go

-- creating the procedure that adds a test in table Tests

CREATE PROCEDURE addTest (@testName varchar(50)) AS
	IF @testName in (SELECT Name From Tests)
	BEGIN
		PRINT 'Test already added in Tests'
		return
	END
	INSERT INTO Tests(Name) values (@testName)


EXEC addTest 'insert_table'
EXEC addTest 'delete_table'
EXEC addTest 'select_view'
-- SELECT * FROM Tests

go

-- creating the procedure that adds to TestTables the combinations of tests related to tables

CREATE PROCEDURE addToTestTables (@tableName varchar(20), @testName varchar(50), @rows int, @position int) AS
	IF @tableName NOT IN (SELECT Name From Tables)
	BEGIN
		PRINT 'Table ' + @tableName + ' not present in Tables'
		return
	END
	IF @testName NOT IN (SELECT Name From Tests)
	BEGIN
		PRINT @testName + ' not present in Tests'
		return
	END

	declare @testID int
	declare @tableID int
	SELECT @testID = TestID FROM Tests WHERE Name = @testName
	SELECT @tableID = TableID FROM Tables WHERE Name = @tableName
	INSERT INTO TestTables (TestID, TableID, NoOfRows, Position) values (@testID, @tableID, @rows, @position)

go

EXEC addToTestTables 'Ingredient', 'insert_table', 500, 1
EXEC addToTestTables 'Product', 'insert_table', 100, 2
EXEC addToTestTables 'Preparation', 'insert_table', 1000, 3
EXEC addToTestTables 'Ingredient', 'delete_table', 500, 3
EXEC addToTestTables 'Product', 'delete_table', 100, 2
EXEC addToTestTables 'Preparation', 'delete_table', 1000, 1
-- SELECT * FROM TestTables

go

-- creating the procedure that adds to TestViews the combinations of tests related with the views

CREATE PROCEDURE addToTestViews (@viewName varchar(20), @testName varchar(50)) AS
	IF @viewName NOT IN (SELECT Name From Views)
	BEGIN
		PRINT 'View ' + @viewName + ' not present in Views'
		return
	END
	IF @testName NOT IN (SELECT Name From Tests)
	BEGIN
		PRINT @testName + ' not present in Tests'
		return
	END

	declare @testID int
	declare @viewID int
	SELECT @testID = TestID FROM Tests WHERE Name = @testName
	SELECT @viewID = ViewID FROM Views WHERE Name = @viewName
	INSERT INTO TestViews (TestID, ViewID) values (@testID, @viewID)

go

EXEC addToTestViews 'View_1', 'select_view'
EXEC addToTestViews 'View_2', 'select_view'
EXEC addToTestViews 'View_3', 'select_view'
-- SELECT * FROM TestViews

go


---- inserting data into each of the 3 tables

CREATE PROCEDURE insertIntoIngredient(@rows int) AS
	WHILE @rows > 0 
	BEGIN
		INSERT INTO Ingredient(Iid, Category, NameOfIngredient) values (@rows + 100, 'Fast Food', 'Ingredient')
		SET @rows = @rows - 1
	END

go

CREATE PROCEDURE insertIntoProduct(@rows int) AS
	declare @fk_PT int
	WHILE @rows > 0 
	BEGIN
		SELECT TOP 1 @fk_PT = PTid FROM ProductType ORDER BY NEWID()
		INSERT INTO Product(Pid, Name, Price, Description, PTid) values (@rows + 50, 'Product', floor(80 * rand()), 'Description', @fk_PT)
		SET @rows = @rows - 1
	END

go

CREATE PROCEDURE insertIntoPreparation(@rows int) AS
	declare @fk_Pid int
	declare @fk_Iid int
	WHILE @rows > 0 
	BEGIN
		SELECT TOP 1 @fk_Pid = Pid FROM Product ORDER BY NEWID()
		SELECT TOP 1 @fk_Iid = Iid FROM Ingredient ORDER BY NEWID()
		WHILE exists (SELECT * FROM Preparation WHERE @fk_Iid = Iid AND @fk_Iid = Pid)
		BEGIN
			SELECT TOP 1 @fk_Pid = Pid FROM Product ORDER BY NEWID()
			SELECT TOP 1 @fk_Iid = Iid FROM Ingredient ORDER BY NEWID()
		END
		INSERT INTO Preparation(Pid, Iid, Description, Duration) values (@fk_Pid, @fk_Iid, 'Description', '30 min')
		SET @rows = @rows - 1
	END

go


-- create the procedure which runs a test

CREATE PROCEDURE runTest (@testName varchar(50)) AS
	IF @testName NOT IN (SELECT Name From Tests)
	BEGIN
		PRINT @testName + ' not present in Tests'
		return
	END

	DECLARE @testID int
	DECLARE @testRunID int
    DECLARE @tableName varchar(50)
    DECLARE @rows int
    DECLARE @pos int
    DECLARE @viewName varchar(50)
	DECLARE @testStartTime datetime2
	DECLARE @testEndTime datetime2
    DECLARE @startTime datetime2
    DECLARE @endTime datetime2
	DECLARE @execCommand varchar(50)

    SELECT @testID=TestID from Tests WHERE Name = @testName
	SET @testRunID = (SELECT MAX(TestRunID) + 1 FROM TestRuns)
	IF @testRunID is NULL
	BEGIN
		SET @testRunID = 1
	END

	-- two cursors are declared in order to process the data from Tables/Views row-by-row, considering the Position in case of Tables
	DECLARE tableCursor CURSOR SCROLL FOR
		SELECT T.Name, TT.NoOfRows, TT.Position
        FROM Tables T INNER JOIN TestTables TT on T.TableID = TT.TableID
        WHERE @testId = TT.TestID
        ORDER BY TT.Position
	DECLARE viewCursor CURSOR FOR
		SELECT V.Name
		FROM Views V INNER JOIN TestViews TV ON V.ViewID = TV.ViewID
		WHERE @testID = TV.TestID

	SET @testStartTime = SYSDATETIME()

	-- insert the testRunID, description and the StartTime of the test into table TestRuns (EndAt value is updated at the end)
	SET IDENTITY_INSERT TestRuns ON
	INSERT INTO TestRuns (TestRunID, Description, StartAt) values (@testRunID, 'Results for test: ' + @testName, @testStartTime)
	SET IDENTITY_INSERT TestRuns OFF

	-- if the test evaluates the delete op of tables, tableCursor is opened and data from tables is deleted starting with
	-- the table with the lowest pos and ending with the one with the highest position
	-- insert the testID, tableID and startTime, endTime in the table TestRunTables
	IF @testName LIKE 'delete%' 
	BEGIN
		OPEN tableCursor
		FETCH FIRST FROM tableCursor INTO @tableName, @rows, @pos
		WHILE @@FETCH_STATUS = 0 
		BEGIN
			SET @startTime = SYSDATETIME()
			EXEC ('DELETE FROM '+ @tableName)
			SET @endTime = SYSDATETIME()
			INSERT INTO TestRunTables (TestRunID, TableId, StartAt, EndAt) values (@testRunID, (SELECT TableID FROM Tables WHERE Name=@tableName), @startTime, @endTime)
			FETCH NEXT FROM tableCursor INTO @tableName, @rows, @pos
		END
		CLOSE tableCursor
	END

	-- if the test evaluates the insert op in tables, tableCursor is opened and data is inserted to tables starting with
	-- the table with the lowest pos and ending with the one with the highest position
	-- insert the testID, tableID and startTime, endTime in the table TestRunTables
	IF @testName LIKE 'insert%' 
	BEGIN
		OPEN tableCursor
		FETCH tableCursor INTO @tableName, @rows, @pos
		WHILE @@FETCH_STATUS = 0 
		BEGIN
			SET @execCommand = 'insertInto' + @tableName
			SET @startTime = SYSDATETIME()
			EXEC @execCommand @rows
			SET @endTime = SYSDATETIME()
			INSERT INTO TestRunTables (TestRunID, TableId, StartAt, EndAt) values (@testRunID, (SELECT TableID FROM Tables WHERE Name=@tableName), @startTime, @endTime)
			FETCH tableCursor INTO @tableName, @rows, @pos
		END
		CLOSE tableCursor
	END

	-- viewCursor is opened and all the views are executed when the testRunID corresponds with the
	-- testID in table TestViews
	OPEN viewCursor
	FETCH viewCursor INTO @viewName
	WHILE @@FETCH_STATUS = 0 
	BEGIN
		SET @startTime = SYSDATETIME()
		EXEC ('SELECT * FROM ' + @viewName)
		SET @endTime = SYSDATETIME()
		INSERT INTO TestRunViews (TestRunID, ViewID, StartAt, EndAt) values (@testRunID, (SELECT ViewID FROM Views WHERE Name=@viewName), @startTime, @endTime)
		FETCH viewCursor INTO @viewName
	END
	CLOSE viewCursor

	-- cursors are deallocated
	DEALLOCATE tableCursor
	DEALLOCATE viewCursor

	-- endTime of the test is set in the table TestRuns
	SET @testEndTime = SYSDATETIME()
	UPDATE TestRuns
	SET EndAt = @testEndTime
	WHERE TestRunID = @testRunID

GO

-- run the tests

-- test 'insert_table' populates tables Ingredient, Product, Preparation
-- test 'delete_table' deletes data from tables Preparation, Product, Ingredient
-- test 'select_view' displays the views View_1, View_2, View_3
EXEC runTest 'insert_table'
EXEC runTest 'delete_table'
EXEC runTest 'select_view'


SELECT * FROM TestRuns
SELECT * FROM TestRunTables
SELECT * FROM TestRunViews

DELETE FROM TestRuns
DELETE FROM TestRunTables
DELETE FROM TestRunViews


/* DELETE FROM Preparation
DELETE FROM Product WHERE Pid >= 50
DELETE FROM Ingredient WHERE Iid >= 100
DELETE FROM OrderProduct
DELETE FROM Delivery
SELECT * FROM Product
SELECT * FROM Ingredient 
SELECT * FROM Preparation */