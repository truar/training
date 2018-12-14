package com.zenika.zenikatas.bootcamp.data;

public class Article {

    private int price;
    private int quantity;

    public Article(int price) {
        this(price, 1);
    }

    public Article(int price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public int getTotal() {
        return price * quantity;
    }

}
