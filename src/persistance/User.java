package persistance;

import java.util.Date;
import java.sql.Timestamp;

public class User {

	int id;

	String username;
	String nom;
	String prenom;
	String email;
	String password;
	int permission;
	int status;

	Date last_connection;






	
	public User() {
	}
	
	public User(int permission, String nom, String prenom, String email) {
		super();
		this.permission = permission;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.status = 1;
	}

	public User(int id, String username,int permission, String nom, String prenom, String email,String password) {
		super();
		this.id = id;
		this.username = username;
		this.nom = nom;
		this.prenom = prenom;
		this.permission = permission;
		this.email = email;
		this.password = password;
		this.status = 1;
		this.last_connection = new Date();
	}

	public User(int permission, String nom, String prenom, String email, String password, String username) {
		super();

		this.username = username;
		this.nom = nom;
		this.prenom = prenom;
		this.permission = permission;
		this.email = email;
		this.password = password;
		this.status = 1;
		this.last_connection = new Date();

	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public void setLast_connection(Date last_connection) {
		this.last_connection = last_connection;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public int getPermission() {
		return permission;
	}

	public Date getLast_connection() {
		return last_connection;
	}

	public int getid() {
		return id;
	}
	public void setid(int id) {
		this.id = id;
	}
	public int getpermission() {
		return permission;
	}
	public void setpermission(int permission) {
		this.permission = permission;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
	}

}
