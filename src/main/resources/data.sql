USE cryptus;
INSERT INTO `user`
VALUES (0,'John','gg','mekky','username','password','salt');
INSERT INTO `klant`
VALUES (LAST_INSERT_ID(),STR_TO_DATE('31-03-2015', '%d-%m-%Y'),'street',10,'1000','Amsterdam','bsn','email','1234567891');





