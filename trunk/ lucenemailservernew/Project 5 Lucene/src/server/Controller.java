package server;

import java.io.File;
import java.util.Hashtable;


/**
 * Class Controller
 */
public class Controller {

	//
	// Fields
	//

	private Hashtable<String, Contact> onlineContacts;
	
	
	public Controller () 
	{ 
		File serverDir = new File("server");//represent server directory
		File accountsDir = new File("server\\accounts");//represnets accounts directory
		File messagesDir = new File("server\\messages");//represents messages directory
		File attachmentsDir = new File("server\\attachments");//represents attachments directory
		if (! serverDir.exists())
		{
			serverDir.mkdir();
			accountsDir.mkdir();
			messagesDir.mkdir();
			attachmentsDir.mkdir();
		}
	}
	
	//
	// Methods
	//


	//
	// Accessor methods
	//

	/**
	 * Set the value of onlineContacts
	 * @param newVar the new value of onlineContacts
	 */
	public void setOnlineContacts ( Hashtable<String, Contact> newVar ) {
		onlineContacts = newVar;
	}

	/**
	 * Get the value of onlineContacts
	 * @return the value of onlineContacts
	 */
	public Hashtable<String, Contact> getOnlineContacts ( ) {
		return onlineContacts;
	}

	//
	// Other methods
	//

}
