import java.util.ArrayList;

public class Authenticator {
    private ArrayList<User> userlist = new ArrayList<User>();

    public Authenticator(){
        this.userlist.add(new User("temp", "1234", new ArrayList<String>()));
        System.out.println(userlist);
    }

    public Boolean authCheck(String username, String password){
        boolean status = false;
        for (int i = 0; i < this.userlist.size(); i++) {
            if (this.userlist.get(i).getUsername() == username && this.userlist.get(i).getPassword() == password){
                System.out.println("element checked");
                status = true;
            }else {
                System.out.println("element checked /fail");
            }
        }
        return status;
    }

}
