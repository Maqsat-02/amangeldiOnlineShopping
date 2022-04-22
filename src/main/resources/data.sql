insert into `APP_USER`(ID,FULL_NAME ,address,balance,username,password) values(1,'Erkin Jumaliev','st. Abdirova 123',500000,'erkin','1dc2b3g456');
insert into `APP_USER`(ID,FULL_NAME ,address,balance,username,password) values(2,'Dias Uliashev','st. Abay 243',300000,'diasUl','7634cv5sd6');
insert into `APP_USER`(ID,FULL_NAME ,address,balance,username,password) values(3,'Serik Ospanov','st. Aitiev 123',100000,'seriik','sd2345gf6');

insert into `APP_USER_APP_USER_ROLES`(APP_USER_ID,APP_USER_ROLES) values(1,0);
insert into `APP_USER_APP_USER_ROLES` (APP_USER_ID,APP_USER_ROLES) values(2,1);
insert into `APP_USER_APP_USER_ROLES` (APP_USER_ID,APP_USER_ROLES) values(3,1);

insert into `order`(id,ORDER_DATE,TOTAL_PRICE ,IS_PAID,IS_DELIVERED,USER_ID) values(1,'2022-04-01 11:30:00',0,true,true,1);
insert into `order`(id,ORDER_DATE,TOTAL_PRICE ,IS_PAID,IS_DELIVERED,USER_ID)  values(2,'2022-04-03 18:40:50',0,false,false,2);
insert into `order`(id,ORDER_DATE,TOTAL_PRICE ,IS_PAID,IS_DELIVERED,USER_ID)  values(3,'2022-04-06 20:30:39',0,true,false,3);

insert into `ITEMS`(id,name,PRICE,category, ORDER_ID) values (1,'IPHONE X 256GB',485990,'Smartphones',null);
insert into `ITEMS`(id,name,PRICE,category, ORDER_ID) values (2,'SAMSUNG GALAXY S8 64GB',253990,'Smartphones',1);
insert into `ITEMS`(id,name,PRICE,category, ORDER_ID) values(3,'IPHONE 7 128GB SILVER 256GB X 256GB',279990,'Smartphones',2);
insert into `ITEMS`(id,name,PRICE,category, ORDER_ID) values(4,'Samsung Galaxy Note 8 64GB',349990,'Smartphones',3);
insert into ITEMS(id,name,PRICE,category, ORDER_ID) values(5,'XIAOMI REDMI NOTE 3 32GB',105990,'Smartphones',null);
