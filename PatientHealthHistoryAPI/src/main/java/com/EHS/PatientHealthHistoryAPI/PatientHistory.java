package com.EHS.PatientHealthHistoryAPI;

public class PatientHistory {

	private String patientId;			
    private String conditions;			
    private String symptoms;			
    private Boolean isTobbaco;			
    private String prevMedication;			
    private Boolean isAlohocolic;			
    private String allergies;		
    private String date;
    private String prescription_file_url;
    private String doctorId;  
    private int bloodPressure;
    private float sugarLevel;
    private float weight;
    private String visit_reason;
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public String getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}
	public Boolean getIsTobbaco() {
		return isTobbaco;
	}
	public void setIsTobbaco(Boolean isTobbaco) {
		this.isTobbaco = isTobbaco;
	}
	public String getPrevMedication() {
		return prevMedication;
	}
	public void setPrevMedication(String prevMedication) {
		this.prevMedication = prevMedication;
	}
	public Boolean getIsAlohocolic() {
		return isAlohocolic;
	}
	public void setIsAlohocolic(Boolean isAlohocolic) {
		this.isAlohocolic = isAlohocolic;
	}
	public String getAllergies() {
		return allergies;
	}
	public void setAllergies(String string) {
		this.allergies = string;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPrescription_file_url() {
		return prescription_file_url;
	}
	public void setPrescription_file_url(String prescription_file_url) {
		this.prescription_file_url = prescription_file_url;
	}
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	public int getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(int bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public float getSugarLevel() {
		return sugarLevel;
	}
	public void setSugarLevel(float sugarLevel) {
		this.sugarLevel = sugarLevel;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getVisit_reason() {
		return visit_reason;
	}
	public void setVisit_reason(String visit_reason) {
		this.visit_reason = visit_reason;
	}
    
    
}
