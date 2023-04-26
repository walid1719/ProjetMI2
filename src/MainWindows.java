import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindows extends JFrame {

    private JMenuBar menuBar;
    private JMenu menuLogin, menuChat, menuUsers;
    private JMenuItem itemLogin, itemLogout, itemExit, itemOpenChat, itemUserList, itemAddUser, itemEditUser, itemDeleteUser;
    static  boolean  isAdmin = false;
    static boolean isLoggedIn = false;
    public MainWindows() {
        // Définition de la taille de la fenêtre principale et du titre
        setSize(600, 400);
        setTitle("Ma fenêtre principale");

        // Création de la barre de menu
        menuBar = new JMenuBar();

        // Création du menu "Login"
        menuLogin = new JMenu("Login");

        // Création des items du menu "Login"
        itemLogin = new JMenuItem("Login");
        itemLogout = new JMenuItem("Logout");
        itemExit = new JMenuItem("Exit");

        // Ajout des items au menu "Login"
        menuLogin.add(itemLogin);
        menuLogin.add(itemLogout);
        menuLogin.addSeparator();
        menuLogin.add(itemExit);

        // Création du menu "Chat"
        menuChat = new JMenu("Chat");

        // Création de l'item du menu "Chat"
        itemOpenChat = new JMenuItem("Open chat");

        // Ajout de l'item au menu "Chat"
        menuChat.add(itemOpenChat);

        // Création du menu "Users"
        menuUsers = new JMenu("Users");

        // Création des items du menu "Users"
        itemUserList = new JMenuItem("List of users");
        itemAddUser = new JMenuItem("Add a user");
        itemEditUser = new JMenuItem("Edit a user");
        itemDeleteUser = new JMenuItem("Delete a user");

        // Ajout des items au menu "Users"
        menuUsers.add(itemUserList);
        menuUsers.add(itemAddUser);
        menuUsers.add(itemEditUser);
        menuUsers.add(itemDeleteUser);

        // Ajout des menus à la barre de menu
        menuBar.add(menuLogin);
        menuBar.add(menuChat);
        menuBar.add(menuUsers);

        // Ajout de la barre de menu à la fenêtre principale
        setJMenuBar(menuBar);
        menuChat.setVisible(false);
        menuUsers.setVisible(false);
        // Définition des droits de l'utilisateur


        itemLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                System.out.println( "main"+MainWindows.isLoggedIn );
                if (isLoggedIn) {
                    menuChat.setVisible(true);
                    menuUsers.setVisible(true);
                    System.out.println("hhhhh");
                } else {
                    menuLogin.setVisible(false);

                    if (!isAdmin) {
                        itemAddUser.setVisible(true);
                        itemEditUser.setVisible(true);
                        itemDeleteUser.setVisible(true);
                    }
                }
            }
        });


        // Masquage des menus et items en fonction des droits de l'utilisateur
        if (!isLoggedIn) {
            menuChat.setVisible(false);
            menuUsers.setVisible(false);
        } else {
            menuLogin.setVisible(false);

            if (!isAdmin) {
                itemAddUser.setVisible(false);
                itemEditUser.setVisible(false);
                itemDeleteUser.setVisible(false);
            }
        }

        // Définition de l'action à effectuer lorsque l'utilisateur clique sur l'item "Exit"
        itemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Affichage de la fenêtre principale
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindows mainWindow = new MainWindows();
    }
}
