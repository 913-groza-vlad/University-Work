 
CREATE PROCEDURE addTable (@tableName varchar(20)) AS
	IF @tableName in (SELECT Name From Tables)
	BEGIN
		PRINT 'Table already added in Tables'
		return
	END
	INSERT INTO Tables (Name) values (@tableName)

go

CREATE PROCEDURE addView (@viewName varchar(20)) AS
	IF @viewName in (SELECT Name From Views)
	BEGIN
		PRINT 'View already added in Views'
		return
	END
	INSERT INTO Views(Name) values (@viewName)

go

CREATE PROCEDURE addTest (@testName varchar(20)) AS
	IF @testName in (SELECT Name From Tests)
	BEGIN
		PRINT 'Test already added in Tests'
		return
	END
	INSERT INTO Tests(Name) values (@testName)

go