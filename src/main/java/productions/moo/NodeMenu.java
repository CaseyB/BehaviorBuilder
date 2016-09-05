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

import productions.moo.events.AddNodeEvent;
import productions.moo.events.ConnectEvent;
import productions.moo.events.EnableConnectMenuEvent;
import productions.moo.events.EventHandler;
import productions.moo.events.NewNodeEvent;
import productions.moo.events.NodeAddedEvent;
import productions.moo.events.SaveStateEvent;
import productions.moo.events.ShowPopupMenuEvent;
import productions.moo.exporters.BuilderSave;
import productions.moo.models.NodeTemplate;

public class NodeMenu extends MenuBar
{
	private EventHandler _eventBus;

	private MenuItemListener _menuListener;
	private PopUpItemListener _popupListener;

	private PopupMenu _popup;
	private Point _popupPosition;

	private Menu _nodeMenu;
	private MenuItem _saveMenu;
	private MenuItem _connectMenu;

	private List<NodeTemplate> _templates;

	public NodeMenu ()
	{
		_eventBus = EventHandler.getInstance();
		_eventBus.register(this);

		buildAppMenu();
		buildPopUpMenu();
	}

	public PopupMenu getPopUpMenu ()
	{
		return _popup;
	}

	private void buildAppMenu()
	{
		_menuListener = new MenuItemListener();

		Menu fileMenu = new Menu("File");

		_saveMenu = new MenuItem("Save");
		_saveMenu.setActionCommand("Save");
		_saveMenu.addActionListener(_menuListener);
		fileMenu.add(_saveMenu);

		MenuItem saveAs = new MenuItem("Save as...");
		saveAs.setActionCommand("SaveAs");
		saveAs.addActionListener(_menuListener);
		fileMenu.add(saveAs);

		fileMenu.addSeparator();

		MenuItem addCustomNode = new MenuItem("Add Custom Node...");
		addCustomNode.setActionCommand("CustomNode");
		addCustomNode.addActionListener(_menuListener);
		fileMenu.add(addCustomNode);

		add(fileMenu);
	}

	private void buildPopUpMenu()
	{
		_popupListener = new PopUpItemListener();

		_nodeMenu = new Menu("New");
		_nodeMenu.setActionCommand("New");
		_nodeMenu.addActionListener(_popupListener);

		_templates = NodeTypeManager.getInstance().getNodeTypes();
		for (NodeTemplate template : _templates)
		{
			MenuItem nodeItem = new MenuItem(template.fullDisplay);
			nodeItem.setActionCommand(template.fullDisplay);
			nodeItem.addActionListener(_popupListener);
			_nodeMenu.add(nodeItem);
		}

		_popup = new PopupMenu();
		_popup.add(_nodeMenu);

		_connectMenu = new MenuItem("Connect");
		_connectMenu.addActionListener(_menuListener);
		_connectMenu.setEnabled(false);
		_popup.add(_connectMenu);
	}

	@Subscribe
	public void addNode(NodeAddedEvent event)
	{
		_templates.add(event.template);

		MenuItem nodeItem = new MenuItem(event.template.fullDisplay);
		nodeItem.setActionCommand(event.template.fullDisplay);
		nodeItem.addActionListener(_popupListener);
		_nodeMenu.add(nodeItem);
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
			String actionCommand = e.getActionCommand();

			if(actionCommand.equalsIgnoreCase("Save"))
			{
				_eventBus.post(new SaveStateEvent());
			}
			else if(actionCommand.equalsIgnoreCase("Connect"))
			{
				_eventBus.post(new ConnectEvent());
			}
			else if(actionCommand.equalsIgnoreCase("CustomNode"))
			{
				_eventBus.post(new AddNodeEvent());
			}
		}
	}

	private class PopUpItemListener implements ActionListener
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