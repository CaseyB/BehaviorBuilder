package productions.moo;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;

import productions.moo.events.EventHandler;
import productions.moo.events.NodeAddedEvent;
import productions.moo.models.NodeTemplate;

public class AddCustomNodeDialog extends Dialog implements ActionListener
{
	private TextField _shortDisplay;
	private TextField _fullDisplay;
	private TextField _className;
	private JColorChooser _colorChooser;
	private Button _ok;
	private Button _cancel;

	private EventHandler _eventBus;

	public AddCustomNodeDialog (Frame owner)
	{
		super(owner);
		setTitle("Add Custom Node");
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing (WindowEvent e)
			{
				setVisible(false);
			}
		});

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		_shortDisplay = new TextField();
		_fullDisplay = new TextField();
		_className = new TextField();

		_colorChooser = new JColorChooser();

		_ok = new Button("Ok");
		_ok.setActionCommand("Ok");
		_ok.addActionListener(this);

		_cancel = new Button("Cancel");
		_cancel.setActionCommand("Cancel");
		_cancel.addActionListener(this);

		add(_shortDisplay);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(_fullDisplay);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(_className);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(_colorChooser);

		add(Box.createRigidArea(new Dimension(0, 5)));
		add(_ok);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(_cancel);

		pack();

		_eventBus = EventHandler.getInstance();
	}

	public void actionPerformed (ActionEvent e)
	{
		if (e.getActionCommand().equalsIgnoreCase("ok"))
		{
			NodeTemplate template = new NodeTemplate(
				_shortDisplay.getText(),
				_fullDisplay.getText(),
				_className.getText(),
				NodeTemplate.Type.LEAF,
				_colorChooser.getColor(),
				NodeTemplate.Shape.CIRCLE
			);

			NodeTypeManager.getInstance().addNodeTemplate(template);
			_eventBus.post(new NodeAddedEvent(template));
		}

		setVisible(false);
	}
}
