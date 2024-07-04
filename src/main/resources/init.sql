CREATE TABLE users
(
    id        varchar(40) primary key,
    name      varchar(50),
    last_name varchar(50),
    avatar    varchar(500),
    user      varchar(20),
    password  varchar(20),
    email     varchar(250)
);
CREATE TABLE medical_record
(
    id                                      varchar(40) primary key,
    patient_id                              varchar(40),
    weight                                  decimal(5, 2),
    height                                  decimal(3, 2),
    weekly_cardio_frequency                 integer,
    blood_type                              varchar(255),
    chronic_diseases                        varchar(255),
    previous_surgeries_and_hospitalizations varchar(255),
    medicines                               varchar(255),
    allergies                               varchar(255),
    neurological_conditions                 varchar(255),
    cardiovascular_conditions               varchar(255),
    respiratory_conditions                  varchar(255),
    musculoskeletal_conditions              varchar(255),
    hereditary_diseases                     varchar(255),
    genetical_predispositions               varchar(255),
    foreign key (patient_id) references patient (id)
);
CREATE TABLE patient (
     id   varchar(40) PRIMARY KEY,
     name varchar(40)
);
CREATE TABLE therapies (
  id varchar(40) primary key,
  name varchar(50),
  description varchar(500),
  active boolean
);

CREATE TABLE therapist (
id VARCHAR(40),
first_name VARCHAR(255),
last_name VARCHAR(255),
birth_date DATE,
gender VARCHAR(10),
phone VARCHAR(20),
address VARCHAR(255),
specialties VARCHAR(255)
);