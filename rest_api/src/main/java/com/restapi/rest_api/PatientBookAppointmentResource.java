package com.restapi.rest_api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("appointments")
public class PatientBookAppointmentResource {

	PatientBookAppointmentRepository repo = new PatientBookAppointmentRepository();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientBookAppointment> getPatientBookAppointments() { // get all entries in appointments table
		
		return repo.getAllAppointments();
	}
	// post is creating resource
	// put is for updating resource
	
	@GET
	@Path("appointment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientBookAppointment getAppointment(@PathParam("id") int index) {
		
		return repo.getAppointment(index);
	}

	@GET
	@Path("patient/{id}")
	//@Path("/doctor_currentday_appointments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientGetAppointmentDetails> getAppointment(@PathParam("id") String patient_id) {
		
		return repo.getPatientAppointments(patient_id);
	}
	
	
	@GET
	@Path("/doctor_currentday_appointments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientBookAppointment> getDoctorCurrentDayAppointments(@QueryParam("doctor_id") String patient_id,@QueryParam("current_date") String current_date) {
		
		return repo.getDoctorCurrentDayAppointments(patient_id, current_date);
		
	}
	
	
	@GET
	@Path("/patient_currentday_appointments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientGetAppointmentDetails> getPatientCurrentDayAppointments(@QueryParam("patient_id") String patient_id,@QueryParam("current_date") String current_date) {
		
		return repo.getPatientCurrentDayAppointments(patient_id, current_date);
		
	}
	
	@GET
	@Path("/patient_past_appointments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientGetAppointmentDetails> getPatientPastAppointments(@QueryParam("patient_id") String patient_id,@QueryParam("current_date") String current_date,@QueryParam("doc_id") String doc_id) {
		
		return repo.getPatientPastAppointments(patient_id, current_date,doc_id);
		
	}
	
	@GET
	@Path("/patient_future_appointments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientGetAppointmentDetails> getPatientFutureAppointments(@QueryParam("patient_id") String patient_id,@QueryParam("current_date") String current_date) {
		
		return repo.getPatientFutureAppointments(patient_id, current_date);
		
	}
	
	@POST
	@Path("appointment")
	public Response createAppointment(PatientBookAppointment obj) { //patient making an appointment with doctor
		
		System.out.println(obj.getPatient_id());
		return repo.createAppointment(obj);
		//System.out.println("slot chosen"+obj.getSlot_chosen());
		//return obj;
	}
}
