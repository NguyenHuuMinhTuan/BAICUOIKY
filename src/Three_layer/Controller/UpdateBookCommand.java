package Three_layer.Controller;

import Three_layer.Model.Book;
import Three_layer.Model.BookRepository;

public class UpdateBookCommand implements Command {
    private BookRepository bookRepository;
    private Book oldBook;
    private Book newBook;

    public UpdateBookCommand(BookRepository bookRepository, Book oldBook, Book newBook) {
        this.bookRepository = bookRepository;
        this.oldBook = oldBook;
        this.newBook = newBook;
    }

    @Override
    public void execute() {
        bookRepository.updateBook(oldBook, newBook);
    }
}
