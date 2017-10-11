/**
* IIIFProducer
*
* Copyright (C) 2017 Leipzig University Library <info@ub.uni-leipzig.de>
*
* @author Leander Seige <seige@ub.uni-leipzig.de>
* @author Stefan Freitag <freitag@uni-leipzig.de>
* @author Felix Kreißig <kreissig@ub.uni-leipzig.de>
* @license http://opensource.org/licenses/gpl-2.0.php GNU GPLv2 *
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License version 2,
* as published by the Free Software Foundation.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA *
*/

package org.ubl.iiifproducer.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.ubl.iiifproducer.template.TemplateRange;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TOC {
	
	//private static Logger logger = Logger.getLogger(TOC.class);
	
	String id, type, label;
	GsonBuilder gbuilder;
	Gson gson;
	ArrayList<String> range = new ArrayList<String>();
	ArrayList<String> canvas = new ArrayList<String>();	
	ArrayList<String> jsonparts = new ArrayList<String>();
	ArrayList<TOC> tocsparts = new ArrayList<TOC>();
	HashMap<String, ArrayList<String>> structLink = new HashMap<String, ArrayList<String>>();
	
	
	/**
	 * table of content class
	 * reproduce the structure
	 * look at the manifest API: http://iiif.io/api/presentation/2.1/#range
	 * 
	 * @param id 			URI of the range
	 * @param type 			range type
	 * @param label 		label of the book paragraph
	 * @param structLink 	illustrate the contained ranges and canvases
	 */
	public TOC(String id, String type, String label, HashMap<String, ArrayList<String>> structLink)
	{
		this.id = id;
		this.type = type;
		this.label = label;
		this.structLink = structLink;
		
		// initialize Google Gson
		gbuilder = new GsonBuilder();
		gbuilder.disableHtmlEscaping();
		gson = gbuilder.setPrettyPrinting().create();
	}
	
	
	/**
	 * add a canvas to the TOC
	 * 
	 * @param url 	URI representation of the canvas
	 */
	public void addCanvas(String url)
	{
		this.canvas.add(url);
	}
	
	
	/**
	 * add a range to the TOC
	 * 
	 * @param url 		URI representation of the range
	 * @param recursiv 	boolean, which decides how continue with the range
	 * @param label 	label of the book paragraph 
	 * @return display the range section as a string
	 */
	public String addRange(String url,boolean recursiv, String label)
	{
		
		int c = range.size();
		String sc = id+"-"+c;
		
		this.range.add(sc);
		
		if(!recursiv)
		{
			TOC ts_1 = new TOC(sc,type,label,structLink);
			for(String s:structLink.get(url))
				ts_1.addCanvas(s);
			
			tocsparts.add(ts_1);
		}
		
		return sc;
	}
	
	
	/**
	 * add TOC part to the manifest
	 * 
	 * @param ts 	table of content
	 */
	public void addTS(TOC ts) {
		this.tocsparts.add(ts);
	}
	
	
	/**
	 * create a segment of the structure
	 * put id, type, label and either ranges and/or canvases together
	 *  
	 * @return get all segments of the structure as a string
	 */
	public String getJSONPart()
	{
		String retval = "";
		
		if(range.size()>0 && canvas.size()>0)
		{
			TemplateRange p3 = new TemplateRange(id, type, label);
			p3.ranges = new ArrayList<String>();
			p3.canvases = new ArrayList<String>();			
			for(String r:range)
				p3.addRanges(r);
			for(String c:canvas)
				p3.addCanvas(c);
			
			retval = gson.toJson(p3);
		}
		
		if(range.size()>0 && canvas.size()==0)
		{
			TemplateRange p2 = new TemplateRange(id, type, label);
			p2.ranges = new ArrayList<String>();
			for(String r:range)				
				p2.addRanges(r);
			
			retval = gson.toJson(p2);
		}

		if(range.size()==0 && canvas.size()>0)
		{
			TemplateRange p1 = new TemplateRange(id, type, label);
			p1.canvases = new ArrayList<String>();
			for(String c:canvas)
				p1.addCanvas(c);
			
			retval = gson.toJson(p1);
			
		}

		if(jsonparts.size()>0)
			for(String json:jsonparts)
				retval+=",\n"+json;
		
		if(tocsparts.size()>0) {
			for(TOC ts:tocsparts)
				retval+=",\n"+ts.getJSONPart();
		}
		
		return retval;
	}
}
