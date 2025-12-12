public class Book {
    protected String author;
    protected String year;
    protected String language;
    protected String ISBN;
    protected String title;
    protected String pages;

    Book(String author, String year, String language, String ISBN, String title, String pages){
        this.author = author;
        this.year = year;
        this.language = language;
        this.ISBN = ISBN;
        this.title = title;
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getLanguage() {
        return language;
    }

    public String getTitle() {
        return title;
    }

    public String getPages() {
        return pages;
    }

    public String getYear() {
        return year;
    }
}

