package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Koers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class KoersDaoJdbc implements KoersDao {

     private JdbcTemplate jdbcTemplate;

    private Logger logger =  LogManager.getLogger(KoersDaoJdbc.class);

    @Autowired
    public KoersDaoJdbc(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("new KoersDaoJdbc");
    }

//    //todo PreparedStatement niet nodig?
//    private PreparedStatement insertKoersStatement(Koers koers, Connection connection) throws SQLException {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO  koers (assetId, assetnaam, " +
//                            "wisselkoersEuro, wisselkoersDollar, datumKoers) VALUES (?, ?, ?, ?, ?) ",
//                    Statement.RETURN_GENERATED_KEYS);
//            ps.setInt(1, koers.getAsset().getAssetId());
//            ps.setString(2, koers.getAsset().getAssetNaam());
//            ps.setDouble(3, koers.getKoersInEuro());
//            ps.setDouble(4, koers.getKoersInDollars());
//            ps.setObject(5, koers.getKoersDatum()); //hoop dat dit werkt
//            return ps;
//        } catch (SQLException exception) {
//        logger.info("SQL Error");
//        }
//        return null;
//    }

    RowMapper<Koers> koersRowMapper = (rs, rownum) -> {
        Koers koers = new Koers();
        koers.setAsset(null);
        koers.setKoersInEuro(rs.getDouble("wisselkoersEuro"));
        koers.setKoersInDollars((rs.getDouble("wisselKoersDollar")));
        koers.setKoersDatum(rs.getObject("datumKoers", LocalDateTime.class)); //hoop dat dit werkt
        return koers;
    };

    //todo hoe de bidirectionaliteit tussen Koers en Asset aanpakken?
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

    @Override
    public Optional<Koers> findMostRecentKoersByAssetNaam(String naam) {
        String sql = "select * from koers k where k.assetnaam = ? order by datumKoers Desc limit 1";
        Koers koers = null;
        try {
            koers = jdbcTemplate.queryForObject(sql, koersRowMapper, naam);
            koers.setAsset(findAssetByAssetNaam(naam).orElse(null));
        } catch (DataAccessException exception){
            logger.info("Koers was not found");
        }
        return Optional.of(koers);
    }

    @Override
    public List<Koers> findAllKoersenByAssetNaam(String naam) {
        String sql = "select * from koers k where k.assetnaam = ?";
        return jdbcTemplate.query(sql, koersRowMapper, naam);
    }

    //todo hier zit een bug in: als niet alle koersen even vaak geüpdate worden,
    // is er van de twintig meest recente koersen één dubbel, en ontbreekt er één,
    // daarom wellicht beter om in de Service laag door alle assets te loopen,
    // en voor elke asset findMostRecentKoersByAssetNaam(String naam) aan te roepen
    @Override
    public List<Koers> findMostRecentKoersen() {
        String sql = "select * from koers k order by datumKoers Desc limit 20";
        return jdbcTemplate.query(sql, koersRowMapper);
    }

    //todo waarschijnlijk moet deze methode verwijderd; hij heeft geen nut, en zou na verloop van tijd een enorme bak info teruggeven
    @Override
    public List<Koers> findAllKoersen() {
        String sql  = "select * from koers";
        return jdbcTemplate.query(sql, koersRowMapper);
    }


    private PreparedStatement insertStatementKoers(Koers koers, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO  koers (assetId, assetnaam, " +
                "wisselkoersEuro, wisselkoersDollar, datumKoers) VALUES (?, ?, ?, ?, now()) ");
        ps.setInt(1, koers.getAsset().getAssetId());
        ps.setString(2, koers.getAsset().getAssetNaam());
        ps.setDouble(3, koers.getKoersInEuro());
        ps.setDouble(4, koers.getKoersInDollars());
        return ps;
    }

    @Override
    public void store(Koers koers) {
        var store = jdbcTemplate.update(con -> insertStatementKoers(koers, con));
    }

    //todo is deze methode wel nodig?
    @Override
    public void update(Koers koers) {
        String sql = "update koers set assetnaam = ?, wisselkoersEuro = ?,  wisselkoersDollar = ? " +
                "where assetId = ? and datumKoers = ?)";
        var update = jdbcTemplate.update(sql);
    }

    @Override
    public void delete(String naam, LocalDateTime dateTime) {
        String sql ="delete from koers where assetnaam = ? and datumKoers = ?";
        jdbcTemplate.update(sql, naam, dateTime);
    }

}
