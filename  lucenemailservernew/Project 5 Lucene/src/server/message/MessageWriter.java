package server.message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import server.Constants;
import server.Contact;
import server.XMLwriter;



/**
 * Class MessageWriter
 */
public class MessageWriter extends XMLwriter {

	private static MessageWriter instance;
	
	private MessageWriter () 
	{ 
		
	}
	
	public static MessageWriter getInstance()
	{
		if(instance == null )
			instance = new MessageWriter();
		return instance;
	}
	/**
	 * @param        message
	 * @param        contact
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public File copyMessage( Message message, Contact contact) throws IOException, SAXException
	{
		contact.incrPrimarySent();
		String primary =contact.getPrimarySent() + "";
		for (int i = primary.length(); i < Constants.PRIMARY_KEY_LENGTH; i++)
			primary = "0" + primary;
		File newMessage = new File("server" + File.separatorChar + "messages" + 
				File.separatorChar + contact.getUserName() + "." +
				primary + ".xml");
		FileOutputStream fos = new FileOutputStream(newMessage);
		ContentHandler hd = init(fos);
		//write start tag
		hd.startDocument();
		AttributesImpl atts = new AttributesImpl();
		hd.startElement("", "", "Message", atts);
		writeElem(hd, "Sender", message.getSender(), atts);
		writeElem(hd, "Subject", message.getSubject(), atts);
		writeElem(hd, "Date", message.getDate(), atts);
		String recievers[] = message.getRecievers();
		hd.startElement("","","Receivers",atts);
		for (int i = 0; i < recievers.length; i++)
		{
			hd.startElement("", "", "Receiver", atts);
			hd.characters(recievers[i].toCharArray(), 0, recievers[i].length());
			hd.endElement("", "", "Receiver");
		}
		hd.endElement("","","Receivers");
		writeElem(hd, "Body", message.getBody().toString(), atts);
		String ptCount = "";
		for (int i = 0; i < Constants.PRIMARY_KEY_LENGTH; i++)
			ptCount += "0";
		writeElem(hd, "Pointers", ptCount, atts);
		hd.endElement("", "", "Message");
		fos.close();
		return newMessage;
	}


	/**
	 * @param        path
	 * @param        contact
	 */
	public void copyAttachment( String path, Contact contact )
	{
		
	}


}
