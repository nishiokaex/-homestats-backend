package com.example.repository;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
public class AssetsRepository {

  static String ASSETS_URL = "https://us-central1-homeboard-firebase.cloudfunctions.net/v1/assets";

  public void show(UUID assetsID, String contentType) {
    RestTemplate rest = new RestTemplate();

    // SignedURLを取得
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("contentType", contentType);
    params.put("filename", assetsID);

    ResponseEntity<String> entity1 = rest.postForEntity(ASSETS_URL + "/read", params, String.class);
    String uploadURL;
    try {
      uploadURL = URLDecoder.decode(entity1.getBody(), "UTF-8");
      System.out.println(uploadURL);
    } catch (UnsupportedEncodingException ex) {
      throw new RuntimeException(ex);
    }
  }

  // POST ファイルをアップロードする
  public void create(UUID assetsID, String contentType, byte[] body) {
    RestTemplate rest = new RestTemplate();

    // SignedURLを取得
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("contentType", contentType);
    params.put("filename", assetsID);

    ResponseEntity<String> entity1 = rest.postForEntity(ASSETS_URL + "/write", params, String.class);
    if (entity1.getStatusCodeValue() != 200) {
      return;
    }

    String uploadURL = entity1.getBody();
    System.out.println("assetsID: " + assetsID);
    System.out.println("contentType: " + contentType);
    System.out.println("UploadURL: " + uploadURL);

    // SignedURLにPOST
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(MediaType.parseMediaTypes(MediaType.ALL_VALUE));
      headers.setContentType(MediaType.parseMediaType(contentType));
      HttpEntity<byte[]> request = new HttpEntity<byte[]>(body, headers);
      rest.put(uploadURL, request);
    } catch (Exception ex) {
      ex.printStackTrace();
      //throw ex;
    }
  }

  // DELETE ファイルを削除する
  public void destroy(UUID assetsID, String token) {

  }
}
