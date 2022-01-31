package by.epam.training.studentcourses.util.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import by.epam.training.studentcourses.util.Identifiable;

public class User implements Identifiable, Serializable {
	private static final long serialVersionUID = -3351049416576412918L;
	private Integer id;
	private LocalDateTime registrationDateTime; // forbid to edit later!
	private UserRole role; 
	private String name;
	private String surename;
	private String patronymic; 
	private LocalDate birthDate;
	private String phoneNumber; 
	private String email;
	private String login;
	private String password;
	private String description;
	
	public User() {}
	
	public User(Integer id, UserRole role, String name, String surename, String patronymic, LocalDate birthDate, 
			String phoneNumber, String emali, String login, String password, String desciption, LocalDateTime registrationDateTime) {
		this.id = id;
		this.role = role;
		this.name = name;
		this.surename = surename;
		this.patronymic = patronymic;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.email = emali;
		this.login = login;
		this.password = password;
		this.description = desciption;
		this.registrationDateTime = registrationDateTime;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", registrationDateTime=" + registrationDateTime + ", role=" + role + ", name=" + name
				+ ", surename=" + surename + ", patronymic=" + patronymic + ", birthDate=" + birthDate
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", login=" + login + ", password=" + password
				+ ", desciption=" + description + "]";
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}	

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurename() {
		return surename;
	}
	
	public void setSurename(String surename) {
		this.surename = surename;
	}
	
	public String getPatronymic() {
		return patronymic;
	}
	
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}

	public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
}
