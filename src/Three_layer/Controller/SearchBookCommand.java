package Three_layer.Controller;

import Three_layer.Model.Book;
import Three_layer.Model.BookRepository;

public class SearchBookCommand implements Command {
    private BookRepository bookRepository;
    private String keyword;

    public SearchBookCommand(BookRepository bookRepository, String keyword) {
        this.bookRepository = bookRepository;
        this.keyword = keyword;
    }

    @Override
    public void execute() {
        StringBuilder output = new StringBuilder();
        for (Book book : bookRepository.getBooks()) {
            if (book.getId().contains(keyword)) {
                output.append(book.toString()).append("\n");
            }
        }
        // Output the result or handle it as needed
        System.out.println(output.toString());
    }
}
