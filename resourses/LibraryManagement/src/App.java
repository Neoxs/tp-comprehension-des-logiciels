import java.util.ArrayList;
import java.util.List;

public class App {
    private List<Author> authors;
    private List<Book> books;
    private List<Customer> customers;

    public App() {
        this.authors = new ArrayList<>();
        this.books = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void registerAuthor(Author author) {
        authors.add(author);
    }

    public void registerBook(Book book) {
        books.add(book);
    }

    public void registerCustomer(Customer customer) {
        customers.add(customer);
    }

    public void displaySystemInfo() {
        System.out.println("\n=== Library System Status ===");
        for (Author author : authors) {
            author.displayAuthorInfo();
        }
        for (Book book : books) {
            book.displayBookInfo();
        }
        for (Customer customer : customers) {
            customer.displayCustomerInfo();
        }
    }

    public static void main(String[] args) {
        App library = new App();

        // Create customers
        Customer customer1 = new Customer("C001", "Alice Smith");
        Customer customer2 = new Customer("C002", "Bob Johnson");
        library.registerCustomer(customer1);
        library.registerCustomer(customer2);

        // Create authors (only coupled with customers)
        Author author1 = new Author("A001", "J.K. Rowling", "British");
        author1.setPreferredCustomer(customer1);
        author1.notifyCustomer("New book coming soon!");
        library.registerAuthor(author1);

        // Create books (only coupled with customers)
        Book book1 = new Book("B001", "Harry Potter", author1.getName());
        Book book2 = new Book("B002", "1984", "George Orwell");
        library.registerBook(book1);
        library.registerBook(book2);

        // Perform operations
        System.out.println("\nPerforming Library Operations:");
        book1.lendTo(customer1);
        book2.lendTo(customer2);
        book1.returnBook();
        book2.returnBook();

        // Display final status
        library.displaySystemInfo();
    }
}