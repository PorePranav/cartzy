package com.pranavpore.cartzy.repository;

import com.pranavpore.cartzy.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
