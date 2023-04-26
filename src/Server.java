import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.Color;

public class Server {

  private int port;
  private List<User> clients;
  private ServerSocket server;

  public static void main(String[] args) throws IOException {
    new Server(8080).run();
  }

  public Server(int port) {
    this.port = port;
    this.clients = new ArrayList<User>();
  }

  public void run() throws IOException {
    server = new ServerSocket(port) {
      protected void finalize() throws IOException {
        this.close();
      }
    };
    System.out.println("Port 8080 est ouvert.");

    while (true) {

      Socket client = server.accept();

      // surnom du Nouveau  Clt
      String surnom = (new Scanner ( client.getInputStream() )).nextLine();
      surnom = surnom.replace(",", "");
      surnom = surnom.replace(" ", "_");
      System.out.println("Nouveau Client: \"" + surnom + "\"\n\t     Host:" + client.getInetAddress().getHostAddress());


      User utlisateur = new User(client, surnom);

      // ajouter utilisateur  liste
      this.clients.add(utlisateur);

      // Welcome msg
      SimpleDateFormat s = new SimpleDateFormat("dd/MM/yy HH:mm");
      Date date = new Date();

      utlisateur.getOutStream().println(
          "<img src='https://media.tenor.com/Es9wm76r9QkAAAAi/angry-typing-cat.gif' height='42' width='42'>"
          + "<b>("+s.format(date)+")</b> " + utlisateur.toString() );

      // create a new thread for newUser incoming messages handling
      new Thread(new UserGestion

(this, utlisateur)).start();
    }
  }

  // supprimer  l user de la liste
  public void removeUser(User user){
    this.clients.remove(user);
  }

  // envoyer des messages  à tous les utilisateurs
  public void broadcastMessages(String msg, User userSender) {
    for (User client : this.clients) {
      client.getOutStream().println(
          userSender.toString() + "<span>: " + msg+"</span>");
    }
  }

  // envoi de la liste clients au #  Users
  public void diffusion(){
    for (User client : this.clients) {
      client.getOutStream().println(this.clients);
    }
  }

  // envoi message au User
  public void sendMessageToUser(String msg, User userSender, String user){
    boolean find = false;
    for (User client : this.clients) {
      if (client.getsurnom

().equals(user) && client != userSender) {
        find = true;
        userSender.getOutStream().println(userSender.toString() + " -> " + client.toString() +": " + msg);
        client.getOutStream().println(
            "(<b>Private</b>)" + userSender.toString() + "<span>: " + msg+"</span>");
      }
    }
    if (!find) {
      userSender.getOutStream().println(userSender.toString() + " -> (<b>no one!</b>): " + msg);
    }
  }
}

class UserGestion implements Runnable {

  private Server server;
  private User user;

  public UserGestion(Server server, User user) {
    this.server = server;
    this.user = user;
    this.server.diffusion();
  }

  public void run() {
    String message;

    // diffusion du nouveau message à tous
    Scanner sc = new Scanner(this.user.getInputStream());
    while (sc.hasNextLine()) {
      message = sc.nextLine();

     if (message.charAt(0) == '#'){
        user.changeColor(message);
        this.server.diffusion();
      }else{

        server.broadcastMessages(message, user);
      }
    }
    // fin
    server.removeUser(user);
    this.server.diffusion();
    sc.close();
  }
}

class User {
  private static int nbUser = 0;
  private int userId;
  private String surnom;
  private PrintStream streamOut;
  private InputStream streamIn;

  private Socket client;
  private String color;

  // constructor
  public User(Socket client, String name) throws IOException {
    this.streamOut = new PrintStream(client.getOutputStream());
    this.streamIn = client.getInputStream();
    this.client = client;
    this.surnom = name;
    this.userId = nbUser;
    this.color = Couleur.getCouleur(this.userId);
    nbUser += 1;
  }

  // change color user
  public void changeColor(String hexColor){
      this.color = hexColor;
      this.getOutStream().println(this.toString());
  }

  // getteur
  public PrintStream getOutStream(){
    return this.streamOut;
  }

  public InputStream getInputStream(){
    return this.streamIn;
  }

  public String getsurnom(){
    return this.surnom

;
  }

  // print user with his color
  public String toString(){

    return "<u><span style='color:"+ this.color
      +"'>" + this.getsurnom() + "</span></u>";

  }
}

class Couleur {
    public static String[] couleurs = {

            "#7d669e", // rose
            "#53bbb4", // aqua
            "#51b46d", // vert
            "#f092b0", // rose
            "#e8d174", // jaune
            "#e39e54", // orange
            "#d64d4d", // rouge

    };

    public static String getCouleur(int i) {
        return couleurs[i % couleurs.length];
    }
}
