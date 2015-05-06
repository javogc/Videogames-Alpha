package alpha;

import java.applet.AudioClip;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author Javier Guajardo, Natalia García, Gabriel Berlanga and Any Landero
 */

public final class Alpha extends JFrame implements Runnable, KeyListener {
    
    /**
     *
     * @globalVariables
     */
    private Character[] arrCtrMan; // array of class character
    
    private LinkedList<Gizmos>[] lklGizmos; // gizmos linked list
    
    private Gizmos gzmBack1; // background 1
    private Gizmos gzmBack2; // background 2
    
    private Animation aniIntro; // intro's animation
    
    private int iIntro; // intro's counter
    private int iLevel; // level counter
    private int iYTemp; // temporal Y integer
    private int iPoints; // points counter
    private int iVelocity; // velocity counter
    private int iSeconds; // seconds counter
    private int iMaxPoints;// max points integer
    
    private double dAcceleration; // acceleration counter
    
    private long actualTime; // machine actual time
    private long l_elapsedTime; // machine elapsed time
    private long l_actualTime; // actual time
    
    private boolean bPoints; // boolea to add or rest points
    private boolean bNextLevel; // boolean for level chage
    private boolean bMusica; // boolean to play music
    private boolean bJump;// boolean to jump
    private boolean bPause; // boolean to pause
    private boolean bGameOver; //game over boolean

    
    private Image imgApplet; // image to paint
    private Graphics graApplet; //graphic to paint
    
    private AudioClip auSoundTrack; // audio clip of soundtrack
    private AudioClip auPoint; //audio clip for points
    
    /**
     *
     * @constructor
     */
    Alpha() {
        init(); 
        start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * init
     *
     * Method <I>init</I> is an override method from the class <code>Applet</code>.<P>
     * In this method we initialize the variables that will be used for the
     * game to work
     */
    public void init() {
        setSize (1000, 700); // screen size
        iIntro = 0; // init intro counter
        iLevel = 0;// init level counter
        iPoints = 0;// init point counter
        iSeconds = 0; //init secods counter
        iVelocity = 10; // init velocity 
        dAcceleration = 0.2; // init acceleration
        bPoints = false; // init points bool
        bNextLevel = false; // init next level bool
        bJump = false; // init jump bool
        bPause  = false; //init pause bool 
        bGameOver = false; // init game over bool
        initImages(); 

        addKeyListener(this);
        
        
        
        //load game sounds
        
        URL URLSoundTrack = this.getClass().getResource("soundtrack.wav"); 
        
<<<<<<< HEAD
        auSoundTrack =  Applet.newAudioClip(URLSoundTrack);
          
=======
        auSoundTrack =  Applet.newAudioClip(URLSoundTrack); // 
        
        
        URL URLPoints = this.getClass().getResource("points.wav");
        auPoint = Applet.newAudioClip(URLPoints);
>>>>>>> d9d761d58e9488e7f0a5667103cae8d1c604821a
                
    }
    
    /**
     * initImages
     *
     * Method <I>initImages</I> is called by the method <I>init</I>
     */
    public void initImages() {
        //load background images to gizmos of background
        Image imgBackground = Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource ("backLevel1.jpg"));
        
        aniIntro = new Animation();
        Image imgIntro;
        
        for (int iI = 0; iI < 10; iI++) {
            imgIntro = Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource ("Logo_0000" + (iI) + ".jpg"));
            aniIntro.sumaCuadro(imgIntro, 100);
        }
        
        for (int iI = 10; iI < 60; iI++) {
            imgIntro = Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource ("Logo_000" + (iI) + ".jpg"));
            aniIntro.sumaCuadro(imgIntro, 100);
        }
        
        //load background image to gizmos
        gzmBack1 = new Gizmos(0, 0, 4868, 900, imgBackground, 0, null);
        gzmBack2 = new Gizmos(4868, 0, 4868, 900, imgBackground, 0, null);
        
        //loading images for the animation of the main character
        Image [][] matImgMan = new Image [4][8];
        Animation [] arrMan = new Animation[4];
        Image [] arrJump = new Image [4];
        
        for (int iI = 0; iI < 4; iI++) {
            arrMan[iI] = new Animation();
        }
        
        for (int iI = 0; iI < 4; iI++) {
            for (int iJ = 0; iJ < 8; iJ++) {
                matImgMan[iI][iJ] = Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource ("manLevel" + (iI+1) + "-"
                                        + (iJ+1) + ".png"));
                arrMan[iI].sumaCuadro(matImgMan[iI][iJ], 100);
            }
        }
        for (int iI = 0; iI < 4; iI++){
            arrJump[iI] = Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource ("jumpLevel" + (iI+1) + ".png"));
        }
        
        //creating the main characters for each level
        arrCtrMan = new Character [4];
        for (int iI = 0; iI < 4; iI++) {
            arrCtrMan[iI] = new Character (50, 400, 100, 100, arrMan[iI], 9, 0, arrJump[iI]);
        }
        
        //loading images of the Gizmos, these include obstacles & tokens
        Image [][] matImgGizmo = new Image[4][6];
        Path pTemp;
        boolean bPath = true;
        
        for (int iI = 0; iI < 4; iI++) {
            for (int iJ = 0; iJ < 6 && bPath; iJ++) {
                pTemp = Paths.get("gizmoLevel" + (iI+1) + "-"
                        + (iJ+1) + ".png");
                bPath = Files.exists(pTemp);
                if (!bPath) {
                    break;
                }
                else {
                    matImgGizmo[iI][iJ] = Toolkit.getDefaultToolkit().getImage
                                    (this.getClass().getResource("gizmoLevel" + (iI+1) + "-"
                                            + (iJ+1) + ".png"));
                }
            }
        }
        
        int iMinimum = 1000, iMaximum = 3000, iTemp = 0;
        int iRandNum = 0;
        
        lklGizmos = new LinkedList [4];
        Gizmos gzm1, gzm2;
        
        for (int iI = 0; iI < 4; iI++) {    
            iTemp = iRandNum = 0;
            for (int iJ = 0; iJ < 5; iJ++) {
                iRandNum = iMinimum + (int)(Math.random()*iMaximum) + iTemp;
                iTemp = iRandNum;
                if(iI == 1){
                    gzm1 = new Gizmos (iRandNum, 375, 200, 125, 
                            Toolkit.getDefaultToolkit().getImage
                          (this.getClass().getResource("gizmoLevel2-1.png")), -100, 
                            Applet.newAudioClip(this.getClass().getResource("audioLevel2-1.wav")));
                }
                else {
                gzm1 = new Gizmos (iRandNum, 375, 200, 200,
                        Toolkit.getDefaultToolkit().getImage
                                                    (this.getClass().getResource("gizmoLevel" + (iI+1) + "-1.png")), -100,    
                        Applet.newAudioClip(this.getClass().getResource("audioLevel" + (iI+1) + "-1.wav")));
                }
                
                if (lklGizmos[iI] == null) {
                    lklGizmos[iI] = new LinkedList<Gizmos>();
                }
                
                lklGizmos[iI].add(gzm1);
            }
            iTemp = iRandNum = 0;
            for (int iJ = 0; iJ < 5; iJ++) {
                iTemp = (int)lklGizmos[iI].get(iJ).getX();
                iRandNum = iMinimum + (int)(Math.random()*iMaximum) + iTemp;
                if(iI == 2) {
                gzm2 = new Gizmos (iRandNum, 200, 100, 100,
                        Toolkit.getDefaultToolkit().getImage
                                            (this.getClass().getResource("gizmoLevel" + (iI+1) + "-2.png")), 100,
                        
                        Applet.newAudioClip(this.getClass().getResource("audioLevel" + (iI+1) + "-2.wav")));
                }
                else {
<<<<<<< HEAD
                    gzm2 = new Gizmos (randNum, 475, 100, 100, Toolkit.getDefaultToolkit().getImage
                                            (this.getClass().getResource("gizmoLevel" + (iI+1) + "-2.png")), 
                            100, Applet.newAudioClip(this.getClass().getResource("audioLevel" + (iI+1) + "-2.wav")));
=======
                    gzm2 = new Gizmos (iRandNum, 475, 100, 100,
                        Toolkit.getDefaultToolkit().getImage
                                            (this.getClass().getResource("gizmoLevel" + (iI+1) + "-2.png")), 100);
>>>>>>> d9d761d58e9488e7f0a5667103cae8d1c604821a
                }
                
                lklGizmos[iI].add(gzm2);
            }
        }
        
        for (int iI = 2; iI < 5; iI++) {
            iTemp = iRandNum = 0;
            for (int iJ = 0; iJ < 5; iJ++) {
                iTemp = iRandNum;
                iRandNum = iMinimum + (int)(Math.random()*iMaximum) + iTemp;
                gzm2 = new Gizmos (iRandNum, 475, 100, 100,
                        Toolkit.getDefaultToolkit().getImage
                                            (this.getClass().getResource("gizmoLevel4-"+ (iI+1) + ".png")), 100, 
                        Applet.newAudioClip(this.getClass().getResource("audioLevel4-" + (iI+1) + ".wav")));        
                lklGizmos[3].add(gzm2);
            }
            }
        
        
    }
    
    /**
     * start
     *
     * Method <I>start</I> override from the class <code>Applet</code>.<P>
     * This method is called after <code>init()</code> and it starts the
     * thread for the game to run.
     **/
    private void start () {
        //Declare thread
        Thread th = new Thread (this);
        //Start thread
        th.start ();
        
    }
    
    @Override
    /**
     * run
     *
     * Method <I>run</I> override of the class <code>Thread</code>.<P>
     * In this method the thread starts running. It's an indefinite cycle
     * that repaints the objects that are shown on screen.
     **/
    public void run() {
        // set times
        actualTime = System.currentTimeMillis();
        l_actualTime = System.currentTimeMillis();
        while (true) {
            //set elapsed time
            l_elapsedTime = System.currentTimeMillis() - l_actualTime;
            l_elapsedTime /= 1000;
            
            //update intro image when its 0
            if (iIntro == 0) {
                updateIntro();
            }
            
            // update images according to the iIntro counter
            if (iIntro > 0) {
                updateBackground();
                
                //start music
                if (!bMusica){
                    bMusica = true;
                    playMusic();
                }
                // update characters
                if (!bNextLevel && !bPause && !bGameOver) {
                    updateCharacters();
                    collision();
                }
            }
                            repaint();
                try {
                    //The thread sleeps
                    Thread.sleep (20);
        }
                    catch (InterruptedException iexError) {
                    System.out.println("Hubo un error en el juego " + iexError.toString());
                    }
        }

    }

   /**
     * updateIntro
     *
     * Method <I>updateIntro</I><P>
     * In this method the intro images are updated. It's an indefinite cycle
     * that repaints the objects that are shown on screen.
     **/
    public void updateIntro() {       
        //update main character's animation
        long elapsedTime = System.currentTimeMillis() - actualTime;
        actualTime += elapsedTime;
        aniIntro.actualiza(elapsedTime);
    }
    
    /**
     * update
     *
     * Method <I>updateCharacters</I> is called in <I>run</I> to change the positions
     * and the animations of the characters and gizmos
     **/
    public void updateCharacters() {        
        //update main character's animation
        long elapsedTime = System.currentTimeMillis() - actualTime;
        actualTime += elapsedTime;
        arrCtrMan[iLevel].getAnimation().actualiza(elapsedTime);
        
        //update gizmos x-position
        for(Object objGizmo: lklGizmos[iLevel]) {
            Gizmos gGizmo = (Gizmos) objGizmo;
            gGizmo.setX(gGizmo.getX()-iVelocity);
        }
        
        //jumping
        if(arrCtrMan[iLevel].getY() < 400) {
            arrCtrMan[iLevel].setdVelocity(arrCtrMan[iLevel].getdVelocity() - 1);
            arrCtrMan[iLevel].setY(arrCtrMan[iLevel].getY() - arrCtrMan[iLevel].getdVelocity());
        }
        else{
            //turn of jump bool
            bJump = false;
        }
    }
    
    /**
     * updateBackground
     * 
     * Method <I> updateBackground </I> keeps the background moving
     */
    public void updateBackground() {
        //move background
        if (!bPause){
            gzmBack1.setX(gzmBack1.getX() - iVelocity);
            gzmBack2.setX(gzmBack2.getX() - iVelocity);

            if(gzmBack1.getX() + gzmBack1.getWidth() < 0){
                gzmBack1.setX(gzmBack1.getWidth()-4);
            }

            if(gzmBack2.getX() + gzmBack2.getWidth() < 0){
                gzmBack2.setX(gzmBack1.getWidth()-4);
            }
        }
    }
    
    /**
     * collision
     *
     * Method <I>collision</I>
     **/
    public void collision() {
        //set min and max
        int iTemp = 0, iRandNum, iMinimum, iMaximum;
        iMinimum = 1000;
        iMaximum = 3000;
        
        //update gizmos x-position
        for(Object objGizmo: lklGizmos[iLevel]) {
            Gizmos gGizmo = (Gizmos) objGizmo;
            if(gGizmo.getX() + gGizmo.getWidth() < 0){
                iRandNum = iMinimum + (int)(Math.random()*iMaximum) + iTemp;
                iTemp = iRandNum;
                gGizmo.setX(iRandNum);
            }
        }
        
        iTemp = 0;
        
        //add points to user according to the item he/she collided with
        for(Object objGizmo: lklGizmos[iLevel]) {
            Gizmos gGizmo = (Gizmos) objGizmo;
            if(arrCtrMan[iLevel].intersecta(gGizmo)){
                addPoints(gGizmo);

                if(iPoints < -500) {
                    bGameOver = true;
                }
<<<<<<< HEAD
                randNum = minimum + (int)(Math.random()*maximum) + iTemp;
                iTemp = randNum;
                gGizmo.setX(randNum);
                gGizmo.getAuSound().play();
                
=======
                iRandNum = iMinimum + (int)(Math.random()*iMaximum) + iTemp;
                iTemp = iRandNum;
                gGizmo.setX(iRandNum);
                auPoint.play();
>>>>>>> d9d761d58e9488e7f0a5667103cae8d1c604821a
            }
        }
    }
    
    /**
     * addPoints
     * 
     * Method <I> addPoints </I> that adds points when character collides with a 
     * gizmo and validates if it's time to change of level
     * 
     * @param gGizmo 
     */
    public void addPoints (Gizmos gGizmo) {
        //add points according to gizmos value
        iPoints += gGizmo.getPoints();
        if (iPoints >= iMaxPoints && (iLevel < 3)) {
            bNextLevel = true;
            nextLevel();
        }
    }
    
    /**
     * nextLevel
     * 
     * Method <I> nextLevel </I> if called when the character has made it to the next level
     */
    public void nextLevel() {
        //update level
        iSeconds = 0;
        iPoints = 0;
        iMaxPoints += 500;
        if (iLevel < 3){
            iLevel++;
            iVelocity+=2;
            gzmBack1.setImagen(Toolkit.getDefaultToolkit().getImage
                                                                                    (this.getClass().getResource("backLevel" + (iLevel+1)
                                                                                            + ".jpg")));
            gzmBack2.setImagen(Toolkit.getDefaultToolkit().getImage
                                                                                   (this.getClass().getResource("backLevel" + (iLevel+1) 
                                                                                           + ".jpg")));
        }
        // turn of when is final level
        if (iLevel == 3) {
            bNextLevel = false;
        }
    }
    
    @Override
    /**
     * paint
     *
     * Method <I>paint</I> override of the class <code>Thread</code>.<P>
     * In this method the DoubleBuffer is initialized, the background and
     * foreground are updated, and the method paint1 is called to paint
     * the window
     **/
    public void paint (Graphics graGraphic){
        //Initialization of DoubleBuffer
        if (imgApplet == null){
            imgApplet = createImage(this.getSize().width, this.getSize().height);
            graApplet = imgApplet.getGraphics ();
        }
        
        if(iIntro < 1) {
        //Update the background image
        graApplet.setColor(Color.WHITE);
        }
        else {
            graApplet.setColor(Color.BLACK);
        }
        graApplet.fillRect(0, 0, this.getSize().width, this.getSize().height);
        
        //Update the foreground images
        graApplet.setColor(getForeground());
        
        // paint intro when intro counter is < 1
        if (iIntro < 1) {
            paintIntro(graApplet);
        }
        
        else if (iIntro >= 1 && !bNextLevel && !bGameOver) {
            paint1(graApplet);
        }
        
        // paint next level
        else if (bNextLevel) {
            paintNextLevel(graApplet);
            
            //delay
            if (iSeconds >= 150) {
                bNextLevel = false;
            }
        }
        
        // paint game over
        else if (bGameOver) {
            paintGameOver(graApplet);
        }
        
        //Paint updated images
        graGraphic.drawImage(imgApplet, 0, 0, this);
    }
    
    /**
     * paint1
     *
     * Method <I>paint1</I> override of the class <code>Thread</code>.<P>
     * In this method the objects shown on screen are painted according
     * to some conditions
     * @param graGraphic
     **/
    public void paint1 (Graphics graGraphic){
        // paint backgrounds
        gzmBack1.paint(graGraphic, this);
        gzmBack2.paint(graGraphic, this);
        
        //paint main character
        if(!bJump){
            arrCtrMan[iLevel].paint(graGraphic, this);
        }
        //pant jumping man
        else{
            arrCtrMan[iLevel].paintJump(graGraphic, this);
        }
        
        for(Object objGizmo: lklGizmos[iLevel]) {
            Gizmos gGizmo = (Gizmos) objGizmo;
            //paint gizmos
            gGizmo.paint(graGraphic, this);
        }
        
        // paint pause
        if (bPause) {
            paintPause(graApplet);
        }
        if(iLevel == 0 && iSeconds < 200) {
            paintInstructions(graApplet);
        }
        graGraphic.setFont(new Font ("Helvetica", Font.PLAIN, 24));
        graGraphic.setColor(Color.white);
        graGraphic.drawString("Puntos: " + iPoints, 675, 50);
    }
    
    /**
     * paintInstructions
     *
     * Method <I>paintInstrucciones</I>.<P>
     * In this method the objects shown on screen are painted according
     * to some conditions
     * @param graGraphic
     **/
    public void paintInstructions(Graphics graGraphic) {
        iSeconds++;
        graGraphic.setFont(new Font ("Helvetica", Font.PLAIN, 30));
        graGraphic.setColor(Color.white);
        graGraphic.drawString("Click SPACE to jump, don't harm the environment!", 100, 670);
    }
    
   /**
     * paintNextLeve
     *
     * Method <I>paintNextLevel</I>.<P>
     * In this method the objects shown on screen are painted according
     * to some conditions
     * @param graGraphic
     **/    
    public void paintNextLevel (Graphics graGraphic) {
        iSeconds++;
        gzmBack1.paint(graGraphic, this);
        gzmBack2.paint(graGraphic, this);
        
        graGraphic.drawImage(Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource("Level" + (iLevel+1) + ".png"))
                , 70, 210, this);
    }
    
    /**
     * playMusic
     *
     * Method <I>playMusic</I>.<P>
     * In this method the sounds are played
     * @param graGraphic
     **/    
    public void playMusic (){
        // play music
        auSoundTrack.play();
    }
    
    /**
     * paintIntro
     *
     * Method <I>paintIntro</I>.<P>
     * In this method the objects shown on screen are painted according
     * to some conditions
     * @param graGraphic
     **/    
    public void paintIntro (Graphics graGraphic){
            graGraphic.drawImage(aniIntro.getImagen(), 0, 0, this);
            graGraphic.setFont(new Font ("Helvetica", Font.PLAIN, 30));
            graGraphic.setColor(Color.black);
            graGraphic.drawString("Click ENTER", 800, 670);
    }
    /**
     * paintGameOver
     *
     * Method <I>paintGameOver</I>.<P>
     * In this method the objects shown on screen are painted according
     * to some conditions
     * @param graGraphic
     **/    
    public void paintGameOver (Graphics graGraphic) {
        //paint game over
        graGraphic.drawImage(Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource("gameover.jpg")),0,0,this);
        graGraphic.setFont(new Font ("Helvetica", Font.PLAIN, 72));
        graGraphic.setColor(Color.white);
        graGraphic.drawString("Puntos: " + iPoints, 500, 350);
    }

    /**
     * paintPause
     *
     * Method <I>painPause</I>.<P>
     * In this method the objects shown on screen are painted according
     * to some conditions
     * @param graGraphic
     **/    
    public void paintPause (Graphics graGraphic) {
        if(bPause) {
            graGraphic.setFont(new Font ("Helvetica", Font.PLAIN, 72));
            graGraphic.setColor(Color.white);
            graGraphic.drawString("PAUSA", 500, 350);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }
    
    @Override
    /**
     * keyReleased
     *
     * Method <I>keyReleased</I> override of the class
     * <code>KeyListener</code>.<P>
     * In this method we answer to the user's released key
     **/
    public void keyReleased(KeyEvent e) {
        // move trough manu
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            iIntro++;
        }
        //jumping character
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(!bPause){
                bJump = true;
                jump();
            }
        }
        //set pause
        if (e.getKeyCode() == KeyEvent.VK_P) {
            bPause = !bPause;
        }
        //Replay
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (bGameOver) {
                bGameOver = false;
                iMaxPoints = 400;
                iVelocity = 10;
                iSeconds =0;
                iLevel = 0;
                iPoints = 0;
                initImages();
               }
        }
    }

    /**
     * jump
     *
     * Method <I>jump</I>.<P>
     * In this method the character is abel to jump
     **/    
    public void jump() {
        //jumping
        if(arrCtrMan[iLevel].getY() == 400 ){
            arrCtrMan[iLevel].setdVelocity(24);
            arrCtrMan[iLevel].setY(arrCtrMan[iLevel].getY() - arrCtrMan[iLevel].getdVelocity());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Alpha game = new Alpha();
        game.setVisible(true);
    }


}