package RahulShettySpring.RahulShettySpringProject.Controller;

import org.springframework.stereotype.Component;

@Component
public class AddBookResponse {

	public String getMssg() {
		return mssg;
	}

	public void setMssg(String mssg) {
		this.mssg = mssg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String mssg;
	private String id;
}
