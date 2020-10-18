import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.awt.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;
import static javafx.application.Platform.exit;

public class JavaFXTemplate extends Application {

	private int colorPreset = 0;

	/**Grid Color Schemes**/
	private final String[] defaults = {"-fx-text-fill: black; -fx-background-color:white; -fx-border-color: black;", "-fx-text-fill: #377178; -fx-background-color:#8AE1EB; -fx-border-color: #377178; "};
	private final String[] selected = {"-fx-text-fill: red;   -fx-background-color:black; -fx-border-color: white;", "-fx-text-fill: lightblue; -fx-background-color:#377178; -fx-border-color: #8AE1EB; "};
	private final String[] computer = {"-fx-text-fill: blue;  -fx-background-color:white; -fx-border-color: blue;", "-fx-text-fill: #BDA0BC; -fx-background-color:#8AE1EB; -fx-border-color: #BDA0BC; "};
	private final String[] winnings = {"-fx-text-fill: gold;  -fx-background-color:black; -fx-border-color: gold;", "-fx-text-fill: #4B296B; -fx-background-color:#8AE1EB; -fx-border-color: #4B296B;"};
	private final String[] backgroundColors = {"maroon;", "#64C6ED;", "#FFD700;"};

	private String buttonSize = "-fx-font-weight: bold; -fx-border-width: 5; -fx-pref-width: 50; -fx-pref-height: 50";
	private String medButtonSize = "-fx-font-weight: bold; -fx-border-width: 5; -fx-pref-width: 150; -fx-pref-height: 50";
	private String largeButtonSize = "-fx-font-weight: bold; -fx-border-width: 5; -fx-pref-width: 500; -fx-pref-height: 50";

	//Booleans which stage of the game
	private boolean gameStarted;
	private boolean gamblePressed;
	private boolean spotSelected;

	//Important Buttons
	private Button returnButton;
	private Button gambleButton;
	private Button resetButton;
	private Button startButton;
	private Button scoreButton;
	private Button randomButton;
	private Button welcomeButton;


	//Menu
	public MenuBar menu;

	/**Text Variables**/
	private Text rulesTxt;
	private Text oneSpotTxt;
	private Text fourSpotTxt;
	private Text eightSpotTxt;
	private Text tenSpotTxt;
	private Text welcomeTxt;
	/******************/

	//All Event Handlers
	private EventHandler<ActionEvent> betHandler;
	private EventHandler<ActionEvent> spotHandler;
	private EventHandler<ActionEvent> gambleHandler;
	private EventHandler<ActionEvent> resetHandler;
	private EventHandler<ActionEvent> startHandler;
	private EventHandler<ActionEvent> scoreHandler;
	private EventHandler<ActionEvent> randomHandler;
	private EventHandler<ActionEvent> drawingsHandler;

	//Array list
	private ArrayList<String> betStrings;
	private ArrayList<Integer> drawingsSelected = new ArrayList<Integer>();
	private ArrayList<Integer> randomSelected;

	//The two Grids
	private GridPane grid;
	private GridPane spotGrid;

	// The two border panes
	private BorderPane gameScene;
	private BorderPane rulesScene;
	private BorderPane welcomeScene;

	private ComboBox<Integer> drawings;

	private int buttonsPress;
	private int matchingPress;
	private int drawingPressed;
	private int iterator = 0;
	private int moneyEarned = 0;

	private Random randomNums;
	private Timeline timeline;

	public static void main(String[] args) {
		launch(args);
	}

	public void addToDrawingsSelected()
	{
		int temp;
		randomNums = new Random();

		/**Continues to add numbers to the array list until there are 20 with no dups**/
			while(drawingsSelected.size() < 20){
				temp = randomNums.nextInt(80) + 1;
				if(!drawingsSelected.contains(temp)){
					drawingsSelected.add(temp);
				}
			}
	}

	public void createComboBox(){
		drawings = new ComboBox<>();
		drawings.setPromptText("Draw a Value");
		drawings.getItems().addAll(1, 2, 3, 4);
	}


	public Scene createWelcomeScene(){
		welcomeScene = new BorderPane();
		welcomeTxt = new Text("Welcome to Keno!");
		welcomeTxt.setFont(javafx.scene.text.Font.font(null, FontWeight.BOLD, 30));
		welcomeButton.setStyle(winnings[0] + medButtonSize);
		VBox welcomeBox = new VBox(40 ,welcomeTxt, welcomeButton);
		welcomeBox.setAlignment(Pos.CENTER);
		welcomeScene.setCenter(welcomeBox);
		welcomeScene.setStyle("-fx-background-color: " + backgroundColors[2]);
		welcomeScene.setBorder(new Border(new BorderStroke(Color.BLACK,
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(10,10,10,10))));
		return new Scene(welcomeScene, 1000, 700);
	}

	/**Create the Scene for the Game**/
	public Scene createGameScene() {
		gameScene = new BorderPane();

		gambleButton = new Button("Gamble!");
		gambleButton.setStyle(selected[colorPreset] + medButtonSize);
		gambleButton.setOnAction(gambleHandler);

		resetButton = new Button("Next Draw");
		resetButton.setStyle(selected[colorPreset] + medButtonSize);
		resetButton.setOnAction(resetHandler);

		timeline = new Timeline(
			new KeyFrame(Duration.millis(200), startHandler)
		);

		timeline.setCycleCount(20);

		createComboBox();
		drawings.setOnAction(drawingsHandler);

		startButton = new Button("Start");
		startButton.setStyle(selected[colorPreset] + medButtonSize);
		startButton.setOnAction(e->{
			addToDrawingsSelected(); timeline.play();
		});

		scoreButton = new Button("Click to Reveal Score!");
		scoreButton.setStyle(winnings[colorPreset] + largeButtonSize);
		scoreButton.setOnAction(scoreHandler);

		randomButton= new Button("Random");
		randomButton.setStyle(selected[colorPreset] + medButtonSize);
		randomButton.setOnAction(randomHandler);



		drawings.setStyle(selected[colorPreset] + medButtonSize);


		gameScene.setStyle("-fx-background-color: "+ backgroundColors[colorPreset]);
		/**This is the game grid initialization for spot and grid**/
		grid = new GridPane();
		grid.setMouseTransparent(true);
		spotGrid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		spotGrid.setAlignment((Pos.CENTER));

		HBox gambleHB = new HBox(25, gambleButton);
		gambleHB.setAlignment(Pos.CENTER);

		//Creating the vertical box
		VBox verticalB = new VBox(25,spotGrid, gambleHB);
		HBox settingsHB = new HBox(25, startButton, randomButton, resetButton);
		HBox scoreHB = new HBox(25, scoreButton);
		HBox drawingsHB = new HBox(25, drawings);

		drawingsHB.setAlignment(Pos.CENTER_LEFT);
		scoreHB.setAlignment(Pos.CENTER);
		settingsHB.setAlignment((Pos.CENTER));
		verticalB.getChildren().addAll(grid, settingsHB, scoreHB);
		gameScene.setCenter(verticalB);
		gameScene.setLeft(drawingsHB);
		gameScene.setTop(menu);

		/**Adds the button into the grid**/
		addBetCard(grid);
		addSpots(spotGrid);

		//new scene with root node
		return new Scene(gameScene, 1000,700);
	}

	/** Creates a scene for the rules **/
	public Scene createRulesScene(){
		rulesScene = new BorderPane();

		rulesScene.setStyle("-fx-background-color: "+ backgroundColors[colorPreset]);
		rulesScene.setBottom(returnButton); //We can access return button because it is a class variable

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

		rulesScene.setTop(menu);
		rulesScene.setCenter(rulesTxt);

		return new Scene(rulesScene, 1000,700);
	}

	public Scene createOddsScene()
	{
		BorderPane oddsPane = new BorderPane();
		oddsPane.setStyle("-fx-background-color: "+ backgroundColors[colorPreset]);

		oddsPane.setBottom(returnButton);

		// Still have to set the odds
		String oneSpot   = "1 Spot Game\n" +
				"Match		Prize\n" +
				"  1                       $2     \n" +
				"Overall odds: 1 in 4.00\n";

		String fourSpot  = "4 Spot Game\n" +
				"Match		Prize  \n" +
				"  4                   $75    \n" +
				"  3                   $5     \n" +
				"  2                   $1     \n" +
				"Overall odds: 1 in 3.86\n";

		String eightSpot = "8 Spot Game \n" +
				"Match		Prize   \n" +
				"  8                    $10,000*\n" +
				" 7                     $750    \n" +
				"6                     $50     \n" +
				"5                     $12     \n" +
				"4                     $2      \n" +
				"Overall odds: 1 in 9.77\n";

		String tenSpot = "10 Spot Game  \n" +
				"Match		Prize     \n" +
				"   10		    $100,000* \n" +
				"  9		   $4,250    \n" +
				"  8                $450      \n" +
				"  7                $40       \n" +
				"  6                $15       \n" +
				"  5                $2        \n" +
				"  0                $5        \n" +
				"Overall odds: 1 in 9.05\n";

		textSettings(oneSpotTxt, oneSpot);
		textSettings(fourSpotTxt, fourSpot);
		textSettings(eightSpotTxt, eightSpot);
		textSettings(tenSpotTxt, tenSpot);

		//Puts all the text into the HBox and put them into the scene
		HBox oddsMenu = new HBox(30, oneSpotTxt, fourSpotTxt, eightSpotTxt, tenSpotTxt);
		oddsMenu.setAlignment(Pos.TOP_CENTER);
		oddsPane.setCenter(oddsMenu);
		oddsPane.setTop(menu);
		return new Scene(oddsPane, 1000,700);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Keno Gambling Game");

		welcomeButton = new Button("Click to Start Game");
		returnButton = new Button("Return to Game");  //This is from the private variable in the class
		rulesTxt = new Text();
		oneSpotTxt = new Text();
		fourSpotTxt = new Text();
		eightSpotTxt = new Text();
		tenSpotTxt = new Text();
		betStrings = new ArrayList<>();

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

		// Button for the welcoming scene
		welcomeButton.setOnAction(e->primaryStage.setScene(createGameScene()));

		//Change the scene to the rules menu
		rulesMenu.setOnAction(e->primaryStage.setScene(createRulesScene()));

		//Change the scene to the odds menu
		oddsWinningsMenu.setOnAction(e->primaryStage.setScene(createOddsScene()));

		//Change the theme colors
		newLook.setOnAction(event -> {
			randomNums = new Random();
			int oldPreset = colorPreset;

			while(colorPreset == oldPreset){
				colorPreset = randomNums.nextInt(2);
			}

			gameScene.setStyle( "-fx-background-color: "+ backgroundColors[colorPreset]);
			returnButton.setStyle(selected[colorPreset] + medButtonSize);

			if(gambleButton.getStyle().equals(selected[oldPreset] + largeButtonSize)){
				gambleButton.setStyle(selected[colorPreset] + largeButtonSize);
			}
			else{
				gambleButton.setStyle(selected[colorPreset] + medButtonSize);
			}

			drawings.setStyle(selected[colorPreset] + medButtonSize);
			resetButton.setStyle(selected[colorPreset] + medButtonSize);
			startButton.setStyle(selected[colorPreset] + medButtonSize);
			scoreButton.setStyle(winnings[colorPreset] + largeButtonSize);
			randomButton.setStyle(selected[colorPreset] + medButtonSize);

			for(Node child: spotGrid.getChildren()){
				child.setStyle(defaults[colorPreset] + buttonSize);
			}

			for(Node child: grid.getChildren()){
				if(child.getStyle().equals(defaults[oldPreset] + buttonSize)){
					child.setStyle(defaults[colorPreset] + buttonSize);
				}
				if(child.getStyle().equals(selected[oldPreset] + buttonSize)){
					child.setStyle(selected[colorPreset] + buttonSize);
				}
				if(child.getStyle().equals(computer[oldPreset] + buttonSize)){
					child.setStyle(computer[colorPreset] + buttonSize);
				}
				if(child.getStyle().equals(winnings[oldPreset] + buttonSize)){
					child.setStyle(winnings[colorPreset] + buttonSize);
				}
			}
		}
		);

		//Exit the Code
		exitGame.setOnAction((e->exit()));

		primaryStage.show();
		/*********************************************************************************************************/

		/** EVENTHANDLERS **/
		/*********************************************************************************************************/
		betHandler = e -> {
			Button b1 = (Button)e.getSource();
			String buttonNum = ((Button)e.getSource()).getText();

			/**Check if the style is the pressed button for undoing a press**/
			if(b1.getStyle().equals(selected[colorPreset]+ buttonSize)){
				b1.setStyle(defaults[colorPreset] + buttonSize);
				betStrings.remove(buttonNum);
				buttonsPress++;

			}
			/**Check how many presses are available to make sure they don't press more spot than allowed**/
			else if(buttonsPress != 0){
				b1.setStyle(selected[colorPreset] + buttonSize);
				betStrings.add(buttonNum);
				buttonsPress--;
			}

			/**Display the spots left as the player presses**/
			gambleButton.setText("Number of Spots: " + buttonsPress);
		};

		spotHandler = e -> {
			String buttonNum = ((Button)e.getSource()).getText();
			buttonsPress = Integer.parseInt(buttonNum.trim());  //String to Int
			spotSelected = true;
			toggleGrid(spotGrid, false);
		};

		gambleHandler = e -> {
			if(buttonsPress > 0 && drawingPressed > 0){
				toggleGrid(grid, false);
				toggleGrid(spotGrid, true);
				Button b1 = (Button)e.getSource();
				b1.setStyle(selected[colorPreset] + largeButtonSize);
				b1.setText("Number of Spots: " + buttonsPress);
				gamblePressed = true;
			}
		};

		//Resets all values
		resetHandler = e -> { // NEXT DRAW HANDLER!!

			// Game has not started or animation has not finished
			if(gameStarted == false || iterator != 20){
				return;
			}

			if(drawingPressed == 1 && iterator == 20){

				resetButton.setText("Next Draw");
				toggleGrid(spotGrid, false);
				toggleGrid(grid, true);
				resetGameGrid(grid);
				//Reset and re-enabling the buttons
				gambleButton.setText("Gamble!");
				gambleButton.setStyle(selected[colorPreset] + medButtonSize);
				gamblePressed = false;
				spotSelected = false;
			}

			/**Goes through the entire grid and change the colors selected by computer**/
			for(Node child: grid.getChildren()){
				final Button button = (Button) child;

					// Change blue squares back to default squares
					if(child.getStyle().equals(computer[colorPreset] + buttonSize)){
						child.setStyle(defaults[colorPreset]+ buttonSize);
					}
					// Change matching squares back to selected squares
					if(child.getStyle().equals(winnings[colorPreset] + buttonSize)){
						child.setStyle(selected[colorPreset] + buttonSize);
						matchingPress++;
					}
				}

			startButton.setDisable(false);
			scoreButton.setText("Click to Reveal Score!");
			gameStarted = false;


			drawingPressed--;
			buttonsPress = 0;
			matchingPress = 0;
			iterator = 0;


		};

		/**The Handler for the Start button**/
		startHandler = e -> {

			/**Check If the user pressed the necessary things**/
			if(drawingPressed == 0){
				return;
			}
			if(drawingPressed == 1){
				resetButton.setText("New Game");
			}

			if(!gamblePressed || buttonsPress != 0 || !spotSelected){
				return;
			}

			gameStarted = true;

			if(e.getSource() instanceof Button)
			{
				Button b1 = (Button)e.getSource();
				b1.setDisable(true);
			}


			toggleGrid(grid, true);

			/**Goes through the entire grid and change the colors selected by computer**/
			for(Node child: grid.getChildren()){
				final Button button = (Button) child;
				//if(drawingsSelected.contains(Integer.parseInt(button.getText().trim()))){
				if(Integer.parseInt(button.getText().trim()) == drawingsSelected.get(iterator)){
					//If the computer chose the button and player did not select
					if(child.getStyle().equals(defaults[colorPreset] + buttonSize)){
						child.setStyle(computer[colorPreset]+ buttonSize);
					}
					//If the computer chose the button and player selected
					if(child.getStyle().equals(selected[colorPreset] + buttonSize)){
						child.setStyle(winnings[colorPreset] + buttonSize);
						matchingPress++;
					}
				}
			}

			iterator++;
		};

		/**Handler for the Score Button**/
		scoreHandler = e->{
			Button b1 = (Button)e.getSource();
			if(gameStarted && spotSelected && gamblePressed && (buttonsPress == 0)) {
				b1.setText("Matching Score: " + matchingPress);
			}
		};

		/**Handler for the random button**/
		randomHandler = e->{
			randomNums = new Random();
			randomSelected = new ArrayList<>();
			if(drawingPressed == 0 || !(buttonsPress > 0) || !gamblePressed || !spotSelected){
				return;
			}


			//Get button press for the user
			while(buttonsPress > 0){
				int temp = randomNums.nextInt(80) + 1;
				if(!randomSelected.contains(temp)){
					randomSelected.add(temp);
					buttonsPress--;
				}
			}

			//Show the button pressed the computer chose
			for(Node child: grid.getChildren()){
				final Button button = (Button) child;
				if(randomSelected.contains(Integer.parseInt(button.getText().trim()))){
					child.setStyle(selected[colorPreset] + buttonSize);
					gambleButton.setText("Number of Spots: " + buttonsPress);
				}
			}
		};


		drawingsHandler = e-> {

			switch(drawings.getValue()){
				case 1:
					drawingPressed = 1;
					break;

				case 2:
					drawingPressed = 2;
					break;

				case 3:
					drawingPressed = 3;
					break;

				case 4:
					drawingPressed = 4;
					break;

				default:
					drawingPressed = 0;
					break;
			};

	    };


		/*********************************************************************************************************/

		/**This makes the return button go to the game scene(Temporary)**/
		returnButton.setOnAction(e->primaryStage.setScene(createGameScene()));

		/**Sets the game scene immediately (Temporary)**/
		primaryStage.setScene(createWelcomeScene()); //set the scene in the stage
		primaryStage.show(); //make visible to the user
	}

	/**Completely change the entire board to Enabled/Disabled**/
	public void toggleGrid(GridPane grid, boolean booleanToggle) {
		for(Node child: grid.getChildren()){
			child.setDisable(booleanToggle);
		}
	}

	/**Completely reset the Game Grid**/
	public void resetGameGrid(GridPane grid){
		for(Node child: grid.getChildren()){
			child.setStyle(defaults[colorPreset] + buttonSize);
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
				bets.setStyle(defaults[colorPreset] + buttonSize);
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
		int[] spotsArr = {1, 4, 8, 10};

		for(int i = 0; i < 4; i++)
		{
			Button spots = new Button(Integer.toString(spotsArr[i]));
			spots.setStyle(defaults[colorPreset] + buttonSize);
			spots.setOnAction(spotHandler);
			grid.add(spots,i,0);
		}
	}

	//Function for creating the text of odds menu
	public void textSettings(Text tempText, String stringText){
		tempText.setText(stringText);
		tempText.setTextAlignment(TextAlignment.CENTER);
		tempText.setFont(Font.font(20.0));
	}

}
