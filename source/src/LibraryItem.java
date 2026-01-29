import java.util.LinkedHashMap;

public interface LibraryItem {
    String getTitle();
    String getISBN();
    String getAuthor();
    boolean matchesSearch(String search);
    LinkedHashMap<String, String> getDisplayInfo();
}
