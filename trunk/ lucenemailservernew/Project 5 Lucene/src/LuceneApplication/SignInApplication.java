package LuceneApplication;

import java.net.InetAddress;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import server.Controller;


public class SignInApplication {
	Display display;
	Shell shell;
	private Composite composite;
	private static SignInApplication instance;
	private SignInApplication(){}
	
	public static SignInApplication getInstance(){
		if(instance == null)
			instance = new SignInApplication();
		return instance;
	}
	public void run(){
		display = new Display();
		shell = new Shell (display);
		shell.setMaximized(true);
		createContent();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}
	
	public void createContent(){
		FillLayout layout = new FillLayout(1);
		shell.setLayout(layout);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		composite = new Composite(shell,SWT.NONE);
		composite.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		FormLayout formLayout = new FormLayout();
		composite.setLayout(formLayout);
		final Composite signInComposite = new Composite(composite,SWT.BORDER);
		signInComposite.setLayout(new GridLayout(2,false));
		FormData data = new FormData();
		data.right = new FormAttachment(3,4,10);
		data.top = new FormAttachment(55);
		signInComposite.setLayoutData(data);
		
		
		
		Label label = new Label (composite,SWT.None);
		data = new FormData();
		data.right = new FormAttachment(3,4,5);
		data.top = new FormAttachment(30);
		data.bottom = new FormAttachment(signInComposite);
		label.setLayoutData(data);
		label.setImage(new Image(display,"m3akLogo.jpg"));
		
		Label userNameLabel = new Label(signInComposite,SWT.NONE);
		userNameLabel.setText("UserName : ");
		final Text userNameText  = new Text(signInComposite,SWT.BORDER);
		GridData gridData = new GridData();
		gridData.widthHint = 100;
		userNameText.setLayoutData(gridData);
		Label passWordLabel = new Label(signInComposite,SWT.NONE);
		passWordLabel.setText("PassWord : ");
		final Text passWordText  = new Text(signInComposite,SWT.PASSWORD|SWT.BORDER);
		passWordText.setLayoutData(gridData);
		Button signInButton = new Button(signInComposite,SWT.PUSH);
		signInButton.setText("SignIn");
		
		signInButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
	 
					 String userName = userNameText.getText();
					 String password = passWordText.getText();
					 try {
						boolean isSignIn = Controller.getInstance().signIn(userName, password, InetAddress.getLocalHost().getCanonicalHostName());
						if(isSignIn){
							composite.dispose();
							MailApplication.getInstance(userName).createContent(shell);
							shell.layout();
						}
					} catch (Exception e1) {
					
					}		
			}
			
		});
		Composite hypComposite = new Composite(signInComposite,SWT.BORDER_SOLID);
		hypComposite.setLayout(new GridLayout(1,false));
	
		Hyperlink signUpLink = new Hyperlink(hypComposite, 0);
		signUpLink.setText("Get a new M3ak ID...");
		signUpLink.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				composite.dispose();
				composite = SignUpApplication.getInstance().createContent(shell);
				shell.layout();
				
			}
			
		});
		
		Hyperlink  forgetLink = new Hyperlink(hypComposite, 0);
		forgetLink.setText("forget password");
		forgetLink.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
			
	}
	
	
	public static void main(String[] args) {
		
		SignInApplication.getInstance().run();
	}

}
