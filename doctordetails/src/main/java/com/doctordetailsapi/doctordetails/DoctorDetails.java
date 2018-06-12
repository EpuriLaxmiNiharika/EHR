package com.doctordetailsapi.doctordetails;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;


public class DoctorDetails {

	private String doc_id;
	private String name;
	//private String address ;
//	private Long phone;
	private ArrayList<String> hospital_id;
	
	public String getSpecialization_id() {
		return specialization_id;
	}
	public void setSpecialization_id(String specialization_id) {
		this.specialization_id = specialization_id;
	}
	public String getSpecialization_name() {
		return specialization_name;
	}
	public void setSpecialization_name(String specialization_name) {
		this.specialization_name = specialization_name;
	}

	private ArrayList<String> hospital_name;
	
	private String designation;
	private String specialization_id;
	private String specialization_name;
	
	private String gender;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	private int year_join;
	
	public int getYear_join() {
		return year_join;
	}
	public void setYear_join(int year_join) {
		this.year_join = year_join;
	}
	public ArrayList<String> getHospital_name() {
		return hospital_name;
	}
	public void setHospital_name(ArrayList<String> hospital_name) {
		this.hospital_name = hospital_name;
	}
	
	//private String email_id;
	public String getDoc_id() {
		return doc_id;
	}
	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}*/
	public ArrayList<String> getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(ArrayList<String> hospital_id) {
		this.hospital_id = hospital_id;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
	/*public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}*/
	
	@Override
	public String toString() {
		return "DoctorDetails [doc_id=" + doc_id + ", name=" + name + ", hospital_id=" + hospital_id + ", designation="
				+ designation + ", specialization_id=" + specialization_id + "]";
	}
	
	
}
