package server.message;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TextEditor
{
	private Display display;
	private Shell shell;

	public TextEditor()
	{
		
	}

	public void run()
	{
		display = new Display();
		shell = new Shell(display);
		shell.setText("Text Editor");
		createContents();
		shell.open();
		while (! shell.isDisposed())
			if (! display.readAndDispatch())
				display.sleep();
		dispose();
	}

	private void dispose()
	{
		display.dispose();
	}

	private void createContents()
	{
		shell.setLayout(new FormLayout());
		Ch5Persistent text = new Ch5Persistent(shell);
//		StyledText text = new StyledText(shell, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(0, 50);
		formData.right = new FormAttachment(100, 0);
		formData.bottom = new FormAttachment(100, 0);
		text.setLayoutData(formData);

		Composite composite = new Composite(shell, SWT.NONE);
		formData = new FormData();
		formData.left = new FormAttachment(0, 100);
		formData.top = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.bottom = new FormAttachment(text, 0);
		composite.setLayoutData(formData);
		Button b = new Button(composite, SWT.PUSH);
		b.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				
			}
		});
	}

	public static void main(String[] args)
	{
		new TextEditor().run();
	}
}
