package com.foodway.api.model;

import com.foodway.api.record.RequestUpvote;
import jakarta.persistence.*;
import java.util.UUID;

@Table(name = "tbUpvote")
@Entity(name = "upvote")
public class Upvote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idUpvote;
    private UUID idCustomer;
    private UUID idEstablishment;
    private UUID idComment;

    public Upvote() {
    }

    public Upvote(UUID idCustomer, UUID idComment) {
        this.idCustomer = idCustomer;
        this.idComment = idComment;
    }

    public Upvote(RequestUpvote data) {
        this.idCustomer = data.idCustomer();
        this.idEstablishment = data.idEstablishment();
        this.idComment = data.idComment();
    }

    public void update(RequestUpvote data) {
        this.idCustomer = data.idCustomer();
        this.idEstablishment = data.idEstablishment();
        this.idComment = data.idComment();
    }

    public long getIdUpvote() {
        return idUpvote;
    }

    public UUID getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(UUID idCustomer) {
        this.idCustomer = idCustomer;
    }

    public UUID getIdEstablishment() {
        return idEstablishment;
    }

    public void setIdEstablishment(UUID idEstablishment) {
        this.idEstablishment = idEstablishment;
    }

    public UUID getIdComment() {
        return idComment;
    }

    public void setIdComment(UUID idComment) {
        this.idComment = idComment;
    }

    public void setIdUpvote(long idUpvote) {
        this.idUpvote = idUpvote;
    }
}
