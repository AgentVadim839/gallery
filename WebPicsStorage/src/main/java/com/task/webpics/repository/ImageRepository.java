package com.task.webpics.repository;

import com.task.webpics.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Images, Long> {
    List<Images> findByTagsContainingIgnoreCase(String tag);
}
