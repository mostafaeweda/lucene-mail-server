package server.message;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


/**
 * Class Body
 */
public class Body
{
	private String text;
	private StyleRange[] styles;

	public Body(String text, StyleRange[] styles)
	{
		this.text = text;
		this.styles = styles;
	}

	public void writeXML(ContentHandler hd) throws SAXException
	{
		final String EMPTY = "";
		AttributesImpl atts = new AttributesImpl();
		hd.startElement(EMPTY, EMPTY, "Body", atts);
		hd.startElement(EMPTY, EMPTY, "Text", atts);
		hd.characters(text.toCharArray(), 0, text.length());
		hd.endElement(EMPTY, EMPTY, "Text");
		for (int i = 0; i < styles.length; i++)
		{
			StyleRange style = styles[i];
			atts = new AttributesImpl();
			atts.addAttribute(EMPTY, EMPTY, "Style", "Start", style.start + "");
			atts.addAttribute(EMPTY, EMPTY, "Style", "Length", style.length + "");
			if (style.font == null)
			{
				atts.addAttribute(EMPTY, EMPTY, "Style", "Font", "null");
			}
			else
			{
				FontData font = style.font.getFontData()[0];
				atts.addAttribute(EMPTY, EMPTY, "Style", "Font", font.name + "+" + font.height + "+" + font.style);
			}
			atts.addAttribute(EMPTY, EMPTY, "Style", "Underline", style.underline+"");
			Color forground = style.foreground;
			if (style.foreground == null)
				atts.addAttribute(EMPTY, EMPTY, "Style", "Forground", "null");
			else
				atts.addAttribute(EMPTY, EMPTY, "Style", "Forground", forground.getRed() + "+" + forground.getGreen()
								+ "+" + forground.getBlue() + '\"');
			}
			hd.endElement(EMPTY, EMPTY, "Body");
	}
}
