package com.impuestos.controllers;

import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.impuestos.models.File;
import com.impuestos.models.Response;
import com.impuestos.services.XmlFileService;

@RestController
@RequestMapping("/xml")
public class XmlController {

	@Autowired
	private XmlFileService xmlFileService;
	
	@PostMapping("/upload")
	public ResponseEntity<Response> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws Exception{
			xmlFileService.save(files);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Los archivos fueron cargados correctamente"));
	}
	
	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception{
		Resource resource = xmlFileService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"")
				.body(resource);
	}
	
	@GetMapping("/files")
	public ResponseEntity<List<File>> getAllFiles() throws Exception{
		List<File> files = xmlFileService.loadAll().map(path->{
			String filename = path.getFileName().toString();
			String url = MvcUriComponentsBuilder.fromMethodName(XmlController.class, "getFile", path.getFileName().toString()).build().toString();
			return new File(filename,url);
		}).collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}
}
