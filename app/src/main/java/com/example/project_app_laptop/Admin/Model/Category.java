package com.example.project_app_laptop.Admin.Model;

public class Category {
    String imgCategory;
    String nameCategory;

    public Category() {
    }

    public Category(String imgCategory, String nameCategory) {
        this.imgCategory = imgCategory;
        this.nameCategory = nameCategory;
    }

    public String getImgCategory() {
        return imgCategory;
    }

    public void setImgCategory(String imgCategory) {
        this.imgCategory = imgCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
