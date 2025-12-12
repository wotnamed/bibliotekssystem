import java.util.ArrayList;

public class User {
    // very rudimentary user
    private String username;
    private String password;
    private ArrayList<String> bookings = new ArrayList<String>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<String> bookings) {
        this.bookings = bookings;
    }

    public User(String username, String password, ArrayList<String> bookings){
        this.username = username;
        this.password = password;
        this.bookings = bookings;

    }
}
