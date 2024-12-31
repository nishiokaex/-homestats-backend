package com.example.repository;

public class Asset {

  public String uuid;
  public String name;
  public String contentType;

  public Asset() {
  }
  
  public Asset(String uuid, String name, String contentType) {
    this.uuid = uuid;
    this.name = name;
    this.contentType = contentType;
  }
}
