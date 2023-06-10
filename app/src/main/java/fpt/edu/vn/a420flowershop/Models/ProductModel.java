package fpt.edu.vn.a420flowershop.Models;

public class ProductModel {
    String product_name, product_price, product_cat, product_img, product_stock, product_des, product_status;

    public ProductModel() {

    }

    public ProductModel(String product_name, String product_price, String product_cat, String product_img, String product_stock, String product_des, String product_status) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_cat = product_cat;
        this.product_img = product_img;
        this.product_stock = product_stock;
        this.product_des = product_des;
        this.product_status = product_status;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public void setProduct_cat(String product_cat) {
        this.product_cat = product_cat;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(String product_stock) {
        this.product_stock = product_stock;
    }

    public String getProduct_des() {
        return product_des;
    }

    public void setProduct_des(String product_des) {
        this.product_des = product_des;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }
}
