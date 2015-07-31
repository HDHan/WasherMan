/*
 * Controller for handling User data
 */

package kr.ac.ajou.lazybones.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.ajou.lazybones.managers.Authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	@Autowired
	private Authenticator userEntityManager;

	/*
	 * Show Login page.
	 */
	@RequestMapping(value = "/User/Login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {

		String credential = (String) request.getSession().getAttribute("credential");

		if (credential != null)
			return "redirect:/";

		return "login";
	}

	/*
	 * Process Login
	 */
	@RequestMapping(value = "/User/Login", method = RequestMethod.POST)
	public String processLogin(@RequestParam(value = "id") String id, @RequestParam(value = "password") String pwd,
			HttpServletRequest request, Model model) {
		try {
			String credential = userEntityManager.getCredential(id, pwd);

			if (credential != null) {
				request.getSession().setAttribute("credential", credential);
				request.getSession().setAttribute("logininfo", true);
				request.getSession().setAttribute("userid", id);
				
				return "redirect:/Node/List";

			} else {
				model.addAttribute("reason", "Login failed. Try again.");
				return "login";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "login";
	}

	/*
	 * Logout Process
	 */
	@RequestMapping(value = "/User/Logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			// deleting session
			request.getSession().invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

}