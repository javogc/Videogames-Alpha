
import javax.swing.ImageIcon;

/**
 *Provides the necessary elements to create an Alien
 * @author Natalia Garcia and Any Landeros
 */
public class Alien extends Sprite implements Commons {
        
        private Bomb bBomb;
        private final String sShot = "alien.png";
        /**
        * Constructs an Alien with the 'x' and 'y' position.
        *
        * @param iX x position
        * @param iY y position
        */
        public Alien(int iX, int iY) {
                this.iX = iX;
                this.iY = iY;
                super.iWidth = iALIEN_WIDTH;
                super.iHeight = iALIEN_WIDTH;
                
                bBomb = new Bomb(iX, iY);
                ImageIcon imgShot = new ImageIcon(this.getClass().getResource(sShot));
                setImage(imgShot.getImage());
                
        }
        /**
         * Moves the alien on the x position
         * @param direction 
         */
        public void act(int direction) {
                this.iX += direction;
        }
        /**
         * Returns if there's a bomb or not
         * @return bBomb;
         */
        public Bomb getBomb() {
                return bBomb;
        }
        
        
        /**
        *Provides the necessary elements to create a Bomb
        * @author Natalia Garcia and Any Landeros
        */
        public class Bomb extends Sprite {
                
                private final String bomb = "shot1.png";
                private boolean destroyed;
                /**
                 * Constructs the Bomb with the position in x and y
                 * @param iX
                 * @param iY 
                 */
                public Bomb(int iX, int iY) {
                        setDestroyed(true);
                        this.iX = iX;
                        this.iY = iY;
                        ImageIcon imgShot = new ImageIcon(this.getClass().getResource(bomb));
                        setImage(imgShot.getImage());
                }
                /**
                 * Sets the boolean variable Destroyed to the parameter
                 * @param destroyed 
                 */
                public void setDestroyed(boolean destroyed) {
                        this.destroyed = destroyed;
                }
                /**
                 * Returns the value of the boolean variable 'destroyed'
                 * @return destroyed
                 */
                public boolean isDestroyed() {
                        return destroyed;
                }
        }
}
