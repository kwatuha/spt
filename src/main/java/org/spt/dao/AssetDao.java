package org.spt.dao;

import org.spt.model.Asset;
import org.spt.model.AssetClearance;
import org.spt.model.AssetContact;
import org.spt.model.AssetLog;
import org.spt.model.AssetLoss;

import java.util.List;

/**
 *  The core interface for the Contact DAO
 */
public interface AssetDao {
    public Asset findAssetById(Integer id);
    public List<Asset> searchForAsset(String criteria);
    public List<Asset> listAllAssets();
    public Asset updateAsset(Asset asset);
    public void deleteAsset(Integer id);
    public Asset addAsset(Asset asset);
    public AssetClearance findAssetClearanceById(Integer id);
    public List<AssetClearance> searchForAssetClearance(String criteria);
    public List<AssetClearance> listAllAssetClearance();
    public AssetClearance updateAssetClearance(AssetClearance assetClearance);
    public void deleteAssetClearance(Integer id);
    public AssetClearance addAssetClearance(AssetClearance assetClearance);
    public AssetContact findAssetContactById(Integer id);
    public List<AssetContact> searchForAssetContact(String criteria);
    public List<AssetContact> listAllAssetContact();
    public AssetContact updateAssetContact(AssetContact assetContact);
    public void deleteAssetContact(Integer id);
    public AssetContact addAssetContact(AssetContact assetContact);
    public AssetLog findAssetLogById(Integer id);
    public List<AssetLog> searchForAssetLog(String criteria);
    public List<AssetLog> listAllAssetLog();
    public AssetLog updateAssetLog(AssetLog asset);

    public void deleteAssetLog(Integer id);

    public AssetLog addAssetLog(AssetLog asset);

    // Asset Loss
    public AssetLoss findAssetLossById(Integer id);

    public List<AssetLoss> searchForAssetLoss(String criteria);

    public List<AssetLoss> listAllAssetLoss();

    public AssetLoss updateAssetLoss(AssetLoss asset);

    public void deleteAssetLoss(Integer id);

    public AssetLoss addAssetLoss(AssetLoss asset);
}
