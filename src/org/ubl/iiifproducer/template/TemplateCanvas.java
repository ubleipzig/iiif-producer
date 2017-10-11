package org.ubl.iiifproducer.template;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TemplateCanvas {
	@SerializedName("@id") public String id = "";
	@SerializedName("@type") public String type = "sc:Canvas";
	public String label = "unnamed canvas";
	public String height = "";
	public String width = "";	
	public ArrayList<Object> images = new ArrayList<>();	
}


