package server;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ProfileXMLReader extends DefaultHandler
{
	private Profile record;
	private String url;
	private StringBuffer buf;
	private int counter;

	public ProfileXMLReader(String url)
	{
		this.url = url;
		this.record = new Profile();
		buf = new StringBuffer();
		counter = 0;
	}

	public Profile beginParsing()
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		try 
		{
			sp = spf.newSAXParser();
			sp.parse(new File(url), this);
		} 
		catch (SAXException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ParserConfigurationException e1) 
		{
			e1.printStackTrace();
		}
		return record;
	}

	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException 
	{
		buf.setLength(0);
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException 
	{
		buf.append(ch, start, length);
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException 
	{
		switch(counter)
		{
			case 0:
				record.setUserName(buf.toString());
				break;
			case 2:
				record.setPassword(buf.toString());
				break;
			case 3:
				record.setFirstName(buf.toString());
				break;
			case 4:
				record.setLastName(buf.toString());
				break;
			case 5:
				if (buf.toString().equals("Male"))
					record.setGender(1);
				else
					record.setGender(1);
				break;
			case 6:
				record.setDateBirth(buf.toString());
				break;
			case 7:
				record.setSecretQuestion(buf.toString());
				break;
			case 8:
				record.setSecretAnswer(buf.toString());
				break;
		}
		buf.setLength(0);
		counter++;
	}
}