package com.foodway.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import com.foodway.api.record.UpdateEstablishmentMobileAccount;
import com.foodway.api.record.UpdateEstablishmentMobileProfile;
import com.foodway.api.record.UpdateEstablishmentPersonal;
import com.foodway.api.record.UpdateEstablishmentProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Table(name = "tbEstablishment")
@Entity(name = "establishment")
public class Establishment extends User {
    @Column(length = 75)
    private String establishmentName;
    @Column(length = 255)
    private String description;
    @PositiveOrZero
    @Max(value = 5)
    @Min(value = 0)
    private Double generalRate;
    @PositiveOrZero
    @Max(value = 5)
    @Min(value = 0)
    private Double ambientRate;
    @PositiveOrZero
    @Max(value = 5)
    @Min(value = 0)
    private Double serviceRate;
    @PositiveOrZero
    @Max(value = 5)
    @Min(value = 0)
    private Double foodRate;
    @Column(length = 14, unique = true)
    private String cnpj;
    private String phone;
    @ManyToMany
    @JoinTable(
            name = "establishment_tags",
            joinColumns = @JoinColumn(name = "establishment_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tags> tags = new HashSet<Tags>();
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @JsonIgnore
    @ManyToMany
    private List<Rate> rates;
    @JsonIgnore
    @OneToMany
    private List<Comment> postList;
    @OneToMany(mappedBy = "establishment", cascade = CascadeType.REMOVE)
    private List<Product> menu;

    public Establishment() {
        this.rates = new ArrayList<>();
    }

    public Establishment(RequestUserEstablishment establishment) {
        super(establishment.name(), establishment.email(), establishment.password(), establishment.typeUser(), establishment.profilePhoto(), establishment.profileHeaderImg(), establishment.culinary());
        this.establishmentName = establishment.establishmentName();
        this.description = establishment.description();
        this.phone = establishment.phone();
        this.address = new Address(establishment.address().cep(), establishment.address().number(), establishment.address().complement(), establishment.address().street(), establishment.address().neighborhood(), establishment.address().city(), establishment.address().state());
        this.cnpj = establishment.cnpj();
        this.rates = new ArrayList<>();
        this.postList = new ArrayList<>();
        this.tags = new HashSet<>();
    }

    public Establishment(String name, String email, String password, ETypeUser typeUser, String profilePhoto, String profileHeaderImg,
                         List<Culinary> culinary, String establishmentName, String description, String cnpj, Address address) {
        super(name, email, password, typeUser, profilePhoto, profileHeaderImg, culinary);
        this.establishmentName = establishmentName;
        this.description = description;
        this.cnpj = cnpj;
        this.address = address;
        this.postList = new ArrayList<>();
        this.rates = new ArrayList<>();
        this.tags = new HashSet<>();
    }

    @Override
    public void update(Optional<?> optional) {
        super.setName(((UpdateEstablishmentData) optional.get()).name());
        super.setEmail(((UpdateEstablishmentData) optional.get()).email());
        super.setPassword(((UpdateEstablishmentData) optional.get()).password());
        super.setTypeUser(((UpdateEstablishmentData) optional.get()).typeUser());
        super.setProfilePhoto(((UpdateEstablishmentData) optional.get()).profilePhoto());
        this.establishmentName = ((UpdateEstablishmentData) optional.get()).establishmentName();
        this.description = ((UpdateEstablishmentData) optional.get()).description();
        this.address.setCep(((UpdateEstablishmentData) optional.get()).address().cep());
        this.address.setNumber(((UpdateEstablishmentData) optional.get()).address().number());
        this.address.setComplement(((UpdateEstablishmentData) optional.get()).address().complement());
        this.address.setStreet(((UpdateEstablishmentData) optional.get()).address().street());
        this.address.setNeighborhood(((UpdateEstablishmentData) optional.get()).address().neighborhood());
        this.address.setCity(((UpdateEstablishmentData) optional.get()).address().city());
        this.address.setState(((UpdateEstablishmentData) optional.get()).address().state());
        this.cnpj = ((UpdateEstablishmentData) optional.get()).cnpj();
        this.postList = new ArrayList<>();
        this.rates = new ArrayList<>();
        this.tags = new HashSet<>();
    }

   
    public Set<Tags> getTags() {
        return tags;
    }

    public void addTags(Tags tag){
        tags.add(tag);
    }

    public void setTags(Set<Tags> tags) {
        this.tags = tags;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getGeneralRate() {
        return generalRate;
    }

    public void setGeneralRate(Double generalRate) {
        this.generalRate = generalRate;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void addRate(Rate rate) {
        this.rates.add(rate);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Comment> getPostList() {
        return postList;
    }

    public void setPostList(List<Comment> postList) {
        this.postList = postList;
    }

    public void addComment(Comment comment) {
        this.postList.add(comment);
    }

    public Double getAmbientRate() {
        return ambientRate;
    }

    public Double getServiceRate() {
        return serviceRate;
    }

    public Double getFoodRate() {
        return foodRate;
    }

    public List<Product> getMenu() {
        return menu;
    }

    public void setMenu(List<Product> menu) {
        this.menu = menu;
    }

    public void addProduct(Product product) {
        this.menu.add(product);
    }

    public void setRates(Map<String, Double> map) {
        String[] rateKeys = { "AMBIENT", "SERVICE", "FOOD" };
        int count = 0;
        Double totalRate = 0.0;

        for (String key : rateKeys) {
            Double rateValue = map.get(key);
            if (rateValue != null) {
                count++;
                totalRate += rateValue;
            } else {
                rateValue = 0.0;
            }

            if ("AMBIENT".equals(key)) {
                this.ambientRate = rateValue;
            } else if ("SERVICE".equals(key)) {
                this.serviceRate = rateValue;
            } else if ("FOOD".equals(key)) {
                this.foodRate = rateValue;
            }
        }

        this.generalRate = (count > 0) ? (totalRate / count) : 0.0;
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public void updateProfileEstablishment(Optional<UpdateEstablishmentProfile> establishment) {
        if(establishment.get().profilePhoto() != null && !establishment.get().profilePhoto().isEmpty()) {
            super.setProfilePhoto(establishment.get().profilePhoto());
        }
        if(establishment.get().profileHeaderImg() != null && !establishment.get().profileHeaderImg().isEmpty()) {
            super.setProfileHeaderImg(establishment.get().profileHeaderImg());
        }

    }

    public void updatePersonalEstablishment(Optional<UpdateEstablishmentPersonal> establishment) {
        if (establishment.get().name() != null && !establishment.get().name().isEmpty()) {
            super.setName(establishment.get().name());
        }
        if(establishment.get().name() != null && !establishment.get().name().isEmpty()){
            setEstablishmentName(establishment.get().establishmentName());
        }
        if(establishment.get().phone() != null && !establishment.get().phone().isEmpty()) {
            setPhone(establishment.get().phone());
        }
        if(establishment.get().description() != null && !establishment.get().description().isEmpty()) {
            setDescription(establishment.get().description());
        }
        if (establishment.get().emailNew() != null && !establishment.get().emailNew().isEmpty()) {
            super.setEmail(establishment.get().emailNew());
        }
        if (establishment.get().passwordNew() != null && !establishment.get().passwordNew().isEmpty()) {
            super.setPassword(encodePassword(establishment.get().passwordNew()));
        }
    }
    public void updateProfileEstablishmentMobile(UpdateEstablishmentMobileProfile establishmentProfile){
        if(establishmentProfile.profilePhoto() != null && !establishmentProfile.profilePhoto().isEmpty()) {
            super.setProfilePhoto(establishmentProfile.profilePhoto());
        }
        if(establishmentProfile.establishmentName() != null && !establishmentProfile.establishmentName().isEmpty()){
            setEstablishmentName(establishmentProfile.establishmentName());
        }
        if(establishmentProfile.phone() != null && !establishmentProfile.phone().isEmpty()) {
            setPhone(establishmentProfile.phone());
        }
        if(establishmentProfile.description() != null && !establishmentProfile.description().isEmpty()) {
            setDescription(establishmentProfile.description());
        }
    }
    public void updateAccountEstablishmentMobile(UpdateEstablishmentMobileAccount establishment){
        if (establishment.name() != null && !establishment.name().isEmpty()) {
            super.setName(establishment.name());
        }
        if (establishment.email() != null && !establishment.email().isEmpty()) {
            super.setEmail(establishment.email());
        }
        if (establishment.newEmail() != null && !establishment.newEmail().isEmpty()) {
            super.setEmail(establishment.newEmail());
        }
        if (establishment.password() != null && !establishment.password().isEmpty()) {
            super.setPassword(encodePassword(establishment.password()));
        }
        if (establishment.newPassword() != null && !establishment.newPassword().isEmpty()) {
            super.setPassword(encodePassword(establishment.newPassword()));
        }
        if (establishment.cep() != null && !establishment.cep().isEmpty()) {
            this.address.setCep(establishment.cep());
        }
    }

}
