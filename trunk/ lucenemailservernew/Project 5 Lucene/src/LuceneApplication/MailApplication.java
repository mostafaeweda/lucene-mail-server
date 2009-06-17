package LuceneApplication;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class MailApplication {

	Display display;
	Shell shell;
	Font font;

	public void run() {
		display = new Display();
		shell = new Shell(display);
		shell.setMaximized(true);
		createContent();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}

	private void createContent() {
		// shell and mail Composite
		shell.setLayout(new FillLayout(1));
		Composite mailComposite = new Composite(shell, SWT.None);
		FormLayout formLayout = new FormLayout();
		mailComposite.setLayout(formLayout);
		
		// Composite for inbox , sent, spam and Contacts
		Composite iconsComposite = new Composite(mailComposite, SWT.BORDER);
		iconsComposite.setLayout(new GridLayout(2, false));
		FormData formData = new FormData();
		formData.top = new FormAttachment(20);
		formData.bottom = new FormAttachment(100, -10);
		formData.width = 205;
		iconsComposite.setLayoutData(formData);
		
		// composite for Tabs 
		Composite tabsComposite = new Composite(mailComposite, SWT.BORDER);
		tabsComposite.setLayout(new FillLayout());
		formData = new FormData();
		formData.left = new FormAttachment(iconsComposite, 2);
		formData.bottom = new FormAttachment(100, -2);
		formData.top = new FormAttachment(10);
		formData.right = new FormAttachment(100, -10);
		tabsComposite.setLayoutData(formData);
		// TabsFolder for TabsItems 
		final CTabFolder cTabFolder = new CTabFolder(tabsComposite, SWT.BORDER);
		final CTabItem item = new CTabItem(cTabFolder, SWT.CLOSE);
		item.setText("your Profile");
		item.setControl(getControl(cTabFolder));

		Label inboxLabel = new Label(iconsComposite, SWT.None);
		inboxLabel.setImage(new Image(display, "inbox.png"));
		font = new Font(display, "Courier New", 12, SWT.NORMAL);
		Hyperlink hyperlink = new Hyperlink(iconsComposite, SWT.None);
		hyperlink.setText("Inbox");
		hyperlink.setFont(font);
		hyperlink.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		Label sentLabel = new Label(iconsComposite, SWT.None);
		sentLabel.setImage(new Image(display, "sent.png"));
		hyperlink = new Hyperlink(iconsComposite, SWT.None);
		hyperlink.setText("Sent");
		hyperlink.setFont(font);
		hyperlink.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		Label spamLabel = new Label(iconsComposite, SWT.None);
		spamLabel.setImage(new Image(display, "spam.png"));
		hyperlink = new Hyperlink(iconsComposite, SWT.None);
		hyperlink.setText("Spam");
		hyperlink.setFont(font);
		hyperlink.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		Label contactsLabel = new Label(iconsComposite, SWT.None);
		contactsLabel.setImage(new Image(display, "contacts.png"));
		hyperlink = new Hyperlink(iconsComposite, SWT.None);
		hyperlink.setText("Contacts");
		hyperlink.setFont(font);
		hyperlink.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		Button composeButton = new Button(mailComposite, SWT.PUSH);
		composeButton.setText("Compose");
		composeButton.setImage(new Image(display, "compose.png"));
		formData = new FormData();
		formData.bottom = new FormAttachment(iconsComposite, 1);
		composeButton.setLayoutData(formData);
		composeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		Button checkMailButton = new Button(mailComposite, SWT.PUSH);
		checkMailButton.setText("check Mail");
		checkMailButton.setImage(new Image(display, "CheckMail.png"));
		formData = new FormData();
		formData.bottom = new FormAttachment(iconsComposite, 1);
		formData.left = new FormAttachment(composeButton, 1);
		checkMailButton.setLayoutData(formData);
		checkMailButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});
		
		final Text searchText = new Text(mailComposite,SWT.BORDER);
		searchText.setText("Search Mail...");
		formData = new FormData();
		formData.bottom = new FormAttachment(composeButton,-2);
		formData.width = 150;
		formData.height = 18;
		formData.top = new FormAttachment(10);
		searchText.setLayoutData(formData);
		searchText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				if(searchText.getText().length() == 0)
					searchText.setText("Search Mail...");
			}
			
		});
		Button searchButton = new Button(mailComposite,SWT.PUSH);
		searchButton.setText("Search");
		formData = new FormData();
		formData.bottom = new FormAttachment(composeButton,-2);
		formData.left = new FormAttachment(searchText,0);
		formData.top = new FormAttachment(10);
		searchButton.setLayoutData(formData);
		searchButton.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

	private Control getControl(CTabFolder cTabFolder) {

		Composite composite = new Composite(cTabFolder, SWT.NONE);
		composite.setLayout(new FormLayout());

		Table table = new Table(composite, SWT.SINGLE | SWT.FULL_SELECTION
				| SWT.CHECK);
		table.setHeaderVisible(true);
		table.setLinesVisible(false);

		// table.setRedraw(false);

		FormData formData = new FormData();
		formData.left = new FormAttachment(0,0);
		formData.right = new FormAttachment(100,-5);
		formData.top = new FormAttachment(0,0);
		formData.bottom = new FormAttachment(100,-5);
		table.setLayoutData(formData);
		TableColumn[] columns = new TableColumn[3];
		columns[0] = new TableColumn(table, SWT.CENTER);
		columns[0].setText("From");
		columns[0].pack();
		columns[1] = new TableColumn(table, SWT.CENTER);
		columns[1].setText("Subject");
		columns[1].pack();
		columns[2] = new TableColumn(table, SWT.CENTER);
		columns[2].setText("Date");
		columns[2].pack();
		table.pack();
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] { "koko", "lolo", "tot" });

		return composite;
	}

	public static void main(String[] args) {

		new MailApplication().run();
	}

}
