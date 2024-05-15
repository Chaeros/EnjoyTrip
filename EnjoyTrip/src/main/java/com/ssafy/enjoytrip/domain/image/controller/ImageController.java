package com.ssafy.enjoytrip.domain.image.controller;

import com.ssafy.enjoytrip.domain.attractionboardimage.dto.request.AttractionBoardImageAddRequestDto;
import com.ssafy.enjoytrip.domain.attractionboardimage.service.AttractionBoardImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final AttractionBoardImageService attractionBoardImageService;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        if (file.isEmpty()) {
            response.put("message", "File is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // 파일 MIME 타입 확인 (이미지 여부 확인)
        String mimeType = file.getContentType();
        if (mimeType == null || !mimeType.startsWith("image/")) {
            response.put("message", "Only image files are allowed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // 파일 크기 제한 (예: 10MB)
        long maxFileSize = 10 * 1024 * 1024;
        if (file.getSize() > maxFileSize) {
            response.put("message", "File is too large");
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
        }

        try {
            // 파일 저장 경로 설정
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadPath + File.separator + filename);

            // 디렉토리가 없으면 생성
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // 파일 저장
            Files.write(path, file.getBytes());
//            attractionBoardImageService.addAttractionBoardImage(
//                    AttractionBoardImageAddRequestDto.builder()
//                            .image(String.valueOf(path))
//                            .attractionBoardId(attractionBoardId)
//                            .build()
//            );

            // 파일 URL 반환
            String fileUrl = "/image/uploads/" + filename;
            response.put("url", fileUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("message", "File upload failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
