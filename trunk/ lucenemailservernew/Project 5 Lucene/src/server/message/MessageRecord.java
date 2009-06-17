package server.message;

import java.util.ArrayList;



/**
 * Class MessageRecord
 */
public class MessageRecord
{
	//
	// Fields
	//
	protected int primaryKey;
	protected ArrayList<String> attachmentNames;
	protected String subject;
	protected String sender;
	protected String date;
	protected String folder;

	//
	// Constructors
	//
	public MessageRecord() 
	{
		attachmentNames = new ArrayList<String>();
	}

	public MessageRecord(int primaryKey, String subject, String sender,
			String date, String folder) 
	{
		this.primaryKey = primaryKey;
		this.subject = subject;
		this.sender = sender;
		this.date = date;
		this.folder = folder;
		this.attachmentNames = new ArrayList<String>();
	}

	public String toString()
	{
		String result = "From " + sender + " with Subject : " + subject + 
		",Date:" + date + "Folder: " + folder;
		return result;
	}
	
	/**
	 * Set the value of primaryKey
	 * @param newVar the new value of primaryKey
	 */
	public void setPrimaryKey ( int newVar )
	{
		primaryKey = newVar;
	}

	/**
	 * Get the value of primaryKey
	 * @return the value of primaryKey
	 */
	public int getPrimaryKey ( )
	{
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

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	public void attach(String attachName)
	{
		attachmentNames.add(attachName);
	}

	public ArrayList<String> getAttachmentNames() {
		return attachmentNames;
	}
	
	
}
