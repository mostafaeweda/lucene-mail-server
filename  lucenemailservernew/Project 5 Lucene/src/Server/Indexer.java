package Server;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.xml.sax.SAXException;


/**
 * Class Indexer
 */
public class Indexer {

	private static Indexer instance; //  stands for a Singleton Object of the class
	
	private  Indexer () { };

	public static Indexer getInstance()
	{
		if(instance == null)
			instance = new Indexer();
		return instance;
	}

	/**
	 * @param        userName
	 * @param        primarykeys
	 */
	public void deleteMessage( String userName, int[] primarykeys )
	{
	}


	/**
	 * @param        message
	 * @param        contact
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public void addMessage( Message message, Contact contact ) throws IOException, SAXException
	{
		File newMessage = MessageWriter.getInstance().copyMessage(message, contact);
		File userIndex = new File("server"+File.separator+"accounts"+File.separator+contact.getUserName() + File.separator + "indexFiles");
		boolean create = false;
		if (! (userIndex.exists()))
		{
			userIndex.mkdir();
			create = true;
		}
		IndexWriter indexWriter = new IndexWriter(FSDirectory.getDirectory(userIndex),new StandardAnalyzer()
			,create, IndexWriter.MaxFieldLength.UNLIMITED);
		
		Document document = new Document();
		document.add(new Field("Sender", message.getSender(), Field.Store.YES, Field.Index.ANALYZED));
		document.add(new Field("Date", message.getDate(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		String recieversString = new String();
		String recievers[]  = message.getRecievers();
		recieversString = recievers[0];
		for(int i = 1 ; i<recievers.length;i++)
			recieversString = "\n" + recieversString+recievers[i];
		document.add(new Field("Recievers", recieversString, Field.Store.YES, Field.Index.ANALYZED));
		document.add(new Field("Subject", message.getSubject(), Field.Store.YES, Field.Index.ANALYZED));
		document.add(new Field("Body", message.getBody().toString(), Field.Store.NO, Field.Index.ANALYZED));
		document.add(new Field("Path", newMessage.getAbsolutePath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		indexWriter.addDocument(document);
		indexWriter.optimize();
		indexWriter.close();
	}


	/**
	 * @param        primaryKey
	 */
	public void getMessageContent( int primaryKey )
	{
		
		
	}


}
