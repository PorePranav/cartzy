package com.pranavpore.cartzy.service.product;

import com.pranavpore.cartzy.dto.ProductDTO;
import com.pranavpore.cartzy.model.Product;
import com.pranavpore.cartzy.request.AddProductRequest;
import com.pranavpore.cartzy.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDTO> getConvertedProducts(List<Product> products);

    ProductDTO convertToDTO(Product product);
}
