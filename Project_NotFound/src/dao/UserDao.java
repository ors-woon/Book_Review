package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import bean.User;
import db.DB_TemQuery;
import db.DB_TemUpdate;
import db.DB_inp;

public class UserDao {
	private DB_inp dbset = null;

	public UserDao() {
		this.dbset = new DB_inp();
	}

	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	static Logger logger = Logger.getLogger(UserDao.class);

	public void user_insert(User user) {
		insert("insert into user values(?,?,?);", user);
	}

	public void user_deleteAll() {
		delete("delete from user;");
	}

	public List<Map<String, String>> user_login(String email) {
		StringBuilder strbuild = new StringBuilder("select * from user where email =");
		strbuild.append("'").append(email).append("'");
		return login(strbuild.toString());
	}

	public boolean user_check(String email) {
		StringBuilder strbuild = new StringBuilder("select * from user where email =");
		strbuild.append("'").append(email).append("'");
		return idcheck(strbuild.toString());
	}

	
	
	private boolean idcheck(final String query) {
		DB_TemQuery db_tmp = new DB_TemQuery() {
			@Override
			public ResultSet QueryTemplate(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(query);
				logger.info(new Timestamp(System.currentTimeMillis()) + " :: " + query);
				ResultSet rs = pstmt.executeQuery();
				return rs;
			}
		};
		return dbset.Template_Query(dbset.dbinit(), db_tmp).size() == 0 ? true : false;
	}

	private void insert(final String query, final User user) {
		DB_TemUpdate db_tmp = new DB_TemUpdate() {
			@Override
			public PreparedStatement QueryTemplate(Connection conn) throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, user.getUseremail());
				pstmt.setString(2, user.getUserpd());
				pstmt.setString(3, user.getUsername());
				return pstmt;
			}
		};
		dbset.Template_Update(dbset.dbinit(), db_tmp);
	}

	private void delete(final String query) {
		DB_TemUpdate db_tmp = new DB_TemUpdate() {
			@Override
			public PreparedStatement QueryTemplate(Connection conn) throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(query);
				return pstmt;
			}

		};
		dbset.Template_Update(dbset.dbinit(), db_tmp);
	}

	private List<Map<String, String>> login(final String query) {
		DB_TemQuery db_tmp = new DB_TemQuery() {
			@Override
			public ResultSet QueryTemplate(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(query);
				logger.info(new Timestamp(System.currentTimeMillis()) + " :: " + query);
				ResultSet rs = pstmt.executeQuery();
				return rs;
			}
		};
		return dbset.Template_Query(dbset.dbinit(), db_tmp);
	}

	//  method dosen't suitable for this class
	public void jsback(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('what ? nope!');");
		out.println("history.back();");
		out.println("</script>");

	}

}
