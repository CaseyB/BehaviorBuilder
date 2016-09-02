package productions.moo.events;

import java.awt.Component;
import java.awt.Point;

public class ShowPopupMenuEvent
{
	public Component parent;
	public Point position;

	public ShowPopupMenuEvent(Component parent, Point position)
	{
		this.parent = parent;
		this.position = position;
	}
}
