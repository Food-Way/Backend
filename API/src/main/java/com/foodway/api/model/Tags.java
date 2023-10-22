package com.foodway.api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "tbRate")
@Entity(name = "tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTags;

    private String name;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Tags(String name) {
        this.name = name;
    }

    public Tags() {
    }

    public Long getIdTags() {
        return idTags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
