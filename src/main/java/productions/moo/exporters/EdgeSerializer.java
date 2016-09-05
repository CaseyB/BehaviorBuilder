package productions.moo.exporters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import productions.moo.models.Edge;

public class EdgeSerializer implements JsonSerializer<Edge>
{
	public JsonElement serialize (Edge src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject object = new JsonObject();

		object.addProperty("node1", src.getNode1().getUUID());
		object.addProperty("node2", src.getNode2().getUUID());

		return object;
	}
}
