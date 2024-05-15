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

import javax.imageio.stream.ImageOutputStream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
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

        String mimeType = file.getContentType();
        if (mimeType == null || !mimeType.startsWith("image/")) {
            response.put("message", "Only image files are allowed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        long maxFileSize = 10 * 1024 * 1024;
        if (file.getSize() > maxFileSize) {
            response.put("message", "File is too large");
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
        }

        try {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadPath + File.separator + filename);

            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // 이미지 읽기
            BufferedImage image = ImageIO.read(file.getInputStream());

            // 고화질 이미지 저장
            if (mimeType.equals("image/jpeg")) {
                saveHighQualityImage(image, path.toFile());
            } else {
                // 다른 이미지 형식의 경우, 일반적인 저장 방법 사용
                ImageIO.write(image, mimeType.split("/")[1], path.toFile());
            }

            String fileUrl = "/image/uploads/" + filename;
            response.put("url", fileUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("message", "File upload failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private void saveHighQualityImage(BufferedImage image, File output) throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(output);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(1.0f);  // 최대 품질로 설정

        writer.write(null, new IIOImage(image, null, null), param);
        ios.close();
        writer.dispose();
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
