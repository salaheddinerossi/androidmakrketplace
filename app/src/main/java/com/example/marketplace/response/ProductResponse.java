package com.example.marketplace.response;

public class ProductResponse {

    private Long id;

    private String image;

    private Double price;

    private String title;

    private String description;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String image, Double price, String title, String description) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
