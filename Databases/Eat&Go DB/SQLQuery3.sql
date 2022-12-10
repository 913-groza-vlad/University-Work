
--  create / drop a table
CREATE PROCEDURE createTableLocation AS
BEGIN
	CREATE TABLE LocationOfSupplier (
		Lid int NOT NULL,
		Country varchar(20) NOT NULL,
		City varchar(25) NOT NULL,
		ZipCode varchar(max),
		Sid int NOT NULL
	)
	PRINT 'Table LocationOfSupplier created' 
END

go
CREATE PROCEDURE dropTableLocation AS
BEGIN
	DROP TABLE LocationOfSupplier
	PRINT 'Table LocationOfSupplier dropped'
END

go

--  add / remove a column
CREATE PROCEDURE addFiscalValueToSupplier AS
BEGIN
	ALTER TABLE Supplier
	ADD FiscalValue float
	PRINT 'Column FiscalValue was added in table Supplier'
END

go

CREATE PROCEDURE removeFiscalValueFromSupplier AS
BEGIN
	ALTER TABLE Supplier
	DROP COLUMN FiscalValue
	PRINT 'Column FiscalValue was removed from table Supplier'
END

go

--  modify the type of a column
CREATE PROCEDURE modifyTypeOfZipcode AS
BEGIN
	ALTER TABLE LocationOfSupplier
	ALTER COLUMN ZipCode int
	PRINT 'Type of column ZipCode from table LocationOfSupplier was changed to int'
END

go

CREATE PROCEDURE previousTypeOfZipcode AS
BEGIN
	ALTER TABLE LocationOfSupplier
	ALTER COLUMN ZipCode varchar(max)
	PRINT 'Type of column ZipCode from table LocationOfSupplier was changed to varchar'
END

go

--  add / remove a DEFAULT constraint
CREATE PROCEDURE addDefaultConsToCountry AS
BEGIN
	ALTER TABLE LocationOfSupplier
	ADD Constraint df_Rom DEFAULT 'Romania' FOR Country
	PRINT 'Default constraint for column Country added in table LocationOfSupplier'
END

go

CREATE PROCEDURE removeDefaultConsFromCountry AS
BEGIN
	ALTER TABLE LocationOfSupplier
	DROP Constraint df_Rom
	PRINT 'Default constraint removed from table LocationOfSupplier'
END

go

--  add / remove a primary key
CREATE PROCEDURE addPrimaryConsToLid AS
BEGIN
	ALTER TABLE LocationOfSupplier
	ADD Constraint pk_Location PRIMARY KEY(Lid)
	PRINT 'Primary Key added in table LocationOfSupplier'
END

go

CREATE PROCEDURE removePrimaryConsFromLid AS
BEGIN
	ALTER TABLE LocationOfSupplier
	DROP Constraint pk_Location
	PRINT 'Primary Key removed from table LocationOfSupplier'
END

go

--  add / remove a foreign key
CREATE PROCEDURE addForeignConsToSid AS
BEGIN
	ALTER TABLE LocationOfSupplier
	ADD Constraint fk_Supplier_Location FOREIGN KEY(Sid) REFERENCES Supplier(Sid)
	PRINT 'Foreign Key added in table LocationOfSupplier'
END

go

CREATE PROCEDURE removeForeignConsFromSid AS
BEGIN
	ALTER TABLE LocationOfSupplier
	DROP Constraint fk_Supplier_Location
	PRINT 'Foreign Key removed from table LocationOfSupplier'
END

go

--  add / remove a candidate key
CREATE PROCEDURE addCandidateKeyToLid AS
BEGIN
	ALTER TABLE LocationOfSupplier
	ADD Constraint uk_Location UNIQUE(Lid)
	PRINT 'Candidate Key added in table LocationOfSupplier'
END

go

CREATE PROCEDURE removeCandidateKeyFromLid AS
BEGIN
	ALTER TABLE LocationOfSupplier
	DROP Constraint uk_Location
	PRINT 'Candidate Key removed from table LocationOfSupplier'
END

go


--  create the version table and set the initial version to 0
CREATE TABLE Version (
	VersionNo int
)
INSERT INTO Version values (0)
--  create a table in which the procedures are stored and the versions from/to which the database 
CREATE TABLE StoredProcedures (
	oldVersion int NOT NULL,
	newVersion int NOT NULL,
	procedureName varchar(30) NOT NULL
)
INSERT INTO StoredProcedures values (0, 1, 'createTableLocation')
INSERT INTO StoredProcedures values (1, 0, 'dropTableLocation')
INSERT INTO StoredProcedures values (1, 2, 'modifyTypeOfZipcode')
INSERT INTO StoredProcedures values (2, 1, 'previousTypeOfZipcode')
INSERT INTO StoredProcedures values (2, 3, 'addFiscalValueToSupplier')
INSERT INTO StoredProcedures values (3, 2, 'removeFiscalValueFromSupplier')
INSERT INTO StoredProcedures values (3, 4, 'addDefaultConsToCountry')
INSERT INTO StoredProcedures values (4, 3, 'removeDefaultConsFromCountry')
INSERT INTO StoredProcedures values (4, 5, 'addPrimaryConsToLid')
INSERT INTO StoredProcedures values (5, 4, 'removePrimaryConsFromLid')
INSERT INTO StoredProcedures values (5, 6, 'addCandidateKeyToLid')
INSERT INTO StoredProcedures values (6, 5, 'removeCandidateKeyFromLid')
INSERT INTO StoredProcedures values (6, 7, 'addForeignConsToSid')
INSERT INTO StoredProcedures values (7, 6, 'removeForeignConsFromSid')

go

CREATE PROCEDURE bringToVersion @newVersion int 
AS
BEGIN
	declare @currentVersion int
	declare @procedureName varchar(50)

	SELECT @currentVersion = Version.VersionNo FROM Version
	IF @newVersion < 0
	BEGIN
		RAISERROR('Invalid version number!', 10, 1)
		RETURN
	END
	IF @newVersion > 7
	BEGIN
		RAISERROR('This version does not exist!', 10, 1)
		RETURN
	END

	IF @currentVersion = @newVersion
		PRINT 'Database is already in this version'
	ELSE
	BEGIN
		IF @currentVersion < @newVersion
			WHILE @currentVersion < @newVersion
			BEGIN
				SELECT @procedureName = procedureName FROM StoredProcedures WHERE oldVersion = @currentVersion AND newVersion = @currentVersion+1
				EXEC @procedureName
				SET @currentVersion = @currentVersion + 1
			END
		IF @currentVersion > @newVersion
			WHILE @currentVersion > @newVersion
			BEGIN
				SELECT @procedureName = procedureName FROM StoredProcedures WHERE oldVersion = @currentVersion AND newVersion = @currentVersion-1
				EXEC @procedureName
				SET @currentVersion = @currentVersion - 1
			END

		UPDATE Version
		SET VersionNo = @newVersion
		PRINT 'Database is now in version ' + CAST(@newVersion AS varchar(3))
	END

END

go

EXEC bringToVersion 0
EXEC bringToVersion 1
EXEC bringToVersion 7
EXEC bringToVersion 5
EXEC bringToVersion -2