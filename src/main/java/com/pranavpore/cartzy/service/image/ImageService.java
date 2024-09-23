package com.pranavpore.cartzy.service.image;

import com.pranavpore.cartzy.dto.ImageDTO;
import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.Image;
import com.pranavpore.cartzy.model.Product;
import com.pranavpore.cartzy.repository.ImageRepository;
import com.pranavpore.cartzy.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
    };

    @Override
    public List<ImageDTO> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDTO> savedImageDto = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadURL = "/api/v1/images/image/download/";
                String downloadURL = buildDownloadURL + image.getId();
                image.setDownloadUrl(downloadURL);

                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadURL + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setImageId(savedImage.getId());
                imageDTO.setImageName(savedImage.getFileName());
                imageDTO.setDownloadUrl(savedImage.getDownloadUrl());

                savedImageDto.add(imageDTO);
            } catch(SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch(IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
