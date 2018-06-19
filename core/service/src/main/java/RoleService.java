package com.exist.ecc.service;

import java.util.List;
import java.util.Set;

import com.exist.ecc.model.Roles;

public interface RoleService {
    
    public List<Roles> listRoles();

    public Roles findById(Long id);

    public void addRole(Roles r);

    public void updateRole(Roles r);

    public void deleteRole(Long id);

}