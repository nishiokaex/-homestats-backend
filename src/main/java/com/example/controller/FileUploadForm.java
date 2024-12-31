package com.example.controller;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm implements Serializable {
  /*
   * <input name="image" type="file" />
   */
  public MultipartFile image;
}
