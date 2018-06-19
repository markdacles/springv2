CREATE TABLE users(
   username varchar(20) NOT NULL,
   password varchar(20) NOT NULL,
   enabled boolean NOT NULL DEFAULT TRUE,
   primary key(username)
);

create table user_roles (
  user_role_id SERIAL PRIMARY KEY,
  username varchar(20) NOT NULL,
  role varchar(20) NOT NULL,
  UNIQUE (username,role),
  FOREIGN KEY (username) REFERENCES users (username)
);

INSERT INTO users(username,password,enabled) VALUES ('admin','password', true);
INSERT INTO users(username,password,enabled) VALUES ('personnelman','personnel1', true);
INSERT INTO users(username,password,enabled) VALUES ('roleman','role1', true);
INSERT INTO users(username,password,enabled) VALUES ('projectman','project1', true);
 
INSERT INTO user_roles (username, role) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role) VALUES ('personnelman', 'ROLE_PERS');
INSERT INTO user_roles (username, role) VALUES ('roleman', 'ROLE_ROLE');
INSERT INTO user_roles (username, role) VALUES ('projectman', 'ROLE_PROJ');