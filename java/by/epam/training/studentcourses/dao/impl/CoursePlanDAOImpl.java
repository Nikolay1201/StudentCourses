package by.epam.training.studentcourses.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.training.studentcourses.dao.CoursePlanDAO;
import by.epam.training.studentcourses.dao.impl.dbmeta.MySQLTypeConverter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.CoursePlan;
import by.epam.training.studentcourses.util.entity.CoursePlanStatus;

public class CoursePlanDAOImpl extends EntityAbstractDAO<CoursePlan> implements CoursePlanDAO {

	public CoursePlanDAOImpl() {
		super(Tables.CoursesPlans.TABLE_NAME, Tables.CoursesPlans.Attr.values(),
				Tables.CoursesPlans.Attr.ID);
	}

	@Override
	public boolean validateEntityForInsert(CoursePlan coursePlan) {
		return coursePlan.getCourseId() != null && coursePlan.getTrainerUserId() != null && coursePlan.getStatus() != null;
	}

	@Override
	public void fillPrepStatementWithResultSet(CoursePlan coursePlan, PreparedStatement ps, boolean skipNull)
			throws SQLException {
		PrepStHelper.fill(ps, skipNull, new Object[] { coursePlan.getId(), coursePlan.getCourseId(),
				coursePlan.getTrainerUserId(), coursePlan.getStatus().getValue(), coursePlan.getDescription() });
	}

//	@Override
//	public CoursePlan createEntityByResultSet(ResultSet rs) throws SQLException, DAOException {
//		Filter courseFilter = new Filter();
//		courseFilter.addCondition(FiltrationType.EQUALS, Tables.Courses.Attr.COURSE_ID.getAttrName(), 
//				Integer.toString(MySQLTypeConverter.toInternalInt(
//						rs.getInt(Tables.CoursesPlans.Attr.COURSE_ID.getAttrName()))));
//		Filter userFilter = new Filter();
//		userFilter.addCondition(FiltrationType.EQUALS, Tables.Users.Attr.USER_ID.getAttrName(), 
//				Integer.toString(MySQLTypeConverter.toInternalInt(
//						rs.getInt(Tables.Users.Attr.USER_ID.getAttrName()))));
//		return new CoursePlan(
//			MySQLTypeConverter.toInternalInt(
//					rs.getInt(Tables.CoursesPlans.Attr.COURSE_PLAN_ID.getAttrName())),
//			EntityDAOFactory.getCourseDAO().getByFilter(courseFilter).get(0),
//			EntityDAOFactory.getUserDAO().getByFilter(userFilter).get(0),
//			CoursePlanStatus.getByVal(MySQLTypeConverter.toInternalInt(
//					rs.getInt(Tables.CoursesPlans.Attr.STATUS_ID.getAttrName())).intValue()),
//			MySQLTypeConverter.toInternalString(rs.getString(Tables.CoursesPlans.Attr.DESCRIPTION.getAttrName()))
//		);
//	}

	@Override
	public CoursePlan createEntityByResultSet(ResultSet rs) throws SQLException {
		CoursePlan coursePlan = new CoursePlan();
		coursePlan.setId(
				MySQLTypeConverter.toInternalInt(rs.getInt(Tables.CoursesPlans.Attr.ID.getAttrName())));
		coursePlan.setCourseId(
				MySQLTypeConverter.toInternalInt(rs.getInt(Tables.CoursesPlans.Attr.COURSE_ID.getAttrName())));
		coursePlan.setTrainerId(
				MySQLTypeConverter.toInternalInt(rs.getInt(Tables.CoursesPlans.Attr.TRAINER_USER_ID.getAttrName())));
		coursePlan.setStatus(CoursePlanStatus.getByVal(
				MySQLTypeConverter.toInternalInt(rs.getInt(Tables.CoursesPlans.Attr.STATUS_ID.getAttrName()))));
		coursePlan.setDescription(
				MySQLTypeConverter.toInternalString(rs.getString(Tables.CoursesPlans.Attr.DESCRIPTION.getAttrName())));
		return coursePlan;
	}

	@Override
	public boolean[] getNullAttributesStates(CoursePlan coursePlan) {
		return new boolean[] { coursePlan.getId() == null, coursePlan.getCourseId() == null,
				coursePlan.getTrainerUserId() == null, coursePlan.getStatus() == null, coursePlan.getDescription() == null, };
	}

}
