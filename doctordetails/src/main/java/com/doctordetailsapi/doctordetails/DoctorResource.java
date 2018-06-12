package com.doctordetailsapi.doctordetails;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("doctors")
public class DoctorResource {

	DoctorRepository repo = new DoctorRepository();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DoctorDetails> getDoctors() throws Exception{
		return repo.getDoctors();
	}
	// post is creating resource
	// put is for updating resource
	
	@GET
	@Path("doctor/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DoctorDetails getDoctor(@PathParam("id") String index)  throws Exception{
		
		return repo.getDoctor(index);
	}
	
	@GET
	@Path("specialization/{id}") // get doctors based on specialization NAME
	@Produces(MediaType.APPLICATION_JSON)
	public List<DoctorDetails> getDoctors(@PathParam("id") String index)  throws Exception{
		
		return repo.getDoctors(index);
	}
	
	@GET
	@Path("specialization_id/{id}") // get doctors based on specialization ID
	@Produces(MediaType.APPLICATION_JSON)
	public List<DoctorDetails> getDoctors_SpecializationID(@PathParam("id") String index) {
		
		return repo.getDoctors_specializationID(index);
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
