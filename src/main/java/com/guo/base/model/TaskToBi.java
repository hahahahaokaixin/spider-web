package com.guo.base.model;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;


public class TaskToBi implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Dao biDao;

	public Dao getBiDao() {
		return biDao;
	}

	public void setBiDao(Dao biDao) {
		this.biDao = biDao;
	}

}
