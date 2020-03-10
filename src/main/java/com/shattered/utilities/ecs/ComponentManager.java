package com.shattered.utilities.ecs;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author JTlr Frost | Jan 10, 2018 : 7:41:05 PM
 */
@Data
public abstract class ComponentManager {

	/**
	 * Represents the {@link Object} for this {@link ComponentManager}.
	 */
	protected Object object;

	/**
	 * Represents the List of {@link Components}
	 */
	protected Map<Components<?>, Component> components;

	/**
	 * @param object
	 */
	public ComponentManager(Object object) {
		setObject(object);
		setComponents(new ConcurrentHashMap<>());
	}


	/**
	 * Initializes the channelComponents.
	 */
	public void onStart() {
		for (Component components : getComponents().values()) {
			if (components == null) continue;
			components.onStart();
		}
	}

	/**
	 * Represents the 'On ChannelLine Awake' for 'Player Components'
	 */
	public void onWorldAwake() {
		for (Component components : getComponents().values()) {
			if (components == null) continue;
			components.onWorldAwake();
		}
	}

	/**
	 * Each is called once per world cycle
	 */
	public void onUpdate() {
		for (Component components : getComponents().values()) {
			if (components == null || !components.getClass().isAnnotationPresent(ProcessComponent.class) || !components.getClass().isAssignableFrom(PriorityComponent.class)) continue;
			components.onUpdate();
		}

		for (Component components : getComponents().values()) {
			if (components == null || !components.getClass().isAnnotationPresent(ProcessComponent.class)
			|| components.getClass().isAssignableFrom(PriorityComponent.class)) continue;
			components.onUpdate();
		}
	}

	/**
	 * Calls any Finishing Methods for {@link Component}
	 */
	public void onFinish() {
		for (Component component : getComponents().values()) {
			if (component == null) continue;
			component.onFinish();
		}
	}

	/**
	 * Inserts all Information for All Components to Database
	 */
	public void onInsertData() {
		for (Component component : getComponents().values()) {
			component.insert();
		}
	}

	/**
	 * Updates all Information for All Components to Database
	 */
	public void onUpdateData() {
		for (Component component : getComponents().values()) {
			component.update();
		}
	}

	/**
	 * Fetches Database Information for All Components from Database
	 */
	public void onFetchData() {
		for (Component component : getComponents().values()) {
			component.fetch();
		}
	}


	/**
	 * @param components
	 * @param component
	 * @return added
	 */
	public boolean attatch(Components<?> components, Component component) {
		getComponents().put(components, component);
		return true;
	}

	/**
	 * Gets a piece of Component
	 * @param components
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> T get(Components<T> components) {
		return (T) getComponents().get(components);
	}



}
