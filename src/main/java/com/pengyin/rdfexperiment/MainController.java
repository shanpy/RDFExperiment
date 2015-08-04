package com.pengyin.rdfexperiment;

import io.searchbox.client.JestClient;
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
		InputStream in = servletContext.getResourceAsStream("/WEB-INF/content/ibm-publications-2005.rdf");
		try {
			RdfProcess.processRDF(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("rdfModel", new RdfModel());
		return "index";
	}
	
	@RequestMapping(value="/performSearch", method = RequestMethod.POST)
	public ModelAndView performSearch(@ModelAttribute("RdfModel") RdfModel rdfModel, Model model){
		ModelAndView res = new ModelAndView();
		res.setViewName("index");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder qb = QueryBuilders.termsQuery("hasAuthor", rdfModel.getHasAuthor());
		searchSourceBuilder.query(qb);
		System.out.println(searchSourceBuilder.toString());
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
