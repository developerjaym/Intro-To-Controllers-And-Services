package com.cooksys.friendlr.mapper;

import org.mapstruct.Mapper;

import com.cooksys.friendlr.dto.PersonDto;
import com.cooksys.friendlr.entity.Person;

@Mapper(componentModel="spring")
public interface PersonMapper {
	PersonDto toPersonDto(Person person);
	
	Person toPerson(PersonDto personDto);
}
