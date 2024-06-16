package com.foodway.api.model;

import com.foodway.api.record.RequestTag;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "tbTag")
@Entity(name = "tag")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTag;
    @NotNull
    @Column(unique = true)
    private String name;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    public Tags() {
    }
    public Tags(String name) {
        this.name = name;
    }
    public Tags(RequestTag tag) {
        this.name = tag.name();
    }
    public Long getIdTag() {
        return idTag;
    }
    public void setIdTag(Long idTag) {
        this.idTag = idTag;
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
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
