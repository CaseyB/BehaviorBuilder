package productions.moo.exporters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;

import java.awt.Point;
import java.lang.reflect.Type;

import productions.moo.models.Node;

public class NodeSerializer implements JsonSerializer<Node>, JsonDeserializer<Node>
{
	public JsonElement serialize (Node src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject object = new JsonObject();
		object.addProperty("uuid", src.getUUID());
		object.add("position", context.serialize(new PointSerial(src.getPosition())));
		object.addProperty("template", src.getTemplate().className);

		return object;
	}

	public Node deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		return null;
	}

	private class PointSerial
	{
		@Expose
		int x;

		@Expose
		int y;

		public PointSerial(Point point)
		{
			x = point.x;
			y = point.y;
		}
	}
}
