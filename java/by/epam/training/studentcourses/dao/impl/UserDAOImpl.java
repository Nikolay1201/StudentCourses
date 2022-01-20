package by.epam.training.studentcourses.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.training.studentcourses.dao.UserDAO;
import by.epam.training.studentcourses.dao.impl.dbmeta.MySQLTypeConverter;
import by.epam.training.studentcourses.util.constant.Tables;
import by.epam.training.studentcourses.util.entity.User;
import by.epam.training.studentcourses.util.entity.UserRole;

public class UserDAOImpl extends EntityAbstractDAO<User> implements UserDAO {

	public UserDAOImpl() {
		super(Tables.Users.tableName, Tables.Users.Attr.values(), Tables.Users.Attr.USER_ID);
	}

	@Override
	public boolean validateEntityForInsert(User user) {
		return user.getRole() != null &&
		user.getName() != null &&
		user.getSurename() != null &&
		user.getPatronymic() != null &&
		user.getBirthDate() != null &&
		user.getPhoneNumber() != null &&
		user.getLogin() != null &&
		user.getPassword() != null &&
		user.getEmali() != null;
	}

	@Override
	public void fillPrepStatementWithResultSet(User user, PreparedStatement ps, boolean skipNull)
		throws SQLException {
		PrepStHelper.fill(ps, skipNull, new Object[] {
			user.getId(),
			user.getRole().id(),
			user.getName(),
			user.getSurename(),
			user.getPatronymic(),
			user.getBirthDate(),
			user.getPhoneNumber(),
			user.getLogin(),
			user.getPassword(),
			user.getEmali(),
			user.getDesciption(),
			user.getRegistrationDateTime(), //to do something
			}		
		);		
	}

	@Override
	public User createEntityByResultSet(ResultSet rs) throws SQLException {
		return new User(
			MySQLTypeConverter.toInternalInt(rs.getInt(Tables.Users.Attr.USER_ID.getAttrName())), 
			UserRole.getById(MySQLTypeConverter.toInternalInt(rs.getInt(Tables.Users.Attr.ROLE_ID.getAttrName()))), 
			MySQLTypeConverter.toInternalString(rs.getString(Tables.Users.Attr.NAME.getAttrName())),
			MySQLTypeConverter.toInternalString(rs.getString(Tables.Users.Attr.SURENAME.getAttrName())), 
			MySQLTypeConverter.toInternalString(rs.getString(Tables.Users.Attr.PATRONYMIC.getAttrName())), 
			MySQLTypeConverter.toInternalDate(rs.getDate(Tables.Users.Attr.BIRTH_DATE.getAttrName())),
			MySQLTypeConverter.toInternalString(rs.getString(Tables.Users.Attr.PHONE_NUMBER.getAttrName())), 
			MySQLTypeConverter.toInternalString(rs.getString(Tables.Users.Attr.LOGIN.getAttrName())), 
			MySQLTypeConverter.toInternalString(rs.getString(Tables.Users.Attr.PASSWORD.getAttrName())), 
			MySQLTypeConverter.toInternalString(rs.getString(Tables.Users.Attr.EMAIL.getAttrName())), 
			MySQLTypeConverter.toInternalString(rs.getString(Tables.Users.Attr.DESCRIPTION.getAttrName())), 
			MySQLTypeConverter.toInternalDateTime(rs.getTimestamp(Tables.Users.Attr.REG_DATETIME.getAttrName()))
			);
	}
	
	@Override
	public boolean[] getNullAttributesStates(User user) {
		return new boolean[] {
				user.getId() == null,
				user.getRole() == null,
				user.getName() == null,
				user.getSurename() == null,
				user.getPatronymic() == null,
				user.getBirthDate() == null,
				user.getPhoneNumber() == null,
				user.getLogin() == null,
				user.getPassword() == null,
				user.getEmali() == null,
				user.getDesciption() == null,
				user.getRegistrationDateTime() == null,
		};
	}

}
