package Server;



/**
 * Class MessageRecord
 */
public class MessageRecord {

	//
	// Fields
	//
	protected int primaryKey;
	protected String subject;
	protected String sender;
	protected String date;

	//
	// Constructors
	//
	public MessageRecord() 
	{ 
		
	}

	public MessageRecord(int primaryKey, String subject, String sender,
			String date) 
	{
		this.primaryKey = primaryKey;
		this.subject = subject;
		this.sender = sender;
		this.date = date;
	}

	public MessageRecord(String substring, String url, String url2) {
		// TODO Auto-generated constructor stub
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
	 * Set the value of subject
	 * @param newVar the new value of subject
	 */
	public void setSubject ( String newVar ) {
		subject = newVar;
	}

	/**
	 * Get the value of subject
	 * @return the value of subject
	 */
	public String getSubject ( ) {
		return subject;
	}

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
}
