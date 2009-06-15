package server.parser;

import java.io.IOException;
import java.io.InputStream;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.pdfbox.Decrypt;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.exceptions.CryptographyException;
import org.pdfbox.exceptions.InvalidPasswordException;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDDocumentInformation;
import org.pdfbox.pdmodel.encryption.DecryptionMaterial;
import org.pdfbox.util.PDFTextStripper;

public class PDFTrial implements DocumentHandler
{
	public static String password = "-password";

	public PDFTrial() {
		// keyword --> Field.Index.NOT_ANALYZED_NO_NORMS
		// Unstored --> Field.Index.ANALYZED_NO_NORMS
		// Analysed - Index the tokens produced by running the field's value
		// through an Analyzer. --> Field.Index.Analysed
		// static Field.Store COMPRESS
		// Store the original field value in the index in a compressed form.
		// static Field.Store NO
		// Do not store the field value in the index.
		// static Field.Store YES
		// Store the original field value in the index.

	}

	public Document getDocument(InputStream is) throws DocumentHandlerException {
		COSDocument cosDoc = null;
		try {
			cosDoc = parseDocument(is);
		} catch (IOException e) {
			closeCOSDocument(cosDoc);
			throw new DocumentHandlerException("Cannot parse PDF document", e);
		}
		// decrypt the PDF document, if it is encrypted
		try {
			if (cosDoc.isEncrypted()) {
				Decrypt decrypt = new Decrypt();
				DecryptDocument decryptor = new DecryptDocument(cosDoc);
				decryptor.decryptDocument(password);
			}
		} catch (CryptographyException e) {
			closeCOSDocument(cosDoc);
			throw new DocumentHandlerException("Cannot decrypt PDF document", e);
		} catch (InvalidPasswordException e) {
			closeCOSDocument(cosDoc);
			throw new DocumentHandlerException("Cannot decrypt PDF document", e);
		} catch (IOException e) {
			closeCOSDocument(cosDoc);
			throw new DocumentHandlerException("Cannot decrypt PDF document", e);
		}
		// extract PDF document's textual content
		String docText = null;
		try {
			PDFTextStripper stripper = new PDFTextStripper();
			docText = stripper.getText(new PDDocument(cosDoc));
		} catch (IOException e) {
			closeCOSDocument(cosDoc);
			throw new DocumentHandlerException("Cannot parse PDF document", e);
		}
		Document doc = new Document();
		if (docText != null) {
			// doc.add(Field.("body", docText));
		}
		// extract PDF document's meta-data
		PDDocument pdDoc = null;
		try {
			PDDocumentInformation docInfo = pdDoc.getDocumentInformation();
			String author = docInfo.getAuthor();
			String title = docInfo.getTitle();
			String keywords = docInfo.getKeywords();
			String summary = docInfo.getSubject();
			if ((author != null) && !author.equals("")) {
				// Text
				doc.add(new Field("author", author, Field.Store.YES,
						Field.Index.ANALYZED));
			}
			if ((title != null) && !title.equals("")) {
				// Text
				doc.add(new Field("title", title, Field.Store.YES,
						Field.Index.ANALYZED));
			}
			if ((keywords != null) && !keywords.equals("")) {
				// Text
				doc.add(new Field("keywords", keywords, Field.Store.YES,
						Field.Index.ANALYZED));
			}
			if ((summary != null) && !summary.equals("")) {
				// Text
				doc.add(new Field("summary", summary, Field.Store.YES,
						Field.Index.ANALYZED));
			}
		} catch (Exception e) {
			closeCOSDocument(cosDoc);
			closePDDocument(pdDoc);
			System.err.println("Cannot get PDF document meta-data: "
					+ e.getMessage());
		}
		return doc;
	}

	private static COSDocument parseDocument(InputStream is) throws IOException {
		PDFParser parser = new PDFParser(is);
		parser.parse();
		return parser.getDocument();
	}

	private void closeCOSDocument(COSDocument cosDoc) {
		if (cosDoc != null) {
			try {
				cosDoc.close();
			} catch (IOException e) {
				// eat it, what else can we do?
			}
		}
	}

	private void closePDDocument(PDDocument pdDoc) {
		if (pdDoc != null) {
			try {
				pdDoc.close();
			} catch (IOException e) {
				// eat it, what else can we do?
			}
		}
	}
}
