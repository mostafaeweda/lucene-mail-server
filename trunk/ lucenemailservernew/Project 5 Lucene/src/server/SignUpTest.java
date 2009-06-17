package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class SignUpTest 
{
	static SignupHandler hd;
	public static void main(String[] args) throws Exception 
	{
		Controller.getInstance();
		hd = SignupHandler.getInstance();
		Profile temp = getProfile();
		if (temp != null)
			hd.createProfile(temp);
	}

	public static Profile getProfile() throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		boolean exist;
		do
		{
			System.out.println("UserName:");
			String userName = in.readLine();
			exist = hd.checkUserExist(userName);
			if (exist)
			{
				System.out.println("Password:");
				String password = in.readLine();
				System.out.println("---------------------------------------------");
				System.out.println("First Name:");
				String firstName = in.readLine();
				System.out.println("Last Name:");
				String lastName = in.readLine();
				System.out.println("Gender: (0) female (1) male");
				int gender = Integer.parseInt(in.readLine());
				System.out.println("Date Birth:");
				String dateBirth = in.readLine();
				System.out.println("secretQuestion:(1) (2) (3) (4)");
				int secretQuestion = Integer.parseInt(in.readLine());
				System.out.println("secretAnswer:");
				String secretAnswer = in.readLine();
				return new Profile(userName, password, firstName, lastName, gender, dateBirth, secretQuestion, secretAnswer);
			}
			else
			{
				System.out.println("User name already exist");
			}
		}while(! exist);
		return null;
	}
}
