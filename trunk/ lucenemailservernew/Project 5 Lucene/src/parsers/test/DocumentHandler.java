package parsers.test;

import java.io.InputStream;

import org.apache.lucene.document.Document;

public interface DocumentHandler
{
	public Document getDocument(InputStream in) throws DocumentHandlerException;
}
