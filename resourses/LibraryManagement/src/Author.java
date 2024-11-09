public class Author {
    private String authorId;
    private String name;
    private String nationality;
    private Customer preferredCustomer; // Only coupling with Customer

    public Author(String authorId, String name, String nationality) {
        this.authorId = authorId;
        this.name = name;
        this.nationality = nationality;
    }

    public void setPreferredCustomer(Customer customer) {
        this.preferredCustomer = customer;
        System.out.println("Set preferred customer: " + customer.getName() + " for author: " + name);
    }

    public void notifyCustomer(String message) {
        if (preferredCustomer != null) {
            preferredCustomer.receiveMessage(message);
        }
    }

    public void displayAuthorInfo() {
        System.out.println("Author Details:");
        System.out.println("ID: " + authorId);
        System.out.println("Name: " + name);
        System.out.println("Nationality: " + nationality);
        if (preferredCustomer != null) {
            System.out.println("Preferred Customer: " + preferredCustomer.getName());
        }
    }

    public String getName() {
        return name;
    }
}