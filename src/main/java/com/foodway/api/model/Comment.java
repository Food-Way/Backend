package com.foodway.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;
import java.util.UUID;

@Entity(name = "tbComment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPost;
    private int upvotes;
    private String coment;
    //todo lista de tags
    private String tagList;
    private List<Costumer> listCostumer;
    private List<String> images;
    //private Rate rate;


    public Comment(UUID idPost) {
        this.idPost = idPost;
    }

    public Comment(int upvotes, String coment, String tagList, List<Costumer> listCostumer, List<String> images) {
        this.upvotes = upvotes;
        this.coment = coment;
        this.tagList = tagList;
        this.listCostumer = listCostumer;
        this.images = images;
        //colocar o rate
//        this.rate = rate;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getTagList() {
        return tagList;
    }

    public void setTagList(String tagList) {
        this.tagList = tagList;
    }

    public List<Costumer> getListCostumer() {
        return listCostumer;
    }

    public void setListCostumer(List<Costumer> listCostumer) {
        this.listCostumer = listCostumer;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
