package it.uniroma3.siw.progettosiw.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Foto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "file_directory", length = 500)
	private String fileDirectory;
	@Column(name = "file_name", length = 500)
	private String fileName;
	@Column(name = "file_extension", length = 5)
	private String fileExtension;
	@Column
	private String fileBaseName;
	@Column
	private LocalDate dataCaricamento;
	@ManyToOne
	private Fotografo autore;
	@ManyToOne
	private Album album;

	public Foto() {

	}

	public Foto(String fileDirectory, String fileName, String fileExtension, String fileBaseName,
			LocalDate dataCaricamento, Fotografo autore, Album album) {
		this.fileDirectory = fileDirectory;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
		this.fileBaseName = fileBaseName;
		this.dataCaricamento = dataCaricamento;
		this.autore = autore;
		this.album = album;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileDirectory() {
		return fileDirectory;
	}

	public void setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileBaseName() {
		return fileBaseName;
	}

	public void setFileBaseName(String fileBaseName) {
		this.fileBaseName = fileBaseName;
	}

	public LocalDate getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(LocalDate dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public Fotografo getAutore() {
		return autore;
	}

	public void setAutore(Fotografo autore) {
		this.autore = autore;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Path getFilePath() {
		if ((fileName == null) || (fileDirectory == null))
			return null;

		return Paths.get(fileDirectory, fileName);
	}

}
