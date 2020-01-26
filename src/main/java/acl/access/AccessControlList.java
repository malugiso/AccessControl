/*
 * Project and Training 1 - Computer Science, Berner Fachhochschule
 */

package acl.access;

import java.util.ArrayList;

import acl.user.User;

public class AccessControlList {
	ArrayList<AccessControlEntity> acl = new ArrayList<AccessControlEntity>();

	public void add(AccessControlEntity ace) {
		acl.add(ace);
	}

	public AccessRight getReadPrivilegeFor(User usr) {
		AccessRight res = AccessRight.UNSPECIFIED;

		ArrayList<AccessControlEntity> useracl = new ArrayList<AccessControlEntity>();
		useracl = findUserEntries(usr);

		if (useracl.equals(null)) {
			return res;
		}

		for (int i = 0; i < useracl.size(); i++) {
			AccessRight tmpAr = useracl.get(i).getReadPrivilege();

			/*
			 * if (tmpAr.equals(AccessRight.UNSPECIFIED)) { if
			 * (res.equals(AccessRight.DENIED) || res.equals(AccessRight.GRANTED)) {
			 * continue; } res = AccessRight.UNSPECIFIED; }
			 */
			if (tmpAr.equals(AccessRight.GRANTED)) {
				if (res.equals(AccessRight.DENIED)) {
					continue;
				}
				res = AccessRight.GRANTED;
			}
			if (tmpAr.equals(AccessRight.DENIED)) {
				res = AccessRight.DENIED;
			}
		}
		return res;
	}

	private ArrayList<AccessControlEntity> findUserEntries(User usr) {
		ArrayList<AccessControlEntity> useracl = new ArrayList<AccessControlEntity>();

		for (int i = 0; i < acl.size(); i++) {
			User tmpUsr = acl.get(i).getPrincipal();
			if (!tmpUsr.equals(null) && tmpUsr.getName().equals(usr.getName())) {
				useracl.add(acl.get(i));
			}
		}

		return useracl;
	}

}