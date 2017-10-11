package org.ubl.iiifproducer.template;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TemplateBody {
		/*public String attribution = "Provided by Leipzig University";
		Object structures;
		ArrayList<Object> sequences = new ArrayList<>();
		ArrayList<Object> metadata = new ArrayList<>();
		public String manifest = "";
		public String related = "";		
		public String label = "unnamed";
		public String logo = "http://iiif.ub.uni-leipzig.de/ubl-logo.png";
		@SerializedName("@id") public String id = "";
		@SerializedName("@type") public String type = "sc:Manifest";*/
		
		
		@SerializedName("@context") public String context = "";
		@SerializedName("@id") public String id = "";
		@SerializedName("@type") public String type = "sc:Manifest";
		//public String description = "unnamed";
		public String label = "unnamed";
		public String attribution = "Provided by Leipzig University";
		public String logo = "http://iiif.ub.uni-leipzig.de/ubl-logo.png";
		public ArrayList<Object> related = new ArrayList<>();
		public ArrayList<Object> metadata = new ArrayList<>();
		public ArrayList<Object> sequences = new ArrayList<>();
		//ArrayList<Object> structures = new ArrayList<>();
		public Object structures;
}

