package productions.moo.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;

public class Node
{
	private static final int RADIUS = 25;
	private static final Font FONT = new Font("Arial Black", Font.BOLD, 20);

	private Point _position;
	private NodeTemplate _template;
	private boolean _selected = false;
	private Rectangle _bounds = new Rectangle();

	public Node (Point position, NodeTemplate template)
	{
		_position = position;
		_template = template;
		updateBounds();
	}

	public Point getPosition ()
	{
		return _position;
	}

	public void updatePosition (Point delta)
	{
		_position.x += delta.x;
		_position.y += delta.y;
		updateBounds();
	}

	public Rectangle getBounds ()
	{
		return _bounds;
	}

	public void setBounds (Rectangle bounds)
	{
		_bounds = bounds;
	}

	private void updateBounds ()
	{
		_bounds.setBounds(_position.x - RADIUS, _position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
	}

	public boolean contains (Point point)
	{
		return _bounds.contains(point);
	}

	public boolean isSelected ()
	{
		return _selected;
	}

	public void setSelected (boolean selected)
	{
		_selected = selected;
	}

	public NodeTemplate getTemplate()
	{
		return _template;
	}

	public void draw (Graphics2D graphics2D)
	{
		graphics2D.setColor(_template.color);
		switch (_template.shape)
		{
			case SQUARE:
				graphics2D.fillRect(_bounds.x, _bounds.y, _bounds.width, _bounds.height);
				break;
			case CIRCLE:
				graphics2D.fillOval(_bounds.x, _bounds.y, _bounds.width, _bounds.height);
				break;
			case DIAMOND:
				graphics2D.fillRoundRect(_bounds.x, _bounds.y, _bounds.width, _bounds.height, RADIUS, RADIUS);
				break;
		}

		graphics2D.setFont(FONT);
		graphics2D.setColor(Color.darkGray);
		FontMetrics metrics = graphics2D.getFontMetrics(FONT);

		int fontBottom = (int)(_bounds.getCenterY() + metrics.getHeight() / 4f);
		int fontLeft = (int)(_bounds.getCenterX() - metrics.stringWidth(_template.shortDisplay) / 2f);
		graphics2D.drawString(_template.shortDisplay, fontLeft, fontBottom);

		if (_selected)
		{
			Stroke oldStroke = graphics2D.getStroke();
			graphics2D.setStroke(new BasicStroke(2));
			graphics2D.setColor(Color.darkGray);
			switch (_template.shape)
			{
				case SQUARE:
					graphics2D.drawRect(_bounds.x, _bounds.y, _bounds.width, _bounds.height);
					break;
				case CIRCLE:
					graphics2D.drawOval(_bounds.x, _bounds.y, _bounds.width, _bounds.height);
					break;
				case DIAMOND:
					graphics2D.drawRoundRect(_bounds.x, _bounds.y, _bounds.width, _bounds.height, RADIUS, RADIUS);
					break;
			}
			graphics2D.setStroke(oldStroke);
		}
	}
}