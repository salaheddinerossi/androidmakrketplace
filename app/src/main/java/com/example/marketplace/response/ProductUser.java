package com.example.marketplace.response;

public class ProductUser {
    private Long id;

    private String image;

    private Double price;

    private String title;

    private String description;

    private String name;

    private String address;

    private String email;

    private Long phone;

    public ProductUser(Long id, String image, Double price, String title, String description, String name, String address, String email, Long phone) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.title = title;
        this.description = description;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public ProductUser() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ProductUser{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }
}
