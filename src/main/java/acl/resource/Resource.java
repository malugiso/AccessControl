package acl.resource;

import acl.access.AccessControlList;
import acl.access.AccessRight;
import acl.user.User;

public abstract class Resource {
	protected String name;
	protected Directory parent;

	protected AccessControlList acl;

	public Resource(String name) {
		this.name = name;
	}

	public void setACL(AccessControlList acl) {
		this.acl = acl;
	}

	public String accessBy(User usr) {
		String message = this.getContent();

		AccessRight readPrivilege = (acl != null) ? acl.getReadPrivilegeFor(usr) : AccessRight.UNSPECIFIED;

		if (readPrivilege.equals(AccessRight.DENIED)) {
			message = null;
		}
		if (readPrivilege.equals(AccessRight.UNSPECIFIED)) {
			if (this.parent == null) {
				message = null;
			} else {
				if (parent.accessBy(usr) == null) {
					message = null;
				}
			}
		}

		return message;
	}

	public String getName() {
		return this.name;
	}

	public abstract String getContent();

	protected void setParent(Directory parent) {
		this.parent = parent;
	}

}
