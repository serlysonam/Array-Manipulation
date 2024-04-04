import java.util.Arrays;
import java.util.Scanner;

public class Main {
    /** Return value for locate methods */
    public static final int NOT_FOUND = -1;

    /** Return value for checkOutOrReturn */
    public static final boolean CHECKOUT_SUCCESS = true;

    public static void main(String[] args) {
        if(unitTests() != 0)
            return;

        bookEvaluation();
        methodsEvaluation();

        final int   ADD_BOOK    = 1,
                    BROWSE      = 2,
                    CHECKOUT    = 3,
                    RETURN      = 4,
                    EXIT        = 0;

        final int   BROWSE_TITLE    = 1,
                    BROWSE_AUTHOR   = 2,
                    BROWSE_GENRE    = 3,
                    BROWSE_ALL      = 0;

        Book[] collection   = new Book[0];
        int[] stockCounts   = new int[0];
        String[] authors    = new String[0];
        int[] authorCounts  = new int[0];

        Scanner in = new Scanner(System.in);
        int choice = 0;

        // Initial inventory from instructions, plus an extra copy of two books
        if(args.length > 0 && args[0].equals("initialize")) {
            collection =
                    addBookToCollection(collection, new Book("A Grief Observed",
                            "C. S. Lewis",
                            Book.getGenreID("Self-help")));
            collection =
                    addBookToCollection(collection, new Book("Dune",
                            "Frank Herbert",
                            Book.getGenreID("Fiction")));
            collection =
                    addBookToCollection(collection, new Book("Mastering the Art of French Cooking",
                            "Julia Child",
                            Book.getGenreID("Cooking")));
            collection =
                    addBookToCollection(collection, new Book("Revel Introduction to Java Programming and Data Structures",
                            "Y. Daniel Lang",
                            Book.getGenreID("Educational")));
            collection =
                    addBookToCollection(collection, new Book("The Lion the Witch and the Wardrobe",
                            "C. S. Lewis",
                            Book.getGenreID("Fiction")));

            collection =
                    addBookToCollection(collection, new Book("The Lion the Witch and the Wardrobe",
                            "C. S. Lewis",
                            Book.getGenreID("Fiction")));
            collection =
                    addBookToCollection(collection, new Book("Mastering the Art of French Cooking",
                            "Julia Child",
                            Book.getGenreID("Cooking")));
            stockCounts = new int[5];//{1,1,2,1,2};
            Arrays.fill(stockCounts, 1);
            stockCounts[2]++;
            stockCounts[4]++;

            authors = new String[4];
            for (int idx = 0; idx < authors.length; idx++)
                authors[idx] = collection[idx].getAuthor();
            authorCounts = new int[authors.length];
            Arrays.fill(authorCounts, 1);
            authorCounts[0]++;
        }
        do {
            printMenu();

            choice = in.nextInt();
            in.nextLine(); //dummy

            switch (choice) {
                case ADD_BOOK:
                    System.out.print("TITLE> ");
                    String title = in.nextLine();

                    System.out.print("AUTHOR> ");
                    String author = in.nextLine();

                    System.out.println("Valid genres are: " + Arrays.toString(Book.GENRE_LIST));
                    System.out.print("GENRE> ");
                    String genre = in.nextLine();

                    int gInt = Book.getGenreID(genre);

                    Book addBook = new Book(title, author, gInt);
                    if (locateTitleInCollection(collection, addBook.getTitle()) == NOT_FOUND) { //title not found
                        collection = addBookToCollection(collection, addBook);

                        stockCounts = Arrays.copyOf(stockCounts, stockCounts.length + 1);
                        stockCounts[stockCounts.length - 1] = 1;

                        if(locateAuthorInCollection(authors, addBook.getAuthor()) == NOT_FOUND) { //author not found
                            authors = Arrays.copyOf(authors, authors.length+1);
                            authors[authors.length-1] = addBook.getAuthor();

                            authorCounts = Arrays.copyOf(authorCounts, authorCounts.length+1);
                            authorCounts[authorCounts.length-1] = 1;
                        }
                        else {
                            int idx = locateAuthorInCollection(authors, addBook.getAuthor());
                            authorCounts[idx]++;
                        }
                    } else {
                        int idx = locateTitleInCollection(collection, addBook.getTitle());
                        stockCounts[idx]++;
                    }


                    //System.out.println(addBook);
                    break;

                case BROWSE:
                    System.out.println("Browse by (1) Title, (2) Author, (3) Genre, (0) All");
                    System.out.print("BROWSE> ");

                    int browse = in.nextInt();
                    in.nextLine();

                    switch (browse) {
                        case BROWSE_TITLE:

                            System.out.print("TITLE> ");
                            String b_title = in.nextLine();

                            int t_idx = locateTitleInCollection(collection, b_title);
                            if(t_idx != NOT_FOUND)
                                System.out.println("(count=" + stockCounts[t_idx] + ") " + collection[t_idx]);
                            else
                                System.out.println("Book not found");

                            break;
                        case BROWSE_AUTHOR:
                            System.out.print("AUTHOR> ");
                            String b_author = in.nextLine();

                            Book[] booksByAuthor = getBooksByAuthor(collection, authors, authorCounts, b_author);

                            if(booksByAuthor.length > 0) {
                                for (Book b : booksByAuthor)
                                    System.out.println(b);
                            }
                            else
                                System.out.println("Author not found");

                            break;
                        case BROWSE_GENRE:
                            System.out.print("GENRE> ");
                            String b_genre = in.nextLine();

                            Book[] booksInGenre = getBooksInGenre(collection, b_genre);

                            if(booksInGenre.length > 0) {
                                for (Book b : booksInGenre)
                                    System.out.println(b);
                            }
                            else
                                System.out.println("No books in that genre found");

                            break;
                        case BROWSE_ALL:
                            int a_idx=0;
                            for(Book b : collection) {
                                int count = stockCounts[a_idx];
                                System.out.println("(count="+count+") " + b);

                                a_idx++;
                            }
                            break;
                        default:
                            System.out.println("Invalid browse choice");
                    }
                    break;

                case CHECKOUT:
                    System.out.print("CHECKOUT TITLE> ");
                    String co_title = in.nextLine();

                    boolean co_res = checkOutOrReturn(collection, stockCounts, co_title, true);
                    if(co_res == CHECKOUT_SUCCESS)
                        System.out.println(co_title + " has been successfully checked out");
                    else
                        System.out.println("Something went wrong. Check the title '" + co_title + "' and try again.");

                    break;

                case RETURN:
                    System.out.print("RETURN TITLE> ");
                    String re_title = in.nextLine();

                    boolean re_res = checkOutOrReturn(collection, stockCounts, re_title, false);
                    if(re_res == CHECKOUT_SUCCESS)
                        System.out.println(re_title + " has been successfully returned");
                    else
                        System.out.println("Something went wrong. Check the title '" + re_title + "' and try again.");

                    break;

                case EXIT:
                    System.out.println("Goodbye");
                    break;

                default:
                    System.out.println("Invalid menu choice");
            }
        } while (choice != 0);

    }

    /** Displays the  menu options. This method may optionally return the menu selection<br>
     * - (Add a New Book) Prompt for book details (title, author, genre), validate
     * genre, and update collections.<br>
     * – (Browse Library Inventory) Provide browsing options (by title, author
     * name, genre, or all entries) and display requested information.<br>
     * – (Check Out a Book) Prompt for the title, validate title, update stock.<br>
     * – (Return a Book) Prompt for the title, validate title, update stock.<br>
     * – (Exit) Terminate the program.<br>
     * – (Default) Handle invalid selections and prompt again
     */
    public static void printMenu() {

        System.out.println( " 1. (Add a New Book) \n" +
                            " 2. (Browse Library Inventory) \n" +
                            " 3. (Check Out a Book) \n" +
                            " 4. (Return a Book) \n" +
                            " 0. (Exit) ");

        System.out.print("> ");

    }

    /**
     * Checks if the title specified in the second parameter exists in the collection.
     * Returns the first index with a matching title, -1 is returned when no match is found.
     * @param array Books to be searched
     * @param title Book title to be found
     * @return Index of first Book element with matching title, -1 (NOT_FOUND) on not found.
     */
    public static int locateTitleInCollection(Book[] array, String title) {
        for(int idx=0; idx<array.length; idx++) {
            if(array[idx].getTitle().equals(title))
                return idx;
        }
        return NOT_FOUND;
    }

    /**
     * Checks if the author specified in the second parameter exists in the author names array.
     * Returns the first index with a matching author, $-1$ is returned when no match is found.
     * @param array Author names to be searched
     * @param author Name to be found
     * @return Index of first String element with matching name, -1 (NOT_FOUND) on not found.
     */
    public static int locateAuthorInCollection(String[] array, String author) {
        for(int idx=0; idx<array.length; idx++) {
            if(array[idx].equals(author))
                return idx;
        }
        return NOT_FOUND;
    }

    /**
     * Counts the number of books in the genre specified by the second parameter and returns the count.
     * @param array The collection of books
     * @param g The genre to be counted
     * @return The number of books with genre g.
     */
    public static int countGenre(Book[] array, int g) {
        int count=0;
        for(Book b : array) {
            if (b.getGenre() == g)
                count ++;
        }
        return count;
    }

    /**
     * Adds the book specified in the second parameter to the collection. If the book is already in the
     * collection, the passed array is immediately returned.  Otherwise, the passed array is expanded by
     * one and the passed book is placed in the last position. The updated array is returned.
     * @return An updated array of books after adding b, if it is not already in the collection
     */
    public static Book[] addBookToCollection(Book[] array, Book b) {
        if( locateTitleInCollection(array, b.getTitle()) != -1 ) // title is found in the array
            return array;

        Book[] newArray = Arrays.copyOf(array, array.length+1);
        newArray[newArray.length-1] = b;

        return newArray;
    }


    /**
     * Creates an array with all books in the collection by the author. The author's name is
     * passed as the second parameter. If no books are found, the returned array has length zero.
     *
     * @param books The collection of Book objects
     * @param names The author names
     * @param counts The counts of each author's publications
     * @param author The author name to search.
     * @return Array containing all Books by the given author
     */

    public static Book[] getBooksByAuthor(Book[] books, String[] names, int[] counts, String author){
        int size=0;
        for(int idx=0; idx<names.length; idx++) {
            if (names[idx].equals(author))
                size = counts[idx];
        }

        Book[] result = new Book[size];
        int idx=0;
        for(Book b : books) {
            if(b.getAuthor().equals(author)) {
                result[idx] = b;
                idx++;
            }
        }
        return result;
    }

    /**
     * Creates an array with all books in the collection that are in the genre. The genre's number is
     * passed as the second parameter. If no books are found, the returned array has length zero.
     * @return A subset of the collection in array where all books have the genre g
     */
    public static Book[] getBooksInGenre(Book[] array, int g) {
        int size = countGenre(array, g);

        Book[] result = new Book[size];
        int idx=0;
        for(Book b : array) {
            if(b.getGenre() == g) {
                result[idx] = b;
                idx++;
            }
        }
        return result;
    }

    /**
     * A helper method to Book[] getBooksInGenre(Book[], int). This method determines the genre number of
     * the passed String and returns the result found by the previous method.
     * @return A subset of the collection in array where all books have the genreDescription genre
     */
    public static Book[] getBooksInGenre(Book[] array, String genre) {
        return getBooksInGenre(array, Book.getGenreID( genre ));
    }

    /**
     * Updates the inventory count of the book with name passed as the third parameter. When the fourth
     * parameter is true, the book is checked out, otherwise the book is returned. There must be at least
     * one copy of the book available in stock, and the book must be in the collection. Whenever the stock
     * counter array changes, true (CHECKOUT_SUCCESS) is returned. Otherwise, false is returned.
     * @return CHECKOUT_SUCCESS or !CHECKOUT_SUCCESS
     */
    public static boolean checkOutOrReturn(Book[] books, int[] counts, String title, boolean checkoutFlag) {
        int idx = locateTitleInCollection(books, title);
        if(idx == NOT_FOUND)
            return !CHECKOUT_SUCCESS;

        if(checkoutFlag) {
            if(counts[idx] > 0) {
                counts[idx]--;
                return CHECKOUT_SUCCESS;
            }
            else
                return !CHECKOUT_SUCCESS;
        }
        else {
            counts[idx]++;
            return CHECKOUT_SUCCESS;
        }
    }
    /**
     * The following code will be placed in the class Main and called at the beginning of the main method.
     * The methods locateTitleInCollection and addBookToCollection are called, and the
     * arrays collection and stockCounts are updated. The remaining required methods and arrays
     * will be added by the student. Each array can be examined via the debugger or by printing.
     * Tests of the methods in class Book and other methods added by the student in class Main
     * will also be included. Other return values may also be used when an incorrect result
     * is detected (i.e., if(collection.length != 4) ... )
     *
     * @return 0 when no errors detected, non-zero value otherwise.
     */
    public static int unitTests(){
        System.out.println("=".repeat(10) + " START UNIT TESTING " + "=".repeat(10));
        Book[] collection   = new Book[0];
        int[] stockCounts   = new int[0];
        String[] authors    = new String[0];
        int[] authorCounts  = new int[0];

        Book b1 = new Book("abc", "X Y", 1);
        Book b2 = new Book("def", "X Y", 1);
        Book b3 = new Book("ghi", "Y Z", 2);
        Book b4 = new Book("jkl", "Z W", 3);

        Book[] testBooks = {b1,b2,b3,b4,b1};

        for(Book theBook : testBooks) {

            if (locateTitleInCollection(collection, theBook.getTitle()) == NOT_FOUND) { //title not found
                collection = addBookToCollection(collection, theBook);

                stockCounts = Arrays.copyOf(stockCounts, stockCounts.length + 1);
                stockCounts[stockCounts.length - 1] = 1;

                if(locateAuthorInCollection(authors, theBook.getAuthor()) == NOT_FOUND) { //author not found
                    authors = Arrays.copyOf(authors, authors.length+1);
                    authors[authors.length-1] = theBook.getAuthor();

                    authorCounts = Arrays.copyOf(authorCounts, authorCounts.length+1);
                    authorCounts[authorCounts.length-1] = 1;
                }
                else {
                    int idx = locateAuthorInCollection(authors, theBook.getAuthor());
                    authorCounts[idx]++;
                }
            } else {
                int idx = locateTitleInCollection(collection, theBook.getTitle());
                stockCounts[idx]++;
            }

        }

        if(collection.length != 4)
            return -1;

        System.out.println(Arrays.toString(collection));
        System.out.println(Arrays.toString(stockCounts));
        System.out.println(Arrays.toString(authors));
        System.out.println(Arrays.toString(authorCounts));

        Book[] X_Y_books = getBooksByAuthor(collection, authors, authorCounts, "X Y");

        if(X_Y_books.length != 2)
            return -2;

        Book[] fictionBooks = getBooksInGenre(collection, "Fiction");
        if(fictionBooks.length != 2)
            return -3;

        Book[] eduBooks = getBooksInGenre(collection, "Educational");
        if(eduBooks.length != 1)
            return -3;

        if( checkOutOrReturn(collection, stockCounts, "abc", true) != CHECKOUT_SUCCESS)
            return -4;

        if(stockCounts[0] != 1)
            return -4;

        System.out.println("=".repeat(10) + " END TESTING " + "=".repeat(10));
        System.out.println();

        return 0;
    }

    /**
     * Automated testing method to evaluate the Book class, including constructors, accessors, and toString.
     * @implNote toString tests include title case and lowercase versions of the genre descriptions, other minor variations are allowed.
     */
    public static void bookEvaluation() {
        System.out.println("=".repeat(20) + " START BOOK EVALUATION " + "=".repeat(20) );

        Book b1 = new Book("aaa", "P Q", 1);
        Book b2 = new Book("bbb", "P Q", 1);
        Book b3 = new Book("ccc", "R S", 2);
        Book b4 = new Book("ddd", "T U", 3);
        Book b5 = new Book("eee", "V W", 3);
        Book b6 = new Book("jjj", "X Y", 4);
        Book b7 = new Book("jjj", "X Y", 5);
        Book b8 = new Book("kkk", "Z Z", 0);

        System.out.println("Author accessor:");
            testOutput(b1.getAuthor().equals("P Q"));
            testOutput(b2.getAuthor().equals("P Q"));
            testOutput(b3.getAuthor().equals("R S"));

        System.out.println("Title accessor:");
            testOutput(b1.getTitle().equals("aaa"));
            testOutput(b2.getTitle().equals("bbb"));
            testOutput(b4.getTitle().equals("ddd"));

        //If this returns a String instead of an int, there is a problem in the Design Specification (Rubric 4)
        System.out.println("Genre accessor:");
            testOutput(b5.getGenre() == 3);
            testOutput(b8.getGenre() == 0);

        System.out.println("toString - author:");
            testOutput(b1.toString().contains(b1.getAuthor()));
            testOutput(b1.toString().contains(b1.getAuthor()));

        System.out.println("toString - title:");
            testOutput(b2.toString().contains(b2.getTitle()));

        System.out.println("toString - genre description:");
            testOutput(b1.toString().contains("Fiction")    || b1.toString().contains("fiction"));
            testOutput(b2.toString().contains("Fiction")    || b2.toString().contains("fiction"));
            testOutput(b3.toString().contains("Education")|| b3.toString().contains("education"));
            testOutput(b4.toString().contains("Cooking")    || b4.toString().contains("cooking"));
            testOutput(b5.toString().contains("Cooking")    || b5.toString().contains("cooking"));
            testOutput(b6.toString().contains("Biography")  || b6.toString().contains("biography"));
            testOutput(b7.toString().contains("Self-help")  || b7.toString().contains("self-help"));
            testOutput(b8.toString().contains("Other")      || b8.toString().contains("other"));

        System.out.println("=".repeat(21) + " END BOOK EVALUATION " + "=".repeat(21) +"\n");
    }

    /**
     * Automated testing method to evaluate the required static methods, including printMenu, locateTitleInCollection,
     * locateAuthorInCollection, and countGenre. If submissions include a third "Library" class, these tests would
     * need to be adjusted.
     * @implNote The implementation for printMenu may contain a Scanner read. This return value can be ignored.
     */
    public static void methodsEvaluation() {

        System.out.println("=".repeat(20) + " START METHOD EVALUATION " + "=".repeat(20) );

        System.out.println("printMenu (validate output manually)");
            printMenu();
            System.out.println("\n");

        Book b1 = new Book("abc", "X Y", 1);
        Book b2 = new Book("def", "X Y", 1);
        Book b3 = new Book("ghi", "Y Z", 2);
        Book b4 = new Book("jkl", "Z W", 3);


        Book[] collection   = {b1,  b2, b3, b4};
        int[] stockCounts   = {2,   1,  3,  4 };

        String[] authors    = {"X Y",   "Y Z",  "Z W"};
        int[] authorCounts  = {2,       1,      1    };

        System.out.println("locateTitleInCollection:");
            testOutput(locateTitleInCollection(collection, "abc") == 0);
            testOutput(locateTitleInCollection(collection, "ghi") == 2);
            testOutput(locateTitleInCollection(collection, "xyz") == -1);

        System.out.println("locateAuthorInCollection:");
            testOutput( locateAuthorInCollection(authors, "X Y") == 0);
            testOutput( locateAuthorInCollection(authors, "Y Z") == 1);
            testOutput( locateAuthorInCollection(authors, "Z W") == 2);
            testOutput( locateAuthorInCollection(authors, "A B") == -1);

        //If submissions require the stock counts in the following method signatures, there is an error with Rubric 6
        System.out.println("countGenre:");
            testOutput(countGenre(collection, 1) == 2);
            testOutput(countGenre(collection, 3) == 1);
            testOutput(countGenre(collection, 4) == 0);
            testOutput(countGenre(collection, 0) == 0);

        System.out.println("=".repeat(21) + " END METHOD EVALUATION " + "=".repeat(21) );
    }

    /**
     * Helper method to output pass/fail status
     * @param b The test result as boolean
     */
    public static void testOutput(boolean b) {
        if(b)
            System.out.println( "\tPASSED" );
        else
            System.out.println( "!! FAIL !!" );
    }
}
