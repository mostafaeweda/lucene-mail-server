package server.message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MessageDetailedViewXMLReader extends DefaultHandler {
	private Message msg;
	private Body body;
	private String url;
	private StringBuffer buf;
	private ArrayList<StyleRange> styles;
	private int counter;
	private boolean inRecievers;
	private ArrayList<String> allRecievers;

	public MessageDetailedViewXMLReader(String url) {
		this.url = url;
		this.msg = new Message();
		counter = 0;
		inRecievers = false;
		body = new Body(null, null);
		allRecievers = new ArrayList<String>();
		styles = new ArrayList<StyleRange>();
		buf = new StringBuffer();
	}

	public Message beginParsing() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		try {
			sp = spf.newSAXParser();
			sp.parse(new File(url), this);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		body.setStyleRanges(styles.toArray(new StyleRange[styles.size()]));
		msg.setBody(body);
		return msg;
	}

	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
		buf.setLength(0);
		if (counter == 3)
			return;
		else if ("Style".equalsIgnoreCase(name))
		{
			StyleRange range = new StyleRange();
			range.start = Integer.parseInt(atts.getValue(0));
			range.length = Integer.parseInt(atts.getValue(1));
			String f = atts.getValue(2);
			if (!"null".equals(f)) {
				String[] fs = new String[3];
				fs[0] = f.substring(0, f.indexOf('+'));
				f = f.substring(f.indexOf('+') + 1);
				fs[1] = f.substring(0, f.indexOf('+'));
				fs[2] = f.substring(f.indexOf('+') + 1);
				range.font = new Font(Display.getCurrent(), fs[0],
						(int) Double.parseDouble(fs[1]), Integer.parseInt(fs[2]));
			}
			range.underline = Boolean.parseBoolean(atts.getValue(3));
			f = atts.getValue(4);
			if (!"null".equals(f)) {
				String[] fs = new String[3];
				fs[0] = f.substring(0, f.indexOf('+'));
				f = f.substring(f.indexOf('+') + 1);
				fs[1] = f.substring(0, f.indexOf('+'));
				fs[2] = f.substring(f.indexOf('+') + 1);
				range.foreground = new Color(Display.getCurrent(), Integer
						.parseInt(fs[0]), Integer.parseInt(fs[1]), Integer
						.parseInt(fs[2]));
			}
			styles.add(range);
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		buf.append(ch, start, length);
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException {
		switch (counter)
		{
		case 0:
			msg.setSender(buf.toString());
			break;
		case 1:
			msg.setSubject(buf.toString());
			break;
		case 2:
			msg.setDate(buf.toString());
			break;
		default:
			if ("receiver".equalsIgnoreCase(name)) {
				allRecievers.add(buf.toString());
			} else if ("receivers".equalsIgnoreCase(name)) {
				msg.setRecievers(allRecievers.toArray(new String[allRecievers
				                         						.size()]));
			} else if ("body".equalsIgnoreCase(name)) {
				counter++;
			} else if ("text".equalsIgnoreCase(name)) {
				body.setText(buf.toString());
			}
		}
		counter++;
	}

	public static void main(String[] args)
	{
		Display display = new Display();
		Message message = new MessageDetailedViewXMLReader("bodytoto.xml").beginParsing();
		System.out.println();
	}
}