import java.util.ArrayList;
import java.util.Objects;

public class Authenticator {
    private ArrayList<User> userlist = new ArrayList<User>();

    public Authenticator(){
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
