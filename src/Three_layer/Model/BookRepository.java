package Three_layer.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepository {
    private List<Book> books = new ArrayList<>();
    private List<BookObserver> observers = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        notifyObservers();
    }

    public void removeBook(String id) {
        books.removeIf(book -> book.getId().equals(id));
        notifyObservers();
    }

    public void updateBook(Book oldBook, Book newBook) {
        int index = books.indexOf(oldBook);
        if (index != -1) {
            books.set(index, newBook);
            notifyObservers();
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<TextBook> getTextBooksByPublisher(String publisher) {
        return books.stream()
                .filter(book -> book instanceof TextBook && book.getPublisher().equalsIgnoreCase(publisher))
                .map(book -> (TextBook) book)
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByPublisher(String publisher) {
        return books.stream()
                .filter(book -> book.getPublisher().equalsIgnoreCase(publisher))
                .collect(Collectors.toList());
    }

    public double calculateTotalPriceOfTextBooks() {
        return books.stream()
                .filter(book -> book instanceof TextBook)
                .mapToDouble(Book::calculateTotalPrice)
                .sum();
    }

    public double calculateTotalPriceOfReferenceBooks() {
        return books.stream()
                .filter(book -> book instanceof ReferenceBook)
                .mapToDouble(Book::calculateTotalPrice)
                .sum();
    }

    public double calculateAveragePriceOfReferenceBooks() {
        return books.stream()
                .filter(book -> book instanceof ReferenceBook)
                .mapToDouble(Book::getPrice)
                .average()
                .orElse(0);
    }

    public void addObserver(BookObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BookObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (BookObserver observer : observers) {
            observer.update();
        }
    }
}
