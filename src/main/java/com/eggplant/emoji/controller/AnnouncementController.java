package com.eggplant.emoji.controller;

import com.eggplant.emoji.entities.Announcement;
import com.eggplant.emoji.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/announcements")
    public String index(Model model) {
        model.addAttribute("announcements", announcementService.findAll());
        return "announcements";
    }

    /**
     * GET request that returns the addannouncement view used to add a new announcement
     * @param model model used to hold the new object to be created
     * @return addannouncement view used to create a new announcement
     */
    @GetMapping("/announcement/add")
    public String getAddAnnouncement(Model model){
        model.addAttribute("announcement", new Announcement());
        return "addannouncement";
    }

    /**
     * GET request that returns the editannouncement view used to edit an existing announcement
     * @param announcementId id for the existing announcement object to be edited in the DB
     * @param model model used to hold the new object to be created
     * @return editannouncement view used to edit an announcement
     */
    @GetMapping("/announcement/edit")
    public String getEditAnnouncement(@RequestParam("id") Long announcementId, Model model){
        model.addAttribute("announcement", announcementService.findById(announcementId));
        return "editannouncement";
    }

    /**
     * Received POST requests and adds the received announcement object to the database
     * @param announcement new announcement object to be added to DB
     * @param model model used to send the list of announcements to the view
     * @return the announcements view to display all the announcements
     */
    @PostMapping("/announcement/add")
    @Transactional
    public ModelAndView postAddAnnouncement(@ModelAttribute Announcement announcement, Model model){
        announcementService.addAnnouncement(announcement);
        model.addAttribute("announcements", announcementService.findAll());
        return new ModelAndView("redirect:/announcements", (ModelMap) model);
    }

    /**
     * POST request that updates the received announcement object in the database
     * @param announcement announcement object to be updated in the DB
     * @param model model used to send the list of announcements to the view
     * @return the announcements view to display all the announcements
     */
    @PostMapping("/announcement/edit")
    @Transactional
    public ModelAndView postEditAnnouncement(@ModelAttribute Announcement announcement, Model model){
        announcementService.updateAnnouncement(announcement);
        model.addAttribute("announcements", announcementService.findAll());
        return new ModelAndView("redirect:/announcements", (ModelMap) model);
    }

    /**
     * Received POST requests and delete the announcement that was clicked
     * @param request HttpServletRequest request from the input field of announcement id
     * @param model model used to send the list of announcements to the view
     * @return the announcements to display all the announcements
     */
    @PostMapping("/announcement/delete")
    @Transactional
    public ModelAndView removeAnnouncement(HttpServletRequest request, Model model){
        Long announcementId = Long.parseLong(request.getParameter("id"));
        announcementService.removeAnnouncementById(announcementId);
        model.addAttribute("announcements", announcementService.findAll());
        return new ModelAndView("redirect:/announcements", (ModelMap) model);
    }
}
