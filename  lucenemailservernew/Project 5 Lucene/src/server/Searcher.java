package server;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.ScoreDocComparator;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortComparatorSource;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.xml.sax.SAXException;

import server.message.MessageRecord;
import server.message.MessageRecordXMLReader;

/**
 * Class Searcher
 */
public class Searcher 
{
	//
	// Fields
	//
	private static final String DEFAULT_FIELD = "Body";
	private static Searcher instance;
	
	//
	// Constructors
	//
	private Searcher()
	{ 
		
	};
	
	//
	// Methods
	//
	public static Searcher getInstance()
	{
		if(instance == null)
			instance = new Searcher();
		return instance;
	}
	//
	// Accessor methods
	//

	//
	// Other methods
	//

	/** 
	 * @return       MessageRecord
	 * @param        query
	 * @throws Exception
	 */
	public MessageRecord[] search(String userName, String queryString, final int start, final int end) throws Exception
	{	
		int numberOfResults = end - start;
		QueryParser parser = new QueryParser(DEFAULT_FIELD, new StandardAnalyzer());
		Query query = parser.parse(queryString);
		final IndexSearcher searcher = new IndexSearcher(FSDirectory.getDirectory(Constants.ACCOUNTS_PATH
				+ userName + File.separatorChar + "indexFiles"));
//		HitCollectorWrapper wrapper = new HitCollectorWrapper(searcher, start, end);
		TopDocs docs = searcher.search(query, null, 20, new Sort(new SortField("Date", new SortComparatorSource()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public ScoreDocComparator newComparator(IndexReader arg0,
					String arg1) throws IOException
			{
				return new ScoreDocComparator()
				{
					@Override
					public int compare(ScoreDoc scoreDoc1, ScoreDoc scoreDoc2) 
					{
						try {
							Document doc1 = searcher.doc(scoreDoc1.doc);
							Document doc2 = searcher.doc(scoreDoc2.doc);
							Date date1 = DateFormat.getInstance().parse((doc1.get("Date")));
							Date date2 = DateFormat.getInstance().parse((doc2.get("Date")));
							return date1.compareTo(date2);
						} catch (CorruptIndexException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ParseException e) {
							e.printStackTrace();
						}
						return 0;
					}

					@Override
					public int sortType()
					{
						return SortField.CUSTOM;
					}

					@SuppressWarnings("unchecked")
					@Override
					public Comparable sortValue(ScoreDoc arg0) {
						return null;
					}
					
				};
			}
			
		}, true)));
//		Hits hits = searcher.search(query);
//		for (int i = 0; i < hits.length(); i ++)
//			System.out.println(hits.doc(i));
//		int numFound = wrapper.getNumFound();
		int numFound = docs.totalHits;
		if (numFound == 0)
			throw new Exception("No results to preview");
//		final Document []docs = wrapper.getDocuments();
//		numberOfResults = Math.min(numFound, numberOfResults);
		numberOfResults = Math.min(numberOfResults, numFound);
		MessageRecord[] results = new MessageRecord[numberOfResults];
		for(int i = 0; i < numberOfResults; i++)
		{
			ScoreDoc scoreDoc = docs.scoreDocs[i + start];
			Document doc = searcher.doc(scoreDoc.doc);
			String path = doc.get("Path");//7
			MessageRecord temp = createMessageRecord(path);
			temp.setFolder(doc.get("Folder"));
			results[i] = temp;
		}
		return results;
	}

	private MessageRecord createMessageRecord(String messagePath) throws SAXException, IOException, ParserConfigurationException
	{
		MessageRecordXMLReader recordReader = new MessageRecordXMLReader(Constants.MESSAGES_PATH + messagePath);
		MessageRecord record = recordReader.beginParsing();
		String s = messagePath.substring(0, messagePath.lastIndexOf('.'));
		record.setPrimaryKey(Integer.parseInt(s.substring(s.lastIndexOf('.') + 1)));
		return record;
	}

	/**
	 * @return       MessageRecord
	 * @param        fields
	 * @param        values
	 * @throws Exception 
	 */
	public MessageRecord[] advancedSearch(String userName, String[] fields, String[] values, final int start, final int end) throws Exception
	{
		if (fields.length != values.length)
			throw new Exception("Fields numbers should be equal to values numbers");
		int numberOfResults = end - start;
		StringBuffer buff = new StringBuffer();
		for (int i = 0, n = fields.length; i < n; i++)
		{
			buff.append(fields[i] + ":" + values[i]);
		}
		QueryParser parser = new QueryParser(DEFAULT_FIELD, new StandardAnalyzer());
		Query query = parser.parse(buff.toString());
		final IndexSearcher searcher = new IndexSearcher(FSDirectory.getDirectory(Constants.ACCOUNTS_PATH
				+ userName + File.separatorChar + "indexFiles"));
		HitCollectorWrapper wrapper = new HitCollectorWrapper(searcher, start, end);
		searcher.search(query, wrapper);
		int numFound = wrapper.getNumFound();
		final Document []docs = wrapper.getDocuments();
		if (numFound == 0)
			throw new Exception("No results to preview");
		MessageRecord[] results = new MessageRecord[numFound];
		for(int i = 0; i < numberOfResults; i++)
		{
			String messagePath = docs[i].get("Path");
			results[i] = createMessageRecord(messagePath);
		}
		return results;
	}

	private class HitCollectorWrapper extends HitCollector
	{
		private int count = 0;
		private int start, end;
		private int i = 0;
		private Document[] docs;
		private org.apache.lucene.search.Searcher searcher;

		public HitCollectorWrapper(org.apache.lucene.search.Searcher searcher,
				int start, int end)
		{
			this.searcher = searcher;
			this.start = start;
			this.end = end;
			docs =  new Document[end - start];
		}

		public Document[] getDocuments()
		{
			return docs;
		}

		public void collect(int docNum, float score)
		{	
			if (count >= start && count < end)
				try {
					docs[i++] = searcher.doc(i);
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			count++;
		}

		public int getNumFound()
		{
			return count - start;
		}
	}
}
