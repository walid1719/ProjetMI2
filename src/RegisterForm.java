package dossier_projet;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RegisterForm extends JFrame implements ActionListener {
    private JLabel nameLabel, passwordLabel, emailLabel;
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JPanel panel;

    public RegisterForm() {
        super("Inscription");

        nameLabel = new JLabel("Nom:");
        passwordLabel = new JLabel("Mot de passe:");
        emailLabel = new JLabel("Email:");

        nameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);

        registerButton = new JButton("S'inscrire");
        registerButton.addActionListener(this);

        panel = new JPanel(new GridLayout(4, 2));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(new JLabel(""));
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:8080/usersdb", "root", "");
            PreparedStatement ps = con.prepareStatement(
                    "insert into user (username, encryptedPassword, email) values (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, Cryptage_bdd.encrypt(password));
            ps.setString(3, email);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Inscription réussie!");
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription");
        }
    }

    public static void main(String[] args) {
        new RegisterForm();
    }
}
