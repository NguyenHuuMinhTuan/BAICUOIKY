package Three_layer.Model;

import java.time.LocalDate;

public class ReferenceBook extends Book {
    private double result;

    public ReferenceBook(String id, LocalDate dateAdded, double price, int quantity, String publisher, double tax , double result) {
        super(id, dateAdded, price, quantity, publisher, tax);
        this.result = result;
    }

    
    public double calculateTotalPrice() {
        return getQuantity() * getPrice() + result;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ReferenceBook{" +
                "id='" + getId() + '\'' +
                ", dateAdded=" + getDateAdded() +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", publisher='" + getPublisher() + '\'' +
                ", tax='" + getTax() + '\'' +
                ", result=" + result +
                '}';
    }
}
