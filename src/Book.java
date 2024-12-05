public class Book {
    private int id;
    private String title;
    private boolean isAvailable;

    // Constructor
    public Book(int id, String title) throws Exception {
        if (!isValidId(id)) {
            throw new Exception("Invalid Book ID: " + id);
        }
        this.id = id;
        this.title = title;
        this.isAvailable = true; // Book is available when created
    }

    // Method to check if ID is valid
    public boolean isValidId(int id) {
        return id >= 100 && id <= 999;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Set the availability of the book
    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    // Borrow the book
    public void borrowBook() {
        if (isAvailable) {
            this.isAvailable = false;  // Book is no longer available
        } else {
            System.out.println("The book '" + title + "' is already borrowed.");
        }
    }

    // Return the book
    public void returnBook() {
        if (!isAvailable) {
            this.isAvailable = true;  // Book is available again
        } else {
            System.out.println("The book '" + title + "' was not borrowed.");
        }
    }
}
