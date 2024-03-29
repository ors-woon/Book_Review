package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.UserDao;
import db.DB_inp;
import filter.LoginCheck;

/**
 * Servlet implementation class JoinController
 */

public class IdcheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(IdcheckController.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IdcheckController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json");
	    
	    PrintWriter out = response.getWriter();
	
		String id = request.getParameter("id");
		logger.info("input id check"+id);
		if (!(id.length() == 0 && id == null)) {
			UserDao userdao = new UserDao();
			if (userdao.user_check(id)) {
				logger.info("OK");
				out.write(" { \"check\" : \"ok\" }");
			} else {
				out.write("{ \"check\" : \"false\" } ");
				logger.info("False");
			}
		
		}
		out.flush();
		out.close();
	}

}
