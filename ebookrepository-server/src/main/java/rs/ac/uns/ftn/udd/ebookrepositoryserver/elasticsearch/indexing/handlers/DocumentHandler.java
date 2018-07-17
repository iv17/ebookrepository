package rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.handlers;

import java.io.File;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.IndexUnit;

public abstract class DocumentHandler {
	/**
	 * Od prosledjene datoteke se konstruise Lucene Document
	 * 
	 * @param file
	 *            datoteka u kojoj se nalaze informacije
	 * @return Lucene Document
	 */
	public abstract IndexUnit getIndexUnit(File file);
	public abstract String getText(File file);

}
