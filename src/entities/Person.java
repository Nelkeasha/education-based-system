package entities;

/**
 * Abstract base class for all people in the university system.
 * Demonstrates: Abstraction, Inheritance, Encapsulation
 */
public abstract class Person {

    // Encapsulation - private fields
    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public Person(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Abstract method - forces subclasses to implement
    public abstract String getRole();

    // Abstract method - polymorphism
    public abstract void displayInfo();

    // Encapsulation - controlled access via getters
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "[" + getRole() + "] " + getFullName() + " (ID: " + id + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
