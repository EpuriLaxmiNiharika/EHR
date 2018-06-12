package com.restapi.rest_api;

import java.util.ArrayList;

public class PatientGetAppointmentDetails {

	private String patient_id;
	private String doctor_id;
	private String date_appoint;
	private int slot_chosen;
	private String reason_appointment;
	private String doctor_name;
	private String hospital_id;
	private String hospital_name;
	private String prescription;
	
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}
	public String getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}
	public String getDate_appoint() {
		return date_appoint;
	}
	public void setDate_appoint(String date_appoint) {
		this.date_appoint = date_appoint;
	}
	public int getSlot_chosen() {
		return slot_chosen;
	}
	public void setSlot_chosen(int slot_chosen) {
		this.slot_chosen = slot_chosen;
	}
	public String getReason_appointment() {
		return reason_appointment;
	}
	public void setReason_appointment(String reason_appointment) {
		this.reason_appointment = reason_appointment;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(String doc_hosp) {
		this.hospital_id = doc_hosp;
	}
	public String getHospital_name() {
		return hospital_name;
	}
	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}
	
	
}
