package persistance;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Fenetre extends JFrame{
	
	private EntetePanel entetePanel;
	private DB.RequestUser.UserTablePanel UserTablePanel;
	private DB.RequestUser.UserDialog UserDialog;
	//private UserRechercheDialog UserRechercheDialog;

	
	private ControleurUser controleurUser;
	private EcouteurEntete ecouteurEntete;
	private ControleurUser.EcouteurDialog ecouteurDialog;
	private ControleurUser.EcouteurTableUser ecouteurTableUser;
	//private EcouteurDialogRecherche ecouteurDialogRecherche;
	
	public Fenetre() {
		
		this.setTitle("Gestion des utilisateurs");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(700,400);
		/**
		 * Pour centrer la fenetre dans le screen
		 */
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		
		/**
		 * L'instanciation des panels
		 */
		entetePanel=new EntetePanel();
		UserTablePanel=new DB.RequestUser.UserTablePanel();
		UserDialog=new DB.RequestUser.UserDialog(this);

		
		controleurUser=new ControleurUser();
		ecouteurEntete=new EcouteurEntete(this);
		ecouteurDialog=new ControleurUser.EcouteurDialog(this);
		ecouteurTableUser=new ControleurUser.EcouteurTableUser(this);

		
		/**
		 * L'ajout de donnees dans la table
		 */
		UserTablePanel.setData(controleurUser.getUsers());
		
		UserTablePanel.refresh();
		
		/**
		 * L'ajout des libelles dans le combobox du dilog
		 */
		for (TypeUser typeUser : controleurUser.getUserTypes()) {
			UserDialog.getTypeUserComboBox().addItem(typeUser.getLibelle());
		}

		//System.out.println("je suis la"+(new DB.RequestUser()).getUserByName("admin","xxxx").getNom());

		/**
		 * L'ajout des libelles dans le combobox du dilog
		 */
		for (Status status : controleurUser.getStatus()) {
			UserDialog.getStatusComboBox().addItem(status.getLibelle());
		}
		
		/**
		 * Les evenements
		 */
		entetePanel.getExitItem().addActionListener(ecouteurEntete);
		entetePanel.getAjouterUserItem().addActionListener(ecouteurEntete);
		entetePanel.getChercherUserItem().addActionListener(ecouteurEntete);
		
		UserDialog.getCancelButton().addActionListener(ecouteurDialog);
		UserDialog.getOkButton().addActionListener(ecouteurDialog);
		
		UserTablePanel.getModifierButton().addActionListener(ecouteurTableUser);
		UserTablePanel.getDeleteButton().addActionListener(ecouteurTableUser);
		

		
		
		/**
		 * Le layout manager de la fenetre
		 */
		this.setLayout(new BorderLayout());
		
		/**
		 */
		/**
		 * L'ajout des panels dans le layout principale
		 */
		this.add(entetePanel, BorderLayout.NORTH);
		this.add(UserTablePanel, BorderLayout.CENTER);
		
		/**
		 * Rendre la fenetre visible
		 */
		this.setVisible(true);
	}

	public EntetePanel getEntetePanel() {
		return entetePanel;
	}

	public DB.RequestUser.UserTablePanel getUserTablePanel() {
		return UserTablePanel;
	}

	public DB.RequestUser.UserDialog getUserDialog() {
		return UserDialog;
	}





	public ControleurUser getControleurUser() {
		return controleurUser;
	}

	public static class EntetePanel extends JPanel {

		private JMenuBar menuBar;
		private JMenu fileMenu, UserMenu;
		private JMenuItem exitItem, ajouterUserItem, chercherUserItem;


		public EntetePanel() {

			setLayout(new BorderLayout());

			menuBar=new JMenuBar();

			fileMenu=new JMenu("File");
			UserMenu=new JMenu("User");

			exitItem=new JMenuItem("Exit");
			ajouterUserItem=new JMenuItem("Ajouter User");
			chercherUserItem=new JMenuItem("Chercher User");

			exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
			ajouterUserItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
			chercherUserItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));

			fileMenu.add(exitItem);
			UserMenu.add(ajouterUserItem);
			UserMenu.add(chercherUserItem);

			menuBar.add(fileMenu);
			menuBar.add(UserMenu);

			this.add(menuBar, BorderLayout.NORTH);
		}


		public JMenuItem getExitItem() {
			return exitItem;
		}

		public JMenuItem getAjouterUserItem() {
			return ajouterUserItem;
		}

		public JMenuItem getChercherUserItem() {
			return chercherUserItem;
		}

	}

	public static class EcouteurEntete implements ActionListener {

		Fenetre fenetre;

		public EcouteurEntete(Fenetre fenetre) {
			this.fenetre=fenetre;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource().equals(fenetre.getEntetePanel().getExitItem())) {
				int respone=JOptionPane.showConfirmDialog(fenetre.getEntetePanel(), "Voulez-vous vraiment quiter ",
						"Confirmer le quit", JOptionPane.OK_CANCEL_OPTION);
				if(respone==JOptionPane.OK_OPTION) {
					//System.exit(0);
					fenetre.dispose();

				}
			}

			else if(e.getSource().equals(fenetre.getEntetePanel().getAjouterUserItem())) {
				fenetre.getUserDialog().getOkButton().setText("Ajouter");
				fenetre.getUserDialog().getOkButton().setActionCommand("Ajout");
				fenetre.getUserDialog().setVisible(true);
			}

		}

	}
}
