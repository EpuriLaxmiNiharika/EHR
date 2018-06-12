package com.restapi.rest_api;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PatientBookAppointment {

	private String patient_id;
	private String doctor_id;
	private String date_appoint;
	private int slot_chosen;
	private String reason_appointment;
		
	public String getReason_appointment() {
		return reason_appointment;
	}
	public void setReason_appointment(String reason_appointment) {
		this.reason_appointment = reason_appointment;
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
	public void setSlot_chosen (int slot_chosen) {
		this.slot_chosen = slot_chosen;
	}
	@Override
	public String toString() {
		return "PatientBookAppointment [patient_id=" + patient_id + ", doctor_id=" + doctor_id + ", date_appoint="
				+ date_appoint + ", slot_chosen=" + slot_chosen + "]";
	}
	
	
}
