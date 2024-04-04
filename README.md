# Array-Manipulation

**Assignment Purpose:**

Explore arrays and array manipulation. Understand and implement classes in Java. Arrays dynamically resize to manage the library collection records. Basic console input/output, object-oriented programming principles, and simple control flow.

3. The Main class serves as the core of a Library Management System (LMS), facilitating
interactions such as adding new books, browsing the inventory, and managing checkedout books through a console-based interface. The data for each book is stored in a Book object and other information to implement the library functions is stored in several arrays. Arrays must be declared in the main method or (optionally) as private fields in a third
class. Static arrays in the declared outside of a method Main class are not allowed.

(a) The program will be completed by following the basic steps:
i. Implement the Book class according to the given requirements (see 4a).
ii. Test the implementation of class Book by creating objects at the beginning of main. Call every method from class Book on each object and
verify the returned results are correct. Test code will remain at the top
of the main method.
iii. In the Main class, initialize several arrays (see 3c) to maintain the Library Management System. Each array will begin with length zero (0)
and be expanded by one element before any element is added (see the
copyOf method in java.util.Arrays)
iv. Implement methods to display the main menu, retrieve the index of a
Book in the collection array, retrieve the position of an author’s name
in the author array, and count the number of books of a given genre in
the collection array. See 4c for a complete listing and description.
v. Test each method written by creating a collection of Books with at least
two different books of the same author, two different books of the same
genre, and two copies of the same book. See the sample test data (3b)
below, calling the methods with the data instead of adding them directly
to the arrays. Test code will remain at the top of main and may be used
to initialize the library’s collection for the remainder of the program.
vi. For each menu option (see 4b), implement the corresponding functionality. The menu system will allow the user to add new books; browse
the library by title, author name, genre description, or all entries; check
out a book; return a book; or exit.
vii. Test the application thoroughly to ensure all works as expected.

(b) Data used for testing will be based on these or other books chosen by the student.
• A Grief Observed by C. S. Lewis, Genre: Self-help
• Dune by Frank Herbert, Genre: Fiction
• Mastering the Art of French Cooking by Julia Child, Genre: Cooking
• Revel Introduction to Java Programming and Data Structures by Y. Daniel
Lang, Genre: Educational
• The Lion the Witch and the Wardrobe by C. S. Lewis, Genre: Fiction

(c) The arrays that will maintain the Library Management System are described
next. Arrays must be declared in the main method or as private fields in a
separate class. The arrays are initialized with length zero (0) and expanded by
one before each element is added.

i. All Book objects representing the collection. When a second copy of the
same book (determined by the book titles) is added to the library, this
array remains unchanged.
ii. Numeric (int) values representing how many of each book is in stock.
This is a parallel array to the Book objects. When the first copy of book
is added to the collection, a count of one (1) is stored here. When the
second copy of a book is added, that count is incremented. The count’s
index in this array is the same as the object in the previous array.
iii. String objects representing the author names in the collection. When a
second book by the same author (determined by the author names) is
added to the library, this array remains unchanged.
iv. Numeric (int) values representing how prolific an author is in the library.
This is a parallel array to the author names. When the first book by
an author is added to the collection, a count of one (1) is stored here.
When the second book by an author is added, the count is incremented.
The count’s index is the same as the object in the previous array.
4. Design Specification for Library Managment System
The program will consist of at least two classes: Book and Main.
(a) The class Book is written in the file Book.java and contains the following members.
• Private String fields for title and author. Private integer field for genre.
• A parameterized constructor to initialize the book objects.
• Getter or accessor methods for each field.
• No setter or mutator methods for any field.
• A toString method returns a String representation of the book. The return
value contains the title, author, and genre description (not only the integer).
• Static final members for each genre number and genre description based on
the list below. These may be public, and they may be either named constants
or stored in an array.
– Fiction (1)
– Educational (2)
– Cooking (3)
– Biography (4)
– Self-help (5)
– Other (0)
(b) The class Main contains methods and logic to complete the Library Management
System. Static variables declared outside a method are not allowed in class Main.
• A method to display the main menu and prompt user input.
• Functionality to add a new book, browse the library, check out books, and
return books.

• Use arrays to manage the book collection and stock count. The arrays are
initialized with length zero (0) and expanded by one before each element is
added.
• Include validation for user inputs where necessary.
The execution of the main method will follow the outline here:
• Print Menu - Display the main menu options to the user.
• User Input - Prompt the user to enter a selection from the menu.
• Menu Options - Based on the user’s selection, proceed to one of the following cases.
– (Add a New Book) Prompt for book details (title, author, genre), validate
genre, and update collections.
– (Browse Library Inventory) Provide browsing options (by title, author
name, genre, or all entries) and display requested information.
– (Check Out a Book) Prompt for the title, validate title, update stock.
– (Return a Book) Prompt for the title, validate title, update stock.
– (Exit) Terminate the program.
– (Default) Handle invalid selections and prompt again.
• Loop - Return to the ”Print Menu” step unless the exit option is chosen.
• End - The program terminates.
(c) Components The primary implementation of methods in class Main includes
the following required public and static methods:
• void printMenu() displays the menu options. This method may optionally
return the menu selection.
• int locateTitleInCollection(Book[], String) checks if the title specified in the second parameter exists in the collection. Returns the first index
with a matching title, −1 is returned when no match is found.
• int locateAuthorInCollection(String[], String) checks if the author
specified in the second parameter exists in the author names array. Returns
the first index with a matching author, −1 is returned when no match is
found.
• int countGenre(Book[], int) counts the number of books in the genre
specific by the second parameter and returns the count.
• The void main(String[] args) method initializes the collection arrays and
processes user inputs.
The program may also contain the following optional public and static methods. The functionality provided by them will be required as either a method or
standalone code in main.
• Book[] addBookToCollection(Book[], Book) adds the book specified in
the second parameter to the collection. If the book is already in the collection,
the passed array is immediately returned. Otherwise, the passed array is expanded by one and the passed book is placed in the last position. The
updated array is returned.
• Book[] getBooksByAuthor(Book[], String) creates an array with all books
in the collection by the author. The author’s name is passed as the second
parameter. If no books are found, the returned array has length zero.
• Book[] getBooksInGenre(Book[], int) creates an array with all books in
the collection that are in the genre. The genre’s number is passed as the
second parameter. If no books are found, the returned array has length zero.
• Book[] getBooksInGenre(Book[], String) is a helper method to Book[]
getBooksInGenre(Book[], int). This method determines the genre number of the passed String and returns the result found by the previous method.
• boolean checkOutOrReturn(Book[], int[], String, boolean) updates
the inventory count of the book with name passed as the third parameter.
When the fourth parameter is true, the book is checked out, otherwise the
book is returned. There must be at least one copy of the book available in
stock, and the book must be in the collection. Whenever the stock counter
array changes, true is returned. Otherwise, false is returned.
