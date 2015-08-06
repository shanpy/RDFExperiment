<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>RDF Experiment</title>
	<link rel="stylesheet" type="text/css" href="./resources/css/bootstrap.min.css">
	<style>
		.row{
			padding: 1em;
		}
	</style>

	<script src="./resources/js/jquery.min.js"></script>
	<script src="./resources/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.6/d3.min.js" charset="utf-8"></script>	
</head>
<body>
				<nav class="navbar navbar-default">
				  <div class="container-fluid">
				    <div class="navbar-header">
				      <a class="navbar-brand" href="./">Pengyin's Experiment of RDF</a>
				    </div>  
					<form:form method="post" action="performSearch" class="form-inline" modelAttribute="rdfModel">
						  <div class="form-group">
						    <label for="searchtitle">Search Title</label>
						    <form:input type="text" class="form-control" id="searchtitle" path="hasTitle"/>
						  </div>
						  <button id="searchButton" class="btn btn-default" type="submit">Submit</button>
					</form:form>
				   </div>
				</nav> 
				<div class="container-fluid"> 
					<div class="row">
						<div  class="col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10">
							<div class="alert alert-dismissible alert-success">
							  <button type="button" class="close" data-dismiss="alert">×</button>
							  <strong>This page is working in process. </strong>Try "System" or "Security" for Title Field.
							</div>
							<table id="resultTable" class="table table table-striped table-hover ">
							  <thead>
							  	<tr>
							  	<th>Title</th>
							  	<th>Author</th>
							  	<th>Date</th>
							  	</tr>
							  </thead>
							  <tbody>
							  	<c:forEach items="${searchResults}" var="result">
								    <tr>
								    	<td>${result.hasTitle}</td>
								    	<td class="authorResult">${result.hasAuthor}</td>
								    	<td class="dateResult">${result.hasDate}</td>
								    </tr>
								</c:forEach>
							  </tbody>
							</table>
							<div style="margin-top: 2em; height:300px; width: 500px;" id="chartSVG">
							</div>
						</div>
					</div>
				</div>
</body>
<script>
$(document).ready( function () {
    
    //Remove end comma for author and date
    $('.authorResult').each(function(index,value){
    	var len = $(value).text().trim().length
    	if(len>0)
    		$(value).text($(value).text().trim().substr(0,len-2));
    });   
    $('.dateResult').each(function(index,value){
    	var len = $(value).text().trim().length
    	if(len>0)
    		$(value).text($(value).text().trim().substr(0,len-2));
    });

    if($('#resultTable').find('tbody').find('tr').length > 0)
    	createPieChart();
} );

//Use D3.js to virtualize data in table
function createPieChart(){
	
		//Prepare dataset
		var totaldocs = parseInt($('#resultTable').find('tbody').find('tr').length);
		var docs2005 = 0;
		var docs2006 = 0; 
		var docsunknown = 0;    	
		$('.dateResult').each(function(index,value){
			if(parseInt(value.innerHTML.trim())==2005){
				docs2005 += 1;
			}
			else if(parseInt(value.innerHTML.trim())==2006){
				docs2006 += 1;
			}
			else
				docsunknown += 1;
		});
		var dataset = [{"label": "Publications from 2005", "value": docs2005},
		               {"label": "Publications from 2006", "value": docs2006},
		               {"label": "Unknown Year of Publications", "value": docsunknown}];
    
		//Prepare for D3
	    var h = 300;
    	var w = 500;
    	var r = Math.min(w, h) / 2;
	    var svg = d3.select(document.getElementById('chartSVG')).data([dataset])
	    			.append('svg').attr('height',h).attr('width', w).append('g').attr("transform", "translate(" + r + "," + r + ")");
	
        var arc = d3.svg.arc().outerRadius(r);
    	
    	//Create Pie Chart
    	var pie = d3.layout.pie().value(function(d){return d.value;});
    	var arcs = svg.selectAll('g.slice')
    					.data(pie)
    					.enter()
    					.append('g')
    					.attr('class', 'slice');
    	
    	//Fill Color
    	var color = d3.scale.category20c();
    	arcs.append('path')
    		.attr('fill', function(d,i){
    			return color(i);
    		}).attr('d',arc);
		
    	//Add Label
    	arcs.append('text').attr('transform',function(d){
    		d.innerRadius = 0;
            d.outerRadius = r;
    		return "translate(" + arc.centroid(d) + ")";})
    						.attr('text-anchor','middle')
					    	.text(function(d,i){
					    		if(dataset[i].value != 0)
					    			return dataset[i].label + ": " + dataset[i].value;
					    	});			
}
</script>
</html>