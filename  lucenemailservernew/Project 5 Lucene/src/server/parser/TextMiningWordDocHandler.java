package server.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

public class TextMiningWordDocHandler implements DocumentHandler {
	public Document getDocument(InputStream is) throws DocumentHandlerException {
		String bodyText = null;
		try {
			bodyText = new WordExtractor().extractText(is);
		} catch (Exception e) {
			throw new DocumentHandlerException(
					"Cannot extract text from a Word document", e);
		}
		if ((bodyText != null) && (bodyText.trim().length() > 0)) {
			Document doc = new Document();
			doc.add(new Field("body", bodyText, Field.Store.NO,
					Field.Index.ANALYZED));
			return doc;
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		TextMiningWordDocHandler handler = new TextMiningWordDocHandler();
		Document doc = handler.getDocument(new FileInputStream(
				new File(args[0])));
		System.out.println(doc);
	}
}