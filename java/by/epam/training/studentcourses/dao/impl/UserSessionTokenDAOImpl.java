package by.epam.training.studentcourses.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.training.studentcourses.dao.UserSessionTokenDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.dao.impl.dbmeta.MySQLTypeConverter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.SessionToken;

public class UserSessionTokenDAOImpl extends EntityAbstractDAO<SessionToken>
	implements UserSessionTokenDAO {

	public UserSessionTokenDAOImpl() {
		super(Tables.UserSessionToken.TABLE_NAME, 
				Tables.UserSessionToken.Attr.values(), Tables.UserSessionToken.Attr.USER_ID);
	}

	@Override
	public boolean validateEntityForInsert(SessionToken userSessionToken) {
		return userSessionToken.getUserId() != null &&
			   userSessionToken.getValue() != null;
	}

	@Override
	public void fillPrepStatementWithResultSet(SessionToken userSessionToken, 
			PreparedStatement ps, boolean skipNull) throws SQLException {
		PrepStHelper.fill(ps, skipNull, new Object[] {
				userSessionToken.getId(),
				userSessionToken.getUserId(),
				userSessionToken.getValue()
				}		
			);	
	}

	@Override
	public SessionToken createEntityByResultSet(ResultSet rs) throws SQLException, DAOException {
		return new SessionToken(
				MySQLTypeConverter.toInternalInt(
						rs.getInt(Tables.UserSessionToken.Attr.USER_ID.getAttrName())), 
				MySQLTypeConverter.toInternalString(
						rs.getString(Tables.UserSessionToken.Attr.SESSION_TOKEN.getAttrName()))
				);
	}

	@Override
	public boolean[] getNullAttributesStates(SessionToken userSessionToken) {
		return new boolean[] {
				userSessionToken.getId() == null,
				userSessionToken.getUserId() == null,
				userSessionToken.getValue() == null
		};			   
	}
	
}
