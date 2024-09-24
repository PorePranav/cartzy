package com.pranavpore.cartzy.controller;

import com.pranavpore.cartzy.dto.ImageDTO;
import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.Image;
import com.pranavpore.cartzy.response.APIResponse;
import com.pranavpore.cartzy.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<APIResponse> uploadImages(@RequestParam List<MultipartFile> file,
                                                    @RequestParam Long productId) {
        try {
            List<ImageDTO> images = imageService.saveImage(file, productId);
            return ResponseEntity.ok(new APIResponse("Upload Successful", images));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Upload Failed", e.getMessage()));
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            ByteArrayResource resource = new ByteArrayResource(image
                    .getImage()
                    .getBytes(1, (int) image.getImage().length()));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(resource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<APIResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new APIResponse("Update Successful", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Update Failed", INTERNAL_SERVER_ERROR));
        }
        return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Update Failed", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<APIResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new APIResponse("Delete Successful", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Delete Failed", INTERNAL_SERVER_ERROR));
        }
        return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Delete Failed", INTERNAL_SERVER_ERROR));
    }
}
