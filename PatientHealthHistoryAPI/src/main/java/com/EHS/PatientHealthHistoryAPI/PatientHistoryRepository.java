package com.EHS.PatientHealthHistoryAPI;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class PatientHistoryRepository {

	Connection con = null;

	public PatientHistoryRepository() {
		
		
		 	String url = "jdbc:mysql://localhost:3306/sample_db";
			String uname = "root";
			String password = "123";
		
			
		
	/*	
		String url = "jdbc:mysql://localhost:3306/EHR?zeroDateTimeBehavior=convertToNull";
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
	
	
	public PatientHistory getPatientHistory(String id) {
		
		String sql1 = "select * from patienthistory where patientId = '"+id+"' ORDER BY pdate DESC limit 0, 1";
		PatientHistory obj1 = new PatientHistory();
		
		Statement st1;
		try {
			st1 = con.createStatement();
			ResultSet res_set1 = st1.executeQuery(sql1);
			ResultSetMetaData meta = res_set1.getMetaData();
			
			while(res_set1.next()) {
					System.out.println(res_set1.toString());
					
					String pat_id = res_set1.getString(2);
					String cond = res_set1.getString(3);
					String symp = res_set1.getString(4);
					Boolean is_tob = res_set1.getBoolean(5);
					String prev_med = res_set1.getString(6);
					Boolean is_alc = res_set1.getBoolean(7);
					String allergies = res_set1.getString(8);
					String date = res_set1.getString(9);
					String pres_url = res_set1.getString(10);
					String doc_id = res_set1.getString(11);
					int bp = res_set1.getInt(12);
					Float sugar = res_set1.getFloat(13);
					Float weight = res_set1.getFloat(14);
					String reason = res_set1.getString(15);
					
					System.out.println(pat_id);
					System.out.println(cond);
					System.out.println(symp);
					System.out.println(is_tob);
					System.out.println(prev_med);
					System.out.println(is_alc);
					System.out.println(allergies);
					System.out.println(date);
					System.out.println(pres_url);
					System.out.println(doc_id);
					System.out.println(bp);
					System.out.println(sugar);
					System.out.println(weight);
					System.out.println(reason);
					System.out.println("hyyyyyy");
					
					for(int i = 1; i<=meta.getColumnCount();i++) {
						System.out.println(meta.getColumnName(i));
					}
					
				obj1.setPatientId(pat_id);
				
				obj1.setConditions(cond);
				
				obj1.setSymptoms(symp);
				
				obj1.setIsTobbaco(is_tob);
			
				System.out.println("HELLO: "+(res_set1).getString(5));
				
				obj1.setPrevMedication(prev_med);
				
				obj1.setIsAlohocolic(is_alc);
				
				obj1.setAllergies(allergies);
				
				obj1.setDate(date);
				
				obj1.setPrescription_file_url(pres_url);
				
				obj1.setDoctorId(doc_id);
				
				obj1.setBloodPressure(bp);
				
				obj1.setSugarLevel(sugar);
				
				obj1.setWeight(weight);
				
				obj1.setVisit_reason(reason);
				
				System.out.println("Hey");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj1;
	}
	
	
	public List<PatientHistory> getAllPatientHistory(String id){
		
		List<PatientHistory> lst = new ArrayList<PatientHistory>();

		String sql1 = "select * from patienthistory where patientId = '"+id+"' ORDER BY pdate DESC limit 0, 5";
		
		
		Statement st1;
		try {
			st1 = con.createStatement();
			ResultSet res_set1 = st1.executeQuery(sql1);
			
			while(res_set1.next()) {
				PatientHistory obj1 = new PatientHistory();
				String pat_id = res_set1.getString(2);
				String cond = res_set1.getString(3);
				String symp = res_set1.getString(4);
				Boolean is_tob = res_set1.getBoolean(5);
				String prev_med = res_set1.getString(6);
				Boolean is_alc = res_set1.getBoolean(7);
				String allergies = res_set1.getString(8);
				String date = res_set1.getString(9);
				String pres_url = res_set1.getString(10);
				String doc_id = res_set1.getString(11);
				int bp = res_set1.getInt(12);
				Float sugar = res_set1.getFloat(13);
				Float weight = res_set1.getFloat(14);
				String reason = res_set1.getString(15);
				
				System.out.println(pat_id);
				System.out.println(cond);
				System.out.println(symp);
				System.out.println(is_tob);
				System.out.println(prev_med);
				System.out.println(is_alc);
				System.out.println(allergies);
				System.out.println(date);
				System.out.println(pres_url);
				System.out.println(doc_id);
				System.out.println(bp);
				System.out.println(sugar);
				System.out.println(weight);
				System.out.println(reason);
				System.out.println("hyyyyyy");
				
				
			obj1.setPatientId(pat_id);
			
			obj1.setConditions(cond);
			
			obj1.setSymptoms(symp);
			
			obj1.setIsTobbaco(is_tob);
		
			System.out.println("HELLO: "+(res_set1).getString(5));
			
			obj1.setPrevMedication(prev_med);
			
			obj1.setIsAlohocolic(is_alc);
			
			obj1.setAllergies(allergies);
			
			obj1.setDate(date);
			
			obj1.setPrescription_file_url(pres_url);
			
			obj1.setDoctorId(doc_id);
			
			obj1.setBloodPressure(bp);
			
			obj1.setSugarLevel(sugar);
			
			obj1.setWeight(weight);
			
			obj1.setVisit_reason(reason);
			
			System.out.println("Hey");
			
				lst.add(obj1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return lst;
	}
	
	public PatientHistory createPatientHistory(PatientHistory patient) {
		
		String sql = "insert into patienthistory (patientId, conditions,symptoms,isTobbaco,prevMedication,isAlohocolic,allergies,pdate,prescription_file_url,doctorId,bloodPressure,sugarLevel,weight,visit_reason) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement st3 = con.prepareStatement(sql);
			st3.setString(1, patient.getPatientId());
			st3.setString(2, patient.getConditions());
			st3.setString(3, patient.getSymptoms());
			st3.setBoolean(4, patient.getIsTobbaco());
			st3.setString(5, patient.getPrevMedication());
			st3.setBoolean(6, patient.getIsAlohocolic());
			st3.setString(7, patient.getAllergies());
			st3.setString(8,patient.getDate());
			st3.setString(9, patient.getPrescription_file_url());
			st3.setString(10, patient.getDoctorId());
			st3.setInt(11, patient.getBloodPressure());
			st3.setFloat(12, patient.getSugarLevel());
			st3.setFloat(13, patient.getWeight());
			st3.setString(14, patient.getVisit_reason());
			
			int res = st3.executeUpdate();
			System.out.println("res "+res);
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
			
		}
		return patient;
	}
}
