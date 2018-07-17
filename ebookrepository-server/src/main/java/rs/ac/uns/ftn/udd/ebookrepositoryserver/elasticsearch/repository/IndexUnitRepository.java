package rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.IndexUnit;

public interface IndexUnitRepository extends ElasticsearchRepository<IndexUnit, String> {

	List<IndexUnit> findByTitle(String title);
	IndexUnit findByFilename(String filename);
	
}
