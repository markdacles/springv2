package com.exist.ecc.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exist.ecc.model.*;
import com.exist.ecc.dao.*;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired    
    private Dao dao;
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Transactional(readOnly = true)
    public List<Project> listProject() {
        logger.info("ProjectService listProject method");
        return dao.getList("com.exist.ecc.model.Project");
    }

    @Transactional(readOnly = true)
    public Project findById(Long id) {
        logger.info("ProjectService listProject method");
        return (Project) dao.getById(id, "com.exist.ecc.model.Project");
    }

    @Transactional
    public void addProject(Project p) {
        logger.info("ProjectService addProject method");
        dao.create(p);
    }

    @Transactional
    public void updateProject(Project p) {
        logger.info("ProjectService updateProject method");
        dao.update(p);
    }

    @Transactional
    public void deleteProject(Long id) {
        logger.info("ProjectService deleteProject method");
        dao.delete(id, "com.exist.ecc.model.Project");
    }

}