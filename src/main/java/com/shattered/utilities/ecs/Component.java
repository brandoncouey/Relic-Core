package com.shattered.utilities.ecs;


import com.shattered.account.Account;
import com.shattered.datatable.mysql.MySQLEntry;
import com.shattered.datatable.mysql.MySQLFetch;
import lombok.Setter;

/**
 * @author JTlr Frost | Mar 7, 2018 : 8:28:14 PM
 */
public abstract class Component implements MySQLEntry, MySQLFetch {

	/**
	 * Represents the {@link Account} Object
	 */
	@Setter
	protected Object gameObject;


	/**
	 * Creates a new constructor setting the {@link Account}
	 * @param gameObject
	 */
	public Component(Object gameObject) {
		setGameObject(gameObject);
	}

	/**
	 * Initializes the content.
	 * Used for 'Pre-Loading' data from Storage
	 */
	public abstract void onStart();

	/**
	 * Used for using the data after storage load is finished.
	 */
	public abstract void onWorldAwake();

	/**
	 * Called once per world cycle per each instance.
	 */
	public abstract void onUpdate();

	/**
	 * Called once Actor is Finished
	 */
	public abstract void onFinish();

	/**
	 * Inserts all of the Information into the Database
	 */
	public boolean insert() {
		return false;
	}

	/**
	 * Updates all of the Information to the Database
	 */
	public boolean update() {
		return false;
	}

	/**
	 * Fetches the Information for the Component from the Database
	 */
	public boolean fetch() {
		return false;
	}

}
