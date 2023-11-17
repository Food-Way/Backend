package com.foodway.api.controller;


import com.foodway.api.service.AzureBlobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/files")
public class AzureController {

  @Autowired
  private AzureBlobService azureBlobAdapter;

  @PostMapping
  public ResponseEntity<String> upload
          (@RequestParam List<MultipartFile> file)
          throws IOException {
    System.out.println(file);
    for (int i = 0; i < file.size(); i++) {
      System.out.println(azureBlobAdapter.upload(file.get(i)) + " uploaded");
    }
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<String>> getAllBlobs() {
    List<String> items = azureBlobAdapter.listBlobs();
    return ResponseEntity.ok(items);
  }

  @DeleteMapping
  public ResponseEntity<Boolean> delete
          (@RequestParam String fileName) {

    azureBlobAdapter.deleteBlob(fileName);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/download")
  public ResponseEntity<Resource> getFile
          (@RequestParam String fileName)
          throws URISyntaxException {

    ByteArrayResource resource =
            new ByteArrayResource(azureBlobAdapter
                    .getFile(fileName));

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\""
                    + fileName + "\"");

    return ResponseEntity.ok().contentType(MediaType
                    .APPLICATION_OCTET_STREAM)
            .headers(headers).body(resource);
  }
}