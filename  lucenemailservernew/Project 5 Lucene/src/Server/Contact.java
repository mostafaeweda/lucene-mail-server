

/**
 * Class Contact
 */
public class Contact {

	//
	// Fields
	//

	static private int primarySent;
	private String userName;
	private String IP;
	private string[] contactList;
	
	//
	// Constructors
	//
	public Contact () { };
	
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
	private int getPrimarySent ( ) {
		return primarySent;
	}

	/**
	 * Set the value of userName
	 * @param newVar the new value of userName
	 */
	private void setUserName ( String newVar ) {
		userName = newVar;
	}

	/**
	 * Get the value of userName
	 * @return the value of userName
	 */
	private String getUserName ( ) {
		return userName;
	}

	/**
	 * Set the value of IP
	 * @param newVar the new value of IP
	 */
	private void setIP ( String newVar ) {
		IP = newVar;
	}

	/**
	 * Get the value of IP
	 * @return the value of IP
	 */
	private String getIP ( ) {
		return IP;
	}

	/**
	 * Set the value of contactList
	 * @param newVar the new value of contactList
	 */
	private void setContactList ( string[] newVar ) {
		contactList = newVar;
	}

	/**
	 * Get the value of contactList
	 * @return the value of contactList
	 */
	private string[] getContactList ( ) {
		return contactList;
	}

	//
	// Other methods
	//

}
