package com.example.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AssetCollection {

  public List<String> ids;
  public Map<String, Asset> byId;

  public void add(UUID assetId, String name, String contentType) {
    String id = assetId.toString();
    ids.add(id);
    byId.put(id, new Asset(id, name, contentType));
  }

  public void remove(UUID assetId) {
    String id = assetId.toString();
    
    ids.remove(id);
    byId.remove(id);
  }
}
