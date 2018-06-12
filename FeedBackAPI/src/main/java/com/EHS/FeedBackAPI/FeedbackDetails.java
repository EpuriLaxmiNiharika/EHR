package com.EHS.FeedBackAPI;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;


public class FeedbackDetails {

	String hospital_id;
	String doctor_id;
	Double doctor_review;
	Double hospital_review;
	Double hospital_service;
	Double overall_service;
	
	
	public String getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(String hospital_id) {
		this.hospital_id = hospital_id;
	}
	public String getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}
	public Double getDoctor_review() {
		return doctor_review;
	}
	public void setDoctor_review(Double doctor_review) {
		this.doctor_review = doctor_review;
	}
	public Double getHospital_review() {
		return hospital_review;
	}
	public void setHospital_review(Double hospital_review) {
		this.hospital_review = hospital_review;
	}
	public Double getHospital_service() {
		return hospital_service;
	}
	public void setHospital_service(Double hospital_service) {
		this.hospital_service = hospital_service;
	}
	public Double getOverall_service() {
		return overall_service;
	}
	public void setOverall_service(Double overall_service) {
		this.overall_service = overall_service;
	}
	
	@Override
	public String toString() {
		return "DoctorDetails [hospital_id=" + hospital_id + ", doctor_id=" + doctor_id + ", doctor_review="
				+ doctor_review + ", hospital_review=" + hospital_review + ", hospital_service=" + hospital_service
				+ ", overall_service=" + overall_service + ", comments=" +  "]";
	}
	
}
