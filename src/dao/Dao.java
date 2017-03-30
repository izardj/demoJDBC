package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import exceptions.ClientExistePasException;
import metier.Client;

public class Dao implements IDao {

	@Override
	public void ajouterClient(Client c) {
		try {
			// 1- charger le pilote
			Class.forName("com.mysql.jdbc.Driver");
			// 2- cr�er la connexion
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bddclients", "root", "");
			// 3- cr�er la requ�te
			//PreparedStatement ps = conn.prepareStatement("INSERT INTO Client(nom, prenom) VALUES ('" + c.getNom() + "', '" + c.getPrenom() + "')");
			
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Client(nom, prenom) VALUES (?, ?)");
			ps.setString(1, c.getNom());
			ps.setString(2, c.getPrenom());
			
			// 4- executer la requ�te
			ps.executeUpdate();
			// 5- pr�senter les r�sultats

			// 6- fermer la connexion
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// code qui est execut� quelque soit les �tapes pr�c�dentes
		}

	}

	@Override
	public Client chercherClient(int id) throws ClientExistePasException {
		
		Client c = new Client();
		
		try {
			// 1- charger le pilote
			Class.forName("com.mysql.jdbc.Driver");
			// 2- cr�er la connexion
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bddclients", "root", "");
			// 3- cr�er la requ�te
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Client WHERE id = ?");
			ps.setInt(1, id);
			// 4- executer la requ�te
			ResultSet rs = ps.executeQuery();
			// 5- pr�senter les r�sultats
			
			if (rs.next()){
	
				c.setId(rs.getInt("id"));
				c.setNom(rs.getString("nom"));
				c.setPrenom(rs.getString("prenom"));
				conn.close();

			}
			else throw new ClientExistePasException("Le client id = "+ id +" n'existe pas");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// code qui est execut� quelque soit les �tapes pr�c�dentes
			
		}
		
		return c;
	}

	@Override
	public void modifierClient(int id, String nom, String prenom) {

		try {
			// 1- charger le pilote
			Class.forName("com.mysql.jdbc.Driver");
			// 2- cr�er la connexion
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bddclients", "root", "");
			// 3- cr�er la requ�te
			PreparedStatement ps = conn.prepareStatement("UPDATE client SET nom = ? , prenom = ? WHERE id = ?");
			ps.setString(1, nom);
			ps.setString(2, prenom);
			ps.setInt(3, id);

			// 4- executer la requ�te
			ps.executeUpdate();
			// 5- pr�senter les r�sultats

			// 6- fermer la connexion
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// code qui est execut� quelque soit les �tapes pr�c�dentes
		}
	}

	@Override
	public void supprimerClient(int id) throws ClientExistePasException {
		try {
			// 1- charger le pilote
			Class.forName("com.mysql.jdbc.Driver");
			// 2- cr�er la connexion
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bddclients", "root", "");
			// 3- cr�er la requ�te
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Client WHERE id = " + id);
			// 4- executer la requ�te
			if(ps.executeUpdate() == 0)
				throw new ClientExistePasException("Le client id = "+ id +" � effacer n'existe pas");
			
			// 5- pr�senter les r�sultats

			// 6- fermer la connexion
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// code qui est execut� quelque soit les �tapes pr�c�dentes
		}

	}

	@Override
	public Collection<Client> listerClients() {

		Collection<Client> clients = new ArrayList<Client>();
		try {
			// 1- charger le pilote
			Class.forName("com.mysql.jdbc.Driver");
			// 2- cr�er la connexion
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bddclients", "root", "");
			// 3- cr�er la requ�te
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Client");
			// 4- executer la requ�te
			ResultSet rs = ps.executeQuery();
			// 5- pr�senter les r�sultats
			while (rs.next()) {
				Client c = new Client();
				c.setId(rs.getInt("id"));
				c.setNom(rs.getString("nom"));
				c.setPrenom(rs.getString("prenom"));

				clients.add(c);
			}
			// 6- fermer la connexion
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// code qui est execut� quelque soit les �tapes pr�c�dentes
		}
		return clients;
	}

	@Override
	public Collection<Client> chercherParMC(String mc) {
		
		Collection<Client> clients = new ArrayList<Client>();
		try {
			// 1- charger le pilote
			Class.forName("com.mysql.jdbc.Driver");
			// 2- cr�er la connexion
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bddclients", "root", "");
			// 3- cr�er la requ�te
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Client WHERE UPPER(nom) LIKE UPPER('%"+ mc +"%')");
			// 4- executer la requ�te
			ResultSet rs = ps.executeQuery();
			// 5- pr�senter les r�sultats
			while (rs.next()) {
				Client c = new Client();
				c.setId(rs.getInt("id"));
				c.setNom(rs.getString("nom"));
				c.setPrenom(rs.getString("prenom"));

				clients.add(c);
			}
			// 6- fermer la connexion
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// code qui est execut� quelque soit les �tapes pr�c�dentes
		}
		return clients;
	}

}
