package by.epam.training.studentcourses.util.entity;

import java.time.LocalDate;

import by.epam.training.studentcourses.util.Identifiable;

public class CoursePlan implements Identifiable {
	@Override
	public String toString() {
		return "CoursePlan [id=" + id + ", courseId=" + courseId + ", trainerUserId=" + trainerUserId + ", startDate="
				+ startDate + ", status=" + status + ", description=" + description + "]";
	}

	private Integer id;
	private Integer courseId;
	private Integer trainerUserId;
	private LocalDate startDate;
	private CoursePlanStatus status;
	private String description;

	public CoursePlan() {
	}

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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

}
