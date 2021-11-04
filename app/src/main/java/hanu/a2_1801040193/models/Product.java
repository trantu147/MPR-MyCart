package hanu.a2_1801040193.models;

public class Product {
    private long id;
    private String name;
    private String thumbnail;
    private double unitPrice;
    private int quantity;


    public Product(long id,String name, String thumbnail, double unitPrice, int quantity) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public Product(long id, String name, String thumbnail, double unitPrice) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.unitPrice = unitPrice;
    }

    public Product(String name, String thumbnail, double unitPrice, int quantity) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public long getId() {
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(){
        this.quantity++;
    }

    public boolean decreaseQuantity(){
        if(quantity> 0){
            this.quantity--;
            return true;
        }else{
            return false;
        }
    }

    public double getPrice(){
        return this.unitPrice*this.quantity;
    }

}
