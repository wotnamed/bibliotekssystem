import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.AlreadyBoundException;
import java.util.ArrayList;
import java.util.Objects;

public class Library {
    FileManager fileManager = new FileManager();
    ArrayList<Book> bookList = fileManager.loadBookData();
    ArrayList<User> userList = fileManager.loadUserData();
    ArrayList<Loan> loanList = fileManager.loadLoanData();

    public Library() throws FileNotFoundException {
    }

    public ArrayList<Book> getBooklist(){return bookList;}

    public void changeUserPassword(String userID, String newPassword) throws IOException, IllegalArgumentException {
        if (Objects.equals(newPassword, "")) {
            throw new IllegalArgumentException();
        }
        for (User user : userList){
            if (Objects.equals(userID, user.getUserID())){
                fileManager.removeUser(user.getUserID());
                user.setPassword(newPassword);
                fileManager.saveUserToFile(user);
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
        fileManager.saveUserToFile(user);
    }

    public void deleteCustomer(User user) throws IOException {
        ArrayList<Loan> userLoans = getUserLoans(user);
        fileManager.removeUser(user.getUserID());
        for (Loan loan : userLoans){
            returnLoan(loan);
        }
    }

    public void updateLoanData() throws FileNotFoundException {
        loanList = fileManager.loadLoanData();
    }

    public void updateUserData() throws FileNotFoundException {
        userList = fileManager.loadUserData();
    }
    public void loanBook(Book book, User user) throws FileNotFoundException, AssertionError {
        ArrayList<Loan> userLoans = getUserLoans(user);
        // We don't want users to be able to loan multiple (infinite) copies of each book so here is a way of stopping that.
        String takenISBN = book.getISBN();
        for(Loan loan: userLoans){
            if (Objects.equals(loan.getISBN(), takenISBN)){
                throw new AssertionError();
            }
        }
        Loan loan = new Loan(user.getUserID(), book.getISBN());
        fileManager.saveLoan(loan);
    }

    public void returnLoan(Loan loan) throws IOException {
        loanList.remove(loan);
        fileManager.removeLoan(loan);
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
