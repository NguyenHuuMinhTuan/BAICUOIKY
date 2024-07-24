package Three_layer.Persistance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Three_layer.Model.Book;
import Three_layer.Model.BookObserver;
import Three_layer.Model.ReferenceBook;
import Three_layer.Model.TextBook;

public class BookRepository {
    private List<Book> books = new ArrayList<>();
    private List<BookObserver> observers = new ArrayList<>();

    public BookRepository() {
        // Load books from the database when the repository is created
        loadBooksFromDatabase();
    }

    public void addBook(Book book) {
        books.add(book);
        notifyObservers();
        saveBookToDatabase(book);
    }

    public void removeBook(String id) {
        books.removeIf(book -> book.getId().equals(id));
        notifyObservers();
        deleteBookFromDatabase(id);
    }

    public void updateBook(Book oldBook, Book newBook) {
        int index = books.indexOf(oldBook);
        if (index != -1) {
            books.set(index, newBook);
            notifyObservers();
            updateBookInDatabase(newBook);
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

    private void loadBooksFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, ngayNhap, donGia, soLuong, nhaXuatBan, tinhTrang, thue, loaiSach, thanhTien FROM Sach");
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                String id = rs.getString("id");
                LocalDate dateAdded = rs.getDate("ngayNhap").toLocalDate();
                double price = rs.getDouble("donGia");
                int quantity = rs.getInt("soLuong");
                String publisher = rs.getString("nhaXuatBan");
                double tax = rs.getDouble("thue");
                String condition = rs.getString("tinhTrang");
                String bookType = rs.getString("loaiSach");
                double totalPrice = rs.getDouble("thanhTien");
    
                if ("Sách giáo khoa".equals(bookType)) {
                    TextBook textBook = new TextBook(id, dateAdded, price, quantity, publisher, tax, condition);
                    books.add(textBook);
                } else if ("Sách tham khảo".equals(bookType)) {
                    ReferenceBook referenceBook = new ReferenceBook(id, dateAdded, price, quantity, publisher, tax, tax);
                    books.add(referenceBook);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void saveBookToDatabase(Book book) {
        String sql = "INSERT INTO Sach (id, ngayNhap, donGia, soLuong, nhaXuatBan, tinhTrang, thue, loaiSach, thanhtien) VALUES ('MH01','2012-12-12', 23.500, 23, N'Kim Đồng', N'Mới', 0.2, N'Sách Giáo Khoa',220.800)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, book.getId());
            stmt.setDate(2, java.sql.Date.valueOf(book.getDateAdded()));
            stmt.setDouble(3, book.getPrice());
            stmt.setInt(4, book.getQuantity());
            stmt.setString(5, book.getPublisher());
            stmt.setDouble(6, book.getTax());
            stmt.setString(7, book instanceof TextBook ? ((TextBook) book).getCondition() : null);
            stmt.setString(8, book instanceof TextBook ? "Sách giáo khoa" : "Sách tham khảo");
    
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Delete a book from the database
    private void deleteBookFromDatabase(String id) {
        String sql = "DELETE FROM Sach WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateBookInDatabase(Book book) {
        String sql = "UPDATE Sach SET ngayNhap = ?, donGia = ?, soLuong = ?, nhaXuatBan = ?, thue = ?, tinhTrang = ?, loaiSach = ? WHERE id = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setDate(1, java.sql.Date.valueOf(book.getDateAdded()));
            stmt.setDouble(2, book.getPrice());
            stmt.setInt(3, book.getQuantity());
            stmt.setString(4, book.getPublisher());
            stmt.setDouble(5, book.getTax());
            stmt.setString(6, book instanceof TextBook ? ((TextBook) book).getCondition() : null);
            stmt.setString(7, book instanceof TextBook ? "Sách giáo khoa" : "Sách tham khảo");
            stmt.setString(8, book.getId());
    
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}