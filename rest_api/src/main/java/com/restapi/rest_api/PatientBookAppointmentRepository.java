package com.restapi.rest_api;

import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.*;

public class PatientBookAppointmentRepository {

	   public static final String SLOTS_AVAILABLE_TBL_COL1 = "doctor_id"; // DOC MOBILE NUMBER
	    public static final String SLOTS_AVAILABLE_TBL_COL2 = "date";
	   // public static final String SLOTS_AVAILABLE_TBL_COL3 = "slot1";
	   // public static final String SLOTS_AVAILABLE_TBL_COL4 = "slots_avail_slot2";
	   // public static final String SLOTS_AVAILABLE_TBL_COL5 = "slots_avail_slot3";
	   // public static final String SLOTS_AVAILABLE_TBL_COL6 = "slots_avail_slot4";
	    
	List<PatientBookAppointment> list;
	Connection con = null;

	public PatientBookAppointmentRepository() {
		list = new ArrayList<PatientBookAppointment>();
		
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
	
	public List<PatientBookAppointment> getAllAppointments(){
		List<String>list1 = new ArrayList<String>();
		
		String sql = "select * from appointment_tbl";
		try {
			Statement st = con.createStatement();
			ResultSet res_set = st.executeQuery(sql);
			while(res_set.next()) {
				list1.add(res_set.getString(1));
				System.out.println(res_set.getString(1));
				PatientBookAppointment myobj = new PatientBookAppointment();
				myobj.setPatient_id(res_set.getString(1));
				myobj.setDoctor_id(res_set.getString(2));
				myobj.setDate_appoint(res_set.getString(3));
				myobj.setSlot_chosen(res_set.getInt(4));
				myobj.setReason_appointment(res_set.getString(5));
				list.add(myobj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	public PatientBookAppointment getAppointment(int index) {
		getAllAppointments();
		System.out.println("patient_id"+list.get(index).getPatient_id());
		return list.get(index);
	}
	
	public List<PatientBookAppointment> getPatientAppointment(String patient_id) {
		getAllAppointments();
		List<PatientBookAppointment> appointments = new ArrayList<PatientBookAppointment>();
		
		for(int i = 0; i< list.size();i++) {
			PatientBookAppointment appointment = list.get(i);
			if(appointment.getPatient_id().equals(patient_id)) {
				appointments.add(appointment);
			}
		}
		
		return appointments;
	}
	
	
	//SELECT * FROM doc_hospitals inner join doc_details_tbl on doc_hospitals.doc_id=doc_details_tbl.doc_id where doc_hospitals.doc_id = '1';

	public List<PatientGetAppointmentDetails> getPatientAppointments(String patient_id) {

		List<PatientGetAppointmentDetails> appointments = new ArrayList<PatientGetAppointmentDetails>();
		String sql = "select * from appointment_tbl where patient_id='" + patient_id + "'";
		
		try {
			Statement st = con.createStatement();
			ResultSet res_set = st.executeQuery(sql);
			while(res_set.next()) {
				String pat_id = patient_id;
				String doc_id = res_set.getString(2);
				String date_appointment = res_set.getString(3);
				int slot_chosen = res_set.getInt(4);
				String reason_visit = res_set.getString(5);
				
		/*		System.out.println("Patient_id: "+pat_id);
				System.out.println("Doc Id"+doc_id);
				System.out.println("date_appointment"+date_appointment);
				System.out.println("slot_chosen:"+slot_chosen);
				System.out.println("reason_visit"+reason_visit);*/
				
				PatientGetAppointmentDetails myobj = new PatientGetAppointmentDetails();
				myobj.setPatient_id(pat_id);			
				myobj.setDoctor_id(doc_id);
				myobj.setDate_appoint(date_appointment);
				myobj.setSlot_chosen(slot_chosen);
				myobj.setReason_appointment(reason_visit);
				//SELECT * FROM doc_hospitals inner join doc_details_tbl on doc_hospitals.doc_id=doc_details_tbl.doc_id;

				String sql1 ="SELECT * FROM doc_hospitals inner join doc_details_tbl on doc_hospitals.doc_id=doc_details_tbl.doc_id where doc_hospitals.doc_id ='"+doc_id+"'";
				Statement st1 = con.createStatement();
				ResultSet res_set1 = st1.executeQuery(sql1);
				String hosp_id = "";
				String hosp_name = "";
				String doc_name = "";
				while(res_set1.next()) {
					
					hosp_id = res_set1.getString(2);
					doc_name = res_set1.getString(4);
					System.out.println("hospital id"+hosp_id);
					Statement st2 = con.createStatement();
					String sql3 = "select name from hospitals where id = '"+hosp_id+"'";
					ResultSet res_set3 = st2.executeQuery(sql3);
					while(res_set3.next()) {
					//	System.out.println("hospital name"+res_set3.getString(1));
						hosp_name = res_set3.getString(1);
					}
				//	System.out.println("bye");
				}
				
			//		System.out.println("hey..."+doc_hosp.get(0));
					myobj.setHospital_id(hosp_id);
					myobj.setHospital_name(hosp_name);
					myobj.setDoctor_name(doc_name);
					myobj.setPrescription("");
					appointments.add(myobj);
					System.out.println("exit loop");
				}	
			}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return appointments;
	}
	
	
	public List<PatientBookAppointment> getDoctorCurrentDayAppointments(String doctor_id,String current_date) {		
		
		
/*
		List<PatientBookAppointment> doctor_currentday_appointments = new ArrayList<PatientBookAppointment>();
		String sql = "select * from appointment_tbl where doctor_id='" + doctor_id + "'" + " and appointment_date='"+ current_date + "'";
		
		try {
			Statement st = con.createStatement();
			ResultSet res_set = st.executeQuery(sql);
			while(res_set.next()) {
				System.out.println(res_set.getString(1));
				PatientBookAppointment myobj = new PatientBookAppointment();
				myobj.setPatient_id(res_set.getString(1));
				myobj.setDoctor_id(res_set.getString(2));
				myobj.setDate_appoint(res_set.getString(3));
				myobj.setSlot_chosen(res_set.getInt(4));
				myobj.setReason_appointment(res_set.getString(5));
				doctor_currentday_appointments.add(myobj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doctor_currentday_appointments;
	*/
		return null;
	}
		
	public List<PatientGetAppointmentDetails> getPatientCurrentDayAppointments(String patient_id,String current_date) {
		List<PatientGetAppointmentDetails> all_patient_appointments = getPatientAppointments(patient_id);
		List<PatientGetAppointmentDetails> patient_currentday_appointments = new ArrayList<PatientGetAppointmentDetails>();
		String current_date_DMY[] = current_date.split("-");
		int current_day_date = Integer.parseInt(current_date_DMY[2]);
		int current_day_month = Integer.parseInt(current_date_DMY[1]);
		int current_day_year = Integer.parseInt(current_date_DMY[0]);

	/*	System.out.println("CURRENT"+current_date_DMY[0]);
		System.out.println(current_date_DMY[1]);
		System.out.println(current_date_DMY[2]);*/

		for(int i = 0; i<all_patient_appointments.size();i++) {
			PatientGetAppointmentDetails appointment = all_patient_appointments.get(i);
			String appointment_date = appointment.getDate_appoint();
			String date_month_year[] = appointment_date.split("-");
			/*for(int i1 = 0; i1<date_month_year.length;i1++) {
				System.out.println("---"+date_month_year[i1]);
			}*/
			int date = Integer.parseInt(date_month_year[2]);
			int month = Integer.parseInt(date_month_year[1]);
			int year = Integer.parseInt(date_month_year[0]);
			
		/*	System.out.println("date" + date);
			System.out.println("month" + month);
			System.out.println("year" + year);*/
			
			if(year==current_day_year && month == current_day_month && date ==current_day_date) {
				
				appointment.setPrescription("");
			
				patient_currentday_appointments.add(appointment);
				
			//	System.out.println("HEY I AM PRESENT!!!");
			}
		}
		return patient_currentday_appointments;
	}
	
	
	public List<PatientGetAppointmentDetails> getPatientPastAppointments(String patient_id,String current_date,String doc_id) {
		List<PatientGetAppointmentDetails> all_patient_appointments = getPatientAppointments(patient_id);
		List<PatientGetAppointmentDetails> patient_past_appointments = new ArrayList<PatientGetAppointmentDetails>();
		String current_date_DMY[] = current_date.split("-");
		int current_day_date = Integer.parseInt(current_date_DMY[2]);
		int current_day_month = Integer.parseInt(current_date_DMY[1]);
		int current_day_year = Integer.parseInt(current_date_DMY[0]);

		System.out.println(current_date_DMY[0]);
		System.out.println(current_date_DMY[1]);

		System.out.println(current_date_DMY[2]);

		for(int i = 0; i<all_patient_appointments.size();i++) {
			PatientGetAppointmentDetails appointment = all_patient_appointments.get(i);
			String appointment_date = appointment.getDate_appoint();
			System.out.println("MY appointment:"+appointment_date);
			String date_month_year[] = appointment_date.split("-");
			int date = Integer.parseInt(date_month_year[2]);
			int month = Integer.parseInt(date_month_year[1]);
			int year = Integer.parseInt(date_month_year[0]);
			
			if(year<current_day_year) {
				
				String sql1 = "select * from patienthistory where pdate='" + appointment_date + "' and patientId = '"+patient_id+"' and doctorId = '"+doc_id+"'";
				
				Statement st;
				try {
					st = con.createStatement();
					ResultSet res_set = st.executeQuery(sql1);
					while(res_set.next()) {
						System.out.println("HEY I AM PRESENT!!!");
						String url = res_set.getString(10);
						if(url==null) {
							url ="";
						}
						
						appointment.setPrescription(url);
						
						patient_past_appointments.add(appointment);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(year==current_day_year){
				if(month<current_day_month) {
					String sql1 = "select * from patienthistory where pdate='" + appointment_date + "' and patientId = '"+patient_id+"'";
					
					Statement st;
					try {
						st = con.createStatement();
						ResultSet res_set = st.executeQuery(sql1);
						while(res_set.next()) {
							System.out.println("HEY I AM PRESENT!!!");
							String url = res_set.getString(10);
							appointment.setPrescription(url);
							patient_past_appointments.add(appointment);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(month==current_day_month){
					if(date<=current_day_date) {
						String sql1 = "select * from patienthistory where pdate='" + appointment_date + "' and patientId = '"+patient_id+"'";
						
						Statement st;
						try {
							st = con.createStatement();
							ResultSet res_set = st.executeQuery(sql1);
							while(res_set.next()) {
								System.out.println("HEY I AM PRESENT!!!");
								String url = res_set.getString(10);
								appointment.setPrescription(url);
								patient_past_appointments.add(appointment);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			else {
				
			}
		}
		
		return patient_past_appointments;
		
	}
	
	public List<PatientGetAppointmentDetails> getPatientFutureAppointments(String patient_id,String current_date) {
		List<PatientGetAppointmentDetails> all_patient_appointments = getPatientAppointments(patient_id);
		List<PatientGetAppointmentDetails> patient_future_appointments = new ArrayList<PatientGetAppointmentDetails>();
		String current_date_DMY[] = current_date.split("-");
		int current_day_date = Integer.parseInt(current_date_DMY[2]);
		int current_day_month = Integer.parseInt(current_date_DMY[1]);
		int current_day_year = Integer.parseInt(current_date_DMY[0]);

		System.out.println(current_date_DMY[0]);
		System.out.println(current_date_DMY[1]);

		System.out.println(current_date_DMY[2]);

		for(int i = 0; i<all_patient_appointments.size();i++) {
			PatientGetAppointmentDetails appointment = all_patient_appointments.get(i);
			String appointment_date = appointment.getDate_appoint();
			System.out.println("MY appointment:"+appointment_date);
			String date_month_year[] = appointment_date.split("-");
			int date = Integer.parseInt(date_month_year[2]);
			int month = Integer.parseInt(date_month_year[1]);
			int year = Integer.parseInt(date_month_year[0]);
			
			System.out.println("My year"+year);
			System.out.println("Current year"+current_day_year);
			
			if(year<current_day_year) {
				
			}
			else if(year==current_day_year){
				if(month<current_day_month) {

				}
				else if(month==current_day_month){
					if(date<current_day_date) {
					
					}
					else if(date>current_day_date){
						String sql1 = "select * from patienthistory where pdate='" + appointment_date + "' and patientId = '"+patient_id+"'";
						appointment.setPrescription("");
						patient_future_appointments.add(appointment);

					}
					else {
						
					}
				}
				else {
					appointment.setPrescription("");
					patient_future_appointments.add(appointment);
				}
			}
			else {		
				appointment.setPrescription("");
				patient_future_appointments.add(appointment);

			}
		}
		
		return patient_future_appointments; 
	}
	
	
	public Response createAppointment(PatientBookAppointment obj) {
		Response resp= new Response();
		
		System.out.println("slott-="+obj.getSlot_chosen());
		// before inserting into the database we should check that if the slots are available or not.
		
		// first check if the chosen (doctor, date available or not)
		
		System.out.println(obj.getDate_appoint());
		String doc_id = obj.getDoctor_id();
		String date = obj.getDate_appoint();
		String pat_id = obj.getPatient_id();
		
		/*First check if slots table has doctor slots entry*/
		String sql_find_slots = "select * from slots_table where doctor_id='" + doc_id + "'" + " and date='"+ date + "'";
		
	//	String sql_find = "select * from appointment_tbl where doctor_id='" + doc_id + "'" + " and appointment_date='"+ date + "'"+ " and patient_id='"+ pat_id + "'";
		//String sql_find = "select * from appointment_tbl";
		//String sql_find = "select * from appointment_tbl where doctor_id='" + doc_id + "'";
		//boolean entry_found = false;
		try {
			Statement st = con.createStatement();
			ResultSet res_set = st.executeQuery(sql_find_slots);
			
			if(res_set.next()) {// doctor slots there in slots table 
				System.out.println("I am presnet");
				String sql_find = "select * from appointment_tbl where doctor_id='" + doc_id + "'" + " and appointment_date='"+ date + "'"+ " and patient_id='"+ pat_id + "'";
				ResultSet res_set1 = st.executeQuery(sql_find);
				
				if(res_set1.next()) {
					System.out.println("My slot present: "+res_set1.getString(2));
					System.out.println("Sorry multiple appointments cannot be booked on the same day");
					resp.setResponse(2);
					return resp;
				}
				
				else {
					System.out.println("I am present and i have no appointment booked yet");
			
				//	entry_found = true;
				//String sql = "select * slots_table where slot"+obj.getSlot_chosen() +" = " + obj.getSlot_chosen() + "";
				//	String sql = "select * from slots_table where slot"+obj.getSlot_chosen() +" = 1" + " and doctor_id='" + doc_id + "'";
				
				String sql = "select slot"+obj.getSlot_chosen() +" from slots_table where doctor_id='" + doc_id + "'" + " and date='"+ date + "'";
				
				
					//String sql = "select * from slots_table where  slot"+obj.getSlot_chosen() +" = " + obj.getSlot_chosen();
				try {
					ResultSet res_set2 = st.executeQuery(sql);
					
					
					if(res_set2.next()) {
						System.out.println("hey I am booking slot");
						int slots_avail = res_set2.getInt(1);
						
						if(slots_avail<10) {
							slots_avail= slots_avail +1;
						
							String slots = slots_avail + "";
							System.out.println("slots avail: "+slots_avail);
							
							int slot_chosen = obj.getSlot_chosen();
							System.out.println(slot_chosen);
							String partial = "Update slots_table set ";
							String partial_midd = "slot"+slot_chosen;
							String end = "=? WHERE doctor_id=? and date=?";
							
							String sql_command = partial + partial_midd+end;
							PreparedStatement pst = con.prepareStatement( sql_command );
							pst.setInt( 1, slots_avail );
							pst.setString( 2, doc_id );
							pst.setString( 3, date );
	
	
							int updateResult = pst.executeUpdate();
							
							String insert_appointment = "insert into appointment_tbl values(?,?,?,?,?)";
							
							PreparedStatement st1 = con.prepareStatement(insert_appointment);
							st1.setString(1,obj.getPatient_id());
							st1.setString(2, obj.getDoctor_id());
							st1.setString(3,obj.getDate_appoint());
							st1.setInt(4,obj.getSlot_chosen());
							st1.setString(5,obj.getReason_appointment());
							st1.executeUpdate(); 
						
						}
						else {
							System.out.println("Chosen slot is not available");
							resp.setResponse(1);
							return resp;
						}
					}
					//	    i1 = st.executeQuery("select inTime,outTime from EmployeeAttendanceSystem.Clocking where EID=" + i + "");
					System.out.println("over");
				}
				
				
				catch(MySQLIntegrityConstraintViolationException e) {
					System.out.println("Duplicate primary keys");
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				list.add(obj);
				}
			}
			//rs = st.executeQuery("select * from EmployeeAttendanceSystem.Employee where username='" + userid + "'");
			else {
				System.out.println("hey there!!");
				
				String sql = "insert into slots_table values(?,?,?,?,?,?,?,?,?)";
					
			//	String sql = "insert into slots_table(doctor_id,date,slot+"+obj.getSlot_chosen()+")" + "values('" + obj.getDoctor_id() + "', '" + obj.getDate_appoint()+ "',1)";                          
			
				PreparedStatement st1 = con.prepareStatement(sql);
				st1.setString(1,obj.getDoctor_id());
				st1.setString(2, obj.getDate_appoint());
				st1.setInt(2+obj.getSlot_chosen(), 1);
				
				int i = 1 + obj.getSlot_chosen();	
				
				while(i>=3) {
					st1.setInt(i, 0);
					i--;
				}
				
				i = 3+obj.getSlot_chosen();
				while(i<=9) {
					st1.setInt(i, 0);
					i++;
				}
				st1.executeUpdate();
								
				String sql1 = "insert into appointment_tbl values(?,?,?,?,?)";
					PreparedStatement st2 = con.prepareStatement(sql1);
					
					st2.setString(1,obj.getPatient_id());
					st2.setString(2, obj.getDoctor_id());
					st2.setString(3, obj.getDate_appoint());
					st2.setInt(4, obj.getSlot_chosen());
					st2.setString(5, obj.getReason_appointment());
					st2.executeUpdate();
				
			}
			
			/*else {
				System.out.println("Inside else loop");
				System.out.println("Inside else loop");
				System.out.println("Inside else loop");

					String sql = "insert into appointment_tbl values(?,?,?,?,?)";
				try {
					PreparedStatement st1 = con.prepareStatement(sql);
					
					st1.setString(1,obj.getPatient_id());
					st1.setString(2, obj.getDoctor_id());
					st1.setString(3, obj.getDate_appoint());
					st1.setInt(4, obj.getSlot_chosen());
					st1.setString(5, obj.getReason_appointment());
					st1.executeUpdate();
					
				} 
				
				catch(MySQLIntegrityConstraintViolationException e) {
					System.out.println("Duplicate primary keys");
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				list.add(obj);
			}*/
		}
		
		catch(Exception e) {
			
		}
		
		resp.setResponse(0);
		return resp;
		// TODO Auto-generated method stub
		
	}
}
