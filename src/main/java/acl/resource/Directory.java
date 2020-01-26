package acl.resource;

import java.util.ArrayList;

public class Directory extends Resource {

	ArrayList<Resource> children = new ArrayList<Resource>();

	public Directory(String name) {
		super(name);
	}

	public void add(Resource res) {
		res.setParent(this);
		children.add(res);
	}

	@Override
	public String getContent() {
		String message = "";
		for (int i = 0; i < children.size(); i++) {
			message += children.get(i).getName() + "\n";
		}
		return message;
	}

}
