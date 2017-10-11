package org.ubl.iiifproducer.template;
import com.google.gson.annotations.SerializedName;
//import java.util.ArrayList;

public class TemplateResource {
	@SerializedName("@id") public String id = "";
	@SerializedName("@type") public String type = "dctypes:Image";
	public String label = "unnamed resource";
	public String format = "";
	public String height = "";
	public String width = "";	
	public Object service;
}
