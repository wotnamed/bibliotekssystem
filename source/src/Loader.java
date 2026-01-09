import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {
    String path = "src/books_the_library_system.txt";
    File file = new File(path);
    Scanner scanner = new Scanner(file);

    public Loader() throws FileNotFoundException {
    }

    public Book bookMaker(String[] bookList){
        String title = bookList[0].substring(bookList[0].indexOf(":"));
        String author = bookList[1].substring(bookList[1].indexOf(":"));
        String pages = bookList[2].substring(bookList[2].indexOf(":"));
        String language = bookList[3].substring(bookList[3].indexOf(":"));
        String year = bookList[4].substring(bookList[4].indexOf(":"));
        String ISBN = bookList[5].substring(bookList[5].indexOf(":"));

        return new Book(title, author, pages, language, year, ISBN);
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
