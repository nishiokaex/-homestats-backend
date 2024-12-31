package com.example.controller;

import com.fasterxml.jackson.databind.JsonNode;

public class SessionResponse {
  public boolean authenticated;
  public boolean created;
  public JsonNode dashboards;

  public SessionResponse() {
    authenticated = false;
  }

  public SessionResponse(boolean created, JsonNode dashboards) {
    this.authenticated = true;
    this.created = created;
    this.dashboards = dashboards;
  }
}
