package server.message;


/**
 * Class Message
 */
public class Message extends MessageRecord
{
	//
	// Fields
	//
	private String[] recievers;
	private Body body;

	//
	// Constructors
	//
	public Message() 
	{ 
		super();
	}
	
	public Message(int primaryKey, String subject, String sender,
			String date, String folder)
	{
		super(primaryKey, subject, sender, date, folder);
	}

	public Message(String sender, String[] recievers, String subject, Body body)
	{
		this.sender = sender;
		this.recievers = recievers;
		this.subject = subject;
		this.body = body;
	}

	/**
	 * @return the recievers
	 */
	public String[] getRecievers() {
		return recievers;
	}

	/**
	 * @param recievers the recievers to set
	 */
	public void setRecievers(String[] recievers) {
		this.recievers = recievers;
	}

	/**
	 * @return the body
	 */
	public Body getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(Body body) {
		this.body = body;
	}
}