package nl.triple_it.assignment.JSON;

public class Employees {

	private String firstname;
	private String lastname;
	private String emailaddress;
	private String photourl;

	public Employees() {
	}

	public Employees(String firstname, String lastname, String emailaddress, String photourl) {
		super();

		this.firstname = firstname;
		this.lastname = lastname;
		this.emailaddress = emailaddress;
		this.photourl = photourl;

	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

}
