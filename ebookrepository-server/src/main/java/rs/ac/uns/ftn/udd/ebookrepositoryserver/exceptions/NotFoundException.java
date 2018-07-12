package rs.ac.uns.ftn.udd.ebookrepositoryserver.exceptions;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {
	
	public NotFoundException() {}
	
	public NotFoundException(String message) {
		super(message);
	}

}
