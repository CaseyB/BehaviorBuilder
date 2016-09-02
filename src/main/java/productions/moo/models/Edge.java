package productions.moo.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

public class Edge
{
	private Node _node1;
	private Node _node2;

	public Edge (Node node1, Node node2)
	{
		_node1 = node1;
		_node2 = node2;
	}

	public void draw (Graphics2D graphics2D)
	{
		Point p1 = _node1.getPosition();
		Point p2 = _node2.getPosition();

		Stroke oldStroke = graphics2D.getStroke();
		graphics2D.setStroke(new BasicStroke(2));
		graphics2D.setColor(Color.darkGray);
		graphics2D.drawLine(p1.x, p1.y, p2.x, p2.y);
		graphics2D.setStroke(oldStroke);
	}
}