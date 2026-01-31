package com.task.webpics.service;

import com.task.webpics.entity.Images;
import com.task.webpics.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final AwsService awsService;
    private final ImageRepository imageRepository;

    @Transactional
    public Images uploadAndAnalyze(MultipartFile file) {
        String url = awsService.uploadImage(file);

        String fileName = url.substring(url.lastIndexOf("/") + 1);
        List<String> detectedTags = awsService.detectLabels(fileName);

        String tagsString;
        if (detectedTags.isEmpty()) {
            tagsString = "Unknown";
        } else {
            tagsString = String.join(",", detectedTags);
        }

        Images images = new Images(url, fileName, tagsString);
        return imageRepository.save(images);
    }

    public List<Images> searchByTag(String tag) {
        if (tag == null || tag.isBlank()) {
            return imageRepository.findAll();
        }
        return imageRepository.findByTagsContainingIgnoreCase(tag);
    }

    public List<Images> findAll() {
        return imageRepository.findAll();
    }
}
