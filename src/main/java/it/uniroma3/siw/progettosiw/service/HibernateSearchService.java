package it.uniroma3.siw.progettosiw.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.progettosiw.model.Album;
import it.uniroma3.siw.progettosiw.model.Foto;
import it.uniroma3.siw.progettosiw.model.Fotografo;

@Service
public class HibernateSearchService {

	@PersistenceContext
	private EntityManager entityManager;

	public void initializeHibernateSearch() {
		Session session = entityManager.unwrap(Session.class);
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
		try {
			fullTextSession.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public List<Foto> fuzzySearchFotos(String searchTerm) {
		initializeHibernateSearch();
		List<Foto> fotos = new ArrayList<>();
		Session session = entityManager.unwrap(Session.class);
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Foto.class).get();
		try {
			Query query = qb.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(1).onField("fileBaseName")
					.matching(searchTerm).createQuery();
			@SuppressWarnings("unchecked")
			org.hibernate.query.Query<Foto> hibQuery = fullTextSession.createFullTextQuery(query, Foto.class);
			hibQuery.setMaxResults(50);
			fotos = hibQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fotos;
	}

	@Transactional
	public List<Fotografo> fuzzySearchFotografi(String searchTerm) {
		initializeHibernateSearch();
		List<Fotografo> fotografi = new ArrayList<>();
		Session session = entityManager.unwrap(Session.class);
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Fotografo.class).get();
		try {
			Query query = qb.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(1).onField("nome")
					.matching(searchTerm).createQuery();
			@SuppressWarnings("unchecked")
			org.hibernate.query.Query<Fotografo> hibQuery = fullTextSession.createFullTextQuery(query, Fotografo.class);
			hibQuery.setMaxResults(50);
			fotografi = hibQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fotografi;
	}

	@Transactional
	public List<Album> fuzzySearchAlbums(String searchTerm) {
		initializeHibernateSearch();
		List<Album> album = new ArrayList<>();
		Session session = entityManager.unwrap(Session.class);
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Album.class).get();
		try {
			Query query = qb.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(1).onField("nome")
					.matching(searchTerm).createQuery();
			@SuppressWarnings("unchecked")
			org.hibernate.query.Query<Album> hibQuery = fullTextSession.createFullTextQuery(query, Album.class);
			hibQuery.setMaxResults(50);
			album = hibQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return album;
	}
}
