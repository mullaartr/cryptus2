USE `cryptus`;
/*SET FOREIGN_KEY_CHECKS=0;*/

INSERT INTO `bankinstelling`
VALUES ('percentage', '15');

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

INSERT INTO `account`
VALUES ('admin', 1,1);

INSERT INTO `beheerder`
VALUES (11, 123456789);

INSERT INTO `klant`
VALUES (1, '1969-08-13', 'Justine de Gouwerhof', 6, '2011GP', 'Haarlem',
        '163647861', '0647185165'),
       (2, '1973-06-16', 'Dam', 1, '1011ZH', 'Amsterdam', '163647895'
        , '0647176156'),
       (3, '1950-09-10', 'Rokin', 1, '1001AA', 'Amsterdam', '156677882', '0647186543'),
       (4, '1945-01-10', 'Kleine houtstraat', 1, '2010AP', 'Haarlem',
        '122365477', '0647186544'),
       (5, '1960-10-13', 'Rokin', 2, '2010AP', 'Groningen', '123564564'
        , '0647186543'),
       (6, '1969-08-13', 'Justine de Gouwerhof', 3, '2010AP', 'Leiden',
        '534543533', '0647186543'),
       (7, '1969-08-13', 'Kleine houtstraat', 4, '2010AP', 'Den Haag',
        '354353453', '0647186543'),
       (8, '1969-08-13', 'Rokin', 5, '2010AP', 'Haarlem', '325324234'
        , '0647186543'),
       (9, '1969-08-13', 'Dam', 6, '2010AP', 'Leiden', '234224323'
        , '0647186543'),
       (10, '1954-08-13', 'Kleine houtstraat', 7, '2010AP', 'Den Haag',
        '234232444', '0647186543');

INSERT INTO `bankrekening`
VALUES ('1234567891', 1000000.00, 1),
       ('9876543211', 1000000.00, 2);

INSERT INTO `asset`
values
(1, 'Bitcoin', 'BTC'),
       (2, 'Ethereum', 'ETH'),
       (3, 'Tether', 'USDT'),
       (4, 'USD-Coin', 'USDC'),
       (5, 'BinanceCoin', 'BNB'),
       (6, 'Cardano', 'ADA'),
       (7, 'Ripple', 'XRP'),
       (8, 'Binance-USD', 'BUSD'),
       (9, 'Solana', 'SOL'),
       (10, 'Dogecoin', 'Doge'),
       (11, 'Polkadot', 'DOT'),
       (12, 'Wrapped-Bitcoin', 'WBTC'),
       (13, 'TRON', 'TRX'),
       (14, 'Staked-Ether', 'STETH'),
       (15, 'Dai', 'DAI'),
       (16, 'Avalanche-2', 'AVAX'),
       (17, 'Shiba-Inu', 'SHIB'),
       (18, 'Leo', 'Leo'),
       (19, 'Cronos', 'CRO'),
       (20, 'Litecoin', 'LTC'),
        (21, 'Euro', 'Eur'),
        (22, 'Dollar', 'USD');

INSERT INTO `portefeuille`
VALUES (1, 1),
       (2, 2);

INSERT INTO `portefeuille_regel`
VALUES (1, 1, 1, 25.000000),
       (2, 1, 2, 25.000000),
       (3, 2, 1, 25.000000),
       (4, 2, 2, 25.000000);

INSERT INTO `transactie`
VALUES (1, '2022-05-19 01:14:07.00', 5.00, '1234567891', '9876543211', 300, 2,
        2,
        10, 1, 1),
       (2, '2022-05-19 01:14:07.00', 6.00, '9876543211', '1234567891', 300, 1,
        1,
        20, 2, 2);

INSERT INTO `koers`
VALUES (1, '2022-05-19 13:14:07', 'bitcoin', 30000.000000, 29000.000000),
       (2, '2022-05-19 14:14:07', 'Ethereum', 29000.000000, 28000.000000),
       (3, '2022-05-19 15:14:07', 'Tether', 30000.000000, 27000.000000),
       (4, '2022-05-19 13:14:07', 'USD-Coin', 1687.000000, 1587.000000),
       (5, '2022-05-19 14:14:07', 'BinanceCoin', 1687.000000, 1587.000000),
       (6, '2022-05-19 15:14:07', 'Cardano', 1687.000000, 1587.000000);

/*SET FOREIGN_KEY_CHECKS=1;*/






