package server;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import server.message.Body;
import server.message.Message;


/**
 * **********************************************************************************
 * Class SignupHandler represents a handler for the sign up process, it creates a   * 
 * new user with a unique ID and a password and store its informations in a users'  *
 * database																		    *
 * **********************************************************************************
 * @author Ahmed Elmorsy, Kareem El-Sayed, Mohamed Abd Elsalam, Mohamed Yasser 
 * and Mostafa Eweda 
 */
public class SignupHandler {

	/**
	 * user primary key which represents a unique ID for each user 
	 */
	private static int usersPrimaryKey;
	
	/**
	 * instance for having singleton Object for SignUpHandler
	 */
	private static SignupHandler instance;
	
	//
	// Constructors
	//
	private SignupHandler () 
	{ 
		SignupHandler.usersPrimaryKey = 0;
	}
	
	public static SignupHandler getInstance()
	{
		if (instance == null)
			instance = new SignupHandler();
		return instance;
	}

	
	/**
	 * Get the value of usersPrimaryKey
	 * @return the value of usersPrimaryKey
	 */
	public int getUsersPrimaryKey ( ) 
	{
		return usersPrimaryKey;
	}

	/**check availibilty of the user name passed
	 * @param userName user name of the user
	 */
	public boolean checkUserExist( String userName )//*changed from void to boolean*
	{
		File accountsDir = new File("server/accounts");//represnets accounts directory
		File[] accounts = accountsDir.listFiles();
		if (accounts == null) return true;
		for (int i = 0, n = accounts.length; i < n; i++)
		{
			if (accounts[i].getName().equals(userName))
				return false;
		}
		return true;
	}


	/**
	 * create a profile on the database with user info
	 * @param profile user's info
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public Contact createProfile(Profile profile ) throws SAXException, IOException
	{
		Contact newContact = XMLProfileWriter.getInstance().writeProfile(profile, "" + usersPrimaryKey);
		usersPrimaryKey++;
		sendWelcomeMessage(profile.getUserName());
		return newContact;
	}


	/**
	 * @param reciever the new user to send welcome message
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public void sendWelcomeMessage( String receiver ) throws IOException, SAXException
	{
		String userName = ".admin";
		String subject = "Welcome in M3ak";
		String text = "Hello " + receiver + ",\n\nwelcome in A3MK world!!!!!";
		Body body = new Body(text);
		Message msg = new Message(userName, new String[] {receiver}, subject, body);
		Indexer.getInstance().addMessage(msg, new Contact(".admin","11"), new Contact[]{new Contact(receiver, "11")});
	}


}
