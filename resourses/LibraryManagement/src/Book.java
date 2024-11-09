public class Book {
    private String bookId;
    private String title;
    private String authorName;
    private Customer borrower; // Only coupling with Customer

    public Book(String bookId, String title, String authorName) {
        this.bookId = bookId;
        this.title = title;
        this.authorName = authorName;
    }

    public void lendTo(Customer customer) {
        if (borrower == null) {
            this.borrower = customer;
            customer.addBorrowedBook(title);
            System.out.println("Book '" + title + "' lent to " + customer.getName());
        } else {
            System.out.println("Book is already borrowed by " + borrower.getName());
        }
    }

    public void returnBook() {
        if (borrower != null) {
            borrower.removeBorrowedBook(title);
            System.out.println("Book '" + title + "' returned by " + borrower.getName());
            borrower = null;
        }
    }

    public void displayBookInfo() {
        System.out.println("\nBook Details:");
        System.out.println("ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author Name: " + authorName);
        System.out.println("Status: " + (borrower == null ? "Available" : "Borrowed by " + borrower.getName()));
    }
}