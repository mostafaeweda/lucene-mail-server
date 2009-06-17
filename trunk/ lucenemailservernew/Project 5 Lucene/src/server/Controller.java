package server;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.lucene.index.CorruptIndexException;
import org.xml.sax.SAXException;

import server.message.Body;
import server.message.Message;
import server.message.MessageRecord;
import server.message.MessageWriter;


/**
 * Class Controller
 */
public class Controller
{

	//
	// Fields
	//

	private Hashtable<String, Contact> onlineContacts;
	
	
	public Controller () 
	{ 
		onlineContacts = new Hashtable<String, Contact>();
		File serverDir = new File(Constants.SERVER_PATH);//represent server directory
		File accountsDir = new File(Constants.ACCOUNTS_PATH);//represents accounts directory
		File messagesDir = new File(Constants.MESSAGES_PATH);//represents messages directory
		File attachmentsDir = new File(Constants.Attachments_PATH);//represents attachments directory
		if (! serverDir.exists())
		{
			serverDir.mkdir();
			accountsDir.mkdir();
			messagesDir.mkdir();
			attachmentsDir.mkdir();
		}
	}
	
	public void SignIn(String userName, String password, String IP) throws Exception
	{
		Contact contact = SignInHandler.getInstance().signIn(userName, password, IP);
		onlineContacts.put(IP, contact);
	}
	
	public void newMessage(String IP)
	{
		Contact sender = onlineContacts.get(IP);
		sender.newMessage();
	}
	
	public void attach(String IP, File path) throws IOException
	{
		Contact sender = onlineContacts.get(IP);
		File fileOut = new File(Constants.ACCOUNTS_PATH + sender.getUserName() 
				+ File.separatorChar + "tempAttachment");
		fileOut.mkdirs();
		fileOut = new File(fileOut.getAbsolutePath() + File.separatorChar + sender.getUserName()
				 + "." + sender.getPrimarySent() + "." + path.getName());
		MessageWriter.getInstance().copyFiles(path, fileOut);
	}
	
	public void sendMessage(String IP, String[] receivers, String subject, Body body) throws Exception
	{
		Contact sender = onlineContacts.get(IP);
		Contact[] rec = new Contact[receivers.length];
		for (int i = 0; i < receivers.length; i++)
		{
			rec[i] = new Contact();
			rec[i].setUserName(receivers[i]);
		}
		Indexer.getInstance().addMessage(new Message(sender.getUserName(), receivers
				, subject, body), sender, rec);
	}
	
	public boolean validateUserName(String userName)
	{
		return SignupHandler.getInstance().checkUserExist(userName);
	}
	
	public void signUp(String IP, String userName, String password, String first, String last,
			int gender, String birth, int secretQuestion, String secretAns) throws Exception
	{
		Contact newContact = SignupHandler.getInstance().createProfile(new Profile(userName, password, first, last,
				gender, birth, secretQuestion, secretAns));
		newContact.setIP(IP);
		onlineContacts.put(IP, newContact);
	}
	
	public void search(String IP, String query, int start, int end) throws Exception
	{
		Contact con = onlineContacts.get(IP);
		con.setSignInTime(System.currentTimeMillis());
		MessageRecord[] messages = Searcher.getInstance().search(con.getUserName(), query, start, end);
		for (int i = 0; i < messages.length; i++)
		{
			System.out.println(messages[i].toString());
		}
	}
	
	public void moveMessage(String IP, Message msg, String to) throws CorruptIndexException, IOException
	{
		Contact sender = onlineContacts.get(IP);
		sender.setSignInTime(System.currentTimeMillis());
		Indexer.getInstance().deleteMessage(sender.getUserName(), 
				new String[]{msg.getSender()}, new int[]{msg.getPrimaryKey()});
		Indexer.getInstance().indexMessage(msg, sender, to);
	}
	
	public void checkIdle()
	{
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				while (true)
				{
					synchronized (onlineContacts) {
						Set<Entry<String, Contact>> pairs= onlineContacts.entrySet();
						Iterator<Entry<String, Contact>> it = pairs.iterator();
						while (it.hasNext())
						{
							long now = System.currentTimeMillis();
							Entry<String, Contact> pair = it.next();
							if ((now - pair.getValue().getSignInTime()) >= 900000)//15 min = 900000 ms
							{
								try {
									signOut(pair.getKey());
								} catch (SAXException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}						
					}
					try {
						Thread.sleep(600000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}
	
	public void signOut(String IP) throws SAXException, IOException
	{
		onlineContacts.get(IP).changeStatus();
		onlineContacts.remove(IP);
	}
	
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
	public Hashtable<String, Contact> getOnlineContacts ( ) 
	{
		return onlineContacts;
	}

	
	public static void main(String[] args) throws Exception 
	{
		Controller cont = new Controller();
		cont.SignIn("1", "1", "12");
		cont.sendMessage("12", new String[]{"2"}, "Hiiii!!!", new Body("3weda and morsy and yasser and 3ebso and kimo :p"));
		cont.SignIn("2", "2", "13");
		cont.search("13", "kimo", 0, 20);
	}
}
