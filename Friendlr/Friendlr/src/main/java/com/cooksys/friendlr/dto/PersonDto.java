package com.cooksys.friendlr.dto;


public class PersonDto {
	private Long id;
	private String firstName;
	private String lastName;
	
	public PersonDto()
	{
		
	}

	public PersonDto(Long iD, String firstName, String lastName) {
		id = iD;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person [ID=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long iD) {
		id = iD;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
