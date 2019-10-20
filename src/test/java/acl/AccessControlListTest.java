package acl;

import org.junit.jupiter.api.Test;

import acl.access.AccessRight;
import acl.access.AccessControlEntity;
import acl.access.AccessControlList;
import acl.resource.Directory;
import acl.resource.File;
import acl.resource.Resource;
import acl.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;;

class AccessControlListTest {
	private User joe = new User("Joe");
	private User tom = new User("Tom");
	private Directory root = new Directory("root");
	private Directory docs = new Directory("docs");
	private Directory personal = new Directory("personal");
	private Resource letter = new File("letter", "Dear Bill, don't kill. Regards Joe");
	private AccessControlEntity grantJoe = new AccessControlEntity(joe, AccessRight.GRANTED);
	private AccessControlEntity denyJoe = new AccessControlEntity(joe, AccessRight.DENIED);
	private AccessControlList acl;
	
	// root
	//   \--docs
	//        |--letter
	//        \--personal
	@BeforeEach
	void setup() {
		root.add(docs);  
		docs.add(letter);
		docs.add(personal);
	}
	
	@Test
	void testNoACl() {
		assertNull(letter.accessBy(joe));
	}

	@Test
	void testNoACE() {
		acl = new AccessControlList();
		letter.setACL(acl);
		assertNull(letter.accessBy(joe));
	}

	@Test
	void testGrant() {
		acl = new AccessControlList();
		acl.add(grantJoe);
		letter.setACL(acl);
		assertEquals("Dear Bill, don't kill. Regards Joe", letter.accessBy(joe));
	}

	@Test
	void testGrantJoeTomNoAccess() {
		acl = new AccessControlList();
		acl.add(grantJoe);
		letter.setACL(acl);
		assertNull(letter.accessBy(tom));
	}

	@Test
	void testDeny() {
		acl = new AccessControlList();
		acl.add(denyJoe);
		letter.setACL(acl);
		assertNull(letter.accessBy(joe));;
	}
	
	@Test
	void testGrantDeny() {
		acl = new AccessControlList();
		acl.add(grantJoe);
		acl.add(denyJoe);
		letter.setACL(acl);
		assertNull(letter.accessBy(joe));
	}
	
	@Test
	void testDenyGrant() {
		acl = new AccessControlList();
		acl.add(denyJoe);
		acl.add(grantJoe);
		letter.setACL(acl);
		assertNull(letter.accessBy(joe));
	}
	
	@Test
	void testReadDirectory() {
		acl = new AccessControlList();
		acl.add(grantJoe);
		docs.setACL(acl);
		assertEquals("letter\npersonal\n", docs.accessBy(joe));;
	}

	@Test
	void testInheritGrant() {
		acl = new AccessControlList();
		acl.add(grantJoe);
		root.setACL(acl);
		assertEquals("Dear Bill, don't kill. Regards Joe", letter.accessBy(joe));;
	}
	
	@Test
	void testExplicitBeforeInherited() {
		acl = new AccessControlList();
		acl.add(denyJoe);
		root.setACL(acl);
		acl = new AccessControlList();
		acl.add(grantJoe);
		letter.setACL(acl);
		assertEquals("Dear Bill, don't kill. Regards Joe", letter.accessBy(joe));
	}
}