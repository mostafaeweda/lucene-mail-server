package server.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

public class RTFParser implements DocumentHandler 
{
	public Document getDocument(InputStream is) throws DocumentHandlerException 
	{
		String bodyText = null;
		DefaultStyledDocument styledDoc = new DefaultStyledDocument();
		try 
		{
			new RTFEditorKit().read(is, styledDoc, 0);
			bodyText = styledDoc.getText(0, styledDoc.getLength());
		} 
		catch (IOException e) 
		{
			throw new DocumentHandlerException(
					"Cannot extract text from a RTF document", e);
		} catch (BadLocationException e) {
			throw new DocumentHandlerException(
					"Cannot extract text from a RTF document", e);
		}
		if (bodyText != null) {
			Document doc = new Document();
			doc.add(new Field("body", bodyText, Field.Store.NO,
					Field.Index.ANALYZED));
			return doc;
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		RTFParser handler = new RTFParser();
		Document doc = handler.getDocument(new FileInputStream(
				new File("lolo.rtf")));
		System.out.println(doc);
	}
}