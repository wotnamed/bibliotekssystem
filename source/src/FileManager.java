import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Paths; // thanks Lukas1!!!11!!

public class FileManager {
    private static final String basePath = Paths.get("").toAbsolutePath() + File.separator + "resources" + File.separator;
    private static final String bookPath = basePath + "books_the_library_system.txt";
    private static final String userPath = basePath + "users.txt";
    private static final String loanPath = basePath + "loans.txt";

    public Book bookMaker(String[] bookList){
        String title = bookList[0].substring(bookList[0].indexOf(":") + 1).trim();
        String author = bookList[1].substring(bookList[1].indexOf(":") + 1).trim();
        String pages = bookList[2].substring(bookList[2].indexOf(":") + 1).trim();
        String language = bookList[3].substring(bookList[3].indexOf(":") + 1).trim();
        String year = bookList[4].substring(bookList[4].indexOf(":") + 1).trim();
        String ISBN = bookList[5].substring(bookList[5].indexOf(":") + 1).trim();

        return new Book(author, year, language, ISBN, title, pages);
    }

    public ArrayList<Book> loadBookData() throws FileNotFoundException{
        File bookfile = new File(bookPath);
        ArrayList<Book> bookList = new ArrayList<>();

        try (Scanner bookScanner = new Scanner(bookfile)){
        while (bookScanner.hasNextLine()){
            String bookInformation = bookScanner.nextLine();
            if (bookInformation.trim().isEmpty()) continue;
            String[] splitData = bookInformation.split("\\|");
            bookList.add(bookMaker(splitData));
            }
        }
        return bookList;
    }
    public User userMaker(String[] userList){
        String username = userList[0].substring(userList[0].indexOf(":") + 1).trim();
        String password = userList[1].substring(userList[1].indexOf(":") + 1).trim();
        String userID = userList[2].substring(userList[2].indexOf(":") + 1).trim();

        return new User(username, password, userID);
    }
    public ArrayList<Loan> loadLoanData() throws FileNotFoundException {
        File loanFile = new File(loanPath);
        ArrayList<Loan> bookList = new ArrayList<>();

        try (Scanner bookScanner = new Scanner(loanFile)) {
            while (bookScanner.hasNextLine()) {
                String bookInformation = bookScanner.nextLine();
                if (bookInformation.trim().isEmpty()) continue;

                String[] splitData = bookInformation.split("\\|");
                bookList.add(loanMaker(splitData));
            }
        }
        return bookList;
    }

    public Loan loanMaker(String[] loanData) {
        String userID = loanData[0];
        String ISBN = loanData[1];
        LocalDate loanDate = LocalDate.parse(loanData[2]);
        LocalDate returnDate = LocalDate.parse(loanData[3]);

        return new Loan(userID, ISBN, loanDate, returnDate);
    }


    public ArrayList<User> loadUserData() throws FileNotFoundException {
        File userFile = new File(userPath);
        ArrayList<User> userList = new ArrayList<>();

        try (Scanner userScanner = new Scanner(userFile)) {
            while (userScanner.hasNextLine()) {
                String userInformation = userScanner.nextLine();
                if (userInformation.trim().isEmpty()) continue;
                String[] splitData = userInformation.split("\\|");
                userList.add(userMaker(splitData));
            }
        }
        return userList;
    }

    public void saveUserToFile(User user) throws IOException {
        try (FileWriter fw = new FileWriter(userPath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)){

            String userLine = "username:" + user.getUsername() +
                    "|password:" + user.getPassword() +
                    "|userid:" + user.getUserID();

            out.println(userLine);
        }
    }

    public void saveLoan(Loan loan) throws FileNotFoundException{
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(loanPath, true)))){
            out.println(loan.getUserID() + "|" + loan.getISBN() + "|" + loan.getLoanDate() + "|" + loan.getReturnDate());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearFile(String path) throws IOException{
        new FileWriter(path, false).close();
    }

    public void removeUser(String userID) throws IOException {
        ArrayList<User> users = loadUserData();
        users.removeIf(user -> user.getUserID().equals(userID));
        clearFile(userPath);
        for(User user: users){
            saveUserToFile(user);
        }
    }

    public void removeLoan(String userID) throws IOException{
        ArrayList<Loan> loans = loadLoanData();
        loans.removeIf(loan -> loan.getUserID().equals(userID));
        clearFile(loanPath);
        for(Loan loan: loans){
            saveLoan(loan);
        }
    }
}
