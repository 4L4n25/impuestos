package com.impuestos.dao;

import org.springframework.data.repository.CrudRepository;

import com.impuestos.models.FileXml;

public interface XmlDao extends CrudRepository<FileXml, Long>{

}
