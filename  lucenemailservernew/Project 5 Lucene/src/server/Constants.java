package server;

import java.io.File;

public interface Constants {
	
	String SERVER_PATH = "server";
	String ACCOUNTS_PATH = SERVER_PATH + File.separatorChar + "accounts" + File.separatorChar;
	String MESSAGES_PATH = SERVER_PATH + File.separatorChar + "messages" + File.separatorChar;
	String Attachments_PATH = SERVER_PATH + File.separatorChar + "attachments" +  File.separatorChar;
	int PRIMARY_KEY_LENGTH = 6;
}
