
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *This is the base for the application
 * Loads images
 * Pauses, starts, ends game
 * Checks collision of objects
 * @author Natalia Garcia and Any Landeros
 */
public class Board extends JPanel implements Runnable, Commons {
        
        private Dimension dimDimension;         //dimension of window
        private ArrayList arrAliens;                    //list of aliens to print matrix of aliens
        private Player playPlayer;                      //the player
        private Shot shShot;                            //what the aliens shot at the player
        private Alien alienDying;                       //when an alien is dying
        
        private int ialienX = 150;                      //the position of the alien in the x axis
        private int ialienY = 5;                           //the position of the alien in the y axis
        private int idirection = -1;                     //the direction of the alien
        private int ideaths = 0;                        //how many alien deaths
        private int iTime;                                  //times the game runs
        private int iPuntos;
        
        private boolean ingame = true;                                          //if the game is still on
        private final String strExplosion = "explosion.png";             //name of image when someone dies
        private final String strAlienPix = "alien.png";                      //name of alien image
        private String strMessage = "Game Over";                        //message when game is over
        private String nombreArchivo;                                           //name of scores file
        private String[] arr;                                                           //Arreglo del archivo divido.
        
        private Thread thrAnimator;                  //the thread of the game
        
        private Image imgFondo;                      //background image
        private Image imgInstr;                        //instruction image
        private Image imgPause;                        //instruction image
        private Image imgGameOver;              //gameover image
        private Image imgAutor;                      //author image
        private ImageIcon imgAlien;                      //alien image
        
        private boolean bPausa;                      //when the game is paused
        private boolean bInstr;                        //when the instructions are on
        private boolean bRecAutor;                 //when the authors are on
        private boolean bGameOver;               //when it's gameover
        private boolean bCountTime;              //when a time counter is needed
        
        private AudioClip adcLaser;             //sound when alien or player is shot
        
        /**
         * Constructor
         * calls the method needed to initialize variables
         * @see Board()
         */
        public Board()
        {
                addKeyListener(new TAdapter()); //key listener to know what key is pressed or released
                setFocusable(true);                     //sets focusable
                dimDimension = new Dimension(iBOARD_WIDTH, iBOARD_HEIGTH);      //sets board dimensions
                setBackground(Color.black);             //sets background color
                gameInit();                                       //calls initialization method
                setDoubleBuffered(true);                  //sets double buffer
        }
        
        /**
         * Makes this Component displayable by connecting it to a native screen resource
         * This method is called internally by the toolkit
         * @see isDisplayable(), removeNotify()
         */
        public void addNotify() {
                super.addNotify();
                gameInit();
        }
        
        /**
         * This method initializes the variables needed in this class
         * @see gameInit()
         */
        public void gameInit() {
                nombreArchivo = "Puntaje.txt";                  //the name of the file with past game is Puntaje
                arrAliens = new ArrayList();                        //array of aliens
                alienDying = new Alien(0, 0);                    //alien when dying
                
                //loading images
                imgAlien = new ImageIcon(this.getClass().getResource(strAlienPix));
                imgFondo = Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource ("background.jpg"));
                imgInstr = Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource ("instrucciones.jpg"));
                imgPause = Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource ("pause.jpg"));
                imgAutor = Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource ("autor.jpg"));
                imgGameOver = Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource ("gameover.jpg"));
                
                //creates matrix of aliens
                for (int ii=0; ii < 3; ii++) {
                        for (int ij=0; ij < 6; ij++) {
                                Alien alien = new Alien(ialienX + 100*ij, ialienY + 100*ii);
                                alien.setImage(imgAlien.getImage());
                                arrAliens.add(alien);
                        }
                }
                
                //initiates time
                iTime = 0;
                
                //initiates player and shot
                playPlayer = new Player();
                shShot = new Shot();
                
                //creates thread
                if (thrAnimator == null || !ingame) {
                        thrAnimator = new Thread(this);
                        thrAnimator.start();
                }
                
                //initiates booleans to false
                bPausa = false;
                bInstr = false;
                bRecAutor = false;
                bGameOver = false;
                bCountTime = false;
                
                URL urlLaser = this.getClass().getResource("laser.wav");
                adcLaser = Applet.newAudioClip(urlLaser);
        }
        /**
         * Re initializes the array of aliens, as well as the shots and bombs
         * @param fileIn
         * @throws IOException
         */
        public void initArchivo(BufferedReader fileIn) throws IOException {
                //dato will be used to read each line of file
                String dato;
                
                //position of sprite
                int iPosX, iPosY;
                
                //visibility of sprite
                boolean bVisible;
                
                //read past game's alien info
                for (int iI = 0; iI < (iNUMBER_OF_ALIENS_TO_DESTROY) ; iI ++) {
                        //first line of alien info has visibility of the alien
                        dato = fileIn.readLine();
                        bVisible = Boolean.parseBoolean(dato);
                        
                        //second line of alien info has position on x axis
                        dato = fileIn.readLine();
                        iPosX = Integer.parseInt(dato);
                        
                        //third line of alien info has position on y axis
                        dato = fileIn.readLine();
                        iPosY = Integer.parseInt(dato);
                        
                        //create new alien with the information that was read
                        Alien alien = new Alien(iPosX, iPosY);
                        
                        //set alien image
                        alien.setImage(imgAlien.getImage());
                        
                        //add alien to matrix/list
                        arrAliens.add(alien);
                        
                        //set alien visibility
                        alien.setVisible(bVisible);
                }
                
                //next lines are shot coordinates  if the player had fired a shot
                dato = fileIn.readLine();
                shShot.setX(Integer.parseInt(dato));
                
                dato = fileIn.readLine();
                shShot.setY(Integer.parseInt(dato));
                
                //we need to go through the alien list to read bomb information
                Iterator itAlien = arrAliens.iterator();
                
                while (itAlien.hasNext()) {
                        Alien alien = (Alien) itAlien.next();
                        
                        //read bomb visibility
                        dato = fileIn.readLine();
                        bVisible = Boolean.parseBoolean(dato);
                        alien.getBomb().setVisible(bVisible);
                        
                        //read bomb position on x axis
                        dato = fileIn.readLine();
                        alien.getBomb().setX(Integer.parseInt(dato));
                        
                        //read bomb position on y axis
                        dato = fileIn.readLine();
                        alien.getBomb().setY(Integer.parseInt(dato));
                }
        }
        /**
         * Reads the file where the previous game was loaded
         * @throws IOException
         */
        public void leeArchivo() throws IOException {
                //let's not crash the program while trying to read the file
                BufferedReader fileIn;
                try {
                        fileIn = new BufferedReader(new FileReader(nombreArchivo));
                        
                } catch (FileNotFoundException e){
                        File puntos = new File(nombreArchivo);
                        try (PrintWriter fileOut = new PrintWriter(puntos)) {
                                fileOut.println("100,demo");
                                fileOut.close();
                        }
                        fileIn = new BufferedReader(new FileReader(nombreArchivo));
                }
                
                //dato will be used to read each line of the file
                String dato = fileIn.readLine();
                
                //first line is iTime
                iTime = Integer.parseInt(dato);
                dato = fileIn.readLine();
                
                bCountTime = Boolean.parseBoolean(dato);
                dato = fileIn.readLine();
                
                ideaths = Integer.parseInt(dato);
                dato = fileIn.readLine();
                
                playPlayer.setX(Integer.parseInt(dato));
                
                arrAliens.clear();
                
                initArchivo(fileIn);
                
                fileIn.close();
        }
        
        /**
         * Saves game on file
         * @throws IOException
         */
        public void grabaArchivo() throws IOException {
                PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
                
                fileOut.println(Integer.toString(iTime));
                fileOut.println(Boolean.toString(bCountTime));
                fileOut.println(Integer.toString(ideaths));
                fileOut.println(Integer.toString(playPlayer.getX()));
                
                Iterator itAlien = arrAliens.iterator();
                
                while (itAlien.hasNext()) {
                        Alien alien = (Alien) itAlien.next();
                        
                        fileOut.println(Boolean.toString(alien.isVisible()));
                        fileOut.println(Integer.toString(alien.getX()));
                        fileOut.println(Integer.toString(alien.getY()));
                }
                
                fileOut.println(Integer.toString(shShot.getX()));
                fileOut.println(Integer.toString(shShot.getY()));
                
                Iterator itBomb = arrAliens.iterator();
                
                while (itBomb.hasNext()) {
                        Alien alien = (Alien) itBomb.next();
                        Alien.Bomb b = alien.getBomb();
                        
                        fileOut.println(Boolean.toString(b.isVisible()));
                        fileOut.println(Integer.toString(b.getX()));
                        fileOut.println(Integer.toString(b.getY()));
                }
                
                fileOut.close();
        }
        /**
         * Method to paint background
         * @param g
         */
        public void drawBackground(Graphics g)
        {
                if(imgFondo != null) {
                        g.drawImage (imgFondo, 0, 0, this);
                }
        }
        /**
         * Method to paint the instructions of how to play the game
         * @param g
         */
        public void drawInstr (Graphics g) {
                if(imgInstr != null) {
                        g.drawImage (imgInstr, 0, 0, this);
                }
        }
        /**
         * Method to paint the pause window
         * @param g
         */
        public void drawPause (Graphics g) {
                if(imgPause != null) {
                        g.drawImage (imgPause, 0, 0, this);
                }
        }
        /**
         * Method to paint the about the authors window
         * @param g
         */
        public void drawAutor (Graphics g) {
                if(imgAutor != null) {
                        g.drawImage (imgAutor, 0, 0, this);
                }
        }
        /**
         * Method to paint the list of aliens
         * @param g
         */
        public void drawAliens(Graphics g)
        {
                Iterator it = arrAliens.iterator();
                
                while (it.hasNext()) {
                        Alien alien = (Alien) it.next();
                        
                        if (alien.isVisible()) {
                                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
                        }
                        
                        if (alien.isDying()) {
                                alien.die();
                        }
                }
        }
        public void drawDyingAlien() {
                if(iTime < 50) {
                        ImageIcon imgExplosion =
                                new ImageIcon(getClass().getResource(strExplosion));
                        alienDying.setImage(imgExplosion.getImage());
                }
                iPuntos++;
        }
        /**
         * Method to paint the player
         * @param g
         */
        public void drawPlayer(Graphics g) {
                
                if (playPlayer.isVisible()) {
                        g.drawImage(playPlayer.getImage(), playPlayer.getX(), playPlayer.getY(), this);
                }
                
                if (playPlayer.isDying()) {
                        playPlayer.die();
                        ingame = false;
                }
        }
        /**
         * Method to paint shot if it must be painted
         * @param g
         */
        public void drawShot(Graphics g) {
                if (shShot.isVisible())
                        g.drawImage(shShot.getImage(), shShot.getX(), shShot.getY(), this);
        }
        /**
         * Method to paint the bombs that the aliens throw at the player
         * @param g
         */
        public void drawBombing(Graphics g) {
                
                Iterator i3 = arrAliens.iterator();
                
                while (i3.hasNext()) {
                        Alien a = (Alien) i3.next();
                        
                        Alien.Bomb b = a.getBomb();
                        
                        if (!b.isDestroyed()) {
                                g.drawImage(b.getImage(), b.getX() + a.iDx, b.getY() + 75, this);
                        }
                }
        }
        
        /**
         * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>,
         * heredado de la clase Container.<P>
         * En este metodo se dibuja la imagen con la posicion actualizada,
         * ademas que cuando la imagen es cargada te despliega una advertencia.
         * @param g es el <code>objeto grafico</code> usado para dibujar.
         */
        public void paint(Graphics g)
        {
                super.paint(g);
                
                g.setColor(Color.black);
                g.fillRect(0, 0, dimDimension.width, dimDimension.height);
                g.setColor(Color.green);
                
                if (ingame) {
                        drawBackground(g);
                        g.drawLine(0, iGROUND, iBOARD_WIDTH, iGROUND);
                        drawAliens(g);
                        drawPlayer(g);
                        drawShot(g);
                        drawBombing(g);
                }
                
                if (bInstr) {
                        drawInstr(g);
                }
                
                if (bPausa) {
                        drawPause(g);
                }
                
                if (bRecAutor) {
                        drawAutor(g);
                }
                
                if (bGameOver) {
                        gameOver(g);
                }
                
                Toolkit.getDefaultToolkit().sync();
                g.dispose();
                if(bCountTime) {
                        if(iTime < 50) {
                                ImageIcon imgExplosion =
                                        new ImageIcon(getClass().getResource(strExplosion));
                                alienDying.setImage(imgExplosion.getImage());
                        }
                        if (iTime == 50) {
                                bCountTime = false;
                                alienDying.setDying(true);
                        }
                }
        }
        
        /**
         * This method paints the game over window
         * @param g
         */
        public void gameOver(Graphics g){
                if(imgGameOver != null) {
                        g.drawImage (imgGameOver, 0, 0, this);
                }
        }
        /**
         * This method is constantly checking if the game should be over
         * If there's a collision
         * @see animationCycle
         */
        public void animationCycle()  {
                
                //if the player destroys all aliens the game is over
                if (ideaths == iNUMBER_OF_ALIENS_TO_DESTROY) {
                        ingame = false;
                        strMessage = "Game won!";
                }
                
                //We need to actualize the position of playPlayer
                playPlayer.act();
                
                //We need to check what will happen when the player shots at the aliens
                if (shShot.isVisible()) {
                        Iterator it = arrAliens.iterator();
                        
                        //store the position of the shot
                        int ishShotX = shShot.getX();
                        int ishShotY = shShot.getY();
                        
                        //go through the alien list to see if any intersect with the shot
                        while (it.hasNext()) {
                                Alien alien = (Alien) it.next();
                                if (alien.isVisible() && shShot.isVisible()) {
                                        //if one intersect…
                                        if(alien.intersecta(ishShotX, ishShotY)) {
                                                adcLaser.play();
                                                //make alienDying be that alien that intersects
                                                alienDying = alien;
                                                //re initialize time to 0
                                                iTime = 0;
                                                bCountTime = true;
                                                
                                                //one alien less to go!
                                                ideaths++;
                                                //disappear the shot
                                                shShot.die();
                                        }
                                        
                                }
                        }
                        
                        //move shot
                        int iy = shShot.getY();
                        iy -= 4;
                        if (iy < 0) {
                                shShot.die();
                        }
                        else {
                                shShot.setY(iy);
                        }
                }
                
                
                //We need to actualize the aliens movement
                Iterator itAliens = arrAliens.iterator();
                
                while (itAliens.hasNext()) {
                        Alien alien = (Alien) itAliens.next();
                        int ix = alien.getX();
                        
                        //if the aliens touch a boarder they must change their direction
                        if (ix  >= iBOARD_WIDTH - iBORDER_RIGHT && idirection != -1) {
                                idirection = -1;
                                Iterator itAliensTemp = arrAliens.iterator();
                                while (itAliensTemp.hasNext()) {
                                        Alien alienTemp = (Alien) itAliensTemp.next();
                                        alienTemp.setY(alienTemp.getY() + iGO_DOWN);
                                }
                        }
                        if (ix <= iBORDER_LEFT && idirection != 1) {
                                idirection = 1;
                                
                                Iterator itAliensTemp = arrAliens.iterator();
                                while (itAliensTemp.hasNext()) {
                                        Alien alienTemp = (Alien) itAliensTemp.next();
                                        alienTemp.setY(alienTemp.getY() + iGO_DOWN);
                                }
                        }
                }
                
                
                //this next loop is to check if alien touches the ground
                Iterator it = arrAliens.iterator();
                
                while (it.hasNext()) {
                        Alien alien = (Alien) it.next();
                        if (alien.isVisible()) {
                                
                                int iy = alien.getY();
                                
                                if (iy > iGROUND - iALIEN_HEIGHT) {
                                        ingame = false;
                                        strMessage = "Invasion!";
                                }
                                
                                alien.act(idirection);
                        }
                }
                
                //this next loop checks if the aliens are attacking the user
                Iterator itBombs = arrAliens.iterator();
                Random generator = new Random();
                
                while (itBombs.hasNext()) {
                        int ishShot = generator.nextInt(15);
                        Alien alien1 = (Alien) itBombs.next();
                        Alien.Bomb bomb = alien1.getBomb();
                        
                        //reset bomb coordinates if misses target
                        if (ishShot == iCHANCE && alien1.isVisible() && bomb.isDestroyed()) {
                                bomb.setDestroyed(false);
                                bomb.setX(alien1.getX());
                                bomb.setY(alien1.getY());
                        }
                        
                        //check if bomb targets the player
                        int ibombX = bomb.getX();
                        int ibombY = bomb.getY();
                        
                        if (playPlayer.isVisible() && !bomb.isDestroyed()) {
                                if(playPlayer.intersecta(ibombX, ibombY + 125)) {
                                        adcLaser.play();
                                        ImageIcon imgExplosion =
                                                new ImageIcon(this.getClass().getResource(strExplosion));
                                        playPlayer.setImage(imgExplosion.getImage());
                                        playPlayer.setDying(true);
                                        bomb.setDestroyed(true);
                                }
                        }
                        //destroys the bomb if touches ground
                        if (!bomb.isDestroyed()) {
                                bomb.setY(bomb.getY() + 1);
                                if (bomb.getY() >= iGROUND - iBOMB_HEIGHT) {
                                        bomb.setDestroyed(true);
                                }
                        }
                }
        }
        
        /**
         * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
         * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se incrementa
         * la posicion en x, se repinta el <code>Applet</code> y luego manda a dormir
         * el hilo.
         *
         */
        public void run() {
                
                long beforeTime, timeDiff, sleep;
                
                beforeTime = System.currentTimeMillis();
                
                //while playing, play
                while (ingame) {
                        //if it's not paused, instructions view on, or authors view on, play
                        if(!bPausa && !bInstr && !bRecAutor) {
                                iTime++;
                                animationCycle();
                        }
                        //always repaint
                        repaint();
                        
                        timeDiff = System.currentTimeMillis() - beforeTime;
                        sleep = iDELAY - timeDiff;
                        
                        if (sleep < 0)
                                sleep = 2;
                        try {
                                Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                                System.out.println("interrupted");
                        }
                        beforeTime = System.currentTimeMillis();
                }
                //if not ingame, gameover
                bGameOver = true;
                //always repaint
                repaint();
        }
        
        /**
         * Class TAdapter
         * changes variables used in Board  depending on the keys the user types
         * @see TAdapter
         */
        private class TAdapter extends KeyAdapter {
                /**
                 * Does what the user wants depending on the released key
                 * @param e 
                 */
                public void keyReleased(KeyEvent e) {
                        playPlayer.keyReleased(e);
                        bPausa = playPlayer.getPausa();
                        bInstr = playPlayer.getInstr();
                        bRecAutor = playPlayer.getRecAutor();
                        
                        if  (e.getKeyCode () == KeyEvent.VK_G) {
                                try {
                                        grabaArchivo();
                                } catch (IOException ex) {
                                        Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_C) {
                                try {
                                        leeArchivo();
                                } catch (IOException ex) {
                                        Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                }
                
                /**
                 * Does what must be done while there's a key pressed
                 * @param e 
                 */
                public void keyPressed(KeyEvent e) {
                        
                        playPlayer.keyPressed(e);
                        
                        //we need to know where to send the shots from
                        int iX = playPlayer.getX();
                        int iY = playPlayer.getY();
                        
                        if (ingame)
                        {
                                //if the user presses altdown, it shoots the aliens
                                if (e.isAltDown()) {
                                        if (!shShot.isVisible())
                                                shShot = new Shot(iX, iY);
                                }
                        }
                }
        }
}
