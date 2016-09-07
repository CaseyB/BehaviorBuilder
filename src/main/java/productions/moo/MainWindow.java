package productions.moo;

import com.google.common.eventbus.Subscribe;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.ScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;

import productions.moo.events.AddNodeEvent;
import productions.moo.events.EventHandler;

public class MainWindow
{
	private Frame _mainFrame;
	private NodeCanvas _canvas;
	private NodeMenu _meunu;
	private AddCustomNodeDialog _customNodeDialog;

	private EventHandler _eventBus;

	private File _saveFile;

	public MainWindow()
	{
		_mainFrame = new Frame("Behavior Builder");
		_canvas = new NodeCanvas();
		_meunu = new NodeMenu(this);

		_canvas.add(_meunu.getPopUpMenu());

		ScrollPane scroller = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		scroller.add(_canvas);

		_mainFrame.setSize(400, 400);
		_mainFrame.setMenuBar(_meunu);
		_mainFrame.add(scroller, BorderLayout.CENTER);
		_mainFrame.pack();
		_mainFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing (WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});

		_customNodeDialog = new AddCustomNodeDialog(_mainFrame);
		_eventBus = EventHandler.getInstance();
		_eventBus.register(this);

		_mainFrame.setVisible(true);
	}

	public File getSaveFile()
	{
		return _saveFile;
	}

	public File createSaveFile()
	{
		FileDialog dialog = new FileDialog(_mainFrame, "Choose a file", FileDialog.SAVE);
		dialog.setFile("*.json");
		dialog.setVisible(true);
		String filename = dialog.getFile();

		if(filename != null)
		{
			if(!filename.endsWith(".json"))
			{
				filename += ".json";
			}

			_saveFile = new File(dialog.getDirectory(), filename);
		}

		return _saveFile;
	}

	@Subscribe
	public void showCustomNodeDialog(AddNodeEvent event)
	{
		_customNodeDialog.setVisible(true);
	}
}
