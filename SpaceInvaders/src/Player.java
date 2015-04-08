
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
/**
 *This class creates a Player
 * @author Natalia Garcia and Any Landeros
 */
public class Player extends Sprite implements Commons{
        
        private final int iSTART_Y = 600;               //where the player starts in y axis
        private final int iSTART_X = 450;               //where the player starts in x axis
        
        private final String sPlayer = "player.png";
        
        /**
        * Constructs a Player with the 'x' and 'y' position, the image, and the width
        *@see Player()
        */
        public Player() {
                
                ImageIcon imgPlayer = new ImageIcon(this.getClass().getResource(sPlayer));
                
                iWidth = iPLAYER_WIDTH;
                iHeight = iPLAYER_HEIGHT;
                
                setImage(imgPlayer.getImage());
                setX(iSTART_X);
                setY(iSTART_Y);
        }
        /**
         * Moves the player in the X position according to the Dx (user clicking arrow)
         */
        public void act() {
                iX+= iDx;
                if (iX<= 2)
                        iX= 2;
                if (iX>= iBOARD_WIDTH - 2*iWidth)
                        iX= iBOARD_WIDTH - 2*iWidth;
        }
        /**
         * While the user presses Left or Right, Dx increments or decrements
         * @param e 
         */
        public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                
                if (key == KeyEvent.VK_LEFT)
                {
                        iDx = -2;
                }
                
                else if (key == KeyEvent.VK_RIGHT)
                {
                        iDx = 2;
                }
        }
        /**
         * When the user stops pressing the left or right key, the player stops moving
         * When the user presses P the game pauses
         * When the user presses I the instructions are shown
         * When the user presses R the authors are shown
         * @param e 
         */
        public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                
                if (key == KeyEvent.VK_LEFT) {
                        iDx = 0;
                }
                
                else if (key == KeyEvent.VK_RIGHT) {
                        iDx = 0;
                }
                
                else if (key == KeyEvent.VK_P) {
                        bPausa = !bPausa;
                }
                
                else if (key == KeyEvent.VK_I) {
                        bInstr = !bInstr;
                }
                
                else if (key == KeyEvent.VK_R) {
                        bRecAutor = !bRecAutor;
                }
        }
}
