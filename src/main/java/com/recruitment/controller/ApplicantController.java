package com.recruitment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApplicantController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/uploadResume")
    public ResponseEntity<?> uploadResume(@RequestParam("resume") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "file required"));
        }
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        File dest = new File(dir, System.currentTimeMillis() + "_" + file.getOriginalFilename());
        FileCopyUtils.copy(file.getInputStream(), new java.io.FileOutputStream(dest));
        return ResponseEntity.ok(Map.of("success", true, "path", dest.getAbsolutePath()));
    }
}
