package persistance;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DB {

    public static Connection connection=null;

    private DB() {

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/usersDB?serverTimezone=UTC", "root", "walid2023");
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public static Connection getConection() {
        if(connection==null) {
            new DB();
        }
        return connection;
    }

    public static class RequestUser {

        private Statement stm;
        private PreparedStatement pstm;
        private ResultSet rst;


        public RequestUser() {
            try {
                stm= getConection().createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * La liste des Users
         * @return
         */
        public LinkedList<User> getUsers(){

            LinkedList<User> Users=new LinkedList<>();
            try{
                String query="SELECT * FROM User";
                rst=stm.executeQuery(query);
                while(rst.next()) {
                    User user=new User();
                    user.setid(rst.getInt("id"));
                    user.setpermission(rst.getInt("permission"));
                    user.setStatus(rst.getInt("status"));
                    user.setNom(rst.getString("nom"));
                    user.setPrenom(rst.getString("prenom"));
                    user.setemail(rst.getString("email"));
                    user.setUsername(rst.getString("username"));
                    user.setPassword(rst.getString("password"));
                    user.setLast_connection(rst.getDate("last_connection"));
                    Users.add(user);
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }

            return Users;
        }
        /** get user by login and password
         *
         */
        public User getUserByName(String nom,String pw) {
            User user=new User();
            try {
                String query="SELECT * FROM user where username=? and password=?";
                pstm= getConection().prepareStatement(query);
                pstm.setString(1, nom);
                pstm.setString(2, pw);
                rst=pstm.executeQuery();
                if(rst.next()) {
                    user.setid(rst.getInt("id"));
                    user.setpermission(rst.getInt("permission"));
                    user.setStatus(rst.getInt("status"));
                    user.setNom(rst.getString("nom"));
                    user.setPrenom(rst.getString("prenom"));
                    user.setemail(rst.getString("email"));
                    user.setUsername(rst.getString("username"));
                    user.setPassword(rst.getString("password"));
                    user.setLast_connection(rst.getDate("last_connection"));
                }
                else { user = null;}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return user;
        }
        public User getUserById(int id) {
            User user=new User();
            try {
                String query="SELECT * FROM user where id=?";
                pstm= getConection().prepareStatement(query);
                pstm.setInt(1, id);

                rst=pstm.executeQuery();
                if(rst.next()) {
                    user.setid(rst.getInt("id"));
                    user.setpermission(rst.getInt("permission"));
                    user.setStatus(rst.getInt("status"));
                    user.setNom(rst.getString("nom"));
                    user.setPrenom(rst.getString("prenom"));
                    user.setemail(rst.getString("email"));
                    user.setUsername(rst.getString("username"));
                    user.setPassword(rst.getString("password"));
                    user.setLast_connection(rst.getDate("last_connection"));
                }
                else { user = null;}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return user;
        }

        /**
         * La liste des types de Users
         */
        public LinkedList<TypeUser> getUserTypes(){

            LinkedList<TypeUser> typeUsers=new LinkedList<>();
            try{
                String query="SELECT * FROM typesUser";
                rst=stm.executeQuery(query);
                while(rst.next()) {
                    TypeUser typeUser=new TypeUser();
                    typeUser.setIdTypesUser(rst.getInt("idtypesUser"));
                    typeUser.setLibelle(rst.getString("libelle"));
                    typeUsers.add(typeUser);
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }

            return typeUsers;
        }
        /**
         * La liste des status de Users
         */
        public LinkedList<Status> getStatus(){

            LinkedList<Status> statusUsers=new LinkedList<>();
            try{
                String query="SELECT * FROM status where id <=3";
                rst=stm.executeQuery(query);
                while(rst.next()) {
                    Status status=new Status();
                    status.setId(rst.getInt("id"));
                    status.setLibelle(rst.getString("libelle"));
                    statusUsers.add(status);
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            for (Status s : statusUsers){
                //System.out.println(s.getId()+"lib  :"+s.getLibelle());
            }
            return statusUsers;
        }

        /**
         * Type User par nom
         */
        public TypeUser getTypeUserByName(String libelle) {
            TypeUser typeUser=new TypeUser();
            try {
                String query="SELECT * FROM typesUser where libelle=?";
                pstm= getConection().prepareStatement(query);
                pstm.setString(1, libelle);
                rst=pstm.executeQuery();
                if(rst.next()) {
                    typeUser.setIdTypesUser(rst.getInt("idtypesUser"));
                    typeUser.setLibelle(rst.getString("libelle"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return typeUser;
        }
        /**
         * status User par nom
         */
        public Status getStatusByName(String libelle) {
            Status status=new Status();
            try {
                String query="SELECT * FROM status where libelle=?";
                pstm= getConection().prepareStatement(query);
                pstm.setString(1, libelle);
                rst=pstm.executeQuery();
                if(rst.next()) {
                    status.setId(rst.getInt("id"));
                    status.setLibelle(rst.getString("libelle"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return status;
        }


        /**
         * L'ajout d'un nouveau User
         */
        public int addUser(User User) {
            try {
                String query="INSERT INTO User(permission, nom, prenom, email,password,username,last_connection,status)"
                        + " VALUES(?, ?, ?, ?,?,?,?,?)";
                pstm= getConection().prepareStatement(query);
                pstm.setInt(1, User.getpermission());
                pstm.setInt(8, User.getStatus());
                pstm.setString(2, User.getNom());
                pstm.setString(3, User.getPrenom());
                pstm.setString(4, User.getemail());
                pstm.setString(5, User.getPassword());
                pstm.setString(6, User.getUsername());
                pstm.setDate(7, new Date(User.getLast_connection().getTime()));

                return pstm.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return 0;
            }
        }
        /* ajout d'u msg

         */
        public int addMessage(User User,String msg) {
            try {
                String query="INSERT INTO message( idUser, temps, content)"
                        + " VALUES(?, ?, ?)";
                pstm= getConection().prepareStatement(query);
                pstm.setInt(1, User.getid());
                pstm.setString(3, msg);
                pstm.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
                System.out.println(new Timestamp(System.currentTimeMillis()));
                return pstm.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return 0;
            }
        }

        /*ajoout dans le log

         */
        public int addLog(User User,int type) {
            try {
                String query="INSERT INTO log( idUser, temps, type)"
                        + " VALUES(?, ?, ?)";
                pstm= getConection().prepareStatement(query);
                pstm.setInt(1, User.getid());
                pstm.setInt(3, type);
                pstm.setTimestamp(2,new Timestamp(System.currentTimeMillis()));

                return pstm.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return 0;
            }
        }
        /**
         /**
         *
         * La supression d'un User
         */
        public int deleteUser(int id) {
            try{
                String query="DELETE FROM User WHERE id=?";
                pstm= getConection().prepareStatement(query);
                pstm.setInt(1, id);
                return pstm.executeUpdate();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            return 0;
        }

        public int updateUser(User User) {
            try {
                String query="UPDATE User SET permission=?, nom=?, prenom=?, password=?,username=?,email=?,last_connection=?,status=?"
                        + " WHERE id=?";
                pstm= getConection().prepareStatement(query);
                pstm.setInt(1, User.getpermission());
                pstm.setInt(8, User.getStatus());
                pstm.setString(2, User.getNom());
                pstm.setString(3, User.getPrenom());
                pstm.setString(4, User.getPassword());
                pstm.setString(5, User.getUsername());

                pstm.setString(6, User.getemail());

                pstm.setDate(7, (Date) User.getLast_connection());
                pstm.setInt(9,User.getid());
                return pstm.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return 0;
            }
        }

        public static class UserDialog extends JDialog {

            private JLabel usernameL,passwordL,emailL,nomLabel, prenomLabel, typeUserLabel,status;
            private JTextField usernameT,emailT,nomTextField, prenomTextField;
            private JPasswordField passwordT;
            private JComboBox<String> typeUserComboBox;
            private JComboBox<String> statusComboBox;
            private JButton okButton, cancelButton;
            private String m;

            public void setM(String m) {
                this.m = m;
                layoutControls();
            }

            public UserDialog(JFrame parent) {




                super(parent, "User", false);
                usernameL=new JLabel("Username");
                passwordL=new JLabel("password");
                emailL=new JLabel("email");
                nomLabel=new JLabel("Nom");
                prenomLabel=new JLabel("Prenom");
                typeUserLabel=new JLabel("Type");
                status =new JLabel("Status");
                usernameT=new JTextField(15);
                passwordT=new JPasswordField(15);
                emailT=new JTextField(15);
                prenomTextField=new JTextField(15);
                nomTextField=new JTextField(15);


                typeUserComboBox=new JComboBox<>();
                statusComboBox=new JComboBox<>();
                typeUserComboBox.setPreferredSize(new Dimension(165, 20));
                statusComboBox.setPreferredSize(new Dimension(165, 20));
                okButton=new JButton("Ok");
                cancelButton=new JButton("Cancel");

                setLayout(new GridBagLayout());
                layoutControls();


                setSize(400, 400);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
                setUndecorated(true);
            }

            private void layoutControls() {
                JPanel controlsPannel=new JPanel();
                JPanel buttonsPanel=new JPanel();

                int space=15;

                Border titleBorder=BorderFactory.createTitledBorder("MAJ User");

                Border spaceBorder=BorderFactory.createEmptyBorder(space, space, space, space);

                /**
                 * Controls layout
                 */
                controlsPannel.setLayout(new GridBagLayout());
                controlsPannel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));

                /**
                 * Buttons layout
                 */
                buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

                GridBagConstraints gc=new GridBagConstraints();
                Insets rightPadding=new Insets(0, 0, 0, 15);
                Insets noPadding=new Insets(0, 0, 0, 0);

                gc.fill=GridBagConstraints.NONE;
                /**


                 */
                gc.weightx=1;
                gc.weighty=1;

                gc.gridy=0;

                gc.gridx=0;
                gc.anchor=GridBagConstraints.EAST;
                gc.insets=rightPadding;
                controlsPannel.add(usernameL, gc);
                gc.gridx++;
                gc.anchor=GridBagConstraints.WEST;
                gc.insets=noPadding;
                controlsPannel.add(usernameT, gc);

                /**
                 * First row
                 */
                gc.weightx=1;
                gc.weighty=1;

                gc.gridy++;

                gc.gridx=0;
                gc.anchor=GridBagConstraints.EAST;
                gc.insets=rightPadding;
                controlsPannel.add(nomLabel, gc);
                gc.gridx++;
                gc.anchor=GridBagConstraints.WEST;
                gc.insets=noPadding;
                controlsPannel.add(nomTextField, gc);

                /**
                 * Next row
                 */
                gc.weightx=1;
                gc.weighty=1;

                gc.gridy++;

                gc.gridx=0;
                gc.anchor=GridBagConstraints.EAST;
                gc.insets=rightPadding;
                controlsPannel.add(prenomLabel, gc);
                gc.gridx++;
                gc.anchor=GridBagConstraints.WEST;
                gc.insets=noPadding;
                controlsPannel.add(prenomTextField, gc);
                //***********
                gc.weightx=1;
                gc.weighty=1;

                gc.gridy++;

                gc.gridx=0;
                gc.anchor=GridBagConstraints.EAST;
                gc.insets=rightPadding;
                controlsPannel.add(usernameL, gc);
                gc.gridx++;
                gc.anchor=GridBagConstraints.WEST;
                gc.insets=noPadding;
                controlsPannel.add(usernameT, gc);
                gc.weightx=1;
                gc.weighty=1;

                gc.gridy++;

                gc.gridx=0;
                gc.anchor=GridBagConstraints.EAST;
                gc.insets=rightPadding;
                controlsPannel.add(passwordL, gc);
                gc.gridx++;
                gc.anchor=GridBagConstraints.WEST;
                gc.insets=noPadding;
                controlsPannel.add(passwordT, gc);

                gc.weightx=1;
                gc.weighty=1;

                gc.gridy++;

                gc.gridx=0;
                gc.anchor=GridBagConstraints.EAST;
                gc.insets=rightPadding;
                controlsPannel.add(emailL, gc);
                gc.gridx++;
                gc.anchor=GridBagConstraints.WEST;
                gc.insets=noPadding;
                controlsPannel.add(emailT, gc);
                //**********
                /**
                 * Next row
                 */
                gc.weightx=1;
                gc.weighty=1;

                gc.gridy++;

                gc.gridx=0;
                gc.anchor=GridBagConstraints.EAST;
                gc.insets=rightPadding;
                controlsPannel.add(typeUserLabel, gc);
                gc.gridx++;
                gc.anchor=GridBagConstraints.WEST;
                gc.insets=noPadding;
                controlsPannel.add(typeUserComboBox, gc);
                //**********
                /**
                 * Next row
                 */
                gc.weightx=1;
                gc.weighty=1;

                gc.gridy++;

                gc.gridx=0;
                gc.anchor=GridBagConstraints.EAST;
                gc.insets=rightPadding;
                controlsPannel.add(status, gc);
                gc.gridx++;
                gc.anchor=GridBagConstraints.WEST;
                gc.insets=noPadding;
                controlsPannel.add(statusComboBox, gc);
                /**
                 * Next row
                 */
                gc.weightx=1;
                gc.weighty=1;
                gc.gridy++;

                gc.gridx=0;
                buttonsPanel.add(okButton, gc);
                gc.gridx++;
                buttonsPanel.add(cancelButton, gc);

                setLayout(new BorderLayout());
                add(controlsPannel, BorderLayout.CENTER);
                add(buttonsPanel, BorderLayout.SOUTH);
            }

            public JTextField getNomTextField() {
                return nomTextField;
            }

            public JTextField getUsernameT() {
                return usernameT;
            }


            public JTextField getPasswordT() {
                return passwordT;
            }

            public JTextField getEmailT() {
                return emailT;
            }

            public JTextField getPrenomTextField() {
                return prenomTextField;
            }

            public JComboBox<String> getTypeUserComboBox() {
                return typeUserComboBox;
            }
            public JComboBox<String> getStatusComboBox() {
                return statusComboBox;
            }

            public JButton getOkButton() {
                return okButton;
            }

            public JButton getCancelButton() {
                return cancelButton;
            }

        }

        public static class UserTableModel extends AbstractTableModel {

            /**
             * Les colonnes a afficher
             */
            // String[] colonnes=new String[] {"id",  "nom", "prenom","userName","passWord", "email","idTypesUser"};
            String[] colonnes=new String[] {"id",  "nom", "prenom","userName", "email","permission"};
            /**
             * La liste des Users
             */
            List<User> Users;

            @Override
            public String getColumnName(int index) {

                return colonnes[index];
            }

            @Override
            public int getColumnCount() {

                return colonnes.length;
            }

            @Override
            public int getRowCount() {

                return Users.size();
            }

            @Override
            public Object getValueAt(int row, int col) {
                User User=Users.get(row);

                switch(col) {
                    case 0: return User.getid();
                    case 1: return User.getNom();
                    case 2: return User.getPrenom();
                    case 3: return User.getUsername();
                    // case 4: return User.getPassword();
                    case 5:
                        switch (User.getPermission()) {
                            case 1:
                                return "administrateur";
                            case 2:
                                return "moderateur";
                            case 3:
                                return "normal";
                        }
                    case 4: return User.getemail();
                    case 6: return User.getStatus();
                }
                return null;
            }

            public void setUsers(List<User> Users) {
                this.Users = Users;
            }

        }

        public static class UserTablePanel extends JPanel{

            private JTable table;
            private RequestUser.UserTableModel UserTableModel;

            private JButton fermerButton;
            private JButton modifierButton;
            private JButton deleteButton;

            private JPanel buttonsPanel;

            public UserTablePanel() {
                /**
                 * Specification de la taille du panel
                 */
                Dimension dimension=getPreferredSize();
                dimension.width=350;
                setPreferredSize(dimension);
                dimension.width=250;
                setMinimumSize(dimension);

                /**
                 * L'ajout du model dans la table
                 */
                table=new JTable();
                UserTableModel=new UserTableModel();
                table.setModel(UserTableModel);
                fermerButton=new JButton("Fermer");
                modifierButton=new JButton("Modifierr");
                deleteButton=new JButton("Delete");

                /**
                 * Buttons Panel
                 */
                buttonsPanel=new JPanel();

                buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                buttonsPanel.add(fermerButton);

                buttonsPanel.add(modifierButton);
                buttonsPanel.add(deleteButton);

                /**
                 * Pour centrer les informations
                 */
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment( DefaultTableCellRenderer.CENTER );
                table.setDefaultRenderer(Object.class, centerRenderer);

                /**
                 * Le layout manager du panel
                 */
                setLayout(new BorderLayout());
                /**
                 * L'ajout de la table dans le panel
                 */
                add(new JScrollPane(table), BorderLayout.CENTER);
                add(buttonsPanel, BorderLayout.SOUTH);
            }

            public void setData(List<User> Users) {
                UserTableModel.setUsers(Users);
            }

            public void refresh() {
                UserTableModel.fireTableDataChanged();
            }

            public JTable getTable() {
                return table;
            }

            public RequestUser.UserTableModel getUserTableModel() {
                return UserTableModel;
            }

            public JButton getModifierButton() {
                return modifierButton;
            }
            public JButton getFermerButton() {
                return fermerButton;
            }
            public JButton getDeleteButton() {
                return deleteButton;
            }

        }
    }
}
