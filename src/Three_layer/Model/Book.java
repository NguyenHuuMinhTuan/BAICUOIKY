package Three_layer.Model;

import java.time.LocalDate;

public abstract class Book {
    private String id;
    private LocalDate dateAdded;
    private double price;
    private int quantity;
    private String publisher;
    private double tax;

    public Book(String id, LocalDate dateAdded, double price, int quantity, String publisher, double tax) {
        this.id = id;
        this.dateAdded = dateAdded;
        this.price = price;
        this.quantity = quantity;
        this.publisher = publisher;
        this.tax = tax;
    }

    public abstract double calculateTotalPrice();

    // Getters and setters

    public String getId() {
        return id;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPublisher() {
        return publisher;
    }

    public double getTax() {
        return tax;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", dateAdded=" + dateAdded +
                ", price=" + price +
                ", quantity=" + quantity +
                ", publisher='" + publisher +
                ", tax=" + tax + '\'' +
                '}';
    }
}
