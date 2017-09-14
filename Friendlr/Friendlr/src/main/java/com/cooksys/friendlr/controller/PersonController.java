package com.cooksys.friendlr.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.friendlr.dto.PersonDto;
import com.cooksys.friendlr.service.PersonService;


@RestController
@RequestMapping("person")
public class PersonController {
	
	private PersonService personService;
	
	public PersonController(PersonService personService)
	{ 
		this.personService = personService;
	}
	
	@GetMapping()
	public Collection<PersonDto> getPeople()
	{
		//this will retrieve a list of all PersonDtos
		return personService.getPeople();
	}
	
	@GetMapping("{id}")
	public PersonDto getPerson(@PathVariable Long id, HttpServletResponse  response)
	{
		if(personService.exists(id))
			return personService.get(id);
		else
			response.setStatus(404);
		return null;
		//this will retrieve a single PersonDto as indicated
			//by the {id} or return 404 if the ID does not exist
	}
	
	
	@GetMapping("{id}/friends")
	public List<PersonDto> getPersonsFriends(@PathVariable Long id, HttpServletResponse  response)
	{
		if(personService.exists(id))
			return personService.getPersonsFriends(id);
		else
			response.setStatus(404);
		return null;
		//this will retrieve a single PersonDto as indicated
			//by the {id} or return 404 if the ID does not exist
	}
	
	@PatchMapping("{id}/patch")
	public void patchFriend(@PathVariable Long id, @RequestBody PersonDto pdto, HttpServletResponse response)
	{
		if(!personService.exists(id))
			response.setStatus(404);
		else
			personService.patchPerson(id, pdto);
	}
	
	@PatchMapping("{id}/{friendId}")
	public void giveFriend(@PathVariable Long id, @PathVariable Long friendId, HttpServletResponse response)
	{
		if(id.equals(friendId))
		{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		response.setStatus(201);
		if(personService.exists(id) && personService.exists(friendId))
			personService.giveFriend(id, friendId);
		else
			response.setStatus(404);
	}
	
	@PostMapping
	public PersonDto createPerson(@RequestBody PersonDto person, HttpServletResponse  response)
	{
		PersonDto returnMe = personService.createPerson(person);
		response.setStatus(201);
		return returnMe;
		//create a Person instance
		//assign an ID to that Person
		//store the Person instance in a collection in the Person service
		//it will then return a 201 - Created status code
		//Return the 404-Not Found if that ID does not exist ?????
	}
	
	@PutMapping("{id}")
	public PersonDto putPerson(@PathVariable Long id, @RequestBody PersonDto person, HttpServletResponse  response)//how?
	{
		if(!personService.exists(id))
		{
			response.setStatus(404);
			return null;
		}
		response.setStatus(200);
		return personService.putPerson(id, person);
		//this wil overwrite the Person with the indicated ID
			//with the unmarshalled JSON contents of the body of this request
		//or return 404 if that ID does not exist
	}
	
	@DeleteMapping("{id}/friends/{friendId}")
	public void unfriend(@PathVariable Long id, @PathVariable Long friendId, HttpServletResponse response)
	{
		response.setStatus(201);
		if(id.equals(friendId))
		{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		if(personService.exists(id) && personService.exists(friendId))
		{
			boolean possible = personService.unfriend(id, friendId);
			if(possible)
				response.setStatus(200);
			else
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		else
			response.setStatus(404);
	}
	
	@DeleteMapping("{id}")
	public PersonDto deletePerson(@PathVariable Long id, HttpServletResponse  response)
	{
		if(personService.exists(id))
			personService.deletePerson(id);
		else
			response.setStatus(404);
		return null;
		//this will delete the Person with the ID specified
		//and remove all references to the specified ID in the Friends list of all other Person objects
		//return the 404 if the ID does not exist
		
	}
}
