package persistance;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

public class ControleurUser {

	DB.RequestUser RequestUser;

	public ControleurUser() {
		RequestUser=new DB.RequestUser();
	}

	public LinkedList<User> getUsers(){
		return RequestUser.getUsers();
	}



	public LinkedList<TypeUser> getUserTypes(){
		return RequestUser.getUserTypes();
	}

	public TypeUser getTypeUserByName(String libelle) {
		return RequestUser.getTypeUserByName(libelle);
	}
	public LinkedList<Status> getStatus(){
		return RequestUser.getStatus();
	}

	public Status getStatusByName(String libelle) {
		return RequestUser.getStatusByName(libelle);
	}

	public int addUser(User User) {
		return RequestUser.addUser(User);
	}

	public int deleteUser(int id) {
		return RequestUser.deleteUser(id);
	}

	public int updateUser(User User) {
		return RequestUser.updateUser(User);
	}

	public static class EcouteurDialog implements ActionListener {

		Fenetre fenetre;

		public EcouteurDialog(Fenetre fenetre) {
			this.fenetre=fenetre;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource().equals(fenetre.getUserDialog().getCancelButton())) {
				fenetre.getUserDialog().setVisible(false);
			}
			else if(e.getSource().equals(fenetre.getUserDialog().getOkButton())) {
				String libelle=(String) fenetre.getUserDialog().getTypeUserComboBox().getSelectedItem();
				String libelle1=(String) fenetre.getUserDialog().getStatusComboBox().getSelectedItem();
				String nom=fenetre.getUserDialog().getNomTextField().getText();
				String prenom=fenetre.getUserDialog().getPrenomTextField().getText();
				String email=fenetre.getUserDialog().getEmailT().getText();
				String password=fenetre.getUserDialog().getPasswordT().getText();
				String username=fenetre.getUserDialog().getUsernameT().getText();
				TypeUser typeUser=fenetre.getControleurUser().getTypeUserByName(libelle);
				Status status=fenetre.getControleurUser().getStatusByName(libelle1);
				User User= new User(typeUser.getIdTypesUser(), nom, prenom, email,password,username);

				if(fenetre.getUserDialog().getOkButton().getActionCommand().equals("Ajout")) {
					fenetre.getControleurUser().addUser(User);
					fenetre.getUserTablePanel().setData(fenetre.getControleurUser().getUsers());
					fenetre.getUserTablePanel().refresh();
					JOptionPane.showMessageDialog(fenetre, "User ajouté avec succes", "Succes",
							JOptionPane.INFORMATION_MESSAGE);
				}
				else if(fenetre.getUserDialog().getOkButton().getActionCommand().equals("Modification")) {
					int rowIndex=fenetre.getUserTablePanel().getTable().getSelectedRow();
					User=fenetre.getControleurUser().getUsers().get(rowIndex);

					User.setUsername(username);
					User.setPassword(password);
					User.setNom(nom);
					User.setPrenom(prenom);
					User.setemail(email);
					User.setpermission(typeUser.getIdTypesUser());
					User.setStatus(status.getId());
					int respone=JOptionPane.showConfirmDialog(fenetre, "Voulez-vous vraiment modifier le User en question",
							"Confirmer la modification", JOptionPane.OK_CANCEL_OPTION);
					if(respone==JOptionPane.OK_OPTION) {
						fenetre.getControleurUser().updateUser(User);
						fenetre.getUserTablePanel().setData(fenetre.getControleurUser().getUsers());
						fenetre.getUserTablePanel().refresh();
						JOptionPane.showMessageDialog(fenetre, "User  modifié avec succes", "Succes",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}

		}

	}

	public static class EcouteurTableUser implements ActionListener{

		Fenetre fenetre;

		public EcouteurTableUser(Fenetre fenetre) {
			this.fenetre=fenetre;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int rowIndex=fenetre.getUserTablePanel().getTable().getSelectedRow();
			if(rowIndex==-1) {
				// message d'erreur
			}
			else {
				User User=fenetre.getControleurUser().getUsers().get(rowIndex);

				if(e.getSource().equals(fenetre.getUserTablePanel().getDeleteButton())) {
					int respone=JOptionPane.showConfirmDialog(fenetre, "Voulez-vous supprimer ",
							"Confirmer la modification", JOptionPane.OK_CANCEL_OPTION);
					if(respone==JOptionPane.OK_OPTION) {
						fenetre.getControleurUser().deleteUser(User.getid());
						fenetre.getUserTablePanel().setData(fenetre.getControleurUser().getUsers());
						fenetre.getUserTablePanel().refresh();
						JOptionPane.showMessageDialog(fenetre, "User  supprimé !!!  ", "Succes",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else if(e.getSource().equals(fenetre.getUserTablePanel().getModifierButton())) {


					fenetre.getUserDialog().getNomTextField().setText(User.getNom());
					fenetre.getUserDialog().getPrenomTextField().setText(User.getPrenom());
					fenetre.getUserDialog().getPrenomTextField().setText(User.getPrenom());
					fenetre.getUserDialog().getEmailT().setText(User.getEmail());
					fenetre.getUserDialog().getPasswordT().setText(User.getPassword());
					fenetre.getUserDialog().getUsernameT().setText(User.getUsername());

					fenetre.getUserDialog().getTypeUserComboBox().setSelectedIndex(User.getPermission()-1);
					fenetre.getUserDialog().getStatusComboBox().setSelectedIndex(User.getStatus()-1);
					fenetre.getUserDialog().getOkButton().setText("Modifier");
					fenetre.getUserDialog().getOkButton().setActionCommand("Modification");

					fenetre.getUserDialog().setVisible(true);
				}
			}
		}
	}
}