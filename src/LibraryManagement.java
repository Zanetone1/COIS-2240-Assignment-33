import java.util.Scanner;

public class LibraryManagement {
    private Library library = new Library();

    public static void main(String[] args) {
        new LibraryManagement().run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("===========================");
            System.out.println("Library Management System");
            System.out.println("1. Add Member");
            System.out.println("2. Add Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Borrowed Books");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.println("===========================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMember(scanner);
                    break;
                case 2:
                    addBook(scanner);
                    break;
                case 3:
                    borrowBook(scanner);
                    break;
                case 4:
                    returnBook(scanner);
                    break;
                case 5:
                    viewBorrowedBooks(scanner);
                    break;
                case 6:
                    viewTransactionHistory();
                    break;
                case 7:
                    System.out.println("Exiting. Good Bye..");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void addMember(Scanner scanner) {
        System.out.print("Enter member ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter member name: ");
        String name = scanner.next();
        scanner.nextLine();

        Member newMember = new Member(id, name);
        if (library.addMember(newMember)) {
            System.out.println("Member added successfully.");
        } else {
            System.out.println("Failed to add member. Duplicate ID.");
        }
    }

    private void addBook(Scanner scanner) {
        System.out.print("Enter book ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter book title: ");
        String title = scanner.next();
        scanner.nextLine();

        try {
	        Book newBook = new Book(id, title);
	        if (library.addBook(newBook)) {
	            System.out.println("Book added to library successfully.");
	        } else {
	            System.out.println("Failed to add book. Duplicate ID.");
	        }
        }
        catch(Exception ex) {}
    }

    private void borrowBook(Scanner scanner) {
        System.out.println("\n--- Available Members ---");
        for (Member member : library.getMembers()) {
            System.out.println(member.getId() + ". " + member.getName());
        }

        System.out.print("Enter member ID: ");
        int memberId = scanner.nextInt();

        System.out.println("\n--- Available Books ---");
        for (Book book : library.getBooks()) {
            if (book.isAvailable()) {
                System.out.println(book.getId() + ". " + book.getTitle());
            }
        }

        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        Member member = library.findMemberById(memberId);
        Book book = library.findBookById(bookId);

        if (member != null && book != null) {
            Transaction transaction = Transaction.getTransaction();
            if (transaction.borrowBook(book, member)) {
                System.out.println("Book borrowed successfully.");
            }
        } else {
            System.out.println("Invalid member or book ID.");
        }
    }

    private void returnBook(Scanner scanner) {
        System.out.print("Enter member ID: ");
        int memberId = scanner.nextInt();

        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        Member member = library.findMemberById(memberId);
        Book book = library.findBookById(bookId);

        if (member != null && book != null) {
            Transaction transaction = Transaction.getTransaction();
            transaction.returnBook(book, member);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Invalid member or book ID.");
        }
    }

    private void viewBorrowedBooks(Scanner scanner) {
        System.out.print("Enter member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();

        Member specificMember = library.findMemberById(memberId);

        if (specificMember != null) {
            System.out.println("Books borrowed by " + specificMember.getName() + ":");
            for (Book bk : specificMember.getBorrowedBooks()) {
                System.out.println(" - " + bk.getTitle());
            }
        } else {
            System.out.println("Invalid member ID.");
        }
    }

    private void viewTransactionHistory() {
        Transaction transaction = Transaction.getTransaction();
        transaction.displayTransactionHistory();
    }
}
