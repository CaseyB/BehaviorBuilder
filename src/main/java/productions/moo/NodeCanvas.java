package productions.moo;

import com.google.common.eventbus.Subscribe;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import productions.moo.events.ConnectEvent;
import productions.moo.events.EnableConnectMenuEvent;
import productions.moo.events.EventHandler;
import productions.moo.events.NewNodeEvent;
import productions.moo.events.ShowPopupMenuEvent;
import productions.moo.models.Edge;
import productions.moo.models.Node;

public class NodeCanvas extends Component
{
	private EventHandler _eventBus;
	private NodeTypeManager _typeManager;

	private boolean _selecting;
	private Node _hoverNode;
	private Rectangle _selectionRect = new Rectangle();
	private Point _mousePoint = new Point();

	private List<Node> _nodes = new ArrayList<Node>();
	private List<Edge> _edges = new ArrayList<Edge>();

	public NodeCanvas ()
	{
		_eventBus = EventHandler.getInstance();
		_eventBus.register(this);
		_typeManager = NodeTypeManager.getInstance();

		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseMotionHandler());

		_nodes.add(new Node(new Point(50, 50), _typeManager.getNodeTypes().get(0)));
		_nodes.add(new Node(new Point(105, 50), _typeManager.getNodeTypes().get(1)));
		_nodes.add(new Node(new Point(160, 50), _typeManager.getNodeTypes().get(2)));

		_nodes.add(new Node(new Point(50, 105), _typeManager.getNodeTypes().get(3)));
		_nodes.add(new Node(new Point(105, 105), _typeManager.getNodeTypes().get(4)));
		_nodes.add(new Node(new Point(160, 105), _typeManager.getNodeTypes().get(5)));
		_nodes.add(new Node(new Point(215, 105), _typeManager.getNodeTypes().get(6)));

		_edges.add(new Edge(_nodes.get(0), _nodes.get(1)));
		_edges.add(new Edge(_nodes.get(0), _nodes.get(3)));
	}

	@Subscribe
	public void addNode (NewNodeEvent event)
	{
		_nodes.add(new Node(event.position, event.template));
		repaint();
	}

	@Subscribe
	public void connectNodes(ConnectEvent event)
	{
		List<Node> selectedNodes = getSelectedNodes();
		for(Node node : selectedNodes)
		{
			_edges.add(new Edge(node, _hoverNode));
		}

		repaint();
	}

	@Override
	public void paint (Graphics graphics)
	{
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics2D.setColor(new Color(0x00f0f0f0));
		graphics2D.fillRect(0, 0, getWidth(), getHeight());

		for (Edge e : _edges)
		{
			e.draw(graphics2D);
		}

		for (Node n : _nodes)
		{
			n.draw(graphics2D);
		}

		if (_selecting)
		{
			graphics2D.setColor(Color.darkGray);
			graphics2D.drawRect(_selectionRect.x, _selectionRect.y,
				_selectionRect.width, _selectionRect.height);
		}
	}

	private Node getNode (Point position)
	{
		for (Node n : _nodes)
		{
			if (n.contains(position))
			{
				return n;
			}
		}

		return null;
	}

	private List<Node> getSelectedNodes ()
	{
		List<Node> selected = new ArrayList<Node>();

		for (Node node : _nodes)
		{
			if (node.isSelected())
			{
				selected.add(node);
			}
		}

		return selected;
	}

	private class MouseHandler extends MouseAdapter
	{
		@Override
		public void mouseReleased (MouseEvent e)
		{
			_selecting = false;
			_selectionRect.setBounds(0, 0, 0, 0);
			if (e.isPopupTrigger())
			{
				showPopup(e);
			}
			e.getComponent().repaint();
		}

		@Override
		public void mousePressed (MouseEvent e)
		{
			_mousePoint = e.getPoint();
			if (e.isShiftDown())
			{
				selectToggle(_nodes, _mousePoint);
			}
			else if (e.isPopupTrigger())
			{
				// If a node is already selected and it's not this one then
				// enable the connect menu
				_hoverNode = getNode(e.getPoint());
				List<Node> selectedNodes = getSelectedNodes();

				if (!selectedNodes.isEmpty() && !selectedNodes.contains(_hoverNode))
				{
					_eventBus.post(new EnableConnectMenuEvent(true));
				}
				else
				{
					_eventBus.post(new EnableConnectMenuEvent(false));
				}

				showPopup(e);
			}
			else if (selectOne(_nodes, _mousePoint))
			{
				_selecting = false;
			}
			else
			{
				selectNone(_nodes);
				_selecting = true;
			}
			e.getComponent().repaint();
		}

		private void showPopup (MouseEvent e)
		{
			_eventBus.post(new ShowPopupMenuEvent(e.getComponent(), e.getPoint()));
		}

		private void selectNone (List<Node> list)
		{
			for (Node n : list)
			{
				n.setSelected(false);
			}
		}

		private boolean selectOne (List<Node> list, Point p)
		{
			Node node = getNode(p);
			if (node != null)
			{
				if (!node.isSelected())
				{
					selectNone(list);
					node.setSelected(true);
				}
				return true;
			}
			return false;
		}

		private void selectToggle (List<Node> list, Point p)
		{
			for (Node n : list)
			{
				if (n.contains(p))
				{
					n.setSelected(!n.isSelected());
				}
			}
		}
	}

	private class MouseMotionHandler extends MouseMotionAdapter
	{
		Point _delta = new Point();

		@Override
		public void mouseDragged (MouseEvent e)
		{
			if (_selecting)
			{
				_selectionRect.setBounds(
					Math.min(_mousePoint.x, e.getX()),
					Math.min(_mousePoint.y, e.getY()),
					Math.abs(_mousePoint.x - e.getX()),
					Math.abs(_mousePoint.y - e.getY()));
				selectRect(_nodes, _selectionRect);
			}
			else
			{
				_delta.setLocation(
					e.getX() - _mousePoint.x,
					e.getY() - _mousePoint.y);
				updatePosition(_nodes, _delta);
				_mousePoint = e.getPoint();
			}
			e.getComponent().repaint();
		}

		private void selectRect (List<Node> list, Rectangle r)
		{
			for (Node n : list)
			{
				n.setSelected(r.contains(n.getPosition()));
			}
		}

		private void updatePosition (List<Node> list, Point delta)
		{
			for (Node n : list)
			{
				if (n.isSelected())
				{
					n.updatePosition(delta);
				}
			}
		}
	}
}
