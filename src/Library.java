import java.util.ArrayList;
import java.util.List;

public class Library {

    // Singleton instance
    private static Library instance;

    // Member and Book lists
    private List<Member> members = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    // Private constructor to enforce singleton
    private Library() {}

    // Method to get the single instance of Library
    public static Library getLibrary() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    // Add a new member to the library
    public boolean addMember(Member member) {
        if (findMemberById(member.getId()) != null) {
            System.out.println("Error: A member with ID " + member.getId() + " already exists.");
            return false; // Duplicate ID
        }
        members.add(member);
        return true; // Successfully added
    }

    // Add a new book to the library
    public boolean addBook(Book book) {
        if (findBookById(book.getId()) != null) {
            System.out.println("Error: A book with ID " + book.getId() + " already exists.");
            return false; // Duplicate ID
        }
        books.add(book);
        return true; // Successfully added
    }

    // Find a member by ID
    public Member findMemberById(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null; // Member not found
    }

    // Find a book by ID
    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null; // Book not found
    }

    // Get the list of members
    public List<Member> getMembers() {
        return members;
    }

    // Get the list of books
    public List<Book> getBooks() {
        return books;
    }

    // Example of additional method for validation (optional)
    public boolean isMemberExists(int id) {
        return findMemberById(id) != null;
    }

    public boolean isBookExists(int id) {
        return findBookById(id) != null;
    }
}
