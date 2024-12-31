package com.example.repository;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserMetasRepository {

  static String USER_METAS_URL = "https://us-central1-homeboard-firebase.cloudfunctions.net/v1/usermetas";

  public UserMeta show(UUID metaId) {
    if (metaId == null) {
      throw new IllegalArgumentException();
    }

    RestTemplate web = new RestTemplate();
    String url = USER_METAS_URL + "/" + metaId.toString();
    ResponseEntity<UserMeta> entity = web.getForEntity(url, UserMeta.class);
    return entity.getBody();
  }

  public void update(UUID metaId, UserMeta entity) {
    if (metaId == null) {
      throw new IllegalArgumentException();
    }

    RestTemplate web = new RestTemplate();
    String url = USER_METAS_URL + "/" + metaId;
    web.put(url, entity, String.class);
  }

  public void destroy(UUID metaId) {
    if (metaId == null) {
      throw new IllegalArgumentException();
    }

    RestTemplate web = new RestTemplate();
    String url = USER_METAS_URL + "/" + metaId;
    web.delete(url);
  }
}
