package com.example.anhbui.sportaccessoriesstore.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int cost;
    private String description;
    private byte[] image;
    private int idType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public Product(int id, String name, int cost, String description, byte[] image, int idType) {

        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.image = image;
        this.idType = idType;
    }
}
