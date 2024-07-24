package Three_layer.Controller;

import Three_layer.Model.Book;
import Three_layer.Model.BookRepository;


public class AddBookCommand implements Command {
    private BookRepository bookRepository;
    private Book book;

    public AddBookCommand(BookRepository bookRepository, Book book) {
        this.bookRepository = bookRepository;
        this.book = book;
    }

    @Override
    public void execute() {
        bookRepository.addBook(book);
    }
}
