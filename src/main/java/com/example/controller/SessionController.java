package com.example.controller;

import com.example.repository.FirebaseToken;
import com.example.repository.FirebaseTokenRepository;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

  static String SESSION_URL = "https://us-central1-homeboard-firebase.cloudfunctions.net/v1/sessions";
  static String EXCHANGE_TOKEN = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyCustomToken?key=AIzaSyDzBMQ0L_XtM-Zhvw2pwQ22BuaLDyhtb68";

  @Autowired
  private OAuth2AuthorizedClientService authorizedClientService;

  @Autowired
  private HttpSession session;

  @Autowired
  private UserSecret secret;

  private FirebaseTokenRepository tokens = new FirebaseTokenRepository();

  // OAuthログインしている場合、セッションを作る
  @PostMapping("/api/sessions")
  public SessionResponse create(OAuth2AuthenticationToken token) throws IOException {
    System.out.println("OK");
    OAuth2AuthorizedClient client = getAuthorizedClient(token);
    String provider = client.getClientRegistration().getClientName();
    String userId = client.getClientRegistration().getClientId();

    System.out.println("Provider: " + provider);
    System.out.println("ClientId: " + userId);

    FirebaseToken ftoken = tokens.create(provider + ":" + userId);

    secret.idToken = ftoken.idToken;
    secret.refreshToken = ftoken.refreshToken;
    secret.userMetaId = UUID.fromString(ftoken.userMetaId);
    secret.userStoreId = UUID.fromString(ftoken.userStoreId);

    return new SessionResponse(ftoken.created, ftoken.dashboards);
  }

  @DeleteMapping("/api/sessions")
  public void destroy(OAuth2AuthenticationToken token) {
    session.invalidate();
  }

  private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken token) {
    return authorizedClientService.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), token.getName());
  }
}
