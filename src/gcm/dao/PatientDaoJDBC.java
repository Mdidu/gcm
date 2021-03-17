package gcm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gcm.model.Patient;

public class PatientDaoJDBC implements IPatientDao {

	Statement stmt;
	
	public PatientDaoJDBC() {
		try {
			stmt = JdbcConnexion.connecter().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void add(Patient p) {
		// TODO Auto-generated method stub
		try {
			
			String sql="insert into patient(nss,nom,prenom,adresse,datenaissance,ville) values("+p.getNss()+",'"+p.getNom()+"','"+p.getPrenom()+"','"+p.getAdresse()+"','"+p.getDateNaissance()+"','"+p.getVille()+"')";
            stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(int nss) {
		// TODO Auto-generated method stub
		try {
			String requestSQL = "DELETE FROM patient WHERE nss = " + nss + "";
			stmt.execute(requestSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(int nss, String ville, String adresse) {
		// TODO Auto-generated method stub
		try {
			String requestSQL = "UPDATE patient SET adresse = '"+ adresse + "', ville = '" + ville + "' WHERE nss = "+ nss + "";
			stmt.execute(requestSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Patient> findAll() {
		List<Patient> list = new ArrayList<Patient>();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM patient");
			
			while (rs.next()) {
				int nss= rs.getInt("nss");
				String nom= rs.getString("nom");
				String prenom= rs.getString("prenom");
				String adresse= rs.getString("adresse");
				String ville= rs.getString("ville");
				Date dateNaissance= rs.getDate("datenaissance");
			
				//remplir l'objet
				Patient p=new Patient();
				p.setNss(nss);
		        p.setPrenom(nom);
		        p.setNom(prenom);
		        p.setAdresse(adresse);
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        String sqlDate = sdf.format(dateNaissance);
		        p.setDateNaissance(sqlDate);
		        p.setVille(ville);
		        
		        list.add(p);
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void update(int nss) {
	}
	
	public boolean findUserByUsernameAndPassword(String username, String password) {
        
        try {
            ResultSet rs = stmt.executeQuery("select * from GCM_USER where username='"+username+"' and password='"+password+"' ");
            
            if (rs.next()) 
                return true;
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        return false;
    }
	
	public static void main(String[] args) {
		PatientDaoJDBC patientDaoJDBC=new PatientDaoJDBC();
        Patient p=new Patient();
        p.setNss(15);
        p.setPrenom("Xx");
        p.setNom("YY");
        p.setAdresse("ZZ");
//        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sqlDate = sdf.format(new Date());
        
        p.setDateNaissance(sqlDate);
        p.setVille("Paris");
        boolean resultat = patientDaoJDBC.findUserByUsernameAndPassword("test", "test");
//        patientDaoJDBC.add(p);
//		PatientDaoJDBC patientDaoJDBC=new PatientDaoJDBC();
//        Patient p=new Patient();
//        p.setNss(16);
//        patientDaoJDBC.update(16);
    }
}
