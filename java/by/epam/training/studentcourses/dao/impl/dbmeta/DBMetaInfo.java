package by.epam.training.studentcourses.dao.impl.dbmeta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import by.epam.training.studentcourses.dao.exception.DAOException;
import by.epam.training.studentcourses.dao.exception.InternalException;
import by.epam.training.studentcourses.dao.impl.pool.ConnectionPool;

public class DBMetaInfo {

	private final Map<String, TableInfo> tablesInfoMap = new HashMap<>();
	
	public Map<String, TableInfo> getTablesInfo() {
		return tablesInfoMap;
	}
		
	public DBMetaInfo(ConnectionPool connPool) throws DAOException {
		Connection conn = null;
		ResultSet tablesMetainfoRS = null;
		ResultSet columnsRS = null;
		ResultSet primaryKeysRS = null;
		try {
			try {
				conn = connPool.getConnection();
				DatabaseMetaData dbMeta = conn.getMetaData();
				tablesMetainfoRS = dbMeta.getTables(null, null, "%", new String[]{"TABLE"});
				while (tablesMetainfoRS.next()) {
					if (tablesMetainfoRS.getString("TABLE_NAME").equals("sys_config")) continue; // CRUTCH!
					TableInfo tableInfo = new TableInfo(tablesMetainfoRS.getString("TABLE_NAME"));
					Map<String, AttrInfo> attrMap = new HashMap<>();
					columnsRS = dbMeta.getColumns(null, null, tableInfo.getName(), null);
					while (columnsRS.next()) {
						Class<?> dataType = MySQLTypeConverter.mySQLDataTypeToInternalDataType(columnsRS.getInt("DATA_TYPE"));
						String attrName = columnsRS.getString("COLUMN_NAME");
						boolean isEditable = false;
						switch (columnsRS.getString("IS_AUTOINCREMENT")) {
						case "YES":
							isEditable = false;
							break;
						case "NO":
							isEditable = true;
							break;
						default:
							throw new SQLException();
						}
						AttrInfo attrInfo = new AttrInfo(attrName, dataType);
						attrInfo.setEditable(isEditable);
						attrMap.put(attrName, attrInfo);
					}
					primaryKeysRS = dbMeta.getPrimaryKeys(null, null, tableInfo.getName());
					while (primaryKeysRS.next()) {
						String pkAttrName = primaryKeysRS.getString("COLUMN_NAME");
						attrMap.get(pkAttrName).setPK(true);
					}
					tableInfo.addAttrInfo(new ArrayList<>(attrMap.values()));
					tablesInfoMap.put(tableInfo.getName(), tableInfo);
					
				}
			} finally {
				if (tablesMetainfoRS != null) {
					tablesMetainfoRS.close();
				}
				if (columnsRS != null) {
					columnsRS.close();
				}
				if (primaryKeysRS != null) {
					primaryKeysRS.close();
				}
				if (conn != null) {
					connPool.releaseConnection(conn);
				}
			}
		 } catch (SQLException e) {
			throw new InternalException(e);
		 }
	}

}
