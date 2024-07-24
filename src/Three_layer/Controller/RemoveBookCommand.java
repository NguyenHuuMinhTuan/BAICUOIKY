package Three_layer.Controller;

import Three_layer.Model.Book;
import Three_layer.Model.BookRepository;

public class RemoveBookCommand implements Command {
    private BookRepository bookRepository;
    private Book book;

    public RemoveBookCommand(BookRepository bookRepository, Book book) {
        this.bookRepository = bookRepository;
        this.book = book;
    }

    @Override
    public void execute() {
        if (book != null) {
            bookRepository.removeBook(book.getId());
        }
    }
}
