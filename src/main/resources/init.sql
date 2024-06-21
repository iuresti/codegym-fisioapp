CREATE TABLE users (
    id varchar(40) primary key ,
    name varchar(50),
    last_name varchar(50),
    avatar varchar(500),
    user varchar(20),
    password varchar(20),
    email varchar(250)
);

CREATE TABLE therapies (
  id varchar(40) primary key,
  name varchar(50),
  description varchar(500),
  active boolean
);