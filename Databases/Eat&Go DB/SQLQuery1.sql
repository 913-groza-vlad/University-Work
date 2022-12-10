DROP DATABASE EatandGo_Restaurant
go
CREATE DATABASE EatandGo_Restaurant

go 
use EatandGo_Restaurant
go 

CREATE TABLE Address (
	Aid int PRIMARY KEY,
	Street varchar(50) NOT NULL,
	StreetNo int,
	Flat varchar(50),
	FloorNo int,
	Apartment int
)

CREATE TABLE Employee (
	Eid int PRIMARY KEY, 
	FirstName varchar(50) NOT NULL,
	LastName varchar(50) NOT NULL,
	PhoneNumber varchar(13) NOT NULL,
	Email varchar(30),
	Aid int FOREIGN KEY REFERENCES Address(Aid)
)
ALTER TABLE Employee
ADD Role varchar(30) NOT NULL

CREATE TABLE CV (
	CVid int FOREIGN KEY REFERENCES Employee(Eid),
	EducationLevel varchar(50) NOT NULL,
	Specialization varchar(50),
	Competences varchar(250),
	Constraint PK_EmployeeCV PRIMARY KEY(CVid)
)

CREATE TABLE Customer (
	Cid int PRIMARY KEY, 
	FirstName varchar(50) NOT NULL,
	LastName varchar(50) NOT NULL,
	PhoneNumber varchar(13),
)

CREATE TABLE _Order (
	Cid int FOREIGN KEY REFERENCES Customer(Cid),
	Eid int FOREIGN KEY REFERENCES EMPLOYEE(Eid),
	DateOfOrder date NOT NULL,
	TimeOfOrder time NOT NULL,
	Constraint PK_EmployeeCustomer PRIMARY KEY(Cid, Eid)
)

CREATE TABLE ProductType (
	PTid int PRIMARY KEY,
	Type varchar(50) NOT NULL,	
)
ALTER TABLE ProductType 
ADD Details varchar(100)

CREATE TABLE Product (
	Pid int PRIMARY KEY,
	Name varchar(50) NOT NULL,
	Price int NOT NULL,
	Description varchar(50) NOT NULL,
	PTid int FOREIGN KEY REFERENCES ProductType(PTid)
)


CREATE TABLE OrderProduct (
	Cid int,
	Eid int,
	CONSTRAINT FK_OrderProduct FOREIGN KEY (Cid, Eid) REFERENCES _Order(Cid, Eid),
	Pid int ,
	CONSTRAINT FK_ProductOrder FOREIGN KEY (Pid) REFERENCES Product(Pid),
	Constraint PK_OrderProduct PRIMARY KEY(Cid, Eid, Pid)
	
)


CREATE TABLE Ingredient (
	Iid int PRIMARY KEY,
	Category varchar(30) NOT NULL,
	NameOfIngredient varchar(30) NOT NULL
)

CREATE TABLE Preparation (
	Pid int FOREIGN KEY REFERENCES Product(Pid),
	Iid int FOREIGN KEY REFERENCES Ingredient(Iid),
	Description varchar(150),
	Duration varchar(10) NOT NULL,
	CONSTRAINT PK_ProductIngredient PRIMARY KEY(Pid, Iid)
)


CREATE TABLE Supplier(
	Sid int PRIMARY KEY,
	NameOfSupplier varchar(50)
)

CREATE TABLE DeliveryMan (
	DMid int PRIMARY KEY,
	Name varchar(50) NOT NULL,
	PhoneNumber varchar(13) NOT NULL,
	Sid int FOREIGN KEY REFERENCES Supplier(Sid)
)


CREATE TABLE Delivery (
	DMid int FOREIGN KEY REFERENCES DeliveryMan(DMid),
	Iid int FOREIGN KEY REFERENCES Ingredient(Iid),
	CONSTRAINT PK_IngredientDelivery PRIMARY KEY(DMid, Iid),
	Quantity float NOT NULL,
	DateOfDelivery date NOT NULL,
	ExpiringDate date
)

ALTER TABLE Delivery
ADD CONSTRAINT expiringDate_range CHECK(ExpiringDate >= '2022-01-01')