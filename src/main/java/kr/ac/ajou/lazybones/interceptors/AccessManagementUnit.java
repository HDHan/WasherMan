/*
 * Login Intercepter.
 */

package kr.ac.ajou.lazybones.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AccessManagementUnit extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			// For testing
			String credential = (String) request.getSession().getAttribute("credential");

			// Get session --> if null --> Return to Login page.
			if (credential == null) {
				response.sendRedirect("/User/Login");
				return false;
			}
			
			System.out.println("LoginInterceptor called. Credential: " + credential);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}