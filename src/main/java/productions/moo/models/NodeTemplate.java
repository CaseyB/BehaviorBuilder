package productions.moo.models;

import com.google.gson.annotations.Expose;

import java.awt.Color;

public class NodeTemplate
{
	@Expose
	public String shortDisplay;

	@Expose
	public String fullDisplay;

	@Expose
	public String className;

	@Expose
	public Type type;

	@Expose
	public Color color;

	@Expose
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
