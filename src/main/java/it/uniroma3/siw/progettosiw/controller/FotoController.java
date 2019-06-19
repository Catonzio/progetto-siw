package it.uniroma3.siw.progettosiw.controller;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.progettosiw.exceptions.InvalidFileException;
import it.uniroma3.siw.progettosiw.model.Album;
import it.uniroma3.siw.progettosiw.model.Foto;
import it.uniroma3.siw.progettosiw.model.Fotografo;
import it.uniroma3.siw.progettosiw.service.AlbumService;
import it.uniroma3.siw.progettosiw.service.FotoService;
import it.uniroma3.siw.progettosiw.service.FotografoService;
import it.uniroma3.siw.progettosiw.support.VerifyLogIn;

@Controller
public class FotoController {

	@Value("/home/danilo/OneDrive/Ubuntu/progetto-siw/src/main/resources/static/images")
	private String uploadDirectory;

	@Autowired
	private FotoService fotoService;

	@Autowired
	private AlbumService albumService;

	@Autowired
	private FotografoService fotografoService;

	private VerifyLogIn verify = new VerifyLogIn();

	@RequestMapping("/fotografie")
	public String fotografie(Model model) {
		verify.addLoginAttributes(model);
		model.addAttribute("fotografie", fotoService.tutteFoto());
		return "fotografie.html";
	}

	@RequestMapping(value = "/uploadFoto")
	public String upload(Model model) {
		verify.addLoginAttributes(model);
		model.addAttribute("fotografi", fotografoService.tuttiFotografi());
		model.addAttribute("albumi", albumService.tuttiAlbum());
		return "uploadFoto.html";
	}

	@RequestMapping(value = "/upload_images", method = RequestMethod.POST)
	public String fotoUpload(Model model, @RequestParam("file") MultipartFile file,
			@RequestParam("nomeAlbum") String nomeAlbum, @RequestParam("nomeFotografo") String nomeFotografo) {
		verify.addLoginAttributes(model);
		model.addAttribute("fotografi", fotografoService.tuttiFotografi());
		model.addAttribute("albumi", albumService.tuttiAlbum());
		try {
			Album album = albumService.trovaPerNome(nomeAlbum).get(0);
			Fotografo fotografo = fotografoService.trovaPerNome(nomeFotografo).get(0);
			if (isAlbumDelFotografo(album, fotografo)) {
				Foto uploadedFoto = fotoService.uploadFoto(file, uploadDirectory, fotografo, album);
				album.aggiungiFoto(uploadedFoto);
				fotografo.aggiungiFoto(uploadedFoto);
				fotoService.save(uploadedFoto);
			} else
				return "uploadFoto.html";
		} catch (InvalidFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("fotografie", fotoService.tutteFoto());
		return "fotografie.html";
	}

	@RequestMapping(value = "/upload_images/{id}", method = RequestMethod.GET)
	public String aggiornaConAlbum(Model model, @PathVariable("id") Long id) {
		verify.addLoginAttributes(model);
		model.addAttribute("fotografi", fotografoService.tuttiFotografi());
		if (id != null) {
			Fotografo fotografo = fotografoService.trovaPerId(id);
			if (fotografo != null) {
				model.addAttribute("albumi", fotografo.getAlbum());
			}
			return "uploadFoto.html";
		} else
			return "uploadFoto.html";
	}

	@RequestMapping(value = "/file", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> uploadedFile() throws IOException {
		Path filePath = fotoService.findLastFile().getFilePath();
		return ResponseEntity.ok().contentLength(Files.size(filePath))
				.contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(filePath.toString())))
				.body(new InputStreamResource(Files.newInputStream(filePath, StandardOpenOption.READ)));
	}

	private boolean isAlbumDelFotografo(Album album, Fotografo fotografo) {
		return fotografo.getAlbum().contains(album);
	}
}
