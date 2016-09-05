package productions.moo.events;

import productions.moo.models.NodeTemplate;

public class NodeAddedEvent
{
	public NodeTemplate template;

	public NodeAddedEvent(NodeTemplate template)
	{
		this.template = template;
	}
}
