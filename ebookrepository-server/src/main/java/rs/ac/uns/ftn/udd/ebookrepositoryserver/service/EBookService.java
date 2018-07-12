package rs.ac.uns.ftn.udd.ebookrepositoryserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.repository.EBookRepository;

@Service
public class EBookService {

	@Autowired
	EBookRepository eBookRepository;

}
