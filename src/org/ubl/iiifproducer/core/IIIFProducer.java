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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import org.fsi.JParam.JParameterHandler;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.ubl.iiifproducer.template.TemplateBody;
import org.ubl.iiifproducer.template.TemplateCanvas;
import org.ubl.iiifproducer.template.TemplateImage;
import org.ubl.iiifproducer.template.TemplateMetadata;
import org.ubl.iiifproducer.template.TemplateResource;
import org.ubl.iiifproducer.template.TemplateSequence;
import org.ubl.iiifproducer.template.TemplateService;

import com.google.gson.Gson;    
import com.google.gson.GsonBuilder;

public class IIIFProducer {
	
	private static Logger logger = Logger.getLogger(IIIFProducer.class);	

	Properties p = new Properties();
	String config;
	String fs = File.separator;
	GsonBuilder gbuilder = new GsonBuilder();
	Gson gson;	
    Document doca = null;
    Document doc = null;
    Document docl = null;
    String base_url;
    String source_dir;
    Boolean anchorFile = false;
    Namespace goobi_name = Namespace.getNamespace("goobi","http://meta.goobi.org/v1.5.1/");
	Namespace mets_name = Namespace.getNamespace("mets","http://www.loc.gov/METS/");
	Namespace mods_name = Namespace.getNamespace("mods", "http://www.loc.gov/mods/v3");
	Namespace xlink = Namespace.getNamespace("http://www.w3.org/1999/xlink");
	Namespace dv = Namespace.getNamespace("dv", "http://dfg-viewer.de/");
	TemplateBody body = new TemplateBody();
	TemplateMetadata tempmeta;
	TemplateSequence tempseq = new TemplateSequence();
	TemplateCanvas tempcanv;
	TemplateImage tempimg;
	TemplateResource tempres;
	TemplateService tempser;
	Element text;
	DOMprocedure dp = new DOMprocedure();
	Manuscript man = new Manuscript();
    
    int toc_counter = 0;
   
    /**
     * IIIFProducer Class
     * 
     * BasicConfigurator:	initialize the log4j configuration
     * loadConfig:			load the properties configuration file for all classes
     * init:				initialize the xml files to read with jdom
     * run:					run the program to create a JSON manifest
     * 
     * @param ptitle 		title of the digital copy
     * @param viewid 		number on the target disk
     * @param sourcedir 	path to the source digital copy
     * @param output 		name of the target json-file
     * @param configPath   	path to the configuration file
     */    
	public IIIFProducer(String ptitle, String viewid, String sourcedir, String output, String configPath)
	{
		BasicConfigurator.configure();
		config = configPath;
		loadConfig(config);
		dp.loadConfig(config);
		init(ptitle, viewid, sourcedir);
		run(ptitle, viewid, sourcedir, output);
	}
	
	
	/**
	 * load the configuration file
	 * 
	 * @param config	path to the configuration file
	 */
	public void loadConfig(String config) {
		try {
			//loading property-values						
			p.load(new FileInputStream(config + File.separator + "iiifproducer.properties"));
		} catch(Exception e)
		{
			logger.error(e.fillInStackTrace());
		}		
	}
	
	
	/**
	 * method to initialization
	 * 
	 * @param label_xml 	path to translating xml-file
	 * @param anchor_xml 	path to the anchor-xml-file
	 * @param mets_xml 		path to the mets/mods-xml-file
	 * @param doca load 	document for the anchor-xml-file
	 * @param doc load 		document for the mets/mods-xml-file
	 */
	private void init(String ptitle, String viewid, String sourcedir)
	{
		gbuilder.disableHtmlEscaping();
		gson = gbuilder.setPrettyPrinting().create();
	    
		logger.info("Path to the export files: " + source_dir + ptitle);
	    logger.info("Update DigiLife-Datenbank on " + ptitle + " with the ViewID " + Integer.toString(Integer.parseInt(viewid)));
	    updateSQLdig(ptitle, Integer.toString(Integer.parseInt(viewid)));

	    source_dir = sourcedir;
		String label_xml = p.getProperty("Label-Dir");
		String anchor_xml = source_dir + ptitle + "_anchor.xml";
		String mets_xml = source_dir + ptitle + ".xml";	    
	    
	    File labelfile = new File(label_xml);
	    if (labelfile.exists()) {
	    	try {
	    		docl = new SAXBuilder().build(labelfile);
	        } catch (Exception e) {
				logger.error(e.getMessage());
	        	System.exit(0);
	        }
	    	logger.info("Reading locallang.xml ...");
		} else {
			logger.error("false\n\nExiting.");
			System.exit(0);
		}
		
	    File anchorfile = new File(anchor_xml);
	    if (anchorfile.exists())
	    {
	    	try {
	        	doca = new SAXBuilder().build(anchorfile);
	        } catch (Exception e) {
				logger.error(e.getMessage());
	        	System.exit(0);
	        }
	    	anchorFile = true;
	    	logger.info("Reading anchor.xml ...");
	    } 
	    
	    File metsfile = new File(mets_xml);
	    if (metsfile.exists()) {
	    	try {
	        	doc = new SAXBuilder().build(metsfile);
	        } catch (Exception e) {
				System.out.println(e.getMessage());
	        	System.exit(0);
	        }	    	
	    	logger.info("Reading mets.xml ...");			
		} else {
			logger.error("false\n\nExiting.");
			System.exit(0);
		}
	}
	
	
	/**
	 * Uses templates to reconstruct the structure of an IIIF-Manifest.
	 * 
	 * method to run the IIIFProducer
	 * 
	 * @param ptitle 		title of the digital copy
	 * @param viewid 		number on the target disk
	 * @param sourcedir 	path to the source digital copy
	 * @param output 		name of the target json-file
	 */
	@SuppressWarnings("unchecked")
	public void run(String ptitle, String viewid, String sourcedir, String output) {
		base_url = p.getProperty("Base-URL");
		body.id = base_url + viewid + "/manifest.json";
		body.context = p.getProperty("IIIF-Version");
		
		Collection<Namespace> cname = new ArrayList<Namespace>();
		cname.add(mets_name); 
		cname.add(mods_name);
		if (anchorFile) {
			String xpath = p.getProperty("Image-Description");
			String xpath2 = p.getProperty("Metadata.Census");
			if (dp.existElement(xpath, Filters.element(), cname, doca) && dp.existElement(xpath2, Filters.element(), cname, doc)) {
				body.label = ((Element)dp.getFirstDOM(xpath, Filters.element(), cname, doca)).getText() + " ; " + ((Element)dp.getFirstDOM(xpath2, Filters.element(), cname, doc)).getText();
				tempmeta = new TemplateMetadata();
				tempmeta.label = "Part of";
				tempmeta.value = ((Element)dp.getFirstDOM(xpath, Filters.element(), cname, doca)).getText() + " ; " + ((Element)dp.getFirstDOM(xpath2, Filters.element(), cname, doc)).getText();
				body.metadata.add(tempmeta);
			} else {
				body.label = ((Element)dp.getFirstDOM(xpath, Filters.element(), cname, doca)).getText();				
			}
		} else {
			String xpath = p.getProperty("Image-Description");
			text = (Element)dp.getFirstDOM(xpath, Filters.element(), cname, doc);
			body.label = text.getText();
		}
		
		String xpath = p.getProperty("Viewer-URL");
		body.related.add(xpath + viewid);
		body.related.add(base_url + viewid + "/manifest.json");
		
		cname.add(dv);
		xpath = p.getProperty("Metadata.Owner");
		if (dp.existElement(xpath, Filters.element(), cname, doc)) {
			text = (Element)dp.getFirstDOM(xpath, Filters.element(), cname, doc);
			body.attribution = "Provided by " + text.getText();
		}
		
		xpath = p.getProperty("Metadata.Logo");
		if (dp.existElement(xpath, Filters.element(), cname, doc)) {
			text = (Element)dp.getFirstDOM(xpath, Filters.element(), cname, doc);
			body.logo = text.getText();
		}
		
		xpath = p.getProperty("Manuscript");
		if (dp.existElement(xpath, Filters.element(), cname, doc)) {
			if (((Element)dp.getFirstDOM(xpath, Filters.element(), cname, doc)).getText().equals("Handschrift")) {
				body.metadata.addAll(man.info(config, doc));
			}
		} else {
			xpath = p.getProperty("Metadata.IDS");
			List<Element> ids = (List<Element>)(Object)dp.getAllDOM(xpath, Filters.element(), cname, doc);
			for (Element ident:ids) {				
				tempmeta = new TemplateMetadata();
				String meta_label = ident.getAttributeValue("type").trim();
				switch (meta_label) {
				case "goobi":
					tempmeta.label = "Kitodo";
					break;
				case "urn":
					tempmeta.label = "URN";
					break;
				case "swb-ppn":
					tempmeta.label = "Source PPN (SWB)";
					break;
				default:
					tempmeta.label = meta_label.toUpperCase();
				}
				tempmeta.value = ident.getTextTrim();
				body.metadata.add(tempmeta);
			}
			
			try {
				for (String attr:Files.readAllLines(Paths.get(config + File.separator + "attribute.txt"), StandardCharsets.UTF_8)) {
					xpath = p.getProperty(attr.substring(0, attr.indexOf(":")));
					if (dp.existElement(xpath, Filters.element(), cname, doc)) {
						body.metadata.add(dp.setSingleInfo(attr.substring(0, attr.indexOf(":")), attr.substring(attr.indexOf(":") + 1), Filters.element(), cname, doc));
					}
				}
			} catch (IOException e) {
				logger.error(e.fillInStackTrace());
			}

			xpath = p.getProperty("Metadata.Note");
			if (dp.existElement(xpath, Filters.text(), cname, doc)) {				
				ids = (List<Element>)(Object)dp.getAllDOM(xpath, Filters.element(), cname, doc);
				for (Element ident:ids) {
					tempmeta = new TemplateMetadata();
					tempmeta.label = ident.getAttributeValue("type", mods_name).trim();
					tempmeta.value = ident.getTextTrim();
					body.metadata.add(tempmeta);
				}
			}
		}
		
		tempseq.id = base_url + viewid + "/sequence/1";
		
		String temp;
		String width;
		String height;
		xpath = p.getProperty("Struct.Phys");		
		List<Element> cs = (List<Element>)(Object)dp.getAllDOM(xpath, Filters.element(), cname, doc);
		ArrayList<Object> canid = new ArrayList<>();
		//int i = 1; 
		for (Element ces:cs) {
			tempcanv = new TemplateCanvas();
			tempimg = new TemplateImage();
			tempres = new TemplateResource();
			tempser = new TemplateService();

			//tempcanv.id = base_url + viewid + "/canvas/" + (i++);
			canid.add(tempcanv.id.trim());
			tempres.service = tempser;	
			try {
				xpath = p.getProperty("Struct.File");				
				tempcanv.label = ces.getAttributeValue("ORDERLABEL").trim();				
				tempres.label = ces.getAttributeValue("ORDERLABEL").trim();				
				temp = ces.getChild("fptr", mets_name).getAttributeValue("FILEID");				
				tempres.format = "image/jpeg";
				List<Element> files = (List<Element>)(Object)dp.getAllDOM(xpath, Filters.element(), cname, doc);
				String filename = "";
				for (Element file:files) {
					if (file.getAttributeValue("ID").equals(temp)) {
						filename = file.getChild("FLocat", mets_name).getAttributeValue("href", xlink);
						filename = source_dir + filename.replace("/", File.separator);
					}
				}
				
				if(!new File(filename).exists()) 
				{
					int p0 = filename.lastIndexOf(".");
					filename = filename.substring(0, p0) + ".TIF";
					if(!new File(filename).exists())
						logger.error(new Exception("No file found (try to correct was not working!)"));
				}
				
				width = getImageDimensions(filename)[0];
				height = getImageDimensions(filename)[1];				
				tempcanv.width = width;
				tempcanv.height = height;
				tempres.width = width;				
				tempres.height = height;
				filename = FilenameUtils.getBaseName(filename);
				tempcanv.id = base_url + viewid + "/canvas/" + filename;
				tempres.id = (base_url + viewid + "/" + filename + ".jpx").trim();
				
				int viewid_int = Integer.parseInt(viewid);
				String image_dir = p.getProperty("Image-Dir");				
				String newimage_dir = Integer.toString(viewid_int/100);
				
				while(newimage_dir.length() < 4)
					newimage_dir = "0" + newimage_dir;
				
				image_dir = image_dir.split("/")[0] + "/" + image_dir.split("/")[1] + "/" + image_dir.split("/")[2] + "/" + newimage_dir + "/";
				
				tempser.id = (p.getProperty("Service-Base") + image_dir + viewid + "/" + filename + ".jpx").trim();
			} catch (Exception e) {
				logger.error(e.getMessage());
				System.exit(1);
			}

			tempimg.resource = tempres;
			tempimg.on = tempcanv.id;
			tempcanv.images.add(tempimg);
			tempseq.canvases.add(tempcanv);
		}
		
		body.sequences.add(tempseq);
		
		try{								
			String json = gson.toJson(body);
			
			String toc_json = createTOC(ptitle, sourcedir, viewid, cname);

			if(toc_json!=null) {
			
				json = json.substring(0,json.lastIndexOf("}")-1);
				json +=",\n\"structures\": [\n"+ toc_json;
				json +="]\n}";
			
			}
			
			FileOutputStream fos = new FileOutputStream(new File(output));
			fos.write(json.getBytes());
			fos.close();
		}
		catch(Exception e)
		{
			logger.error(e.fillInStackTrace());
		}
	}
	
	
	/**
	 * method to create parts of the table of content recursively
	 * divided into ranges and canvases 
	 * 
	 * @param ls 			list of all elements, which are contained in the logical part of the xml-file
	 * @param structLink 	mapping between a logical part and the related images
	 * @param depth 		depth of the logical structure
	 * @param ts 			initiation of a table of content structure
	 * @param viewid 		number on the target disk
	 * @return the complete table of content
	 */
	public TOC createTOC_recursiv(List<Element> ls, HashMap<String, ArrayList<String>> structLink, int depth, TOC ts, String viewid)
	{
		for(Element child_log0000:ls)
		{			
			if(child_log0000.getChildren().size()>0)
			{
				String log = child_log0000.getAttributeValue("ID");
				String label = child_log0000.getAttributeValue("LABEL");
				if(label==null) {
					label = child_log0000.getAttributeValue("TYPE");
					label = label_matching(label);
				}				
				String sc = ts.addRange(log, true, label);				
				sc = sc.substring(sc.lastIndexOf("/")+1);
				TOC tsx = new TOC(p.getProperty("Base-URL")+viewid+"/range/"+sc,"sc:Range",label,structLink);
				for (int i = 0; i < structLink.get(log).size(); i++)
				{		
					tsx.addCanvas(structLink.get(log).get(i));
				}
				tsx = createTOC_recursiv(child_log0000.getChildren(), structLink, depth+1, tsx, viewid);				
				ts.addTS(tsx);				
			}
			else
			{
				String log = child_log0000.getAttributeValue("ID");
				String label = child_log0000.getAttributeValue("LABEL");
				if(label==null) {
					label = child_log0000.getAttributeValue("TYPE");
					label = label_matching(label);
				}				
				ts.addRange(log,false,label);				
			}
		}
		
		return ts;
	}
	
	
	/**
	 * method to create the whole table of content
	 * 
	 * @param ptitle 		title of the digital copy
	 * @param sourcedir 	path to the source digital copy
	 * @param viewid 		number on the target disk
	 * @param cname 		collection of namespaces from the mets/mods-format
	 * @return a json-formatted string with the whole TOC 
	 */
	private String createTOC(String ptitle, String sourcedir, String viewid, Collection<Namespace> cname)
	{
		HashMap<String, ArrayList<String>> structLink = getPhysImage(ptitle,sourcedir,viewid, p.getProperty("Base-URL"), cname);
		
		Element logic_struct = (Element)dp.getFirstDOM(p.getProperty("Struct.Logic"), Filters.element(), cname, doc);
	
		if (anchorFile)	
		{
			Element log_element = logic_struct.getChildren().get(0).getChildren().get(1);
			List<Element> ls = logic_struct.getChildren().get(0).getChildren().get(1).getChildren();
						
			if(ls.size()==0) {
				return null;
			}
			
			String log_mets = log_element.getAttributeValue("ID");			
			Element logic_struct_anchor = (Element)dp.getFirstDOM(p.getProperty("Struct.Logic"), Filters.element(), cname, doca);
			Element log0001 = logic_struct_anchor.getChildren().get(0).getChildren().get(0);
			
			String log_start = log0001.getAttributeValue("ID");
			String label_start = log0001.getAttributeValue("LABEL");
			if (label_start == null) {
				String volume_title = logic_struct_anchor.getChildren().get(0).getAttributeValue("LABEL");
				String xpath2 = p.getProperty("Anchor.Number");
				if (dp.existElement(xpath2, Filters.element(), cname, doc)) {
					Element part = (Element)dp.getFirstDOM(xpath2, Filters.text(), cname, doc);					
					label_start = volume_title + " ; " + part.getTextTrim();
				} else 
					label_start = volume_title;
			}
						
			int d = 0;
			TOC ts = new TOC(p.getProperty("Base-URL")+viewid+"/range/"+toc_counter,"sc:Range","TOC",structLink);
			String sc = ts.addRange(log_start, true, label_start);
			sc = sc.substring(sc.lastIndexOf("/")+1);
			TOC tsx = null;
			if (log0001.getAttributeValue("LABEL") != null) {
				tsx = new TOC(p.getProperty("Base-URL")+viewid+"/range/"+sc,"sc:Range",log0001.getAttributeValue("LABEL"),structLink);
			} else {
				tsx = new TOC(p.getProperty("Base-URL")+viewid+"/range/"+sc,"sc:Range",label_start,structLink);
			}
			for (int i = 0; i < structLink.get(log_mets).size(); i++)
			{					
				tsx.addCanvas(structLink.get(log_mets).get(i));
			}
			tsx = createTOC_recursiv(ls, structLink, d+1, tsx, viewid);
			ts.addTS(tsx);
			
			return ts.getJSONPart();
		}
		else
		{
			List<Element> ls = logic_struct.getChildren().get(0).getChildren();			
			
			if(ls.size()==0) {
				return null;
			}
			
			int d = 0;
			TOC ts = new TOC(p.getProperty("Base-URL")+viewid+"/range/"+toc_counter,"sc:Range","TOC",structLink);
			ts = createTOC_recursiv(ls,structLink, d, ts, viewid);
			
			return ts.getJSONPart();
		}
			
	}
	
	
	/**
	 * access to the image through the canvas path
	 * 
	 * @param ptitle 		title of the digital copy
	 * @param sourcedir 	path to the source digital copy
	 * @param viewid 		number on the target disk
	 * @param baseurl 		Base-URI "https://iiif.ub.uni-leipzig.de
	 * @param cname 		collection of namespaces from the mets/mods-format
	 * @return hash map with all informations about the images
	 */
	private HashMap<String, ArrayList<String>> getPhysImage(String ptitle, String sourcedir, String viewid, String baseurl, Collection<Namespace> cname)
	{
		String xpath = p.getProperty("Struct.Link");
		Element struct = (Element)dp.getFirstDOM(xpath, Filters.element(), cname, doc);
		
		HashMap<String, ArrayList<String>> mainmap = new HashMap<String, ArrayList<String>>();
		
		for(Element smlink:struct.getChildren()) {
			String from = smlink.getAttributeValue("from",xlink);
			if(!mainmap.containsKey(from))
			{
				mainmap.put(from, new ArrayList<String>());
			}
			String to = smlink.getAttributeValue("to",xlink);
			mainmap.get(from).add(baseurl+viewid+"/canvas/"+Integer.parseInt(to.substring(5)));
		}
		
		return mainmap;
	}
	
	
	/**
	 * get image width and height from the filepath of the image server 
	 * 
	 * @param filename 	path name of the current image  
	 * @return String Array with width and height of the current image
	 */
	private String[] getImageDimensions(String filename) {
		String retval[] = new String[2];
		try{
			PlanarImage image = JAI.create("fileload", filename);
			int w = image.getWidth();
			int h = image.getHeight();
			retval[0] = Integer.toString(w);
			retval[1] = Integer.toString(h);
		} catch(Exception e) {
			logger.error(e.fillInStackTrace());
		}
		return retval;
	}
	

	/**
	 * method to replace the String with the correct bibliographical meaning
	 * 
	 * @param preLabel 	name to search in the locallang-xml-file 
	 * @return matched String
	 */
	@SuppressWarnings("unchecked")
	private String label_matching (String preLabel) {
		String xpath = p.getProperty("Label.Index");
		List<Element> lab = (List<Element>)(Object)dp.getAllLang(xpath, Filters.element(), docl);
		String postLabel = "";
		for(Element index:lab) {
			try {				
				String label = index.getAttributeValue("index");				
				if(label.equals(preLabel)) {
					postLabel = index.getText();
					break;
				}				
			} catch (Exception e) {
				logger.error(e.getMessage());
				System.exit(1);
			}				
		}
		return postLabel;
	}
	
	
	/**
	 * method to update the database
	 * 
	 * @param ptitle 	title of the digital copy
	 * @param viewid 	number on the target disk
	 */
	private void updateSQLdig(String ptitle, String viewid) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error(e.fillInStackTrace());
		}
		
		try {
			Connection connect_digilife = DriverManager.getConnection("jdbc:mysql://localhost/digilife?user=goobi&password=FipuvRib2");			
			Statement statement_digilife = connect_digilife.createStatement();
			
			ResultSet rs = statement_digilife.executeQuery("select view_id from metadata where title like '" + ptitle + "'");
			if(rs.next()) {
				String vid = rs.getString(1);
				if (vid.isEmpty() || vid == null)
					statement_digilife.executeUpdate("update metadata set view_id = '" + viewid + "' where title like '" + ptitle + "'");
			}			
			
			statement_digilife.close();
			connect_digilife.close();
		} catch (SQLException e) {
			logger.error("Datenbankeintrag der ViewID " + viewid + " hat nicht geklappt.");
			logger.error(e.fillInStackTrace());
		}
	}
	
	
	/**
	 * A java tool to generate a manifest.json, a required file to display books or manuscripts with the mirador viewer.
	 * 
	 * input:
	 * mets/mods-xml file
	 * tiff-images
	 * 
	 * output:
	 * manifest.json
	 * 
	 * main class
	 * initiate IIIFProducer
	 * 
	 * @param args 			ptitle viewid config output sourcedir
     * @param ptitle 		title of the digital copy
     * @param viewid 		number on the target disk
     * @param sourcedir 	path to the source digital copy
     * @param output 		name of the target json-file
     * @param config 		path to the cinfiguration file
	 */
	public static void main(String[] args) {
		JParameterHandler jp = new JParameterHandler(args);
		
		if( !jp.consistsParam("ptitle") || 
				!jp.consistsParam("viewid") ||
				!jp.consistsParam("config") ||
				!jp.consistsParam("output") ||
				!jp.consistsParam("sourcedir") ) {
			logger.warn("Usage: \n\nIIIFProducer -ptitle:{ptitle} -viewid:{viewid} -sourcedir:{sourcedir} -output:{output} -config:{config}");
			System.exit(0);
		}
		
		new IIIFProducer(
				jp.getParamValue("ptitle"),
				jp.getParamValue("viewid"),
				jp.getParamValue("sourcedir"),
				jp.getParamValue("output"),
				jp.getParamValue("config")
				);
		
		logger.info("End.");
	}

}
