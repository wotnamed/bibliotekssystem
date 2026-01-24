import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;

public class User {
    // very rudimentary user
    private String username;
    private String password;
    private String userID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserID() {return userID; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.userID = UUID.randomUUID().toString();
    }

    //for loading from file
    public User(String username, String password, String userID){
        this.username = username;
        this.password = password;
        this.userID = userID;
    }
}
