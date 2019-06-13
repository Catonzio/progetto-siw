package it.uniroma3.siw.progettosiw.controller;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.progettosiw.exceptions.InvalidFileException;
import it.uniroma3.siw.progettosiw.model.Foto;
import it.uniroma3.siw.progettosiw.service.FotoService;

@Controller
public class FotoController {

	@Value("/home/danilo/OneDrive/Ubuntu/Danilo/Eclipse/progetto-siw/src/main/resources/static/images")
	private String uploadDirectory;

	@Autowired
	private FotoService fotoService;

	@RequestMapping("/fotografie")
	public String fotografie() {
		return "fotografie.html";
	}

	@RequestMapping(value = "/uploadFoto")
	public String upload() {
		return "uploadFoto.html";
	}

	@RequestMapping(value = "/upload_images", method = RequestMethod.POST)
	public String fotoUpload(Model model, @RequestParam("file") MultipartFile file) {
		try {
			Foto uploadedFoto = fotoService.uploadFoto(file, uploadDirectory);
			uploadedFoto.setAlbum(null);
			uploadedFoto.setAutore(null);
			uploadedFoto.setDataCaricamento(LocalDate.now());
			fotoService.save(uploadedFoto);
		} catch (InvalidFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("fotografie", fotoService.tutteFoto());
		return "uploadFoto.html"; // MODIFICARE LA PAGINA
	}

	@RequestMapping(value = "/file", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> uploadedFile() throws IOException {
		Path filePath = fotoService.findLastFile().getFilePath();
		return ResponseEntity.ok().contentLength(Files.size(filePath))
				.contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(filePath.toString())))
				.body(new InputStreamResource(Files.newInputStream(filePath, StandardOpenOption.READ)));
	}
}
