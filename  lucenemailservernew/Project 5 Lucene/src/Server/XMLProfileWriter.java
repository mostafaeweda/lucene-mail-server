package Server;

import java.io.File;

import org.xml.sax.ContentHandler;


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
	 */
	public void writeProfile(Profile profile, String id)
	{
		File newUserDir = new File("server\\accounts\\" + profile.getUserName() + "\\profile\\info.xml");
		newUserDir.mkdirs();
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
		writeElem(hd, "ID", id, atts);
		/*
		 * encode
		 * original character * 2 - seed = incrypted character
		 * decode
		 * (incrypted character + seed) / 2 = original character
		 */
		writeElem(hd, "Password", profile.getPassword(), atts);
		writeElem(hd, "First Name", profile.getFirstName(), atts);
		writeElem(hd, "Last Name", profile.getLastName(), atts);
		writeElem(hd, "Gender", profile.getGender(), atts);
		writeElem(hd, "DateBrith", profile.getDateBrith(), atts);
		writeElem(hd, "SecretQuestion", profile.getSecretQuestion(),atts);
		writeElem(hd, "SecretAnswer", pprofile.getSecretAnswer(),atts);
		hd.endElement("", "", "USER");
		fos.close();
	}
	
	private void writeElem(ContentHandler hd, String tag, String characters, Attributes atts)
	{
		hd.startElement("","",tag,atts);
		hd.characters(characters.toCharArray(),0,characters.length());
		hd.endElement("","",tag);
	}
}
