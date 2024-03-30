package com.example.demo.controller;

import com.example.demo.model.response.ResponseApi;
import com.example.demo.service.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class UploadController {
    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @Operation(summary = "Upload multiple file", description = "Upload multiple file")
    @PostMapping("/uploadMultiple")
    public ResponseEntity<ResponseApi<?>> uploadMultipleFile(@RequestParam(value = "files") MultipartFile[] file) {
        return uploadService.uploadMultipleFile(file);
    }

    @Operation(summary = "Upload single file", description = "Upload single file")
    @PostMapping("/uploadFile")
    public ResponseEntity<ResponseApi<?>> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return uploadService.uploadFile(file);
    }

//    @PostMapping("/createBucket")
//    public ResponseEntity<?> createBucket(@RequestBody String name) {
//        return ResponseEntity.ok(uploadService.createBucket(name));
//    }
//
//    @GetMapping("/getListBucket")
//    public ResponseEntity<?> getListBucket() {
//        return ResponseEntity.ok(uploadService.getListBucket());
//    }
}
