package ro.unitbv.springwebapp.dto;

import ro.unitbv.springwebapp.model.Product;

public class ProductResponse {
    private Integer id;
    private String name;
    private double price;
    private int stock;

    public ProductResponse() {}

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
    }

    public Integer getId() { return id; }

    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
