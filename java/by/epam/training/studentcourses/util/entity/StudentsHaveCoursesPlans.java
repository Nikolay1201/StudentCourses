package by.epam.training.studentcourses.util.entity;

import by.epam.training.studentcourses.util.Identifiable;

public class StudentsHaveCoursesPlans implements Identifiable {
	
	private Integer id;
	private Integer studentId;
	private Integer coursePlanId;
	private Integer mark;
	private String review;
	
	public StudentsHaveCoursesPlans() {}

	public StudentsHaveCoursesPlans(Integer id, Integer studentId, Integer coursePlanId, Integer mark, String review) {
		this.id = id;
		this.studentId = studentId;
		this.coursePlanId = coursePlanId;
		this.mark = mark;
		this.review = review;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;

	}

	public Integer getCoursePlanId() {
		return coursePlanId;
	}

	public void setCoursePlanId(Integer coursePlanId) {
		this.coursePlanId = coursePlanId;
	}

	public Integer getStudentUserId() {
		return studentId;
	}

	public void setStudentUserId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

}
