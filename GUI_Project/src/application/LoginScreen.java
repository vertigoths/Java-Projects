package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class LoginScreen extends UIHelper implements Screen
{
	private TextField usernameField;
	private PasswordField passwordField;
	private Text loginInfoText;
	private Stage currentStage;
	
	public LoginScreen(Stage primaryStage) 
	{
		GridPane grid = createPane(10,10,25,true);
		finishUpTheScreen(grid);
		
		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.show();
		currentStage = primaryStage;
	}
	
	public Object addButtontoScreen(GridPane grid, String str,int columnIndex, int rowIndex) 
	{
		Button button = new Button(str);
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(button);
		grid.add(hbBtn, columnIndex, rowIndex);
		
		if(str.equals("Login")) 
		{
			addListenerToTheButton(button,0);
		}
		
		else 
		{
			addListenerToTheButton(button,1);
		}
		
		return button;
	}

	public void finishUpTheScreen(GridPane grid) 
	{
		addUItoScreen(grid,"Text","Welcome",0,0);
		addUItoScreen(grid,"Label","Username:",0,1);
		usernameField = (TextField) addUItoScreen(grid,"TextField",null,1,1);
		addUItoScreen(grid,"Label","Password",0,2);
		passwordField = (PasswordField) addUItoScreen(grid,"PasswordField",null,1,2);
		addButtontoScreen(grid,"Login",1,6);
		addButtontoScreen(grid,"Register",0,6);
		loginInfoText = (Text) addUItoScreen(grid,"Text","",0,3);
	}

	public void addListenerToTheButton(Button button, int type) 
	{
		button.setOnAction(new EventHandler<ActionEvent>() 
		{	 
		    @Override
		    public void handle(ActionEvent e) 
		    {
		        if(type == 0) 
		        {
		        	checkTheLogin(true);
		        }
		        else 
		        {
		        	try
					{
						createNewAccount();
					} 
		        	catch (IOException e1)
					{
						loginInfoText.setText("Error occured!");
					}
		        }
		    }
		});
	}
	
	private boolean checkTheLogin(boolean isCalledFromLogin)
	{
		boolean isUsernameExists = false;
		Scanner file = null;
		try 
		{
			file=new Scanner(new File(System.getProperty("user.dir") + "\\accounts.txt"),"UTF-8");
		}
		catch(Exception e) 
		{
			loginInfoText.setText("Txt file doesn't exist!");
		}
		
		if(file!=null) 
		{
			while(file.hasNext()) 
			{
				String line = file.nextLine();
				String[] array = line.split("\\s+");
				if(array[0].equals(usernameField.getText()) && array[1].equals(passwordField.getText()) && isCalledFromLogin)
				{
					currentStage.close();
					MenuScreen MS = new MenuScreen(currentStage, array[0]);
					MS.toString();
					break;
				}
				
				else if(array[0].equals(usernameField.getText()) && array[1].equals(passwordField.getText()) && !isCalledFromLogin)
				{
					isUsernameExists = true;
					break;
				}
				
				else if(array[0].equals(usernameField.getText()) && !array[1].equals(passwordField.getText()))
				{
					isUsernameExists = true;
					loginInfoText.setText("Wrong password!");
					break;
				}
			}
			file.close();
		}
		
		if(!isUsernameExists) 
		{
			loginInfoText.setText("Account not found!");
		}
		return isUsernameExists;
	}
	
	private void createNewAccount() throws IOException 
	{
		if(!isLoginCredsValid()) 
		{
			return;
		}
		
		FileWriter file = null;
		try 
		{
			file = new FileWriter(System.getProperty("user.dir") + "\\accounts.txt",true);
		}
		catch(Exception e) 
		{
			loginInfoText.setText("Txt file doesn't exist!");
		}
		
		if(file!=null) 
		{
			if(checkTheLogin(false)) 
			{
				loginInfoText.setText("Username already taken");
			}
			
			else 
			{
				file.append(usernameField.getText() + " " + passwordField.getText() + "\n");
				
				FileWriter newFile = null;
				try 
				{
					newFile = new FileWriter(System.getProperty("user.dir") + "\\accounts\\" + usernameField.getText() + ".txt",true);
				}
				catch(Exception e) {}
				
				newFile.append(usernameField.getText() + " " + 250 + "\n");
				
				newFile.flush();
				newFile.close();
				
				loginInfoText.setText("Account created");
			}
		}
		
		file.flush();
		file.close();
	}

	private boolean isLoginCredsValid() 
	{
		if(usernameField.getText().length() < 6) 
		{
			loginInfoText.setText("Username is too short!");
			return false;
		}
		else if(passwordField.getText().length() < 6) 
		{
			loginInfoText.setText("Password is too short!");
			return false;
		}
		
		return true;
	}
}
