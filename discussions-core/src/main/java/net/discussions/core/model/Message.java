package net.discussions.core.model;

public class Message {
	
	private String content;
	
	public Message(String message) {
		this.content = message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
