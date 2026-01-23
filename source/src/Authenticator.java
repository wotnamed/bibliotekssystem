import java.util.ArrayList;
import java.util.Objects;

public class Authenticator {
    private ArrayList<User> userlist = new ArrayList<User>();

    public Authenticator(){
        this.userlist.add(new User("f", "f"));
        System.out.println(userlist);
    }

    public Boolean authCheck(String username, String password){
        boolean status = false;
        for (int i = 0; i < this.userlist.size(); i++) {
            User user = this.userlist.get(i);
            // objects.equals seems to work, "==" operator does not work.
            if (Objects.equals(username, user.getUsername()) && Objects.equals(user.getPassword(), password)){
                status = true;
            }
        }
        return status;
    }

}
