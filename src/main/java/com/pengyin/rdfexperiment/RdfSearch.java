/**
 * 
 */
package com.pengyin.rdfexperiment;
import io.searchbox.client.*;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;

/**
 * @author Pengyin
 *
 */
public class RdfSearch {
	
		public static JestClient Init(){
			String connectionUrl = System.getenv("http://paas:5cd731240d5ddaef6596cf82347a6d17@dwalin-us-east-1.searchly.com");
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig.Builder(connectionUrl).multiThreaded(true).build());
			JestClient client = factory.getObject();
			Indexing(client);
			return client;
		}
		
		private static void Indexing(JestClient client){
			try {
				client.execute(new CreateIndex.Builder("publications").build());
				Index index = new Index.Builder(CreateRdfModel("Test Title", "2015", "Pengyin Shan", "Editor", "Publisher")).index("publications").type("publication").build();
				client.execute(index);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private static RdfModel CreateRdfModel(String title, String date, String author, String editedBy, String publishedBy){
			RdfModel result = new RdfModel();
			result.setHasAuthor(author);
			result.setHasTitile(title);
			result.setEditedBy(editedBy);
			result.setHasDate(date);
			result.setPublishedBy(publishedBy);
			return result;
		}
}
