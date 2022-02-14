package by.epam.training.studentcourses.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.training.studentcourses.dao.LessonDAO;
import by.epam.training.studentcourses.dao.impl.dbmeta.MySQLTypeConverter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.Lesson;

public class LessonDAOImpl extends EntityAbstractDAO<Lesson> implements LessonDAO {

	public LessonDAOImpl() {
		super(Tables.Lessons.TABLE_NAME, Tables.Lessons.Attr.values(), Tables.Lessons.Attr.ID);
	}

	@Override
	public boolean validateEntityForInsert(Lesson lesson) {
		return lesson.getCoursePlanId() != null &&
		lesson.getStartTime() != null &&
		lesson.getDuration() != null &&
		lesson.isCompleted() != null;
	}

	@Override
	public void fillPrepStatementWithResultSet(Lesson lesson, PreparedStatement ps, boolean skipNull)
			throws SQLException {
		PrepStHelper.fill(ps, skipNull, new Object[] {
			lesson.getId(),
			lesson.getCoursePlanId(),
			lesson.getStartTime(),
			lesson.getDuration(),
			lesson.getClassroomNumber(),
			lesson.isCompleted(),
			lesson.getRemarks()
			}		
		);
		
	}

	@Override
	public Lesson createEntityByResultSet(ResultSet rs) throws SQLException {
		Lesson lesson = new Lesson();
		lesson.setId(MySQLTypeConverter.toInternalInt(rs.getInt(Tables.Lessons.Attr.ID.getAttrName())));
		lesson.setCoursePlanId(
				MySQLTypeConverter.toInternalInt(rs.getInt(Tables.Lessons.Attr.COURSE_PLAN_ID.getAttrName())));
		lesson.setStartTime(
				MySQLTypeConverter.toInternalDateTime(rs.getTimestamp(Tables.Lessons.Attr.START_TIME.getAttrName())));
		lesson.setDuration(MySQLTypeConverter.toInternalInt(rs.getInt(Tables.Lessons.Attr.DURATION.getAttrName())));
		lesson.setClassroomNumber(
				MySQLTypeConverter.toInternalInt(rs.getInt(Tables.Lessons.Attr.CLASSROOM_NUMBER.getAttrName())));
		lesson.setCompleted(
				MySQLTypeConverter.toInternalBool(rs.getBoolean(Tables.Lessons.Attr.IS_COMPLETED.getAttrName())));
		lesson.setRemarks(
				MySQLTypeConverter.toInternalString(rs.getString(Tables.Lessons.Attr.REMARKS.getAttrName())));

		return lesson;
	}

	@Override
	public boolean[] getNullAttributesStates(Lesson lesson) {
		return new boolean[] {
			lesson.getId() == null,
			lesson.getCoursePlanId() == null,
			lesson.getStartTime() == null, 
			lesson.getDuration() == null,
			lesson.getClassroomNumber() == null,
			lesson.isCompleted() == null,
			lesson.getRemarks() == null,
		};
	}
	
}
