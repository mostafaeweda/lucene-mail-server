package server;

/**
 * Class Profile
 * profile class
 */
public class Profile {

	//
	// Fields
	//

	static private String[] questions;
	private String firstName;
	private String lastName;
	private int gender;
	private String dateBirth;
	private String userName;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	
	static 
	{
		questions = new String[]{
			"What is your pet name?", "What is your favourite car?",
			"What is your favourite friend's name?", "What is your favourite Country?"
		};
	}
	
	//
	// Constructors
	//
	public Profile (String userName,String password,String firstName,String lastName
			,int gender,String date,int secretQuestion,String secretAns) 
	{
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateBirth = date;
		this.secretQuestion = questions[secretQuestion];
		this.secretAnswer = secretAns;
		
	}
	
	//
	// Methods
	//


	//
	// Accessor methods
	//

	/**
	 * Get the value of questions
	 * @return the value of questions
	 */
	public String[] getQuestions ( ) {
		return questions;
	}

	/**
	 * Set the value of firstName
	 * @param newVar the new value of firstName
	 */
	public void setFirstName ( String newVar ) {
		firstName = newVar;
	}

	/**
	 * Get the value of firstName
	 * @return the value of firstName
	 */
	public String getFirstName ( ) {
		return firstName;
	}

	/**
	 * Set the value of lastName
	 * @param newVar the new value of lastName
	 */
	public void setLastName ( String newVar ) {
		lastName = newVar;
	}

	/**
	 * Get the value of lastName
	 * @return the value of lastName
	 */
	public String getLastName ( ) {
		return lastName;
	}

	/**
	 * Set the value of gender
	 * @param newVar the new value of gender
	 */
	public void setGender ( int newVar ) {
		gender = newVar;
	}

	/**
	 * Get the value of gender
	 * @return the value of gender
	 */
	public String getGender () {
		if (gender == 1)
			return "Male";
		else
			return "Female";
	}

	/**
	 * Set the value of dateBirth
	 * @param newVar the new value of dateBirth
	 */
	public void setDateBirth ( String newVar ) {
		dateBirth = newVar;
	}

	/**
	 * Get the value of dateBirth
	 * @return the value of dateBirth
	 */
	public String getDateBirth ( ) {
		return dateBirth;
	}

	/**
	 * Set the value of userName
	 * @param newVar the new value of userName
	 */
	public void setUserName ( String newVar ) {
		userName = newVar;
	}

	/**
	 * Get the value of userName
	 * @return the value of userName
	 */
	public String getUserName ( ) {
		return userName;
	}

	/**
	 * Set the value of password
	 * @param newVar the new value of password
	 */
	public void setPassword ( String newVar ) {
		password = newVar;
	}

	/**
	 * Get the value of password
	 * @return the value of password
	 */
	public String getPassword ( ) {
		return password;
	}

	/**
	 * Set the value of secretQuestion
	 * @param newVar the new value of secretQuestion
	 */
	public void setSecretQuestion (String newVar ) {
		secretQuestion = newVar;
	}

	/**
	 * Get the value of secretQuestion
	 * @return the value of secretQuestion
	 */
	public String getSecretQuestion ( ) {
		return secretQuestion;
	}

	/**
	 * Set the value of secretAnswer
	 * @param newVar the new value of secretAnswer
	 */
	public void setSecretAnswer ( String newVar ) {
		secretAnswer = newVar;
	}

	/**
	 * Get the value of secretAnswer
	 * @return the value of secretAnswer
	 */
	public String getSecretAnswer ( ) {
		return secretAnswer;
	}

	//
	// Other methods
	//

}
