CREATE TABLE USER_ACCOUNTS (
    a_id varchar2(36) not null,
    a_username varchar2(50) not null,
    a_password varchar2(50) not null, 
    a_firstName varchar2(50) not null,
    a_lastName varchar2(50) not null,
    a_accountType varchar2(50) not null,
    a_isAdmin number(1) default 0,
    a_active number(1) default 0,
    a_balance number(15),
primary key (a_id)
);
