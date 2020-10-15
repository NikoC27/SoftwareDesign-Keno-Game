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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static javafx.application.Platform.exit;


public class JavaFXTemplate extends Application {

		private Button returnButton;
		
		private TextField t1;
		
		private MenuBar menu;

		private Text txt;

		private EventHandler<ActionEvent> betHandler;

		private EventHandler<ActionEvent> spotHandler;

		private PauseTransition pause = new PauseTransition(Duration.seconds(3));

		private ArrayList<String> betStrings;

		private ArrayList<String> spotStrings;




	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	/**Create the Scene for the Game**/
	public Scene createGameScene() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: maroon;");

		/**This is the game grid initialization for spot and grid**/
		GridPane grid = new GridPane();
		GridPane spotGrid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		spotGrid.setAlignment((Pos.CENTER));

		//Creating the vertical box
		VBox verticalB = new VBox(25,menu,spotGrid);
		verticalB.getChildren().addAll(grid);
		toggleGrid(grid, true);
		pane.setCenter(verticalB);

		/**Adds the button into the grid**/
		addBetCard(grid);
		addSpots(spotGrid);

		//new scene with root node
		return new Scene(pane, 1000,700);
	}

	/** Creates a scene for the rules **/
	public Scene createRulesScene(){
		BorderPane rulesPane = new BorderPane();

		rulesPane.setStyle("-fx-background-color: maroon;");
		rulesPane.setBottom(returnButton); //We can access return button because it is a class variable

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

		VBox verticalBox = new VBox(25,menu, txt);
		rulesPane.setCenter(verticalBox);

		return new Scene(rulesPane, 1000,700);
	}

//	public Scene createWinningsMenu(){
//		BorderPane rulesPane = new BorderPane();
//		rulesPane.setStyle("-fx-background-color: maroon;");
//
//		return new Scene();
//	}

//	public Scene createNewLook(){
//		BorderPane looksPane = new BorderPane();
//		String stringArr[] = {"maroon;", "lightblue;", "black;", "white;"};
//
//		Random rand = null;
//		looksPane.setStyle("-fx-background-color: "+ stringArr[rand.nextInt(3)]);
//
//
//		return new Scene(looksPane, 1000, 700);
//	}



	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Welcome to Keno");
		returnButton = new Button("Return to Game");  //This is from the private variable in the class
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
		/** Menu Option Code **/
		/*********************************************************************************************************/
		//Creates all the menu and the drop down items
		menu = new MenuBar(); //a menu bar takes menus as children
		Menu optionMenu = new Menu("Menu");
		MenuItem rulesMenu = new MenuItem("Rules of the Game"); //a menu goes inside a menu bar
		MenuItem winningsMenu = new MenuItem("Odds of Winning");
		MenuItem newLook = new MenuItem("New Look");
		MenuItem exitGame = new MenuItem("Exit Game");
		menu.getMenus().addAll(optionMenu);

		/**Putting menu items into the menu (The drop down menu)**/
		optionMenu.getItems().add(rulesMenu); //add menu item to first menu
		optionMenu.getItems().add(winningsMenu);
		optionMenu.getItems().add(newLook);
		optionMenu.getItems().add(exitGame);

		//Change the scene to the rules menu
		rulesMenu.setOnAction(e->primaryStage.setScene(createRulesScene()));
		//Exit the Code
		exitGame.setOnAction((e->exit()));

//		winningsMenu.setOnAction(e->primaryStage.setScene();
		primaryStage.show();
		/*********************************************************************************************************/

		/** EVENTHANDLERS **/
		/*********************************************************************************************************/
		betHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//System.out.println("button pressed: " + ((Button)e.getSource()).getText());
				Button b1 = (Button)e.getSource();
				String buttonNum = ((Button)e.getSource()).getText();
				b1.setDisable(true);
				betStrings.add(buttonNum);
			}
		};


		spotHandler = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				Button b1 =(Button)e.getSource();
				String buttonNum = ((Button)e.getSource()).getText();
				//System.out.println(buttonNum);
				spotStrings.add(buttonNum);
				//System.out.println(spotStrings.size());
				b1.setDisable(true);
			}
		};

		/*********************************************************************************************************/

		/**This makes the return button go to the game scene(Temporary)**/
		returnButton.setOnAction(e->primaryStage.setScene(createGameScene()));

		/**Sets the game scene immediately (Temporary)**/
		primaryStage.setScene(createGameScene()); //set the scene in the stage
		primaryStage.show(); //make visible to the user
	}

	/**Completely change the entire board to Enabled/Disabled**/
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
				bets.setOnAction(betHandler);
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
			spots.setOnAction(spotHandler);
			grid.add(spots,i,0);
		}
	}


	public void odds(Text txt)
	{

	}


	// Return the string of the button that was pressed
	public String spotNum()
	{
		for(int i = 0; i < spotStrings.size(); i++)
		{
			if(spotStrings.get(i) == "1" | spotStrings.get(i) == "4"|
					spotStrings.get(i) == "8" | spotStrings.get(i) == "10")
			{
				return spotStrings.get(i);
			}
		}

		return "";
	}
}
