
package rpsls;

import java.io.*;
import java.util.Scanner;
import java.awt.*; //needed for graphics
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.*; //needed for graphics
import static javax.swing.JFrame.EXIT_ON_CLOSE; //needed for graphics

public class RPSLS extends JFrame {

    //FIELDS
    int numGenerations = 500;
    int currGeneration = 1;
    
    //Colours of the possible objects
    Color RockColor = Color.BLACK;
    Color PaperColor = Color.WHITE;
    Color ScissorColor = Color.GRAY;
    Color LizardColor = Color.GREEN;
    Color SpockColor = Color.BLUE;

    int width = 800; //width of the window in pixels
    int height = 800;


    int numCellsX = 80; //width of the grid (in cells)
    int numCellsY = 80;

    //create arrays
    String type[][] = new String [numCellsX][numCellsY];
    String typeNext[][] = new String [numCellsX][numCellsY];
    
    //find out size of cells
    int cellWidth = (width)/numCellsX;
    
    String Object[] = {"rock", "paper", "scissors", "lizard", "spock"};

    
    //METHODS
    public void plantFirstGeneration() throws IOException {
        
        //Randomly Fill Arrays
        for (int i = 0; i < numCellsX; i ++){
            
            for (int j = 0; j < numCellsY; j ++){
                
                type[i][j] = getRandomType();
            }
        }
    }

    
    public String getRandomType(){
        //Picks a random object
        Random r = new Random();
        int randomObjectPicker = r.nextInt(5);
        return Object[randomObjectPicker];     
    }
    
    public String[] Enemies(String a){
        //Store each object's enemies and return the desired one
        String RockEnemies[] = {"paper","spock"};
        String PaperEnemies[] = {"scissors","lizard"};
        String ScissorsEnemies[] = {"rock","spock"};
        String LizardEnemies[] = {"scissors","rock"};
        String SpockEnemies[] = {"paper","lizard"};
        
        if (a == "rock"){
            return RockEnemies;
        }
        else if (a == "paper"){
            return PaperEnemies;
        }
        else if (a == "scissors"){
            return ScissorsEnemies;
        }
        else if (a == "lizard"){
            return LizardEnemies;
        }
        else{
            return SpockEnemies;
        }
    }
    
    public String PickOne(String a){
        //Of the current objects enemies, randomly pick one
        Random r = new Random();      
        int randomPicker = r.nextInt(2);
        String[] Options = Enemies(a);
        
        return Options[randomPicker];
    }

    public void computeNextGeneration() {
        
        int enemy1 = 0;
        int enemy2 = 0;
        
        for (int i = 0; i < numCellsX; i++){
            for (int j = 0; j < numCellsY; j ++){
                //Store the enemies of the current object in CurrEnemy
                String [] CurrEnemy = Enemies(type[i][j]);
                
                //count how many enemyneighbors there are for each enemy of the current object
                enemy1 = countScaryNeighbors(i , j, type[i][j], 0);
                enemy2 = countScaryNeighbors(i , j, type[i][j], 1);
                
                //if there is a tie, and the current object is defeated, pick one of the enemies randomly
                if (enemy1 == enemy2 && enemy1 >=3){
                     typeNext[i][j] = PickOne(type[i][j]);                       
                    }
                
                //if there isn't a tie and the first enemy can defeat the current object, make sure it is more dominant than the other enemy
                else if (enemy1 >= 3 && enemy1 > enemy2){
                    typeNext[i][j] = CurrEnemy[0];
                }
                
                //if there isn't a tie and it is more dominant that enemy1
                else if(enemy2 >= 3){
                    typeNext[i][j] = CurrEnemy[1];
                }
                
                //if not defeated, don't change
                else{
                    typeNext[i][j] = type[i][j];
                }
                
                    }
                }
            }
        
            

    
    //Overwrites the current generation's 2-D array with the values from the next generation's 2-D array
    public void plantNextGeneration() {
        for (int i = 0; i < numCellsX; i ++){
            for (int j = 0; j < numCellsY; j++ ){
                type[i][j] = typeNext[i][j];
            }
        }
    }


    
    //Counts the number of enemies adjacent to cell (i, j)
    //k is the current object type and g is which enemy we want to count
    public int countScaryNeighbors(int i, int j, String k, int g) {
        int horizontal = 0;
        int vertical = 0;
        int count = 0;
        
        //gets the enemies of the current object
        String[] CurrEnemy = Enemies(k);
        
        for (int a = -1; a < 2; a++){
            for (int b = -1; b < 2; b++){
                //horizontal and vertical places we want to check around the current place
                horizontal = a + i;
                vertical = b + j;
                
                //checks that it is not an edge
                if (horizontal >= 0 && horizontal < (numCellsX-1) && vertical >= 0 && vertical < (numCellsY - 1)){
                    //if it is the enemy increase the counter
                    if (type[horizontal][vertical] == CurrEnemy[g]){
                        count++;

                    }
                }
            }
        }
        return count;                  
    }


    public static void sleep(int duration) {//makes the program slow down
        try {
            Thread.sleep(duration);
        } 
        catch (Exception e) {}
    }


    
    //Draws the current generation on the screen
    public void paint( Graphics g ) {
        
        Image img = createImage();
        g.drawImage(img,0,20,this);
        
    }
    
    public Image createImage(){
        
        BufferedImage bufferedImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        int x, y, i, j;
        
        x = 0;
        y = 0;

        //paint the cells the right color
        for (i = 0; i < numCellsX; i++) {
            
            for (j = 0; j < numCellsY; j++) {
                if (type[i][j] == "paper") {
                    g.setColor(PaperColor);
                }
                else if (type[i][j] == "rock") {
                    g.setColor(RockColor);
                }
                else if(type[i][j] == "scissors"){
                    g.setColor(ScissorColor);
                }
                else if(type[i][j] == "lizard"){
                    g.setColor(LizardColor);
                }
                else{
                    g.setColor(SpockColor);
                }
            
            g.fillRect(x, y, cellWidth, cellWidth);

            x += cellWidth;

            }
            x = 0;           
            y += cellWidth;
        }
        return bufferedImage;
    }


    //Sets up the JFrame screen
    public void initializeWindow() {
        setTitle("Game of Life Simulator");
        setSize(height, width);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.black);
        setVisible(true); //calls paint() for the first time
    }
    
    
    //Main algorithm
    public static void main(String args[]) throws IOException {

        RPSLS currGame = new RPSLS();

        
        currGame.plantFirstGeneration(); //Sets the initial generation
        currGame.initializeWindow();

        for (int i = 1; i <= currGame.numGenerations; i++) {
            currGame.computeNextGeneration();
            currGame.plantNextGeneration();
            currGame.sleep(100);//slow down
            currGame.repaint();
        }
        
    } 
    
} //end of class
