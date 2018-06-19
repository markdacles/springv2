package com.exist.ecc.controllers;

import com.exist.ecc.model.Project;
import com.exist.ecc.model.Personnel;
import com.exist.ecc.service.ProjectService;
import com.exist.ecc.service.PersonnelService;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.propertyeditors.CustomDateEditor;

@Controller
@RequestMapping(value="/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private PersonnelService personnelService;

	@InitBinder
    protected void dataBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
            dateFormat, true));
	}

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView listProject () {
		ModelAndView mav = new ModelAndView("projectIndex");
		List<Project> projectList = projectService.listProject();
		mav.addObject("projectList", projectList);
		mav.addObject("pact", "manj");
		return mav;
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView addProject () {
		ModelAndView mav = new ModelAndView("projectForm");
		mav.addObject("personnelList", personnelService.listPersonnel());
		mav.addObject("pact", "addj");
		String url = "project/add?";
		mav.addObject("url", url);
		mav.addObject("project", new Project());
		return mav;
	}

	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public ModelAndView deleteRole (@RequestParam(value="pid", required=true) long id) {
		ModelAndView mav = new ModelAndView("redirect:/project/list");
		projectService.deleteProject(id);
		return mav;
	}

	@RequestMapping(value="/update", method=RequestMethod.GET)
	public ModelAndView updateRole (@RequestParam(value="pid", required=true) long id) {
		ModelAndView mav = new ModelAndView("projectForm");
        Project p = projectService.findById(id);
        List<Personnel> available = personnelService.listPersonnel();
        List<Personnel> assigned = new ArrayList<Personnel>(p.getPersonnel());
        available.removeAll(assigned);
        mav.addObject("project", p);
        mav.addObject("personnelList", available);
        mav.addObject("assigned", assigned);
		mav.addObject("pact", "updatej");
		String url = "personnel/update?pid=" + id + "&";
		mav.addObject("url", url);
		return mav;
	}

	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView saveRole (@ModelAttribute("project") Project project, BindingResult result,
		@RequestParam(value="pid", required=false) Long pid,
		@RequestParam(value="lstBox2", required=false) String[] assigned) {

		if(assigned != null){
			for(String id : assigned){
				Personnel p = personnelService.findById(Long.parseLong(id));
				project.getPersonnel().add(p);
			}
		} else if(assigned == null) {
			project.setPersonnel(Collections.emptySet());
		}

		if (pid == null) {
			projectService.addProject(project);	
		}
		else {
			project.setProjectId(pid);
			projectService.updateProject(project);	
		}
        
		return new ModelAndView("redirect:/project/list");
	}
}