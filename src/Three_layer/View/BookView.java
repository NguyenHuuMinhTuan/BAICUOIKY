package Three_layer.View;

import Three_layer.Controller.BookController;
import Three_layer.Model.Book;
import Three_layer.Model.BookRepository;
import Three_layer.Model.ReferenceBook;
import Three_layer.Model.TextBook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BookView extends JFrame implements ActionListener {
    private BookController bookController;
    private BookRepository bookRepository;
    private JTextField txtId, txtDateAdded, txtPrice, txtQuantity, txtPublisher, txtConditionOrTax, txtTax;
    private JComboBox<String> cmbBookType, cbStatusBook;
    private JTextArea txtAreaOutput;
    private JButton btnAdd, btnRemove, btnUpdate, btnSearch, btnTotalTextBookPrice, btnTotalReferenceBookPrice,
            btnAvgReferenceBookPrice, btnTextBooksByPublisher;
    private JTextField txtTotalTextBookPrice, txtTotalReferenceBookPrice, txtAvgReferenceBookPrice, txtSearchPublisher;
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public BookView(BookController bookController, BookRepository bookRepository) {
        this.bookController = bookController;
        this.bookRepository = bookRepository;

        setTitle("Library Management");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        loadAllBooks();
        
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        txtId = new JTextField(15);
        txtSearchPublisher = new JTextField(15);
        txtTax = new JTextField(15);
        txtDateAdded = new JTextField(15);
        txtPrice = new JTextField(15);
        txtQuantity = new JTextField(15);
        txtPublisher = new JTextField(15);
        txtConditionOrTax = new JTextField(15);
        cmbBookType = new JComboBox<>(new String[] { "Sách giáo khoa", "Sách tham khảo" });
        cbStatusBook = new JComboBox<>(new String[] { "Mới", "Cũ" });

        inputPanel.add(new JLabel("Mã sách:"));
        inputPanel.add(txtId);
        inputPanel.add(new JLabel("Ngày nhập (yyyy-mm-dd):"));
        inputPanel.add(txtDateAdded);
        inputPanel.add(new JLabel("Đơn giá:"));
        inputPanel.add(txtPrice);
        inputPanel.add(new JLabel("Số lượng:"));
        inputPanel.add(txtQuantity);
        inputPanel.add(new JLabel("Nhà xuất bản:"));
        inputPanel.add(txtPublisher);
        inputPanel.add(new JLabel("Tình trạng "));
        inputPanel.add(cbStatusBook);
        inputPanel.add(new JLabel("Thuế :"));
        inputPanel.add(txtTax);
        inputPanel.add(new JLabel("Loại sách:"));
        inputPanel.add(cmbBookType);

        // Table to display books
        String[] columnNames = { "Mã sách", "Ngày nhập", "Đơn giá", "Số lượng", "Nhà xuất bản", "Tình trạng", "Thuế",
                "Loại sách", "Thành tiền" };
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(bookTable);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        btnAdd = new JButton("Thêm sách");
        btnRemove = new JButton("Xóa sách");
        btnUpdate = new JButton("Sửa sách");
        btnSearch = new JButton("Tìm sách");

        btnAdd.addActionListener(this);
        btnRemove.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnSearch.addActionListener(this);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnSearch);

        JPanel totalPanel = new JPanel(new GridLayout(4, 4, 10, 10)); // Tạo GridLayout 4 hàng và 4 cột, với khoảng cách 10 pixel

        btnTotalTextBookPrice = new JButton("Tính tổng tiền sách giáo khoa");
        btnTotalReferenceBookPrice = new JButton("Tính tổng tiền sách tham khảo");
        btnAvgReferenceBookPrice = new JButton("Tính tổng tiền trung bình sách tham khảo");
        btnTextBooksByPublisher = new JButton("Xuất ra sách của nhà xuất bản");

        txtTotalTextBookPrice = new JTextField(15);
        txtTotalTextBookPrice.setEditable(false);
        txtTotalReferenceBookPrice = new JTextField(15);
        txtTotalReferenceBookPrice.setEditable(false);
        txtAvgReferenceBookPrice = new JTextField(15);
        txtAvgReferenceBookPrice.setEditable(false);
        txtSearchPublisher = new JTextField(15);

        btnTotalTextBookPrice.addActionListener(this);
        btnTotalReferenceBookPrice.addActionListener(this);
        btnAvgReferenceBookPrice.addActionListener(this);
        btnTextBooksByPublisher.addActionListener(this);

        totalPanel.add(new JLabel("Thanh tìm kiếm:"));
        totalPanel.add(txtSearchPublisher);
        totalPanel.add(btnTextBooksByPublisher);
        totalPanel.add(new JLabel(""));

        totalPanel.add(new JLabel("Tổng giá sách giáo khoa:"));
        totalPanel.add(txtTotalTextBookPrice);
        totalPanel.add(btnTotalTextBookPrice);
        totalPanel.add(new JLabel(""));
        totalPanel.add(new JLabel("Tổng giá sách tham khảo:"));
        totalPanel.add(txtTotalReferenceBookPrice);
        totalPanel.add(btnTotalReferenceBookPrice);
        totalPanel.add(new JLabel(""));
        totalPanel.add(new JLabel("Tổng trung bình cộng sách tham khảo:"));
        totalPanel.add(txtAvgReferenceBookPrice);
        totalPanel.add(btnAvgReferenceBookPrice);
        totalPanel.add(new JLabel(""));

        txtAreaOutput = new JTextArea(10, 30);
        txtAreaOutput.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(txtAreaOutput);

        // Layout setup
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.NORTH);
        southPanel.add(totalPanel, BorderLayout.CENTER);
        southPanel.add(outputScrollPane, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
        
        loadAllBooks();  // Load all books from database when the UI is initialized
    }

    private void loadAllBooks() {
        List<Book> books = bookRepository.getBooks();
        for (Book book : books) {
            addRowToTable(book);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String id = txtId.getText();

        LocalDate dateAdded = LocalDate.parse(txtDateAdded.getText());

        double price = Double.parseDouble(txtPrice.getText());

        int quantity = Integer.parseInt(txtQuantity.getText());

        String publisher = txtPublisher.getText();

        double tax = Double.parseDouble(txtTax.getText());

        String condition = (String) cbStatusBook.getSelectedItem();

        String bookType = (String) cmbBookType.getSelectedItem();

        if (e.getSource() == btnAdd) {
            if ("Sách giáo khoa".equals(bookType)) {
                TextBook textBook = new TextBook(id, dateAdded, price, quantity, publisher, tax, condition);
                bookRepository.addBook(textBook);
                addRowToTable(textBook);
            } else if ("Sách tham khảo".equals(bookType)) {
                ReferenceBook referenceBook = new ReferenceBook(id, dateAdded, price, quantity, publisher, tax, tax);
                bookRepository.addBook(referenceBook);
                addRowToTable(referenceBook);
            }
            txtAreaOutput.setText("Thêm sách thành công.");
        } else if (e.getSource() == btnRemove) {
            bookRepository.removeBook(id);
            removeRowFromTable(id);
            txtAreaOutput.setText("Xóa sách thành công.");
        } else if (e.getSource() == btnUpdate) {
            Book bookToUpdate = null;
            if ("Sách giáo khoa".equals(bookType)) {
                bookToUpdate = new TextBook(id, dateAdded, price, quantity, publisher, tax, condition);
            } else if ("Sách tham khảo".equals(bookType)) {
                bookToUpdate = new ReferenceBook(id, dateAdded, price, quantity, publisher, tax, tax);
            }
            if (bookToUpdate != null) {
                bookRepository.updateBook(bookToUpdate, bookToUpdate);
                updateRowInTable(bookToUpdate);
                txtAreaOutput.setText("Cập nhật sách thành công.");
            }
        } else if (e.getSource() == btnSearch) {
            // You can add code for searching here, if needed.
        }
    }

    private void addRowToTable(Book book) {
        Object[] rowData = {
                book.getId(),
                book.getDateAdded(),
                book.getPrice(),
                book.getQuantity(),
                book.getPublisher(),
                book instanceof TextBook ? ((TextBook) book).getCondition() : ((ReferenceBook) book).getTax(),
                book.getTax(),
                book instanceof TextBook ? "Sách giáo khoa" : "Sách tham khảo",
                book.calculateTotalPrice()
        };
        tableModel.addRow(rowData);
    }

    private void removeRowFromTable(String id) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(id)) {
                tableModel.removeRow(i);
                break;
            }
        }
    }

    private void updateRowInTable(Book newBook) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(newBook.getId())) {
                tableModel.setValueAt(newBook.getId(), i, 0);
                tableModel.setValueAt(newBook.getDateAdded(), i, 1);
                tableModel.setValueAt(newBook.getPrice(), i, 2);
                tableModel.setValueAt(newBook.getQuantity(), i, 3);
                tableModel.setValueAt(newBook.getPublisher(), i, 4);
                tableModel.setValueAt(newBook.getTax(), i, 5);
                tableModel.setValueAt(
                        newBook instanceof TextBook ? ((TextBook) newBook).getCondition()
                                : ((ReferenceBook) newBook).getTax(),
                        i, 6);
                tableModel.setValueAt(
                        newBook instanceof TextBook ? "Sách giáo khoa" : "Sách tham khảo",
                        i, 7);
                tableModel.setValueAt(newBook.calculateTotalPrice(), i, 8);
                break;
            }
        }
    }
}
