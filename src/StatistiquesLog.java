
    import javax.swing.*;
import javax.swing.table.DefaultTableModel;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.sql.*;

    public class StatistiquesLog extends JFrame {
        private JTable table,table1;
        private DefaultTableModel model,model1;

        public StatistiquesLog() {
            setTitle("Analyse de journalisation");
            setSize(500, 500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            /*
            Bouton fermer
             */

            JButton btnFermer = new JButton("Fermer");

            //  le bouton "fermer"
            btnFermer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            // Ajouter le bouton "fermer" au JPanel principal
            JPanel panel = new JPanel();



            model = new DefaultTableModel();
            model.addColumn("Date");
            model.addColumn("Type");
            model.addColumn("Total");
            model1 = new DefaultTableModel();
            model1.addColumn("Date");
            model1.addColumn("User");
            model1.addColumn("Total");


            table = new JTable(model);
            table1 = new JTable(model1);
            JScrollPane scrollPane = new JScrollPane(table);
            JScrollPane scrollPane1 = new JScrollPane(table1);

            JLabel titre1 = new JLabel("Nombre d'opérations par type");
            JScrollPane tr1 = new JScrollPane(titre1);
            titre1.setBounds(40,40,200,40);
            JLabel titre2 = new JLabel("Nombre d'opérations par user");
            JScrollPane tr2 = new JScrollPane(titre2);
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
            panel.add(tr1);

            panel.add(scrollPane);

            panel.add(tr2);
            panel.add(scrollPane1);
            panel.add(btnFermer);

            getContentPane().add(panel);

            try {

                Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/usersDB?serverTimezone=UTC", "root", "walid2023");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT DATE(temps) as day,COUNT(*) AS total, type FROM log l,status s where l.type = s.id  GROUP BY day, type");
                while (rs.next()) {
                    String[] row = {rs.getString("day"),rs.getString("type"), rs.getString("total")};
                    model.addRow(row);
                }
                ResultSet rs1 = stmt.executeQuery("SELECT DATE(temps) as day,COUNT(*) AS total, iduser  FROM log l,status s,user u where l.type = s.id and u.id = l.iduser GROUP BY day, iduser");
                while (rs1.next()) {
                    String[] row = {rs1.getString("day"),rs1.getString("iduser"), rs1.getString("total")};
                    model1.addRow(row);
                }

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            setVisible(true);
        }

        public static void main(String[] args) {
            new StatistiquesLog();
        }
    }

