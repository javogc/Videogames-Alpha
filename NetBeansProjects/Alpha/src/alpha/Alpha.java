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
import javax.swing.ImageIcon;
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
    private Character[] ctrMan;
    
    private LinkedList<Gizmos>[] lklGizmos;
    
    private Gizmos gzmBack1;
    private Gizmos gzmBack2;
    
    private Animation aniIntro;
    
    private int iIntro;
    private int iLevel;
    private int iTemp; 
    private int iYTemp;
    private int iPoints;
    private int iVelocity;
    private int iSeconds;
    
    private double dAcceleration;
    
    private long actualTime;
    private long l_elapsedTime;
    private long l_actualTime;
    
    private boolean bPoints;
    private boolean bNextLevel;
    private boolean bMusica;
    private boolean bJump;

    
    private Image imgApplet;
    private Graphics graApplet;
    
    private AudioClip auSoundTrack;
    private AudioClip auPoint;
    
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
        setSize (1000, 700);
        iIntro = 0;
        iLevel = 0;
        iTemp = 0;
        iPoints = 0;
        iSeconds = 0;
        iVelocity = 10;
        dAcceleration = 0.2;
        bPoints = false;
        bNextLevel = false;
        bJump = false;
        initImages();
        addKeyListener(this);
        
        
        
        //Cargar sonido del juego
        
        URL URLSoundTrack = this.getClass().getResource("soundtrack.wav");
        
        auSoundTrack =  Applet.newAudioClip(URLSoundTrack);
        
        
        URL URLPoints = this.getClass().getResource("points.wav");
        auPoint = Applet.newAudioClip(URLPoints);
                
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
        gzmBack1 = new Gizmos(0, 0, 4868, 900, imgBackground, 0);
        gzmBack2 = new Gizmos(4868, 0, 4868, 900, imgBackground, 0);
        
        //loading images for the animation of the main character
        Image [][] imgMan = new Image [4][8];
        Animation [] aniMan = new Animation[4];
        Image [] imgJump = new Image [4];
        
        for (int iI = 0; iI < 4; iI++) {
            aniMan[iI] = new Animation();
        }
        
        for (int iI = 0; iI < 4; iI++) {
            for (int iJ = 0; iJ < 8; iJ++) {
                imgMan[iI][iJ] = Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource ("manLevel" + (iI+1) + "-"
                                        + (iJ+1) + ".png"));
                aniMan[iI].sumaCuadro(imgMan[iI][iJ], 100);
            }
        }
        for (int iI = 0; iI < 4; iI++){
            imgJump[iI] = Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource ("jumpLevel" + (iI+1) + ".png"));
        }
        
        //creating the main characters for each level
        ctrMan = new Character [4];
        for (int iI = 0; iI < 4; iI++) {
            ctrMan[iI] = new Character (50, 400, 100, 100, aniMan[iI], 9, 0, imgJump[iI]);
        }
        
        //loading images of the Gizmos, these include obstacles & tokens
        Image [][] imgGizmo = new Image[4][6];
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
                    imgGizmo[iI][iJ] = Toolkit.getDefaultToolkit().getImage
                                    (this.getClass().getResource("gizmoLevel" + (iI+1) + "-"
                                            + (iJ+1) + ".png"));
                }
            }
        }
        
        Random r = new Random();
        int Low = 10;
        int High = 100;
        int R = 50;
        
        int iNewX = 700;
        
        lklGizmos = new LinkedList [4];
        Gizmos gzm1, gzm2;
        
        for (int iI = 0; iI < 4; iI++) {
            
            iNewX = 700;
            
            for (int iJ = 0; iJ < 5; iJ++) {
                if(iI == 1){
                    gzm1 = new Gizmos (iNewX, 375, 200, 125, 
                            Toolkit.getDefaultToolkit().getImage
                          (this.getClass().getResource("gizmoLevel2-1.png")), -100);
                }
                else {
                gzm1 = new Gizmos (iNewX, 375, 200, 200,
                        Toolkit.getDefaultToolkit().getImage
                                                    (this.getClass().getResource("gizmoLevel" + (iI+1) + "-1.png")), -100);
                }
                
                
                iNewX += 700;
                
                if (lklGizmos[iI] == null) {
                    lklGizmos[iI] = new LinkedList<Gizmos>();
                }
                
                lklGizmos[iI].add(gzm1);
            }
            
            iNewX = 100;
            
            for (int iJ = 0; iJ < 5; iJ++) {
                if(iI == 2) {
                gzm2 = new Gizmos (iNewX, 200, 100, 100,
                        Toolkit.getDefaultToolkit().getImage
                                            (this.getClass().getResource("gizmoLevel" + (iI+1) + "-2.png")), 100);
                }
                else {
                    gzm2 = new Gizmos (iNewX, 475, 100, 100,
                        Toolkit.getDefaultToolkit().getImage
                                            (this.getClass().getResource("gizmoLevel" + (iI+1) + "-2.png")), 100);
                }
                
                iNewX += 700;
                
                lklGizmos[iI].add(gzm2);
            }
        }
        
        for (int iI = 2; iI < 5; iI++) {
            
            for (int iJ = 0; iJ < 5; iJ++) {
                gzm2 = new Gizmos (iNewX, 475, 100, 100,
                        Toolkit.getDefaultToolkit().getImage
                                            (this.getClass().getResource("gizmoLevel4-"+ (iI+1) + ".png")), 100);
                
                iNewX += 700;
                
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
        actualTime = System.currentTimeMillis();
        l_actualTime = System.currentTimeMillis();
        while (true) {
            l_elapsedTime = System.currentTimeMillis() - l_actualTime;
            l_elapsedTime /= 1000;
            
            if (iIntro == 0) {
                updateIntro();
            }
            
            if (iIntro > 3) {
                updateBackground();
                
                if (!bMusica){
                    
                    bMusica = true;
                    playMusic();
                }
                if (!bNextLevel) {
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
                System.out.println("Hubo un error en el juego " +
                        iexError.toString());
            }
        }
    }
    
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
        ctrMan[iLevel].getAnimation().actualiza(elapsedTime);
        
        //update gizmos x-position
        for(Object objGizmo: lklGizmos[iLevel]) {
            Gizmos gGizmo = (Gizmos) objGizmo;
            gGizmo.setX(gGizmo.getX()-iVelocity);
        }
        
        //jumping
        if(ctrMan[iLevel].getY() < 400) {
            ctrMan[iLevel].setdVelocity(ctrMan[iLevel].getdVelocity() - 1);
            ctrMan[iLevel].setY(ctrMan[iLevel].getY() - ctrMan[iLevel].getdVelocity());
        }
        else{
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
        gzmBack1.setX(gzmBack1.getX() - iVelocity);
        gzmBack2.setX(gzmBack2.getX() - iVelocity);
        
        if(gzmBack1.getX() + gzmBack1.getWidth() < 0){
            gzmBack1.setX(gzmBack1.getWidth()-4);
        }
        
        if(gzmBack2.getX() + gzmBack2.getWidth() < 0){
            gzmBack2.setX(gzmBack1.getWidth()-4);
        }
    }
    
    /**
     * collision
     *
     * Method <I>collision</I>
     **/
    public void collision() {
        int iNewX = this.getWidth() + 100;
        
        //update gizmos x-position
        for(Object objGizmo: lklGizmos[iLevel]) {
            Gizmos gGizmo = (Gizmos) objGizmo;
            if(gGizmo.getX() + gGizmo.getWidth() < 0){
                gGizmo.setX(iNewX);
            }
            iNewX += 300;
        }
        
        int iNewX2 = this.getWidth() + 100;
        
        //add points to user according to the item he/she collided with
        for(Object objGizmo: lklGizmos[iLevel]) {
            Gizmos gGizmo = (Gizmos) objGizmo;
            if(ctrMan[iLevel].intersecta(gGizmo)){
                addPoints(gGizmo);
                auPoint.play();
                gGizmo.setX(iNewX);
                iNewX2 += 300;
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
        iPoints += gGizmo.getPoints();
        if (iPoints >= 300 && !bNextLevel) {
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
        iLevel++;
        iSeconds = 0;
        iPoints = 0;
        
        gzmBack1.setImagen(Toolkit.getDefaultToolkit().getImage
                                                                                (this.getClass().getResource("backLevel" + (iLevel+1)
                                                                                        + ".jpg")));
        gzmBack2.setImagen(Toolkit.getDefaultToolkit().getImage
                                                                               (this.getClass().getResource("backLevel" + (iLevel+1) 
                                                                                       + ".jpg")));
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
        
        //Update the background image
        graApplet.setColor(Color.BLACK);
        graApplet.fillRect(0, 0, this.getSize().width, this.getSize().height);
        
        //Update the foreground images
        graApplet.setColor(getForeground());
        
        if (iIntro < 4) {
            paintIntro(graApplet);
        }
        
        else if (!bNextLevel) {
            paint1(graApplet);
        }
        
        else if (bNextLevel) {
            paintNextLevel(graApplet);
            
            if (iSeconds >= 150) {
                bNextLevel = false;
            }
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
        gzmBack1.paint(graGraphic, this);
        gzmBack2.paint(graGraphic, this);
        
        //paint main character
        if(!bJump){
            ctrMan[iLevel].paint(graGraphic, this);
        }
        else{
            ctrMan[iLevel].paintJump(graGraphic, this);
        }
        
        for(Object objGizmo: lklGizmos[iLevel]) {
            Gizmos gGizmo = (Gizmos) objGizmo;
            //paint gizmos
            gGizmo.paint(graGraphic, this);
        }
        
        graGraphic.setFont(new Font ("Helvetica", Font.PLAIN, 24));
        graGraphic.setColor(Color.white);
        graGraphic.drawString("Puntos: " + iPoints, 675, 50);
    }
    
    public void paintNextLevel (Graphics graGraphic){
        iSeconds++;
        gzmBack1.paint(graGraphic, this);
        gzmBack2.paint(graGraphic, this);
        
        graGraphic.drawImage(Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource("Level" + (iLevel+1) + ".png"))
                , 70, 210, this);
    }
    
    public void playMusic (){
        
        auSoundTrack.play();
        
    }
    
    public void paintIntro (Graphics graGraphic){
        if (iIntro > 0) {
        //draws the intro image according to the intro phase
        graGraphic.drawImage(Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource("intro" + (iIntro+1) + ".png"))
                , 0, 0, this);
        }
        else {
            graGraphic.drawImage(aniIntro.getImagen(), 0, 0, this);
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            iIntro++;
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                bJump = true;
                jump();
        }
    }
    
    public void jump() {
        if(ctrMan[iLevel].getY() == 400 ){
            ctrMan[iLevel].setdVelocity(24);
            ctrMan[iLevel].setY(ctrMan[iLevel].getY() - ctrMan[iLevel].getdVelocity());
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