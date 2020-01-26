package acl.access;

import acl.user.User;

public class AccessControlEntity {
	private AccessRight pt;
	private User usr;

	public AccessControlEntity(User usr, AccessRight pt) {
		this.pt = pt;
		this.usr = usr;
	}

	protected User getPrincipal() {
		return usr;
	}

	protected AccessRight getReadPrivilege() {
		return pt;
	}

}
