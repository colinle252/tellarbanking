-- Create 'company' table
CREATE TABLE credit.company (
	id UUID NOT NULL,
    company_id VARCHAR(256) NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(50),
    email VARCHAR(255)
);

-- Create 'employee' table
CREATE TABLE credit.employee (
	id UUID NOT NULL PRIMARY KEY,
    employee_id VARCHAR(256) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    email VARCHAR(255),
    phone VARCHAR(50),
    hire_date DATE NOT NULL
);

-- Create 'account' table
CREATE TABLE credit.account (
	id UUID NOT NULL PRIMARY KEY,
    account_id VARCHAR(256) NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    balance DECIMAL(15, 2) DEFAULT 0,
    created_date DATE DEFAULT CURRENT_DATE,
    status VARCHAR(50) DEFAULT 'Active'
);

-- Create 'transaction' table
CREATE TABLE credit.transaction (
	id UUID NOT NULL PRIMARY KEY,
    transaction_id VARCHAR(256) NOT NULL,
    transaction_date DATE DEFAULT CURRENT_DATE,
    amount DECIMAL(15, 2) NOT NULL,
    transaction_type VARCHAR(50) NOT NULL, -- Credit or Debit
    description VARCHAR(255)
);
