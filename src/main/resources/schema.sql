create table if not exists `APP_USER`(
                                     ID INTEGER AUTO_INCREMENT,
                                      FULL_NAME  VARCHAR(100),
                                      address VARCHAR(50),
                                      balance LONG,
                                      username VARCHAR(50),
                                      password VARCHAR(100),
                                      PRIMARY KEY(ID)
);

create table if not exists `APP_USER_APP_USER_ROLES` (
                      APP_USER_ROLES  int,
                      APP_USER_ID  int,
                      FOREIGN KEY( APP_USER_ID ) references APP_USER(ID)

);
create table if not exists`order`(
                  ID int AUTO_INCREMENT NOT NULL  ,
                  ORDER_DATE TIMESTAMP ,
                  TOTAL_PRICE long,
                  IS_PAID boolean,
                  IS_DELIVERED boolean,
                  USER_ID int,
                  PRIMARY KEY(ID),
                  FOREIGN KEY(USER_ID) references  APP_USER(ID) ON DELETE RESTRICT
);
create table if not exists `items`(
                  ID int AUTO_INCREMENT NOT NULL ,
                  NAME varchar(50),
                  PRICE long,
                  CATEGORY varchar(20),
                  ORDER_ID int,
                  PRIMARY KEY(id),
                  FOREIGN KEY(ORDER_ID) references  `order`(ID) ON DELETE CASCADE
);