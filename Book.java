
public class Book {
    /** The title of the book, used as a unique ID. */
    private String title;
    /** The author of the book. */
    private String author;
    /** An integer ID of the book's genre. Used to determine the genre description from GENRE_LIST */
    private int genre;

    /** All possible genre descriptions */
    public static final String[] GENRE_LIST={"Fiction", "Educational", "Cooking", "Biography", "Self-help", "Other"};
    /** The genre IDs of each description */
    public static final int[]    GENRE_NUMS={1,         2,             3,         4,           5,           0};

    /**
     * Parameterized constructor
     * @param title
     * @param author
     * @param genre
     */
    public Book(String title, String author, int genre) {
        this.title = title;
        this.author = author;

        if(validateGenre(genre))
            this.genre = genre;
        else
            this.genre = 0; //Other
    }

    /**
     * Helper method to check if the passed genre ID is found in the GENRE_NUMS
     * @param genre
     * @return
     */
    public static boolean validateGenre(int genre) {
        for(int g : GENRE_NUMS) {
            if(g == genre)
                return true;
        }

        return false;
    }

    /**
     * Finds the correct genre description for the pass genre ID
     * @param genre
     * @return
     */
    public static String getGenreDescription(int genre) {
        for(int idx=0; idx<GENRE_NUMS.length; idx++) {
            int g = GENRE_NUMS[idx];
            if(g == genre)
                return GENRE_LIST[idx];
        }

        return GENRE_LIST[GENRE_LIST.length-1]; //Other

    }


    /**
     * Finds the correct genre ID for the passed genre description
     * @param genre
     * @return
     */
    public static int getGenreID(String genre) {
        for(int idx=0; idx<GENRE_LIST.length; idx++) {
            String g = GENRE_LIST[idx];
            if(g.equals( genre ))
                return GENRE_NUMS[idx];
        }

        return 0; //Other

    }

    /**
     * Accessor for genre
     * @return
     */
    public int getGenre() {
        return genre;
    }

    /**
     * Accessor for author
     * @return
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Accessor for title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to generate a summary of the current object including the title, author, and genre description.
     * @return
     */
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre=" + getGenreDescription( genre ) + " (" + genre + ")" +
                '}';
    }
}
