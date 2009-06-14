package Server;

import java.util.Hashtable;


/**
 * Class Controller
 */
public class Controller {

	//
	// Fields
	//

	private Hashtable<String, Contact> onlineContacts;
	
	//
	// Constructors
	//
	public Controller () { };
	
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
