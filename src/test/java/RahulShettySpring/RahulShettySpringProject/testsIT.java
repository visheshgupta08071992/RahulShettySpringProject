package RahulShettySpring.RahulShettySpringProject;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import RahulShettySpring.RahulShettySpringProject.Controller.Library;

@SpringBootTest
public class testsIT {

	@Test
	public void getAuthorNameBooksTest() throws JSONException {

		String expected="[{\"id\":\"abcd4\",\"isbn\":\"abcd\",\"aisle\":4,\"author\":\"Rahul\",\"bookName\":\"Cypress\"},{\"id\":\"fdsefr343\",\"isbn\":\"fdsefr3\",\"aisle\":43,\"author\":\"Rahul\",\"bookName\":\"Devops\"}]";
		TestRestTemplate testRestTemplate=new TestRestTemplate();
		ResponseEntity<String> response=testRestTemplate.getForEntity("http://localhost:8080/getBooks/author" +
				"?authorname=Rahul",String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expected,response.getBody(),false);
	}

	//Post request using Test Rest Template
	@Test
	public void addBookTest(){
		TestRestTemplate testRestTemplate=new TestRestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Library> request=new HttpEntity<Library>(buildLibrary(),headers);
		ResponseEntity<String> response=testRestTemplate.postForEntity("http://localhost:8080/addBook",request,
				String.class);
		System.out.println(response.getBody());
	}

	public Library buildLibrary(){
		Library library=new Library();
		library.setIsbn("def");
		library.setBookName("oracle");
		library.setAuthor("Vishesh");
		library.setAisle(567);
		library.setId("kjh123");
		return library;
	}
}
