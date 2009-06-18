package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

import server.message.Message;
import server.message.MessageRecord;
import server.message.MessageWriter;
import server.parser.ExtensionHandler;


/**
 * Class Indexer
 */

public class Indexer
{
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
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	public void deleteMessage( String userName, MessageRecord[] messages) throws CorruptIndexException, IOException
	{
		IndexReader reader = IndexReader.open(FSDirectory.getDirectory(Constants.ACCOUNTS_PATH+userName+File.separatorChar+"indexFiles"));
		for(int i = 0, n = messages.length; i < n; i++)
		{
			String str = "" + messages[i].getPrimaryKey();
			for(int j = str.length(); j < Constants.PRIMARY_KEY_LENGTH; j++)
				str = "0" + str;
			Term wanted = new Term("PrimaryKey", messages[i].getSender() + "." + str);
			updateMessagePointers(Constants.MESSAGES_PATH + messages[i].getSender()
					+ "." + str + ".xml",-1);
			for (String attachment : messages[i].getAttachmentNames())
			{
				updateAttachmentPointer(attachment, -1);
			}
			reader.deleteDocuments(wanted);
		}
		reader.close();
	}


	private void updateAttachmentPointer(String attachment, int i) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(attachment + ".inf"));
		int wanted = Integer.parseInt(in.readLine()) + i;
		in.close();
		if (wanted == 0)
		{
			new File(attachment).delete();
			new File(attachment + ".inf").delete();
			return;
		}
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(attachment + ".inf")));
		writer.println(wanted);
		writer.close();
	}

	private void updateMessagePointers(String path, int num) throws IOException
	{
		FileChannel fileChannel = new RandomAccessFile(path, "rw").getChannel();
		File file = new File(path);
		fileChannel.position(file.length() - 29);
		ByteBuffer b = ByteBuffer.allocate(Constants.PRIMARY_KEY_LENGTH);
		fileChannel.read(b);
		byte[] byteArray = b.array();
		int pt = 0;
		for (int i = byteArray.length - 1;i >= 0;i--)
			pt += (byteArray[i]-'0')*Math.pow(10, byteArray.length - i - 1);
		pt += num;
		if (pt == 0)
		{
			fileChannel.close();
			return;
		}
		String str = pt + "";
		for (int i = str.length(); i < Constants.PRIMARY_KEY_LENGTH; i++)
			str = "0" + str;
		byte[] buff = new byte[Constants.PRIMARY_KEY_LENGTH];
		for (int i = 0; i < Constants.PRIMARY_KEY_LENGTH; i++)
			buff[i] = (byte) str.charAt(i);
		fileChannel.position(file.length() - 29);
		fileChannel.write(ByteBuffer.wrap(buff));
		fileChannel.close();
	}

	/**
	 * @param        message
	 * @param        sender
	 * @throws Exception 
	 */
	public void addMessage( Message message, Contact sender, Contact[] receivers ) throws Exception
	{
		String[] attachNames = MessageWriter.getInstance().copyAttachment(sender, receivers.length + 1);
		MessageWriter.getInstance().copyMessage(message, attachNames, sender);
		message.setPrimaryKey(sender.getPrimarySent());
		indexMessage(message, sender, "Sent");
		String str = "" + message.getPrimaryKey();
		for(int j = str.length(); j < Constants.PRIMARY_KEY_LENGTH; j++)
			str = "0" + str;
		String primary = sender.getUserName() + "." + str;
		if (attachNames == null)
		{
			for (Contact contact : receivers) 
			{
				indexMessage(message, contact, "Inbox");
			}	
		}
		else
		{
			indexAttachments(primary, attachNames, sender);
			for (Contact contact : receivers) 
			{
				indexMessage(message, contact, "Inbox");
				indexAttachments(primary, attachNames, contact);
			}
		}
	}


	private void indexAttachments(String primaryKey, String[] attachNames,
			Contact contact) throws Exception 
	{
		File userIndex = new File(Constants.ACCOUNTS_PATH + contact.getUserName() + File.separatorChar
				+ "indexFiles");
		IndexWriter indexWriter = new IndexWriter(FSDirectory
				.getDirectory(userIndex), new StandardAnalyzer(), false,
				IndexWriter.MaxFieldLength.UNLIMITED);
		for (String attachmentName : attachNames) 
		{
			ExtensionHandler handler = new ExtensionHandler();
			Document doc = handler.parse(Constants.Attachments_PATH + attachmentName);
			//name of the msg
			doc.add(new Field("PrimaryKey", primaryKey, Field.Store.YES, Field.Index.NOT_ANALYZED));
			//name of the attachment file in attachments folder
			doc.add(new Field("AttachmentName", attachmentName, Field.Store.YES, Field.Index.NOT_ANALYZED));
			indexWriter.addDocument(doc);
		}
		indexWriter.optimize();
		indexWriter.close();
	}

	public void indexMessage(Message message, Contact sender, String folder) throws CorruptIndexException, IOException
	{
		File userIndex = new File(Constants.ACCOUNTS_PATH + sender.getUserName() + File.separatorChar
				+ "indexFiles");
		boolean create = false;
		if (!(userIndex.exists())) {
			userIndex.mkdir();
			create = true;
		}
		String str = "" + message.getPrimaryKey();
		for(int j = str.length(); j < Constants.PRIMARY_KEY_LENGTH; j++)
			str = "0" + str;
		IndexWriter indexWriter = new IndexWriter(FSDirectory
				.getDirectory(userIndex), new StandardAnalyzer(), create,
				IndexWriter.MaxFieldLength.UNLIMITED);

		Document document = new Document();
		document.add(new Field("Sender", message.getSender(), Field.Store.YES,
				Field.Index.ANALYZED));
		document.add(new Field("Date", message.getDate(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		String primary = message.getSender() + "." + str;
		document.add(new Field("PrimaryKey", primary, Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		document.add(new Field("Folder", folder, Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		String recieversString = new String();
		String recievers[] = message.getRecievers();
		recieversString = recievers[0];
		for (int i = 1; i < recievers.length; i++)
			recieversString += "\n" + recievers[i];
		document.add(new Field("Recievers", recieversString, Field.Store.YES,
				Field.Index.ANALYZED));
		document.add(new Field("Subject", message.getSubject(),
				Field.Store.YES, Field.Index.ANALYZED));
		document.add(new Field("Body", message.getBody().toString(),
				Field.Store.NO, Field.Index.ANALYZED));
		document.add(new Field("Path", message.getSender()+ "." + str + ".xml", Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		indexWriter.addDocument(document);
		indexWriter.optimize();
		indexWriter.close();
		
		updateMessagePointers(Constants.MESSAGES_PATH + message.getSender()+ "." + str + ".xml", 1);
	}
}
