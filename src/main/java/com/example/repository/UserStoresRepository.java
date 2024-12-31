package com.example.repository;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserStoresRepository {

  static String USER_STORE_URL = "https://us-central1-homeboard-firebase.cloudfunctions.net/v1/userstores";

  public UserStore show(UUID storeId) {
    RestTemplate web = new RestTemplate();
    String url = USER_STORE_URL + "/" + storeId;
    ResponseEntity<UserStore> entity = web.getForEntity(url, UserStore.class);
    return entity.getBody();
  }

  public void update(UUID storeId, UserStore entity) {
    RestTemplate web = new RestTemplate();
    String url = USER_STORE_URL + "/" + storeId;
    web.put(url, entity, String.class);
  }

  public void destroy(UUID storeId) {
    RestTemplate web = new RestTemplate();
    String url = USER_STORE_URL + "/" + storeId;
    web.delete(url);
  }
}
