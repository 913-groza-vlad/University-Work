 
CREATE PROCEDURE addTables (@tableName varchar(20)) AS
	IF @tableName in (SELECT Name From Tables)
	BEGIN
		PRINT 'Table already added in Tables'
		return
	END
	INSERT INTO Tables (Name) values (@tableName)

go

