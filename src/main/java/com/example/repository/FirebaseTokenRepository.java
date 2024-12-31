package com.example.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class FirebaseTokenRepository {

  static String TOKENS_URL = "https://us-central1-homeboard-firebase.cloudfunctions.net/v1/tokens";
  static String EXCHANGE_TOKEN = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyCustomToken?key=AIzaSyDzBMQ0L_XtM-Zhvw2pwQ22BuaLDyhtb68";

  public FirebaseToken create(String userId) {
    RestTemplate rest = new RestTemplate();

    Map<String, Object> params = new HashMap<>();
    params.put("userId", userId);
    params.put("userMetaId", UUID.randomUUID().toString());
    params.put("userStoreId", UUID.randomUUID().toString());

    // firebase functions POST /v1/sessions
    //TODO: 400の場合、例外処理が発生するので、例外処理を作り込む
    ResponseEntity<FirebaseCreateToken> entity1 = rest.postForEntity(TOKENS_URL, params, FirebaseCreateToken.class);
    if (entity1.getStatusCodeValue() == 200) {
      FirebaseCreateToken ctoken = entity1.getBody();

      System.out.println("customToken: " + ctoken.token + ", dashboards: " + ctoken.dashboards);

      params = new HashMap<>();
      params.put("token", ctoken.token);
      params.put("returnSecureToken", true);
      //TODO: 400の場合、例外処理が発生するので、例外処理を作り込む
      ResponseEntity<FirebaseExchangeToken> entity2 = rest.postForEntity(EXCHANGE_TOKEN, params, FirebaseExchangeToken.class);
      FirebaseExchangeToken etoken = entity2.getBody();

      System.out.println("idtoken: " + etoken.idToken + " - " + etoken.refreshToken);

      return new FirebaseToken(ctoken, etoken);
    }

    return null;
  }
}
