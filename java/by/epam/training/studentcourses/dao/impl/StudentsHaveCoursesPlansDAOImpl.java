package by.epam.training.studentcourses.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.training.studentcourses.dao.StudentsHaveCoursesPlansDAO;
import by.epam.training.studentcourses.dao.impl.dbmeta.MySQLTypeConverter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.StudentsHaveCoursesPlans;

public class StudentsHaveCoursesPlansDAOImpl extends EntityAbstractDAO<StudentsHaveCoursesPlans>
		implements StudentsHaveCoursesPlansDAO {

	public StudentsHaveCoursesPlansDAOImpl() {
		super(Tables.StudentsHaveCoursesPlans.TABLE_NAME, Tables.StudentsHaveCoursesPlans.Attr.values(), 
				Tables.StudentsHaveCoursesPlans.Attr.ID);
	}

	@Override
	public boolean validateEntityForInsert(StudentsHaveCoursesPlans entity) {
		return  entity.getStudentUserId() != null &&
				entity.getCoursePlanId() != null;
	}

	@Override
	public void fillPrepStatementWithResultSet(StudentsHaveCoursesPlans entity, PreparedStatement ps, boolean skipNull)
		throws SQLException {
		PrepStHelper.fill(ps, skipNull, new Object[] {
			entity.getId(),
			entity.getCoursePlanId(),
			entity.getStudentUserId(),
			entity.getMark(), 
			entity.getReview()
			}		
		);		
	}

	@Override
	public StudentsHaveCoursesPlans createEntityByResultSet(ResultSet rs) throws SQLException {
		return new StudentsHaveCoursesPlans(
			MySQLTypeConverter.toInternalInt(rs.getInt(Tables.StudentsHaveCoursesPlans.Attr.ID.getAttrName())), 
			MySQLTypeConverter.toInternalInt(rs.getInt(Tables.StudentsHaveCoursesPlans.Attr.STUDENT_USER_ID.getAttrName())),
			MySQLTypeConverter.toInternalInt(rs.getInt(Tables.StudentsHaveCoursesPlans.Attr.COURSE_PLAN_ID.getAttrName())),
			MySQLTypeConverter.toInternalInt(rs.getInt(Tables.StudentsHaveCoursesPlans.Attr.MARK.getAttrName())), 
			MySQLTypeConverter.toInternalString(rs.getString(Tables.StudentsHaveCoursesPlans.Attr.REVIEW.getAttrName())) 
			);
	}
	
	@Override
	public boolean[] getNullAttributesStates(StudentsHaveCoursesPlans entity) {
		return new boolean[] {
				entity.getId() == null,
				entity.getCoursePlanId() == null,
				entity.getStudentUserId() == null, 
				entity.getMark() == null,
				entity.getReview() == null,
		};
	}

	
}
