import java.util.ArrayList;
import java.util.List;

public class Member {
    private int memberId;
    private String name;
    private List<Book> borrowedBooks = new ArrayList<>();

    // Constructor
    public Member(int memberId, String name) {
        if (memberId <= 0) {
            throw new IllegalArgumentException("Member ID must be a positive integer.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        this.memberId = memberId;
        this.name = name;
    }

    // Get member ID (updated to match the code's expectations)
    public int getId() {
        return memberId;
    }

    // Get member name
    public String getName() {
        return name;
    }

    // Get list of borrowed books
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Borrow a book
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    // Return a borrowed book
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    // Optional setter for name
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name;
    }

    // Optional setter for memberId
    public void setMemberId(int memberId) {
        if (memberId <= 0) {
            throw new IllegalArgumentException("Member ID must be a positive integer.");
        }
        this.memberId = memberId;
    }

    // Optional toString method for better representation
    @Override
    public String toString() {
        return "Member ID: " + memberId + ", Name: " + name;
    }
}
