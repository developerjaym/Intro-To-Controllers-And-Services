package com.cooksys.friendlr.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.friendlr.dto.PersonDto;
import com.cooksys.friendlr.entity.Person;
import com.cooksys.friendlr.mapper.PersonMapper;


@Service
public class PersonService {
	private Map<Long, Person> people = new HashMap<Long, Person>();
	private static Long index = 1L;
	
	private PersonMapper personMapper;//dependency injection
	
	public PersonService(PersonMapper personMapper)//dependency injection
	{
		this.personMapper = personMapper;
		
		//make some fake people?
		Person a = new Person(index, "Jay", "M");
		people.put(index, a);
		index++;
		
		Person b = new Person(index, "Lucy", "H");
		people.put(index, b);
		index++;
		
		Person c = new Person(index, "Mike", "B");
		people.put(index, c);
		index++;
		
		Person d = new Person(index, "Will", "M");
		d.addFriend(a);
		d.addFriend(b);
		people.put(index, d);
		index++;
		
		
	}
	public boolean exists(Long id)
	{
		if(people.containsKey(id))
			return true;
		return false;
	}
	public Collection<PersonDto> getPeople() {
		return people.values().stream().map(personMapper::toPersonDto).collect(Collectors.toSet());//people.values();
	}
	public PersonDto get(Long id) {
		return personMapper.toPersonDto(people.get(id));
	}
	public PersonDto createPerson(PersonDto person) {
		person.setId(index);
		people.put(person.getId(), personMapper.toPerson(person));
		index++;
		return person;
	}
	public PersonDto patchPerson(Long id, PersonDto person){
		Person oldPerson = people.get(id);
		if(person.getFirstName()!=null)
			oldPerson.setFirstName(person.getFirstName());
		if(person.getLastName()!=null)
			oldPerson.setLastName(person.getLastName());
		return personMapper.toPersonDto(oldPerson);
	}
	public PersonDto putPerson(Long id, PersonDto person){
		Person oldPerson = people.get(id);
		oldPerson.setFirstName(person.getFirstName());
		oldPerson.setLastName(person.getLastName());
		return personMapper.toPersonDto(oldPerson);
	}
	public boolean deletePerson(Long id)
	{
		if(!people.containsKey(id))
			return false;
		people.remove(id);
		return true;
	}
	public List<PersonDto> getPersonsFriends(Long id) {
		
		return people.get(id).getFriends().stream().map(personMapper::toPersonDto).collect(Collectors.toList());
		
	}
	public void giveFriend(Long idOne, Long friendId) {
		people.get(idOne).addFriend(people.get(friendId));
	}
}
