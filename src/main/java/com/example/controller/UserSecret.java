package com.example.controller;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "secret", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSecret implements Serializable {
  public boolean authed;
  public UUID userMetaId;
  public UUID userStoreId;
  public String idToken;
  public String refreshToken;
}
