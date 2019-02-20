package assignment;

public class Main {

    public static void main(String[] args) {
        new NamePrinter().printNames();
    }
}

class NamePrinter {
    /**
     * Prints the names of the group members separated by commas.
     */
    public void printNames() {
        String separator = ",";

        String[] names = {
                "Name 1",
                "Name 2",
                "Jacob",
                "Name 4",
                "Name 5"};

        System.out.println(String.join(separator, names));
    }
}