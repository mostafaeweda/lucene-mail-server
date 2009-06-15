package server.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

public class PlainTextParser implements DocumentHandler {
	public Document getDocument(InputStream is) throws DocumentHandlerException {
		String bodyText = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				bodyText += line;
			}
			br.close();
		} catch (IOException e) {
			throw new DocumentHandlerException("Cannot read the text document",
					e);
		}
		if (!bodyText.equals("")) {
			Document doc = new Document();
			doc.add(new Field("body", bodyText, Field.Store.NO,
					Field.Index.ANALYZED));
			return doc;
		}
		return null;
	}

	public static void main(String[] args) throws Exception
	{
		PlainTextParser handler = new PlainTextParser();
		Document doc = handler.getDocument(new FileInputStream(
				new File("sossa.txt")));
		System.out.println(doc);
	}
}