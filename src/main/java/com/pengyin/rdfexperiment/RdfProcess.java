package com.pengyin.rdfexperiment;

import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.StmtIterator;

import java.util.*;

public class RdfProcess {
	
	public static ArrayList<RdfModel> processRDF(InputStream in){
		Model model = ModelFactory.createDefaultModel();
		ArrayList<RdfModel> result = new ArrayList<RdfModel>();
		if(in != null){
			model.read(in, "RDF/XML");
			//Now, I only care these propties: has-title, year-of, full-name. All three of them must exist'
			for(final ResIterator it = model.listSubjectsWithProperty(RdfPropertyList.p_hasTitle); it.hasNext();)
			{
				RdfModel rm = new RdfModel();
				try{
				final Resource node = it.next().asResource(); //node is a resource which has title property
				rm.setHasTitle(node.getProperty(RdfPropertyList.p_hasTitle).getString());
				
				StringBuilder authors = new StringBuilder();
				StringBuilder dates = new StringBuilder(); 
				
				for(final StmtIterator all_props = node.listProperties(); all_props.hasNext();)
				{
					try{
						Resource all_res = all_props.next().getObject().asResource();
						StmtIterator fullnames = all_res.listProperties(RdfPropertyList.p_fullName);
						StmtIterator years = all_res.listProperties(RdfPropertyList.p_year);
						//Just for now I may have mutiple author or dates in a String, seperated by comma
						RdfProcess newprocess = new RdfProcess();
									
						while(fullnames.hasNext()){
							String fullname = newprocess.getValue(fullnames.next().getObject());
							if(!fullname.equals("Invalid/Lack of Information")){
								authors.append(fullname + " , ");
							}
						}
						while(years.hasNext()){
							String year = newprocess.getValue(years.next().getObject());
							if(!year.equals("Invalid/Lack of Information")){
								dates.append(year + " , ");
							}
						}	
					}catch(Exception e){
					}
				}
				rm.setHasDate(dates.toString());	
				rm.setHasAuthor(authors.toString());
				}catch(Exception e){
				}
				result.add(rm);
			}
		}
		return result;
	}
	

	private String getValue(RDFNode r){
		String result = "Invalid/Lack of Information";
		if(r.isAnon()){			
		}else if(r.isLiteral()){
			result = r.asLiteral().getString();
		}else if(r.isResource() || r.isURIResource()){
			result = r.asResource().getLocalName();
		}else{;
		}
		return result;
	}
}


