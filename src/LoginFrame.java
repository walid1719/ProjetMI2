import javax.swing.*;
import java.awt.*;
import persistance.DB;
import persistance.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton quitterButton;
    private JMenuBar menuBar;
    private JMenu menuLogin, menuChat, menuUsers;
    private JMenuItem itemLogin, itemLogout, itemExit, itemOpenChat, itemUserList, itemAddUser, itemEditUser, itemDeleteUser;
    static  boolean  isAdmin = false;
    static  persistance.User userCurrent;
    static boolean isLoggedIn = false;

    public LoginFrame() {
        super("Login");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 200);
        setLayout(new GridLayout(4, 2, 10, 10));
        setResizable(false);
        // Création de la barre de menu
        menuBar = new JMenuBar();
        menuLogin = new JMenu("Quitter");
        itemExit = new JMenuItem("Exit");
        menuLogin.add(itemExit);
        setJMenuBar(menuBar);
        menuBar.add(menuLogin);
        menuLogin.setVisible(true);



        titleLabel = new JLabel("Messagerie instantanée");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        usernameLabel = new JLabel("Nom d'utilisateur :");
        usernameTextField = new JTextField();

        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordField = new JPasswordField();

        quitterButton = new JButton("Quitter");
        quitterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });
        loginButton = new JButton("Se connecter");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = new String(passwordField.getPassword());
                userCurrent = (new DB.RequestUser()).getUserByName(username,password);

                // Vérifier si le nom d'utilisateur et le mot de passe sont corrects
                // Ici, nous supposons que le nom d'utilisateur et le mot de passe sont "admin"

                // if (username.equals("admin") && password.equals("admin")) {
                // Ouvrir la fenêtre de chat
                if(userCurrent != null){
                    if(userCurrent.getPermission() == 1) isAdmin = true;
                    switch (userCurrent.getStatus()){
                        case 1 :
                            ClientInterface chatFrame = new ClientInterface();
                            chatFrame.run();
                            dispose(); // Fermer la fenêtre de login
                            break;
                        case 2  :
                            JOptionPane.showMessageDialog(LoginFrame.this, "Compte a été désactivé");
                            break;
                        case 3 :
                            JOptionPane.showMessageDialog(LoginFrame.this, "Compte a été banni");

                    }

                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Nom d'utilisateur ou mot de passe incorrect.");
                }
            }
        });

        add(titleLabel);
        add(new JLabel());
        add(usernameLabel);
        add(usernameTextField);
        add(passwordLabel);
        add(passwordField);
        add(quitterButton);
        add(loginButton);

        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        itemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
}