package com.exist.ecc.service;

import java.util.List;
import java.util.stream.*;
import java.util.Set;
import java.util.Comparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exist.ecc.model.*;
import com.exist.ecc.dao.*;
 
@Service("personnelService")
public class PersonnelServiceImpl implements PersonnelService {

    @Autowired
    private Dao dao;

    private static Logger logger = LoggerFactory.getLogger(PersonnelServiceImpl.class);

    @Transactional(readOnly = true)
    public Personnel findById(Long id) {
        return (Personnel) dao.getById(id, "com.exist.ecc.model.Personnel");
    }

    @Transactional
    public void deletePersonnel(Long id) {
        dao.delete(id, "com.exist.ecc.model.Personnel");
    }

    @Transactional
    public void addPersonnel(Personnel p) {
        dao.create(p);
    }

    @Transactional
    public void updatePersonnel(Personnel p) {
        dao.update(p);
    }

    @Transactional(readOnly = true)
    public List<Personnel> listPersonnel() {
        return dao.getList("com.exist.ecc.model.Personnel");
}
}