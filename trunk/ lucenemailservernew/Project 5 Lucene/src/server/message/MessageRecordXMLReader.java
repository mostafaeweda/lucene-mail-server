package server.message;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MessageRecordXMLReader extends DefaultHandler
{
	private MessageRecord record;
	private String url;
	private StringBuffer buf;
	private int counter;

	public MessageRecordXMLReader(String url)
	{
		this.url = url;
		this.record = new MessageRecord();
		counter = 0;
	}

	public MessageRecord beginParsing()
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
		buf = new StringBuffer();
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException 
	{
		String str = new String(ch,start,length);
		buf.append(str);
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException 
	{
		switch(counter)
		{
			case 0:
				record.setSender(buf.toString());
				break;
			case 1:
				record.setSubject(buf.toString());
				break;
			case 2:
				record.setDate(buf.toString());
				break;
		}
		buf.setLength(0);
		counter++;
	}
}