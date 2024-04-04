package com.foodway.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodway.api.record.RequestComment;
import com.foodway.api.record.RequestCommentChild;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "tbComment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPost;
    private UUID idParent;
    private UUID idEstablishment;
    private UUID idCustomer;
    private String comment;
    private Double generalRate;
    private int upvotes;
    private List<String> images;
    private String userPhoto;
    private String userName;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    @GeneratedValue(strategy = GenerationType.UUID)
    private Comment parentComment;
    @OneToMany(mappedBy = "parentComment")
    List<Comment> replies;
    @JsonIgnore
    @OneToMany
    private List<Upvote> upvoteList;

    public Comment() {
    }

    public Comment(UUID idPost) {
        this.idPost = idPost;
    }

    public Comment(int upvotes, String comment, List<String> images) {
        this.upvotes = upvotes;
        this.comment = comment;
        this.images = images;
        this.generalRate = 0.0;
        this.replies = new ArrayList<>();
    }

    public Comment(RequestComment data) {
        this.idCustomer = data.idCustomer();
        this.idEstablishment = data.idEstablishment();
        this.comment = data.comment();
        this.userPhoto = data.userPhoto();
        this.images = data.images();
        this.userName = data.userName();
        this.generalRate = 0.0;
        this.replies = new ArrayList<>();
    }

    public Comment(RequestCommentChild data) {
        this.idCustomer = data.idCustomer();
        this.idEstablishment = data.idEstablishment();
        this.idParent = data.idParent();
        this.comment = data.comment();
        this.userPhoto = data.userPhoto();
        this.images = data.images();
        this.userName = data.userName();
        this.generalRate = 0.0;
        this.replies = new ArrayList<>();
    }

    public void update(@NotNull Optional<?> optional) {
        RequestComment c = (RequestComment) optional.get();
        this.setcomment(c.comment());
        this.setImages(c.images());
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setIdPost(UUID idPost) {
        this.idPost = idPost;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public String getComment() {
        return comment;
    }

    public void setcomment(String comment) {
        this.comment = comment;
    }

    public UUID getIdPost() {
        return idPost;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public UUID getIdParent() {
        return idParent;
    }

    public UUID getIdEstablishment() {
        return idEstablishment;
    }

    public void setIdEstablishment(UUID idEstablishment) {
        this.idEstablishment = idEstablishment;
    }

    public UUID getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(UUID idCustomer) {
        this.idCustomer = idCustomer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Upvote> getUpvoteList() {
        return upvoteList;
    }

    public void addUpvote(Upvote upvote) {
        this.upvoteList.add(upvote);
    }

    public void removeUpvote(Upvote upvote) {
        this.upvoteList.remove(upvote);
    }

    public Double getGeneralRate() {
        return generalRate;
    }

    public Double setGeneralRate(Double generalRate) {
        this.generalRate = generalRate;
        return generalRate;
    }

    public void addReply(Comment reply) {
        this.replies.add(reply);
        reply.setParentComment(this);
    }
}