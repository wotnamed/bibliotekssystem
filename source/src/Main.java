import javax.swing.*;
import java.awt.*;
import java.lang.reflect. *;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createLoginView());
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
                frame.getContentPane().removeAll();
                createSearchView(frame);
                frame.revalidate();
                frame.repaint();
            } else {
                System.out.println("Invalid username or password!");
            }
        });
    }

    private void createSearchView(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Top search bar
        JPanel topPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchField);
        topPanel.add(searchButton);

        // Center scrollable area for the 3 boxes
        JPanel boxesContainer = new JPanel();
        boxesContainer.setLayout(new BoxLayout(boxesContainer, BoxLayout.Y_AXIS));

        // Add titled boxes
        //Todo: use addTitledBoxesForAllBooksInList() with library books

        JScrollPane scrollPane = new JScrollPane(boxesContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
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

    private void addTitledBoxesForAllBooksInList(Book[] bookList, JPanel boxesContainer){
        for(Book book : bookList){
        boxesContainer.add(createSampleBox(book));
        boxesContainer.add(Box.createRigidArea(new Dimension(0, 15)));}
    }

    private Component centerComponent(Component comp) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(comp);
        return wrapper;
    }
}