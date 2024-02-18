package com.foodway.api.model;

import com.foodway.api.model.Enums.ETagName;
import com.foodway.api.record.RequestTag;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "tbTag")
@Entity(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTag;
    @NotNull
    private UUID idEstablishment;
    @NotBlank
    private ETagName name;
    @NotNull
    private boolean enable;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Tag() {
    }

    public Tag(UUID idEstablishment, ETagName name, boolean enable) {
        this.idEstablishment = idEstablishment;
        this.name = name;
        this.enable = enable;
    }

    public Tag(RequestTag tag) {
        this.name = tag.name();
        this.idEstablishment = tag.idEstablishment();
        this.enable = tag.enable();
    }

    public Long getIdTag() {
        return idTag;
    }

    public void setIdTag(Long idTag) {
        this.idTag = idTag;
    }

    public UUID getIdEstablishment() {
        return idEstablishment;
    }

    public void setIdEstablishment(UUID idEstablishment) {
        this.idEstablishment = idEstablishment;
    }

    public ETagName getName() {
        return name;
    }

    public void setName(ETagName name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
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
