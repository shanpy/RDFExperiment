package com.pengyin.rdfexperiment;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class RdfPropertyList {
	public static String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static String akt = "http://www.aktors.org/ontology/portal#";
	public static String support = "http://www.aktors.org/ontology/support#";
	
	public static Resource r_proceedings_paper_reference = ResourceFactory.createResource(akt+"Proceedings-Paper-Reference");
	
	public static Property p_hasTitle = ResourceFactory.createProperty(akt+"has-title");
	
	public static Property p_hasAuthor = ResourceFactory.createProperty(akt+"has-author");
	public static Property p_fullName = ResourceFactory.createProperty(akt+"full-name");
	
	public static Property p_hasDate = ResourceFactory.createProperty(akt+"has-date");
	public static Property p_year = ResourceFactory.createProperty(support+"year-of");
	
	//Tempory skip conference record
}
