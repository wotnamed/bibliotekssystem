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

    public void createNewCustomer(User user) throws IOException, IllegalArgumentException, AlreadyBoundException {
        if (Objects.equals(user.getUsername(), "") || Objects.equals(user.getPassword(), "")){
            throw new IllegalArgumentException();
        }
        for(User otherUser: userList){
            if(Objects.equals(otherUser.getUsername(), user.getUsername())){
                throw new AlreadyBoundException();
            }
        }
        userList.add(user);
        fileManager.saveUserToFile(user);
    }
    public void updateLoanData() throws FileNotFoundException {
        loanList = fileManager.loadLoanData();
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
