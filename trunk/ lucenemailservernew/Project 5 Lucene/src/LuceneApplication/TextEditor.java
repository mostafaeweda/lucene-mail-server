package LuceneApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import server.message.Body;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

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
 ******************************************************************************/
public class TextEditor
{

	private ToolBar toolBar;

	private StyledText text;

	private Images images = new Images();

	private Vector<StyleRange> cachedStyles = new Vector<StyleRange>();

	private Font font = null;

	private Composite composite;

	private ToolItem boldButton, italicButton, underlineButton, strikeoutButton;

	/*
	 * Set a style
	 */
	private void setStyle(Widget widget)
	{
		Point sel = text.getSelectionRange();
		if ((sel == null) || (sel.y == 0))
			return;
		StyleRange style;
		for (int i = sel.x; i < sel.x + sel.y; i++)
		{
			StyleRange range = text.getStyleRangeAtOffset(i);
			if (range != null)
			{
				style = (StyleRange) range.clone();
				style.start = i;
				style.length = 1;
			} else
			{
				style = new StyleRange(i, 1, null, null, SWT.NORMAL);
			}
			if (widget == boldButton)
			{
				style.fontStyle ^= SWT.BOLD;
			} else if (widget == italicButton)
			{
				style.fontStyle ^= SWT.ITALIC;
			} else if (widget == underlineButton)
			{
				style.underline = !style.underline;
			} else if (widget == strikeoutButton)
			{
				style.strikeout = !style.strikeout;
			}
			text.setStyleRange(style);
		}
		text.setSelectionRange(sel.x + sel.y, 0);
	}

	/*
	 * Clear all style data for the selected text.
	 */
	void clear()
	{
		Point sel = text.getSelectionRange();
		if ((sel != null) && (sel.y != 0))
		{
			StyleRange style;
			style = new StyleRange(sel.x, sel.y, null, null, SWT.NORMAL);
			text.setStyleRange(style);
		}
		text.setSelectionRange(sel.x + sel.y, 0);
	}

	/*
	 * Set the foreground color for the selected text.
	 */
	void fgColor()
	{
		Point sel = text.getSelectionRange();
		if ((sel == null) || (sel.y == 0))
			return;
		ColorDialog dialog = new ColorDialog(composite.getShell());
		dialog.setText("Text color choosing");
		RGB rgb = dialog.open();
		if (rgb == null)
			return;
		Color fg = new Color(Display.getDefault(), rgb);
		StyleRange style, range;
		for (int i = sel.x; i < sel.x + sel.y; i++)
		{
			range = text.getStyleRangeAtOffset(i);
			if (range != null)
			{
				style = (StyleRange) range.clone();
				style.start = i;
				style.length = 1;
				style.foreground = fg;
			}
			else
			{
				style = new StyleRange(i, 1, fg, null, SWT.NORMAL);
			}
			text.setStyleRange(style);
		}
		text.setSelectionRange(sel.x + sel.y, 0);
	}

	private void setPartFont()
	{
		Point sel = text.getSelectionRange();
		if ((sel == null) || (sel.y == 0))
			return;
		FontDialog fontDialog = new FontDialog(composite.getShell());
		fontDialog.setFontList((text.getFont()).getFontData());
		FontData fontData = fontDialog.open();
		if (fontData == null)
		{
			return;
		}
		Font font = new Font(Display.getDefault(), fontData);
		StyleRange style = null, range = null;
		for (int i = sel.x; i < sel.x + sel.y; i++)
		{
			range = text.getStyleRangeAtOffset(i);
			if (range != null)
			{
				style = (StyleRange) range.clone();
				style.start = i;
				style.length = 1;
				style.font = font;
			}
			else
			{
				style = new StyleRange(i, 1,null, null,	font.getFontData()[0].getStyle());
				style.font = font;
			}
			text.setStyleRange(style);
		}
		text.setSelectionRange(sel.x + sel.y, 0);
	}

	private void createStyledText()
	{
		text = new StyledText(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL);
		text.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.keyCode == (SWT.MOD1 | 'c'))
				{
					handleCutCopy();
					text.copy();
				}
				else if (e.keyCode == (SWT.MOD1 | 'x'))
				{
					handleCutCopy();
					text.cut();
				}
				else if (e.keyCode == (SWT.MOD1 | 'v'))
				{
					text.paste();
				}
		}});
		GridData spec = new GridData();
		spec.horizontalAlignment = GridData.FILL;
		spec.grabExcessHorizontalSpace = true;
		spec.verticalAlignment = GridData.FILL;
		spec.grabExcessVerticalSpace = true;
		text.setLayoutData(spec);
		text.addExtendedModifyListener(new ExtendedModifyListener() {
			public void modifyText(ExtendedModifyEvent e) {
				handleExtendedModify(e);
			}
		});
	}

	void createToolBar() {
		toolBar = new ToolBar(composite, SWT.NULL);
		SelectionAdapter listener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				setStyle(event.widget);
			}
		};
		boldButton = new ToolItem(toolBar, SWT.CHECK);
		boldButton.setImage(images.Bold);
		boldButton.setText("Bold");
		boldButton.addSelectionListener(listener);
		italicButton = new ToolItem(toolBar, SWT.CHECK);
		italicButton.setImage(images.Italic);
		italicButton.setText("Italic");
		italicButton.addSelectionListener(listener);
		underlineButton = new ToolItem(toolBar, SWT.CHECK);
		underlineButton.setImage(images.Underline);
		underlineButton.setText("Underline");
		underlineButton.addSelectionListener(listener);
		strikeoutButton = new ToolItem(toolBar, SWT.CHECK);
		strikeoutButton.setImage(images.Strikeout);
		strikeoutButton.setText("Strikeout");
		strikeoutButton.addSelectionListener(listener);

		ToolItem item = new ToolItem(toolBar, SWT.SEPARATOR);
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setText("Color");
		item.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				fgColor();
			}
		});
		item = new ToolItem(toolBar, SWT.SEPARATOR);
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setText("Erase");
		item.setImage(images.Erase);
		item.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				clear();
			}
		});
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setText("Save");
		item.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				save();
			}
		});
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setText("Font");
		item.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				setFont();
			}
		});
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setText("Z");
		item.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				setPartFont();
			}
		});
	}

	/*
	 * Cache the style information for text that has been cut or copied.
	 */
	void handleCutCopy() {
		// Save the cut/copied style info so that during paste we will maintain
		// the style information. Cut/copied text is put in the clipboard in
		// RTF format, but is not pasted in RTF format. The other way to
		// handle the pasting of styles would be to access the Clipboard
		// directly and
		// parse the RTF text.
		cachedStyles = new Vector<StyleRange>();
		Point sel = text.getSelectionRange();
		int startX = sel.x;
		for (int i = sel.x; i <= sel.x + sel.y - 1; i++) {
			StyleRange style = text.getStyleRangeAtOffset(i);
			if (style != null) {
				style.start = style.start - startX;
				if (!cachedStyles.isEmpty()) {
					StyleRange lastStyle = (StyleRange) cachedStyles
							.lastElement();
					if (lastStyle.similarTo(style)
							&& lastStyle.start + lastStyle.length == style.start) {
						lastStyle.length++;
					} else {
						cachedStyles.addElement(style);
					}
				} else {
					cachedStyles.addElement(style);
				}
			}
		}
	}

	void handleExtendedModify(ExtendedModifyEvent event) {
		if (event.length == 0)
			return;
		StyleRange style;
		if (event.length == 1
				|| text.getTextRange(event.start, event.length).equals(
						text.getLineDelimiter()))
		{
			// Have the new text take on the style of the text to its right
			// (during
			// typing) if no style information is active.
			int caretOffset = text.getCaretOffset();
			style = null;
			if (caretOffset < text.getCharCount())
				style = text.getStyleRangeAtOffset(caretOffset);
			if (style != null)
			{
				style = (StyleRange) style.clone();
				style.start = event.start;
				style.length = event.length;
			}
			else
			{
				style = new StyleRange(event.start, event.length, null, null,
						SWT.NORMAL);
			}
			if (boldButton.getSelection())
				style.fontStyle |= SWT.BOLD;
			if (italicButton.getSelection())
				style.fontStyle |= SWT.ITALIC;
			style.underline = underlineButton.getSelection();
			style.strikeout = strikeoutButton.getSelection();
			if (!style.isUnstyled())
				text.setStyleRange(style);
		} else {
			// paste occurring, have text take on the styles it had when it was
			// cut/copied
			for (int i = 0; i < cachedStyles.size(); i++) {
				style = (StyleRange) cachedStyles.elementAt(i);
				StyleRange newStyle = (StyleRange) style.clone();
				newStyle.start = style.start + event.start;
				text.setStyleRange(newStyle);
			}
		}
	}

	public Composite createContent(Composite parent)
	{
		images.loadAll(parent.getDisplay());
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);
		composite.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (font != null)
					font.dispose();
				// images.freeAll();
			}
		});
		createToolBar();
		createStyledText();
		return composite;
	}

	private void setFont()
	{
		FontDialog fontDialog = new FontDialog(composite.getShell());
		fontDialog.setFontList((text.getFont()).getFontData());
		FontData fontData = fontDialog.open();
		if (fontData != null)
		{
			if (font != null)
			{
				font.dispose();
			}
			font = new Font(composite.getShell().getDisplay(), fontData);
			text.setFont(font);
		}
	}

	public Body save()
	{
		try {
			write(new Body(text.getText(), text.getStyleRanges()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return new Body(text.getText(), text.getStyleRanges());
	}

	void write(Body body) throws IOException, SAXException
	{
		OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
		of.setIndent(5);//set indentation dfor XML tags
		of.setIndenting(true);
		//create XML serializer with file output stream and output format
		XMLSerializer serializer = new XMLSerializer(new FileOutputStream("bodytoto.xml"), of);
		//get content handler to handle tags in XML doc
		ContentHandler hd = serializer.asContentHandler();
		body.writeXML(hd) ;
	}

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
	 ******************************************************************************/

	class Images {

		// Bitmap Images
		public Image Bold;

		public Image Italic;

		public Image Underline;

		public Image Strikeout;

		public Image Red;

		public Image Green;

		public Image Blue;

		public Image Erase;

		Image[] AllBitmaps;

		Images() {
		}

		public void freeAll() {
			for (int i = 0; i < AllBitmaps.length; i++)
				AllBitmaps[i].dispose();
			AllBitmaps = null;
		}

		Image createBitmapImage(Display display, String fileName) {
			InputStream sourceStream = Images.class.getResourceAsStream(fileName
					+ ".bmp");
			InputStream maskStream = Images.class.getResourceAsStream(fileName
					+ "_mask.bmp");
			ImageData source = new ImageData(sourceStream);
			ImageData mask = new ImageData(maskStream);
			Image result = new Image(display, source, mask);
			try {
				sourceStream.close();
				maskStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		public void loadAll(Display display) {
			// Bitmap Images
			// Bold = createBitmapImage(display, "bold");
			// Italic = createBitmapImage(display, "italic");
			// Underline = createBitmapImage(display, "underline");
			// Strikeout = createBitmapImage(display, "strikeout");
			// Red = createBitmapImage(display, "red");
			// Green = createBitmapImage(display, "green");
			// Blue = createBitmapImage(display, "blue");
			// Erase = createBitmapImage(display, "erase");

			AllBitmaps = new Image[] { Bold, Italic, Underline, Strikeout, Red,
					Green, Blue, Erase, };
		}
	}
}