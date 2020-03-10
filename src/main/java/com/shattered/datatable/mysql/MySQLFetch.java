package com.shattered.datatable.mysql;

import com.shattered.Build;
import com.shattered.datatable.DatabaseService;
import com.shattered.system.SystemLogger;
import org.database.query.command.impl.SelectCommand;
import org.database.query.options.impl.WhereConditionOption;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author JTlr Frost | Mar 13, 2018 : 8:03:47 PM
 */
public interface MySQLFetch {

	/**
	 * Database name.
	 */
	default String getDatabaseName() {
		return "shatteredrelics";
	}

	/**
	 * Table name.
	 */
	default String getTableName() {
		return "";
	}

	/**
	 * Method used for Fetching SQL Tables
	 */
	default boolean fetch() {
		return false;
	}
	
	/**
	 * Conditions.
	 */
	default WhereConditionOption[] getFetchConditions() {
		return null;
	}

	/**
	 * Fetches all columns from the datatable.
	 */
	default ResultSet getResults() {
		ResultSet result = null;
		final MySQLManager database = getDatabaseInstance();
		if (!database.isConnected(getDatabaseName())) {
            SystemLogger.sendDatabaseErr(DatabaseService.MYSQL, "Could not `fetch` MySQL Results from " + getDatabaseName() + ", [Reason=MySQL connection is currently not established.]");
		    return null;
        }
		SelectCommand select = new SelectCommand(getTableName());
		if (getFetchConditions() != null)
			select.addOptions(getFetchConditions());
		result = database.execute(getDatabaseName(), select).getResultSet();
		try {
			result.last();
			result.getRow();
			result.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Checks if the Conditions has Results
	 * @return has results
	 */
	default boolean hasResults() {
		try {
			if (getResults() != null) {
				if (getResults().next())
					return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Gets the Default Database Instance
	 * @return
	 */
	default MySQLManager getDatabaseInstance() {
		if (getDatabaseName().contains("grizzly"))
			return Build.getGrizzlyDatabase();
		return Build.getShatteredDatabase();
	}

}
