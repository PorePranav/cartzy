package com.pranavpore.cartzy.service.image;

import com.pranavpore.cartzy.dto.ImageDTO;
import com.pranavpore.cartzy.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
