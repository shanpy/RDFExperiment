###An experiment of using Jena and Elastic Search to process RDF

This is a small experiment Spring MVC web project I'm working on in my free time after work, in order to learn more about data processing of RDF or Ohter Linked Data.

####Tools/Frameworks:

- <a href="https://jena.apache.org/">Apache Jena</a>:  A free and open source Java framework for building Semantic Web and Linked Data applications. Very powerful.

- <a href="https://github.com/searchbox-io/Jest">Jest</a>: a Java HTTP Rest client for ElasticSearch

- Sample RDF Data: <a href="http://datahub.io/dataset/rkb-explorer-ibm">IBM Research GmbH</a> from datahub.io

- Heroku Cloud Platform: This Spring MVC web app is hosting freely on <a href="https://rdfexperiment.herokuapp.com/">Heroku</a>, with <a href="https://devcenter.heroku.com/articles/searchbox#using-jest-with-java">SearchBox Elasticsearch addon</a>, which is basiclly Jest on Heroku. I choose this free addon because for a quick project, I prefer to find a cloud ElasticSearch provider.

- Due to time limitation, I use simple Spring MVC framework and a little bit Bootstrap3, just want to build a Java web app quickly.

- <a href="http://d3js.org/">D3.js</a>: I'm learning it. It's a great client-side data-driven document manipulating tool.

####Agenda

- 8/1/2015 - 8/2/2015: A brief learn to Introdcution of RDF. Plan web app structure and find proper data resouce. Deploy web app to Heroku and quickly learn and add Jest to project.

- 8/3/2015: Learn and add Jena to project. Now app can read sample RDF file and get following properties: title, author fullname and year.

- 8/4/2015: Add this README file.

- 8/5/2015: Combine Jena and Elastic Search. Now app can index RDF file for following properties: title, author fullname and year. User can search keyword in title, and app will search for corresponding RDF entries.

			Add D3.js pie chart example to try clinet-side data processing, using JavaScript.
