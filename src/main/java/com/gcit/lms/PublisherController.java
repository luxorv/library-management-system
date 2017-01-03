package com.gcit.lms;

import com.gcit.lms.model.*;
import com.gcit.lms.service.PublisherService;
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
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    PublisherService publisherService;

    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String query(@RequestParam("q") String query, @RequestParam("pageNo") Integer pageNo) {

        ArrayList<Publisher> publishers = publisherService.getAllPublishers(query, pageNo);
        ArrayList<JSONObject> formattedPublishers = publisherService.getPublishersInfo(publishers);

        JSONObject response = new JSONObject();

        response.put("publishers", formattedPublishers);
        response.put("pageNo", pageNo);
        response.put("fetchSize", publisherService.getPublisherFetchSize());

        if(publisherService.getAlert() != null) {
            response.put("alert", publisherService.getAlert());
        }

        return response.toString();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {

        if(request.getParameter("redirected") == null) {
            publisherService.setAlert("");
        }

        return "publishers/list";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView detail(@RequestParam("publisherId") Integer publisherId) {

        Map<String, Object> result = new HashMap<>();
        Publisher publisher = new Publisher();

        publisher.setPublisherId(publisherId);
        publisher = publisherService.getPublisher(publisher);

        result.put("publisher", publisher);

        return new ModelAndView("publishers/view", result) ;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RedirectView save(@RequestParam("publisherName") String publisherName,
                             @RequestParam("publisherAddress") String publisherAddress,
                             @RequestParam("publisherPhone") String publisherPhone,
                             @RequestParam("publisherId") Integer publisherId) {

        Publisher publisher = this.fillPublisher(
                publisherName,
                publisherAddress,
                publisherPhone,
                publisherId
        );

        publisherService.createPublisher(publisher);
        publisherService.setAlert("Good");

        return new RedirectView("/lms/publishers?redirected=true");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RedirectView update(@RequestParam("publisherName") String publisherName,
                               @RequestParam("publisherAddress") String publisherAddress,
                               @RequestParam("publisherPhone") String publisherPhone,
                               @RequestParam("publisherId") Integer publisherId) {

        Publisher publisher = this.fillPublisher(
                publisherName,
                publisherAddress,
                publisherPhone,
                publisherId);

        publisherService.updatePublisher(publisher);
        publisherService.setAlert("Good");

        return new RedirectView("/lms/publishers?redirected=true");
    }

    private Publisher fillPublisher(String publisherName,
                                    String publisherAddress,
                                    String publisherPhone,
                                    Integer publisherId) {

        Publisher publisher = new Publisher();

        if(publisherId != null && publisherId > 0) {
            publisher.setPublisherId(publisherId);
        }

        publisher.setPublisherName(publisherName);
        publisher.setPublisherAddress(publisherAddress);
        publisher.setPublisherPhone(publisherPhone);

        return publisher;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RedirectView delete(@RequestParam("publisherId") Integer publisherId) {

        Publisher publisher = new Publisher();
        publisher.setPublisherId(publisherId);

        publisherService.setAlert("Good");
        publisherService.deletePublisher(publisher);

        return new RedirectView("/lms/publishers?redirected=true");
    }
}
