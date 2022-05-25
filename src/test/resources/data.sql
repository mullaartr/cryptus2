USE `cryptus`;
/*SET FOREIGN_KEY_CHECKS=0;*/

INSERT INTO `user`
VALUES (1, 'Rogier', NULL, 'Mullaart', 'mullaart', '3f2b04468dffbaa00ae5651d8ff2586b2b6c7568e0f4796a61a01c883ecd9476'),
       (2, 'Frits', NULL, 'Botersprits', 'boter', '12345'),
       (3, 'Jan', 'van', 'Zevenaar', 'zeven', '12345'),
       (4, 'Gerard', NULL, 'Klaasen', 'klaas', '12345'),
       (5, 'Tinus', NULL, 'Peters', 'peter', '12345'),
       (6, 'Klaas', '', 'Fransen', 'frans', '12345'),
       (7, 'Barry', NULL, 'Olafsen', 'olaf', '12345'),
       (8, 'Peter', NULL, 'Jacobseb', 'jacob', '12345'),
       (9, 'Lars', 'van der', 'Lussen', 'lus', '12345'),
       (10, 'Joris', NULL, 'Pietersen', 'piet', '12345'),
       (11, 'Rogier', NULL, 'Mullaart', 'admin', 'admin');


INSERT INTO `beheerder`
VALUES (11, 123456789);


INSERT INTO `klant`
VALUES (1, '1969-08-13', 'Justine de Gouwerhof', 6, '2011GP', 'Haarlem',
        '163647861', 'rogier.mullaart@gmail.com', '0647185165'),
       (2, '1973-06-16', 'Dam', 1, '1011ZH', 'Amsterdam', '163647895',
        'joop.jansen@knp.nl', '0647176156'),
       (3, '1950-09-10', 'Rokin', 1, '1001AA', 'Amsterdam', '156677882',
        'harry.kreeft@lumc.nl', '0647186543'),
       (4, '1945-01-10', 'Kleine houtstraat', 1, '2010AP', 'Haarlem',
        '122365477', 'harry.kreeft@lumc.nl', '0647186544'),
       (5, '1960-10-13', 'Rokin', 2, '2010AP', 'Groningen', '123564564',
        'harry.kreeft@lumc.nl', '0647186543'),
       (6, '1969-08-13', 'Justine de Gouwerhof', 3, '2010AP', 'Leiden',
        '534543533', 'harry.kreeft@lumc.nl', '0647186543'),
       (7, '1969-08-13', 'Kleine houtstraat', 4, '2010AP', 'Den Haag',
        '354353453', 'harry.kreeft@lumc.nl', '0647186543'),
       (8, '1969-08-13', 'Rokin', 5, '2010AP', 'Haarlem', '325324234',
        'harry.kreeft@lumc.nl', '0647186543'),
       (9, '1969-08-13', 'Dam', 6, '2010AP', 'Leiden', '234224323',
        'harry.kreeft@lumc.nl', '0647186543'),
       (10, '1954-08-13', 'Kleine houtstraat', 7, '2010AP', 'Den Haag',
        '234232444', 'harry.kreeft@lumc.nl', '0647186543');


INSERT INTO `bankrekening`
VALUES ('1234567891', 250.50, 1),
       ('9876543211', 350.00, 2);


INSERT INTO `asset`
VALUES (1, 'Bitcoin', 'BTC'),
       (2, 'Etherium', 'ETH'),
       (3, 'Dodgecoin', 'DGC'),
       (4, 'Euro', 'EUR');



INSERT INTO `portefeuille`
VALUES (1, 1),
       (2, 2);


INSERT INTO `portefeuille_regel`
VALUES (1, 1, 25.000000),
       (2, 2, 25.000000);



INSERT INTO `transactie`
VALUES (1, '2022-05-19 01:14:07.00', 5.00, '1234567891',  '9876543211', 300,  2,  2,
        10, 1, 1 ),
       (2, '2022-05-19 01:14:07.00', 6.00, '9876543211', '1234567891', 300, 1, 1,
        20, 2, 2);


INSERT INTO `koers`
VALUES (1, 2, 30000.000000, '2022-05-19 01:14:07.00'),
       (1, 3, 1500.000000, '2022-05-19 01:14:07.00'),
       (1, 4, 250.000000, '2022-05-19 01:14:07.00');

/*SET FOREIGN_KEY_CHECKS=1;*/






