package com.example.customlistview;

public class Contact {
    private int id;
    private String Images;
    private String name;
    private String phone;

    public Contact(int id, String images, String name, String phone) {
        this.id = id;
        this.Images = images;
        this.name = name;
        this.phone = phone;
    }

    public Contact (){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        this.Images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
