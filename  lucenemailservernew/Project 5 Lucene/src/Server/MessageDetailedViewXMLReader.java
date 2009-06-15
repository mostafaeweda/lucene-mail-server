package Server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MessageDetailedViewXMLReader extends DefaultHandler
{
	private Message msg;
	private String url;
	private StringBuffer buf;
	private int counter;
	private boolean inRecievers;
	private ArrayList<String> allRecievers;
	
	public MessageDetailedViewXMLReader(String url)
	{
		this.url = url;
		this.msg = new Message();
		counter = 0;
		inRecievers = false;
		allRecievers = new ArrayList<String>();
		buf = new StringBuffer();
	}
	
	public void beginParsing()
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
	}
	
	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException 
	{
		if(counter == 3)
			inRecievers = true;
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException 
	{
		buf.append(ch,start,length);
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException 
	{
		switch(counter)
		{
			case 0:
				msg.setSender(buf.toString());
				counter++;
				break;
			case 1:
				msg.setSubject(buf.toString());
				counter++;
				break;
			case 2:
				msg.setDate(buf.toString());
				counter++;
				break;
			default:
				if(!"reciever".equalsIgnoreCase(name))
				{
					msg.setRecievers(allRecievers.toArray(new String[allRecievers.size()]));
					inRecievers = false;
					counter++;
				}
				else if(inRecievers)
				{
					allRecievers.add(buf.toString());
				}
				else if("body".equalsIgnoreCase(name))
				{
					msg.setBody(new Body(buf.toString()));
					counter++;
				}
		}
		buf.setLength(0);
	}
}