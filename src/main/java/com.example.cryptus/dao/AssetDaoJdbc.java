package com.example.cryptus.dao;
import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Portefeuille;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    private PreparedStatement insertAssetStatement(Asset asset, Connection connection) throws SQLException {
        PreparedStatement ps =  connection.prepareStatement(
                "insert into Cryptus.Asset (naam, afkorting) values (?,?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, asset.getAssetNaam());
        ps.setString(2, asset.getAssetAfkorting());
        return ps;
    }

    @Override
    public Optional<Asset> findAssetByAssetNaam(String naam) {
        String sql = "select * from asset a where a.naam = ?";
        Asset asset = null;
        try {
            asset = jdbcTemplate.queryForObject(sql, assetRowMapper, naam);
        } catch (DataAccessException exception) {
            logger.info("Asset not found");
        }
        return Optional.of(asset);
    }


    RowMapper<Asset> assetRowMapper = (rs, rownum) -> {
        Asset asset = new Asset();
        asset.setAssetId(rs.getInt("assetId"));
        asset.setAssetNaam(rs.getString("naam"));
        asset.setAssetAfkorting(rs.getString("afkorting"));
        //asset.setSaldo(rs.getDouble("saldo")); //bestaat nog niet in DB
        //asset.setKoers(rs.get); // wat hier te doen met Koers?
        return asset;
    };

    @Override
    public List<Asset> findAllAssets() {
        return null;
    }

    @Override
    public void store(Asset asset) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertAssetStatement(asset, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        asset.setAssetId(newKey);
    }

    @Override
    public void delete(String naam) {

    }

//    @Override
//    public Optional<Asset> findAssetById(int id) {
//        List<Asset> assets =
//                jdbcTemplate.query("select * from asset join " +
//                        "koers on asset.assetId = koers.assetId where assetId " +
//                        " = ?", new AssetRowMapper(), id);
//        if(assets.size() == 0) {
//            return Optional.empty();
//        } else {
//            return Optional.of(assets.get(0));
//        }
//    }


//    public void delete(String naam) {
//        jdbcTemplate.update("DELETE FROM asset WHERE assetId = ?");
//    }

//    //onderstaand SQL statement mogelijke nog niet correct - database nog niet draaiend
//    @Override
//    public Optional<Asset> findAssetById(int id) {
//        List<Asset> assets =
//                jdbcTemplate.query("select * from asset join " +
//                        "koers on asset.assetId = koers.assetb where assetId " +
//                        " = ?", new AssetRowMapper(), id);
//        if(assets.size() == 0) {
//            return Optional.empty();
//        } else {
//            return Optional.of(assets.get(0));
//        }
//    }
    public Optional<Asset> findAssetByTransactionId( int id ) {
        List<Asset> assets =
                jdbcTemplate.query("select * from asset join " +
                        "koers join transactie t on asset.assetId = koers" +
                        ".assetId and asset.assetId = t.debitassetId where t" +
                        ".transactieId " +
                        " = ?", new AssetRowMapper(),id);
        if(assets.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(assets.get(0));
        }
    }

// //   todo klopt mysql join?
//    @Override
//    public Optional<Asset> findAssetByPortefeuille(Portefeuille portefeuille) {
//        List<Asset> assets =
//                jdbcTemplate.query("SELECT assetId, naam, afkorting, " +
//                                "portefeuilleID, wisselkoersEuro " +
//                        "FROM Asset A JOIN portefeuille_regel on portefeuille_regel.assetId = A.assetId " +
//                                "join koers K on A.assetid = K.assetId " +
//                                "where portefeuilleID = ?"
//                        ,
//                        new AssetRowMapper(), portefeuille.getPortefeuilleId());
//        if(assets.size() != 1) {
//            return Optional.empty();
//        } else return Optional.of(assets.get(0));
//    }

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

    //todo wat te doen met koers? wel een attribuut van Asset, maar staat niet in database asset tabel
// //   portefeuilleId meegeven ipv Portefeuille
//    @Override
//    public Optional<Asset> findAssetByPortefeuille(Portefeuille portefeuille) {
//        List<Asset> assets =
//                jdbcTemplate.query("SELECT assetId, naam, afkorting, " +
//                                "portefeuilleID, wisselkoers " +
//                        "FROM Asset A JOIN portefeuille_regel on portefeuille_regel.assetId = A.assetId " +
//                                "join koers K on A.assetid = K.assetb " +
//                                "where portefeuilleID = ?"
//                        ,
//                        new AssetRowMapper(), portefeuille.getPortefeuilleId());
//        if(assets.size() != 1) {
//            return Optional.empty();
//        } else return Optional.of(assets.get(0));
//    }

    private static class AssetRowMapper implements RowMapper<Asset> {

        @Override
        public Asset mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int id = resultSet.getInt("assetId");
            String assetNaam = resultSet.getString("naam");
            String assetAfkorting = resultSet.getString("afkorting");
            //Koers koersEuro = resultSet.getDouble("wisselkoers");
            Asset asset = new Asset (id,assetNaam, assetAfkorting, null);//moet portefeuille wel null zijn?
            return asset;
        }
    }


}
