package com.gcit.lms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.gcit.lms.model.LibraryBranch;
import com.gcit.lms.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	BranchService branchService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "welcome";
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Locale locale, Model model) {
		return "admin";
	}

	@RequestMapping(value = "/borrower", method = RequestMethod.GET)
	public ModelAndView borrower(Locale locale, Model model) {

		Map<String, ArrayList<LibraryBranch>> branches = new HashMap<>();
		branches.put("branches", branchService.getAllBranches("", 0));

		return new ModelAndView("borrower", branches);
	}

}
