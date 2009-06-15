package server.parser;

import java.io.FileInputStream;

import org.apache.lucene.document.Document;

public class ExtensionHandler
{
	public Document parse(String path) throws Exception
	{
		DocumentHandler handler;
		String ext = path.substring(path.lastIndexOf('.'));
		if (ext.equalsIgnoreCase("doc"))
		{
			handler = new TextMiningWordDocHandler();
		}
		else if (ext.equalsIgnoreCase("pdf"))
		{
			handler = new PDFTrial();
		}
		else if (ext.contains("htm"))
		{
			handler = new JTidyHTMLHandler();
		}
		else if (ext.equalsIgnoreCase("rtf"))
		{
			handler = new RTFParser();
		}
		else if (ext.equals("txt"))
		{
			handler = new PlainTextParser();
		}
		else
			throw new Exception("Supported Extensions are only \"*.pdf\"," +
					"\"*.txt\", \"*.doc\", \"*.htm\", \"*.html\", \"*.xhtml\"");
		return handler.getDocument(new FileInputStream(path));
	}
}
