package application;

import java.io.File;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StoreScreen extends UIHelper implements FileHelper
{
	private VBox content;
	private Stage primaryStage;
	private String username;
	
	private TextField TF;
	
	public StoreScreen(Stage primaryStage, String username)
	{
		this.username = username;
		
		constructTheScreen(primaryStage);
	}

	public void constructTheScreen(Stage primaryStage)
	{
		VBox content = new VBox(5);
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);
        
        this.primaryStage = primaryStage;
        this.content = content;
        
        BorderPane BP = new BorderPane(scroller, null, null, null, null);
        
        this.TF = new TextField();
        
        BP.setBottom(this.TF);

        TF.textProperty().addListener((observable, oldValue, newValue) -> {
        	this.content.getChildren().removeAll(this.content.getChildren());
        	getInformationFromFile("\\games.txt");
        });

        Scene scene = new Scene(BP, 400, 400);
        
        getInformationFromFile("\\games.txt");
        
		primaryStage.setScene(scene);
		primaryStage.setTitle("Store");
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
				String str = file.nextLine();
		        if(str.toLowerCase().startsWith(TF.getText().toLowerCase())) 
		        {
		        	Button button = addGamesList(content, str);
			        addListenerToTheButton(button, str, primaryStage, username);
		        }
			}
		}
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
}
