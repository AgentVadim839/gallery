package com.task.webpics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AwsService {
    private final S3Client s3Client;
    private final RekognitionClient rekognitionClient;

    @Value("${aws.bucket}")
    private String bucketName;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    public String uploadImage(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String keyName = System.currentTimeMillis() + "_" + originalFileName;

            // POST request
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();


            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, keyName);

            return url;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла", e);
        }
    }

    public List<String> detectLabels(String fileName) {
        S3Object s3Object = S3Object.builder()
                .bucket(bucketName)
                .name(fileName)
                .build();

        Image image = Image.builder()
                .s3Object(s3Object)
                .build();

        DetectLabelsRequest request = DetectLabelsRequest.builder()
                .image(image)
                .maxLabels(10)
                .minConfidence(90F)
                .build();

        DetectLabelsResponse response = rekognitionClient.detectLabels(request);

        return response.labels().stream()
                .map(Label::name)
                .toList();
    }
}
