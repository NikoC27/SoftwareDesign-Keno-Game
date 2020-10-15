import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.HashMap;


public class JavaFXTemplate extends Application {

		private Button b1;
		
		private TextField t1;
		
		private MenuBar menu;

		private Text txt;

		private EventHandler<ActionEvent> myHandler;

		private HashMap<String, Scene> sceneMap;

		private PauseTransition pause = new PauseTransition(Duration.seconds(3));




	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//method to create our first scene with controls
//	public Scene createControlScene() {
//
//		BorderPane pane = new BorderPane();
//		pane.setPadding(new Insets(70));
//
//		VBox paneCenter = new VBox(10, text, addToListBtn, printListBtn, displayQueueItems);
//
//		pane.setCenter(paneCenter);
//		pane.setLeft(sceneChangeBtn);
//		pane.setStyle("-fx-background-color: lightPink;");
//
//
//		return new Scene(pane, 850, 750);
//	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		sceneMap = new HashMap<String,Scene>();
		primaryStage.setTitle("Welcome to Keno");

		
		b1 = new Button();
		t1 = new TextField();
		txt = new Text();
		
		/*//create an event handler if more than one widget needs same action
		EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent e) {
				t1.setText("button was pressed!");
			}
		};
		
		//attach the event handler to the button
		b1.setOnAction(myHandler);
		*/
		
		/*//use an anonymous class to attach the event handler to the button
		b1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				t1.setText("button was pressed!");
			}
		});
		*/
		
		//use a lambda expression to attach the event handler to a button
//		b1.setOnAction(e->t1.setText("I love this syntax!!!!"));

		menu = new MenuBar(); //a menu bar takes menus as children
		Menu optionMenu = new Menu("Menu");
		MenuItem rulesMenu = new MenuItem("Rules of the Game"); //a menu goes inside a menu bar
		MenuItem winningsMenu = new MenuItem("Odds of Winning");

		MenuItem iOne = new MenuItem("click me"); //menu items go inside a menu
		menu.getMenus().addAll(optionMenu);

		//event handler for menu item
		rulesMenu.setOnAction(rules(txt));

		optionMenu.getItems().add(rulesMenu); //add menu item to first menu
		optionMenu.getItems().add(winningsMenu);


		// This code demonstrates how to use a GridPane. Might be useful for your project

		// event handler is attached to each button in the GridPane
		myHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("button pressed: " + ((Button)e.getSource()).getText());
				Button b1 = (Button)e.getSource();
				b1.setDisable(true);
			}
		};

		GridPane grid = new GridPane();
		GridPane spotGrid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		addBetCard(grid); //populate the GridPane with buttons

		spotGrid.setAlignment((Pos.CENTER));
		addSpots(spotGrid);


		//Creating the vertical box

		VBox verticalB = new VBox(25,menu,spotGrid);
		verticalB.getChildren().addAll(grid,txt);
		toggleGrid(grid, true);

		//new scene with root node
		Scene sceneM = new Scene(verticalB, 1000,700);

		primaryStage.setScene(sceneM); //set the scene in the stage
		primaryStage.show(); //make visible to the user
	}

	public void toggleGrid(GridPane grid, boolean booleanToggle) {
		for(Node child: grid.getChildren()){
			child.setDisable(booleanToggle);
		}
	}

	/*
	 * method to populate a GridPane with buttons and attach a handler to each button
	 * Bet Card
	 */
	public void addBetCard(GridPane grid) {
		
		for(int x = 0; x<8; x++) {
			for(int i = 0; i<10; i++) {
				Button bets = new Button(Integer.toString(1+i+x*10));
				bets.setMinHeight(40.0);
				bets.setMinWidth(40.0);
				bets.setOnAction(myHandler);
				bets.setDisable(true);
				grid.add(bets, i, x);
			}
		}
	}
	/*
	 * method to populate a GridPane with buttons and attach a handler to each button
	 * Spots
	 */
	public void addSpots(GridPane grid)
	{
		int spotsArr[] = {1, 4, 8, 10};

		for(int i = 0; i < 4; i++)
		{
			Button spots = new Button(Integer.toString(spotsArr[i]));
			spots.setMinWidth(40.0);
			spots.setMinHeight(40.0);
			spots.setOnAction(myHandler);
			grid.add(spots,i,0);
		}
	}


	public EventHandler<ActionEvent> rules(Text txt)
	{
		String kenoRules = "Rules of Keno Gambling Game: \n" +
		"Players wager by choosing a set amount of numbers" +
				"\n (pick 2 numbers, pick 10 numbers, etc.)" +
		" ranging from 1 to 80.\n After all players have made their wagers" +
		" and picked their numbers,\n twenty numbers are drawn at random," +
			" between 1 and 80 with no\n duplicates." +
			" Players win by matching a set amount of their\n numbers to" +
			" the numbers that are randomly drawn.\n";

		txt.setFont(Font.font(20));
		txt.setText(kenoRules);
		txt.setTextAlignment(TextAlignment.CENTER);

		return null;
	}

	public void odds(Text txt)
	{

	}
}
