package by.epam.training.studentcourses.util.entity;

import by.epam.training.studentcourses.util.Identifiable;

public class CoursePlan implements Identifiable {
	private Integer id;
	private Course course;
	private User trainer;
	private CoursePlanStatus status;
	private String description;
	
	public CoursePlan(Integer id, Course course, User trainer, CoursePlanStatus status, String description) {
		this.id = id;
		this.course = course;
		this.trainer = trainer;
		this.status = status;
		this.description = description;
	}

	public CoursePlan() { }

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public User getTrainer() {
		return trainer;
	}

	public void setTrainer(User trainer) {
		this.trainer = trainer;
	}

	public CoursePlanStatus getStatus() {
		return status;
	}

	public void setStatus(CoursePlanStatus status) {
		this.status = status;
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
		
	
}
