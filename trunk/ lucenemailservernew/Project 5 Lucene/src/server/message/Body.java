package server.message;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


/*******************************************************************************
 * Copyright (c) 2009  M3ak Corporation and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the M3ak License v1.0
 * Contributors: M3AK Corporation - initial API and implementation
 * @author Ahmed El Morsi
 * @author Mostafa Eweda
 * @author Mohammed Yasser
 * @author Kreem el Sayyed
 * @author Mohammed Abd el Salam
 *******************************************************************************
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
		hd.startElement(EMPTY, EMPTY, "Styles", atts);
		if (styles != null)
		{
			for (int i = 0; i < styles.length; i++)
			{
				StyleRange style = styles[i];
				atts.clear();
				atts.addAttribute(EMPTY, EMPTY, "Start", EMPTY, style.start + "");
				atts.addAttribute(EMPTY, EMPTY, "Length", EMPTY, style.length + "");
				if (style.font == null)
				{
					atts.addAttribute(EMPTY, EMPTY, "Font", EMPTY, "null");
				}
				else
				{
					FontData font = style.font.getFontData()[0];
					atts.addAttribute(EMPTY, EMPTY, "Font", EMPTY, font.name + "+" + font.height + "+" + font.style);
				}
				atts.addAttribute(EMPTY, EMPTY, "Underline", EMPTY, style.underline+"");
				Color forground = style.foreground;
				if (style.foreground == null)
					atts.addAttribute(EMPTY, EMPTY, "Forground", EMPTY, "null");
				else
					atts.addAttribute(EMPTY, EMPTY, "Forground", EMPTY, forground.getRed() + "+" + forground.getGreen()
									+ "+" + forground.getBlue());
				hd.startElement(EMPTY, EMPTY, "Style", atts);
				hd.endElement(EMPTY, EMPTY, "Style");
			}
		}
		atts.clear();
		hd.endElement(EMPTY, EMPTY, "Text");
		hd.endElement(EMPTY, EMPTY, "Body");
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public void setStyleRanges(StyleRange[] styles)
	{
		this.styles = styles;
	}
}
