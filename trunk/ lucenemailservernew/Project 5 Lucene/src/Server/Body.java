package Server;


/**
 * Class Body
 */
public class Body {

	private String body;


	//
	// Constructors
	//
	public Body (String text) 
	{
		this.body = text;
	}
	
	//
	// Methods
	//


	//
	// Accessor methods
	//
	public String getText() {
		return body;
	}


	public void setText(String text) {
		this.body = text;
	}

	
	public String toString()
	{
		return body;
	}
	//
	// Other methods
	//

}
