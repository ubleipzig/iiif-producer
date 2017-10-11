package org.ubl.iiifproducer.template;
import com.google.gson.annotations.SerializedName;

public class TemplateService {
	@SerializedName("@context") public String context = "http://iiif.io/api/image/2/context.json";
	@SerializedName("@id") public String id = "unnamed service";
	public String profile = "http://iiif.io/api/image/2/level1.json";	
}
