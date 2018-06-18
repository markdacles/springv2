// package com.exist.ecc.service;

// import java.util.List;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.exist.ecc.model.*;
// import com.exist.ecc.dao.*;

// @Service
// @Transactional
// public class ProjectServiceImpl implements ProjectService {

//     @Autowired    
//     private Dao dao;
//     private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

//     public List<Project> listProject() {
//         logger.info("ProjectService listProject method");
//         return dao.getList("Project");
//     }

//     public Project findById(Long id, String object) {
//         logger.info("ProjectService listProject method");
//         return (Project) dao.findById(id, "Project");
//     }

//     @Transactional
//     public void addProject(Project p) {
//         logger.info("ProjectService addProject method");
//         dao.add(p);
//     }

//     @Transactional
//     public void updateProject(Project p) {
//         logger.info("ProjectService updateProject method");
//         dao.update(p);
//     }

//     @Transactional
//     public void deleteProject(Long id, String object) {
//         logger.info("ProjectService deleteProject method");
//         dao.delete(id, "Project");
//     }

// }