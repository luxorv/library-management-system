package com.gcit.lms;

import com.gcit.lms.model.*;
import com.gcit.lms.service.BookService;
import com.gcit.lms.service.BranchService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/branches")
public class BranchController {

    @Autowired
    BranchService branchService;

    private boolean hasOperated;

    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String query(@RequestParam("q") String query, @RequestParam("pageNo") Integer pageNo) {

        query = query.trim();

        ArrayList<LibraryBranch> branches = branchService.getAllBranches(query, pageNo);
        ArrayList<JSONObject> formattedBranches = branchService.getBranchesInfo(branches);

        JSONObject response = new JSONObject();

        response.put("branches", formattedBranches);
        response.put("pageNo", pageNo);
        response.put("fetchSize", branchService.getBranchFetchSize());

        if(branchService.getAlert() != null) {
            response.put("alert", branchService.getAlert());
        }

        if(this.hasOperated) {
            branchService.setAlert("");
            this.hasOperated = false;
        }

        return response.toString();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "branches/list";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView detail(@RequestParam("branchId") Integer branchId) {

        Map<String, Object> result = new HashMap<>();
        LibraryBranch branch = new LibraryBranch();

        branch.setBranchId(branchId);
        branch = branchService.getBranch(branch);

        result.put("branch", branch);

        return new ModelAndView("branches/view", result) ;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RedirectView save(@RequestParam("branchName") String branchName,
                             @RequestParam("branchAddress") String branchAddress,
                             @RequestParam("branchId") Integer branchId) {

        System.out.print("branchId: " + branchId);
        System.out.print(" branchName: " + branchName);
        System.out.print(" branchAddress: " + branchAddress + "\n");

        LibraryBranch branch = this.fillBranch(branchName, branchAddress, branchId);

        branchService.createBranch(branch);
        branchService.setAlert("Good");

        this.hasOperated = true;

        return new RedirectView("/lms/branches?redirected=true");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RedirectView update(@RequestParam("branchName") String branchName,
                               @RequestParam("branchAddress") String branchAddress,
                               @RequestParam("branchId") Integer branchId) {

        LibraryBranch branch = this.fillBranch(branchName, branchAddress, branchId);

        branchService.updateBranch(branch);
        branchService.setAlert("Good");

        this.hasOperated = true;

        return new RedirectView("/lms/branches?redirected=true");
    }

    private LibraryBranch fillBranch(String branchName, String branchAddress, Integer branchId) {
        LibraryBranch branch = new LibraryBranch();

        if(branchId != null && branchId > 0) {
            branch.setBranchId(branchId);
        }

        branch.setBranchName(branchName);
        branch.setBranchAddress(branchAddress);

        return branch;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RedirectView delete(@RequestParam("branchId") Integer branchId) {

        LibraryBranch branch = new LibraryBranch();
        branch.setBranchId(branchId);

        branchService.setAlert("Good");
        branchService.deleteBranch(branch);

        this.hasOperated = true;

        return new RedirectView("/lms/branches?redirected=true");
    }
}
