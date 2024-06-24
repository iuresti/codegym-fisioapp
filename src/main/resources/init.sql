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
CREATE TABLE physical_exploration
(
    id                      varchar(40) primary key,
    weight                  decimal(5, 2),
    height                  decimal(5, 3),
    weekly_cardio_frequency int,
    blood_type              varchar(2)
);
CREATE TABLE personal_records
(
    id                                      varchar(40) primary key,
    chronic_diseases                        varchar(255),
    previous_surgeries_and_hospitalizations varchar(255),
    medicines                               varchar(255),
    allergies                               varchar(255),
    neurological_conditions                 varchar(255),
    cardiovascular_conditions               varchar(255),
    respiratory_conditions                  varchar(255),
    musculoskeletal_conditions              varchar(255)
);
CREATE TABLE familiar_records
(
    id                        varchar(40) primary key,
    hereditary_diseases       varchar(255),
    genetical_predispositions varchar(255)
);
CREATE TABLE fisioapp.medical_record
(
    id                      varchar(40) NOT NULL,
    patient_id              varchar(255),
    physical_exploration_id varchar(255),
    personal_records_id     varchar(255),
    familiar_records        varchar(255),
    primary key (id),
    foreign key (patient_id) references patient (id),
    foreign key (physical_exploration_id) references physical_exploration (id),
    foreign key (personal_records_id) references personal_records (id),
    foreign key (familiar_records) references familiar_records (id)
);