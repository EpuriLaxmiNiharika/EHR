package com.EHS.PatientHealthHistoryAPI;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("patient_history")
public class PatientHistoryResource {


	PatientHistoryRepository repo = new PatientHistoryRepository();

@GET
@Path("patient/{id}") // get patient history
@Produces(MediaType.APPLICATION_JSON)
public PatientHistory getPatientHistory(@PathParam("id") String patient_id) {
	
	return repo.getPatientHistory(patient_id);
}


@GET
@Path("all_history/{id}") // get all history of patient
@Produces(MediaType.APPLICATION_JSON)
public List<PatientHistory> getDoctorFeedback(@PathParam("id") String patient_id) {
	
	return repo.getAllPatientHistory(patient_id);
}


@POST
@Path("add_history")
public PatientHistory createHistory(PatientHistory obj) {
	
	return repo.createPatientHistory(obj);
}

}
