package com.EHS.GET_Hospitals_API;

public class HospitalDetails {

	String id;
	String name;
	String streetAddress;
	String location;
	String phoneNumber;
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	String city;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getStreetAddress() {
		return streetAddress;
	}


	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	@Override
	public String toString() {
		return "HospitalDetails [id=" + id + ", name=" + name + ", streetAddress=" + streetAddress + ", location="
				+ location + ", city=" + city + "]";
	}
	
	
	
}
