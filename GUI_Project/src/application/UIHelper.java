package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class UIHelper
{
	public GridPane createPane(int hGap, int vGap, int insetVal, boolean isAligned) 
	{
		GridPane grid = new GridPane();
		
		if(isAligned) 
		{
			grid.setAlignment(Pos.CENTER);
		}
		
		grid.setHgap(hGap);
		grid.setVgap(vGap);
		grid.setPadding(new Insets(insetVal, insetVal, insetVal, insetVal));
		return grid;
	}

	public Object addUItoScreen(GridPane grid, String type, String str,int columnIndex, int rowIndex) 
	{
		if(type.equals("Text")) 
		{
			Text sceneTitle = new Text(str);
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
			grid.add(sceneTitle, columnIndex, rowIndex, 2, 1);
			return sceneTitle;
		}
		
		else if(type.equals("Label")) 
		{
			Label userName = new Label(str);
			grid.add(userName, columnIndex, rowIndex);
			return userName;
		}
		
		else if(type.equals("TextField")) 
		{
			TextField userTextField = new TextField();
			grid.add(userTextField, columnIndex, rowIndex);
			return userTextField;
		}
		
		else if(type.equals("PasswordField")) 
		{
			PasswordField pwBox = new PasswordField();
			grid.add(pwBox, columnIndex, rowIndex);
			return pwBox;
		}
		
		return null;
	}

	public abstract void addListenerToTheButton(Button button, int type);
	
	public abstract Object addButtontoScreen(GridPane grid, String str,int columnIndex, int rowIndex);

	public ImageView addImageToImageView(ImageView imageView, String gameName, int currentImageID, GridPane grid)
	{
		if(imageView == null) 
		{
			imageView = new ImageView();
		}
		
		InputStream stream = null;
		try
		{
			stream = new FileInputStream(System.getProperty("user.dir") + "\\games\\" + gameName + "\\" + currentImageID + ".jpg");
		} catch (FileNotFoundException e){}
		
		if(stream == null) 
		{
			return null;
		}
		
	    Image image = new Image(stream);
	    
	    imageView.setImage(image);

	    imageView.setFitWidth(550);
	    imageView.setFitHeight(300);
	   
	    grid.add(imageView, 0, 0);
	    
	    try
		{
			stream.close();
		} catch (IOException e){}
	    
	    return imageView;
	}

	public Button addGamesList(VBox content, String gameName) 
	{
		AnchorPane anchorPane = new AnchorPane();
        
        Label label = new Label(gameName);
        AnchorPane.setLeftAnchor(label, 5.0);
        AnchorPane.setTopAnchor(label, 5.0);
        
        Button button = new Button("Check Page");
        
        AnchorPane.setRightAnchor(button, 5.0);
        AnchorPane.setTopAnchor(button, 5.0);
        AnchorPane.setBottomAnchor(button, 5.0);
        anchorPane.getChildren().addAll(label, button);
        content.getChildren().add(anchorPane);
        
        return button;
	}
	
	public void addListenerToTheButton(Button button, String gameName, Stage currentStage, String username) 
	{
		button.setOnAction(new EventHandler<ActionEvent>() 
		{	 
		    @Override
		    public void handle(ActionEvent e) 
		    {
		    	GameScreen GS = new GameScreen(currentStage, gameName, username);
	        	GS.toString();
		    }
		});
	}
}
