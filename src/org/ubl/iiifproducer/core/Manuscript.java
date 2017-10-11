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
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.ubl.iiifproducer.template.TemplateBody;
import org.ubl.iiifproducer.template.TemplateMetadata;

public class Manuscript {
	
	private static Logger logger = Logger.getLogger(Manuscript.class);
	
	Properties p = new Properties();
	DOMprocedure dp = new DOMprocedure();
	Namespace mets_name = Namespace.getNamespace("mets","http://www.loc.gov/METS/");
	Namespace mods_name = Namespace.getNamespace("mods", "http://www.loc.gov/mods/v3");
	TemplateBody body = new TemplateBody();
	TemplateMetadata tempmeta;
	
	
	/**
	 * load the configuration file
	 * 
	 * @param config 	path to the configuration file
	 */
	public void loadConfig(String config) {
		try {
			//loading property-values						
			p.load(new FileInputStream(config + File.separator + "iiifproducer.properties"));
		} catch(Exception e)
		{
			logger.info(e.getStackTrace());
		}
		dp.loadConfig(config);
	}
	
	
	/**
	 * collect all metadata information about a manuscript from the xml-file
	 * 
	 * @param config 	path to the configuration-file
	 * @param doc 		load document for the xml-file
	 * @return the whole metadata section with all requirements to the manuscript
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object> info(String config, Document doc) {
		loadConfig(config);
		
		Collection<Namespace> cname = new ArrayList<Namespace>();
		cname.add(mets_name);
		cname.add(mods_name);
		
		try {
			for (String attr:Files.readAllLines(Paths.get(config + File.separator + "attribute_manuscript.txt"), StandardCharsets.UTF_8)) {
				String xpath = p.getProperty(attr.substring(0, attr.indexOf(":")));
				if (dp.existElement(xpath, Filters.element(), cname, doc))
					body.metadata.add(dp.setSingleInfo(attr.substring(0, attr.indexOf(":")), attr.substring(attr.indexOf(":") + 1), Filters.element(), cname, doc));
			}
		} catch (IOException e) {
			logger.info(e.getStackTrace());
		}
		
		String xpath = p.getProperty("Manuscript.Dated");
		if (dp.existElement(xpath, Filters.element(), cname, doc))
			body.metadata.add(dp.setSingleInfo("Manuscript.Dated", "Datierung", Filters.element(), cname, doc));
		
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
			case "shelfmark":
				tempmeta.label = "Signatur";
				break;
			default:
				tempmeta.label = meta_label.toUpperCase();
			}
			tempmeta.value = ident.getTextTrim();
			body.metadata.add(tempmeta);
		}
		
		return body.metadata;
	}
}