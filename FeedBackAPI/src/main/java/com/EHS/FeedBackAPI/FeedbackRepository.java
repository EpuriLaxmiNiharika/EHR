package com.EHS.FeedBackAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;

public class FeedbackRepository {

	Connection con = null;

	public FeedbackRepository() {
		
	/*	String url = "jdbc:mysql://localhost:3306/sample_db";
		String uname = "root";
		String password = "123";
	*/	
		
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
	
	
	HospitalFeedback getHospitalFeedback(String hosp_id){
		
		try {
			/*String sql1 = "select id from hospitals where name = '"+hosp_name+"'";;
			Statement st1;
			st1 = con.createStatement();
		
			ResultSet res_set1 = st1.executeQuery(sql1);
			String hosp_id = "";
		
			if(res_set1.next()) {
				hosp_id  = res_set1.getString(1); // get hospital_id
				
			}
			System.out.println("Hospital ID: "+hosp_id);
		*/
			String sql2 = "select * from review_tbl where hospital_id = '"+hosp_id+"'";;
			Statement st2;
			st2 = con.createStatement();
		
			ResultSet res_set2 = st2.executeQuery(sql2);
			Double hosp_service = 0.0,hosp_review = 0.0,overall_service = 0.0;
			int count = 0;
			
			while(res_set2.next()) {
			//	System.out.println("get:");
				hosp_service = hosp_service + res_set2.getDouble(4);
				hosp_review = hosp_review + res_set2.getDouble(5);
				overall_service = overall_service + res_set2.getDouble(6);
				
				HospitalFeedback feedback = new HospitalFeedback();
				feedback.setHospital_id(hosp_id);;
				feedback.setOverall_review(overall_service);
				feedback.setReview(hosp_review);
				feedback.setService_review(hosp_service);
				return feedback;
			}
			
			if(count==0) {
				HospitalFeedback feedback = new HospitalFeedback();
				feedback.setHospital_id(hosp_id);
				feedback.setOverall_review(0.0);
				feedback.setReview(0.0);
				feedback.setService_review(0.0);
				st2.close();
				return feedback;
			}
			st2.close();
		//	System.out.println("service: "+hosp_service/(1.0*count));
		//	System.out.println("review: "+hosp_review/(1.0*count));
		//	System.out.println(" overall "+overall_service*1.0/(count));
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		HospitalFeedback feedback = new HospitalFeedback();
		feedback.setHospital_id(hosp_id);
		feedback.setOverall_review(0.0);
		feedback.setReview(0.0);
		feedback.setService_review(0.0);
		return feedback;

	}
	
DoctorFeedback getDoctorFeedback(String doc_id){
		
	
	try {
	
		String sql2 = "select * from review_tbl where doctor_id = '"+doc_id+"'";
		Statement st2;
		st2 = con.createStatement();
	
		ResultSet res_set2 = st2.executeQuery(sql2);
		Double hosp_service = 0.0,hosp_review = 0.0,overall_service = 0.0;
		int count = 0;
		
		while(res_set2.next()) {
	//		System.out.println("get");
			hosp_service = hosp_service + res_set2.getDouble(4);
			hosp_review = hosp_review + res_set2.getDouble(5);
			overall_service = overall_service + res_set2.getDouble(6);
			
			DoctorFeedback feedback = new DoctorFeedback();
			feedback.setDoctor_id(doc_id);
			feedback.setOverall_review(overall_service);
			feedback.setReview(hosp_review);
			feedback.setService_review(hosp_service);
			st2.close();
			return feedback;
		}
	
		
		DoctorFeedback feedback = new DoctorFeedback();
		feedback.setDoctor_id(doc_id);
		feedback.setOverall_review(0.0);
		feedback.setReview(0.0);
		feedback.setService_review(0.0);
		st2.close();
		return feedback;
		
	/*	st2.close();
		DoctorFeedback feedback = new DoctorFeedback();
		feedback.setDoctor_id(doc_id);
		feedback.setOverall_review(overall_service/(1.0*count));
		feedback.setReview(hosp_review/(1.0*count));
		feedback.setService_review(hosp_service/(1.0*count));
		*/
	}
	catch(Exception e) {
		e.printStackTrace();
		DoctorFeedback feedback = new DoctorFeedback();
		feedback.setDoctor_id(doc_id);
		feedback.setOverall_review(0.0);
		feedback.setReview(0.0);
		feedback.setService_review(0.0);
		return feedback;
	}
	
		
	}

FeedbackDetails createFeedback(FeedbackDetails feedback) {
		
		String doc_id = feedback.doctor_id;
		String hosp_id = feedback.hospital_id;
		
		String sql = "select * from review_tbl where hospital_id = '"+hosp_id+"' and doctor_id = '"+doc_id+"'";
		try {
			Statement st2;
			st2 = con.createStatement();
			ResultSet res_set1 = st2.executeQuery(sql);
			
			if(res_set1.next()) {
				
				Double doctor_review = res_set1.getDouble(3);
				Double hospital_review = res_set1.getDouble(4);
				Double hospital_service = res_set1.getDouble(5);
				Double overall_review = res_set1.getDouble(6);
				int count = res_set1.getInt(7);
				System.out.println(doc_id);
				System.out.println(hospital_review);
				System.out.println(hospital_service);
				System.out.println(overall_review);
				System.out.println(count);
			
				doctor_review = (doctor_review * count + feedback.getDoctor_review())/((count+1)*1.0);
				hospital_review = (doctor_review * count + feedback.getHospital_review())/((count+1)*1.0);
				hospital_service = (doctor_review * count + feedback.getHospital_service())/((count+1)*1.0);
				overall_review = (overall_review * count + feedback.getOverall_service())/((count+1)*1.0);

				String sql2 = "delete from review_tbl where hospital_id =? and doctor_id = ?";

				PreparedStatement st_2 = con.prepareStatement(sql2);
				st_2.setString(1, hosp_id);
				st_2.setString(2, doc_id);
				
				boolean  res1 = st_2.execute();
				
				
				//System.out.println("del: "+res1);
				String sql3 = "insert into review_tbl(hospital_id,doctor_id,doctor_review,hospital_review,hospital_service,overall_service,count) values(?,?,?,?,?,?,?)";
						
				PreparedStatement st3 = con.prepareStatement(sql3);
				st3.setDouble(3, doctor_review);
				st3.setDouble(4, hospital_review);
				st3.setDouble(5, hospital_service);
				st3.setDouble(6, overall_review);
				st3.setInt(7, count+1);
				st3.setString(1, hosp_id);
				st3.setString(2, doc_id);
				
				int res = st3.executeUpdate();
				System.out.println("res "+res);
				
			}
			
			else {
				
				String sql3 = "insert into review_tbl(hospital_id,doctor_id,doctor_review,hospital_review,hospital_service,overall_service,count) values(?,?,?,?,?,?,?)";
				
				PreparedStatement st3 = con.prepareStatement(sql3);
				st3.setString(1, hosp_id);
				st3.setString(2, doc_id);
				st3.setDouble(3, feedback.getDoctor_review());
				st3.setDouble(4, feedback.getHospital_review());
				st3.setDouble(5, feedback.getHospital_service());
				st3.setDouble(6, feedback.getOverall_service());
				st3.setInt(7, 1);
				
				int res = st3.executeUpdate();
				System.out.println("result "+res);
			}
		}
		catch(Exception e) {
			
		}
		return feedback;
	}

	List<FeedbackDetails> getAllFeedbacks(){
		
		List<FeedbackDetails> feedbacks = new ArrayList<FeedbackDetails>();
		
		String sql = "select * from review_tbl";
		try {
			Statement st2;
			st2 = con.createStatement();
			ResultSet res_set1 = st2.executeQuery(sql);
			
			while(res_set1.next()) {
				
				FeedbackDetails feedback = new FeedbackDetails();
				feedback.setDoctor_id(res_set1.getString(1));
				feedback.setHospital_id(res_set1.getString(2));
				feedback.setDoctor_review(res_set1.getDouble(3));
				feedback.setHospital_review(res_set1.getDouble(4));
				feedback.setHospital_service(res_set1.getDouble(5));
				feedback.setOverall_service(res_set1.getDouble(6));
				feedbacks.add(feedback);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return feedbacks;
	}
}

// List hospitals based on location
// List hospitals based on 
