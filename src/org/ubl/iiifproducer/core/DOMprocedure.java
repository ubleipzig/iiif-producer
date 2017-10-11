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
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.ubl.iiifproducer.template.TemplateMetadata;

public class DOMprocedure {
	
	private static Logger logger = Logger.getLogger(DOMprocedure.class);
	
	Properties p = new Properties();
	XPathFactory fact = XPathFactory.instance();
	XPathExpression xpe;
	TemplateMetadata tempmeta;
	Element text;	
	
	
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
	}
	
	
	/**
	 * get access to the document and find the first occurrence of the searched element
	 * 
	 * @param expression 	jdom2 path to the searched element
	 * @param definition 	type of filter e.g.: element, attribute, text
	 * @param cname 		collection of namespaces from the mets/mods-format
	 * @param doc 			load document for the xml-file
	 * @return representation of the choosen filter as a string
	 */
	public Object getFirstDOM(String expression, Filter<?> definition, Collection<Namespace> cname, Document doc) {
		Object retval = "";
		try {			
			xpe = fact.compile(expression, definition, null, cname);
			retval = (Object)xpe.evaluateFirst(doc);
		} catch (Exception e) {
			logger.info(e.getMessage());
			System.exit(0);
		}
		return retval;
	}
	
	
	/**
	 * get access to the locallang-xml-document and find the first occurrence of the searched element
	 * 
	 * @param expression 	jdom2 path to the searched element
	 * @param definition 	type of filter e.g.: element, attribute, text
	 * @param doc 			load document for the locallang-xml-file
	 * @return representation of the choosen filter as a string
	 */
	public Object getFirstLang(String expression, Filter<?> definition, Document doc) {
		Object retval = "";		
		try {			
			xpe = fact.compile(expression, definition);			
			retval = (Object)xpe.evaluateFirst(doc);			
		} catch (Exception e) {
			logger.info(e.getMessage());
			System.exit(0);
		}
		return retval;
	}
	
	
	/**
	 * get access to the document and find all occurrences of the searched element
	 * 
	 * @param expression 	jdom2 path to the searched element
	 * @param definition 	type of filter e.g.: element, attribute, text
	 * @param cname 		collection of namespaces from the mets/mods-format
	 * @param doc 			load document for the xml-file
	 * @return all representations of the choosen filter as a list of objects
	 */
	public List<Object> getAllDOM(String expression, Filter<?> definition, Collection<Namespace> cname, Document doc) {
		List<Object> domList = null;
		try {
			xpe = fact.compile(expression, definition, null, cname);
			domList = xpe.evaluate(doc);
		} catch (Exception e) {
			logger.info(e.getMessage());
			System.exit(0);
		}		
		return domList;
	}
	
	
	/** 
	 * get access to the locallang-xml-document and find all occurrences of the searched element
	 * 
	 * @param expression 	jdom2 path to the searched element
	 * @param definition 	type of filter e.g.: element, attribute, text
	 * @param doc 			load document for the locallang-xml-file
	 * @return all representations of the choosen filter as a list of objects
	 */
	public List<Object> getAllLang(String expression, Filter<?> definition, Document doc) {
		List<Object> domList = null;
		try {
			xpe = fact.compile(expression, definition);
			domList = xpe.evaluate(doc);
		} catch (Exception e) {
			logger.info(e.getMessage());
			System.exit(0);
		}		
		return domList;
	}
	
	
	/**
	 * get access to the document and set up a metadata-template with the appropriate data
	 * 
	 * @param point 		jdom2 path to the searched element
	 * @param label 		label of the metadata element 
	 * @param definition 	type of filter e.g.: element, attribute, text
	 * @param cname 		collection of namespaces from the mets/mods-format
	 * @param doc 			load document for the xml-file
	 * @return get a metadata-template, contains label and the label
	 */
	public TemplateMetadata setSingleInfo(String point, String label, Filter<?> definition, Collection<Namespace> cname, Document doc) {
		String domPath = p.getProperty(point);
		tempmeta = new TemplateMetadata();
		tempmeta.label = label;
		text = (Element)getFirstDOM(domPath, definition, cname, doc);
		tempmeta.value = text.getText();
		return tempmeta;
	}
	
	
	/**
	 * check for existence of the element in the given document
	 * 
	 * @param expression 	jdom2 path to the searched element
	 * @param definition 	type of filter e.g.: element, attribute, text
	 * @param cname 		collection of namespaces from the mets/mods-format
	 * @param doc 			load document for the xml-file
	 * @return either true if the element exists or false if not
	 */
	public boolean existElement(String expression, Filter<?> definition, Collection<Namespace> cname, Document doc) {
		Object retval = "";
		boolean exist = false;
		try {
			xpe = fact.compile(expression, definition, null, cname);
			retval = (Object)xpe.evaluateFirst(doc);
			if (retval != null && retval != "")
				exist = true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			System.exit(0);
		}
		
		return exist;
	}

}
