package com.EHS.GET_Hospitals_API;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hospitals")
public class HospitalResource {

	HospitalRepository repo = new HospitalRepository();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<HospitalDetails> getHospitals() {
		return repo.getHospitals();
	}
	// post is creating resource
	// put is for updating resource
	
	@GET
	@Path("hospital/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public HospitalDetails getHospital(@PathParam("id") String index) {
		
		return repo.getHospital(index);
	}
	
	@GET
	@Path("specialization/{id}") // get doctors based on specialization name
	@Produces(MediaType.APPLICATION_JSON)
	public List<HospitalDetails> getHospitals(@PathParam("id") String index) {
		
		return repo.getHospitals(index);
	}
	
	@GET
	@Path("specialization_id/{id}") // get doctors based on specialization ID
	@Produces(MediaType.APPLICATION_JSON)
	public List<HospitalDetails> getHospitals_SpecializationID(@PathParam("id") String index) {
		
		return repo.getHospitals_specializationID(index);
	}
	
/*
	@POST
	@Path("dotor")
	public DoctorDetails createDoctor(DoctorDetails obj) {
		
		System.out.println(obj.getName());
		repo.create(obj);
		return obj;
	}*/
}
