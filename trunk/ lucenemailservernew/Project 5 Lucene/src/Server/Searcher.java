package Server;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;
import org.xml.sax.SAXException;

/**
 * Class Searcher
 */
public class Searcher 
{
	//
	// Fields
	//
	private static final String DEFAULT_FIELD = "body";
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
	 * TODO To handle all 
	 * @return       MessageRecord
	 * @param        query
	 * @throws Exception
	 */
	public MessageRecord[] search(String userName, String queryString, final int start, final int end) throws Exception
	{
		final int numberOfResults = end - start;
		QueryParser parser = new QueryParser(DEFAULT_FIELD, new StandardAnalyzer());
		Query query = parser.parse(queryString);
		final IndexSearcher searcher = new IndexSearcher(FSDirectory.getDirectory(userName));
		HitCollectorWrapper wrapper = new HitCollectorWrapper(searcher, start, end);
		searcher.search(query, wrapper);
		int numFound = wrapper.getNumFound();
		if (numFound == 0)
			throw new Exception("No results to preview");
		final Document []docs = wrapper.getDocuments();
		MessageRecord[] results = new MessageRecord[numberOfResults];
		for(int i = 0; i < numberOfResults; i++)
		{
			String messagePath = docs[i].get("path");
			results[i] = createMessageRecord(messagePath);
		}
		return results;
	}

	private MessageRecord createMessageRecord(String messagePath) throws SAXException, IOException, ParserConfigurationException
	{
		MessageRecordXMLReader recordReader = new MessageRecordXMLReader(messagePath);
		return recordReader.beginParsing();
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
		final IndexSearcher searcher = new IndexSearcher(FSDirectory.getDirectory(userName));
		HitCollectorWrapper wrapper = new HitCollectorWrapper(searcher, start, end);
		searcher.search(query, wrapper);
		int numFound = wrapper.getNumFound();
		final Document []docs = wrapper.getDocuments();
		if (numFound == 0)
			throw new Exception("No results to preview");
		MessageRecord[] results = new MessageRecord[numFound];
		for(int i = 0; i < numberOfResults; i++)
		{
			String messagePath = docs[i].get("path");
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
