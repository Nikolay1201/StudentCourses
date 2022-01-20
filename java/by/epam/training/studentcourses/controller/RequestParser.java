package by.epam.training.studentcourses.controller;

import java.util.List;
import java.util.Map;

import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.Lesson;
import by.epam.training.studentcourses.util.entity.User;

public interface RequestParser {
	
	List<User> parseUsers(Map<String, String[]> paramsMap);
	List<Course> parseCourses(Map<String, String[]> paramsMap);
	List<CoursePlan> parseCoursePlans(Map<String, String[]> paramsMap);
	List<Lesson> parseLessons(Map<String, String[]> paramsMap);
	Filter parseFiltrer(Map<String, String[]> paramsMap, boolean ignoreInvalidConditions);
	
}
