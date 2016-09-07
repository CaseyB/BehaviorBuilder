package productions.moo.exporters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import productions.moo.models.Edge;
import productions.moo.models.Node;
import productions.moo.models.NodeTemplate;

public class BuilderSave
{
	public static void save (File file, List<NodeTemplate> allTemplates, List<Node> nodes, List<Edge> edges)
	{
		SaveState state = new SaveState(allTemplates, nodes, edges);
		Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.registerTypeAdapter(Node.class, new NodeSerializer())
			.registerTypeAdapter(Edge.class, new EdgeSerializer())
			.create();

		String templates = gson.toJson(state);

		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(templates);
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		System.out.println(templates);
	}

	private static class SaveState
	{
		@Expose
		public List<NodeTemplate> templates;

		@Expose
		public List<Node> nodes;

		@Expose
		public List<Edge> edges;

		public SaveState(List<NodeTemplate> templates, List<Node> nodes, List<Edge> edges)
		{
			this.templates = templates;
			this.nodes = nodes;
			this.edges = edges;
		}
	}
}
