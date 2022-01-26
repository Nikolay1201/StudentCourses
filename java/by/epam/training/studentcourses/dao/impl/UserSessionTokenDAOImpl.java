package by.epam.training.studentcourses.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.training.studentcourses.dao.UserSessionTokenDAO;
import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.dao.impl.dbmeta.MySQLTypeConverter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.UserSessionToken;

public class UserSessionTokenDAOImpl extends EntityAbstractDAO<UserSessionToken>
	implements UserSessionTokenDAO {

	public UserSessionTokenDAOImpl() {
		super(Tables.UserSessionToken.TABLE_NAME, 
				Tables.UserSessionToken.Attr.values(), Tables.UserSessionToken.Attr.USER_ID);
	}

	@Override
	public boolean validateEntityForInsert(UserSessionToken userSessionToken) {
		return userSessionToken.getUserId() != null &&
			   userSessionToken.getSessionToken() != null;
	}

	@Override
	public void fillPrepStatementWithResultSet(UserSessionToken userSessionToken, 
			PreparedStatement ps, boolean skipNull) throws SQLException {
		PrepStHelper.fill(ps, skipNull, new Object[] {
				userSessionToken.getId(),
				userSessionToken.getUserId(),
				userSessionToken.getSessionToken()
				}		
			);	
	}

	@Override
	public UserSessionToken createEntityByResultSet(ResultSet rs) throws SQLException, DAOException {
		return new UserSessionToken(
				MySQLTypeConverter.toInternalInt(
						rs.getInt(Tables.UserSessionToken.Attr.USER_ID.getAttrName())), 
				MySQLTypeConverter.toInternalString(
						rs.getString(Tables.UserSessionToken.Attr.SESSION_TOKEN.getAttrName()))
				);
	}

	@Override
	public boolean[] getNullAttributesStates(UserSessionToken userSessionToken) {
		return new boolean[] {
				userSessionToken.getId() == null,
				userSessionToken.getUserId() == null,
				userSessionToken.getSessionToken() == null
		};			   
	}
	
}
