package rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto;

public class ChangePasswordRequestDTO {

	private String newPassword;
	private String repeatedNewPassword;
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getRepeatedNewPassword() {
		return repeatedNewPassword;
	}
	
	public void setRepeatedNewPassword(String repeatedNewPassword) {
		this.repeatedNewPassword = repeatedNewPassword;
	}
	
}
