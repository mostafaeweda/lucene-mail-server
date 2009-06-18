package LuceneApplication;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import server.Controller;

public class PassRecovery
{
	public void run()
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		open(shell);
		shell.open();
		while (! shell.isDisposed())
			if (! display.readAndDispatch())
				display.sleep();
		display.dispose();
	}

	public Composite open(final Shell shell)
	{
		Font labelFont = new Font(shell.getDisplay(), "Monotype Corsiva", 20, SWT.BOLD | SWT.ITALIC);
		Font textFont = new Font(shell.getDisplay(), "Comic Sans MS", 16, SWT.BOLD);

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		composite.setLayout(new GridLayout(1, false));

		CLabel m3akID = new CLabel(composite, SWT.NONE);
		m3akID.setText("M3ak ID *");
		m3akID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		m3akID.setFont(labelFont);
		m3akID.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));

		final Text m3akIDtext = new Text(composite, SWT.BORDER);
		m3akIDtext.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		m3akIDtext.setFont(textFont);

		Button request  = new Button(composite, SWT.PUSH);
		request.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		request.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		request.setText("Request Question");

		final Text questionText = new Text(composite, SWT.BORDER | SWT.CENTER);
		questionText.setText("-----Question-----");
		questionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		questionText.setFont(textFont);
		questionText.setEditable(false);

		final CLabel secretAnswerLabel = new CLabel(composite, SWT.NONE);
		secretAnswerLabel.setText("Secret Answer");
		secretAnswerLabel.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		secretAnswerLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		secretAnswerLabel.setFont(labelFont);

		final Text answerText = new Text(composite, SWT.BORDER);
		answerText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		answerText.setFont(textFont);
		answerText.setEditable(false);
		answerText.setText("Answer");
		answerText.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_GRAY));

		Button restorePass = new Button(composite, SWT.PUSH);
		restorePass.setText("REstore Passward");
		restorePass.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e)
			{
				String pass = Controller.getInstance().getPassword(m3akIDtext.getText(),
						answerText.getText());
				if (pass == null)
				{
					MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
					box.setMessage("Answer not right --> try to remember and try again");
					box.setText("Restore Password");
					box.open();
				}
				else
				{
					MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
					box.setMessage("User: " + m3akIDtext.getText() + "\r\nPassword is " + pass);
					box.setText("Restore Password");
					box.open();
				}
			}
		});

		request.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				String question = Controller.getInstance().forgetPassword(m3akIDtext.getText());
				if (question != null)
				{
					m3akIDtext.setEditable(false);
					answerText.setEditable(true);
					questionText.setText(question);
					questionText.setEditable(true);
				}
			}
		});

		answerText.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				answerText.setSelection(0, answerText.getText().length());
				answerText.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				answerText.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_GRAY));
			}
		});
		return composite;
	}

	public static void main(String[] args)
	{
		new PassRecovery().run();
	}
}
