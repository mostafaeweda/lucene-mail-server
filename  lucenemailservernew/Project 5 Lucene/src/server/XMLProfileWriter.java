package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


/**
 * Class XMLProfileWriter write profile to an XML file
 */
public class XMLProfileWriter extends XMLwriter
{
	/**
	 * a singleton instance of the class
	 */
	private static XMLProfileWriter instance;
	
	private XMLProfileWriter () {}
	
	public static XMLProfileWriter getInstance()
	{
		if (instance == null)
			instance = new XMLProfileWriter();
		return instance;
	}

	
	/**
	 * write the profile informations in an XML File
	 * @param profile contains information of the new user
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public Contact writeProfile(Profile profile, String id) throws SAXException, IOException
	{
		File newUserDir = new File("server" + File.separatorChar + "accounts" + File.separatorChar 
				+ profile.getUserName() + File.separatorChar + "profile");
		newUserDir.mkdirs();
		newUserDir = new File("server" + File.separatorChar + "accounts" + File.separatorChar 
				+ profile.getUserName() + File.separatorChar + "profile" + File.separatorChar + "info.xml");
		FileOutputStream fos = new FileOutputStream(newUserDir);
		ContentHandler hd = init(fos);
		//write start tag
		hd.startDocument();
		AttributesImpl atts = new AttributesImpl();
		hd.startElement("", "", "USER", atts);
		writeElem(hd, "UserName", profile.getUserName(), atts);
		writeElem(hd, "ID", id.toString(), atts);
		/*
		 * encode
		 * original character * 2 - seed = incrypted character
		 * decode
		 * (incrypted character + seed) / 2 = original character
		 */
		writeElem(hd, "Password", profile.getPassword(), atts);
		writeElem(hd, "FirstName", profile.getFirstName(), atts);
		writeElem(hd, "LastName", profile.getLastName(), atts);
		writeElem(hd, "Gender", profile.getGender(), atts);
		writeElem(hd, "DateBrith", profile.getDateBirth(), atts);
		writeElem(hd, "SecretQuestion", profile.getSecretQuestion(),atts);
		writeElem(hd, "SecretAnswer", profile.getSecretAnswer(),atts);
		hd.endElement("", "", "USER");
		fos.close();
		writeFolders(profile.getUserName(), new String[]{"Inbox", "Sent", "Spam"});
		Contact result = new Contact();
		result.setUserName(profile.getUserName());
		Properties props = new Properties();
		props.setProperty("Sent", "0");
		props.storeToXML(new FileOutputStream(Constants.ACCOUNTS_PATH +
				profile.getUserName() + File.separatorChar + "sent.xml"), "");
		return result;
	}
	
	public void writeFolders(String userName, String[] folders) throws SAXException, IOException
	{
		File newUserDir = new File("server" + File.separatorChar + "accounts" + File.separatorChar 
				+ userName + File.separatorChar + "profile" + File.separatorChar + "folders.xml");
		FileOutputStream fos = new FileOutputStream(newUserDir);
		ContentHandler hd = init(fos);
		AttributesImpl atts = new AttributesImpl();
		//write start tag
		hd.startDocument();
		hd.startElement("", "", "Folders", atts);
		for (int i = 0; i < folders.length; i++)
			writeElem(hd, "Folder", folders[i],atts);
		hd.endElement("", "", "Folders");
		fos.close();
	}
	
}
