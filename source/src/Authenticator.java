import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

public class Authenticator {
    FileManager fileManager = new FileManager();
    private ArrayList<User> userlist = fileManager.loadUserData();

    //Needed when user is removed as to not enable invalid login
    public void updateUserList() throws FileNotFoundException {
        this.userlist = fileManager.loadUserData();
    }

    public Authenticator() throws FileNotFoundException {
        this.userlist.add(new User("admin", "admin"));
        System.out.println(userlist);
    }

    public Boolean authCheck(String username, String password){
        boolean status = false;
        for(User user : this.userlist) {
            // objects.equals seems to work, "==" operator does not work.
            if (Objects.equals(username, user.getUsername()) && Objects.equals(user.getPassword(), password)) {
                status = true;
                break;
            }
        }
        return status;
    }

}
