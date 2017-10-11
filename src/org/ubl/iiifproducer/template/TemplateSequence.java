package org.ubl.iiifproducer.template;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TemplateSequence {
	@SerializedName("@id") public String id = "";
	@SerializedName("@type") public String type = "sc:Sequence";
	public ArrayList<Object> canvases = new ArrayList<>();
}
