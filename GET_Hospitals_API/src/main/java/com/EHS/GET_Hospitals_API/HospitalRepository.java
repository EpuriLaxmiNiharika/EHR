package com.EHS.GET_Hospitals_API;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class HospitalRepository {

	List<HospitalDetails> list;
	Connection con = null;

	public HospitalRepository() {
		list = new ArrayList<HospitalDetails>();
		
		String url = "jdbc:mysql://localhost:3306/EHR?zeroDateTimeBehavior=convertToNull";
		String uname = "root";
		String password = "admin";
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
			
			con = DriverManager.getConnection(url,uname,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<HospitalDetails> getHospitals(){
		
		String sql = "select * from hospitals";
		try {
			Statement st = con.createStatement();
			ResultSet res_set = st.executeQuery(sql);
			while(res_set.next()) {
			//	System.out.println("hello world!");
				HospitalDetails myobj = new HospitalDetails();
				
				String id = res_set.getString(1);
				String name = res_set.getString(2);
				String street_address = res_set.getString(3);
				String location =res_set.getString(4);
				String city = res_set.getString(5);
				
				myobj.setId(id);
				myobj.setName(name);
				myobj.setStreetAddress(street_address);
				myobj.setLocation(location);
				myobj.setCity(city);
		
				list.add(myobj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return list;
	}
	
	public HospitalDetails getHospital(String index) {
		
		getHospitals();
		for(HospitalDetails i: list) {
		//	System.out.println("doctor: "+i.getDoc_id());
			if(i.getId().equals(index)) {
				return i;
			}
		}
		return null;
	}
	
	//http://localhost:8080/GET_Hospitals_API/webapi/hospitals/specialization/ENT
	public List<HospitalDetails> getHospitals(String specialization){ // LIST hospitals BASED ON SPECIALIZATION
		try {
			List<HospitalDetails> hospitals = new ArrayList<HospitalDetails>();
			String sql1 = "select id from specialization_tbl where name = '"+specialization+"'";;
			Statement st1;
			st1 = con.createStatement();
		
			ResultSet res_set1 = st1.executeQuery(sql1);
			String specialization_id = "";
		
			if(res_set1.next()) {
				 specialization_id = res_set1.getString(1); // get specialization
				
				 System.out.println("Specialization"+specialization_id);
			}
			
			String sql2 = "SELECT doc_hospitals.hospital_id FROM doc_hospitals inner join doc_details_tbl on doc_hospitals.doc_id = doc_details_tbl.doc_id where doc_details_tbl.specialization = '"+specialization_id+"'";
			Statement st2  = con.createStatement();
			ResultSet res_set2 = st1.executeQuery(sql2);

			ArrayList<String> hospital_ids = new ArrayList<String>();
			while(res_set2.next()) {
				String hospital_id = res_set2.getString(1);
				hospital_ids.add(hospital_id);		
				System.out.println("Hosp id"+hospital_id);
			}
			
			for(int i = 0; i<hospital_ids.size();i++) {
				String hosp_id = hospital_ids.get(i);
				hospitals.add(getHospital(hosp_id));
			}
			return hospitals;
		} 
		catch (SQLException e) 
			{
				e.printStackTrace();
			}
		
		return null;
	}
	
 List<HospitalDetails> getHospitals_specializationID(String spec_id) {
		 
		List<HospitalDetails> hospitals = new ArrayList<HospitalDetails>();

		 try {
				
			 	String sql2 = "SELECT doc_hospitals.hospital_id FROM doc_hospitals inner join doc_details_tbl on doc_hospitals.doc_id = doc_details_tbl.doc_id where doc_details_tbl.specialization = '"+spec_id+"'";
				Statement st2  = con.createStatement();
				ResultSet res_set2 = st2.executeQuery(sql2);

				ArrayList<String> hospital_ids = new ArrayList<String>();
				while(res_set2.next()) {
					String hospital_id = res_set2.getString(1);
					hospital_ids.add(hospital_id);		
					System.out.println("Hosp id"+hospital_id);
				}
				
				for(int i = 0; i<hospital_ids.size();i++) {
					String hosp_id = hospital_ids.get(i);
					hospitals.add(getHospital(hosp_id));
				}
				
		 }
		 catch(Exception e) {
			 System.out.println("Exception came");
		 }
		 return hospitals;
 	}
}

// List hospitals based on location
// List hospitals based on 
