package by.epam.training.studentcourses.util.entity;

import by.epam.training.studentcourses.util.Identifiable;

public class CoursePlan implements Identifiable {
	private Integer id;
	private Integer courseId;
	private Integer trainerUserId;
	private CoursePlanStatus status;
	private String description;
	
	public CoursePlan(Integer id, Integer courseId, Integer trainerUserId, CoursePlanStatus status, String description) {
		this.id = id;
		this.courseId = courseId;
		this.trainerUserId = trainerUserId;
		this.status = status;
		this.description = description;
	}

	public CoursePlan() { }

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getTrainerUserId() {
		return trainerUserId;
	}

	public void setTrainerId(Integer trainerUserId) {
		this.trainerUserId = trainerUserId;
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
