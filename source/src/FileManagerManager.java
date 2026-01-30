import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileManagerManager implements DataManager {
    private UserFileManager userFileManager = new UserFileManager("books_the_library_system.txt");
    private LoanFileManager loanFileManager = new LoanFileManager("loans.txt");
    private LibraryItemFileManager libraryItemFileManager = new LibraryItemFileManager("users.txt");

    public ArrayList<LibraryItem> loadLibraryItems() throws FileNotFoundException {
        return libraryItemFileManager.loadLibraryItems();
    }

    public ArrayList<User> loadUserData() throws FileNotFoundException {
        return userFileManager.loadUserData();
    }

    public ArrayList<Loan> loadLoanData() throws FileNotFoundException {
        return loanFileManager.loadLoanData();
    }

    public void saveUserToFile(User user) throws IOException {
        userFileManager.saveUserToFile(user);
    }

    public void saveLoan(Loan loan) throws IOException {
        loanFileManager.saveLoan(loan);
    }

    public void removeUser(String userID) throws IOException {
        userFileManager.removeUser(userID);
    }

    public void removeLoan(Loan loan) throws IOException {
        loanFileManager.removeLoan(loan);
    }
}
