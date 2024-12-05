import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class LibraryGUI extends Application {

    private Library library = Library.getLibrary();
    private Transaction transaction = Transaction.getTransaction();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        // Main Layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        // Buttons
        Button addBookButton = new Button("Add Book");
        Button addMemberButton = new Button("Add Member");
        Button borrowBookButton = new Button("Borrow Book");
        Button returnBookButton = new Button("Return Book");
        Button viewTransactionHistoryButton = new Button("View Transaction History");

        // Event Handlers
        addBookButton.setOnAction(e -> addBookDialog());
        addMemberButton.setOnAction(e -> addMemberDialog());
        borrowBookButton.setOnAction(e -> borrowBookDialog());
        returnBookButton.setOnAction(e -> returnBookDialog());
        viewTransactionHistoryButton.setOnAction(e -> showTransactionHistoryDialog());

        // Add buttons to layout
        mainLayout.getChildren().addAll(
                addBookButton,
                addMemberButton,
                borrowBookButton,
                returnBookButton,
                viewTransactionHistoryButton
        );

        primaryStage.setScene(new Scene(mainLayout, 400, 300));
        primaryStage.show();
    }

    private void addBookDialog() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Prompt for book ID
            System.out.print("Enter book ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Prompt for book title
            System.out.print("Enter book title: ");
            String title = scanner.nextLine();

            // Create a new book object with the provided information
            Book newBook = new Book(id, title);

            // Attempt to add the book to the library
            if (library.addBook(newBook)) {
                System.out.println("Book added to the library successfully.");
            } else {
                System.out.println("Failed to add book. Duplicate ID or other issue.");
            }
        } catch (Exception e) {
            // Catch and handle any exception
            System.out.println("An error occurred while adding the book: " + e.getMessage());
        }
    }


    private void addMemberDialog() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Prompt for member ID
            System.out.print("Enter member ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Prompt for member name
            System.out.print("Enter member name: ");
            String name = scanner.nextLine();

            // Create a new member object with the provided information
            Member newMember = new Member(id, name);

            // Attempt to add the member to the library
            if (library.addMember(newMember)) {
                System.out.println("Member added successfully.");
            } else {
                System.out.println("Failed to add member. Duplicate ID or other issue.");
            }
        } catch (Exception e) {
            // Catch and handle any exception
            System.out.println("An error occurred while adding the member: " + e.getMessage());
        }
    }


    private void borrowBookDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Borrow Book");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        ComboBox<Book> bookDropdown = new ComboBox<>();
        bookDropdown.getItems().addAll(library.getBooks());

        ComboBox<Member> memberDropdown = new ComboBox<>();
        memberDropdown.getItems().addAll(library.getMembers());

        Button borrowButton = new Button("Borrow");

        borrowButton.setOnAction(e -> {
            Book selectedBook = bookDropdown.getValue();
            Member selectedMember = memberDropdown.getValue();
            if (selectedBook != null && selectedMember != null) {
                if (transaction.borrowBook(selectedBook, selectedMember)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book borrowed successfully!");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Book is not available.");
                    alert.show();
                }
                dialog.close();
            }
        });

        layout.getChildren().addAll(new Label("Select Book"), bookDropdown, new Label("Select Member"), memberDropdown, borrowButton);
        dialog.setScene(new Scene(layout, 300, 200));
        dialog.show();
    }

    private void returnBookDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Return Book");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        ComboBox<Book> bookDropdown = new ComboBox<>();
        ComboBox<Member> memberDropdown = new ComboBox<>();
        memberDropdown.getItems().addAll(library.getMembers());

        Button returnButton = new Button("Return");

        memberDropdown.setOnAction(e -> {
            Member selectedMember = memberDropdown.getValue();
            if (selectedMember != null) {
                bookDropdown.getItems().clear();
                bookDropdown.getItems().addAll(selectedMember.getBorrowedBooks());
            }
        });

        returnButton.setOnAction(e -> {
            Book selectedBook = bookDropdown.getValue();
            Member selectedMember = memberDropdown.getValue();
            if (selectedBook != null && selectedMember != null) {
                transaction.returnBook(selectedBook, selectedMember);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book returned successfully!");
                alert.show();
                dialog.close();
            }
        });

        layout.getChildren().addAll(new Label("Select Member"), memberDropdown, new Label("Select Book"), bookDropdown, returnButton);
        dialog.setScene(new Scene(layout, 300, 200));
        dialog.show();
    }

    private void showTransactionHistoryDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Transaction History");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextArea transactionHistoryArea = new TextArea();
        transactionHistoryArea.setEditable(false);

        // Read the transaction history from the file
        File file = new File("transactions.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder history = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    history.append(line).append("\n");
                }
                transactionHistoryArea.setText(history.toString());
            } catch (IOException e) {
                transactionHistoryArea.setText("An error occurred while reading the transaction history.");
            }
        } else {
            transactionHistoryArea.setText("No transaction history found.");
        }

        layout.getChildren().addAll(new Label("Transaction History"), transactionHistoryArea);
        dialog.setScene(new Scene(layout, 400, 300));
        dialog.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
