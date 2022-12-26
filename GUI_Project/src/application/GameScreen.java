package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameScreen extends UIHelper implements FileHelper,Screen
{
	private ImageView imageView;
	private String gameName;
	private Game game;
	private String username;
	private int balanceOfUser;
	private Text buyInformation;
	private Text balanceText;
	private TextField friendName;
	private Stage currentStage;
	
	
	public GameScreen(Stage primaryStage, String gameName, String username)
	{
		GridPane grid = createPane(20, 20, 20,false);
		
		this.currentStage = primaryStage;
		
		this.username = username;
		
		this.balanceOfUser = getBalanceOfUser();
		
		Scene scene = new Scene(grid, 600, 550);
		
		this.gameName = gameName;
		
		finishUpTheScreen(grid);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle(gameName);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	@Override
	public void getInformationFromFile(String textFile)
	{
		Scanner file = null;
		try 
		{
			file=new Scanner(new File(System.getProperty("user.dir") + textFile),"UTF-8");
		}
		catch(Exception e) {}
		
		if(file!=null) 
		{
			while(file.hasNext()) 
			{
				String array[] = file.nextLine().split("-");
				
				if(array.length != 8) 
				{
					return;
				}
				
				this.game = new Game(array[0],array[1],Integer.parseInt(array[2]),array[3],array[4],array[5],array[6],array[7]);
			}
		}
	}

	@Override
	public void addListenerToTheButton(Button button, int type)
	{
		button.setOnAction(new EventHandler<ActionEvent>() 
		{	 
		    @Override
		    public void handle(ActionEvent e) 
		    {
		    	if(button.getText().equals("TO MENU")) 
		    	{
		    		currentStage.close();
					MenuScreen MS = new MenuScreen(currentStage, username);
					MS.toString();
					return;
		    	}
		    	
		        if(balanceOfUser >= game.getPrice()) 
		        {
		        	buyInformation.setText("Thank you for purchase!");
		        	
		        	try
					{
						if(button.getText().equals("BUY FOR ME")) 
						{
							if(!isAlreadyOwned(gameName, username)) 
							{
								addGameToList(username);
							}
							
							else 
							{
								buyInformation.setText("You own the game!");
								return;
							}
						}
						
						else 
						{
							if(checkIfFriendExists(friendName.getText()))
							{
								if(!isAlreadyOwned(gameName, friendName.getText())) 
								{
									addGameToList(friendName.getText());
								}
								
								else 
								{
									buyInformation.setText("Friend owns the game!");
									return;
								}
							}
							
							else 
							{
								buyInformation.setText("Friend not found!");
								return;
							}
						}
						
						balanceOfUser -= game.getPrice();
			        	
			        	balanceText.setText("Your balance: $" + balanceOfUser);
			        	
			        	changeAccountBalance();
					} 
		        	catch (IOException e1){}
		        }
		        
		        else 
		        {
		        	buyInformation.setText("Redirecting deposit page!");
		        }
		    }
		});
		
	}

	@Override
	public Object addButtontoScreen(GridPane grid, String str, int columnIndex, int rowIndex)
	{
		Button button = new Button(str);
		
		button.setTranslateX(-135);
		button.setTranslateY(-25);
		button.setMinWidth(100);
		button.setMinHeight(30);
		
		if(str.equals("BUY FRIEND")) 
		{
			button.setTranslateX(-270);
			button.setTranslateY(25);
		}
		
		else if(str.equals("TO MENU")) 
		{
			button.setTranslateX(164);
			button.setTranslateY(333);
		}
		
		addListenerToTheButton(button, 0);
		
		grid.add(button, columnIndex, rowIndex);
		return button;
	}

	@Override
	public void finishUpTheScreen(GridPane grid)
	{
		addImageToImageView(imageView,gameName,0,grid);
		getInformationFromFile("\\games\\" + gameName + "\\gameInfo.txt");
		
		if(this.game != null) 
		{
			addUItoScreen(grid,"Text","Name: " + game.getName() + "\t\t\t\t     Minimum Ram Requirement: " + game.getMinRamReq(),0,1);
			addUItoScreen(grid,"Text","Size: " + game.getSize() + " GB" + "\t\t\t    Minimum Graphic Card Requirement: " + game.getMinGraphCardReq(),0,2);
			addUItoScreen(grid,"Text","Price: $" + game.getPrice(),0,3);
			addUItoScreen(grid,"Text","Publisher: " + game.getPublisher(),0,4);
			addUItoScreen(grid,"Text","Developer: " + game.getDeveloper(),0,5);
			
			if(!isAlreadyOwned(gameName, this.username)) 
			{
				addButtontoScreen(grid, "BUY FOR ME", 1, 5);
			}
			
			addButtontoScreen(grid, "BUY FRIEND", 1, 4);
			
			addButtontoScreen(grid, "TO MENU", 0, 0);
			
			this.friendName = (TextField) addUItoScreen(grid, "TextField", "", 1, 2);
			this.friendName.setTranslateY(72);
			this.friendName.setTranslateX(-270);
			
			this.balanceText = (Text) addUItoScreen(grid, "Text" ,"Your balance: $" + this.balanceOfUser, 1, 6);
			this.balanceText.setTranslateY(-33);
			this.balanceText.setTranslateX(-324);
			
			this.buyInformation = (Text) addUItoScreen(grid, "Text" ,"", 1, 3);
			this.buyInformation.setTranslateY(103);
			this.buyInformation.setTranslateX(-173);
		}
	}

	public int getBalanceOfUser() 
	{
		Scanner file = null;
		try 
		{
			file=new Scanner(new File(System.getProperty("user.dir") + "\\accounts\\" + this.username + ".txt"),"UTF-8");
		}
		catch(Exception e) {}
		
		if(file!=null) 
		{
			String[] accountInfo = file.nextLine().split("\\s+");
			return Integer.parseInt(accountInfo[1]);
		}
		
		return -1;
	}

	public void addGameToList(String toWho) throws IOException 
	{
		FileWriter file = null;
		try 
		{
			file = new FileWriter(System.getProperty("user.dir") + "\\accounts\\" + toWho + "_games.txt", true);
		}
		catch(Exception ex) {}
		
		if(file!=null) 
		{
			try
			{
				file.append(gameName + "\n");
			} catch (IOException e){}
		}
		
		file.flush();
		file.close();
	}

	public void changeAccountBalance() throws IOException
	{
		FileWriter file = null;
		try 
		{
			file = new FileWriter(System.getProperty("user.dir") + "\\accounts\\" + username + ".txt", false);
		}
		catch(Exception ex) {}
		
		if(file!=null) 
		{
			try
			{
				file.write(username + " " + balanceOfUser);
				file.flush();
				file.close();
			} catch (IOException e){}
		}
	}

	public boolean checkIfFriendExists(String friendName) 
	{
		Scanner file = null;
		try 
		{
			file=new Scanner(new File(System.getProperty("user.dir") + "\\accounts.txt"),"UTF-8");
		}
		catch(Exception e) {}
		
		if(file!=null) 
		{
			while(file.hasNext()) 
			{
				String[] accountInfo = file.nextLine().split("\\s+");
				
				if(accountInfo[0].equals(friendName)) 
				{
					return true;
				}
			}
			
		}
		
		file.close();
		
		return false;
	}

	public boolean isAlreadyOwned(String gameName, String username) 
	{
		Scanner file = null;
		try 
		{
			file=new Scanner(new File(System.getProperty("user.dir") + "\\accounts\\" + username + "_games.txt"),"UTF-8");
		}
		catch(Exception e) {}
		
		if(file!=null) 
		{
			while(file.hasNext()) 
			{
				String line = file.nextLine();
				
				if(gameName.equals(line)) 
				{
					return true;
				}
			}
			
			file.close();
		}
		
		return false;
	}
}
