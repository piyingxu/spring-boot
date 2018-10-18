package com.util;

public class ReqBody {
	
    private String transid;
   
    private String msisdn;
    
    private String firstName;
    
    private String lastName;

    private String addressCountry;
    
    private String currency;

	public String getTransid() {
		return transid;
	}
	
	public void setTransid(String transid) {
		this.transid = transid;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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

	public String getAddressCountry() {
		return addressCountry;
	}

	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	  
}
