package LuceneApplication;
import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;


public class ComposePage {
	
	Display display;
	Shell shell;
	private String SEND_IMAGES_PATH = "sendMail"+File.separatorChar;
	public void run() {
		display = new Display();
		shell = new Shell(display);
		createContent();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}
	private void createContent() {

		shell.setLayout(new FillLayout());
		Composite composite = new Composite(shell, SWT.None);
		composite.setLayout(new GridLayout(2,false));

		ToolBar toolBar = new ToolBar(composite, SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		toolBar.setLayoutData(gridData);
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);
		item.setText("Send");
		item.setImage(new Image(display,SEND_IMAGES_PATH+"send.png"));
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setText("Attach");
		item.setImage(new Image(display,SEND_IMAGES_PATH+"attach.png"));
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setText("Cancel");
		item.setImage(new Image(display,SEND_IMAGES_PATH+"cancel.png"));
		
		Label toLabel = new Label(composite,SWT.NO);
		toLabel.setText("To: ");
		Text toText = new Text(composite,SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		toText.setLayoutData(gridData);
		
		Label subjectLabel = new Label(composite,SWT.NO);
		subjectLabel.setText("Subject: ");
		Text subjectText = new Text(composite,SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		subjectText.setLayoutData(gridData);
		
		Text bodyText = new Text(composite,SWT.BORDER);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		bodyText.setLayoutData(gridData);
		
	}

	public static void main(String[] args) {
		new ComposePage().run();
	}
}
