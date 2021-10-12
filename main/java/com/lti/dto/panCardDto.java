package com.lti.dto;

import org.springframework.web.multipart.MultipartFile;

public class panCardDto {
	private MultipartFile panCard;  //File Type
	
	public MultipartFile getPanCard() {
		return panCard;
	}
	public void setPanCard(MultipartFile panCard) {
		this.panCard = panCard;
	}
	
	

}
