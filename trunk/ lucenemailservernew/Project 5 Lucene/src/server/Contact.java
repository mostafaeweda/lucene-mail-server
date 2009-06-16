package server;

import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.SAXException;


/**
 * *****************************************************************
 * Class Contact represents a contact with some of his information *
 * *****************************************************************
 */
public class Contact {

	//
	// Fields
	//

	private int primarySent;
	private String userName;
	private String IP;
	private String[] contactList;
	private ArrayList<String> folders;
	private boolean online;
	private long signInTime;
	
	//
	// Constructors
	//
	public Contact (String userName, String IP) 
	{
		this.userName = userName;
		this.IP = IP;
		this.online = true;
		folders = new ArrayList<String>();
		folders.add("Inbox");
		folders.add("Sent");
		folders.add("Spam");
		signInTime = System.currentTimeMillis();
	}

	public Contact()
	{
		this.folders = new ArrayList<String>();
		signInTime = System.currentTimeMillis();
	}

	/**
	 * Get the value of primarySent
	 * @return the value of primarySent
	 */
	public int getPrimarySent ( ) {
		return primarySent;
	}

	/**
	 * Set the value of userName
	 * @param newVar the new value of userName
	 */
	public void setUserName ( String newVar ) {
		userName = newVar;
	}

	/**
	 * Get the value of userName
	 * @return the value of userName
	 */
	public String getUserName ( ) {
		return userName;
	}

	/**
	 * Set the value of IP
	 * @param newVar the new value of IP
	 */
	public void setIP ( String newVar ) {
		IP = newVar;
	}

	/**
	 * Get the value of IP
	 * @return the value of IP
	 */
	public String getIP ( ) {
		return IP;
	}

	/**
	 * Set the value of contactList
	 * @param newVar the new value of contactList
	 */
	public void setContactList ( String[] newVar ) {
		contactList = newVar;
	}

	/**
	 * Get the value of contactList
	 * @return the value of contactList
	 */
	public String[] getContactList ( ) {
		return contactList;
	}

	public void changeStatus() throws SAXException, IOException
	{
		if (online)
		{
			XMLContactListWriter.getInstance().writeContactList(contactList, userName);
			String[] arr = new String[folders.size()];
			folders.toArray(arr);
			XMLProfileWriter.getInstance().writeFolders(userName, arr);
		}
		else
		{
			signInTime = System.currentTimeMillis();
		}
		online = ! online;
	}
	
	public void incrPrimarySent()
	{
		this.primarySent++;
	}
	
	public void addFolder(String name)
	{
		folders.add(name);
	}

	public long getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(long signInTime) {
		this.signInTime = signInTime;
	}
	
	
}
