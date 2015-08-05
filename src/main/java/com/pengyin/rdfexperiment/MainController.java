package com.pengyin.rdfexperiment;

import io.searchbox.client.JestClient;
import io.searchbox.core.Delete;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.*;

import javax.servlet.ServletContext;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;

@Controller
public class MainController {
	
	private @Autowired ServletContext servletContext;	
	private JestClient jc = RdfSearch.Init();
	
	@ModelAttribute("RdfModel")
	public RdfModel getUserForm() {
	    return new RdfModel();
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {		
		//Two Test Dataset from datehub.io
		InputStream in2005 = servletContext.getResourceAsStream("/WEB-INF/content/ibm-publications-2005.rdf");
		InputStream in2006 = servletContext.getResourceAsStream("/WEB-INF/content/ibm-publications-2006.rdf");
		try {
			ArrayList<RdfModel> rms2005 = RdfProcess.processRDF(in2005);
			Boolean b = rms2005.addAll(RdfProcess.processRDF(in2006));
			//First I temporary need to delete all existing indexes first to prevent duplicate insertion
			for(int i=0; i<rms2005.size(); i++)
				jc.execute(new Delete.Builder(String.valueOf(i)).index("publications").type("publication").build());
			//Then I want to re-index
			RdfSearch.Indexing(jc, rms2005);
			model.addAttribute("rdfModel", new RdfModel());
			model.addAttribute("TotalDocuments", rms2005.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}
	
	@RequestMapping(value="/performSearch", method = RequestMethod.POST)
	public ModelAndView performSearch(@ModelAttribute("RdfModel") RdfModel rdfModel, Model model){
		ModelAndView res = new ModelAndView();
		res.setViewName("index");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder qb = QueryBuilders.matchQuery("hasTitle", rdfModel.getHasTitle());
		searchSourceBuilder.query(qb);
		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("publications").build();
		try {
			SearchResult searchresult = jc.execute(search);		
			List<RdfModel> publications = searchresult.getSourceAsObjectList(RdfModel.class);
			System.out.println("result: " + publications.size());
			res.addObject("searchResults", publications);
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.addObject("rdfModel", new RdfModel());
		return res;
	}
}
