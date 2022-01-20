package by.epam.training.studentcourses.util.entity;

import java.time.LocalDateTime;

import by.epam.training.studentcourses.util.Identifiable;

public class Course implements Identifiable {
	private Integer id;
	private String name;
	private String duration;
	private String description;
	private LocalDateTime creationTime;
	
	public Course(Integer id, String name, String duration, String description, LocalDateTime creationTime) {
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.description = description;
		this.creationTime = creationTime;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public LocalDateTime getCreationDateTime() {
		return creationTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", duration=" + duration + ", description=" + description
				+ ", creationTime=" + creationTime + "]";
	}
	
	
			
}
