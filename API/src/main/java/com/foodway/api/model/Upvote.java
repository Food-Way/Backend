package com.foodway.api.model;

import com.foodway.api.record.RequestUpvote;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Upvote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idUpvote;
    private UUID idCustomer;
    private UUID idComment;

    public Upvote() {
    }

    public Upvote(UUID idCustomer, UUID idComment) {
        this.idCustomer = idCustomer;
        this.idComment = idComment;
    }

    public Upvote(RequestUpvote data) {
        this.idCustomer = data.idCustomer();
        this.idComment = data.idComment();
    }

    public void update(RequestUpvote data) {
        this.idCustomer = data.idCustomer();
        this.idComment = data.idComment();
    }

    public int getIdUpvote() {
        return idUpvote;
    }

    public UUID getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(UUID idCustomer) {
        this.idCustomer = idCustomer;
    }

    public UUID getIdComment() {
        return idComment;
    }

    public void setIdComment(UUID idComment) {
        this.idComment = idComment;
    }
}
