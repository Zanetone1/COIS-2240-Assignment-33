import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class LibraryManagementTest {

    private Book validBook;
    private Book invalidBook;
    private Member member;
    private Transaction transaction;

    @Before
    public void setUp() {
        // Setup method to instantiate objects for each test
        try {
            validBook = new Book(100, "Valid Book");
            invalidBook = new Book(1000, "Invalid Book");
        } catch (Exception e) {
            fail("Exception occurred while creating books: " + e.getMessage());
        }
        member = new Member(1, "John Doe");
        transaction = Transaction.getTransaction(); // Singleton instance
    }

    // Test 1: Book ID Validation
    @Test
    public void testBookId() {
        try {
            // Valid cases
            Book book1 = new Book(100, "Valid Book");
            Book book2 = new Book(999, "Valid Book");
            assertNotNull(book1);
            assertNotNull(book2);
        } catch (Exception e) {
            fail("Exception should not be thrown for valid IDs: " + e.getMessage());
        }

        try {
            // Invalid case: ID 1000 is invalid
            new Book(1000, "Invalid Book");
            fail("Exception should be thrown for ID 1000");
        } catch (Exception e) {
            assertEquals("Invalid Book ID: 1000", e.getMessage());
        }

        try {
            // Invalid case: ID less than 100
            new Book(99, "Invalid Book");
            fail("Exception should be thrown for ID less than 100");
        } catch (Exception e) {
            assertEquals("Invalid Book ID: 99", e.getMessage());
        }

        try {
            // Invalid case: ID greater than 999
            new Book(1001, "Invalid Book");
            fail("Exception should be thrown for ID greater than 999");
        } catch (Exception e) {
            assertEquals("Invalid Book ID: 1001", e.getMessage());
        }
    }

    // Test 2: Borrow and Return Validation
    @Test
    public void testBorrowReturn() {
        // Check that the book is available initially
        assertTrue(validBook.isAvailable());

        // Borrow the book and assert successful borrowing
        boolean borrowSuccess = transaction.borrowBook(validBook, member);
        assertTrue(borrowSuccess);
        assertFalse(validBook.isAvailable()); // The book should now be unavailable

        // Try borrowing the book again, which should fail
        boolean secondBorrowAttempt = transaction.borrowBook(validBook, member);
        assertFalse(secondBorrowAttempt);

        // Return the book and assert successful returning
        transaction.returnBook(validBook, member);
        assertTrue(validBook.isAvailable());

        // Try returning the book again, which should fail
        transaction.returnBook(validBook, member); // No boolean return needed
        assertTrue(validBook.isAvailable());  // The book should still be available
    }

    // Test 3: Singleton Validation for Transaction class
    @Test
    public void testSingletonTransaction() {
        try {
            // Access the constructor of the Transaction class
            Constructor<Transaction> constructor = Transaction.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // Check if the constructor is private
            int modifiers = constructor.getModifiers();
            assertEquals(Modifier.PRIVATE, modifiers);

            // Try to instantiate Transaction using reflection (should fail)
            Transaction newTransaction = constructor.newInstance();
            fail("Exception should be thrown when trying to instantiate Transaction using reflection.");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalAccessException); // This should be thrown for reflection access
        }

        // Ensure Singleton pattern works - both should point to the same instance
        Transaction t1 = Transaction.getTransaction();
        Transaction t2 = Transaction.getTransaction();
        assertSame(t1, t2); // Both references should be the same instance
    }
}
