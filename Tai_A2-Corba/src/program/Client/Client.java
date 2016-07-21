package program.Client;

import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Client {
	
	protected Logger logger;

	public void setLogger(String username, String fileName) {
		try{
			this.logger = Logger.getLogger(username);
			FileHandler fileTxt 	 = new FileHandler(fileName);
			SimpleFormatter formatterTxt = new SimpleFormatter();
		    fileTxt.setFormatter(formatterTxt);
		    logger.addHandler(fileTxt);
			logger.setUseParentHandlers(false);

		}
		catch(Exception err) {
			System.out.println("Couldn't create Logger. Please check file permission");
		}
	}
	public String clientIDValidation(Scanner keyboard){
		boolean valid=false;
		String id="";
		while(!valid){
			try{
				id=keyboard.nextLine();
				String m=id.substring(0,3);
				if(id.length()==7 && m.equalsIgnoreCase("mtl") || m.equalsIgnoreCase("lvl")||m.equalsIgnoreCase("ddo")){
					valid=true;
					}
				else{
					System.out.println("Invalid Client-ID, please enter again");
					id=keyboard.nextLine();
					valid=false;
				}
				}
				catch(Exception e){
					System.out.println("Invalid Client-ID, please enter again");

					valid= false;
					

				}
			
		}
		return id;
	}
	public String InputStringValidation(Scanner keyboard) {
		boolean valid = false;
		String userInput = "";
		while(!valid)
		{
			try{
				userInput=keyboard.nextLine();
				valid=true;
			}
			catch(Exception e)
			{
				System.out.println("Invalid Input, please enter an String");
				valid=false;
				keyboard.nextLine();
			}
		}
		return userInput;
	}
	
	public int InputIntValidation(Scanner keyboard) {
		boolean valid = false;
		int userInput = 0;
		while(!valid)
		{
			try{
				userInput=keyboard.nextInt();
				valid=true;
			}
			catch(Exception e)
			{
				System.out.println("Invalid Input, please enter an Integer");
				valid=false;
				keyboard.nextLine();
			}
		}
		return userInput;
	}
}
