package productions.moo.events;

import java.awt.Point;

import productions.moo.models.NodeTemplate;

public class NewNodeEvent
{
	public NodeTemplate template;
	public Point position;

	public NewNodeEvent(NodeTemplate template, Point position)
	{
		this.template = template;
		this.position = position;
	}
}
