package com.pranavpore.cartzy.dto;

import com.pranavpore.cartzy.model.Category;
import com.pranavpore.cartzy.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDTO> images;
}
