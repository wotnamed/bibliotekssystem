import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Paths; // thanks Lukas1!!!11!!

public class FileManager {
    private static String basePath = Paths.get("").toAbsolutePath().toString() + "\\" + "/resources/";
    // TODO check if toString() in line above is necessary or not
    private static String booksPath = basePath + "books_the_library_system.txt";
    private static String loansPath = basePath + "loans.txt";
    private static String usersPath = basePath + "users.txt";
    File file = new File(booksPath);
    Scanner scanner = new Scanner(file);

    public FileManager() throws FileNotFoundException {
    }

    public Book bookMaker(String[] bookList){
        String title = bookList[0].substring(bookList[0].indexOf(":"));
        String author = bookList[1].substring(bookList[1].indexOf(":"));
        String pages = bookList[2].substring(bookList[2].indexOf(":"));
        String language = bookList[3].substring(bookList[3].indexOf(":"));
        String year = bookList[4].substring(bookList[4].indexOf(":"));
        String ISBN = bookList[5].substring(bookList[5].indexOf(":"));

        return new Book(author, year, language, ISBN, title, pages);
    }

    public ArrayList<Book> loadData(){
        ArrayList<Book> bookList = new ArrayList<>();
        while (scanner.hasNextLine()){
            String bookInformation = scanner.nextLine();
            String[] splitData = bookInformation.split("\\|");
            bookList.add(bookMaker(splitData));

        }
        return bookList;
    }
}
