import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionUtilisateurFrame extends JFrame {

    private JTable userTable;
    private JScrollPane scrollPane;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private DefaultTableModel model;

    public GestionUtilisateurFrame() {
        super("Gestion des utilisateurs");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);

        model = new DefaultTableModel();
        model.addColumn("Nom d'utilisateur");
        model.addColumn("Mot de passe");
        model.addRow(new Object[]{"admin", "admin"}); // Ajouter un utilisateur par défaut

        userTable = new JTable(model);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(userTable);

        addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AjouterUtilisateurDialog dialog = new AjouterUtilisateurDialog(GestionUtilisateurFrame.this);
                dialog.setVisible(true);
                if (dialog.isOK()) {
                    String username = dialog.getUsername();
                    String password = dialog.getPassword();
                    model.addRow(new Object[]{username, password});
                }
            }
        });

        editButton = new JButton("Modifier");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = userTable.getSelectedRow();
                if (row >= 0) {
                    String username = (String) model.getValueAt(row, 0);
                    String password = (String) model.getValueAt(row, 1);
                    ModifierUtilisateurDialog dialog = new ModifierUtilisateurDialog(GestionUtilisateurFrame.this, username, password);
                    dialog.setVisible(true);
                    if (dialog.isOK()) {
                        String newUsername = dialog.getUsername();
                        String newPassword = dialog.getPassword();
                        model.setValueAt(newUsername, row, 0);
                        model.setValueAt(newPassword, row, 1);
                    }
                } else {
                    JOptionPane.showMessageDialog(GestionUtilisateurFrame.this, "Veuillez sélectionner un utilisateur à modifier.");
                }
            }
        });

        deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = userTable.getSelectedRow();
                if (row >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(GestionUtilisateurFrame.this, "Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        model.removeRow(row);
                    }
                } else {
                    JOptionPane.showMessageDialog(GestionUtilisateurFrame.this, "Veuillez sélectionner un utilisateur à supprimer.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
    }

    public static void main(String[] args) {
        GestionUtilisateurFrame frame = new GestionUtilisateurFrame();
        frame.setVisible(true);
    }
}
