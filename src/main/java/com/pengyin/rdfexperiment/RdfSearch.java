/**
 * 
 */
package com.pengyin.rdfexperiment;
import java.util.*;

import org.apache.jena.atlas.json.JsonBuilder;

import io.searchbox.client.*;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;

/**
 * @author Pengyin
 *
 */
public class RdfSearch {
	
		public static JestClient Init(){
			String connectionUrl ="https://paas:5cd731240d5ddaef6596cf82347a6d17@dwalin-us-east-1.searchly.com";
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig.Builder(connectionUrl).multiThreaded(true).build());		
			JestClient client = factory.getObject();
			Indexing(client);
			return client;
		}
		
		private static void Indexing(JestClient client){
			try {
				client.execute(new CreateIndex.Builder("publications").build());
				//Test Data
				Map<String, String> source = new LinkedHashMap<String,String>();
				source.put("hasAuthor", "PengyinShan");
				source.put("hasTitle", "TestTitle");
				source.put("editedBy", "Editor");
				source.put("hasDate", "2015");
				source.put("publishedBy", "Publisher");
				Index index = new Index.Builder(source).index("publications").type("publication").build();
				client.execute(index);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
