package application;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibraryScreen extends UIHelper implements Screen,FileHelper
{
	private String username;
	private VBox content;
	private Stage primaryStage;
	
	private Queue<String> recommendList;
	
	public LibraryScreen(Stage primaryStage, String username)
	{
		GridPane grid = createPane(20, 20, 20,false);
		
		recommendList = new LinkedList<>();
		fillTheRecommendList();
		
		VBox content = new VBox(5);
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);
        
        this.primaryStage = primaryStage;
        this.content = content;
		
		this.username = username;
		
		BorderPane BP = new BorderPane(scroller, null, null, null, null);
		
		Label label = new Label("We recommend to try " + recommendList.remove() + " and " + recommendList.remove());
		label.setMinHeight(25);
		
		BP.setBottom(label);
		
		Scene scene = new Scene(BP, 400, 400);
		
		finishUpTheScreen(grid);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Library");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	@Override
	public void addListenerToTheButton(Button button, int type)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object addButtontoScreen(GridPane grid, String str, int columnIndex, int rowIndex)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void finishUpTheScreen(GridPane grid)
	{
		getInformationFromFile("\\accounts\\" + this.username + "_games.txt");
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
				String str = file.nextLine();
		        Button button = addGamesList(content, str);
		        addListenerToTheButton(button, str, primaryStage, username);
			}
		}
	}
	
	public void fillTheRecommendList() 
	{
		Scanner file = null;
		
		int rand1 = (int) (Math.random() * 3);
		int rand2 = (int) (Math.random() * 4) + 3;
		
		int count = 0;
		
		try 
		{
			file=new Scanner(new File(System.getProperty("user.dir") + "\\games.txt"),"UTF-8");
		}
		catch(Exception e) {}
		
		if(file!=null) 
		{
			while(file.hasNext()) 
			{
				String str = file.nextLine();
		        
		        if(count == rand1 || count == rand2) 
		        {
		        	recommendList.add(str);
		        }
		        
		        count++;
			}
			
			file.close();
		}
	}
}
