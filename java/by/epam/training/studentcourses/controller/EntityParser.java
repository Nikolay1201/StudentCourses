package by.epam.training.studentcourses.controller;

import java.util.List;
import java.util.Map;

import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.entity.Course;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.Lesson;
import by.epam.training.studentcourses.util.entity.User;

public interface EntityParser {
	
	List<User> parseUsers(Map<String, String[]> paramsMap) throws InvalidRequestException;
	List<Course> parseCourses(Map<String, String[]> paramsMap) throws InvalidRequestException;
	List<CoursePlan> parseCoursePlans(Map<String, String[]> paramsMap) throws InvalidRequestException;
	List<Lesson> parseLessons(Map<String, String[]> paramsMap) throws InvalidRequestException;
	Filter parseFilter(Map<String, String[]> paramsMap) throws InvalidRequestException;
	
}
