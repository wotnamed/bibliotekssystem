public class Book {
    protected String author;
    protected String year;
    protected String language;
    protected String ISBN;
    protected String title;
    protected String length;

    public Book(String title, String author, String length, String language, String year, String ISBN){
        this.title = title;
        this.author = author;
        this.length = length;
        this.language = language;
        this.year = year;
        this.ISBN = ISBN;

    }
}

