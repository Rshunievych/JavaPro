package com.my.restaurant;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name="Menu.all", query = "SELECT m FROM Menu m"),
        @NamedQuery(name="Menu.discount", query = "SELECT m FROM Menu m WHERE m.discount = :discount")
})
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double weight;

    private String discount;

    public Menu() {}

    public Menu(String name, double price, double weight, String discount) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "id:" + id + " " + name + ", price = " + price + ", weight = " + weight + ", discount=" + discount;
    }

}