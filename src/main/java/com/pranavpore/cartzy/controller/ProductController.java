package com.pranavpore.cartzy.controller;

import com.pranavpore.cartzy.exceptions.ResourceNotFoundException;
import com.pranavpore.cartzy.model.Product;
import com.pranavpore.cartzy.request.AddProductRequest;
import com.pranavpore.cartzy.request.UpdateProductRequest;
import com.pranavpore.cartzy.response.APIResponse;
import com.pranavpore.cartzy.service.product.IProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("${api.prefix}/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping("/")
    public ResponseEntity<APIResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new APIResponse("Success", products));
    }

    @GetMapping("${productId}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new APIResponse("Success", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new APIResponse("Product added successfully", theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<APIResponse> updateProduct(@PathVariable Long productId,
                                                     @RequestBody UpdateProductRequest product) {
        try {
            Product theProduct = productService.updateProduct(product, productId);
            return ResponseEntity.ok(new APIResponse("Product updated successfully", theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new APIResponse("Product deleted successfully", productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byBrandAndName/{brandName}/{productName}")
    public ResponseEntity<APIResponse> getProductByBrandAndName(@PathVariable String brandName,
                                                                @PathVariable String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No products found",
                        null));
            return ResponseEntity.ok(new APIResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byBrandAndName/{categoryName}/{brandName}")
    public ResponseEntity<APIResponse> getProductByCategoryAndBrandName(@PathVariable String categoryName,
                                                                @PathVariable String brandName) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No products found",
                        null));
            return ResponseEntity.ok(new APIResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{name}/product")
    public ResponseEntity<APIResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No products found", null));
            return ResponseEntity.ok(new APIResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{brandName}/brand")
    public ResponseEntity<APIResponse> getProductByBrand(@PathVariable String brandName) {
        try {
            List<Product> products = productService.getProductsByBrand(brandName);
            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No products found", null));
            return ResponseEntity.ok(new APIResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{category}/all/products")
    public ResponseEntity<APIResponse> getProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No products found", null));
            return ResponseEntity.ok(new APIResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }
}
