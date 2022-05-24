CREATE SCHEMA  `cryptus`;
USE `cryptus`;
CREATE TABLE if not exists `user`
(
    `userId`         INT         NOT NULL AUTO_INCREMENT,
    `voornaam`       VARCHAR(45) NOT NULL,
    `tussenvoegsel`  VARCHAR(10) NULL,
    `achternaam`     VARCHAR(45) NOT NULL,
    `gebruikersnaam` VARCHAR(45) NOT NULL,
    `wachtwoord`     VARCHAR(100) NOT NULL,
    `salt`           VARCHAR(45) NOT NULL,
    PRIMARY KEY (`userId`)
);
CREATE TABLE if not exists `klant`
(
    `userId`        INT         NOT NULL,
    `geboortedatum` DATE        NOT NULL,
    `straat`        VARCHAR(45) NOT NULL,
    `huisnummer`    INT         NOT NULL,
    `postcode`      VARCHAR(10) NOT NULL,
    `woonplaats`    VARCHAR(45) NOT NULL,
    `bsn`           VARCHAR(45) NOT NULL,
    `emailadres`    VARCHAR(45) NOT NULL,
    `telefoon`      VARCHAR(10) NOT NULL,
    PRIMARY KEY (`userId`),
    CONSTRAINT `verzinzelf`
        FOREIGN KEY (`userId`)
            REFERENCES `user` (`userId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION);
CREATE TABLE if not exists `beheerder`
(
    `userId`           INT NOT NULL,
    `personeelsnummer` INT NOT NULL,
    PRIMARY KEY (`userId`),
    CONSTRAINT `verzinzelf1`
        FOREIGN KEY (`userId`)
            REFERENCES `user` (`userId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
CREATE TABLE if not exists `bankrekening`
(
    `iban`   VARCHAR(45)    NOT NULL,
    `saldo`  DECIMAL(16, 2) NOT NULL,
    `userId` INT            NOT NULL,
    PRIMARY KEY (`iban`),
    CONSTRAINT `verzinzelf2`
        FOREIGN KEY (`userId`)
            REFERENCES `user` (`userId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE if not exists `Asset`
(
    `assetId`   INT         NOT NULL AUTO_INCREMENT,
    `naam`      VARCHAR(45) NOT NULL,
    `afkorting` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`assetId`)
);

CREATE TABLE if not exists `portefeuille`
(
    `portefeuilleID` INT NOT NULL AUTO_INCREMENT,
    `userId`         INT NOT NULL,
    PRIMARY KEY (`portefeuilleID`),
    CONSTRAINT `user_portefeuille`
        FOREIGN KEY (`userId`)
            REFERENCES `user` (`userId`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE if not exists `portefeuille_regel`
(
    `portefeuilleID` INT            NOT NULL,
    `assetId`        INT            NOT NULL,
    `saldo`          DECIMAL(16, 6) NOT NULL,
    PRIMARY KEY (`portefeuilleID`, `assetId`),
    CONSTRAINT `verzinzelf4`
        FOREIGN KEY (`portefeuilleID`)
            REFERENCES `portefeuille` (`portefeuilleID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `verzinzelf5`
        FOREIGN KEY (`assetId`)
            REFERENCES `Asset` (`assetId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE if not exists `transactie`
(
    `transactieId`          INT            NOT NULL AUTO_INCREMENT,
    `datumtijd`             TIMESTAMP(2)   NOT NULL,
    `kosten`                DECIMAL(16, 2) NOT NULL,
    `percentage`            INT            NOT NULL,
    `creditiban`            VARCHAR(45)    NOT NULL,
    `debitiban`             VARCHAR(45)    NOT NULL,
    `euroammount`           DECIMAL(16, 2) NOT NULL,
    `debitportefeuilleID`   INT            NOT NULL,
    `creditportefeuilleID1` INT            NOT NULL,
    `AssetId`               INT            NOT NULL,
    `assetammount`          DECIMAL(16, 6) NOT NULL,
    PRIMARY KEY (`transactieId`),
    CONSTRAINT `verzinzelf3`
        FOREIGN KEY (`debitportefeuilleID`, `AssetId`)
            REFERENCES `portefeuille_regel` (`portefeuilleID`, `assetId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
 /*   CONSTRAINT `verzinzelf7`
        FOREIGN KEY (`creditportefeuilleID1`)
            REFERENCES `portefeuille_regel` (`portefeuilleID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,*/
    CONSTRAINT `verzinzelf9`
        FOREIGN KEY (`creditiban`)
            REFERENCES `bankrekening` (`iban`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `verzinzelf10`
        FOREIGN KEY (`debitiban`)
            REFERENCES `bankrekening` (`iban`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE if not exists `koers`
(
    `asseta`      INT            NOT NULL,
    `assetb`      INT            NOT NULL,
    `wisselkoers` DECIMAL(16, 6) NOT NULL,
    `timestamp`   TIMESTAMP(2)   NOT NULL,
    PRIMARY KEY (`asseta`, `assetb`, `timestamp`),
    CONSTRAINT `verzinzelf6`
        FOREIGN KEY (`asseta`)
            REFERENCES `Asset` (`assetId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `verzinzelf8`
        FOREIGN KEY (`assetb`)
            REFERENCES `Asset` (`assetId`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

 -- CREATE USER 'userCryptus'@'localhost' IDENTIFIED BY '12345';
-- GRANT ALL PRIVILEGES ON cryptus.* TO 'userCryptus'@'localhost';