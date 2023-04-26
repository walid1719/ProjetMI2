import persistance.DB;
import persistance.Fenetre;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.*;

import java.util.ArrayList;
import java.util.Arrays;


public class ClientInterface extends Thread{

  final JTextPane champDiscu = new JTextPane();
  final JTextPane listeUtilisateurs = new JTextPane();
  final JTextField champsMessage = new JTextField();
  private String oldMsg = "";
  private Thread read;
  private String serverName;
  private int PORT;
  private String name;
  BufferedReader input;
  PrintWriter output;
  Socket server;
  private JMenuBar menuBar;
  private JMenu  menuUser;
  private JMenuItem itemUser;

  public ClientInterface() {
    this.serverName = "localhost";
    this.PORT = 8080;
    this.name = LoginFrame.userCurrent.getUsername();


    String fontA = "Arial, sans-serif";
    Font font = new Font(fontA, Font.PLAIN, 15);

    final JFrame jframe = new JFrame("Messagerie Instantanée");
    jframe.getContentPane().setLayout(null);
    jframe.setSize(700, 500);
    jframe.setResizable(false);
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // champs de discussion
    champDiscu.setBounds(186, 25, 490, 320);
    champDiscu.setFont(font);
    champDiscu.setMargin(new Insets(6, 6, 6, 6));
    champDiscu.setEditable(false);
    JScrollPane champDiscuSP = new JScrollPane(champDiscu);
    champDiscuSP.setBounds(186, 25, 490, 320);

    champDiscu.setContentType("text/html");
    champDiscu.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

    //**la liste des utilisateurs
    listeUtilisateurs.setBounds(25, 25, 156, 320);
    listeUtilisateurs.setEditable(true);
    listeUtilisateurs.setFont(font);
    listeUtilisateurs.setMargin(new Insets(6, 6, 6, 6));
    listeUtilisateurs.setEditable(false);
    JScrollPane jsplistuser = new JScrollPane(listeUtilisateurs);
    jsplistuser.setBounds(25, 25, 156, 320);

    listeUtilisateurs.setContentType("text/html");
    listeUtilisateurs.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

    // champs message
    champsMessage.setBounds(0, 350, 400, 50);
    champsMessage.setFont(font);
    champsMessage.setMargin(new Insets(6, 6, 6, 6));
    final JScrollPane jSP = new JScrollPane(champsMessage);
    jSP.setBounds(25, 350, 650, 50);

    // envoyer
    final JButton btnEnvoyer = new JButton("Envoyer");
    btnEnvoyer.setFont(font);
    btnEnvoyer.setBounds(575, 410, 100, 35);
    //*****************

    // Decooncter
    final JButton btnDeconnecter = new JButton("Deconnecter");
    btnDeconnecter.setFont(font);
    btnDeconnecter.setBounds(25, 410, 130, 35);

    champsMessage.addKeyListener(new KeyAdapter() {
      // send message on Enter
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          envoiMessage();
        }
      }
    });

    // CliQUER BOUTON ENVOYER
    btnEnvoyer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        envoiMessage();
      }
    });

    // Connection view
    final JTextField textName = new JTextField(this.name);
    final JTextField textport = new JTextField(Integer.toString(this.PORT));
    final JTextField textAddr = new JTextField(this.serverName);
    final JButton jcbtn = new JButton("Connecter");

    // les champs non vides
    textName.getDocument().addDocumentListener(new TextListener(textName, textport, textAddr, jcbtn));
    textport.getDocument().addDocumentListener(new TextListener(textName, textport, textAddr, jcbtn));
    textAddr.getDocument().addDocumentListener(new TextListener(textName, textport, textAddr, jcbtn));

    // position
    jcbtn.setFont(font);
    textAddr.setBounds(25, 380, 135, 40);
    textName.setBounds(375, 380, 135, 40);
    textport.setBounds(200, 380, 135, 40);
    jcbtn.setBounds(575, 380, 100, 40);

    // couleur par defaut
    champDiscu.setBackground(Color.LIGHT_GRAY);
    listeUtilisateurs.setBackground(Color.LIGHT_GRAY);

    // ajout des items
    jframe.add(jcbtn);
    jframe.add(jsplistuser);
    jframe.add(champDiscuSP);

    jframe.add(textName);
    jframe.add(textport);
    jframe.add(textAddr);

    //*****************
    // Création de la barre de menu
    if (LoginFrame.isAdmin ){
    menuBar = new JMenuBar();
    menuUser = new JMenu("Utilisateurs");
    itemUser = new JMenuItem("Utilisateur");
    menuUser.add(itemUser);
    jframe.setJMenuBar(menuBar);
    menuBar.add(menuUser);
    itemUser.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //GestionUtilisateurFrame guf = new GestionUtilisateurFrame();
        Fenetre guf = new Fenetre();
        guf.setVisible(true);
        //guf.dispose();

      }
    });
    menuUser.setVisible(true);
    }
    //**********
    jframe.setVisible(true);




    // connecté
    jcbtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        try {
          name = textName.getText();
          serverName = textAddr.getText();
          PORT = Integer.parseInt(textport.getText());
          //liaison au serveur
          server = new Socket(serverName, PORT);

          appendToPane(champDiscu, "Connecté au :  " + server.getRemoteSocketAddress()+"");

          input = new BufferedReader(new InputStreamReader(server.getInputStream()));
          output = new PrintWriter(server.getOutputStream(), true);


          output.println(name);

          // creation  Thread
          read = new Read();
          read.start();
          jframe.remove(textName);
          jframe.remove(textport);
          jframe.remove(textAddr);
          jframe.remove(jcbtn);
          jframe.add(btnEnvoyer);
          jframe.add(jSP);
          jframe.add(btnDeconnecter);
          jframe.revalidate();
          jframe.repaint();
          champDiscu.setBackground(Color.WHITE);
          listeUtilisateurs.setBackground(Color.WHITE);
          //(new DB.RequestUser()).addMessage(LoginFrame.userCurrent,name);
          (new DB.RequestUser()).addLog(LoginFrame.userCurrent,4);
        } catch (Exception ex) {
          appendToPane(champDiscu, "<span>Connexion au serveur impossible</span>");
          JOptionPane.showMessageDialog(jframe, ex.getMessage());
        }
      }

    });

    //
    btnDeconnecter.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent ae) {
        jframe.add(textName);
        jframe.add(textport);
        jframe.add(textAddr);
        jframe.add(jcbtn);
        jframe.remove(btnEnvoyer);
        jframe.remove(jSP);
        jframe.remove(btnDeconnecter);
        jframe.revalidate();
        jframe.repaint();
        read.interrupt();
        listeUtilisateurs.setText(null);
        champDiscu.setBackground(Color.LIGHT_GRAY);
        listeUtilisateurs.setBackground(Color.LIGHT_GRAY);
        appendToPane(champDiscu, "<span>Connexion fermée.</span>");
        (new DB.RequestUser()).addLog(LoginFrame.userCurrent,6);
        output.close();
      }
    });



  }

  // les champs vides
  public class TextListener implements DocumentListener{
    JTextField text1;
    JTextField text2;
    JTextField text3;
    JButton jcbtn;

    public TextListener(JTextField text1, JTextField text2, JTextField text3, JButton jcbtn){
      this.text1 = text1;
      this.text2 = text2;
      this.text3 = text3;
      this.jcbtn = jcbtn;
    }

    public void changedUpdate(DocumentEvent e) {}

    public void removeUpdate(DocumentEvent e) {
      if(text1.getText().trim().equals("") ||
          text2.getText().trim().equals("") ||
          text3.getText().trim().equals("")
          ){
        jcbtn.setEnabled(false);
      }else{
        jcbtn.setEnabled(true);
      }
    }
    public void insertUpdate(DocumentEvent e) {
      if(text1.getText().trim().equals("") ||
          text2.getText().trim().equals("") ||
          text3.getText().trim().equals("")
          ){
        jcbtn.setEnabled(false);
      }else{
        jcbtn.setEnabled(true);
      }
    }

  }

  // envoi des messages
  public void envoiMessage() {
    try {
      String message = champsMessage.getText().trim();
      if (message.equals("")) {
        return;
      }
      this.oldMsg = message;
      output.println(message);
      champsMessage.requestFocus();
      champsMessage.setText(null);
      (new DB.RequestUser()).addMessage(LoginFrame.userCurrent,message);
      (new DB.RequestUser()).addLog(LoginFrame.userCurrent,5);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage());
      System.exit(0);
    }
  }

  public static void main(String[] args) throws Exception {
    ClientInterface client = new ClientInterface();
  }

  // read new incoming messages
  class Read extends Thread {
    public void run() {
      String message;
      while(!Thread.currentThread().isInterrupted()){
        try {
          message = input.readLine();
          if(message != null){
            if (message.charAt(0) == '[') {
              message = message.substring(1, message.length()-1);
              ArrayList<String> ListUser = new ArrayList<String>(
                  Arrays.asList(message.split(", "))
                  );
              listeUtilisateurs.setText(null);
              for (String user : ListUser) {
                appendToPane(listeUtilisateurs, "@" + user);
              }
            }else{
              appendToPane(champDiscu, message);
            }
          }
        }
        catch (IOException ex) {
          System.err.println("Imposible de anlyser  message");
        }
      }
    }
  }

  // ajouter au panel
  private void appendToPane(JTextPane tp, String msg){
    HTMLDocument doc = (HTMLDocument)tp.getDocument();
    HTMLEditorKit editorKit = (HTMLEditorKit)tp.getEditorKit();
    try {
      editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
      tp.setCaretPosition(doc.getLength());
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}
