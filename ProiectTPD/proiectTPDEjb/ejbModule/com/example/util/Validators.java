package com.example.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
	
	public boolean isValidPhoneNumber(String phoneNumber) {
		String regex = "\\d{10}";
		
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(phoneNumber);
		
		return matcher.matches();
	}
	
}
