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

import dao.UserDao;
import db.DB_inp;

/**
 * Servlet implementation class JoinController
 */
@WebServlet("/JoinController")
public class IdcheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		String id = request.getParameter("id");
		if (!(id.length() == 0 && id == null)) {
			UserDao userdao = new UserDao();
			if (userdao.user_check(id)) {
				request.setAttribute("check", 1);
			} else {
				request.setAttribute("check", 2);
			}
			RequestDispatcher rd = request.getRequestDispatcher("/view/check/Join_idcheck.jsp");
			rd.forward(request, response);
		}
	}

}
