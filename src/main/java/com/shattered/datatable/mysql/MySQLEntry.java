package com.shattered.datatable.mysql;

import com.shattered.Build;
import org.database.query.command.impl.DeleteCommand;
import org.database.query.command.impl.InsertCommand;
import org.database.query.command.impl.SelectCommand;
import org.database.query.command.impl.UpdateCommand;
import org.database.query.options.impl.TableColumnValueOption;
import org.database.query.options.impl.WhereConditionOption;
import org.database.result.QueryResult;

import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author JTlr Frost | Mar 13, 2018 : 9:40:48 PM
 */
public interface MySQLEntry {

	/**
	 * Conditions.
	 */
	default WhereConditionOption[] getEntryConditions() {
		return null;
	}

	/**
	 * Conditions.
	 */
	default WhereConditionOption[] getDeleteConditions() {
		return null;
	}

	/**
	 * Inserts a new SQL Column
	 */
	default boolean insert() {
		return false;
	}

	/**
	 * Updates an existing SQL Column
	 */
	default boolean update() {
		return false;
	}

	/**
	 * Deletes an existing SQL Column
	 */
	default void delete() {

	}

	/**
	 * @param databaseName
	 * @param tableName
	 * @param values
	 * @param commandType
	 */
	default void entry(String databaseName, String tableName, List<MySQLColumn> values, MySQLCommand commandType) {

		QueryResult statement = null;

		switch (commandType) {

			case UPDATE: {

				/*
				 * Construct a select command for gathering player profile
				 */
				SelectCommand command = new SelectCommand(tableName);
				command.addOptions(getEntryConditions());

				/*
				 * Execute the constructed select command
				 */
				statement = getDatabaseInstance(databaseName).execute(databaseName, command);

				/*
				 * Check if a player record is found
				 */
				if (statement.getRowCount() > 0) {
					UpdateCommand update = new UpdateCommand(tableName);

					for (int i = 0; i < values.size(); i++) {
						update.addOption(new TableColumnValueOption(values.get(i).getName(), values.get(i).getValue()));
					}

					if (getEntryConditions() != null)
						update.addOptions(getEntryConditions());

					statement = getDatabaseInstance(databaseName).execute(databaseName, update);

					if (statement != null)
						statement.terminate();
				} else {
					InsertCommand insert = new InsertCommand(tableName);

					for (int i = 0; i < values.size(); i++) {
						insert.addOption(new TableColumnValueOption(values.get(i).getName(), values.get(i).getValue()));
					}

					statement = getDatabaseInstance(databaseName).execute(databaseName, insert);

					if (statement != null)
						statement.terminate();
				}

				break;
			}

			case INSERT: {
				InsertCommand insert = new InsertCommand(tableName);

				for (int i = 0; i < values.size(); i++) {
					insert.addOption(new TableColumnValueOption(values.get(i).getName(), values.get(i).getValue()));
				}

				statement = getDatabaseInstance(databaseName).execute(databaseName, insert);

				if (statement != null)
					statement.terminate();

				break;
			}

			case DELETE_FROM: {

				/*
				 * Construct a select command for gathering player profile
				 */
				SelectCommand deleteCommand = new SelectCommand(tableName);
				deleteCommand.addOptions(getDeleteConditions());

				/*
				 * Execute the constructed select command
				 */
				statement = getDatabaseInstance(databaseName).execute(databaseName, deleteCommand);

				/*
				 * Check if a player record is found
				 */
				if (statement.getRowCount() > 0) {
					DeleteCommand delete = new DeleteCommand(tableName);


					if (getDeleteConditions() != null)
						delete.addOptions(getDeleteConditions());

					statement = getDatabaseInstance(databaseName).execute(databaseName, delete);

					if (statement != null)
						statement.terminate();
				}
				break;
			}

			default:
				break;

		}
	}


	/**
	 * @param databaseName
	 * @param tableName
	 * @param values
	 * @param commandType
	 * @throws Throwable
	 */
	default void load(String databaseName, String tableName, List<MySQLColumn> values, MySQLCommand commandType) throws Throwable {

		switch (commandType) {

			case UPDATE:

				if (!getDatabaseInstance(databaseName).isConnected(databaseName)) {
					System.out.println("Unable to establish connection [" + databaseName + " -> " + tableName + "]");
					return;
				}
				SelectCommand select = new SelectCommand(tableName);

				if (getEntryConditions() != null)
					select.addOptions(getEntryConditions());

				QueryResult statement = getDatabaseInstance(databaseName).execute(databaseName, select);

				if (statement == null || statement.getRowCount() == 0)
					Logger.getGlobal().info("Unable to gather details from datatable.");
				else {

					ResultSet result = statement.getRow();

					for (MySQLColumn module : values) {

						switch (module.getColumnType()) {

							case INTEGER:
								module.parse("" + result.getInt(module.getName()));
								break;

							case VARCHAR:
								module.parse(result.getString(module.getName()));
								break;

							case DOUBLE:
								module.parse(result.getDouble(module.getName()));
								break;

							case SHORT:
								module.parse(result.getShort(module.getName()));
								break;

							case BOOLEAN:
								module.parse(result.getBoolean(module.getName()));
								break;
						}

					}
				}

				statement.terminate();

				break;

			default:
				break;
		}
	}

	/**
	 * Gets the Default Database Instance
	 * @return
	 */
	default MySQLManager getDatabaseInstance(String database) {
		if (database.contains("grizzly"))
			return Build.getGrizzlyDatabase();
		return Build.getShatteredDatabase();
	}

}
