package com.pranavpore.cartzy.controller;

import com.pranavpore.cartzy.dto.ProductDTO;
import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.Product;
import com.pranavpore.cartzy.request.AddProductRequest;
import com.pranavpore.cartzy.request.UpdateProductRequest;
import com.pranavpore.cartzy.response.APIResponse;
import com.pranavpore.cartzy.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping("/")
    public ResponseEntity<APIResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOS = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new APIResponse("success", productDTOS));
    }

    @PostMapping("/")
    public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new APIResponse("product added successfully", theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDTO productDTO = productService.convertToDTO(product);
            return ResponseEntity.ok(new APIResponse("success", productDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<APIResponse> updateProduct(@PathVariable Long productId,
                                                     @RequestBody UpdateProductRequest product) {
        try {
            Product theProduct = productService.updateProduct(product, productId);
            ProductDTO theProductDTO = productService.convertToDTO(theProduct);
            return ResponseEntity.ok(new APIResponse("product updated successfully", theProductDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new APIResponse("product deleted successfully", productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/search/byBrandAndName")
    public ResponseEntity<APIResponse> getProductByBrandAndName(@RequestParam String brandName,
                                                                @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
            List<ProductDTO> productDTOS = productService.getConvertedProducts(products);
            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("no products found", null));
            return ResponseEntity.ok(new APIResponse("success", productDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/search/byCategoryAndBrand")
    public ResponseEntity<APIResponse> getProductByCategoryAndBrand(@RequestParam String categoryName,
                                                                    @RequestParam String brandName) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
            List<ProductDTO> productDTOS = productService.getConvertedProducts(products);
            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("no products found", null));
            return ResponseEntity.ok(new APIResponse("success", productDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/search/byName")
    public ResponseEntity<APIResponse> getProductByName(@RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByName(productName);
            List<ProductDTO> productDTOS = productService.getConvertedProducts(products);
            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("no products found", null));
            return ResponseEntity.ok(new APIResponse("success", productDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/search/byBrand/{brandName}")
    public ResponseEntity<APIResponse> getProductByBrand(@PathVariable String brandName) {
        try {
            List<Product> products = productService.getProductsByBrand(brandName);
            List<ProductDTO> productDTOS = productService.getConvertedProducts(products);
            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("no products found", null));
            return ResponseEntity.ok(new APIResponse("success", productDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/search/byCategory/{categoryName}")
    public ResponseEntity<APIResponse> getProductByCategory(@PathVariable String categoryName) {
        try {
            List<Product> products = productService.getProductsByCategory(categoryName);
            List<ProductDTO> productDTOS = productService.getConvertedProducts(products);
            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("no products found", null));
            return ResponseEntity.ok(new APIResponse("success", productDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count/byBrandAndName")
    public ResponseEntity<APIResponse> getProductCountByBrandAndName(@RequestParam String brand,
                                                                     @RequestParam String name) {
        try {
            Long count = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new APIResponse("success", count));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }
}
