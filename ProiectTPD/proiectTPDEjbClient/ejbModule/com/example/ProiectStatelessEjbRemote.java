package com.example;

import javax.ejb.Remote;

@Remote
public interface ProiectStatelessEjbRemote{
	
	public void insert(String name);
}
