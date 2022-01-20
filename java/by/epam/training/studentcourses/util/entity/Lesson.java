package by.epam.training.studentcourses.util.entity;

import java.time.LocalDateTime;

import by.epam.training.studentcourses.util.Identifiable;

public class Lesson implements Identifiable {
	private Integer id;
	private CoursePlan coursePlan;
	private LocalDateTime startDateTime;
	private Integer durationInMin;
	private Integer classroomNumber;
	Boolean isCompleted;
	
	public Lesson() {}
	
	public Lesson(Integer id, CoursePlan coursePlan, LocalDateTime startDateTime, Integer durationInMin,
			Integer classroomNumber, Boolean isCompleted) {
		super();
		this.id = id;
		this.coursePlan = coursePlan;
		this.startDateTime = startDateTime;
		this.durationInMin = durationInMin;
		this.classroomNumber = classroomNumber;
		this.isCompleted = isCompleted;
	}

	public CoursePlan getCoursePlan() {
		return coursePlan;
	}
	
	public void setCoursePlan(CoursePlan coursePlan) {
		this.coursePlan = coursePlan;
	}
	
	public LocalDateTime getStartTime() {
		return startDateTime;
	}
	
	public void setStartTime(LocalDateTime beginningTime) {
		this.startDateTime = beginningTime;
	}
	
	public Integer getDuration() {
		return durationInMin;
	}
	
	public void setDuration(Integer duration) {
		this.durationInMin = duration;
	}
	
	public Integer getClassroomNumber() {
		return classroomNumber;
	}
	
	public void setClassroomNumber(int classroomNumber) {
		this.classroomNumber = classroomNumber;
	}
	
	public Boolean isCompleted() {
		return isCompleted;
	}
	
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
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
