package LuceneApplication;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.PopupList;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class ContactPage {

	
	
	Display display;
	Shell shell;
	private final  String OPTIONS[] = {"All Contacts","Deleted Contacts"};

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
		shell.setLayout(new FillLayout(1));
		Composite composite = new Composite(shell,SWT.None);
		composite.setLayout(new FormLayout());
		Composite upperComposite = new Composite(composite,SWT.BORDER);
		upperComposite.setLayout(new GridLayout(5,false));
		
		final Text searchText = new Text(upperComposite,SWT.BORDER);
		searchText.setText("Search All Contacts");
		searchText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				if(searchText.getText().length()==0)
					searchText.setText("Search All Contacts");
			}
			
		});
		
		Button addButton = new Button(upperComposite,SWT.PUSH);
		addButton.setImage(new Image(display,"addContact.png"));
		addButton.setText("Add");
		addButton.addSelectionListener(new SelectionAdapter(){
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub	
			}
			
		});
		
		Button deleteButton = new Button(upperComposite,SWT.PUSH);
		deleteButton.setImage(new Image(display,"delete.png"));
		deleteButton.setText("Delete");
		deleteButton.addSelectionListener(new SelectionAdapter(){
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub	
			}
			
		});
		
		
		
		
		final Composite leftComposite = new Composite(composite,SWT.BORDER|SWT.V_SCROLL);
		leftComposite.setLayout(new FormLayout());
		FormData formData = new FormData();
		formData.left = new FormAttachment();
		formData.top = new FormAttachment(upperComposite);
		formData.bottom = new FormAttachment(100,-2);
		formData.right = new FormAttachment(1,3,-5);
		leftComposite.setLayoutData(formData);
		Button checkButton = new Button(leftComposite,SWT.CHECK);
		checkButton.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		final Button button = new Button(leftComposite,SWT.PUSH);
		button.setText("All Contacts");
		button.setImage(new Image(display,"arrow.png"));
		formData = new FormData();
		formData.left = new FormAttachment(checkButton,2);
		formData.width = 150;
		button.setLayoutData(formData);
		button.addSelectionListener(new SelectionAdapter(){


			@Override
			public void widgetSelected(SelectionEvent e) {
				   PopupList list = new PopupList(shell);
			       list.setItems(OPTIONS);
			       Rectangle rectangle = new Rectangle(leftComposite.getBounds().x+15,leftComposite.getBounds().y,150,50);
			       
			        String selected = list.open(rectangle);
			        if(selected!=null)
			        	button.setText(selected);
			        if(OPTIONS[0].equals(selected)){
			        	
			        }
			        //TODO
				
			}
			
		});
		
		
		
		
		

	}
	
	public static void main(String[] args) {
		new ContactPage().run();
	}

}
