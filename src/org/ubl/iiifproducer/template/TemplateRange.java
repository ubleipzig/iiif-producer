package org.ubl.iiifproducer.template;
import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class TemplateRange {
	@SerializedName("@id") public String id = "";
	@SerializedName("@type") public String type = "sc:Range";
	public String label = "unnamed range";
	public ArrayList<String> canvases;
	public ArrayList<String> ranges;
	
	public TemplateRange(String id, String type, String label) {
		this.id = id;
		this.type = type;
		this.label = label;
	}
	
	public void addCanvas(String canvas) {		
		this.canvases.add(canvas);
	}
	
	public void addRanges(String range) {		
		this.ranges.add(range);
	}
}
