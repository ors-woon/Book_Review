package test;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import bean.User;
import dao.UserDao;

public class Index_DBTest {
	static Logger logger = Logger.getLogger(Index_DBTest.class);
/*
	@Test
	public void SHA() {
		String base = "password123";

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < 10; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			// 출력
			System.out.println(hexString.toString());

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	// bcrypt test ...
	@Test
	public void BCrypts() {
		// password sample
		String password = "!@#$password1234";

		// create hash , passwordHashed is 60byte. gensalt default value 10
		String passwordHashed = BCrypt.hashpw(password, BCrypt.gensalt());
		// if password hash value equals passwordHashed true / else false
		boolean isValidPassword = BCrypt.checkpw(password, passwordHashed);

		System.out.println(passwordHashed);

	}*/

	// 데이터 넣고 로그인되는지 테스트 해보자
	@Test
	public void insertTest() {
		UserDao dao = new UserDao();
		dao.user_deleteAll();
		String password = "test";
		String passwordHashed = BCrypt.hashpw(password, BCrypt.gensalt());
		User user = new User("test", passwordHashed, "name");
		dao.user_insert(user);
		List<Map<String, String>> list = dao.user_login("test");
		System.out.println( BCrypt.checkpw(password,list.get(0).get("pass")));

	}

}
