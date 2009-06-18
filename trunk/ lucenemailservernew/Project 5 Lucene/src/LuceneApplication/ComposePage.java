package LuceneApplication;

import java.io.File;
import java.net.InetAddress;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import server.Controller;
import server.message.Body;

public class ComposePage {

	private Display display;
	private static ComposePage instance;
	private String SEND_IMAGES_PATH = "sendMail" + File.separatorChar;

	private ComposePage() {
	}

	void init() {
		display = Display.getCurrent();
	}

	public static ComposePage getInstance() {
		if (instance == null)
			instance = new ComposePage();
		return instance;
	}

	public Composite createContent(final CTabFolder cTabFolder) {
		init();

		Composite composite = new Composite(cTabFolder, SWT.None);
		composite.setLayout(new GridLayout(2, false));

		ToolBar toolBar = new ToolBar(composite, SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		toolBar.setLayoutData(gridData);
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);

		Label toLabel = new Label(composite, SWT.NO);
		toLabel.setText("To: ");
		final Text toText = new Text(composite, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		toText.setLayoutData(gridData);

		Label subjectLabel = new Label(composite, SWT.NO);
		subjectLabel.setText("Subject: ");
		final Text subjectText = new Text(composite, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		subjectText.setLayoutData(gridData);

		final TextEditor editor = new TextEditor();
		Composite edi = editor.createContent(composite);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		edi.setLayoutData(gridData);
//		final Text bodyText = new Text(composite, SWT.BORDER);	
//		bodyText.setLayoutData(gridData);

		item.setText("Send");
		item.setImage(new Image(display, SEND_IMAGES_PATH + "send.png"));
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String recievers = toText.getText();
				if (recievers.length() != 0) {
					Controller controller = Controller.getInstance();
					String recieversArray[] = recievers.split(";");
					// TODO
					Body body = editor.save();
					String subject = subjectText.getText();
					if (subject.length() == 0)
						subject = "Sub";
					try {
						controller.sendMessage(InetAddress.getLocalHost()
								.getCanonicalHostName(), recieversArray,
								subject, body);
					} catch (Exception e1) {
						e1.printStackTrace();

					}

				}

			}

		});
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setText("Attach");
		item.setImage(new Image(display, SEND_IMAGES_PATH + "attach.png"));
		item.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Controller controller = Controller.getInstance();
				FileDialog dlg = new FileDialog(cTabFolder.getShell(), SWT.OPEN|SWT.MULTI);
				dlg
						.setFilterNames(new String[] {
								"Microsoft Word (*.doc)",
								"Portable document Format (*.pdf)",
								" (*Extended Markup language(*.xml)",
								"All Files (*.*)" });
				dlg.setFilterExtensions(new String[] { "*.doc", "*.pdf",
						"*.xml", "*.*" });

				if (dlg.open() != null) {
					String[] names = dlg.getFileNames();
					String str = "";
					File file;
					for (int i = 0; i < names.length; i++) {
						str = dlg.getFilterPath()+File.separatorChar;
						file = new File(str+names[i]);
						try {
							controller.attach(InetAddress.getLocalHost()
									.getCanonicalHostName(), file);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}

			}

		});
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setText("Cancel");
		item.setImage(new Image(display, SEND_IMAGES_PATH + "cancel.png"));
		item.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				cTabFolder.getSelection().dispose();
				
			}
			
		});
		return composite;
	}
}
