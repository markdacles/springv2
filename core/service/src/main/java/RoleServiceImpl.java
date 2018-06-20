package com.exist.ecc.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exist.ecc.model.Roles;
import com.exist.ecc.dao.*;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private Dao dao;
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Transactional(readOnly = true)
    public List<Roles> listRoles() {
        logger.info("RoleService listRoles method");
        return dao.getList("com.exist.ecc.model.Roles");
    }

    @Transactional(readOnly = true)
    public Roles findById(Long id) {
        logger.info("RoleService findById method");
        return (Roles) dao.getById(id, "com.exist.ecc.model.Roles");
    }

    @Transactional
    public void addRole(Roles r) {
        logger.info("RoleService addRole method");
        dao.create(r);
    }

    @Transactional
    public void updateRole(Roles r) {
        logger.info("RoleService updateRole method");
        dao.update(r);
    }

    @Transactional
    public void deleteRole(Long id) {
        logger.info("RoleService deleteRole method");
        dao.delete(id, "com.exist.ecc.model.Roles");
    }
}