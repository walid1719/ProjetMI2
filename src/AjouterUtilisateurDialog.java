import javax.swing.*;

public class AjouterUtilisateurDialog extends JDialog {

    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton ajouterButton;
    private JButton annulerButton;
    private boolean isOK;

    public AjouterUtilisateurDialog(JFrame parent) {
        super(parent, "Ajouter un utilisateur", true);

        // Initialisation des composants
        usernameLabel = new JLabel("Nom d'utilisateur:");
        usernameField = new JTextField(20);
        passwordLabel = new JLabel("Mot de passe:");
        passwordField = new JPasswordField(20);
        ajouterButton = new JButton("Ajouter");
        annulerButton = new JButton("Annuler");
        isOK = false;

        // Ajout des composants au conteneur de la boîte de dialogue
        JPanel panel = new JPanel();
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(ajouterButton);
        panel.add(annulerButton);
        getContentPane().add(panel);

        // Définition des actions des boutons
        ajouterButton.addActionListener(e -> {
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