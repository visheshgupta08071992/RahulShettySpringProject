package RahulShettySpring.RahulShettySpringProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import RahulShettySpring.RahulShettySpringProject.Controller.AddBookResponse;
import RahulShettySpring.RahulShettySpringProject.Controller.Library;
import RahulShettySpring.RahulShettySpringProject.Controller.LibraryController;
import RahulShettySpring.RahulShettySpringProject.Repository.LibraryRepository;
import RahulShettySpring.RahulShettySpringProject.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class RahulShettySpringProjectApplicationTests {

	@Autowired
	LibraryService libraryService;

	@Autowired
	LibraryController libraryController;

	@MockBean
	LibraryService libService;

	@MockBean
	LibraryRepository libraryRepository;

	@MockBean
	Library library;

	@Autowired
	MockMvc mockMvc;


	@Test
	void contextLoads() {
	}

	@Test
	public void checkBuildIDLogic(){
		String id=libraryService.buildID("zMAN",24);
		assertEquals(id,"OLDzMAN24");
		String id1=libraryService.buildID("MAN",24);
		assertEquals(id1,"MAN24");
	}


	//unit test
	@Test
	public void addBookTestWhenBookDoesNotExist(){

		//Mock
		Library lib=buildLibrary();
		when(libService.buildID(lib.getIsbn(),lib.getAisle())).thenReturn("abc123");
		when(libService.checkBookAlreadyExists("abc123")).thenReturn(false);

		ResponseEntity response=libraryController.addBookImplementation(buildLibrary());
		AddBookResponse ad= (AddBookResponse) response.getBody();
		//Verifyies that libraryRepository method is called once
		verify(libraryRepository,times(1)).save(any());
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertEquals(ad.getId(),"abc123");
		assertEquals(ad.getMssg(),"Book Added susccesfully");
	}

	//unit test
	@Test
	public void addBookTestWhenBookExist(){

		//Mock
		Library lib=buildLibrary();
		when(libService.buildID(lib.getIsbn(),lib.getAisle())).thenReturn("abc123");
		when(libraryService.checkBookAlreadyExists("abc123")).thenReturn(true);

		ResponseEntity response=libraryController.addBookImplementation(buildLibrary());

		AddBookResponse ad= (AddBookResponse) response.getBody();

		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
		assertEquals(ad.getId(),"abc123");
		assertEquals(ad.getMssg(),"Book already exist");
	}

	//unit test
	@Test
	public void getBooksByIDTest(){
		//mock
		Library lib=buildLibrary();
		Optional<Library> libOptional = Optional.of(lib);
		when(libraryRepository.findById("abc123")).thenReturn(libOptional);
		//when(libOptional.get()).thenReturn(lib);
		Library lib1=libraryController.getBooks("abc123");
		assertEquals(lib1.getId(),lib.getId());
		assertEquals(lib1.getIsbn(),lib.getIsbn());
		assertEquals(lib1.getAisle(),lib.getAisle());
		assertEquals(lib1.getAuthor(),lib1.getAuthor());
	}

	//unit test
	@Test
	public void getBooksByAuthorTest(){
		//mock
		Library lib=buildLibrary();
		List<Library> libList =new ArrayList<>();
		libList.add(lib);
        when(libraryRepository.findAllByAuthor("Vishesh")).thenReturn(libList);
		List<Library> libra =libraryController.getBooksByAuthor("Vishesh");
		assertEquals(libList.get(0).getId(),libra.get(0).getId());
	}

	public Library buildLibrary(){
		Library library=new Library();
		library.setIsbn("abc");
		library.setBookName("oracle");
		library.setAuthor("Vishesh");
		library.setAisle(123);
		library.setId("abc123");
		return library;
	}

	@Test
	public void updateBookByIDTest(){
		//mock
		Library lib1=buildLibrary();
		Library lib2=buildLibrary();
		lib2.setBookName("java");
		lib2.setAisle(321);

		Optional<Library> libOptional = Optional.of(lib1);
		when(libraryRepository.findById("abc123")).thenReturn(libOptional);

		ResponseEntity<Library> response=libraryController.updateBookByID("abc123",lib2);
		verify(libraryRepository,times(1)).save(lib1);

		assertEquals(lib2.getBookName(),lib1.getBookName());
		assertEquals(lib2.getAisle(),lib2.getAisle());
	}

	@Test
	public void deleteBookByIDTest(){
		Library lib1=buildLibrary();
		Library lib2=buildLibrary();
		lib2.setId("abc123");

		Optional<Library> libOptional = Optional.of(lib2);
		when(libraryRepository.findById(lib2.getId())).thenReturn(libOptional);

		String response=libraryController.deleteBookByID(lib2);
		verify(libraryRepository,times(1)).delete(lib2);
		assertEquals(response,"Book is successfully deleted");
	}



	//MockMVC Test Begins

	@Test
	public void addBookControllerTest() throws Exception {
		Library lib=buildLibrary();
		ObjectMapper map=new ObjectMapper();
		String request=map.writeValueAsString(lib);
		when(libService.buildID(lib.getIsbn(),lib.getAisle())).thenReturn("abc123");
		when(libraryService.checkBookAlreadyExists("abc123")).thenReturn(true);
		this.mockMvc.perform(post("/addBook").contentType(MediaType.APPLICATION_JSON).content(request)).andDo(print()).andExpect(status().isAccepted())
				.andExpect( jsonPath("$.id").value(lib.getId()));
	}

	@Test
	public void getBooksByAuthorControllerTest() throws Exception {
		Library lib=buildLibrary();
		List<Library> libList =new ArrayList<>();
		libList.add(lib);
		when(libraryRepository.findAllByAuthor("Vishesh")).thenReturn(libList);
		mockMvc.perform(get("/getBooks/author").contentType(MediaType.APPLICATION_JSON).queryParam("authorname",
				"Vishesh")).andDo(print()).andExpect(status().is2xxSuccessful())
		            .andExpect(jsonPath("$[0].id").value(lib.getId()));
	}

	@Test
	public void updateBookByIDControllerTest() throws Exception {
		Library lib1=buildLibrary();
		Library lib2=buildLibrary();
		lib2.setBookName("java");
		lib2.setIsbn("hero");

		Optional<Library> libOptional = Optional.of(lib1);
		when(libraryRepository.findById("abc123")).thenReturn(libOptional);
		ObjectMapper map=new ObjectMapper();
		String request=map.writeValueAsString(lib2);
		mockMvc.perform(put("/updateBook/"+lib1.getId()).contentType(MediaType.APPLICATION_JSON).content(request))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(lib2.getId())).andExpect(jsonPath("$" +
				".isbn").value(lib2.getIsbn()))
		.andExpect(content().json("{\"id\":\"abc123\",\"isbn\":\"hero\",\"aisle\":123,\"author\":\"Vishesh\",\"bookName\":\"java\"}"));
	}

	@Test
	public void deleteBookByIDControllerTest() throws Exception {

		Library lib1=buildLibrary();
		Library lib2=buildLibrary();
		lib2.setId("abc123");

		ObjectMapper objectMapper=new ObjectMapper();
		String jsonBody=objectMapper.writeValueAsString(lib2);

		Optional<Library> libraryOptional=Optional.of(lib1);
		when(libraryRepository.findById(lib2.getId())).thenReturn(libraryOptional);

		mockMvc.perform(delete("/deleteBook").contentType(MediaType.APPLICATION_JSON).content(jsonBody)).andDo(print()).andExpect(status().is2xxSuccessful()).andExpect(content().string("Book is successfully deleted"));
	}

}
