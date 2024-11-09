import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private List<String> borrowedBooks;
    private List<String> messages;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public void addBorrowedBook(String bookTitle) {
        borrowedBooks.add(bookTitle);
    }

    public void removeBorrowedBook(String bookTitle) {
        borrowedBooks.remove(bookTitle);
    }

    public void receiveMessage(String message) {
        messages.add(message);
        System.out.println("Message for " + name + ": " + message);
    }

    public void displayCustomerInfo() {
        System.out.println("\nCustomer Details:");
        System.out.println("ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Borrowed Books: " + borrowedBooks);
        System.out.println("Messages: " + messages);
    }

    public String getName() {
        return name;
    }
}