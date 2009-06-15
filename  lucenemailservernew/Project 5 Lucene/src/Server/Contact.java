package Server;

/**
 * Class Contact
 */
public class Contact {

	//
	// Fields
	//

	private int primarySent;
	private String userName;
	private String ID;
	private String IP;
	private String[] contactList;
	
	//
	// Constructors
	//
	public Contact (String userName, String id, String IP) 
	{
		this.userName = userName;
		this.ID = id;
		this.IP = IP;
	}
	
	//
	// Methods
	//


	//
	// Accessor methods
	//

	/**
	 * Get the value of primarySent
	 * @return the value of primarySent
	 */
	public int getPrimarySent ( ) {
		return primarySent;
	}

	/**
	 * Set the value of userName
	 * @param newVar the new value of userName
	 */
	public void setUserName ( String newVar ) {
		userName = newVar;
	}

	/**
	 * Get the value of userName
	 * @return the value of userName
	 */
	public String getUserName ( ) {
		return userName;
	}

	/**
	 * Set the value of IP
	 * @param newVar the new value of IP
	 */
	public void setIP ( String newVar ) {
		IP = newVar;
	}

	/**
	 * Get the value of IP
	 * @return the value of IP
	 */
	public String getIP ( ) {
		return IP;
	}

	/**
	 * Set the value of contactList
	 * @param newVar the new value of contactList
	 */
	public void setContactList ( String[] newVar ) {
		contactList = newVar;
	}

	/**
	 * Get the value of contactList
	 * @return the value of contactList
	 */
	public String[] getContactList ( ) {
		return contactList;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public void setPrimarySent(int primarySent) {
		this.primarySent = primarySent;
	}
	

}
