import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class LoanFileManager extends FileManagerMaker{
    private static final String basePath = Paths.get("").toAbsolutePath() + File.separator + "resources" + File.separator;
    private static final String loanPath = basePath + "loans.txt";

    public Loan loanMaker(String[] loanData) {
        String userID = loanData[0];
        String ISBN = loanData[1];
        LocalDate loanDate = LocalDate.parse(loanData[2]);
        LocalDate returnDate = LocalDate.parse(loanData[3]);

        return new Loan(userID, ISBN, loanDate, returnDate);
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
    public void saveLoan(Loan loan) throws FileNotFoundException{
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(loanPath, true)))){
            out.println(loan.getUserID() + "|" + loan.getISBN() + "|" + loan.getLoanDate() + "|" + loan.getReturnDate());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeLoan(Loan givenLoan) throws IOException{
        String userID = givenLoan.getUserID();
        String ISBN = givenLoan.getISBN();
        ArrayList<Loan> loans = loadLoanData();
        loans.removeIf(loan -> loan.getUserID().equals(userID) && loan.getISBN().equals(ISBN));
        this.clearFile(loanPath);
        for(Loan loan: loans){
            saveLoan(loan);
        }
    }
}
