package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

// 占쏙옙占쏙옙 占쏙옙占싶부븝옙占쏙옙 占쏙옙占쏙옙 占실억옙占쏙옙占쏙옙 占쏙옙占쏙옙
/**
 * Servlet Filter implementation class LoginCheck
 */
@WebFilter("/LoginCheck")
public class LoginCheck implements Filter {
	/**
	 * Default constructor.
	 */
	static Logger logger = Logger.getLogger(LoginCheck.class);

	public LoginCheck() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// get session
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		boolean login = false;

		// path logging ...
		String path = ((HttpServletRequest) request).getRequestURI();
		logger.info("path ::" + path);

		if (session != null) {
			if (session.getAttribute("logincheck") != null) {
				login = true; //
			}
		}
		if (login) {
			if (path.equals("/NotFound/view/index.jsp") || path.equals("/NotFound/view/main.jsp")) {
				((HttpServletResponse) response).sendRedirect("/NotFound/main.do");
			}
			chain.doFilter(request, response);
		} else {
			if (path.equals("/NotFound/index.do") || path.equals("/NotFound/idcheck.do")
					|| path.equals("/NotFound/view/index.jsp")) {
				chain.doFilter(request, response);
			} else if (path.equals("/NotFound/view/check/Join_idcheck.jsp")) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).sendRedirect("/NotFound/view/index.jsp");
			}

		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
