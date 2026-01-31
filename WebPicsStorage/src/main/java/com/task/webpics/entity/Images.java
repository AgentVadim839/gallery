package com.task.webpics.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String tags;

    public Images(String url, String fileName, String tags) {
        this.url = url;
        this.fileName = fileName;
        this.tags = tags;
    }
}
