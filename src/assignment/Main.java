package assignment;

public class Main {

    public static void main(String[] args) {
        new NamePrinter().printNames();
    }
}

class NamePrinter {
    /**
     * Prints the names of the group members separated by semicolons.
     */
    public void printNames() {
        String separator = ";";

        String[] names = {
                "Name 1",
                "Name 2",
                "Name 3",
                "Jonathan",
                "Name 5"};

        System.out.println(String.join(separator, names));
    }
}
