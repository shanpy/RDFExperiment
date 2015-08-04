package com.pengyin.rdfexperiment;

import java.io.InputStream;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.StmtIterator;

public class RdfProcess {
	
	public static void processRDF(InputStream in){
		Model model = ModelFactory.createDefaultModel();
		if(in != null){
			model.read(in, "RDF/XML");
			//Now, I only care these propties: has-title, year-of, full-name. All three of them must exist'
			for(final ResIterator it = model.listSubjectsWithProperty(RdfPropertyList.p_hasTitle); it.hasNext();)
			{
				try{
				final Resource node = it.next().asResource(); //node is a resource which has title property
				System.out.println("title: " + node.getProperty(RdfPropertyList.p_hasTitle).getString());				
				for(final StmtIterator all_props = node.listProperties(); all_props.hasNext();)
				{
					try{
						Resource all_res = all_props.next().getObject().asResource();
						StmtIterator fullnames = all_res.listProperties(RdfPropertyList.p_fullName);
						StmtIterator years = all_res.listProperties(RdfPropertyList.p_year);
							try{
									while(fullnames.hasNext()){
										System.out.println("Author: " + fullnames.next().getString());
									}
									while(years.hasNext()){
										System.out.println("Year: " + years.next().getString());		
									}
								}catch(Exception e){
									System.out.println("No result");
								}
					}catch(Exception e){
					}
				}
				}catch(Exception e){
				}
			}
		}
	}
}