package com.example.controller;

import com.example.repository.AssetCollection;
import com.example.service.AssetsService;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * SecurityConfig設定により、認証済みの場合のみアクセス可能.
 */
@RestController
public class AssetsController {
  @Autowired
  UserSecret secret;

  AssetsService service = new AssetsService();

  @GetMapping("/api/assets")
  public AssetsResponse index() throws IOException {
    AssetsResponse response = new AssetsResponse();

    AssetCollection assets = service.index(secret.userMetaId);
    assets.ids.forEach((id) -> response.ids.add(id));

    return response;
  }

  @PostMapping("/api/assets")
  public AssetsResponse create(@RequestParam MultipartFile file) throws IOException {
    AssetsResponse response = new AssetsResponse();
    AssetCollection assets = service.create(secret.userMetaId, file.getContentType(), file.getOriginalFilename(), file.getBytes());
    assets.ids.forEach((id) -> response.ids.add(id));

    return response;
  }

  @DeleteMapping("/api/assets")
  public void delete(OAuth2AuthenticationToken token, String uuid) throws IOException {
    if (secret == null) {
      throw new AccessDeniedException("403 Forbidden");
    }

    service.destroy(secret.userMetaId, UUID.fromString(uuid));
  }
}
