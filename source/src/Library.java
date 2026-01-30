import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.AlreadyBoundException;
import java.util.ArrayList;
import java.util.Objects;

public class Library {
    private DataManager dataManager;
    private ArrayList<LibraryItem> bookList;
    private ArrayList<User> userList;
    private ArrayList<Loan> loanList;
    private SimpleSearch simpleSearch;

    public Library(DataManager dataManager) throws FileNotFoundException {
        this.dataManager = dataManager;
        this.bookList = dataManager.loadLibraryItems();
        this.userList = dataManager.loadUserData();
        this.loanList = dataManager.loadLoanData();
        this.simpleSearch = new SimpleSearch();


    }

    public ArrayList<LibraryItem> getLibraryItems(){return bookList;}

    public void changeUserPassword(String userID, String newPassword) throws IOException, IllegalArgumentException {
        if (Objects.equals(newPassword, "")) {
            throw new IllegalArgumentException();
        }
        for (User user : userList){
            if (Objects.equals(userID, user.getUserID())){
                dataManager.removeUser(user.getUserID());
                user.setPassword(newPassword);
                dataManager.saveUserToFile(user);
                updateUserData();
            }
        }
    }

    public void createNewCustomer(String username, String password) throws IOException, IllegalArgumentException, AlreadyBoundException {
        User user = new User(username, password);
        // Vulnerability: users with equal userID:s...
        // No empty usernames or passwords
        if (Objects.equals(user.getUsername(), "") || Objects.equals(user.getPassword(), "")){
            throw new IllegalArgumentException();
        }
        // No taken usernames
        for(User otherUser: userList){
            if(Objects.equals(otherUser.getUsername(), user.getUsername())){
                throw new AlreadyBoundException();
            }
        }
        userList.add(user);
        dataManager.saveUserToFile(user);
    }

    public void deleteCustomer(User user) throws IOException {
        ArrayList<Loan> userLoans = getUserLoans(user);
        dataManager.removeUser(user.getUserID());
        for (Loan loan : userLoans){
            returnLoan(loan);
        }
    }

    public void updateLoanData() throws FileNotFoundException {
        loanList = dataManager.loadLoanData();
    }

    public void updateUserData() throws FileNotFoundException {
        userList = dataManager.loadUserData();
    }
    public void loanBook(LibraryItem item, User user) throws IOException, AssertionError {
        ArrayList<Loan> userLoans = getUserLoans(user);
        // We don't want users to be able to loan multiple (infinite) copies of each book so here is a way of stopping that.
        String takenISBN = item.getISBN();
        for(Loan loan: userLoans){
            if (Objects.equals(loan.getISBN(), takenISBN)){
                throw new AssertionError();
            }
        }
        Loan loan = new Loan(user.getUserID(), item.getISBN());
        dataManager.saveLoan(loan);
    }

    public void returnLoan(Loan loan) throws IOException {
        loanList.remove(loan);
        dataManager.removeLoan(loan);
        updateLoanData();
    }

    public ArrayList<Loan> getUserLoans(User user) throws FileNotFoundException {
        updateLoanData();
        ArrayList<Loan> myLoans = new ArrayList<Loan>();
        for(Loan loan: loanList){
            if(loan.getUserID().equals(user.getUserID())){
                myLoans.add(loan);
            }
        }
        return myLoans;
    }

    public ArrayList<LibraryItem> searchForBook(String search){
        return simpleSearch.searchForLibraryItem(search, bookList);
    }
}
