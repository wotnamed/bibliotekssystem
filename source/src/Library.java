import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Library {
    FileManager fileManager = new FileManager();
    ArrayList<Book> bookList = fileManager.loadBookData();

    public Library() throws FileNotFoundException {
    }

    public ArrayList<Book> getBooklist(){return bookList;}

    public ArrayList<Book> searchForBook(String search){
        ArrayList<Book> results = new ArrayList<>();
        for (Book i : bookList){
            if (i.getAuthor().contains(search) || i.getISBN().contains(search) || i.getLanguage().contains(search) || i.getTitle().contains(search) || i.getYear().contains(search) || i.getPages().contains(search)) {
                results.add(i);
            }
        }
        return results;
    }
}
