DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS email_data;
DROP TABLE IF EXISTS phone_data;


CREATE TABLE users(
    id             INT AUTO_INCREMENT,
    date_of_birth  TIMESTAMP NOT NULL,
    name           varchar(500) NOT NULL,
    password       varchar(500) DEFAULT NULL,
    CONSTRAINT user_PK PRIMARY KEY (id)
);

CREATE TABLE account(
    id               INT AUTO_INCREMENT,
    balance          INT NOT NULL,
    user_id          INT NOT NULL,
    CONSTRAINT account_PK PRIMARY KEY (id),
    CONSTRAINT account_user_FK FOREIGN KEY (account_id) REFERENCES users (id) on DELETE CASCADE
);

CREATE TABLE email_data(
    id            INT AUTO_INCREMENT,
    email         varchar(200) NOT NULL,
    user_id       INT NOT NULL,
    CONSTRAINT email_data_PK PRIMARY KEY (id),
    CONSTRAINT email_data_user_FK FOREIGN KEY (user_id) REFERENCES users (id) on DELETE CASCADE
);

CREATE TABLE phone_data(
    id            INT AUTO_INCREMENT,
    phone         varchar(13),
    user_id       INT NOT NULL,
    CONSTRAINT phone_data_PK PRIMARY KEY (id),
    CONSTRAINT phone_data_user_FK FOREIGN KEY (user_id) REFERENCES users (id) on DELETE CASCADE
);
