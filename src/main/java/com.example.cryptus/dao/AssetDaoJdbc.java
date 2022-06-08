package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
//import com.example.cryptus.model.Koers;
import com.example.cryptus.model.Portefeuille;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Component
public class AssetDaoJdbc implements AssetDao {

    private final Logger logger = LogManager.getLogger(AssetDaoJdbc.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AssetDaoJdbc (JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New AssetDaoJdbc");
    }

    //hoe een koers opslaan?

    //todo wat te doen met koers? wel een attribuut van Asset, maar staat niet in database asset tabel
    //onderstaand SQL statement mogelijke nog niet correct - database nog niet draaiend
    private PreparedStatement insertAssetStatement(Asset asset, Connection connection) throws SQLException {
        PreparedStatement ps =  connection.prepareStatement(
                "insert into Cryptus.Asset (naam, afkorting) values (?,?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, asset.getAssetNaam());
        ps.setString(2, asset.getAssetAfkorting());
        return ps;
    }

    @Override
    public void store(Asset asset) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertAssetStatement(asset, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        asset.setAssetId(newKey);
    }

    //onderstaand SQL statement mogelijke nog niet correct - database nog niet draaiend
    @Override
    public Optional<Asset> findAssetById(int id) {
        List<Asset> assets =
                jdbcTemplate.query("select * from asset join " +
                        "koers on asset.assetId = koers.assetb where assetId " +
                        " = ?", new AssetRowMapper(), id);
        if(assets.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(assets.get(0));
        }
    }

    public Optional<Asset> findAssetByTransactionId( int id ) {
        List<Asset> assets =
                jdbcTemplate.query("select * from asset join " +
                        "koers join transactie t on asset.assetId = koers" +
                        ".assetb and asset.assetId = t.AssetId where t" +
                        ".transactieId " +
                        " = ?", new AssetRowMapper(), id);
        if(assets.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(assets.get(0));
        }

    }

 //   todo klopt mysql join?
    @Override
    public Optional<Asset> findAssetByPortefeuille(Portefeuille portefeuille) {
        List<Asset> assets =
                jdbcTemplate.query("SELECT assetId, naam, afkorting, " +
                                "portefeuilleID, wisselkoers " +
                        "FROM Asset A JOIN portefeuille_regel on portefeuille_regel.assetId = A.assetId " +
                                "join koers K on A.assetid = K.assetb " +
                                "where portefeuilleID = ?"
                        ,
                        new AssetRowMapper(), portefeuille.getPortefeuilleId());
        if(assets.size() != 1) {
            return Optional.empty();
        } else return Optional.of(assets.get(0));
    }

    //todo wat te doen met koers? wel een attribuut van Asset, maar staat niet in database asset tabel
    //onderstaand SQL statement mogelijke nog niet correct - database nog niet draaiend
 /*private PreparedStatement updateAssetStatement(Asset asset, int id, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "update cryptus.asset SET assetNaam = ?, assetAfkorting = ?, koers = ? WHERE assetId = ?");
        ps.setString(1, asset.getAssetNaam());
        ps.setString(2, asset.getAssetAfkorting());
        ps.setDouble(3, asset.getKoersEuro());
        return ps;
    }*/



    //update koers: set meest recente koers met een join
    @Override
    public void update(Asset asset, int id) {
        String sql = "UPDATE asset A SET A.naam = ?, A.afkorting = ?, " +
                "cryptus.koers.wisselkoers" +
                " = ?" +
 //       " JOIN koers K ON A.assetid = K.asseta" +
                " WHERE assetId = ?";
        int update = jdbcTemplate.update(sql, asset.getAssetNaam(), asset.getAssetAfkorting(), asset.getKoersEuro());
        if(update==1) {
            logger.info("Asset updated" + asset.getAssetId());
        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM asset WHERE assetId = ?");
    }

    //todo wat te doen met koers? wel een attribuut van Asset, maar staat niet in database asset tabel
    private static class AssetRowMapper implements RowMapper<Asset> {

        @Override
        public Asset mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int id = resultSet.getInt("assetId");
            String assetNaam = resultSet.getString("naam");
            String assetAfkorting = resultSet.getString("afkorting");
            double koersEuro = resultSet.getDouble("wisselkoers");
            Asset asset = new Asset (id,assetNaam, assetAfkorting, koersEuro, 0.0);//moet portefeuille wel null zijn?
            return asset;
        }
    }


}
