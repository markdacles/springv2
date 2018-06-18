package com.exist.controllers;

import com.exist.ecc.model.Roles;
import com.exist.ecc.model.Personnel;
import com.exist.ecc.model.Contact;
import com.exist.ecc.service.PersonnelService;
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

@Controller
@RequestMapping(value="/personnel")
public class PersonnelController {
	
	@Autowired
	private PersonnelService personnelService;
	
	@Autowired
	private RoleService roleService;

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView listPersonnel (
		@RequestParam(value="nameFilter", required=false) String nameFilter,
		@RequestParam(value="sortby", required=false) String sortby) {

		ModelAndView mav = new ModelAndView("personnelIndex");
		List<Personnel> personnelList = personnelService.listPersonnel();

		// if(nameFilter != null) {
		// 	personnelList = personnelService.searchFor(nameFilter);
		// }

		if("id".equals(sortby)) {
            Collections.sort(personnelList, (Personnel a1, Personnel a2) -> a1.getId().compareTo(a2.getId()) );
        } else if("name".equals(sortby)) {
            Collections.sort(personnelList, (Personnel a1, Personnel a2) -> a1.getName().getLname().compareTo(a2.getName().getLname()) );
        } else if("address".equals(sortby)) {
            Collections.sort(personnelList, (Personnel a1, Personnel a2) -> a1.getAddress().getBrgy().compareTo(a2.getAddress().getBrgy()) );
        } else if("bday".equals(sortby)) {
            Collections.sort(personnelList, (Personnel a1, Personnel a2) -> a1.getBirthday().compareTo(a2.getBirthday()) );
        } else if("gwa".equals(sortby)) {
            Collections.sort(personnelList, (Personnel a1, Personnel a2) -> a1.getGwa().compareTo(a2.getGwa()) );
        } else if("datehired".equals(sortby)) {
            Collections.sort(personnelList, (Personnel a1, Personnel a2) -> a1.getDateHired().compareTo(a2.getDateHired()) );
        }

		mav.addObject("personnelList", personnelList);
		mav.addObject("pact", "manp");
		return mav;

	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView addPersonnel () {
		ModelAndView mav = new ModelAndView("personnelForm");
        List<Roles> roleList = roleService.listRoles();
		mav.addObject("roleList", roleList);
		mav.addObject("pact", "add");
		String url = "personnel/list/?";
		mav.addObject("url", url);
		mav.addObject("personnel", new Personnel());
		return mav;
	}

	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView savePersonnel (@ModelAttribute("personnel") Personnel p, BindingResult result,
		@RequestParam(value="contactType", required=false) String[] cType,
		@RequestParam(value="contactDetails", required=false) String[] cDetails,
		@RequestParam(value="checkedRoles", required=false) String[] cRoles) {

		ModelAndView mav = new ModelAndView("redirect:/personnel/list");
		
		if(cDetails != null){
			for(int i = 0; i < cDetails.length; i++){
				Contact c = new Contact();
				c.setContactType(cType[i]);
				c.setContactDetails(cDetails[i]);
				p.getContact().add(c);
			}
		}

		if (cRoles != null) {
            for (String id : cRoles) {
               Roles role = roleService.findById(Long.parseLong(id), "Roles");
               p.getRoles().add(role);
            }
        }
        

		if (p.getId() == 0) {
			personnelService.addPersonnel(p);
		}
		else {
			personnelService.updatePersonnel(p);
		}
		return mav;
	}

	// @RequestMapping(value="/delete", method=RequestMethod.GET)
	// public ModelAndView deletePerson(@RequestParam(value="personId", required=true) long id) {
	// 	personService.deletePerson(id);
	// 	ModelAndView modelAndView = new ModelAndView("redirect:/person/list");
	// 	modelAndView.addObject("prompt", "Successfully deleted a Person!");
	// 	return modelAndView;
	// }

	// @RequestMapping(value="/update", method=RequestMethod.GET)
	// public ModelAndView updatePerson(@RequestParam(value="personId", required=true) long id) {
	// 	Person person = personService.getPersonById(id);
	// 	if (person.getContactInformation() == null) {
	// 		person.setContactInformation(new ContactInformation());
	// 	}
	// 	ModelAndView modelAndView = new ModelAndView("personForm");
	// 	List<Role> roles = roleService.listRoles();
	// 	person.setPassword("");
	// 	modelAndView.addObject("person", person);
	// 	modelAndView.addObject("roles", roles);
	// 	modelAndView.addObject("title", "Update Person");
	// 	return modelAndView;
	// }
}