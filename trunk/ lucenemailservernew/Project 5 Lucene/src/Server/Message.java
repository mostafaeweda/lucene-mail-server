package Server;



/**
 * Class Message
 */
public class Message {

	//
	// Fields
	//

	private String sender;
	private String[] recivers;
	private String date;
	private int primaryKey;
	private Body body;
	
	//
	// Constructors
	//
	public Message () { };
	
	//
	// Methods
	//


	//
	// Accessor methods
	//

	/**
	 * Set the value of sender
	 * @param newVar the new value of sender
	 */
	public void setSender ( String newVar ) {
		sender = newVar;
	}

	/**
	 * Get the value of sender
	 * @return the value of sender
	 */
	public String getSender ( ) {
		return sender;
	}

	/**
	 * Set the value of recivers
	 * @param newVar the new value of recivers
	 */
	public void setRecivers ( String[] newVar ) {
		recivers = newVar;
	}

	/**
	 * Get the value of recivers
	 * @return the value of recivers
	 */
	public String[] getRecivers ( ) {
		return recivers;
	}

	/**
	 * Set the value of date
	 * @param newVar the new value of date
	 */
	public void setDate ( String newVar ) {
		date = newVar;
	}

	/**
	 * Get the value of date
	 * @return the value of date
	 */
	public String getDate ( ) {
		return date;
	}

	/**
	 * Set the value of primaryKey
	 * @param newVar the new value of primaryKey
	 */
	public void setPrimaryKey ( int newVar ) {
		primaryKey = newVar;
	}

	/**
	 * Get the value of primaryKey
	 * @return the value of primaryKey
	 */
	public int getPrimaryKey ( ) {
		return primaryKey;
	}

	/**
	 * Set the value of body
	 * @param newVar the new value of body
	 */
	public void setBody ( Body newVar ) {
		body = newVar;
	}

	/**
	 * Get the value of body
	 * @return the value of body
	 */
	public Body getBody ( ) {
		return body;
	}

	//
	// Other methods
	//

}
