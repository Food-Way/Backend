package com.foodway.api.controller;


import com.foodway.api.service.AzureBlobService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("/files")
public class AzureController {

  @Autowired
  private AzureBlobService azureBlobAdapter;

  @Operation(summary = "Upload files to Azure Blob Storage")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully uploaded files"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping("/{container}")
  public ResponseEntity<List<String>> upload(
          @Parameter(name = "Name of the container", required = true) @PathVariable String container,
          @Parameter(name = "Files to upload", required = true) @RequestParam List<MultipartFile> files) throws IOException {
    if (files.isEmpty()) {
      throw new IllegalArgumentException("No files to upload");
    }

    String actualContainer = container != null ? container : azureBlobAdapter.getContainerName();

    for (MultipartFile file : files) {
      if (!file.getContentType().startsWith("image")) {
        throw new IllegalArgumentException("Only images are allowed");
      }
    }
    List<String> itensName = new ArrayList<String>() ;
    for (MultipartFile file : files) {
      itensName.add(file.getOriginalFilename());
      azureBlobAdapter.setContainerName(actualContainer);
      System.out.println(azureBlobAdapter.upload(file));
    }
    return ResponseEntity.ok().body(itensName);
  }

  @Operation(summary = "Get all blobs from Azure Blob Storage")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved blobs"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{container}")
  public ResponseEntity<List<String>> getAllBlobs(
          @Parameter(name = "Name of the container", required = true) @PathVariable String container) {
    String actualContainer = container != null ? container : azureBlobAdapter.getContainerName();
    azureBlobAdapter.setContainerName(actualContainer);
    List<String> items = azureBlobAdapter.listBlobs();
    return ResponseEntity.ok(items);
  }

  @Operation(summary = "Delete a blob from Azure Blob Storage")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Blob deleted successfully"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{container}")
  public ResponseEntity<Boolean> delete(
          @Parameter(name = "Name of the container", required = true) @PathVariable String container,
          @Parameter(name = "Name of the blob to delete", required = true) @RequestParam String fileName) {
    String actualContainer = container != null ? container : azureBlobAdapter.getContainerName();
    azureBlobAdapter.setContainerName(actualContainer);
    azureBlobAdapter.deleteBlob(fileName);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Download a file from Azure Blob Storage")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "File downloaded successfully"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/download/{container}")
  public ResponseEntity<Resource> getFile(
          @Parameter(name = "Name of the container", required = true) @PathVariable String container,
          @Parameter(name = "Name of the file to download", required = true) @RequestParam String fileName)
          throws URISyntaxException {
    String actualContainer = container != null ? container : azureBlobAdapter.getContainerName();
    azureBlobAdapter.setContainerName(actualContainer);

    ByteArrayResource resource = new ByteArrayResource(azureBlobAdapter.getFile(fileName));

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .headers(headers)
            .body(resource);
  }
}