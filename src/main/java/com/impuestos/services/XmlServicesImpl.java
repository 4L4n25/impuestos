package com.impuestos.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.impuestos.commons.GenericServiceImpl;
import com.impuestos.dao.XmlDao;
import com.impuestos.models.FileXml;

@Service

public class XmlServicesImpl extends GenericServiceImpl<FileXml, Long> implements XmlFileService {
	
	@Autowired
	private XmlDao xmlDao;

	private final Path rootFolder = Paths.get("uploads");

	@Override
	public void saveFile(MultipartFile file) throws Exception {
		Files.copy(file.getInputStream(), this.rootFolder.resolve(file.getOriginalFilename()));

	}
	
	@Override
	public CrudRepository<FileXml, Long> getDao(){
		return xmlDao;
	}

	@Override
	public Resource load(String name) throws Exception {
		Path file = rootFolder.resolve(name);
		Resource resource = new UrlResource(file.toUri());
		return resource;
	}

	@Override
	public void saveFile(List<MultipartFile> files) throws Exception {
		for (MultipartFile file : files) {
			this.saveFile(file);
		}
	}

	@Override
	public Stream<Path> loadAll() throws Exception {
		return Files.walk(rootFolder, 1).filter(path -> !path.equals(rootFolder)).map(rootFolder::relativize);
	}


}
