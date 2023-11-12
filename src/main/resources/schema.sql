CREATE TABLE IF NOT EXISTS user_table (
    userid BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- Address table to store addresses
CREATE TABLE IF NOT EXISTS address_table (
    addressid BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- Status table to store status of tasks
CREATE TABLE IF NOT EXISTS status_table (
    statusid BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- Status table to store status of tasks
CREATE TABLE IF NOT EXISTS employee_table (
    employeeid BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- Status table to store status of tasks
CREATE TABLE IF NOT EXISTS approval_table (
    approvalid BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- Task table to store tasks with references to addresses, users, and statuses
CREATE TABLE IF NOT EXISTS task_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    addressid BIGINT,
    employeeid BIGINT,
    request VARCHAR(255),
    response VARCHAR(255),
    feedback VARCHAR(255),
    posted_date DATETIME,
    deadline DATETIME,
    statusid BIGINT,
    approvalid BIGINT,
    FOREIGN KEY (addressid) REFERENCES address_table(addressid),
    FOREIGN KEY (employeeid) REFERENCES employee_table(employeeid),
    FOREIGN KEY (statusid) REFERENCES status_table(statusid),
    FOREIGN KEY (approvalid) REFERENCES approval_table(approvalid)
);