//package com.pranavpore.cartzy.service.image;
//
//import com.pranavpore.cartzy.dto.ImageDTO;
//import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
//import com.pranavpore.cartzy.model.Image;
//import com.pranavpore.cartzy.model.Product;
//import com.pranavpore.cartzy.repository.ImageRepository;
//import com.pranavpore.cartzy.service.product.IProductService;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.EntityTransaction;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.sql.DataSource;
//import javax.sql.rowset.serial.SerialBlob;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ImageService implements IImageService {
//    private final DataSource dataSource;
//    private final ImageRepository imageRepository;
//    private final IProductService productService;
//
////    @Override
////    @Transactional
////    public Image getImageById(Long id) {
////        return imageRepository
////                .findById(id)
////                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
////    }
//
//    @Override
//    public Image getImageById(Long id) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//        try {
//            transaction.begin();
//            Image image = imageRepository
//                    .findById(id)
//                    .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
//            transaction.commit();
//            return image;
//        } catch (Exception e) {
//            if (transaction.isActive()) {
//                transaction.rollback();
//            }
//            throw e;
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    @Override
//    public void deleteImageById(Long id) {
//        imageRepository
//                .findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
//    };
//
//    @Override
//    public List<ImageDTO> saveImage(List<MultipartFile> files, Long productId) {
//        Product product = productService.getProductById(productId);
//        List<ImageDTO> savedImageDto = new ArrayList<>();
//
//        for (MultipartFile file : files) {
//            try {
//                Image image = new Image();
//                image.setFileName(file.getOriginalFilename());
//                image.setFileType(file.getContentType());
//                image.setImage(new SerialBlob(file.getBytes()));
//                image.setProduct(product);
//
//                String buildDownloadURL = "/api/v1/images/download/";
//                String downloadURL = buildDownloadURL + image.getId();
//                image.setDownloadUrl(downloadURL);
//
//                Image savedImage = imageRepository.save(image);
//                savedImage.setDownloadUrl(buildDownloadURL + savedImage.getId());
//                imageRepository.save(savedImage);
//
//                ImageDTO imageDTO = new ImageDTO();
//                imageDTO.setImageId(savedImage.getId());
//                imageDTO.setImageName(savedImage.getFileName());
//                imageDTO.setDownloadUrl(savedImage.getDownloadUrl());
//
//                savedImageDto.add(imageDTO);
//            } catch(SQLException | IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return savedImageDto;
//    }
//
//    @Override
//    public void updateImage(MultipartFile file, Long imageId) {
//        Image image = getImageById(imageId);
//        try {
//            image.setFileName(file.getOriginalFilename());
//            image.setImage(new SerialBlob(file.getBytes()));
//            imageRepository.save(image);
//        } catch(IOException | SQLException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//}
package com.pranavpore.cartzy.service.image;

import com.pranavpore.cartzy.dto.ImageDTO;
import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.Image;
import com.pranavpore.cartzy.model.Product;
import com.pranavpore.cartzy.repository.ImageRepository;
import com.pranavpore.cartzy.service.product.IProductService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final DataSource dataSource;
    private final ImageRepository imageRepository;
    private final IProductService productService;
    private final EntityManagerFactory entityManagerFactory;

    @Override
    @Transactional
    public Image getImageById(Long id) {
        return imageRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteImageById(Long id) {
        Image image = imageRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
        imageRepository.delete(image);
    }

    @Override
    @Transactional
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

                String buildDownloadURL = "/api/v1/images/download/";
                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadURL + savedImage.getId());
                savedImage = imageRepository.save(savedImage);

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
    @Transactional
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
