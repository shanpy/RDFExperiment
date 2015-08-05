/**
 * 
 */
package com.pengyin.rdfexperiment;
import java.util.*;

import io.searchbox.client.*;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;

/**
 * @author Pengyin
 *
 */
public class RdfSearch {
	
		public static JestClient Init(){
			String connectionUrl ="https://paas:29ec3c61c6e94f01f8a5979574ca53da@dwalin-us-east-1.searchly.com";
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig.Builder(connectionUrl).multiThreaded(true).build());		
			JestClient client = factory.getObject();
			return client;
		}
		
		public static void Indexing(JestClient client, ArrayList<RdfModel> rms){
			try {
				client.execute(new CreateIndex.Builder("publications").build());
				//Indexing RDF Data
				for(int i=0; i<rms.size(); i++){
					Map<String, String> source = new LinkedHashMap<String,String>();
					RdfModel rm = rms.get(i);
					source.put("hasAuthor", rm.getHasAuthor());
					source.put("hasTitle", rm.getHasTitle());
					source.put("hasDate", rm.getHasDate());
					Index index = new Index.Builder(source).index("publications").type("publication").id(String.valueOf(i)).build();
					client.execute(index);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
