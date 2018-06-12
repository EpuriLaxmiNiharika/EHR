package com.EHS.FeedBackAPI;

public class DoctorFeedback {

	String Doctor_id;
	Double service_review;
	Double review;
	Double overall_review;
	
	public String getDoctor_id() {
		return Doctor_id;
	}
	public void setDoctor_id(String Doctor_id) {
		this.Doctor_id = Doctor_id;
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
