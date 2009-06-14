

/**
 * Class MessageRecord
 */
public class MessageRecord {

	//
	// Fields
	//

	private int primaryKey;
	private String subject;
	private String sender;
	private String date;
	
	//
	// Constructors
	//
	public MessageRecord () { };
	
	//
	// Methods
	//


	//
	// Accessor methods
	//

	/**
	 * Set the value of primaryKey
	 * @param newVar the new value of primaryKey
	 */
	private void setPrimaryKey ( int newVar ) {
		primaryKey = newVar;
	}

	/**
	 * Get the value of primaryKey
	 * @return the value of primaryKey
	 */
	private int getPrimaryKey ( ) {
		return primaryKey;
	}

	/**
	 * Set the value of subject
	 * @param newVar the new value of subject
	 */
	private void setSubject ( String newVar ) {
		subject = newVar;
	}

	/**
	 * Get the value of subject
	 * @return the value of subject
	 */
	private String getSubject ( ) {
		return subject;
	}

	/**
	 * Set the value of sender
	 * @param newVar the new value of sender
	 */
	private void setSender ( String newVar ) {
		sender = newVar;
	}

	/**
	 * Get the value of sender
	 * @return the value of sender
	 */
	private String getSender ( ) {
		return sender;
	}

	/**
	 * Set the value of date
	 * @param newVar the new value of date
	 */
	private void setDate ( String newVar ) {
		date = newVar;
	}

	/**
	 * Get the value of date
	 * @return the value of date
	 */
	private String getDate ( ) {
		return date;
	}

	//
	// Other methods
	//

}
