import javax.swing.*;
import java.awt.*;
import java.lang.reflect. *;
import java.io.*;
import java.util.ArrayList;

public class Main {
    Library library = new Library();
    public Main() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Main().createLoginView();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void createLoginView() {
        JFrame frame = new JFrame("Login Window");
        frame.setSize(600, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Log In");

        usernameField.setMaximumSize(new Dimension(200, 30));
        passwordField.setMaximumSize(new Dimension(200, 30));

        panel.add(centerComponent(new JLabel("Username:")));
        panel.add(centerComponent(usernameField));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(centerComponent(new JLabel("Password:")));
        panel.add(centerComponent(passwordField));
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        panel.add(centerComponent(loginButton));

        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);

        // load authentication
        Authenticator auth = new Authenticator();

        loginButton.addActionListener(e -> {
            System.out.println("User: "+usernameField.getText());
            System.out.println("Very-secure password: "+passwordField.getText());

            if (auth.authCheck(usernameField.getText(), passwordField.getText())) {
                transitionSearchView(frame);
            } else {
                System.out.println("Invalid username or password!");
            }
        });
    }

    private void transitionSearchView(JFrame frame){
        frame.getContentPane().removeAll();
        createSearchView(frame);
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

        // Center scrollable area for the 3 boxes
        JPanel boxesContainer = new JPanel();
        boxesContainer.setLayout(new BoxLayout(boxesContainer, BoxLayout.Y_AXIS));

        addTitledBoxesForBookInList(library.getBooklist(), boxesContainer);

        JScrollPane scrollPane = new JScrollPane(boxesContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Search method thank you!
        searchButton.addActionListener(e -> {
            boxesContainer.removeAll();
            addTitledBoxesForBookInList(library.searchForBook(searchField.getText()), boxesContainer);
            frame.revalidate();
            frame.repaint();
        }
        );

        frame.add(panel);

        //???
        myLoansButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            createLoanView(frame);
            frame.revalidate();
            frame.repaint();
        });
    }

    private JPanel createSampleBox(Book book) {
        Class<?> thisBook = Book.class;
        Method[] methods = thisBook.getDeclaredMethods();
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.setBorder(BorderFactory.createTitledBorder(book.getTitle()));
        for (Method method : methods){
            if (method.getName().startsWith("get") && method.getParameterCount() == 0) {
                try {
                    Object returnValue = method.invoke(book);
                    boxPanel.add(new JLabel(String.valueOf(returnValue)));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return boxPanel;
    }

    private void addTitledBoxesForBookInList(ArrayList<Book> bookList, JPanel boxesContainer){
        for(Book book : bookList){
        boxesContainer.add(createSampleBox(book));
        boxesContainer.add(Box.createRigidArea(new Dimension(0, 15)));}
    }

    private Component centerComponent(Component comp) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(comp);
        return wrapper;
    }

    private void createLoanView(JFrame frame){
        JPanel loanPanel = new JPanel();
        loanPanel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JLabel titleText = new JLabel();
        titleText.setText("My Loans");
        frame.setTitle("Loans");

        headerPanel.add(titleText);
        JButton homePageButton = new JButton("Home");
        headerPanel.add(homePageButton);

        JPanel boxesContainer = new JPanel();
        boxesContainer.setLayout(new BoxLayout(boxesContainer, BoxLayout.Y_AXIS));

        Book defaultBook = new Book("", "", "", "", "", "");
        // Add three titled boxes
        boxesContainer.add(createSampleBox(defaultBook));
        boxesContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        boxesContainer.add(createSampleBox(defaultBook));
        boxesContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        boxesContainer.add(createSampleBox(defaultBook));

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
    }
}