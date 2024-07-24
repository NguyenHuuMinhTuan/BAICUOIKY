import java.sql.Connection;
import java.sql.SQLException;

import Three_layer.Controller.BookController;
import Three_layer.Model.BookRepository;
import Three_layer.Persistance.DatabaseConnection;
import Three_layer.View.BookView;

public class Main {
    public static void main(String[] args) {

        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thành công!");
                connection.close(); 
            } else {
                System.out.println("Không thể kết nối tới cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối tới cơ sở dữ liệu: " + e.getMessage());
        }

     BookRepository bookRepository = new BookRepository();
    BookController bookController = new BookController(bookRepository);
        
      new BookView(bookController,bookRepository);

         
    }
}