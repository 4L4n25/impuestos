package com.impuestos.services;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.impuestos.commons.GenericService;
import com.impuestos.models.FileXml;

public interface XmlFileService extends GenericService<FileXml, Long> {

	public void saveFile(MultipartFile file) throws Exception;
	
	public Resource load(String name) throws Exception;
	
	public void saveFile(List<MultipartFile> files) throws Exception;
	
	public Stream<Path> loadAll() throws Exception;
	
}
