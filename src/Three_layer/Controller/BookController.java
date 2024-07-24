package Three_layer.Controller;

import Three_layer.Model.Book;
import Three_layer.Model.BookRepository;

public class BookController {
    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book) {
        Command command = new AddBookCommand(bookRepository, book);
        command.execute();
    }

    public void removeBook(Book book) {
        Command command = new RemoveBookCommand(bookRepository, book);
        command.execute();
    }

    public void updateBook(Book oldBook, Book newBook) {
        Command command = new UpdateBookCommand(bookRepository, oldBook, newBook);
        command.execute();
    }

    public void searchBook(String keyword) {
        Command command = new SearchBookCommand(bookRepository, keyword);
        command.execute();
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }
}
