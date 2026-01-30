import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class UserFileManager extends FileManagerMaker{
    private final static String basePath = Paths.get("").toAbsolutePath() + File.separator + "resources" + File.separator;
    private static final String userPath = basePath + "users.txt";

    public User userMaker(String[] userList){
        String username = userList[0].substring(userList[0].indexOf(":") + 1).trim();
        String password = userList[1].substring(userList[1].indexOf(":") + 1).trim();
        String userID = userList[2].substring(userList[2].indexOf(":") + 1).trim();

        return new User(username, password, userID);
    }

    public ArrayList<User> loadUserData() throws FileNotFoundException {
        File userFile = new File(userPath);
        ArrayList<User> userList = new ArrayList<>();

        try (Scanner userScanner = new Scanner(userFile)) {
            while (userScanner.hasNextLine()) {
                String userInformation = userScanner.nextLine();
                if (userInformation.trim().isEmpty()) continue;
                String[] splitData = userInformation.split("\\|");
                userList.add(userMaker(splitData));
            }
        }
        return userList;
    }

    public void saveUserToFile(User user) throws IOException {
        try (FileWriter fw = new FileWriter(userPath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)){

            String userLine = "username:" + user.getUsername() +
                    "|password:" + user.getPassword() +
                    "|userid:" + user.getUserID();

            out.println(userLine);
        }
    }

    public void removeUser(String userID) throws IOException {
        ArrayList<User> users = loadUserData();
        users.removeIf(user -> user.getUserID().equals(userID));
        clearFile(userPath);
        for(User user: users){
            saveUserToFile(user);
        }
    }
}

