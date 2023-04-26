import javax.swing.*;

public class ModifierUtilisateurDialog extends JDialog {

    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton modifierButton;
    private JButton annulerButton;
    private boolean isOK;

    public ModifierUtilisateurDialog(JFrame parent, String username, String password) {
        super(parent, "Modifier un utilisateur", true);

        // Initialisation des composants
        usernameLabel = new JLabel("Nom d'utilisateur:");
        usernameField = new JTextField(20);
        usernameField.setText(username);
        usernameField.setEditable(false); // Le nom d'utilisateur ne peut pas être modifié
        passwordLabel = new JLabel("Mot de passe:");
        passwordField = new JPasswordField(20);
        passwordField.setText(password);
        modifierButton = new JButton("Modifier");
        annulerButton = new JButton("Annuler");
        isOK = false;

        // Ajout des composants au conteneur de la boîte de dialogue
        JPanel panel = new JPanel();
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(modifierButton);
        panel.add(annulerButton);
        getContentPane().add(panel);

        // Définition des actions des boutons
        modifierButton.addActionListener(e -> {
            isOK = true;
            dispose(); // Fermer la boîte de dialogue
        });

        annulerButton.addActionListener(e -> {
            isOK = false;
            dispose(); // Fermer la boîte de dialogue
        });

        // Affichage de la boîte de dialogue
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public boolean isOK() {
        return isOK;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}

