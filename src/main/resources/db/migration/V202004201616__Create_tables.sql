CREATE TABLE IF NOT EXISTS employee(
   employee_id long auto_increment not null,
   name varchar(50),
   age int,
   gender varchar(10),
   salary int,
   company_id int,
   PRIMARY KEY(employee_id)
);

CREATE TABLE IF NOT EXISTS company(
   company_id int auto_increment not null,
   company_name varchar(100),
   employees_number int,
   PRIMARY KEY(company_id)
);

CREATE TABLE IF NOT EXISTS parking_boy(
    employee_id long,
    nick_name varchar(50)
);