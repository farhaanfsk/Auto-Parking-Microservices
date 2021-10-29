CREATE TABLE Employee(
emp_id INT PRIMARY KEY IDENTITY(500000,1),
name VARCHAR(50) NOT NULL,
email VARCHAR(50) NOT NULL
);

CREATE TABLE Office(
id INT PRIMARY KEY IDENTITY(1,1),
name VARCHAR(50) NOT NULL,
address VARCHAR(100) NOT NULL,
city VARCHAR(50) NOT NULL,
);

CREATE TABLE Slot(
id INT PRIMARY KEY IDENTITY(1,1),
status VARCHAR(20) NOT NULL,
is_reserved BIT,
vehicle_type VARCHAR(20) NOT NULL,
office_id INT,
FOREIGN KEY(office_id) REFERENCES office(id)
);

CREATE TABLE Slot_Booking(
id INT PRIMARY KEY IDENTITY(1,1),
slot_id INT NOT NULL,
emp_id INT NOT NULL,
vehicle_id INT NOT NULL,
start_time DATETIME NOT NULL,
end_time DATETIME NOT NULL,
FOREIGN KEY(emp_id) REFERENCES Employee(emp_id),
FOREIGN KEY(slot_id) REFERENCES Slot(id),
FOREIGN KEY(vehicle_id) REFERENCES Vehicle(id)
);


CREATE TABLE Vehicle(
id INT PRIMARY KEY IDENTITY(1,1),
vehicle_no VARCHAR(20) NOT NULL,
is_company_owned BIT,
emp_id INT NOT NULL,
vehicle_type VARCHAR(20) NOT NULL,
FOREIGN KEY(emp_id) REFERENCES Employee(emp_id),
);