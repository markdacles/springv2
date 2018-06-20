package com.exist.ecc.restcontrollers;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
 
import com.exist.ecc.model.Personnel;
import com.exist.ecc.service.PersonnelService;
 
@RestController
public class PersonnelRestController {
 
    @Autowired
    PersonnelService personnelService;  //Service which will do all data retrieval/manipulation work
 
    //-------------------Retrieve All Users--------------------------------------------------------
     
    @RequestMapping(value = "/restpersonnel/", method = RequestMethod.GET)
    public ResponseEntity<List<Personnel>> listAllPersonnel() {
        List<Personnel> personnelList = personnelService.listPersonnel();
        if(personnelList.isEmpty()){
            return new ResponseEntity<List<Personnel>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Personnel>>(personnelList, HttpStatus.OK);
    }
 
 
    //-------------------Retrieve Single User--------------------------------------------------------
     
    @RequestMapping(value = "/restpersonnel/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Personnel> getPersonnel(@PathVariable("id") long id) {
        System.out.println("Fetching Personnel with id " + id);
        Personnel p = personnelService.findById(id);
        if (p == null) {
            System.out.println("Personnel with id " + id + " not found");
            return new ResponseEntity<Personnel>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Personnel>(p, HttpStatus.OK);
    }
 
     
     
    //-------------------Create a User--------------------------------------------------------
     
    @RequestMapping(value = "/restpersonnel/", method = RequestMethod.POST)
    public ResponseEntity<Void> createPersonnel(@RequestBody Personnel p,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Personnel " + p.getName().getFullName());
 
        personnelService.addPersonnel(p);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/restpersonnel/{id}").buildAndExpand(p.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
     
    //------------------- Update a User --------------------------------------------------------
     
    @RequestMapping(value = "/restpersonnel/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Personnel> updatePersonnel(@PathVariable("id") long id, @RequestBody Personnel p) {
        System.out.println("Updating Personnel " + id);
         
        Personnel upPersonnel = personnelService.findById(id);
         
        if (upPersonnel==null) {
            System.out.println("Personnel with id " + id + " not found");
            return new ResponseEntity<Personnel>(HttpStatus.NOT_FOUND);
        }
 
        upPersonnel.setName(p.getName());
        upPersonnel.setAddress(p.getAddress());
        upPersonnel.setBirthday(p.getBirthday());
        upPersonnel.setGwa(p.getGwa());
        upPersonnel.setDateHired(p.getDateHired());
        upPersonnel.setContact(p.getContact());
        upPersonnel.setRoles(p.getRoles());
        upPersonnel.setProject(p.getProject());
         
        personnelService.updatePersonnel(upPersonnel);

        return new ResponseEntity<Personnel>(upPersonnel, HttpStatus.OK);
    }
 
    //------------------- Delete a User --------------------------------------------------------
     
    @RequestMapping(value = "/restpersonnel/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Personnel> deletePersonnel(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Personnel with id " + id);
 
        Personnel p = personnelService.findById(id);
        if (p == null) {
            System.out.println("Unable to delete. Personnel with id " + id + " not found");
            return new ResponseEntity<Personnel>(HttpStatus.NOT_FOUND);
        }
 
        personnelService.deletePersonnel(id);
        return new ResponseEntity<Personnel>(HttpStatus.NO_CONTENT);
    }
 
}