package Server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;


/**
 * Class XMLProfileWriter write profile to an XML file
 */
public class XMLProfileWriter 
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
	public void writeProfile(Profile profile, String id) throws SAXException, IOException
	{
		File newUserDir = new File("server/accounts/" + profile.getUserName() + "/profile");
		newUserDir.mkdirs();
		newUserDir = new File("server/accounts/" + profile.getUserName() + "/profile/info.xml");
		FileOutputStream fos = new FileOutputStream(newUserDir);
		OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
		of.setIndent(5);//set indentation dfor XML tags
		of.setIndenting(true);
		//create XML serializer with file output stream and output format
		XMLSerializer serializer = new XMLSerializer(fos,of);
		//get content handler to handle tags in XML doc
		ContentHandler hd = serializer.asContentHandler();
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
	}
	
	private void writeElem(ContentHandler hd, String tag, String characters, AttributesImpl atts) throws SAXException
	{
		hd.startElement("","",tag,atts);
		hd.characters(characters.toCharArray(),0,characters.length());
		hd.endElement("","",tag);
	}
}
