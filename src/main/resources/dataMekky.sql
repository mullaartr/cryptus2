USE cryptus;
INSERT INTO `user`
VALUES (0,'Khaled','FF','mekky','WackyMekky','ZIG-ZAG');
INSERT INTO `klant`
VALUES (LAST_INSERT_ID(),STR_TO_DATE('31-03-2015', '%d-%m-%Y'),'street',10,'1000','Amsterdam','bsn','myemail@email.com','1234567891');





