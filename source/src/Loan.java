import java.time.LocalDate;

public class Loan {
    private final String userID;
    private final String ISBN;
    private final LocalDate loanDate;
    private final LocalDate returnDate;

    public Loan(String userID, String ISBN){
        this.userID = userID;
        this.ISBN = ISBN;
        this.loanDate = LocalDate.now();
        this.returnDate = loanDate.plusMonths(1);
    }

    // for loading from file
    public Loan(String userID, String ISBN, LocalDate loanDate, LocalDate returnDate){
        this.userID = userID;
        this.ISBN = ISBN;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public String getUserID(){
        return this.userID;
    }

    public String getISBN(){
        return this.ISBN;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}


