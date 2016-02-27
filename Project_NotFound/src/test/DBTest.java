package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import bean.User;
import dao.UserDao;

public class DBTest {
	static Logger logger = Logger.getLogger(DBTest.class);

	@Test
	public void insertTest() {
		UserDao dao = new UserDao();
		dao.user_deleteAll();
		User user = new User("lus", "pass", "name");
		dao.user_insert(user);
		assertThat(user.getUseremail(), is("lus"));
	}
	
	
	@Test
	public void login() {
		UserDao dao = new UserDao();
		dao.user_deleteAll();
		User user = new User("lus", "pass", "name");
		dao.user_insert(user);
		assertThat(user.getUseremail(), is("lus"));
		List<Map<String,String>> list =  dao.user_login("te");
		assertThat(list.get(0).get("email"), is(user.getUseremail()));
		
	}
}
