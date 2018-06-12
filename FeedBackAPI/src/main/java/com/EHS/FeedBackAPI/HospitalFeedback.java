package com.EHS.FeedBackAPI;

public class HospitalFeedback {

	String hospital_id;
	Double service_review;
	Double review;
	Double overall_review;
	
	public String getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(String hospital_id) {
		this.hospital_id = hospital_id;
	}
	public Double getService_review() {
		return service_review;
	}
	public void setService_review(Double service_review) {
		this.service_review = service_review;
	}
	public Double getReview() {
		return review;
	}
	public void setReview(Double review) {
		this.review = review;
	}
	public Double getOverall_review() {
		return overall_review;
	}
	public void setOverall_review(Double overall_review) {
		this.overall_review = overall_review;
	}
}
