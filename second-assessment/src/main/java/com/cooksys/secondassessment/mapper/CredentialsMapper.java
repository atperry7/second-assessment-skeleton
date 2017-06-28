package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.CredentialsNoPwdDto;
import com.cooksys.secondassessment.entity.Credentials;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
	
	CredentialsNoPwdDto credentialsNoPwdDto(Credentials c);
	Credentials toCredentials(CredentialsNoPwdDto c);
	
}
