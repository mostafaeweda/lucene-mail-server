package LuceneApplication;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SignUpApplication {

	Display display;
	Shell shell;
	Color color;
	Font font;
	final String GENDER_ARRAY[] = { "-SelectOne-", "Male", "Female" };
	final String BRITHDAY_ARRAY[] = { "-SelectMonth-", "January", "Februaray",
			"Marth", "April", "May", "June", "July", "Augaust", "September",
			"October", "Nouvember", "Decamber" };

	final String questions[] = new String[]{
			"-SelectOne-","What is your pet name?", "What is your favourite car?",
			"What is your favourite friend's name?", "What is your favourite Country?"
		};
	void init() {

		color = new Color(display, new RGB(128, 0, 128));
		font = new Font(display, "Arial", 12, SWT.BOLD);
	}

	public void run() {
		display = new Display();
		shell = new Shell(display);
		shell.setMaximized(true);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		createContent();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}

	private void createContent() {
		init();
		shell.setLayout(new FillLayout(1));
		Composite composite = new Composite(shell, SWT.None);
		composite.setLayout(new FormLayout());
		Composite upperComposite = new Composite(composite, SWT.None);
		upperComposite.setLayout(new GridLayout(2, true));
		FormData formData = new FormData();
		formData.top = new FormAttachment();
		formData.bottom = new FormAttachment(25);
		formData.right = new FormAttachment(100, -1);
		formData.left = new FormAttachment(0, 0);
		upperComposite.setLayoutData(formData);
		Canvas canvas = new Canvas(upperComposite, SWT.DOUBLE_BUFFERED);
		canvas.setBackgroundImage(new Image(display, "SignUpLogo.png"));
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		Composite signInComposite = new Composite(upperComposite, SWT.None);
		signInComposite.setLayout(new GridLayout(1, false));
		signInComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		new Label(signInComposite, SWT.NONE);
		new Label(signInComposite, SWT.NONE);
		new Label(signInComposite, SWT.NONE);
		Label signInLabel = new Label(signInComposite, SWT.None);
		signInLabel.setText("Already have an ID or Mail address?");
		Button signInButton = new Button(signInComposite, SWT.PUSH);
		signInButton.setText("        SignIn       ");
		signInButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});
		Hyperlink hyperlink = new Hyperlink(signInComposite, SWT.NONE);
		hyperlink.setText("Forget your password or Yahoo! ID?");
		hyperlink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		Composite lowerComposite = new Composite(composite, SWT.BORDER);
		lowerComposite.setLayout(new FormLayout());
		formData = new FormData();
		formData.top = new FormAttachment(upperComposite);
		formData.bottom = new FormAttachment(100, 0);
		formData.right = new FormAttachment(100, 0);
		formData.left = new FormAttachment(0, 0);
		lowerComposite.setLayoutData(formData);

		Label label1 = new Label(lowerComposite, SWT.None);
		label1.setForeground(color);
		label1.setFont(font);
		label1.setText("1. Tell us about yourself...");
		formData = new FormData();
		formData.top = new FormAttachment(2);
		formData.left = new FormAttachment(2);
		formData.right = new FormAttachment(100, 0);
		formData.bottom = new FormAttachment(10);
		label1.setLayoutData(formData);

		Composite urSelfComposite = new Composite(lowerComposite, SWT.NONE);
		urSelfComposite.setLayout(new GridLayout(4, false));
		formData = new FormData();
		formData.top = new FormAttachment(label1);
		formData.left = new FormAttachment(10);
		formData.right = new FormAttachment(100, 0);
		urSelfComposite.setLayoutData(formData);

		Label myNameLabel = new Label(urSelfComposite, SWT.NO);
		myNameLabel.setText("My Name : ");

		Text firstNameButton = new Text(urSelfComposite, SWT.BORDER);
		firstNameButton.setText("First Name");
		GridData gridData = new GridData();
		gridData.widthHint = 150;
		firstNameButton.setLayoutData(gridData);

		Text lastNameButton = new Text(urSelfComposite, SWT.BORDER);
		lastNameButton.setText("Last Name");
		gridData = new GridData();
		gridData.widthHint = 150;
		lastNameButton.setLayoutData(gridData);

		new Label(urSelfComposite, SWT.None);

		Label genderLabel = new Label(urSelfComposite, SWT.NO);
		genderLabel.setText("Gender : ");

		Combo genderCombo = new Combo(urSelfComposite, SWT.BORDER);
		genderCombo.setItems(GENDER_ARRAY);
		genderCombo.select(0);
		gridData = new GridData();
		gridData.widthHint = 150;
		genderCombo.setLayoutData(gridData);

		new Label(urSelfComposite, SWT.None);
		new Label(urSelfComposite, SWT.None);

		Label brithdayLabel = new Label(urSelfComposite, SWT.NO);
		brithdayLabel.setText("Brithday : ");

		Combo brithdayCombo = new Combo(urSelfComposite, SWT.BORDER);
		brithdayCombo.setItems(BRITHDAY_ARRAY);
		brithdayCombo.select(0);
		gridData = new GridData();
		gridData.widthHint = 150;
		brithdayCombo.setLayoutData(gridData);

		Text dayButton = new Text(urSelfComposite, SWT.BORDER);
		dayButton.setText("Day");
		gridData = new GridData();
		gridData.widthHint = 150;
		dayButton.setLayoutData(gridData);

		Text yearButton = new Text(urSelfComposite, SWT.BORDER);
		yearButton.setText("Year");
		gridData = new GridData();
		gridData.widthHint = 150;
		yearButton.setLayoutData(gridData);

		Label label2 = new Label(lowerComposite, SWT.None);
		label2.setForeground(color);
		label2.setFont(font);
		label2.setText("2. Select an ID and password");
		formData = new FormData();
		formData.top = new FormAttachment(urSelfComposite);
		formData.left = new FormAttachment(2);
		formData.right = new FormAttachment(100, 0);
		label2.setLayoutData(formData);

		Composite idComposite = new Composite(lowerComposite, SWT.None);
		idComposite.setLayout(new GridLayout(5, false));
		formData = new FormData();
		formData.top = new FormAttachment(label2);
		formData.left = new FormAttachment(10);
		formData.right = new FormAttachment(100, 0);
		formData.bottom = new FormAttachment(50);
		idComposite.setLayoutData(formData);

		Label idLabel = new Label(idComposite, SWT.NO);
		idLabel.setText(" M3AK ID And Email : ");

		Text idText = new Text(idComposite, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 150;
		idText.setLayoutData(gridData);
		new Label(idComposite, SWT.None).setText("@m3ak.com");
		Button checkButton = new Button(idComposite, SWT.PUSH);
		checkButton.setText("Check Avilable");
		
		Label checkLabel = new Label(idComposite,SWT.None);
		checkLabel.setText("     ");
		checkLabel.setBackground(display.getSystemColor(SWT.COLOR_RED));
		
		checkButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});
		

		Label passLabel = new Label(idComposite, SWT.NO);
		passLabel.setText("Password : ");
		Text passText = new Text(idComposite, SWT.BORDER|SWT.PASSWORD);
		gridData = new GridData();
		gridData.widthHint = 150;
		passText.setLayoutData(gridData);
		new Label(idComposite,SWT.None);
		new Label(idComposite,SWT.None);
		new Label(idComposite,SWT.None);
		
		Label rePassLabel = new Label(idComposite, SWT.NO);
		rePassLabel.setText("Re-Type Password : ");
		Text rePassText = new Text(idComposite, SWT.BORDER|SWT.PASSWORD);
		gridData = new GridData();
		gridData.widthHint = 150;
		rePassText.setLayoutData(gridData);
		
		
		Label label3 = new Label(lowerComposite, SWT.None);
		label3.setForeground(color);
		label3.setFont(font);
		label3.setText("3. In case you forget your ID or password...");
		formData = new FormData();
		formData.top = new FormAttachment(idComposite);
		formData.left = new FormAttachment(2);
		formData.right = new FormAttachment(100, 0);
		label3.setLayoutData(formData);
		
		
		Composite forgetPasswordComposite = new Composite(lowerComposite,SWT.None);
		forgetPasswordComposite.setLayout(new GridLayout(2, false));
		formData = new FormData();
		formData.top = new FormAttachment(label3);
		formData.left = new FormAttachment(10);
		formData.right = new FormAttachment(100, 0);
		forgetPasswordComposite.setLayoutData(formData);
		
		
		Label secretQLabel = new Label(forgetPasswordComposite, SWT.NO);
		secretQLabel.setText("Security Question : ");
		Combo secretQCombo = new Combo(forgetPasswordComposite, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 150;
		secretQCombo.setItems(questions);
		secretQCombo.select(0);
		secretQCombo.setLayoutData(gridData);
		
		
		Label answerLabel = new Label(forgetPasswordComposite, SWT.NO);
		answerLabel.setText("Your Answer ");
		Text answerText = new Text(forgetPasswordComposite, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 150;
		answerText.setLayoutData(gridData);
		
		
		Button createButton = new Button(lowerComposite, SWT.PUSH);
		formData = new FormData();
		formData.top = new FormAttachment(forgetPasswordComposite,20);
		formData.left = new FormAttachment(20);
		createButton.setText("        Create Account     " );
		createButton.setLayoutData(formData);
		createButton.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

		
		Button cancelButton = new Button(lowerComposite, SWT.PUSH);
		formData = new FormData();
		formData.left = new FormAttachment(createButton,5);
		formData.top = new FormAttachment(forgetPasswordComposite,20);
		cancelButton.setText("         Cancel         " );
		cancelButton.setLayoutData(formData);
		cancelButton.addSelectionListener(new SelectionAdapter(){
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

	public static void main(String[] args) {
		new SignUpApplication().run();
	}

}
