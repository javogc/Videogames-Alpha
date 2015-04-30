package alpha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Javier Guajardo, Natalia García, Gabriel Berlanga and Any Landero
 */

public class Alpha extends JFrame implements Runnable, KeyListener {

        /**
         *
         * @globalVariables
         */
        private Character[] ctrMan;
        private int iIntro;
        private int iLevel;
        private int iVelocity;
        private int iTemp;
        private long actualTime;
        private LinkedList<Gizmos> lklGizmos;
        private Gizmos gzmBack1;
        private Gizmos gzmBack2;
        private boolean bJump;
        private Image imgApplet;
        private Graphics graApplet;
        private long l_elapsedTime;
        private long l_actualTime;
        private int iYTemp;
        private int iPoints;
        private boolean bPoints;

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
            bJump = false;
            iIntro = 0;
            iLevel = 0;
            iVelocity = 10;
            iTemp = 0;
            iPoints = 0;
            bPoints = false;
            initImages();
            addKeyListener(this);
        }

        /** 
         * initImages
         * 
         * Method <I>initImages</I> is called by the method <I>init</I> 
         */
        public void initImages() {
            //load background images to gizmos of background
            Image imgBackground = Toolkit.getDefaultToolkit().getImage
                        (this.getClass().getResource ("background.jpg"));

            //load background image to gizmos
            gzmBack1 = new Gizmos(0, 0, 4868, 900, imgBackground, 0);
            gzmBack2 = new Gizmos(4868, 0, 4868, 900, imgBackground, 0);

            //loading images for the animation of the main character
            Image [][] imgMan = new Image [4][8];
            Animation [] aniMan = new Animation[4];
            
            for (int iI = 0; iI < 4; iI++) {
                    aniMan[iI] = new Animation();
            }

            for (int iI = 0; iI < 4; iI++) {
            	for (int iJ = 0; iJ < 8; iJ++) {
//                                imgMan[iI][iJ] = Toolkit.getDefaultToolkit().getImage
//                        (this.getClass().getResource ("manLevel1-1.png"));
                    imgMan[iI][iJ] = Toolkit.getDefaultToolkit().getImage
                        (this.getClass().getResource ("manLevel" + (iI+1) + "-" 
                            + (iJ+1) + ".png"));
                        aniMan[iI].sumaCuadro(imgMan[iI][iJ], 100);
                }
            }

            //creating the main characters for each level
            ctrMan = new Character [4];
            for (int iI = 0; iI < 4; iI++) {
                ctrMan[iI] = new Character (50, 400, 100, 100, aniMan[iI]);
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

            lklGizmos = new LinkedList();
            Gizmos gzm1, gzm2;
            
            for (int i = 0; i < 5; i++) {
                    gzm1 = new Gizmos (iNewX, 400, 200, 200, 
                             Toolkit.getDefaultToolkit().getImage
                            (this.getClass().getResource("gizmoLevel1-1.png")), -100);
                    
                    iNewX+=700;
                    
                    lklGizmos.add(gzm1);
            }
            iNewX = 100;
            for (int i = 0; i < 5; i++) {
                    gzm2 = new Gizmos (iNewX, 400, 200, 200, 
                             Toolkit.getDefaultToolkit().getImage
                            (this.getClass().getResource("gizmoLevel1-2.png")), 100);
                    
                    iNewX +=700;
                    
                    lklGizmos.add(gzm2);
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
                
                update();
                if(iPoints < 500)
                        collision();
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

        /**
         * update
         *
         * Method <I>update</I> is called in <I>run</I> to change the positions
         * and the animations of the characters and gizmos
        **/
        public void update() {
            //move background
            gzmBack1.setX(gzmBack1.getX() - iVelocity);
            gzmBack2.setX(gzmBack2.getX() - iVelocity);
            
            if(gzmBack1.getX() + gzmBack1.getWidth() < 0){
                gzmBack1.setX(gzmBack1.getWidth()-4);
            }

            if(gzmBack2.getX() + gzmBack2.getWidth() < 0){
                gzmBack2.setX(gzmBack1.getWidth()-4);
            }

            //update main character's animation
            long elapsedTime = System.currentTimeMillis() - actualTime;
            actualTime += elapsedTime;
            ctrMan[iLevel].getAnimation().actualiza(elapsedTime);
            
            if(iIntro > 3) {
                //update gizmos x-position
                for(Object objGizmo: lklGizmos) {
                        Gizmos gGizmo = (Gizmos) objGizmo;
                        gGizmo.setX(gGizmo.getX()-iVelocity);
                }
            }

            if (bJump) {
                jump();
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
                for(Object objGizmo: lklGizmos) {
                        Gizmos gGizmo = (Gizmos) objGizmo;
                        if(gGizmo.getX() + gGizmo.getWidth() < 0){
                                gGizmo.setX(iNewX);
                        }
                        iNewX += 300;
                }
                
                int iNewX2 = this.getWidth() + 100;
                
                //add points to user according to the item he/she collided with
                for(Object objGizmo: lklGizmos) {
                        Gizmos gGizmo = (Gizmos) objGizmo;
                        if(ctrMan[iLevel].intersecta(gGizmo)){
                                addPoints(gGizmo);
                                gGizmo.setX(iNewX);
                                iNewX2 += 300;
                        }
                }
                
        }
        
        public void addPoints (Gizmos gGizmo) {
                iPoints += gGizmo.getPoints();
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
            graApplet.setColor(getBackground());
            graApplet.fillRect(0, 0, this.getSize().width, this.getSize().height);
            
            //Update the foreground images
            graApplet.setColor(getForeground());
            if (iIntro < 4) {
                paintIntro(graApplet);
            }
            else if (iPoints < 500) {
                paint1(graApplet);
            }
            else if (iPoints >= 500) {
                paintNextLevel(graApplet);
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
         **/
        public void paint1 (Graphics graGraphic){
            gzmBack1.paint(graGraphic, this);
            gzmBack2.paint(graGraphic, this);

            //paint main character
            ctrMan[iLevel].paint(graGraphic, this);
            
            for(Object objGizmo: lklGizmos) {
                Gizmos gGizmo = (Gizmos) objGizmo;
                //paint gizmos
                gGizmo.paint(graGraphic, this);
            }
            graGraphic.setFont(new Font ("Helvetica", Font.PLAIN, 24));
            graGraphic.setColor(Color.white);
            graGraphic.drawString("Puntos: " + iPoints, 675, 50);
        }
        
        public void paintNextLevel (Graphics graGraphic){
            gzmBack1.paint(graGraphic, this);
            gzmBack2.paint(graGraphic, this);
            
            graGraphic.setFont(new Font ("Helvetica", Font.PLAIN, 60));
            graGraphic.setColor(Color.white);
            graGraphic.drawString("¡Felicidades!", 450, 300);
            graGraphic.drawString("Nivel 2", 450, 500);
        }

        public void paintIntro (Graphics graGraphic){    
            //draws the intro image according to the intro phase
            graGraphic.drawImage(Toolkit.getDefaultToolkit().getImage
                        (this.getClass().getResource("intro" + (iIntro+1) + ".png"))
                        , 0, 0, this);
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
                if (!bJump) {
                        bJump = true;
                        iTemp = (int)l_elapsedTime;
                        iYTemp = ctrMan[iLevel].getY();
                }
            }
        }

        public void jump() {
            int iSeconds = (int)l_elapsedTime - iTemp;
            int iJump = 150;
            
            if (ctrMan[iLevel].getY() > iJump) {
                    ctrMan[iLevel].setY(iJump);
            }
            
            else if (ctrMan[iLevel].getY() == iJump && iSeconds == 2) {
                    ctrMan[iLevel].setY(iYTemp);
                    bJump = false;
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