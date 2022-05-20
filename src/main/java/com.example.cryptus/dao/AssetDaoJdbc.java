package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Portefeuille;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;
import java.util.Optional;

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
                jdbcTemplate.query("select * from Cryptus.Asset where AssetId = ?", new AssetRowMapper(), id);
        if(assets.size() != 1) {
            return Optional.empty();
        } else {
            return Optional.of(assets.get(0));
        }
    }

    //todo klopt mysql join?
    @Override
    public Optional<Asset> findAssetByPortefeuille(Portefeuille portefeuille) {
        List<Asset> assets =
                jdbcTemplate.query("select assetId, naam, afkorting, portefeuilleID " +
                        "from Asset " +
                        "left join portefeuille_regel on" +
                        "portefeuille_regel.assetId = asset.assetId",
                        new AssetRowMapper(), portefeuille);
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
        String sql = "UPDATE cryptus.asset SET naam = ?, afkorting = ?, koers = ? WHERE assetId = ?";
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
            int id = resultSet.getInt("cryptus.asset.AssetId");
            String assetNaam = resultSet.getString("cryptus.naam");
            String assetAfkorting = resultSet.getString("cryptus.afkorting");
            double koersEuro = resultSet.getDouble("cryptus.koers.wisselkoers");
            Asset asset = new Asset (id,assetNaam, assetAfkorting, koersEuro, null, 0.0);//moet portefeuille wel null zijn?
            return asset;
        }
    }


}
