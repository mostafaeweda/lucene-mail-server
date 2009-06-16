package server;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SignInHandler 
{
	private static SignInHandler instance; 
	
	private SignInHandler()
	{
		
	}
	
	public static SignInHandler getInstance()
	{
		if (instance == null)
			instance = new SignInHandler();
		return instance;
	}
	
	public Contact signIn(String userName, final String password, String IP) throws Exception
	{
		final Contact result = new Contact();
		result.setIP(IP);
		File profile = new File(Constants.ACCOUNTS_PATH + userName + File.separatorChar
				+ "profile" + File.separatorChar + "info.xml");
		if (! profile.exists())
			throw new Exception("There is no user with this user name");
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		DefaultHandler handler = new DefaultHandler() {
			String body;
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				body = "";
			}

			public void characters(char ch[], int start, int length)
					throws SAXException {
				body = new String(ch).substring(start, start + length);
			}
			
			public void endElement(String uri, String localName,
					String qName) throws SAXException {
				if (qName.equals("UserName")) 
					result.setUserName(body);
				else if (qName.equals("Password"))
					if (! password.equalsIgnoreCase(body)) {
						result.setUserName(null);
					}
			}
		};
		saxParser.parse(profile, handler);
		if (result.getUserName() == null)
			throw new Exception("Wrong Password");
		File contactList = new File(Constants.ACCOUNTS_PATH + userName + File.separatorChar
				+ "profile" + File.separatorChar + "contactList.xml");
		if (contactList.exists())
		{
			final ArrayList<String> list = new ArrayList<String>();
			factory = SAXParserFactory.newInstance();
			saxParser = factory.newSAXParser();
			handler = new DefaultHandler() {
				String userName;
				public void startElement(String uri, String localName,
						String qName, Attributes attributes) throws SAXException {
					userName = "";
					
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					userName += userName.substring(start, start + length);
				}
				
				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					if (qName.equals("Contact")) 
						list.add(userName);
				}
				
			};
			saxParser.parse(contactList, handler);
			String[] listArr = new String[list.size()];
			list.toArray(listArr);
			result.setContactList(listArr);
		}
		result.changeStatus();
		return result;
	}
	
}
