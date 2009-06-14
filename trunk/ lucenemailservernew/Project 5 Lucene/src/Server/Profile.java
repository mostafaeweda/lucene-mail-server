

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
	
	//
	// Constructors
	//
	public Profile () { };
	
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
	private String[] getQuestions ( ) {
		return questions;
	}

	/**
	 * Set the value of firstName
	 * @param newVar the new value of firstName
	 */
	private void setFirstName ( String newVar ) {
		firstName = newVar;
	}

	/**
	 * Get the value of firstName
	 * @return the value of firstName
	 */
	private String getFirstName ( ) {
		return firstName;
	}

	/**
	 * Set the value of lastName
	 * @param newVar the new value of lastName
	 */
	private void setLastName ( String newVar ) {
		lastName = newVar;
	}

	/**
	 * Get the value of lastName
	 * @return the value of lastName
	 */
	private String getLastName ( ) {
		return lastName;
	}

	/**
	 * Set the value of gender
	 * @param newVar the new value of gender
	 */
	private void setGender ( int newVar ) {
		gender = newVar;
	}

	/**
	 * Get the value of gender
	 * @return the value of gender
	 */
	private int getGender ( ) {
		return gender;
	}

	/**
	 * Set the value of dateBirth
	 * @param newVar the new value of dateBirth
	 */
	private void setDateBirth ( String newVar ) {
		dateBirth = newVar;
	}

	/**
	 * Get the value of dateBirth
	 * @return the value of dateBirth
	 */
	private String getDateBirth ( ) {
		return dateBirth;
	}

	/**
	 * Set the value of userName
	 * @param newVar the new value of userName
	 */
	private void setUserName ( String newVar ) {
		userName = newVar;
	}

	/**
	 * Get the value of userName
	 * @return the value of userName
	 */
	private String getUserName ( ) {
		return userName;
	}

	/**
	 * Set the value of password
	 * @param newVar the new value of password
	 */
	private void setPassword ( String newVar ) {
		password = newVar;
	}

	/**
	 * Get the value of password
	 * @return the value of password
	 */
	private String getPassword ( ) {
		return password;
	}

	/**
	 * Set the value of secretQuestion
	 * @param newVar the new value of secretQuestion
	 */
	private void setSecretQuestion ( String newVar ) {
		secretQuestion = newVar;
	}

	/**
	 * Get the value of secretQuestion
	 * @return the value of secretQuestion
	 */
	private String getSecretQuestion ( ) {
		return secretQuestion;
	}

	/**
	 * Set the value of secretAnswer
	 * @param newVar the new value of secretAnswer
	 */
	private void setSecretAnswer ( String newVar ) {
		secretAnswer = newVar;
	}

	/**
	 * Get the value of secretAnswer
	 * @return the value of secretAnswer
	 */
	private String getSecretAnswer ( ) {
		return secretAnswer;
	}

	//
	// Other methods
	//

}
