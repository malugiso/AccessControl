package acl.resource;

public class File extends Resource {
	private String content;

	public File(String name, String content) {
		super(name);
		this.content = content;
	}

	@Override
	public String getContent() {
		return this.content;
	}

}
