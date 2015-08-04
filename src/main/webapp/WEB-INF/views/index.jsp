<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>RDF Experiment</title>
	<link rel="stylesheet" type="text/css" href="./resources/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.7/css/jquery.dataTables.css">
	<script src="./resources/js/jquery.min.js"></script>
	<script src="./resources/js/bootstrap.min.js"></script>
	<script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.js"></script>
	
	<style>
		.row{
			padding: 1em;
		}
	</style>
</head>
<body>
				<nav class="navbar navbar-default">
				  <div class="container-fluid">
				    <div class="navbar-header">
				      <a class="navbar-brand" href="#">Pengyin's Experiment of RDF</a>
				    </div>  
					<form:form method="post" action="performSearch" class="form-inline" modelAttribute="rdfModel">
						  <div class="form-group">
						    <label for="searchtitle">Search Title</label>
						    <form:input type="text" class="form-control" id="searchtitle" path="hasTitle"/>
						  </div>
						  <div class="form-group">
						    <label for="searchauthor">Search Author</label>
						    <form:input type="text" class="form-control" id="searchauthor" path="hasAuthor"/>
						  </div>
						  <button class="btn btn-default" type="submit">Submit</button>
					</form:form>
				   </div>
				</nav> 
				<div class="container-fluid"> 
					<div class="row">
						<div  class="col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10">
							<div class="alert alert-dismissible alert-success">
							  <button type="button" class="close" data-dismiss="alert">×</button>
							  <strong>This page is working in process. </strong>Try "Pengyin Shan" for Search Author Field.
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
								    	<td>${result.hasAuthor}</td>
								    	<td>${result.hasDate}</td>
								    </tr>
								</c:forEach>
							  </tbody>
							</table>
						</div>
					</div>
				</div>
</body>
<script>
$(document).ready( function () {
    $('#resultTable').DataTable();
} );
</script>
</html>