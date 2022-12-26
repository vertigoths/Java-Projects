package application;

import java.io.File;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MenuScreen extends UIHelper implements FileHelper,Screen
{
	private ImageView imageView;
	private Stage currentStage;
	private String previousText;
	private String username;
	
	public static MenuScreen menuScreen;
	
	public MenuScreen(Stage primaryStage, String username)
	{
		if(menuScreen == null) 
		{
			menuScreen = this;
			primaryStage.setTitle("Menu");
		}
		
		else 
		{
			primaryStage.setTitle("Menu (Easter Egg)");
		}
		
		GridPane grid = createPane(20, 20, 20,false);
		
		this.username = username;
		
		Scene scene = new Scene(grid, 600, 550);
		
		finishUpTheScreen(grid);
		
		primaryStage.setScene(scene);
		
		primaryStage.setResizable(false);
		primaryStage.show();
		currentStage = primaryStage;
	}

	@Override
	public void addListenerToTheButton(Button button, int type)
	{
		button.setOnAction(new EventHandler<ActionEvent>() 
		{	 
		    @Override
		    public void handle(ActionEvent e) 
		    {
		        if(type == 0) 
		        {
		        	LibraryScreen LS = new LibraryScreen(currentStage, username);
		        	LS.toString();
		        }
		        else 
		        {
		        	StoreScreen SS = new StoreScreen(currentStage, username);
		        	SS.toString();
		        }
		    }
		});
	}

	@Override
	public Object addButtontoScreen(GridPane grid, String str, int columnIndex, int rowIndex)
	{
		Button button = new Button(str);
	
		button.setScaleX(5);
		button.setScaleY(5);
		
		HBox hbBtn = new HBox(6);
		
		hbBtn.getChildren().add(button);
		grid.add(hbBtn, columnIndex, rowIndex);
		
		hbBtn.setTranslateY(60);
	
		if(str.equals("Library")) 
		{
			hbBtn.setTranslateX(60);
			addListenerToTheButton(button,0);
		}
		
		else 
		{
			hbBtn.setTranslateX(460);
			addListenerToTheButton(button,1);
		}
		
		return button;
	}

	public void finishUpTheScreen(GridPane grid)
	{
		addImageToImageView(imageView,"Menu",(int)(Math.random() * 5),grid);
		addButtontoScreen(grid,"Library",0,3);
		addButtontoScreen(grid,"Store",0,3);
		getInformationFromFile("\\game_news.txt");
		addUItoScreen(grid, "Text", "• " + previousText,0, 1);
		getInformationFromFile("\\updates.txt");
		addUItoScreen(grid, "Text", "• " + previousText,0, 2);
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
		
		int count = 0;
		
		if(file!=null) 
		{
			while(file.hasNext()) 
			{
				count++;
				file.nextLine();
			}
		}
		
		int rand = (int) (Math.random() * count);
		count = 0;
		
		try 
		{
			file=new Scanner(new File(System.getProperty("user.dir") + textFile),"UTF-8");
		}
		catch(Exception e) {}
		
		if(file!=null) 
		{
			while(file.hasNext()) 
			{
				String line = file.nextLine();
				if(rand == count) 
				{
					previousText = line;
					break;
				}
				count++;
			}
			file.close();
		}
	}
}
