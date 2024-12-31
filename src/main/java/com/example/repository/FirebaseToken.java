package com.example.repository;

import com.fasterxml.jackson.databind.JsonNode;

public class FirebaseToken {

  public String userMetaId;
  public String userStoreId;
  public boolean created;
  public JsonNode dashboards;
  public String kind;
  public String idToken;
  public String refreshToken;
  public String expiresIn;

  public FirebaseToken(
    FirebaseCreateToken ctoken,
    FirebaseExchangeToken etoken) {
    
    userMetaId = ctoken.userMetaId;
    userStoreId = ctoken.userStoreId;
    created = ctoken.created;
    dashboards = ctoken.dashboards;
    
    kind = etoken.kind;
    idToken = etoken.idToken;
    refreshToken = etoken.refreshToken;
    expiresIn = etoken.expiresIn;
  }
}
