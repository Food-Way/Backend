package com.foodway.api.model;

import com.foodway.api.record.RequestComment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import javax.swing.text.html.HTML;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity(name = "tbComment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPost;
    private int upvotes;
    private String coment;
//    private List<Tags> tagList;
//    private List<Costumer> listCostumer;
    private List<String> images;
//    private Rate rate;
    @ManyToOne
    private Establishment establishment;

    public Comment() {
    }

    public Comment(UUID idPost) {
        this.idPost = idPost;
    }

    public Comment(int upvotes, String coment, List<String> images) {
        this.upvotes = upvotes;
        this.coment = coment;
//        this.tagList = tagList;
//        this.listCostumer = listCostumer;
        this.images = images;
//        this.rate = rate;
    }

    public Comment(RequestComment data){
        this.coment = data.coment();
        this.upvotes = data.upvotes();
//        this.tagList = data.tagList();
//        this.listCostumer = data.listCostumer();
        this.images = data.images();
//        this.rate = data.rate();
    }

    public void update(@NotNull Optional<?> optional) {
        RequestComment c = (RequestComment) optional.get();
        this.setComent(c.coment());
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

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
