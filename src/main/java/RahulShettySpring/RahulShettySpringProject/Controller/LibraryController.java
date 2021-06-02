package RahulShettySpring.RahulShettySpringProject.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import RahulShettySpring.RahulShettySpringProject.Repository.LibraryRepository;
import RahulShettySpring.RahulShettySpringProject.service.LibraryService;

@RestController
public class LibraryController {

	@Autowired
	LibraryRepository libraryRepository;

	@Autowired
	AddBookResponse addBookResponse;

	@Autowired
	LibraryService libraryService;

	private static final Logger logger=LoggerFactory.getLogger(LibraryController.class);


	@PostMapping("/addBook")
	public ResponseEntity addBookImplementation(@RequestBody Library library){
	//	library.setId(library.getIsbn() + library.getAisle());
		String id=libraryService.buildID(library.getIsbn(),library.getAisle());
		if(!libraryService.checkBookAlreadyExists(id)){
			logger.info("Book does not exist hence creating one");
			libraryRepository.save(library);
			addBookResponse.setMssg("Book Added susccesfully");
			addBookResponse.setId(library.getIsbn() + library.getAisle());
			HttpHeaders headers=new HttpHeaders();
			headers.add("id",library.getIsbn() + library.getAisle());
			return new ResponseEntity<AddBookResponse>(addBookResponse,headers, HttpStatus.CREATED);
		}
		else{
			addBookResponse.setMssg("Book already exist");
			addBookResponse.setId(id);
			return new ResponseEntity<AddBookResponse>(addBookResponse, HttpStatus.ACCEPTED);
		}
	}
	@GetMapping("/getBooks/{id}")
	public Library getBooks(@PathVariable(value="id") String id){
         try {
	         Library libra = libraryRepository.findById(id).get();
	         return libra;
         }
         catch (Exception e){
         	throw new ResponseStatusException(HttpStatus.NOT_FOUND);
         }
	}

	@GetMapping("getBooks/author")
	public List<Library> getBooksByAuthor(@RequestParam(value="authorname") String authorname){
		try {
			List<Library> libra = libraryRepository.findAllByAuthor(authorname);
			return libra;
		}
		catch (Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/updateBook/{id}")
	public ResponseEntity<Library> updateBookByID(@PathVariable(value = "id") String id, @RequestBody Library library){

			Library library1=libraryRepository.findById(id).get();
			library1.setBookName(library.getBookName());
			library1.setIsbn(library.getIsbn());
			libraryRepository.save(library1);
			return new ResponseEntity<Library>(library1,HttpStatus.OK);

	}

	@DeleteMapping("/deleteBook")
	public String deleteBookByID(@RequestBody Library library){
		try {

			Library library1=libraryRepository.findById(library.getId()).get();
			libraryRepository.delete(library1);
			logger.info("Book is Deleted");
			return "Book is successfully deleted";
		}
		catch (Exception e){
			return "Book is not found to be deleted";
		}

	}
}
