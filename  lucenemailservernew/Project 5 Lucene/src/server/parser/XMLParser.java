package server.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler implements DocumentHandler
{
	private StringBuffer buff;

	public XMLParser()
	{
		buff = new StringBuffer();
	}

	@Override
	public Document getDocument(InputStream in) throws DocumentHandlerException
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		try 
		{
			sp = spf.newSAXParser();
			sp.parse(in, this);
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
		Document doc = new Document();
		System.out.println(buff.toString());
		doc.add(new Field("Body", buff.toString(), Field.Store.NO,
				Field.Index.ANALYZED));
		return doc;
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException 
	{
		buff.append(ch, start, length);
	}

	public static void main(String[] args) throws Exception
	{
		ExtensionHandler handler = new ExtensionHandler();
		System.out.println(handler.parse("bodytoto.xml"));
	}
}
