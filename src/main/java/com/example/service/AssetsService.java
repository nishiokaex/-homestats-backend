package com.example.service;

import com.example.repository.AssetCollection;
import com.example.repository.UserMetasRepository;
import com.example.repository.AssetsRepository;
import com.example.repository.UserMeta;
import java.util.UUID;

public class AssetsService {

  AssetsRepository assets = new AssetsRepository();
  UserMetasRepository metas = new UserMetasRepository();

  public AssetCollection index(UUID metaId) {
    if (metaId == null) {
      throw new IllegalArgumentException();
    }

    UserMeta user = metas.show(metaId);
    return user.assets;
  }

  // POST ファイルをアップロードする
  public AssetCollection create(UUID metaId, String contentType, String name, byte[] body) {
    if (metaId == null) {
      throw new IllegalArgumentException();
    }

    UUID assetId = UUID.randomUUID();

    // contentType をチェックする
    // storage に put
    assets.create(assetId, contentType, body);

    // store の images に add
    UserMeta meta = metas.show(metaId);
    meta.assets.add(assetId, name, contentType);
    metas.update(metaId, meta);

    return meta.assets;
  }

  // DELETE ファイルを削除する
  public void destroy(UUID userId, UUID assetId) {
    // assetIDの所有者チェック
    String token = "";
    assets.destroy(assetId, token);

    UserMeta user = metas.show(userId);
    user.assets.remove(assetId);
    metas.update(userId, user);
  }
}
