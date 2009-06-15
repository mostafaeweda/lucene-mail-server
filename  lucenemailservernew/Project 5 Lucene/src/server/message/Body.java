package server.message;


/**
 * Class Body
 */
public class Body {

	private String text;


	//
	// Constructors
	//
	public Body (String text) 
	{
		this.text = text;
	}
	
	//
	// Methods
	//


	//
	// Accessor methods
	//
	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}

	
	public String toString()
	{
		return text;
	}
	//
	// Other methods
	//

}
