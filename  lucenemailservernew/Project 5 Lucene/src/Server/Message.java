package Server;

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
			String date)
	{
		super(primaryKey, subject, sender, date);
	}

	public Message(String userName, String[] recievers, String subject, Body body)
	{
		
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