//package com.example.controller;
//
//import com.example.repository.FirebaseExchangeToken;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//@RestController
//public class HomeController {
//
//  static String CREATE_TOKEN = "https://us-central1-homeboard-firebase.cloudfunctions.net/getStore";
//  static String EXCHANGE_TOKEN = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyCustomToken?key=AIzaSyDzBMQ0L_XtM-Zhvw2pwQ22BuaLDyhtb68";
//
//  @Autowired
//  private OAuth2AuthorizedClientService authorizedClientService;
//
//  @Autowired
//  private ClientRegistrationRepository clientRegistrationRepository;
//
//  @RequestMapping("/api/init")
//  public HomeResponse index(OAuth2AuthenticationToken token, Model model) throws IOException {
//    // 認証していない場合
//    if (token == null) {
//      return new HomeResponse(false);
//    }
//
//    // 認証している場合
//    OAuth2AuthorizedClient authorizedClient = getAuthorizedClient(token);
//
//    String provider = authorizedClient.getClientRegistration().getClientName();
//    String userId = authorizedClient.getClientRegistration().getClientId();
//
//    System.out.println("Provider: " + provider);
//    System.out.println("ClientId: " + userId);
//
//    RestTemplate rest = new RestTemplate();
//    Map<String, Object> params = new HashMap<String, Object>();
//    params.put("uid", provider + ":" + userId);
//
//    //TODO: 400の場合、例外処理が発生するので、例外処理を作り込む
//    ResponseEntity<String> entity1 = rest.postForEntity(CREATE_TOKEN, params, String.class);
//    if (entity1.getStatusCodeValue() == 200) {
//      ObjectMapper mapper = new ObjectMapper();
//      ObjectNode node = mapper.readValue(entity1.getBody(), ObjectNode.class);
//      
//      String customToken = node.get("token").textValue();
//      String documentId = node.get("documentId").textValue();
//      boolean created = node.get("created").booleanValue();
//      JsonNode dashboards = node.get("dashboards");
//      
//      System.out.println("customToken: " + customToken + ", dashboards: " + dashboards);
//      params = new HashMap<String, Object>();
//      params.put("token", customToken);
//      params.put("returnSecureToken", true);
//      //TODO: 400の場合、例外処理が発生するので、例外処理を作り込む
//      ResponseEntity<FirebaseExchangeToken> entity2 = rest.postForEntity(EXCHANGE_TOKEN, params, FirebaseExchangeToken.class);
//
//      // System.out.println("status: " + entity2.getStatusCodeValue());
//      if (entity2.getStatusCodeValue() == 200) {
//        FirebaseExchangeToken exchangeToken = entity2.getBody();
//        System.out.println("token: " + exchangeToken.idToken + " - " + exchangeToken.refreshToken);
//        
//        return new HomeResponse(true, created, documentId, dashboards, exchangeToken.idToken, exchangeToken.refreshToken);
//      }
//    }
//
//    return new HomeResponse(false);
//  }
//
//  private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken token) {
//    return authorizedClientService.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), token.getName());
//  }
//}
