
import javax.swing.ImageIcon;
/**
 *This class is about the shot that the player throws to the alien
 * @author Natalia Garcia & Any Landeros
 */
public class Shot extends Sprite {
        
        private String sShot = "shot2.png";      //name of the file that contains the image of shot
        private final int iH_SPACE = 6;          //the pixels it will move horizontally
        private final int iV_SPACE = 10;          //the pixels it will move vertically
        
        public Shot() {
        }
        
        /**
         * This is the constructor with parameters
         * iX is the position of the shot on the x axis
         * iY is the position of the shot on the y axis
         * @param iX
         * @param iY 
         */
        public Shot(int iX, int iY) {
                
                ImageIcon imgShot = new ImageIcon(this.getClass().getResource(sShot));
                setImage(imgShot.getImage());
                setX(iX + iH_SPACE);
                setY(iY - iV_SPACE);
        }
}