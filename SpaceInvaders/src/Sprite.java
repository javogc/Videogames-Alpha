
import java.awt.Image;
import java.awt.Rectangle;
/**
 *The base for the construction of characters
 * @author http://zetcode.com/
 */
public class Sprite {
        
        private boolean bVisible;
        private Image imgImage;
        protected int iX;
        protected int iY;
        protected boolean bDying;
        protected int iDx;
        protected boolean bPausa;
        protected boolean bInstr;
        protected boolean bRecAutor;
        protected int iWidth;
        protected int iHeight;
        
        /**
         * The constructor
         * @see Sprite()
         */
        public Sprite() {
                bVisible = true;
                bPausa = false;
                bInstr = false;
                bRecAutor = false;
        }
        
        /**
         * If the character dies it stops being visible
         * @see die()
         */
        public void die() {
                bVisible = false;
        }
        
        /**
         * Returns of the character is visible
         * @return bVisible
         */
        public boolean isVisible() {
                return bVisible;
        }
        
        /**
         * Sets the value for the variable bVisible
         * @param bVisible
         */
        protected void setVisible(boolean bVisible) {
                this.bVisible = bVisible;
        }
        
        /**
         * Sets the value of the image of the character
         * @param imgImage
         */
        public void setImage(Image imgImage) {
                this.imgImage = imgImage;
        }
        
        /**
         * Returns the image of the character
         * @return imgImage
         */
        public Image getImage() {
                return imgImage;
        }
        
        /**
         * Sets the position of the character on the x axis
         * @param iX
         */
        public void setX(int iX) {
                this.iX = iX;
        }
        
        /**
         * Sets the position of the character on the y axis
         * @param iY
         */
        public void setY(int iY) {
                this.iY = iY;
        }
        
        /**
         * Returns the position of the character on the y axis
         * @return iY
         */
        public int getY() {
                return iY;
        }
        
        /**
         * Returns the positions of the sprite on the x axis
         * @return iX
         */
        public int getX() {
                return iX;
        }
        
        /**
         * Returns the position of the character on the x axis
         * @return iX
         */
        public void setDying(boolean bDying) {
                this.bDying = bDying;
        }
        
        /**
         * Returns if the character is dying
         * @return bDying
         */
        public boolean isDying() {
                return this.bDying;
        }
        
        /**
         * Returns if the game is paused
         * @return bPausa
         */
        public boolean getPausa() {
                return bPausa;
        }
        
        /**
         * Sets the value of bPausa
         * @param bPausa
         */
        public void setPausa(boolean bPausa) {
                this.bPausa = bPausa;
        }
        
        /**
         * Returns if instruction view is on
         * @return bInstr
         */
        public boolean getInstr() {
                return bInstr;
        }
        
        /**
         * Sets the value of bInstr
         * @param bInstr
         */
        public void setInstr(boolean bInstr) {
                this.bInstr = bInstr;
        }
        
        /**
         * Returns if the author information view is on
         * @return bRecAutor
         */
        public boolean getRecAutor() {
                return bRecAutor;
        }
        
        /**
         * Sets the value of bRecAutor
         * @param bRecAutor
         */
        public void setRecAutor(boolean bRecAutor) {
                this.bRecAutor = bRecAutor;
        }
        
        /**
         * Returns if two sprite intersect
         * @param objSprite
         * @return
         */
        public boolean intersecta (Object objSprite) {
                //valido si el objeto es Sprite
                if (objSprite instanceof Sprite) {
                        //creo los rectangulos de ambos
                        Rectangle rctEste = new Rectangle(this.getX(), this.getY(), this.iWidth, this.iHeight);
                        Sprite sprTemp = (Sprite) objSprite;
                        Rectangle rctParam = new Rectangle(sprTemp.getX(), sprTemp.getY(), this.iWidth, this.iHeight);
                        
                        return rctEste.intersects(rctParam);
                }
                //si no entro entonces no es Sprite
                return false;
        }
        
        public boolean intersecta (int iX, int iY) {
                return (iX >= (this.iX) && iX <= (this.iX+this.iWidth) && iY >= (this.iY));
        }
}
