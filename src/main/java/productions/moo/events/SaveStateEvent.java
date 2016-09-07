package productions.moo.events;

import java.io.File;

public class SaveStateEvent
{
	public File saveFile;

	public SaveStateEvent (File saveFile)
	{
		this.saveFile = saveFile;
	}
}
