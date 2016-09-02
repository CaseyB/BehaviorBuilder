package productions.moo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import productions.moo.models.NodeTemplate;

public class NodeTypeManager
{
	private static NodeTypeManager _instance = new NodeTypeManager();

	public static NodeTypeManager getInstance ()
	{
		return _instance;
	}

	private List<NodeTemplate> _templates = new ArrayList<NodeTemplate>();

	private NodeTypeManager ()
	{
		// Add default node templates
		_templates.add(new NodeTemplate("~", "Sequence", "SequenceNode", NodeTemplate.Type.COMPOSITE, new Color(0x19F0EE), NodeTemplate.Shape.SQUARE));
		_templates.add(new NodeTemplate("?", "Selector", "SelectorNode", NodeTemplate.Type.COMPOSITE, new Color(0xE202F0), NodeTemplate.Shape.SQUARE));
		_templates.add(new NodeTemplate("||", "Parallel", "ParallelNode", NodeTemplate.Type.COMPOSITE, new Color(0xF08D01), NodeTemplate.Shape.SQUARE));

		_templates.add(new NodeTemplate("!", "Inverter", "InverterNode", NodeTemplate.Type.DECORATOR, new Color(0xF0EF38), NodeTemplate.Shape.DIAMOND));
		_templates.add(new NodeTemplate("↻", "Repeater", "RepaterNode", NodeTemplate.Type.DECORATOR, new Color(0x5C52F0), NodeTemplate.Shape.DIAMOND));
		_templates.add(new NodeTemplate("*", "RepeatUntilFailure", "RepeatUntilFailureNode", NodeTemplate.Type.DECORATOR, new Color(0xF04051), NodeTemplate.Shape.DIAMOND));
		_templates.add(new NodeTemplate("!", "Succeeder", "SucceederNode", NodeTemplate.Type.DECORATOR, new Color(0x45F02B), NodeTemplate.Shape.DIAMOND));
	}

	public List<NodeTemplate> getNodeTypes()
	{
		return _templates;
	}

	public void addNodeTemplate(final NodeTemplate template)
	{
		_templates.add(template);
	}
}
