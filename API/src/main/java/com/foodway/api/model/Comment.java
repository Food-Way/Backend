package com.foodway.api.model;

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
    private String comment;
    //    private Rate rate;
//    private List<Tags> tagList;
//    private List<Costumer> listCostumer;
    private int upvotes;
    private List<String> images;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    @GeneratedValue(strategy = GenerationType.UUID)
    private Comment parentComment;
    @OneToMany(mappedBy = "parentComment")
    List<Comment> replies;

    public Comment() {
    }

    public Comment(UUID idPost) {
        this.idPost = idPost;
    }

    public Comment(int upvotes, String comment, List<String> images) {
        this.upvotes = upvotes;
        this.comment = comment;
//        this.tagList = tagList;
//        this.listCostumer = listCostumer;
        this.images = images;
//        this.rate = rate;
        this.replies = new ArrayList<>();
    }

    public Comment(RequestComment data) {
        this.comment = data.comment();
        this.upvotes = data.upvotes();
//        this.tagList = data.tagList();
//        this.listCostumer = data.listCostumer();
        this.images = data.images();
//        this.rate = data.rate();
        this.replies = new ArrayList<>();
    }

    public Comment(UUID idParent, RequestCommentChild data) {
        this.idParent = idParent;
        this.comment = data.comment();
        this.upvotes = data.upvotes();
//        this.tagList = data.tagList();
//        this.listCostumer = data.listCostumer();
        this.images = data.images();
//        this.rate = data.rate();
        this.replies = new ArrayList<>();
    }

    public void update(@NotNull Optional<?> optional) {
        RequestComment c = (RequestComment) optional.get();
        this.setcomment(c.comment());
        this.setUpvotes(c.upvotes());
//        this.setTagList(c.tagList());
//        this.setListCostumer(c.listCostumer());
        this.setImages(c.images());
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

    //    public List<Tags> getTagList() {
//        return tagList;
//    }
//
//    public void setTagList(List<Tags> tagList) {
//        this.tagList = tagList;
//    }
//    public List<Costumer> getListCostumer() {
//        return listCostumer;
//    }
//
//    public void setListCostumer(List<Costumer> listCostumer) {
//        this.listCostumer = listCostumer;
//    }
    public UUID getIdPost() {
        return idPost;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void addReply(Comment reply) {
        this.replies.add(reply);
        reply.setParentComment(this);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}