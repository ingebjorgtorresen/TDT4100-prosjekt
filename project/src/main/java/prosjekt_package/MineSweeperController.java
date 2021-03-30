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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MineSweeperController {
	
	MineSweeper game;
	
	@FXML Pane board;
	private int height = 9;
	private int width = 9;
	
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
	
	@FXML
    TextField filename;
    
    @FXML
    Text fileNotFoundMessage;
	
	
	public void initialize() {
		
		this.buttonClick = 0; 
		game = new MineSweeper(width, height);
		game.setBombs();
		game.getNeighbourTiles();
		
		
		this.flagCount = (game.getHeight() * game.getWidth())/10;
		
		createBoard();
		drawBoard();
	}
	private void createBoard() {
		board.getChildren().clear();
		
		for (int y = 0; y < game.getHeight(); y++) { //bygge brett gjenom for-løkker
			for (int x = 0; x < game.getWidth(); x++) {
				int id = x + y * game.getWidth();
				Pane pane = new Pane();
				Button button = new Button(); //så man kan trykke på rutene
				
				pane.setTranslateX(x*30); //magnus kode
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
	
	/*public ImageView setNewImage(String image) {
		ImageView img = new ImageView();	
		Image img1 = new Image(getClass().getResourceAsStream(image));
		img.setImage(img1);
		return img;
	}*/
	
	private void drawBoard() {
		System.out.println(aPane.getHeight());
		System.out.println(aPane.getWidth());
		
		/*for (int y = 0; y < game.getHeight(); y++) {
			for (int x = 0; x < game.getWidth(); x++) {
				board.getChildren().get(y*game.getWidth() + x).setStyle("-fx-background-color: "+ getTileColor(game.getTile(x,  y)) + ";");	
			}
		}*/
	}
	
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
		aPane.setMinWidth(1200); //selv om jeg har prøvd å endre på verdier her, endres ikke vinduet, må evt fikses i SB
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
	public void saveGame() {
		try {
    		MineSweeperManager.writeToFile(getFilename(), game);
    		fileNotFoundMessage.setVisible(false);
    	} catch (FileNotFoundException e) {
    		fileNotFoundMessage.setVisible(true);
    	}
	}
	
	@FXML
	public void loadGame() {
		try {
			game = MineSweeperManager.readFromFile(getFilename());
    		fileNotFoundMessage.setVisible(false);
    	} catch (FileNotFoundException e) {
    		fileNotFoundMessage.setVisible(true);
		}
    	createBoard();
    	drawBoard();
	}
	
	private void buttonClick() {
		this.buttonClick += 1;
	}
	
	private int getButtonClick() {
		return buttonClick;
	}
	
	private void useFlags() {
		this.flagCount -= 1;
	}
	
	private void addFlags() {
		this.flagCount += 1;
	}
	
	private int getFlags() {
		return flagCount;
	}
	
	@FXML
	public void isGameOver() {
		if (game.isGameOver()) {
			Text gameOverText = new Text();
			HBox textBox = new HBox();
			gameOverText.setText("GAME OVER");
			gameOverText.setStyle("-fx-font-size: 20;");
			textBox.setAlignment(Pos.CENTER);
			board.getChildren().clear();
			board.getChildren().add(textBox);
			textBox.getChildren().add(gameOverText);
		}
	}
	
	@FXML
	public void isGameWon() {
		if(game.isGameWon()) {
			Text gameWonText = new Text();
			HBox textBox = new HBox();
			textBox.setAlignment(Pos.CENTER);
			gameWonText.setText("YOU WON");
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
			if (mouseButton == MouseButton.PRIMARY) {
				for (int i = 0; i < game.getHeight(); i ++) {
					for (int j = 0; j < game.getWidth(); j++) {
						if (i * game.getWidth() + j == id) {
							y = i; 
							x = j; 
							break;
						}
					}
				}
				if(game.isBomb(game.getTile(x, y))) {
					button.setDisable(true);
					button.setOpacity(0.65);
					button.setText("o");
					game.setGameOver();
				} else if (game.getTile(x, y).getNeighbourBombs() == 0) {
					button.setDisable(true);
					button.setText(null);
					button.setOpacity(0.50);
					buttonClick();
				} else {
					button.setDisable(true);
					String num = "" + game.getTile(x, y).getNeighbourBombs();
					button.setText(num);
					button.setOpacity(1);
					buttonClick();
				}
				if (getButtonClick() == game.getWidth() * game.getHeight() - (game.getHeight() * game.getWidth())/10) {
					game.setGameWon();
				}
				isGameOver();
				isGameWon();
			} else if (mouseButton == MouseButton.SECONDARY) {
				if (!(button.getText() == null)) {
					button.setText(null);
					addFlags();
					flags.setText("FLAGS: " + flagCount);
				} else { 
					if (getFlags() > 0) {
						button.setText("F");
						useFlags();	
						flags.setText("FLAGS: " + flagCount);
					}
				}
			}
		}
	};
	
}
