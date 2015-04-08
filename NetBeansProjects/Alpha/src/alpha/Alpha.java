/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package alpha;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JFrame;

/**
 *
 * @author Javier Guajardo, Natalia García, Gabriel Berlanga & Any Landero
 */
public class Alpha extends JFrame implements Runnable, KeyListener {
        /**
         *
         * @globalVariables
         */
        
        LinkedList lklCharacter;
        LinkedList lklBumps;
        LinkedList lklTokens;
        
        Image imgBackground;
        Image imgCharacterLevel1;
        Image imgCharacterLevel2;
        Image imgCharacterLevel3;
        Image imgCharacterLevel4;
        Image imgTree;
        Image imgApple;
        Image imgPig;
        Image imgBullet;
        Image imgEgg;
        Image imgRock;
        Image imgWaterDrop;
        Image imgGasoline;
        Image imgTrash;
        
        public void init() {
                
                imgBackground = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("background1.png"));
                
                imgCharacterLevel1 = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("monitohacha1.png"));
                
                imgCharacterLevel2 = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("monitopistola1.png"));
                
                imgCharacterLevel3 = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("monitocubeta1.png"));
                
                imgCharacterLevel1 = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("monitomoto1.png"));
                
                imgTree = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("arbol.png"));
                
                imgApple = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("manzana.png"));
                
                imgPig = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("cerdito.png"));
                
//                imgBullet = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
//                        getResource ("bala.png"));
                
                imgEgg = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("huevo.png"));
                
                imgRock = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("rosa1.png"));
                
                imgWaterDrop = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("gota.png"));
                
                imgGasoline = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("gas.png"));
                
                imgTrash = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("basura1.png"));
                
                
//                LinkedList lklCharacter;
//                LinkedList lklBumps;
//                LinkedList lklTokens;
                
                Gizmos gzmCharacter;
                gzmCharacter = new Gizmos (0, 0, 50, 50, imgCharacterLevel1);
                Gizmos gzmObstacle;
                gzmObstacle = new Gizmos (0, 0, 50, 50, imgTree);
        }
        
        @Override
        public void run() {
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
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
