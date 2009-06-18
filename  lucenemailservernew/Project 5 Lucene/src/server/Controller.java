package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.xml.sax.SAXException;

import server.message.Body;
import server.message.Message;
import server.message.MessageDetailedViewXMLReader;
import server.message.MessageRecord;
import server.message.MessageWriter;


/**
 * Class Controller
 */
public class Controller {

	//
	// Fields
	//

	private Hashtable<String, Contact> onlineContacts;
	
	private static Controller controller;

	private Controller ()
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
	
	public static Controller getInstance()
	{
		if(controller == null)
			controller = new Controller();
		return controller;
	}
	
	public boolean signIn(String userName, String password, String IP) throws Exception
	{
		Contact contact = SignInHandler.getInstance().signIn(userName, password, IP);
		if(contact == null)
			return false;
		contact.setSignInTime(System.currentTimeMillis());
		onlineContacts.put(IP, contact);
		return true;
	}
	
	public MessageRecord[] openFolder(String IP, String folderName, int start, int end) throws Exception
	{
		return search(IP, "Folder:\"" + folderName + "\"", start, end);
	}
	
	public void newMessage(String IP)
	{
		Contact sender = onlineContacts.get(IP);
		sender.setSignInTime(System.currentTimeMillis());
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
	
	public void cancelAttach(String IP, String attachName)
	{
		Contact sender = onlineContacts.get(IP);
		File attachment = new File(Constants.ACCOUNTS_PATH + sender.getUserName() 
				+ File.separatorChar + attachName);
		File parent = attachment.getParentFile();
		attachment.delete();
		if (parent.list().length == 0)
			parent.delete();
		
	}

	public void deleteMessages(String IP, MessageRecord[] messages) throws CorruptIndexException, IOException
	{
		Contact con = onlineContacts.get(IP);
		Indexer.getInstance().deleteMessage(con.getUserName(),
				messages);
		final IndexWriter w = new IndexWriter(FSDirectory.getDirectory(Constants.ACCOUNTS_PATH
				+ con.getUserName() + File.separatorChar + "indexFiles"), new StandardAnalyzer(), 
				false, IndexWriter.MaxFieldLength.UNLIMITED);
		w.optimize();
		w.close();
	}

	public void sendMessage(String IP, String[] receivers, String subject, Body body) throws Exception
	{
		Contact sender = onlineContacts.get(IP);
		Contact[] rec = new Contact[receivers.length];
		sender.setSignInTime(System.currentTimeMillis());
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
	
	public MessageRecord[] search(String IP, String query, int start, int end) throws Exception
	{
		Contact con = onlineContacts.get(IP);
		con.setSignInTime(System.currentTimeMillis());
		MessageRecord[] messages = null;
		Searcher.getInstance().search(con.getUserName(), query, start, end);
//		MessageRecord[] messages = Searcher.getInstance().search(con.getUserName(), query, start, end);
//		for (int i = 0; i < messages.length; i++)
//		{
//			System.out.println(messages[i].toString());
//		}
		return messages;
	}
	
	public void moveMessage(String IP, MessageRecord[] msgs, String to) throws CorruptIndexException, IOException
	{
		Contact sender = onlineContacts.get(IP);
		sender.setSignInTime(System.currentTimeMillis());
		Indexer.getInstance().deleteMessage(sender.getUserName(), msgs);
		for (MessageRecord messageRecord : msgs)
		{
			MessageDetailedViewXMLReader reader = new MessageDetailedViewXMLReader(
				messageRecord.getSender() + "." + messageRecord.getPrimaryKey()
				 + ".xml");
			Message msg = reader.beginParsing();
			Indexer.getInstance().indexMessage(msg, sender, to);
		}
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
		Contact con = onlineContacts.get(IP);
		con.changeStatus();
		Properties props = new Properties();
		props.setProperty("Sent", "" + con.getPrimarySent());
		props.storeToXML(new FileOutputStream(Constants.ACCOUNTS_PATH
				+ con.getUserName() + File.separatorChar + "sent.xml"), "");
		onlineContacts.remove(IP);
	}
	
	/**
	 * Set the value of onlineContacts
	 * @param newVar the new value of onlineContacts
	 */
	public void setOnlineContacts ( Hashtable<String, Contact> newVar ) {
		onlineContacts = newVar;
	}

public void addContact(String IP, String name)
	{
		Contact con = onlineContacts.get(IP);
		con.addContact(name);
	}

	/**
	 * Get the value of onlineContacts
	 * @return the value of onlineContacts
	 */
	public Hashtable<String, Contact> getOnlineContacts ( ) 
	{
		return onlineContacts;
	}
	
	public void close() throws SAXException, IOException
	{
		Collection<Contact> contacts = onlineContacts.values();
		for (Contact con : contacts) {
			con.changeStatus();
			Properties props = new Properties();
			props.setProperty("Sent", "" + con.getPrimarySent());
			props.storeToXML(new FileOutputStream(Constants.ACCOUNTS_PATH
					+ con.getUserName() + File.separatorChar + "sent.xml"), "");
		}
		onlineContacts.clear();
	}
	
	public static void main(String[] args) throws Exception 
	{
		Controller cont = getInstance();
//		cont.signUp("12", "1", "1", "1", "1",
//			1, "!", 1, "1");
//		MessageRecord[] re = cont.openFolder("12", "Inbox", 0, 20);
//		System.out.println(re.length);
//		cont.signUp("13", "2", "2", "1", "1",
//				1, "!", 1, "1");
		cont.signIn("1", "1", "12");
		cont.signIn("2", "2", "13");
		cont.sendMessage("12", new String[]{"2"}, "3weda!!!", new Body("3weda yasser and 3ebso and kimo :p", null));
		cont.sendMessage("12", new String[]{"2"}, "7mraaa!!!", new Body("7mra ya 3weda :p", null));
		cont.sendMessage("13", new String[]{"1"}, "mohamed yasser", new Body("want to know that you are my friends welcome to my world", null));
		Controller.getInstance().search("13", "Folder:Inbox AND 7mra", 0, 20);
		Controller.getInstance().search("12", "Folder:Sent AND 7mra", 0, 20);
//		MessageRecord[] record = cont.search("12", "friends", 0, 20);
		System.out.println("-------------------------------------------------");
//		cont.deleteMessages("13", record);
//		record = cont.search("12", "7mra", 0, 20);
		System.out.println("-------------------------------------------------");
		cont.signOut("12");
		cont.signOut("13");
//		MessageRecord[] record2 = cont.search("12", "ezayak", 0, 20);
	}
}
