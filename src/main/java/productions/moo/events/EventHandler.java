package productions.moo.events;

import com.google.common.eventbus.EventBus;

public class EventHandler
{
	private static EventHandler _instance;
	public static EventHandler getInstance()
	{
		if(_instance == null)
		{
			_instance = new EventHandler();
		}

		return _instance;
	}

	private EventBus _eventBus;

	private EventHandler()
	{
		_eventBus = new EventBus();
	}

	public void register(Object listener)
	{
		_eventBus.register(listener);
	}

	public void unregister(Object listener)
	{
		_eventBus.unregister(listener);
	}

	public void post(Object event)
	{
		_eventBus.post(event);
	}
}
