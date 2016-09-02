package productions.moo.models;

import java.awt.Color;

public class NodeTemplate
{
	public String shortDisplay;
	public String fullDisplay;
	public String className;

	public Type type;
	public Color color;
	public Shape shape;

	public enum Shape
	{
		SQUARE,
		CIRCLE,
		DIAMOND
	}

	public enum Type
	{
		COMPOSITE,
		DECORATOR,
		LEAF
	}

	public NodeTemplate (String shortDisplay, String fullDisplay, String className, Type type, Color color, Shape shape)
	{
		this.shortDisplay = shortDisplay;
		this.fullDisplay = fullDisplay;
		this.className = className;
		this.type = type;
		this.color = color;
		this.shape = shape;
	}
}
