package by.epam.training.studentcourses.service;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.CoursePlan;

public interface CoursePlanValidator {
	
	Tables.CoursesPlans.Attr validate(CoursePlan coursePlan);
	
}
