package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public abstract class XMLwriter {
	
	protected File file;
	
	protected ContentHandler init(FileOutputStream fos) throws IOException
	{
		OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
		of.setIndent(5);//set indentation dfor XML tags
		of.setIndenting(true);
		//create XML serializer with file output stream and output format
		XMLSerializer serializer = new XMLSerializer(fos,of);
		//get content handler to handle tags in XML doc
		ContentHandler hd = serializer.asContentHandler();
		return hd;
	}
	
	protected void writeElem(ContentHandler hd, String tag, String characters, AttributesImpl atts) throws SAXException
	{
		hd.startElement("","",tag,atts);
		hd.characters(characters.toCharArray(),0,characters.length());
		hd.endElement("","",tag);
	}
	
	

}
