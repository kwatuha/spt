package org.spt.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.spt.model.Asset;
import org.spt.model.AssetClearance;
import org.spt.model.AssetContact;
import org.spt.model.AssetLog;
import org.spt.model.AssetLoss;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class JdbcAssetDao implements AssetDao {

    private final static String SQL_SELECT_ASSET = "SELECT id,model,serial_number,make,ownership,comments,date_created FROM asset ";
    private final static String SQL_UPDATE_ASSET = "UPDATE asset set model = :model, serial_number = :serialNumber,make = :make " +
            " ,ownership = :ownership " +
            " ,comments = :comments " +
            " ,date_created = :dateCreated " +
            "WHERE id = :id";
    private final static String SQL_DELETE_ASSET = "DELETE FROM asset WHERE id = ?";
    private final static String SQL_ASSET_SEARCH = "WHERE serial_number LIKE :criteria ";

    private final static String SQL_SELECT_ASSET_CLEARANCE = "SELECT id,asset_id,comments,status,recovery_date,date_created,created_by FROM asset_clearance ";
    private final static String SQL_UPDATE_ASSET_CLEARANCE = "UPDATE asset_clearance set asset_id = :asset, status = :status,recovery_date = :recoveryDate " +
            " ,comments = :comments " +
            " ,date_created = :dateCreated " +
            " ,created_by = :createdBy " +
            "WHERE id = :id";
    private final static String SQL_DELETE_ASSET_CLEARANCE = "DELETE FROM asset_clearance WHERE id = ?";
    private final static String SQL_ASSET_CLEARANCE_SEARCH = " WHERE status LIKE :criteria ";

    private final static String SQL_SELECT_ASSET_LOG = "SELECT id,asset_id,entry_time,exit_time,date_created,created_by FROM asset_log ";
    private final static String SQL_UPDATE_ASSET_LOG = "UPDATE asset_log set asset_id = :asset, exit_time = :exitTime,entry_time = :entryTime " +
            " ,date_created = :dateCreated " +
            " ,created_by = :createdBy " +
            "WHERE id = :id";
    private final static String SQL_DELETE_ASSET_LOG = "DELETE FROM asset_log WHERE id = ?";
    private final static String SQL_ASSET_LOG_SEARCH = " WHERE asset_id LIKE :criteria ";

    private final static String SQL_SELECT_ASSET_LOSS = "SELECT id,asset_id,comments,date_lost,date_reported,date_created,created_by FROM asset_loss ";
    private final static String SQL_UPDATE_ASSET_LOSS = "UPDATE asset_loss set asset_id = :asset, date_lost = :dateLost,date_reported = :dateReported " +
            " ,comments = :comments " +
            " ,date_created = :dateCreated " +
            " ,created_by = :createdBy " +
            "WHERE id = :id";
    private final static String SQL_DELETE_ASSET_LOSS = "DELETE FROM asset_loss WHERE id = ?";
    private final static String SQL_ASSET_LOSS_SEARCH = "WHERE asset_id LIKE :criteria ";

    private final static String SQL_SELECT_ASSET_CONTACT = "SELECT id,asset_id,contact_id,status,comments,date_assigned,date_returned,date_created,created_by FROM asset_contact ";
    private final static String SQL_UPDATE_ASSET_CONTACT = "UPDATE asset_contact set asset_id = :asset, date_returned = :dateReturned,date_assigned = :dateAssigned " +
            " ,contact_id = :contact " +
            " ,status = :status " +
            " ,comments = :comments " +
            " ,date_created = :dateCreated " +
            " ,created_by = :createdBy " +
            "WHERE id = :id";
    private final static String SQL_DELETE_ASSET_CONTACT = "DELETE FROM asset_contact WHERE id = ?";
    private final static String SQL_ASSET_CONTACT_SEARCH = " WHERE asset_id LIKE :criteria ";

    private SimpleJdbcTemplate simpleJdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Override
    @Value("id")
    public Asset findAssetById(Integer id) {
        Asset asset;
		try {
			asset = this.simpleJdbcTemplate.queryForObject(
					SQL_SELECT_ASSET + "WHERE id=?",
					BeanPropertyRowMapper.newInstance(Asset.class),
					id);
		}
		catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Asset.class, id);
		}

        return asset;
    }

    @Override
    @Value("criteria")
    public List<Asset> searchForAsset(String criteria){
        Map<String,String> params = new HashMap<String,String>();
        params.put("criteria","%" + criteria + "%");
        List<Asset> assets = null;
         try{
            assets = this.simpleJdbcTemplate.query(
                SQL_ASSET_SEARCH, BeanPropertyRowMapper.newInstance(Asset.class), params);

         } catch(Exception e){
                 e.printStackTrace();
         }
       
       
       
       
                return assets ;
    }

    @Override
    public List<Asset> listAllAssets(){
        List<Asset> assetList = this.simpleJdbcTemplate.query(
                SQL_SELECT_ASSET + "ORDER BY id DESC",
                BeanPropertyRowMapper.newInstance(Asset.class));

        return assetList;
    }

    @Override
    @Value("asset")
    public Asset updateAsset(Asset asset){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id", asset.getId());
        params.put("model", asset.getModel());
        params.put("make", asset.getMake());
        params.put("serialNumbe", asset.getSerialNumber());
        params.put("ownership", asset.getOwnership());
        params.put("comments", asset.getComments());
        params.put("createdBy", asset.getCreatedBy());
        params.put("dateCreated", asset.getDateCreated());
        this.simpleJdbcTemplate.update(SQL_UPDATE_ASSET, params);

        return asset;
    }

    @Override
    @Transactional
    @Value("id")
    public void deleteAsset(Integer id){
        this.simpleJdbcTemplate.update(SQL_DELETE_ASSET, id);
    }

    @Override
    public Asset addAsset(Asset asset){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("model", asset.getModel());
        params.put("make", asset.getMake());
        params.put("serialNumber", asset.getSerialNumber());
        params.put("ownership", asset.getOwnership());
        params.put("comments", asset.getComments());
        params.put("createdBy", asset.getCreatedBy());
        params.put("dateCreated", asset.getDateCreated());

        Number newId = simpleInsert.executeAndReturnKey(params);
        asset.setId(newId.intValue());
        return asset;
    }

    // Asset clearance

    @Override
    public AssetClearance findAssetClearanceById(Integer id){
        AssetClearance assetClearance;
        try {
            assetClearance = this.simpleJdbcTemplate.queryForObject(
                    SQL_SELECT_ASSET_CLEARANCE + "WHERE id=?",
                    BeanPropertyRowMapper.newInstance(AssetClearance.class),
                    id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(AssetClearance.class, id);
        }

        return assetClearance;
    }

    @Override
    public List<AssetClearance> searchForAssetClearance(String criteria){
        Map<String,String> params = new HashMap<String,String>();
        params.put("criteria","%" + criteria + "%");

        List<AssetClearance> assetClearanceList = this.simpleJdbcTemplate.query(
                SQL_ASSET_CLEARANCE_SEARCH,
                BeanPropertyRowMapper.newInstance(AssetClearance.class), params);

        return assetClearanceList;
    }

    @Override
    public List<AssetClearance> listAllAssetClearance(){
        List<AssetClearance> assetClearanceList = this.simpleJdbcTemplate.query(
                SQL_SELECT_ASSET_CLEARANCE+ "ORDER BY id DESC",
                BeanPropertyRowMapper.newInstance(AssetClearance.class));

        return assetClearanceList;
    }

    @Override
    @Value("assetClearance")
    public AssetClearance updateAssetClearance(AssetClearance assetClearance){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id", assetClearance.getId());
        params.put("recoveryDate", assetClearance.getRecoveryDate());
        params.put("status", assetClearance.getStatus());
        params.put("comments", assetClearance.getComments());
        params.put("createdBy", assetClearance.getCreatedBy());
        params.put("dateCreated", assetClearance.getDateCreated());
        this.simpleJdbcTemplate.update(SQL_UPDATE_ASSET_CLEARANCE, params);

        return assetClearance;
    }

    @Override
    @Transactional
    @Value("id")
    public void deleteAssetClearance(Integer id){
        this.simpleJdbcTemplate.update(SQL_DELETE_ASSET_CLEARANCE, id);
    }

    @Override
    @Value("assetClearance")
    public AssetClearance addAssetClearance(AssetClearance assetClearance){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("recoveryDate", assetClearance.getRecoveryDate());
        params.put("status", assetClearance.getStatus());
        params.put("comments", assetClearance.getComments());
        params.put("createdBy", assetClearance.getCreatedBy());
        params.put("dateCreated", assetClearance.getDateCreated());

        Number newId = simpleInsert.executeAndReturnKey(params);
        assetClearance.setId(newId.intValue());
        return assetClearance;
    }

    // Asset Contact
    @Override
    @Value("id")
    public AssetContact findAssetContactById(Integer id){
        AssetContact assetContact;
        try {
            assetContact = this.simpleJdbcTemplate.queryForObject(
                    SQL_SELECT_ASSET_CONTACT + "WHERE id=?",
                    BeanPropertyRowMapper.newInstance(AssetContact.class),
                    id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(AssetContact.class, id);
        }

        return assetContact;

    }

    @Override
    @Value("criteria")
    public List<AssetContact> searchForAssetContact(String criteria){
        Map<String,String> params = new HashMap<String,String>();
        params.put("criteria","%" + criteria + "%");

        List<AssetContact> assetContactList = this.simpleJdbcTemplate.query(
                SQL_ASSET_CONTACT_SEARCH,
                BeanPropertyRowMapper.newInstance(AssetContact.class), params);

        return assetContactList;

    }

    @Override
    public List<AssetContact> listAllAssetContact(){
        List<AssetContact> assetContactList = this.simpleJdbcTemplate.query(
                SQL_SELECT_ASSET_CONTACT+ "ORDER BY id DESC",
                BeanPropertyRowMapper.newInstance(AssetContact.class));

        return assetContactList;
    }

    @Override
    @Value("assetContact")
    public AssetContact updateAssetContact(AssetContact assetContact){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id", assetContact.getId());
        params.put("dateAssigned", assetContact.getDateAssigned());
        params.put("dateReturned", assetContact.getDateReturned());
        params.put("asset", assetContact.getAsset());
        params.put("status", assetContact.getStatus());
        params.put("comments", assetContact.getComments());
        params.put("createdBy", assetContact.getCreatedBy());
        params.put("dateCreated", assetContact.getDateCreated());
        this.simpleJdbcTemplate.update(SQL_UPDATE_ASSET_CONTACT, params);

        return assetContact;
    }

    @Override
    @Transactional
    @Value("id")
    public void deleteAssetContact(Integer id){
        this.simpleJdbcTemplate.update(SQL_DELETE_ASSET_CONTACT, id);
    }

    @Override
    @Value("assetContact")
    public AssetContact addAssetContact(AssetContact assetContact){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("dateAssigned", assetContact.getDateAssigned());
        params.put("dateReturned", assetContact.getDateReturned());
        params.put("asset", assetContact.getAsset());
        params.put("status", assetContact.getStatus());
        params.put("comments", assetContact.getComments());
        params.put("createdBy", assetContact.getCreatedBy());
        params.put("dateCreated", assetContact.getDateCreated());

        Number newId = simpleInsert.executeAndReturnKey(params);
        assetContact.setId(newId.intValue());
        return assetContact;
    }

    // Asset Log
    @Override
    @Value("id")
    public AssetLog findAssetLogById(Integer id){
        AssetLog assetLog;
        try {
            assetLog = this.simpleJdbcTemplate.queryForObject(
                    SQL_SELECT_ASSET_LOG + "WHERE id=?",
                    BeanPropertyRowMapper.newInstance(AssetLog.class),
                    id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(AssetLog.class, id);
        }

        return assetLog;

    }

    @Override
    @Value("criteria")
    public List<AssetLog> searchForAssetLog(String criteria){
        Map<String,String> params = new HashMap<String,String>();
        params.put("criteria","%" + criteria + "%");

        List<AssetLog> assetLogList = this.simpleJdbcTemplate.query(
                SQL_ASSET_LOG_SEARCH,
                BeanPropertyRowMapper.newInstance(AssetLog.class), params);

        return assetLogList;
    }

    @Override
    public List<AssetLog> listAllAssetLog(){
        List<AssetLog> assetLogList = this.simpleJdbcTemplate.query(
                SQL_SELECT_ASSET_LOG+ "ORDER BY id DESC",
                BeanPropertyRowMapper.newInstance(AssetLog.class));

        return assetLogList;
    }


    @Override
    @Value("assetLog")
    public AssetLog updateAssetLog(AssetLog assetLog){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id", assetLog.getId());
        params.put("entryTime", assetLog.getEntryTime());
        params.put("exitTime", assetLog.getExitTime());
        params.put("asset", assetLog.getAsset());
        params.put("createdBy", assetLog.getCreatedBy());
        params.put("dateCreated", assetLog.getDateCreated());
        this.simpleJdbcTemplate.update(SQL_UPDATE_ASSET_CONTACT, params);


        return assetLog;

    }

    @Override
    @Transactional
    @Value("id")
    public void deleteAssetLog(Integer id){
        this.simpleJdbcTemplate.update(SQL_DELETE_ASSET_LOG, id);
    }

    @Override
    @Value("assetLog")
    public AssetLog addAssetLog(AssetLog assetLog){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("entry_time", assetLog.getEntryTime());
        params.put("exit_time", assetLog.getExitTime());
        params.put("asset", assetLog.getAsset());
        params.put("created_by", assetLog.getCreatedBy());
        params.put("date_created", assetLog.getDateCreated());

        Number newId = simpleInsert.executeAndReturnKey(params);
        assetLog.setId(newId.intValue());
        return assetLog;
    }

    // Asset Loss
    @Override
    @Value("id")
    public AssetLoss findAssetLossById(Integer id){
        AssetLoss assetLoss;
        try {
            assetLoss = this.simpleJdbcTemplate.queryForObject(
                    SQL_SELECT_ASSET_LOSS + "WHERE id=?",
                    BeanPropertyRowMapper.newInstance(AssetLoss.class),
                    id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(AssetLoss.class, id);
        }

        return assetLoss;

    }

    @Override
    @Value("criteria")
    public List<AssetLoss> searchForAssetLoss(String criteria){
        Map<String,String> params = new HashMap<String,String>();
        params.put("criteria","%" + criteria + "%");

        List<AssetLoss> assetLossList = this.simpleJdbcTemplate.query(
                SQL_ASSET_LOSS_SEARCH, BeanPropertyRowMapper.newInstance(AssetLoss.class), params);

        return assetLossList;
    }

    @Override
    public List<AssetLoss> listAllAssetLoss(){
        List<AssetLoss> assetLossList = this.simpleJdbcTemplate.query(
                SQL_SELECT_ASSET_LOSS+ "ORDER BY id DESC",
                BeanPropertyRowMapper.newInstance(AssetLoss.class));

        return assetLossList;
    }

    @Override
    @Value("assetLoss")
    public AssetLoss updateAssetLoss(AssetLoss assetLoss){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id", assetLoss.getId());
        params.put("dateLost", assetLoss.getDateLost());
        params.put("dateReported", assetLoss.getDateReported());
        params.put("asset", assetLoss.getAsset());
        params.put("status", assetLoss.getStatus());
        params.put("comments", assetLoss.getComments());
        params.put("createdBy", assetLoss.getCreatedBy());
        params.put("dateCreated", assetLoss.getDateCreated());
        this.simpleJdbcTemplate.update(SQL_UPDATE_ASSET_CONTACT, params);

        return assetLoss;
    }

    @Override
    @Transactional
    @Value("id")
    public void deleteAssetLoss(Integer id){
        this.simpleJdbcTemplate.update(SQL_DELETE_ASSET_LOSS, id);
    }

    @Override
    @Value("assetLoss")
    public AssetLoss addAssetLoss(AssetLoss assetLoss){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("date_lost", assetLoss.getDateLost());
        params.put("date_reported", assetLoss.getDateReported());
        params.put("asset", assetLoss.getAsset());
        params.put("status", assetLoss.getStatus());
        params.put("comments", assetLoss.getComments());
        params.put("created_by", assetLoss.getCreatedBy());
        params.put("date_created", assetLoss.getDateCreated());;

        Number newId = simpleInsert.executeAndReturnKey(params);
        assetLoss.setId(newId.intValue());
        return assetLoss;
    }
}
