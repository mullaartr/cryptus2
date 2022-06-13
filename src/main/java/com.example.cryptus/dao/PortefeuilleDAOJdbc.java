package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Portefeuille;
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

import java.sql.*;
import java.util.*;

@Component
public class PortefeuilleDAOJdbc  implements PortefeuilleDAO{

    private JdbcTemplate jdbcTemplate;

    private Logger logger =  LogManager.getLogger(PortefeuilleDAOJdbc.class);

    @Autowired
    public PortefeuilleDAOJdbc(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("new PortefeuilleDAOJdbc");
    }



    RowMapper<Portefeuille> rowMapper = (rs, rownum) ->  {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setOwner(null);
        portefeuille.setPortefeuilleId(rs.getInt("portefeuilleId"));
        return portefeuille;
    };

    ResultSetExtractor<List<Portefeuille>> portefeuilleResultExtractor = rs-> {
        List<Portefeuille> portefeuilles = new ArrayList<>();
        while (rs.next()){
            Portefeuille portefeuille = new Portefeuille();
            portefeuille.setOwner(null);
            portefeuille.setPortefeuilleId(rs.getInt("portefeuilleId"));
            portefeuilles.add(portefeuille);
        }
        return portefeuilles;
    };

    ResultSetExtractor <List<Asset>> assetResultExtractor = rs -> {
        List<Asset> assetLijst = new ArrayList<>();
        while (rs.next()){
            Asset asset = new Asset();
            asset.setAssetId(rs.getInt("assetId"));
            asset.setAssetNaam(rs.getString("naam"));
            asset.setAssetAfkorting(rs.getString("afkorting"));
            asset.setSaldo (rs.getDouble("saldo"));
            assetLijst.add(asset);
        }
        return assetLijst;
    };


    @Override
    public Optional<Portefeuille> findPortefeuilleById(int id) {
        String Sql = "select * from portefeuille p where p.portefeuilleId = ?";
        Portefeuille portefeuille = null;
        try{
            portefeuille = jdbcTemplate.queryForObject(Sql, rowMapper, id);
            portefeuille.setAssetLijst(findAssetsByPortefeuille(id).orElse(null));
        }catch (DataAccessException exception){
            logger.info("portefeuille was not found");
        }
        return Optional.ofNullable(portefeuille);
    }

    private Optional <List<Asset>> findAssetsByPortefeuille(int id){
        String sql = "select * from portefeuille_Regel po join asset a on a.assetId = po.assetId  where po.portefeuilleId = ?";
        List<Asset> assetLijst = null;
        try {
            assetLijst = jdbcTemplate.query(sql, assetResultExtractor, id);
        }catch (DataAccessException exception){
            logger.info("no assets where found found");
        }
        return Optional.ofNullable(assetLijst);
    }

    @Override
    public Optional<List<Portefeuille>> findPortefeuilles(){
        String sql = "select * from portefeuille";
        String sql2 = "select * from portefeuille_regel po join asset a on a.assetId = po.assetId where portefeuilleId = ?";
        List<Portefeuille> portefeuilles = null;
        try {
            portefeuilles = jdbcTemplate.query(sql, portefeuilleResultExtractor);
            for (int i = 0; i < portefeuilles.size(); i++) {
                List<Asset> assets = jdbcTemplate.query(sql2, assetResultExtractor, portefeuilles.get(i).getPortefeuilleId());
                portefeuilles.get(i).setAssetLijst(assets);
            }
        } catch (DataAccessException exception){
            logger.info("portefeuille was not found");
        }

        return Optional.of(portefeuilles);
    }


    private PreparedStatement insertPortefeuilleStatement(Portefeuille portefeuille, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into portefeuille (UserId) values (?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, portefeuille.getOwner().getUserId());
        return ps;
    }

    private PreparedStatement insertPortefeuilleRegelStatement(Portefeuille portefeuille, Asset asset, Connection connection)throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO portefeuille_regel(portefeuilleID, SALDO, ASSETId) VALUES (?,?,?)");
        ps.setInt(1, portefeuille.getPortefeuilleId());
        ps.setDouble(2, asset.getSaldo());
        ps.setInt(3, asset.getAssetId());
        return ps;
    }
    @Override
    public void store(Portefeuille portefeuille) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertPortefeuilleStatement(portefeuille, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        portefeuille.setPortefeuilleId(newKey);
        for (int i = 0; i < portefeuille.getAssetLijst().size(); i++) {
            storePortefeuilleRegel(portefeuille, portefeuille.getAssetLijst().get(i));
        }

    }

    public void storePortefeuilleRegel(Portefeuille portefeuille, Asset asset){
        jdbcTemplate.update(con -> insertPortefeuilleRegelStatement(portefeuille, asset, con));
    }

    public Optional<Portefeuille> findPortefeuilleOf(int userId) {
        int portefeuilleId = jdbcTemplate.queryForObject("select " +
                "portefeuilleId from portefeuille where userId = ?", Integer.class, userId);
        return findPortefeuilleById(portefeuilleId);
    }


    @Override
    public void update(Portefeuille portefeuille, Asset asset) {
        String sql = "Update portefeuille_regel  SET  saldo = ? where portefeuilleId = ? and assetId = ?";
        int update = jdbcTemplate.update(sql, asset.getSaldo(), portefeuille.getPortefeuilleId(), asset.getAssetId());
        if (update == 1) {
            logger.info("portefeuille updated" + portefeuille.getPortefeuilleId());
        }
    }

        @Override
        public void delete (int id){
        String sql = "DELETE FROM portefeuille WHERE portefeuilleId = ?";
        String sql1 = "Delete from portefeuille_regel where portefeuilleID = ?";
            jdbcTemplate.update(sql, id);
            jdbcTemplate.update(sql1, id);
        }

    }

