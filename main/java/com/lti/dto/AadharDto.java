package com.lti.dto;

import org.springframework.web.multipart.MultipartFile;

public class AadharDto {

	private MultipartFile aadharCard;  //File Type
	
	
	public MultipartFile getAadharCard() {
		return aadharCard;
	}
	public void setAadharCard(MultipartFile aadharCard) {
		this.aadharCard = aadharCard;
	}
	
	
}
