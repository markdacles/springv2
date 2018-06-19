package com.exist.ecc.service;

import java.util.*;

import com.exist.ecc.model.Personnel;

public interface PersonnelService {

    public void addPersonnel(Personnel p);

    public void updatePersonnel(Personnel p);

    public List<Personnel> listPersonnel();

 	public void deletePersonnel(Long id);

	public Personnel findById(Long id);

	// public List<Personnel> searchFor(String str);
}