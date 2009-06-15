package Server;

import java.util.Date;



/**
 * Class Message
 */
public class Message {

	//
	// Fields
	//

	private String sender;
	private String[] receivers;
	private String date;
	private Body body;
	private String subject;
	public Message (String sender ,String[] receivers, String subject, Body body)
	{ 
		this.sender = sender;
		this.receivers = receivers;
		this.body = body;
		this.subject = subject;
		this.date = new Date().toString();
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
	 * Set the value of receivers
	 * @param newVar the new value of receivers
	 */
	public void setRecivers ( String[] newVar ) {
		receivers = newVar;
	}

	/**
	 * Get the value of recivers
	 * @return the value of recivers
	 */
	public String[] getRecivers ( ) {
		return receivers;
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


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}

	

}
