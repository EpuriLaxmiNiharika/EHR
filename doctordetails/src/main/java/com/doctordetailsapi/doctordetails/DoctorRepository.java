package  com.doctordetailsapi.doctordetails;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DoctorRepository {

	List<DoctorDetails> list;
	Connection con = null;

	public DoctorRepository() {
		list = new ArrayList<DoctorDetails>();
	
		
		String url = "jdbc:mysql://localhost:3306/sample_db?zeroDateTimeBehavior=convertToNull";
		String uname = "root";
		String password = "123";
		
		
	/*	String url = "jdbc:mysql://localhost:3306/EHR?zeroDateTimeBehavior=convertToNull";
		String uname = "root";
		String password = "admin";
	*/	
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
			
			con = DriverManager.getConnection(url,uname,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<DoctorDetails> getDoctors() throws Exception{
		
		String sql = "select * from doc_details_tbl inner join specialization_tbl on doc_details_tbl.specialization = specialization_tbl.id";
		
			if(con==null) {
				return null;
			}
			
			Statement st = con.createStatement();
			ResultSet res_set = st.executeQuery(sql);
			while(res_set.next()) {
			//	System.out.println("hello world!");
				DoctorDetails myobj = new DoctorDetails();
				
				String doc_id = res_set.getString(1);
				String name = res_set.getString(2);
				String dept = res_set.getString(6);
				String speclization_id =res_set.getString(7);
				int year_start = res_set.getInt(9);
				String spec_name = res_set.getString(11);
				
				myobj.setDoc_id(doc_id);
				myobj.setDesignation(dept);
				myobj.setName(name);
				
				myobj.setSpecialization_id(speclization_id);
				myobj.setSpecialization_name(spec_name);
				myobj.setYear_join(year_start);
				
				String sql1 ="select * from doc_hospitals where doc_id = '"+doc_id+"'";
				Statement st1 = con.createStatement();
				ResultSet res_set1 = st1.executeQuery(sql1);
				ArrayList<String> doc_hosp = new ArrayList<String>();
				ArrayList<String> doc_hosp_name = new ArrayList<String>();
				while(res_set1.next()) {
					String hosp_id = res_set1.getString(2);
			//		System.out.println("hospital id"+hosp_id);
					doc_hosp.add(hosp_id);
					Statement st2 = con.createStatement();
					String sql3 = "select name from hospitals where id = '"+hosp_id+"'";
					ResultSet res_set3 = st2.executeQuery(sql3);
					while(res_set3.next()) {
					//	System.out.println("hospital name"+res_set3.getString(1));
						doc_hosp_name.add(res_set3.getString(1));
					}
			//		System.out.println("bye");
			//		System.out.println("hey..."+doc_hosp.get(0));
					myobj.setHospital_id(doc_hosp);
					myobj.setHospital_name(doc_hosp_name);
				//	System.out.println("exit loop");
				}
				list.add(myobj);
			}
		
	
		return list;
	}
	
	public DoctorDetails getDoctor(String index) throws Exception{
		//System.out.println("sasa: "+list.get(index).getName());
		
		//return list.get(index);
		getDoctors();
		for(DoctorDetails i: list) {
		//	System.out.println("doctor: "+i.getDoc_id());
			if(i.getDoc_id().equals(index)) {
				return i;
			}
		}
		return null;
	}
	
	
	public List<DoctorDetails> getDoctors(String specialization) throws Exception{ // LIST DOCTORS BASED ON SPECIALIZATION
		try {
			String sql1 = "select id from specialization_tbl where name = '"+specialization+"'";
			Statement st1;
			st1 = con.createStatement();
		
			ResultSet res_set1 = st1.executeQuery(sql1);
			String specialization_id = "";
		
			if(res_set1.next()) {
				 specialization_id = res_set1.getString(1); // get specialization
			//	 System.out.println("Specialization"+specialization_id);
			}
			
			 getDoctors();
			ArrayList<DoctorDetails> docs = new ArrayList<DoctorDetails>();
			
			for(int i = 0; i<list.size();i++) {
				if(list.get(i).getSpecialization_id().equals(specialization_id)) {
					docs.add(list.get(i));
				}
			}
			return docs;
		} 
		catch (SQLException e) 
			{
				e.printStackTrace();
			}
		
		return null;
		
		/*try {
			String sql1 = "select id from specialization_tbl where name = '"+specialization+"'";;
			Statement st1 = con.createStatement();
			ResultSet res_set1 = st1.executeQuery(sql1);
			while(res_set1.next()) {
				String specialization_id = res_set1.getString(1); // get specialization
				String sql2 ="select * from doc_details_tbl where specialization = '"+specialization_id+"'";
				Statement st2 = con.createStatement();
				ResultSet res_set2 = st2.executeQuery(sql2);
				while(res_set2.next()) {
					DoctorDetails myobj = new DoctorDetails();		
					String doc_id = res_set2.getString(1);
					String name = res_set2.getString(2);
					String dept = res_set2.getString(5);
					String speclization =res_set2.getString(6);
					int year_start = res_set2.getInt(8);
					myobj.setDoc_id(doc_id);
					myobj.setDesignation(dept);
					myobj.setName(name);
					myobj.setSpecialization(speclization);
					myobj.setYear_join(year_start);
					
					String sql3 ="select * from doc_hospitals where doc_id = '"+doc_id+"'";
					Statement st3 = con.createStatement();
					ResultSet res_set3 = st3.executeQuery(sql3);
					ArrayList<String> doc_hosp = new ArrayList<String>();
					ArrayList<String> doc_hosp_name = new ArrayList<String>();
					while(res_set3.next()) {
						String hosp_id = res_set3.getString(2);
				//		System.out.println("hospital id"+hosp_id);
						doc_hosp.add(hosp_id);
						Statement st4 = con.createStatement();
						String sql4 = "select name from hospitals where id = '"+hosp_id+"'";
						ResultSet res_set4 = st4.executeQuery(sql4);
						while(res_set4.next()) {
						//	System.out.println("hospital name"+res_set3.getString(1));
							doc_hosp_name.add(res_set4.getString(1));
						}
				//		System.out.println("bye");
				//		System.out.println("hey..."+doc_hosp.get(0));
						myobj.setHospital_id(doc_hosp);
						myobj.setHospital_name(doc_hosp_name);
					//	System.out.println("exit loop");
						}
					list.add(myobj);
				}
			}
		}
		catch(Exception e) {
			
		}
		return list;*/
		
	}

	
/*	public void create(DoctorDetails obj) {
		String sql = "insert into names_tbl values(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			// st.set
			st.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.add(obj);
		// TODO Auto-generated method stub
		
	}*/
	
	
	 ArrayList<DoctorDetails> getDoctors_specializationID(String spec_id) {
		 
		 try {
				
				 getDoctors();
				ArrayList<DoctorDetails> docs = new ArrayList<DoctorDetails>();
				
				for(int i = 0; i<list.size();i++) {
					if(list.get(i).getSpecialization_id().equals(spec_id)) {
						docs.add(list.get(i));
					}
				}
				return docs;
			} 
			catch (Exception e) 
				{
					e.printStackTrace();
				}
			
			return null;
	}
}

// List hospitals based on location
// List hospitals based on 
