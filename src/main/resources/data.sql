/* Products */

insert into PRODUCT(ID, NAME, PRICE, CURRENCY) values(1001, 'iPhone', '600', 'EUR');
insert into PRODUCT(ID, NAME, PRICE, CURRENCY) values(1002, 'Samsung', '610', 'EUR');
insert into PRODUCT(ID, NAME, PRICE, CURRENCY) values(1003, 'LG', '620', 'EUR');


/* Offers */
insert into OFFER(ID, PRODUCT_ID, DESCRIPTION, PRICE, CURRENCY, START_DATE, END_DATE, STATUS)
values (2001, select id from PRODUCT where name = 'iPhone' ,'iPhone offer1', 500, 'EUR', '2018-05-01', '2018-05-20', 'ACTIVE');

insert into OFFER(ID, PRODUCT_ID, DESCRIPTION, PRICE, CURRENCY, START_DATE, END_DATE, STATUS)
values (2002, select id from PRODUCT where name = 'iPhone' ,'iPhone offer2', 530, 'EUR', '2018-05-05', '2018-05-23', 'ACTIVE');

insert into OFFER(ID, PRODUCT_ID, DESCRIPTION, PRICE, CURRENCY, START_DATE, END_DATE, STATUS)
values (2003, select id from PRODUCT where name = 'Samsung' ,'Samsung offer1', 500, 'EUR', '2018-05-01', '2018-05-20', 'ACTIVE');

insert into OFFER(ID, PRODUCT_ID, DESCRIPTION, PRICE, CURRENCY, START_DATE, END_DATE, STATUS)
values (2004, select id from PRODUCT where name = 'LG' ,'LG offer1', 590, 'EUR', '2017-06-01', '2017-06-20', 'EXPIRED');
