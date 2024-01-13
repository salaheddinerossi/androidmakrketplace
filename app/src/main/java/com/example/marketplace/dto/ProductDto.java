package com.example.marketplace.dto;

public class ProductDto {
    private String image;
    private Double price;
    private String title;
    private String description;
    private Long userId;

    public ProductDto() {
    }

    public ProductDto(String image, Double price, String title, String description, Long userId) {
        this.image = image;
        this.price = price;
        this.title = title;
        this.description = description;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "image='" + image + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                '}';
    }
}
