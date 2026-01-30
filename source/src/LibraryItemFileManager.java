import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryItemFileManager extends FileManagerMaker{
    private static final String basePath = Paths.get("").toAbsolutePath() + File.separator + "resources" + File.separator;
    private static final String bookPath = basePath + "books_the_library_system.txt";

    public LibraryItem bookMaker(String[] bookList) {
        String title = bookList[0].substring(bookList[0].indexOf(":") + 1).trim();
        String author = bookList[1].substring(bookList[1].indexOf(":") + 1).trim();
        String pages = bookList[2].substring(bookList[2].indexOf(":") + 1).trim();
        String language = bookList[3].substring(bookList[3].indexOf(":") + 1).trim();
        String year = bookList[4].substring(bookList[4].indexOf(":") + 1).trim();
        String ISBN = bookList[5].substring(bookList[5].indexOf(":") + 1).trim();

        return new Book(author, year, language, ISBN, title, pages);
    }

    public ArrayList<LibraryItem> loadLibraryItems() throws FileNotFoundException {
        File bookfile = new File(bookPath);
        ArrayList<LibraryItem> bookList = new ArrayList<>();
        try (Scanner bookScanner = new Scanner(bookfile)) {
            while (bookScanner.hasNextLine()) {
                String bookInformation = bookScanner.nextLine();
                if (bookInformation.trim().isEmpty()) continue;
                String[] splitData = bookInformation.split("\\|");
                bookList.add(bookMaker(splitData));
            }
        }
        return bookList;
    }
}
