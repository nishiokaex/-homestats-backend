package com.example.controller;

import com.fasterxml.jackson.databind.JsonNode;

public class HomeResponse {
  public boolean authenticated;
  public boolean created;
  public String documentId;
  public JsonNode dashboards;
  public String idToken;
  public String refreshToken;

  public HomeResponse(boolean authenticated) {
    this.authenticated = authenticated;
  }
  
  public HomeResponse(boolean authenticated, boolean created, String documentId, JsonNode dashboards, String idToken, String refreshToken) {
    this.authenticated = authenticated;
    this.created = created;
    this.documentId = documentId;
    this.dashboards = dashboards;
    this.idToken = idToken;
    this.refreshToken = refreshToken;
  }
}
