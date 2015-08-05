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
							<div id="totaldocs" hidden>${TotalDocuments}</div>
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
							<div id="chartSVG">
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

    //Prepare for D3
    var h = 300;
    var padding = 5;
    var svg = d3.select(document.getElementById('chartSVG')).append('svg').attr('height',h).attr('width', '100%');
    var w = document.getElementsByTagName('svg')[0].offsetWidth;

    if($('#result').find('tbody').find(tr).length > 0)
    	createPieChart('resultTable');
} );

//Use D3.js to virtualize data in table
function createPieChart(id){
	
    	//Prepare dataset
    	var existingdocs = $('#' + id).find('tbody').find('tr').length;
    	var totaldocs = $('#totaldocs').text().trim();
    	var dateset = [existingdocs/totaldocs];
        window.console.log(dateset);

    	//Create Pie Chart
    	var pie = d3.layout.pie();
    	dateset = pie(dataset);

    	var outRadius = h/2;
    	var inRadius = 0;
    	var arc = d3.svg.arc().innerRadius(inRadius).outerRadius(outRadius);
    	var arcs = svg.selectAll('g.arc')
    					.data(dataset)
    					.enter()
    					.append('g')
    					.attr('class', 'arc')
    					.attr('transform','translate(' + outRadius + ',' + outRadius + ')');

    	arcs.append('path')
    		.attr('fill', function(d){
    			return 'rgb(100,' + Math.round(d.value) + ",200)";
    		}).attr('d',arc);

    	arcs.append('text').attr('transform',function(d){
    		return "translate(" + arc.centroid(d) + ")";})
    						.attr('text-anchor','middle')
					    	.text(function(d){
					    		return d.value;
					    	});			
}
</script>
</html>