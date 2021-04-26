package prosjekt_package;

import java.io.FileNotFoundException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MineSweeperController {
	
	MineSweeper game;
	
	private MineSweeperManager mineSweeperManager;
	
	@FXML Pane board;
	private int height = 10;
	private int width = 10;
	int bombsFlagged = 0;
	
	@FXML AnchorPane aPane;
	
	//private Button startButton;
	@FXML private Button reset;
	@FXML private Button easy; 
	@FXML private Button normal;
	@FXML private Button hard;
	
	@FXML private Text flags;
	@FXML private Text mines;
	
	@FXML int buttonClick = 0;
	@FXML int flagCount = 0;
	
	@FXML long start; //tid
	@FXML long finish; //ferdigtid
	
	@FXML
    TextField filename;
    
    @FXML
    Text fileNotFoundMessage;
    @FXML
    Text gameOverText;
	
	public void initialize() {
		this.buttonClick = 0; 
		this.bombsFlagged = 0;
		game = new MineSweeper(width, height);
		game.setBombs();
		game.getNeighbourTiles();
		
		this.flagCount = (game.getHeight() * game.getWidth())/10;
		
		createBoard();
		
		mineSweeperManager = new MineSweeperManager(game); 
	}
	private void createBoard() {
		board.getChildren().clear();
		gameOverText.setVisible(false);
		
		for (int y = 0; y < game.getHeight(); y++) { //bygge brett gjenom for-løkker
			for (int x = 0; x < game.getWidth(); x++) {
				int id = x + y * game.getWidth();
				Pane pane = new Pane();
				Button button = new Button(); //så man kan trykke på rutene
				
				pane.setTranslateX(x*30); 
				pane.setTranslateY(y*30);
				pane.setPrefHeight(30);
				pane.setPrefWidth(30);
				pane.setStyle("-fx-border-color: black; -fx-border-width: 1px;"); //FARGER <3 (kant)
				pane.getChildren().add(button); //legger til en button til en pane, det er panes som flytter på seg i løkken og brettet
				
				button.setId("" + id);
				button.prefWidthProperty().bind(pane.widthProperty());
				button.prefHeightProperty().bind(pane.heightProperty());
				button.setOnMouseClicked(mouse); //til museklikk
				button.setText(null);
				button.setStyle("-fx-background-radius: 0; -fx-background-color: #03fca5; -fx-border-color: #000000;"); //FARGER <3 (tile)
				button.setDisable(false);
			
				board.getChildren().add(pane); //legger til pane, som har en button
			}
		}	
		mines.setText("MINES: " + (game.getHeight() * game.getWidth())/10); //skriver antall miner og flagg
		flags.setText("FLAGS: " + flagCount);
	}
	
	public ImageView setNewImage(String image) {
		ImageView img = new ImageView();	
		Image img1 = new Image(getClass().getResourceAsStream(image));
		img.setImage(img1);
		return img;
	} //setter inn bilder
	
	
	@FXML
	public void easy() {
		this.width = 10;
		this.height = 10; 
		aPane.setMinWidth(400);
		aPane.setMinHeight(600);
		initialize();
	}
	
	@FXML
	public void medium() {
		this.width = 16;
		this.height = 16; 
		aPane.setMinHeight(1000);
		aPane.setMinWidth(800);
		initialize();
	}
	
	@FXML
	public void hard() {
		this.width = 20;
		this.height = 20; 
		aPane.setMinHeight(1400);
		aPane.setMinWidth(1200); //vinduet blir for lite, med mindre du har 27" skjerm
		initialize();
	}
	
	
	private String getFilename() {
    	String filename = this.filename.getText();
    	if (filename.isEmpty()) {
    		filename = "save_file";
    	}
    	return filename;
    }
	
	
	@FXML
	public void saveGame() throws FileNotFoundException {
		try {
    		mineSweeperManager.writeToFile(getFilename(), game);
    		fileNotFoundMessage.setVisible(false);
    	} catch (FileNotFoundException e) {
    		fileNotFoundMessage.setVisible(true); 
    	}
	}
	
	@FXML
	public void loadGame() {
		try {
			game = mineSweeperManager.readFromFile(getFilename());
    		fileNotFoundMessage.setVisible(false);
    	} catch (FileNotFoundException e) {
    		fileNotFoundMessage.setVisible(true); 
		}
		
		this.bombsFlagged = 0;
		this.buttonClick = 0; 
		this.flagCount = (game.getHeight() * game.getWidth())/10;
    	createBoard();
    	
    	for (int y = 0; y < game.getHeight(); y++) { //åpner knappene som er åpnet basert på info fra fil
			for (int x = 0; x < game.getWidth(); x++) {
				int id = x + y * game.getWidth();
				Button button = (Button) aPane.lookup("#" + id);
				
				if(!game.isTile(x, y)) {
					x++;
				}
				else if(game.getTile(x, y).getIsFlagged()) {
					Image flag_icon = new Image(getClass().getResourceAsStream("flag_icon.png"), 20, 20, false, false);
					button.setOpacity(1);
					button.setGraphic(new ImageView(flag_icon));
					useFlags();
					game.getTile(x, y).setIsFlagged(true);
					flags.setText("FLAGS: " + flagCount);
					
					if(game.getTile(x,y).isBomb() && game.getTile(x, y).getIsFlagged()==true) {
						bombsFlagged ++;	
					}
				}
				else if(game.getTile(x, y).getIsOpen() && game.getTile(x, y).getNeighbourBombs() != 0){
					String num = "" + game.getTile(x, y).getNeighbourBombs();
					button.setText(num);
					Font font = Font.font("Courier New", FontWeight.BOLD, 12);
					button.setFont(font);
					button.setDisable(true);
					buttonClick();
				}
				else if(game.getTile(x, y).getIsOpen()) {
					button.setDisable(true);
					buttonClick();
				} 	
			}
		}
	}
	
	private void buttonClick() {
		this.buttonClick += 1;
		if (buttonClick == 1) {
			long start = System.currentTimeMillis(); //starter tid
			this.start = start;
		}
	}
	
	private int getButtonClick() {
		return buttonClick;
	}
	
	private void useFlags() {
		if(this.flagCount >= 0) {
			this.flagCount -= 1;
		}
	}
	
	private void addFlags() {
		this.flagCount += 1;
	}
	
	private int getFlags() {
		return flagCount;
	}
	

	public Pane getBoard() {
		return board;
	}
	
	public void showBombs() {
		for (int y = 0; y < game.getHeight(); y++) { //finner minene og setter visible
			for (int x = 0; x < game.getWidth(); x++) {
				int id =  x + y * game.getWidth();
				Button button = (Button) aPane.lookup("#" + id); 
				
//				if(game.getTile(x, y).getIsFlagged() == true && game.getTile(x, y).isBomb() == true) {
//					
//				}
				
				if(game.getTile(x, y).isBomb() == true) {
					Image bomb = new Image(getClass().getResourceAsStream("mine_icon2.png"), 20, 20, false, false);
					button.setDisable(true);
					button.setOpacity(0.5);
					button.setGraphic(new ImageView(bomb));	
				}
			}
		}
	}
	public void checkOpenTiles(Tile tile) { //åpner de tomme tilene på brettet
		
		for (int y = 0; y < game.getHeight(); y++) { 
			for (int x = 0; x < game.getWidth(); x++) {
				int id = x + y * game.getWidth();
				Button button = (Button) aPane.lookup("#" + id);
				
				if(game.getTile(x, y).getNeighbourBombs() != 0 && game.getTile(x, y).getIsOpen()==true) {
					buttonClick();
					String num = "" + game.getTile(x, y).getNeighbourBombs();
					button.setDisable(true);
					button.setText(num);
					Font font = Font.font("Courier New", FontWeight.BOLD, 12);
					button.setFont(font);
				}
				else if(game.getTile(x, y).getIsOpen()==true) {
					buttonClick();
					button.setDisable(true);
					button.setOpacity(0.4);
				}
			}
		}
	}
	
	
	@FXML
	public void isGameOver() {
		if (game.isGameOver()) {
			showBombs();
			gameOverText.setVisible(true);
			
			for (int y = 0; y < game.getHeight(); y++) { //disabler alle knappene til slutt :)
				for (int x = 0; x < game.getWidth(); x++) {
					int id = x + y * game.getWidth();
					Button button = (Button) aPane.lookup("#" + id);
					button.setDisable(true);
					button.setOpacity(0.4);
				}
			}
		}
	}
	
	@FXML
	public void isGameWon() {
		if(game.isGameWon()) {
			long finish = System.currentTimeMillis();
			this.finish = finish;
			float finalTime = (this.finish - this.start)/1000;
			
			Text gameWonText = new Text();
			HBox textBox = new HBox();
			textBox.setAlignment(Pos.CENTER);
			gameWonText.setText("GAME WON \n Time: " + finalTime + "sek");
			
			gameWonText.prefHeight(100); 
			gameWonText.prefWidth(300);
			gameWonText.setTextAlignment(TextAlignment.CENTER);
			gameWonText.setStyle("-fx-font-size: 20;");
			board.getChildren().clear();
			board.getChildren().add(textBox);
			textBox.getChildren().add(gameWonText);
		}
	}
	EventHandler<MouseEvent> mouse = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent arg0) {  
			MouseButton mouseButton = arg0.getButton();
 			Button button = (Button)  arg0.getSource();
			int x = 0; 
			int y = 0; 
			int id = Integer.parseInt(button.getId());
			
			if (mouseButton == MouseButton.PRIMARY) { //venstreklikking
				for (int i = 0; i < game.getHeight(); i ++) {
					for (int j = 0; j < game.getWidth(); j++) {
						if (i * game.getWidth() + j == id) {
							y = i; 
							x = j; 
							break;
						}
					}
				}
				
				if(game.getTile(x, y).getIsFlagged()) { //sjekker om det er flagg, gjør ingenting hvis du høyreklikker på flagg
					return;
				}
				
				else if(game.isBomb(game.getTile(x, y))) {
					Image bomb = new Image(getClass().getResourceAsStream("mine_icon2.png"), 20, 20, false, false);
					button.setStyle("-fx-background-radius: 0; -fx-background-color: #FF0000; -fx-border-color: #000000;");
					button.setDisable(true);
					button.setOpacity(0.5);
					button.setGraphic(new ImageView(bomb));
					game.setGameOver();
					
				} else if (game.getTile(x, y).getNeighbourBombs() == 0) {
					button.setDisable(true);
					button.setText(null);
					buttonClick();
					
					game.openEmptyTiles(game.getTile(x,y));
					checkOpenTiles(game.getTile(x,y));
					
				} else {
					button.setDisable(true);
					String num = "" + game.getTile(x, y).getNeighbourBombs();
					button.setText(num);
					Font font = Font.font("Courier New", FontWeight.BOLD, 12);
					button.setFont(font);
					buttonClick();
					game.getTile(x, y).setIsOpen();
					button.setOpacity(0.4);
				}
				if (bombsFlagged == game.getBombNumber()) { //funket før, ikke nå
					game.setGameWon();
				}
				isGameOver();
				isGameWon();
				
			} else if (mouseButton == MouseButton.SECONDARY) { 
				for (int i = 0; i < game.getHeight(); i ++) {
					for (int j = 0; j < game.getWidth(); j++) {
						if (i * game.getWidth() + j == id) {
							y = i; 
							x = j; 
							break;
						}
					}
				}

				if (game.getTile(x,y).getIsFlagged() == true) { //fjerner flagg 
					button.setOpacity(1); //høyreklikking ^
					button.setText(null);
					button.setGraphic(null);
					addFlags();
					flags.setText("FLAGS: " + flagCount);
					game.getTile(x, y).setIsFlagged(false);
					
					if(game.getTile(x,y).isBomb()){
						bombsFlagged --;	
					}
					
				} else { 
					if (getFlags() > 0) { //flagger hvis du har igjen
						Image flag_icon = new Image(getClass().getResourceAsStream("flag_icon.png"), 20, 20, false, false);
						button.setOpacity(1);
						button.setGraphic(new ImageView(flag_icon));
						useFlags();
						flags.setText("FLAGS: " + flagCount);
						game.getTile(x, y).setIsFlagged(true);
						
						if(game.getTile(x,y).isBomb() && game.getTile(x, y).getIsFlagged()==true) {
							bombsFlagged ++;	
						}
					}
				}
			}
		}
	}; //denne må være her

}
