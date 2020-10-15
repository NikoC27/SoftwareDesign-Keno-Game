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

		private Text rulesTxt;

        private Text oneSpotTxt;

        private Text fourSpotTxt;

        private Text eightSpotTxt;

        private Text tenSpotTxt;

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

		rulesTxt.setFont(Font.font(20));
        rulesTxt.setText(kenoRules);
        rulesTxt.setTextAlignment(TextAlignment.CENTER);

		rulesPane.setTop(menu);
		rulesPane.setCenter(rulesTxt);

		return new Scene(rulesPane, 1000,700);
	}

	public Scene createOddsScene()
    {
        BorderPane oddsPane = new BorderPane();

        oddsPane.setStyle("-fx-background-color: #228B22;"); // Forest Green background
        oddsPane.setBottom(returnButton);

        // Still have to set the odds
        String oneSpot   = "1 Spot Game\n" +
                "Match          Prize  \n" +
                "  1            $2     \n";

        String fourSpot  = "4 Spot Game\n" +
                "Match          Prize  \n" +
                "  4            $75    \n" +
                "  3            $5     \n" +
                "  2            $1     \n";

        String eightSpot = "8 Spot Game \n" +
                "Match          Prize   \n" +
                "  8            $10,000*\n" +
                "  7            $750    \n" +
                "  6            $50     \n" +
                "  5            $12     \n" +
                "  4            $2      \n";

        String tenSpot   = "10 Spot Game  \n" +
                "Match          Prize     \n" +
                "  10           $100,000* \n" +
                "  9            $4,250    \n" +
                "  8            $450      \n" +
                "  7            $40       \n" +
                "  6            $15       \n" +
                "  5            $2        \n" +
                "  0            $5        \n" ;

        oneSpotTxt.setText(oneSpot);
        fourSpotTxt.setText(fourSpot);
        eightSpotTxt.setText(eightSpot);
        tenSpotTxt.setText(tenSpot);


        oddsPane.setTop(menu);
        return new Scene(oddsPane, 1000,700);
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
		rulesTxt = new Text();
        oneSpotTxt = new Text();
        fourSpotTxt = new Text();
        eightSpotTxt = new Text();
        tenSpotTxt = new Text();
		betStrings = new ArrayList<>();
		spotStrings = new ArrayList<>();
		
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
		MenuItem oddsWinningsMenu = new MenuItem("Odds of Winning");
		MenuItem newLook = new MenuItem("New Look");
		MenuItem exitGame = new MenuItem("Exit Game");
		menu.getMenus().addAll(optionMenu);

		/**Putting menu items into the menu (The drop down menu)**/
		optionMenu.getItems().add(rulesMenu); //add menu item to first menu
		optionMenu.getItems().add(oddsWinningsMenu);
		optionMenu.getItems().add(newLook);
		optionMenu.getItems().add(exitGame);

		//Change the scene to the rules menu
		rulesMenu.setOnAction(e->primaryStage.setScene(createRulesScene()));

		//Change the scene to the odds menu
		oddsWinningsMenu.setOnAction(e->primaryStage.setScene(createOddsScene()));

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


		return "";
	}
}
