package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class XMLContactListWriter extends XMLwriter
{
	/**
	 * a singleton instance of the class
	 */
	private static XMLContactListWriter instance;
	
	private XMLContactListWriter () {}
	
	public static XMLContactListWriter getInstance()
	{
		if (instance == null)
			instance = new XMLContactListWriter();
		return instance;
	}
	
	/**
	 * write the ContactList informations in an XML File
	 * @param ContactList contains information of the new user
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public void writeContactList(String[] contactList, String userName) throws SAXException, IOException
	{
		File newUserDir = new File("server" + File.separatorChar + "accounts" + File.separatorChar 
				+ userName + File.separatorChar + "profile");
		newUserDir.mkdirs();
		newUserDir = new File("server" + File.separatorChar + "accounts" + File.separatorChar 
				+ userName + File.separatorChar + "profile" + File.separatorChar + "contactList.xml");
		FileOutputStream fos = new FileOutputStream(newUserDir);
		ContentHandler hd = init(fos);
		//write start tag
		hd.startDocument();
		AttributesImpl atts = new AttributesImpl();
		hd.startElement("", "", "ContactList", atts);
		for (int i = 0; i < contactList.length; i++)
		{
			writeElem(hd, "Contact", contactList[i], atts);
		}
		hd.endElement("", "", "ContactList");
		fos.close();
	}
}