package com.exist.ecc.controllers;

import com.exist.ecc.model.Roles;
import com.exist.ecc.model.Personnel;
import com.exist.ecc.model.FileUpload;
import com.exist.ecc.model.Name;
import com.exist.ecc.model.Address;
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
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.propertyeditors.CustomDateEditor;

@Controller
@RequestMapping(value="/personnel")
public class PersonnelController {
	
	@Autowired
	private PersonnelService personnelService;
	
	@Autowired
	private RoleService roleService;

	@InitBinder
    protected void dataBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
            dateFormat, true));
	}

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
		mav.addObject("uploadFile", new FileUpload());
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
		@RequestParam(value="checkedRoles", required=false) String[] cRoles,
		@RequestParam(value="pid", required=false) Long id) {

		ModelAndView mav = new ModelAndView("redirect:/personnel/list");
		
		System.out.println(cDetails.length);

		if(cDetails != null){
			for(int i = 0; i < cDetails.length; i++){
				Contact c = new Contact();
				c.setContactType(cType[i]);
				c.setContactDetails(cDetails[i]);
				p.getContact().add(c);
			}
		}

		if (cRoles != null) {
            for (String rid : cRoles) {
               Roles role = roleService.findById(Long.parseLong(rid));
               p.getRoles().add(role);
            }
        }
        

		if (id == null) {
			personnelService.addPersonnel(p);	
		}
		else {
			p.setId(id);
			personnelService.updatePersonnel(p);
		}
		return mav;
	}

	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public ModelAndView uploadFile (@ModelAttribute("uploadFile") FileUpload f, BindingResult result) {

		try{
	        MultipartFile file = f.getFile();
			Personnel p = new Personnel();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String content = new String(file.getBytes());

			String lines[] = content.split("\\r?\\n");

			String line0[] = lines[0].split(",");

			int i = 0;

			Name name = new Name();
			Address address = new Address();

			if(line0[i] != "") {
				name.setLname(line0[i++]);
			}
			if(line0[i] != "") {
				name.setFname(line0[i++]);
			}
			if(line0[i] != "") {
				name.setMname(line0[i++]);
			}
			if(line0[i] != "") {
				p.setName(name);
			}
			if(line0[i] != "") {
				address.setBrgy(line0[i++]);
			}
			if(line0[i] != "") {
				address.setCity(line0[i++]);
			}
			if(line0[i] != "") {
				p.setAddress(address);
			}
			if(line0[i] != "") {
				p.setBirthday(formatter.parse(line0[i++]));
			}
			if(line0[i] != "") {
				p.setGwa(Double.parseDouble(line0[i++]));
			}
			if(line0[i] != "") {
				p.setDateHired(formatter.parse(line0[i]));
			}

			String line1[] = lines[1].split(",");

			for(String str : line1) {
				Contact c = new Contact();
				c.setContactType("Landline");
				c.setContactDetails(str);
				p.getContact().add(c);
			}

			String line2[] = lines[2].split(",");


			for(String str : line2) {
				Contact c = new Contact();
				c.setContactType("Mobile");
				c.setContactDetails(str);
				p.getContact().add(c);
			}

			String line3[] = lines[3].split(",");


			for(String str : line3) {
				Contact c = new Contact();
				c.setContactType("Email");
				c.setContactDetails(str);
				p.getContact().add(c);
			}

			String line4[] = lines[4].split(",");

			List<Roles> roleList = roleService.listRoles();
			for(String str : line4) {
				for(Roles r : roleList) {
					if(str.equals(r.getRole())) {
						p.getRoles().add(r);
					}
				}
			}

			personnelService.addPersonnel(p);
	        
			return new ModelAndView("redirect:/personnel/list");
			} catch (Exception e) {
				ModelAndView mav = new ModelAndView("personnelIndex");
				List<Personnel> personnelList = personnelService.listPersonnel();
				mav.addObject("personnelList", personnelList);
				mav.addObject("pact", "manp");
				mav.addObject("fileerror", "Invalid file. Try again!");
				return mav;
			}
	}

	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public ModelAndView deletePersonnel(@RequestParam(value="pid", required=true) long id) {
		personnelService.deletePersonnel(id);
		ModelAndView modelAndView = new ModelAndView("redirect:/personnel/list");
		return modelAndView;
	}

	@RequestMapping(value="/update", method=RequestMethod.GET)
	public ModelAndView updatePerson(@RequestParam(value="pid", required=true) long id) {

		ModelAndView mav = new ModelAndView("personnelForm");
		mav.addObject("roleList", roleService.listRoles());
		Personnel p = personnelService.findById(id);		
		mav.addObject("personnel",p);
		mav.addObject("pact", "updateform");
		String url = "updatePersonnel?pid=" + id + "&";
		mav.addObject("url", url);
		return mav;
	}
}