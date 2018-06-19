package com.exist.ecc.controllers;

import com.exist.ecc.model.Roles;
import com.exist.ecc.service.RoleService;

import java.util.List;
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

@Controller
@RequestMapping(value="/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView listRoles () {
		ModelAndView mav = new ModelAndView("roleIndex");
		List<Roles> roleList = roleService.listRoles();
		mav.addObject("roleList", roleList);
		mav.addObject("roles", new Roles());
		return mav;
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView addRole () {
		ModelAndView mav = new ModelAndView("roleIndex");
		List<Roles> rolelist = roleService.listRoles();
		Collections.sort(rolelist, (Roles a1, Roles a2) -> a1.getRoleId().compareTo(a2.getRoleId()) );
		mav.addObject("rolelist", rolelist);
		return mav;
	}

	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public ModelAndView deleteRole (@RequestParam(value="roleid", required=true) long id) {
		ModelAndView mav = new ModelAndView("redirect:/role/list");
		roleService.deleteRole(id);
		return mav;
	}

	@RequestMapping(value="/update", method=RequestMethod.GET)
	public ModelAndView updateRole (@RequestParam(value="roleid", required=true) long id) {
		ModelAndView mav = new ModelAndView("updateRoleForm");
		Roles r = roleService.findById(id);
		mav.addObject("r",r);
		mav.addObject("roles",r);
		mav.addObject("roleid",id);
		return mav;
	}

	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView saveRole (@ModelAttribute("roles") Roles r, BindingResult result,
		@RequestParam(value="roleid", required=false) Long id) {

		if (id == null) {
			roleService.addRole(r);	
		}
		else {
			r.setRoleId(id);
			roleService.updateRole(r);
		}
        
		return new ModelAndView("redirect:/role/list");
	}
}