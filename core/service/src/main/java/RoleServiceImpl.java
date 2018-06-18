package com.exist.ecc.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exist.ecc.model.*;
import com.exist.ecc.dao.*;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private Dao dao;
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Transactional(readOnly = true)
    public List<Roles> listRoles() {
        return (List<Roles>) dao.getList("com.exist.model.Roles");
    }

    @Transactional
    public Roles findById(Long id, String object) {
        return (Roles) dao.getById(id, "com.exist.ecc.model.Roles");
    }

    @Transactional
    public void addRole(Roles r) {
        dao.create(r);
    }

    @Transactional
    public void updateRole(Roles r) {
        dao.update(r);
    }

    @Transactional
    public void deleteRole(Long id, String object) {
        logger.info("RoleService deleteRole method");
        dao.delete(id, "Roles");
    }

}