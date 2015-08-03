package com.pengyin.rdfexperiment;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

import java.text.DateFormat;
import java.util.*;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	JestClient jc;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@ModelAttribute("RdfModel") RdfModel rdfModel, Map<String, Object> model) {
		jc = RdfSearch.Init();
		return "index";
	}
	
	@RequestMapping(value="/performSearch", method = RequestMethod.POST)
	public ModelAndView performSearch(@ModelAttribute("RdfModel") RdfModel rdfModel, Map<String, Object> model){
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("hasAuthor", rdfModel.getHasAuthor()));
		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("publication").build();
		try {
			SearchResult searchresult = jc.execute(search);
			List<RdfModel> publications = searchresult.getSourceAsObjectList(RdfModel.class);
			model.put("searchResults", publications);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("index",model);
	}
}
