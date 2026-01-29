import javax.swing.*;
import java.awt.*;
import java.lang.reflect. *;
import java.io.*;
import java.nio.channels.AlreadyBoundException;
import java.util.ArrayList;

public class Main {
    private DataManager fileManager = new FileManager();// maybe JFrame frame should be located here?
    private Library library = new Library(fileManager);
    private Authenticator auth = new Authenticator();
    private User activeUser;
    public Main() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Main().setup();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setup() throws FileNotFoundException {
        // moved setup code to separate function to be able to call createLoginView() multiple times without creating new windows.
        JFrame frame = new JFrame("Init");
        frame.setSize(600, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        transitionLoginView(frame);
    }

    private void createLoginView(JFrame frame) throws FileNotFoundException {
        library.updateUserData();
        frame.setTitle("Login Window");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Log In");
        JButton createAccountButton = new JButton("Create Account");

        usernameField.setMaximumSize(new Dimension(200, 30));
        passwordField.setMaximumSize(new Dimension(200, 30));

        panel.add(centerComponent(new JLabel("Username:")));
        panel.add(centerComponent(usernameField));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(centerComponent(new JLabel("Password:")));
        panel.add(centerComponent(passwordField));
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        panel.add(centerComponent(loginButton));
        panel.add(centerComponent(createAccountButton));
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);

        // update auth
        auth.updateUserList();

        loginButton.addActionListener(e -> {
            if (auth.authCheck(usernameField.getText(), passwordField.getText())) {
                transitionSearchView(frame);
                activeUser = auth.getUser(usernameField.getText());
                System.out.println("Logged in as " + activeUser.getUserID());
            } else {
                System.out.println("Incorrect username or password!");
            }
        });

        createAccountButton.addActionListener( e -> {
            transitionCreateAccountView(frame);
        });
    }

    private void createAccountView(JFrame frame){
        frame.setTitle("Create Account");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton confirmButton = new JButton("Confirm");
        JButton backToLoginView = new JButton("Back");

        usernameField.setMaximumSize(new Dimension(200, 30));
        passwordField.setMaximumSize(new Dimension(200, 30));

        panel.add(centerComponent(new JLabel("Username:")));
        panel.add(centerComponent(usernameField));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(centerComponent(new JLabel("Password:")));
        panel.add(centerComponent(passwordField));
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        panel.add(centerComponent(confirmButton));
        panel.add(centerComponent(backToLoginView));

        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
        confirmButton.addActionListener( e -> {
            try {
                library.createNewCustomer(usernameField.getText(), passwordField.getText());
                System.out.println("Account created.");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (AlreadyBoundException ex) {
                System.out.println("Username taken!");
            } catch (IllegalArgumentException ex) {
                System.out.println("Username and password fields must not be empty!");
            }
        });

        backToLoginView.addActionListener( e -> {
            try {
                transitionLoginView(frame);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void transitionCreateAccountView(JFrame frame){
        frame.getContentPane().removeAll();
        createAccountView(frame);
        frame.revalidate();
        frame.repaint();
    }

    private void transitionSearchView(JFrame frame){
        frame.getContentPane().removeAll();
        createSearchView(frame);
        frame.revalidate();
        frame.repaint();
    }

    private void transitionLoginView(JFrame frame) throws FileNotFoundException {
        frame.getContentPane().removeAll();
        createLoginView(frame);
        frame.revalidate();
        frame.repaint();
    }

    private void createSearchView(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.setTitle("Search");

        // Top search bar
        JPanel topPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchField);
        topPanel.add(searchButton);

        // garbage search button
        JButton myLoansButton = new JButton("My loans");
        topPanel.add(myLoansButton);

        // settings button
        JButton userSettingsButton = new JButton("Settings");
        topPanel.add(userSettingsButton);

        // logout button
        JButton logOutButton = new JButton("Log out");
        topPanel.add(logOutButton);

        // Center scrollable area for the 3 boxes
        JPanel boxesContainer = new JPanel();
        boxesContainer.setLayout(new BoxLayout(boxesContainer, BoxLayout.Y_AXIS));

        addTitledBoxesForLibraryItemInList(library.getLibraryItems(), boxesContainer);

        JScrollPane scrollPane = new JScrollPane(boxesContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Search method, thank you!
        searchButton.addActionListener(e -> {
            boxesContainer.removeAll();
            addTitledBoxesForLibraryItemInList(library.searchForBook(searchField.getText()), boxesContainer);
            frame.revalidate();
            frame.repaint();
        });

        frame.add(panel);

        myLoansButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            try {
                createLoanView(frame);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            frame.revalidate();
            frame.repaint();
        });

        logOutButton.addActionListener( e ->{
            try {
                transitionLoginView(frame);
                activeUser = null;
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        userSettingsButton.addActionListener(e ->{
            try {
                transitionUserSettingsView(frame);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private JPanel createItemBox(LibraryItem item) {
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.setBorder(BorderFactory.createTitledBorder(item.getTitle()));
        //         panel.setPreferredSize(new Dimension(300, 200));
        boxPanel.add(new JLabel("------------------------------------------------------------"));
        /*for (Method method : methods){
            if (method.getName().startsWith("get") && method.getParameterCount() == 0) {
                try {
                    Object returnValue = method.invoke(book);
                    boxPanel.add(new JLabel(String.valueOf(returnValue)));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }*/ //legacy code by Mr Ragebait the Second
        item.getDisplayInfo().forEach((label, value) -> {boxPanel.add(new JLabel(label + value));
        });
        boxPanel.add(new JLabel("------------------------------------------------------------"));

        JPanel buttonPanel = new JPanel();
        // boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));

        JButton borrowButton = new JButton("Borrow");
        buttonPanel.add(borrowButton);
        boxPanel.add(buttonPanel);

        borrowButton.addActionListener( e->{
            try {
                library.loanBook(item, activeUser);
                System.out.println("Book '"  + item.getTitle() + "' successfully borrowed.");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (AssertionError ex) {
                System.out.println("You're already borrowing this book!");
            }
        });

        return boxPanel;
    }

    private JPanel createLoanBox(Loan loan) {
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.setBorder(BorderFactory.createTitledBorder(loan.getISBN()));
        //         panel.setPreferredSize(new Dimension(300, 200));
        boxPanel.add(new JLabel("------------------------------------------------------------"));
        boxPanel.add(new JLabel("ISBN: " + loan.getISBN()));
        boxPanel.add(new JLabel("Borrow date: " + loan.getLoanDate()));
        boxPanel.add(new JLabel("Return date: " + loan.getReturnDate()));
        boxPanel.add(new JLabel("Loaned by: " + loan.getUserID()));
        boxPanel.add(new JLabel("------------------------------------------------------------"));

        JPanel buttonPanel = new JPanel();
        JButton returnButton = new JButton("Return");
        buttonPanel.add(returnButton);
        boxPanel.add(buttonPanel);

        returnButton.addActionListener(e ->{
            try {
                library.returnLoan(loan);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        return boxPanel;
    }

    private void addTitledBoxesForLibraryItemInList(ArrayList<LibraryItem> itemList, JPanel boxesContainer){
        for(LibraryItem item : itemList){
        boxesContainer.add(createItemBox(item));
        boxesContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        }
    }

    private void addTitledBoxesForLoanInList(ArrayList<Loan> loanList, JPanel boxesContainer){
        for(Loan loan : loanList){
            boxesContainer.add(createLoanBox(loan));
            boxesContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        }
    }

    private Component centerComponent(Component comp) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(comp);
        return wrapper;
    }

    private void createLoanView(JFrame frame) throws FileNotFoundException {
        JPanel loanPanel = new JPanel();
        loanPanel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel titleText = new JLabel();
        titleText.setText("My Loans");
        frame.setTitle("Loans");

        headerPanel.add(titleText);
        JButton homePageButton = new JButton("Home");
        headerPanel.add(homePageButton);

        JButton refreshButton = new JButton("Refresh page");
        headerPanel.add(refreshButton);

        JPanel boxesContainer = new JPanel();
        boxesContainer.setLayout(new BoxLayout(boxesContainer, BoxLayout.Y_AXIS));

        Book defaultBook = new Book("", "", "", "", "", "");

        // Add three titled boxes

        addTitledBoxesForLoanInList(library.getUserLoans(activeUser), boxesContainer);

        JScrollPane scrollPane = new JScrollPane(boxesContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        loanPanel.add(headerPanel, BorderLayout.NORTH);
        loanPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(loanPanel);

        homePageButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            createSearchView(frame);
            frame.revalidate();
            frame.repaint();
        });

        refreshButton.addActionListener(e-> {
            try {
                frame.getContentPane().removeAll();
                createLoanView(frame);
                frame.revalidate();
                frame.repaint();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void transitionUserSettingsView(JFrame frame) throws FileNotFoundException {
        frame.getContentPane().removeAll();
        createUserSettingsView(frame);
        frame.revalidate();
        frame.repaint();
    }

    private void createUserSettingsView(JFrame frame) throws FileNotFoundException {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel titleText = new JLabel("Settings");
        frame.setTitle("Settings");
        headerPanel.add(titleText);

        JButton homePageButton = new JButton("Home");
        headerPanel.add(homePageButton);
        JButton refreshButton = new JButton("Refresh page");
        headerPanel.add(refreshButton);


        JPanel boxesContainer = new JPanel();
        boxesContainer.setLayout(new BoxLayout(boxesContainer, BoxLayout.Y_AXIS));

        settingsPanel.add(headerPanel, BorderLayout.NORTH);
        settingsPanel.add(boxesContainer);
        frame.add(settingsPanel);

        homePageButton.addActionListener(e ->{
            frame.getContentPane().removeAll();
            createSearchView(frame);
            frame.revalidate();
            frame.repaint();
        });

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBorder(BorderFactory.createTitledBorder("User details"));
        userPanel.add(new JLabel("Username: " +activeUser.getUsername()));
        userPanel.add(new JLabel("Password: " +activeUser.getPassword()));
        userPanel.add(new JLabel("UserID: " +activeUser.getUserID()));
        JButton deleteUserButton = new JButton("Delete Account");
        userPanel.add(deleteUserButton);

        JTextField passwordField = new JTextField(20);
        JButton changePasswordButton = new JButton("Change password");

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Change password"));
        passwordPanel.add(passwordField);
        passwordPanel.add(changePasswordButton);
        passwordPanel.setMaximumSize(new Dimension(200,80));




        deleteUserButton.addActionListener(e ->{
            try {
                library.deleteCustomer(activeUser);
                activeUser = null;
                transitionLoginView(frame);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        refreshButton.addActionListener(e-> {
            try {
                frame.getContentPane().removeAll();
                createUserSettingsView(frame);
                frame.revalidate();
                frame.repaint();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        changePasswordButton.addActionListener(e ->{
            try {
                library.changeUserPassword(activeUser.getUserID(), passwordField.getText());
                auth.updateUserList();
                activeUser = auth.getUser(activeUser.getUsername());
                System.out.println("Password changed.");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalArgumentException ex) {
                System.out.println("Password field must not be empty!");
            }
        });

        boxesContainer.add(userPanel);
        boxesContainer.add(passwordPanel);


    }
}