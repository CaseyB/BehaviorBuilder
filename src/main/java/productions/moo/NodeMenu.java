package productions.moo;

import com.google.common.eventbus.Subscribe;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import productions.moo.events.ConnectEvent;
import productions.moo.events.EnableConnectMenuEvent;
import productions.moo.events.EventHandler;
import productions.moo.events.NewNodeEvent;
import productions.moo.events.ShowPopupMenuEvent;
import productions.moo.models.NodeTemplate;

public class NodeMenu extends MenuBar
{
	private EventHandler _eventBus;
	private PopupMenu _popup;
	private Point _popupPosition;

	private MenuItem _connectMenu;

	private List<NodeTemplate> _templates;

	public NodeMenu ()
	{
		_eventBus = EventHandler.getInstance();
		_eventBus.register(this);

		MenuItemListener menuItemListener = new MenuItemListener();

		Menu fileMenu = new Menu("File");

		Menu newMenu = new Menu("New");
		newMenu.setActionCommand("New");
		newMenu.addActionListener(menuItemListener);

		NodeMenuListener nodeListener = new NodeMenuListener();
		_templates = NodeTypeManager.getInstance().getNodeTypes();
		for (NodeTemplate template : _templates)
		{
			MenuItem nodeItem = new MenuItem(template.fullDisplay);
			nodeItem.setActionCommand(template.fullDisplay);
			nodeItem.addActionListener(nodeListener);
			newMenu.add(nodeItem);
		}

		add(fileMenu);

		_popup = new PopupMenu();
		_popup.add(newMenu);

		_connectMenu = new MenuItem("Connect");
		_connectMenu.addActionListener(menuItemListener);
		_connectMenu.setEnabled(false);
		_popup.add(_connectMenu);
	}

	public PopupMenu getPopUpMenu ()
	{
		return _popup;
	}

	@Subscribe
	public void showPopup (final ShowPopupMenuEvent event)
	{
		_popup.show(event.parent, event.position.x, event.position.y);
		_popupPosition = event.position;
	}

	@Subscribe
	public void enableConnectMenu(EnableConnectMenuEvent event)
	{
		_connectMenu.setEnabled(event.enable);
	}

	private class MenuItemListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			if(e.getActionCommand().equalsIgnoreCase("Connect"))
			{
				_eventBus.post(new ConnectEvent());
			}
		}
	}

	private class NodeMenuListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			String nodeName = e.getActionCommand();
			for (NodeTemplate template : _templates)
			{
				if (template.fullDisplay.equals(nodeName))
				{
					_eventBus.post(new NewNodeEvent(template, _popupPosition));
					break;
				}
			}
		}
	}
}