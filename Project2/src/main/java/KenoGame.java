/* Created By Jay Chen
 * and Niko Castellana
 *
 * CS 342 Project 2
 *
 * Professor Hallenbeck
 *
 * Keno Project
 */

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
import java.util.ArrayList;
import java.util.Random;
import static javafx.application.Platform.exit;

public class KenoGame extends Application {

	/*Constant used for colors*/
	private int colorPreset = 0;

	/*Grid Color Schemes*/
	private final String[] defaults = {"-fx-text-fill: black; -fx-background-color:white; -fx-border-color: black;", "-fx-text-fill: #377178; -fx-background-color:#8AE1EB; -fx-border-color: #377178; "};
	private final String[] selected = {"-fx-text-fill: red;   -fx-background-color:black; -fx-border-color: white;", "-fx-text-fill: lightblue; -fx-background-color:#377178; -fx-border-color: #8AE1EB; "};
	private final String[] computer = {"-fx-text-fill: blue;  -fx-background-color:white; -fx-border-color: blue;", "-fx-text-fill: #BDA0BC; -fx-background-color:#8AE1EB; -fx-border-color: #BDA0BC; "};
	private final String[] winnings = {"-fx-text-fill: gold;  -fx-background-color:black; -fx-border-color: gold;", "-fx-text-fill: #4B296B; -fx-background-color:#8AE1EB; -fx-border-color: #4B296B;"};
	private final String[] backgroundColors = {"maroon;", "#64C6ED;", "#FFD700;"};

	/*Button Color Schemes*/
	private String buttonSize = "-fx-font-weight: bold; -fx-border-width: 5; -fx-pref-width: 50; -fx-pref-height: 50";
	private String medButtonSize = "-fx-font-weight: bold; -fx-border-width: 5; -fx-pref-width: 150; -fx-pref-height: 50";
	private String largeButtonSize = "-fx-font-weight: bold; -fx-border-width: 5; -fx-pref-width: 500; -fx-pref-height: 50";

	/*Booleans for each stage of the game*/
	private boolean gameStarted;
	private boolean gamblePressed;
	private boolean spotSelected;

	/*Interface Buttons*/
	private Button returnButton;
	private Button gambleButton;
	private Button resetButton;
	private Button startButton;
	private Button scoreButton;
	private Button randomButton;
	private Button welcomeButton;

	/*Menu*/
	public MenuBar menu;

	/*Text Variables*/
	private Text rulesTxt;
	private Text oneSpotTxt;
	private Text fourSpotTxt;
	private Text eightSpotTxt;
	private Text tenSpotTxt;
	private Text welcomeTxt;

	/*Event Handlers*/
	private EventHandler<ActionEvent> betHandler;
	private EventHandler<ActionEvent> spotHandler;
	private EventHandler<ActionEvent> gambleHandler;
	private EventHandler<ActionEvent> resetHandler;
	private EventHandler<ActionEvent> startHandler;
	private EventHandler<ActionEvent> scoreHandler;
	private EventHandler<ActionEvent> randomHandler;
	private EventHandler<ActionEvent> drawingsHandler;

	/*Array Lists*/
	private ArrayList<String> betStrings;
	private ArrayList<Integer> drawingsSelected = new ArrayList<Integer>();
	private ArrayList<Integer> randomSelected;

	/*Grids*/
	private GridPane grid;
	private GridPane spotGrid;

	/*Border Panes*/
	private BorderPane gameScene;
	private BorderPane rulesScene;
	private BorderPane welcomeScene;

	/*Combo Box*/
	private ComboBox<Integer> drawings;

	/*Integer counter and saved values */
	private int buttonsPress;
	private int matchingPress;
	private int drawingPressed;
	private int saveDrawNumber;
	private int iterator = 0;
	private int moneyEarned = 0;
	private int spotNumber;

	/*Random number generator*/
	private Random randomNums;

	/*Animations timeline*/
	private Timeline timeline;

	public static void main(String[] args) {
		launch(args);
	}


	/* Function generates random numbers and
	adds them to an array list */
	public void addToDrawingsSelected()
	{
		int temp;
		randomNums = new Random();
		drawingsSelected.clear();

		/*Continues to add numbers to the array list until there are 20 with no duplicates*/
			while(drawingsSelected.size() < 20){
				temp = randomNums.nextInt(80) + 1;
				if(!drawingsSelected.contains(temp)){
					drawingsSelected.add(temp);
				}
			}
	}


	/*Calculates the total money won in the game*/
	public void calcTotalMoney(){

		/*1 Spot Earnings*/
		if(spotNumber == 1){
			/*Check # of matches*/
			switch(matchingPress){
				case 1:
					moneyEarned += 2;
					break;
				default:
					break;
			};
		}

		/*4 Spot Earnings*/
		if(spotNumber == 4) {
			/*Check # of matches*/
			switch (matchingPress) {
				case 2:
					moneyEarned += 1;
					break;
				case 3:
					moneyEarned += 5;
					break;
				case 4:
					moneyEarned += 75;
					break;
				default:
					break;
			};
		}

		/*8 Spot Earnings*/
		if(spotNumber == 8){
			/*Check # of matches*/
			switch(matchingPress){
				case 4:
					moneyEarned += 2;
					break;
				case 5:
					moneyEarned += 12;
					break;
				case 6:
					moneyEarned += 50;
					break;
				case 7:
					moneyEarned += 750;
					break;
				case 8:
					moneyEarned += 10000;
					break;
				default:
					break;
			};
		}

		/*10 Spot Earnings*/
		if(spotNumber == 10){
			/*Check # of matches*/
			switch(matchingPress){
				case 0:
					moneyEarned += 5;
					break;
				case 5:
					moneyEarned += 2;
					break;
				case 6:
					moneyEarned += 15;
					break;
				case 7:
					moneyEarned += 40;
					break;
				case 8:
					moneyEarned += 450;
					break;
				case 9:
					moneyEarned += 4250;
					break;
				case 10:
					moneyEarned += 100000;
					break;
				default:
					break;
			};
		}
	}


	/*Creates comboBox box with values (1-4)*/
	public void createComboBox(){
		drawings = new ComboBox<>();
		drawings.setPromptText("Draw a Value");
		drawings.getItems().addAll(1, 2, 3, 4);
	}

	/*Creates the welcome scene for the game*/
	public Scene createWelcomeScene(){

		/*Create BorderPane for welcome scene*/
		welcomeScene = new BorderPane();

		/*Display and style the welcome text*/
		welcomeTxt = new Text("Welcome to Keno!");
		welcomeTxt.setFont(javafx.scene.text.Font.font(null, FontWeight.BOLD, 30));
		welcomeButton.setStyle(winnings[0] + "-fx-font-weight: bold; -fx-border-width: 5; -fx-pref-width: 200; -fx-pref-height: 50");

		/*Create and align VBox*/
		VBox welcomeBox = new VBox(40 ,welcomeTxt, welcomeButton);
		welcomeBox.setAlignment(Pos.CENTER);
		welcomeScene.setCenter(welcomeBox);

		/*Create background color and border*/
		welcomeScene.setStyle("-fx-background-color: " + backgroundColors[2]);
		welcomeScene.setBorder(new Border(new BorderStroke(Color.BLACK,
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(10,10,10,10))));

		return new Scene(welcomeScene, 1000, 700);
	}


	/*Creates the Scene for the Game*/
	public Scene createGameScene() {

		/*Create BorderPane for game scene*/
		gameScene = new BorderPane();

		/*Create/Style gamble button and call the gamble handler*/
		gambleButton = new Button("Gamble!");
		gambleButton.setStyle(selected[colorPreset] + medButtonSize);
		gambleButton.setOnAction(gambleHandler);

		/*Create/Style reset button and call the reset handler*/
		resetButton = new Button("Next Draw");
		resetButton.setStyle(selected[colorPreset] + medButtonSize);
		resetButton.setOnAction(resetHandler);

		/*Create timeline for animation and call the start handler*/
		timeline = new Timeline(
			new KeyFrame(Duration.millis(200), startHandler)
		);

		/*Fill the array list with 20 random buttons and set the timeline cycle*/
		addToDrawingsSelected();
		timeline.setCycleCount(drawingsSelected.size());

		/*Create/Style start button and call the timeline animation*/
		startButton = new Button("Start");
		startButton.setStyle(selected[colorPreset] + medButtonSize);
		startButton.setDisable(true);
		startButton.setOnAction(e->{
			if(buttonsPress == 0){
				timeline.play();
			}

		});

		/*Create/Style score button and call the score handler*/
		scoreButton = new Button("Click to Reveal Score!");
		scoreButton.setStyle(winnings[colorPreset] + largeButtonSize);
		scoreButton.setOnAction(scoreHandler);

		/*Create/Style random button and call the random handler*/
		randomButton= new Button("Random");
		randomButton.setStyle(selected[colorPreset] + medButtonSize);
		randomButton.setOnAction(randomHandler);

		/*Create/Style the draw ComboBox and call the drawings handler*/
		createComboBox();
		drawings.setOnAction(drawingsHandler);
		drawings.setStyle(selected[colorPreset] + medButtonSize);

		/*Set the background style for game scene*/
		gameScene.setStyle("-fx-background-color: "+ backgroundColors[colorPreset]);

		/*This is the game grid initialization for spot and grid*/
		grid = new GridPane();
		grid.setMouseTransparent(true);
		spotGrid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		spotGrid.setAlignment((Pos.CENTER));

		/*Put buttons and grids in HBox's and align them*/
		HBox gambleHB = new HBox(25, gambleButton);
		gambleHB.setAlignment(Pos.CENTER);
		HBox drawAndSpotHB = new HBox(25,drawings, spotGrid);
		drawAndSpotHB.setAlignment(Pos.TOP_CENTER);
		HBox settingsHB = new HBox(25, startButton, randomButton, resetButton);
		settingsHB.setAlignment((Pos.CENTER));
		HBox scoreHB = new HBox(25, scoreButton);
		scoreHB.setAlignment(Pos.CENTER);

		/*Put the HBox's and grid in a VBox*/
		VBox verticalB = new VBox(25,drawAndSpotHB, gambleHB);
		verticalB.getChildren().addAll(grid, settingsHB, scoreHB);
		gameScene.setCenter(verticalB);

		/* Set the menu for the game scene*/
		gameScene.setTop(menu);

		/*Adds the button into the grid and spot grid*/
		addBetCard(grid);
		addSpots(spotGrid);

		return new Scene(gameScene, 1000,700);
	}


	/* Creates a scene for the rules */
	public Scene createRulesScene(){

		/*Create BorderPane for rules scene*/
		rulesScene = new BorderPane();

		/*Set style for scene and put return button on bottom*/
		rulesScene.setStyle("-fx-background-color: "+ backgroundColors[colorPreset]);
		rulesScene.setBottom(returnButton);

		/*Rules of the game*/
		String kenoRules = "Rules of Keno Gambling Game: \n" +
				"Players wager by choosing a set amount of numbers" +
				"\n (pick 2 numbers, pick 10 numbers, etc.)" +
				" ranging from 1 to 80.\n After all players have made their wagers" +
				" and picked their numbers,\n twenty numbers are drawn at random," +
				" between 1 and 80 with no\n duplicates." +
				" Players win by matching a set amount of their\n numbers to" +
				" the numbers that are randomly drawn.\n";

		/*Style and align the rules of the game*/
		rulesTxt.setFont(Font.font(20));
		rulesTxt.setText(kenoRules);
		rulesTxt.setTextAlignment(TextAlignment.CENTER);
		rulesScene.setCenter(rulesTxt);

		/*Set the menu at the top of the scene*/
		rulesScene.setTop(menu);

		return new Scene(rulesScene, 1000,700);
	}


	/* Creates the a scene for the odds of winning the game */
	public Scene createOddsScene()
	{
		/*Create BorderPane for the odds scene*/
		BorderPane oddsPane = new BorderPane();

		/*Set style of odds scene and put return button on bottom*/
		oddsPane.setStyle("-fx-background-color: "+ backgroundColors[colorPreset]);
		oddsPane.setBottom(returnButton);

		/*Odds of winning*/
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

		/*Put the odds in separate texts*/
		textSettings(oneSpotTxt, oneSpot);
		textSettings(fourSpotTxt, fourSpot);
		textSettings(eightSpotTxt, eightSpot);
		textSettings(tenSpotTxt, tenSpot);

		/*Put the menu text in HBox and add to center of scene*/
		HBox oddsMenu = new HBox(30, oneSpotTxt, fourSpotTxt, eightSpotTxt, tenSpotTxt);
		oddsMenu.setAlignment(Pos.TOP_CENTER);
		oddsPane.setCenter(oddsMenu);

		/*Set menu at top of scene*/
		oddsPane.setTop(menu);

		return new Scene(oddsPane, 1000,700);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		/*Set the title of the game*/
		primaryStage.setTitle("Keno Gambling Game");

		/* Initialize objects*/
		welcomeButton = new Button("Click to Start Game");
		returnButton = new Button("Return to Game");
		rulesTxt = new Text();
		oneSpotTxt = new Text();
		fourSpotTxt = new Text();
		eightSpotTxt = new Text();
		tenSpotTxt = new Text();
		betStrings = new ArrayList<>();

		/*Creates all the menu and the drop down items*/
		menu = new MenuBar(); //a menu bar takes menus as children
		Menu optionMenu = new Menu("Menu");
		MenuItem rulesMenu = new MenuItem("Rules of the Game");
		MenuItem oddsWinningsMenu = new MenuItem("Odds of Winning");
		MenuItem newLook = new MenuItem("New Look");
		MenuItem exitGame = new MenuItem("Exit Game");
		menu.getMenus().addAll(optionMenu);

		/*Putting menu items into the menu (The drop down menu)*/
		optionMenu.getItems().add(rulesMenu); //add menu item to first menu
		optionMenu.getItems().add(oddsWinningsMenu);
		optionMenu.getItems().add(newLook);
		optionMenu.getItems().add(exitGame);

		/*Button for the welcoming scene*/
		welcomeButton.setOnAction(e->primaryStage.setScene(createGameScene()));

		/*Changes the scene to the rules menu*/
		rulesMenu.setOnAction(e->primaryStage.setScene(createRulesScene()));

		/*Changes the scene to the odds menu*/
		oddsWinningsMenu.setOnAction(e->primaryStage.setScene(createOddsScene()));

		/*Changes the theme colors*/
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

		/*Exits the game */
		exitGame.setOnAction((e->exit()));

		/*Show the scene*/
		primaryStage.show();

		/*Handler for the bet buttons in the grid*/
		betHandler = e -> {

			/* Get the button clicked and its number */
			Button b1 = (Button)e.getSource();
			String buttonNum = ((Button)e.getSource()).getText();

			/*Check if the style is the pressed button for undoing a press*/
			if(b1.getStyle().equals(selected[colorPreset]+ buttonSize)){
				b1.setStyle(defaults[colorPreset] + buttonSize);
				betStrings.remove(buttonNum);
				buttonsPress++;

			}
			/*Check how many presses are available to make sure they don't press more spots than allowed*/
			else if(buttonsPress != 0){
				b1.setStyle(selected[colorPreset] + buttonSize);
				betStrings.add(buttonNum);
				buttonsPress--;
			}

			/*Display the spots left as the player presses*/
			gambleButton.setText("Number of Spots: " + buttonsPress);
		};

		/* Handler for the number of spots to choose */
		spotHandler = e -> {

			/* Get text from button and save to button pressed as an integer*/
			String buttonNum = ((Button)e.getSource()).getText();
			buttonsPress = Integer.parseInt(buttonNum.trim());

			/* Save the spot number and set spotSelected to true*/
			spotNumber = buttonsPress;
			spotSelected = true;
		};

		/*Handler for the gamble button*/
		gambleHandler = e -> {
			if(buttonsPress > 0 && drawingPressed > 0){
				grid.setMouseTransparent(false);
				toggleGrid(spotGrid, true);
				Button b1 = (Button)e.getSource();
				b1.setStyle(selected[colorPreset] + largeButtonSize);
				b1.setText("Number of Spots: " + buttonsPress);
				gamblePressed = true;
				startButton.setDisable(false);
				drawings.setDisable(true);
			}
		};

		/* Handler for the next draw in the game */
		resetHandler = e -> {

			/* Game has not started or animation has not finished*/
			if(gameStarted == false || iterator != 20){
				return;
			}

			/* Reached the last draw and animation, reset to new game */
			if(drawingPressed == 1 && iterator == 20){

				drawingPressed = saveDrawNumber + 1;
				resetButton.setText("Next Draw");
				toggleGrid(spotGrid, false);
				grid.setMouseTransparent(true);
				resetGameGrid(grid);
				gambleButton.setText("Gamble!");
				gambleButton.setStyle(selected[colorPreset] + medButtonSize);
				gamblePressed = false;
				spotSelected = false;
				randomButton.setDisable(false);
				drawings.setDisable(false);
			}

			/*Goes through the entire grid and change the colors selected by computer*/
			for(Node child: grid.getChildren()){

				final Button button = (Button) child;

					/*Change blue squares back to default squares*/
					if(child.getStyle().equals(computer[colorPreset] + buttonSize)){
						child.setStyle(defaults[colorPreset]+ buttonSize);
					}
					/*Change matching squares back to selected squares*/
					if(child.getStyle().equals(winnings[colorPreset] + buttonSize)){
						child.setStyle(selected[colorPreset] + buttonSize);
					}
				}

			/*Variables are reset every time*/
			startButton.setDisable(false);
			scoreButton.setText("Click to Reveal Score!");
			gameStarted = false;
			drawingsSelected.clear();
			addToDrawingsSelected();
			drawingPressed--;
			buttonsPress = 0;
			matchingPress = 0;
			iterator = 0;
		};

		/*Handler for the start button*/
		startHandler = e -> {

			/*Check If the user pressed the necessary things*/
			if(drawingPressed == 0){
				return;
			}
			if(!gamblePressed || buttonsPress != 0 || !spotSelected){
				return;
			}
			if(drawingPressed == 1){
				resetButton.setText("New Game");
			}

			/* Game has started, disable buttons */
			gameStarted = true;
			startButton.setDisable(true);
			randomButton.setDisable(true);
			grid.setMouseTransparent(true);


			/*Goes through the entire grid and change the colors selected by computer*/
			for(Node child: grid.getChildren()){

				final Button button = (Button) child;

				if(Integer.parseInt(button.getText().trim()) == drawingsSelected.get(iterator)){
					/*If the computer chose the button and player did not select*/
					if(child.getStyle().equals(defaults[colorPreset] + buttonSize)){
						child.setStyle(computer[colorPreset]+ buttonSize);
					}
					/*If the computer chose the button and player selected*/
					if(child.getStyle().equals(selected[colorPreset] + buttonSize)){
						child.setStyle(winnings[colorPreset] + buttonSize);
						matchingPress++;
					}
				}
			}

			/* Increment iterator and calculate money
			when the animation is over for the round */
			iterator++;
			if(iterator == 20)
			{
				calcTotalMoney();
			}
		};

		/*Handler for the Score Button*/
		scoreHandler = e->{

			/*Get the button*/
			Button b1 = (Button)e.getSource();

			if(gameStarted && spotSelected && gamblePressed && (buttonsPress == 0)) {
				/*Display final score*/
				if(drawingPressed == 1)
				{
					// Display final matches and money earned
					b1.setText("Congrats you've won $" + moneyEarned + "!");
				}
				/*Display number of matches for that draw*/
				else
				{
					b1.setText("Matching Score: " + matchingPress);
				}

			}
		};

		/*Handler for the random button*/
		randomHandler = e->{

			/*Initialize objects*/
			randomNums = new Random();
			randomSelected = new ArrayList<>();

			/*Initialize buttonsPress*/
			buttonsPress = spotNumber;

			if(drawingPressed == 0 || !gamblePressed || !spotSelected){
				return;
			}

			/*Fills the randoms array list with values*/
			while(buttonsPress > 0){
				int temp = randomNums.nextInt(80) + 1;
				if(!randomSelected.contains(temp)){
					randomSelected.add(temp);
					buttonsPress--;
				}
			}

			/*Presses random buttons on the grid*/
			for(Node child: grid.getChildren()){
				final Button button = (Button) child;
				if(child.getStyle().equals(selected[colorPreset] + buttonSize)){
					child.setStyle(defaults[colorPreset] + buttonSize);
					gambleButton.setText("Number of Spots: " + buttonsPress);
				}
				if(randomSelected.contains(Integer.parseInt(button.getText().trim()))){
					child.setStyle(selected[colorPreset] + buttonSize);
					gambleButton.setText("Number of Spots: " + buttonsPress);
				}
			}
		};

		/*Handler for the drawing button*/
		drawingsHandler = e-> {

			/*Gets the draw number value and saves it*/
			switch(drawings.getValue()){
				case 1:
					drawingPressed = 1;
					saveDrawNumber = drawingPressed;
					break;

				case 2:
					drawingPressed = 2;
					saveDrawNumber = drawingPressed;
					break;

				case 3:
					drawingPressed = 3;
					saveDrawNumber = drawingPressed;
					break;

				case 4:
					drawingPressed = 4;
					saveDrawNumber = drawingPressed;
					break;

				default:
					drawingPressed = 0;
					break;
			};
		};

		/*This makes the return button go to the game scene(Temporary)*/
		returnButton.setOnAction(e->primaryStage.setScene(createGameScene()));

		/*Sets the game scene immediately (Temporary)*/
		primaryStage.setScene(createWelcomeScene()); //set the scene in the stage
		primaryStage.show(); //make visible to the user
	}


	/*Completely change the entire board to Enabled/Disabled*/
	public void toggleGrid(GridPane grid, boolean booleanToggle) {
		for(Node child: grid.getChildren()){
			child.setDisable(booleanToggle);
		}
	}


	/*Completely reset the Game Grid*/
	public void resetGameGrid(GridPane grid){
		for(Node child: grid.getChildren()){
			child.setStyle(defaults[colorPreset] + buttonSize);
		}
	}


	/*Populates the grid with bet card numbers*/
	public void addBetCard(GridPane grid) {

		for(int x = 0; x<8; x++) {
			for(int i = 0; i<10; i++) {
				Button bets = new Button(Integer.toString(1+i+x*10));
				bets.setStyle(defaults[colorPreset] + buttonSize);
				bets.setOnAction(betHandler);
				grid.add(bets, i, x);
			}
		}
	}


	/*Populates the spot grid with spot numbers*/
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


	/*Set the text/style and align it*/
	public void textSettings(Text tempText, String stringText){
		tempText.setText(stringText);
		tempText.setTextAlignment(TextAlignment.CENTER);
		tempText.setFont(Font.font(20.0));
	}

}
