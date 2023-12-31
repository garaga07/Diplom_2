package ru.praktikum.models;

public class UserCreateRequest {

	private String email;
	private String name;
	private String password;



	public UserCreateRequest(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}