import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface DataManager {
    ArrayList<LibraryItem> loadLibraryItems() throws FileNotFoundException;
    ArrayList<User> loadUserData() throws FileNotFoundException;
    ArrayList<Loan> loadLoanData() throws FileNotFoundException;

    void saveUserToFile(User user) throws IOException;
    void saveLoan(Loan loan) throws IOException;
    void removeUser(String userID) throws IOException;
    void removeLoan(Loan loan) throws IOException;
}
