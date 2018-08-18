package com.example.anhbui.sportaccessoriesstore.Model;

public class Cart {
    private int id;
    private String name;
    private int cost;
    private byte[] image;
    private int countProduct;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(int countProduct) {
        this.countProduct = countProduct;
    }

    public Cart(int id, String name, int cost, byte[] image, int countProduct) {

        this.id = id;
        this.name = name;
        this.cost = cost;
        this.image = image;
        this.countProduct = countProduct;
    }
}
