package RahulShettySpring.RahulShettySpringProject.Controller;

import org.springframework.stereotype.Component;

@Component
public class Greeting {

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private long id;
	private String content;
}
