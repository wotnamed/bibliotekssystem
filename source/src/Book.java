import java.util.LinkedHashMap;

public class Book implements LibraryItem{
    protected String author;
    protected String year;
    protected String language;
    protected String ISBN;
    protected String title;
    protected String pages;

    public Book(String author, String year, String language, String ISBN, String title, String pages){
        this.author = author;
        this.year = year;
        this.language = language;
        this.ISBN = ISBN;
        this.title = title;
        this.pages = pages;
    }

    @Override
    public LinkedHashMap<String, String> getDisplayInfo() {
        LinkedHashMap<String, String> info = new LinkedHashMap<>();
        info.put("Year: ", this.getYear());
        info.put("Author: ", this.getISBN());
        info.put("Title: ", this.getTitle());
        info.put("Languages: ", this.getLanguage());
        info.put("ISBN: ", this.getISBN());

        return info;
    }

    public boolean matchesSearch(String search){
        return title.contains(search) || author.contains(search);
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

