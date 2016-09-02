package productions.moo;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.ScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow
{
	private static Frame _mainFrame;
	private static NodeCanvas _canvas;
	private static NodeMenu _meunu;

	public static void main (String[] args)
	{
		_mainFrame = new Frame("Behavior Builder");
		_canvas = new NodeCanvas();
		_meunu = new NodeMenu();

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

		_mainFrame.setVisible(true);
	}
}
