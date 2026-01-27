import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Library {
    FileManager fileManager = new FileManager();
    ArrayList<Book> bookList = fileManager.loadBookData();
    ArrayList<User> userList = fileManager.loadUserData();
    ArrayList<Loan> loanList = fileManager.loadLoanData();

    public Library() throws FileNotFoundException {
    }

    public ArrayList<Book> getBooklist(){return bookList;}

    public void createNewCustomer(User user) throws IOException {
        userList.add(user);
        fileManager.saveUserToFile(user);
    }

    public void loanBook(Book book, User user) throws FileNotFoundException {
        Loan loan = new Loan(user.getUserID(), book.getISBN());
        fileManager.saveLoan(loan);
    }

    public void returnLoan(Loan loan) throws IOException {
        loanList.remove(loan);
        fileManager.removeLoan(loan.getUserID());
    }

    public ArrayList<Loan> getUserLoans(User user){
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
