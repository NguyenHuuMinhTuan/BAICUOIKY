package Three_layer.Model;

import java.time.LocalDate;

public class TextBook extends Book {
    private String condition;

    public TextBook(String id, LocalDate dateAdded, double price, int quantity, String publisher,double tax ,String condition) {
        super(id, dateAdded, price, quantity, publisher, tax);
        this.condition = condition;
    }

    
    public double calculateTotalPrice() {
        if ("mới".equalsIgnoreCase(condition)) {
            return getQuantity() * getPrice();
        } else if ("cũ".equalsIgnoreCase(condition)) {
            return getQuantity() * getPrice() * 0.5;
        }
        return 0;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "TextBook{" +
                "id='" + getId() + '\'' +
                ", dateAdded=" + getDateAdded() +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", publisher='" + getPublisher() + '\'' +
                ", tax '" + getTax() + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
