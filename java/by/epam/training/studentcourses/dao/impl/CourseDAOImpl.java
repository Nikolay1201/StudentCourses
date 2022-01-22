package by.epam.training.studentcourses.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.training.studentcourses.dao.CourseDAO;
import by.epam.training.studentcourses.dao.impl.dbmeta.MySQLTypeConverter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Course;

public class CourseDAOImpl extends EntityAbstractDAO<Course> implements CourseDAO {
	
	public CourseDAOImpl() {
		super(Tables.Courses.TABLE_NAME, Tables.Courses.Attr.values(), Tables.Courses.Attr.COURSE_ID);
	}

	@Override
	public boolean validateEntityForInsert(Course course) {
		return course.getName() != null &&
		course.getDuration() != null &&
		course.getDescription() != null;
	}

	@Override
	public void fillPrepStatementWithResultSet(Course course, PreparedStatement ps, boolean skipNull)
			throws SQLException {
		PrepStHelper.fill(ps, skipNull, new Object[] {
			course.getId(),
			course.getName(),
			course.getDuration(),
			course.getDescription(),
			course.getCreationDateTime(),
			}		
		);
	}

	@Override
	public Course createEntityByResultSet(ResultSet rs) throws SQLException {
		return new Course(
			MySQLTypeConverter.toInternalInt(rs.getInt(Tables.Courses.Attr.COURSE_ID.getAttrName())),
			MySQLTypeConverter.toInternalString(rs.getString(Tables.Courses.Attr.NAME.getAttrName())),
			MySQLTypeConverter.toInternalString(rs.getString(Tables.Courses.Attr.DURATION.getAttrName())),
			MySQLTypeConverter.toInternalString(rs.getString(Tables.Courses.Attr.DESCRIPTION.getAttrName())),
			MySQLTypeConverter.toInternalDateTime(rs.getTimestamp(Tables.Courses.Attr.CREATION_TIMESTAMP.getAttrName()))
		);
	}

	@Override
	public boolean[] getNullAttributesStates(Course course) {
		return new boolean[] {
				course.getId() == null,
				course.getName() == null,
				course.getDuration() == null,
				course.getDescription() == null,
				course.getCreationDateTime() == null,
		};
	}
	

}
