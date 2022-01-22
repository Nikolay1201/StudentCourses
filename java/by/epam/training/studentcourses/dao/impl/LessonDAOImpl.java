package by.epam.training.studentcourses.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.training.studentcourses.dao.EntityDAOFactory;
import by.epam.training.studentcourses.dao.LessonDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.dao.impl.dbmeta.MySQLTypeConverter;
import by.epam.training.studentcourses.util.Filter;
import by.epam.training.studentcourses.util.FiltrationType;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Lesson;

public class LessonDAOImpl extends EntityAbstractDAO<Lesson> implements LessonDAO {

	public LessonDAOImpl() {
		super(Tables.Lessons.TABLE_NAME, Tables.Lessons.Attr.values(), Tables.Lessons.Attr.LESSON_ID);
	}

	@Override
	public boolean validateEntityForInsert(Lesson lesson) {
		return lesson.getCoursePlan() != null &&
		lesson.getStartTime() != null &&
		lesson.getDuration() != null &&
		lesson.getClassroomNumber() != null &&
		lesson.isCompleted() != null;
	}

	@Override
	public void fillPrepStatementWithResultSet(Lesson lesson, PreparedStatement ps, boolean skipNull)
			throws SQLException {
		PrepStHelper.fill(ps, skipNull, new Object[] {
			lesson.getId(),
			lesson.getCoursePlan().getId(),
			lesson.getStartTime(),
			lesson.getDuration(),
			lesson.getClassroomNumber(),
			lesson.isCompleted(),
			}		
		);
		
	}

	@Override
	public Lesson createEntityByResultSet(ResultSet rs) throws SQLException, DAOException {
		Filter coursePlanFilter = new Filter();
		coursePlanFilter.addCondition(
				FiltrationType.EQUALS, Tables.CoursesPlans.Attr.COURSE_PLAN_ID.getAttrName(), 
				Integer.toString(MySQLTypeConverter.toInternalInt(
						rs.getInt(Tables.Lessons.Attr.COURSE_PLAN_ID.getAttrName()))));
		return new Lesson(
			MySQLTypeConverter.toInternalInt(rs.getInt(Tables.Lessons.Attr.LESSON_ID.getAttrName())),
			EntityDAOFactory.getCoursePlanDAO().getByFilter(coursePlanFilter).get(0),  // !
			MySQLTypeConverter.toInternalDateTime(rs.getTimestamp(Tables.Lessons.Attr.START_TIME.getAttrName())),
			MySQLTypeConverter.toInternalInt(rs.getInt(Tables.Lessons.Attr.DURATION.getAttrName())),
			MySQLTypeConverter.toInternalInt(rs.getInt(Tables.Lessons.Attr.CLASSROOM_NUMBER.getAttrName())),
			MySQLTypeConverter.toInternalBool(rs.getBoolean(Tables.Lessons.Attr.IS_COMPLETED.getAttrName()))					
		);
	}

	@Override
	public boolean[] getNullAttributesStates(Lesson lesson) {
		return new boolean[] {
			lesson.getId() == null,
			lesson.getCoursePlan().getId() == null,
			lesson.getStartTime() == null, 
			lesson.getDuration() == null,
			lesson.getClassroomNumber() == null,
			lesson.isCompleted() == null,
		};
	}
	
}
