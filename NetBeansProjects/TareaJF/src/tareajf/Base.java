package tareajf;

/**
 * Base
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Base</code>
 *
 * @author Javier Guajardo
 * @version 1.0 -- Examen 1er Parcial
 * @date 11/02/2015 
 * 
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class Base {

    private int iX;     //posicion en x.       
    private int iY;     //posicion en y.
    private int iAncho; //ancho del objeto
    private int iAlto; //largo del objeto
    private Image imaImagen;	//imagen.
      private int iVelocidad;   // velocidad
    private ImageIcon imiIcono;	//icono.

    /**
     * Base
     * 
     * Metodo constructor usado para crear el objeto animal
     * creando el icono a partir de una imagen
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iAncho es el <code>ancho</code> del objeto.
     * @param iAlto es el <code>Largo</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public Base(int iX, int iY , int iAncho, int iAlto,Image imaImagen) {
        this.iX = iX;
        this.iY = iY;
        this.iAncho = iAncho;
        this.iAlto = iAlto;
        this.imaImagen = imaImagen;
    }

    
    /**
     * setX
     * 
     * Metodo modificador usado para cambiar la posicion en x del objeto
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public void setX(int iX) {
        this.iX = iX;
    }

    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en x del objeto 
     * 
     * @return iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public int getX() {
            return iX;
    }

    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en y del objeto 
     * 
     * @param iY es la <code>posicion en y</code> del objeto.
     * 
     */
    public void setY(int iY) {
            this.iY = iY;
    }

    /**
     * getY
     * 
     * Metodo de acceso que regresa la posicion en y del objeto 
     * 
     * @return posY es la <code>posicion en y</code> del objeto.
     * 
     */
    public int getY() {
        return iY;
    }

    /**
     * setImagen
     * 
     * Metodo modificador usado para cambiar el icono de imagen del objeto
     * tomandolo de un objeto imagen
     * 
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public void setImagen(Image imaImagen) {
        this.imaImagen = imaImagen;
    }

    /**
     * getImagen
     * 
     * Metodo de acceso que regresa la imagen que representa el icono del objeto
     * 
     * @return la imagen a partide del <code>icono</code> del objeto.
     * 
     */
    public Image getImagen() {
        return imaImagen;
    }
    
      /**
     * setVelocidad
     * 
     * Metodo modificador usado para cambiar la velocidad del objeto 
     * 
     * @param iVelocidad es un <code>entero</code> con la velocidad del objeto.
     * 
     */
    public void setVelocidad(int iVelocidad) {
            this.iVelocidad = iVelocidad;
    }

    /**
     * getVelocidad
     * 
     * Metodo de acceso que regresa la velocidad del objeto 
     * 
     * @return iVelocidad un <code>entero</code> con velocidad del objeto.
     * 
     */
    public int getVelocidad() {
        return iVelocidad;
    }


    /**
     * getAncho
     * 
     * Metodo de acceso que regresa el ancho del icono 
     * 
     * @return un <code>entero</code> que es el ancho de la imagen.
     * 
     */
    public int getAncho() {
        return iAncho;
    }

    /**
     * getAlto
     * 
     * Metodo que  da el alto del icono 
     * 
     * @return un <code>entero</code> que es el alto de la imagen.
     * 
     */
    public int getAlto() {
        return iAlto;
    }
    /**
     * arriba
     * 
     * Metodo que sube al personaje de acuerdo a la velocidad
     * 
     */
    public void arriba() {
        this.setY(this.getY() - iVelocidad);
    }
    
    /**
     * abajo
     * 
     * Metodo que baja al personaje de acuerdo a la velocidad
     * 
     */
    public void abajo() {
        this.setY(this.getY() + iVelocidad);
    }
    
    /**
     * derecha
     * 
     * Metodo que mueve a la derecha al personaje de acuerdo a la velocidad
     * 
     */
    public void derecha() {
        this.setX(this.getX() + iVelocidad);
    }
    
    /**
     * izquierda
     * 
     * Metodo que mueve a la izquierda al personaje de acuerdo a la velocidad
     * 
     */
    public void izquierda() {
        this.setX(this.getX() - iVelocidad);
    }
    
    
    /**
     * paint
     * 
     * Metodo para pintar el animal
     * 
     * @param graGrafico    objeto de la clase <code>Graphics</code> para graficar
     * @param imoObserver  objeto de la clase <code>ImageObserver</code> es el 
     *    Applet donde se pintara
     * 
     */
    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        graGrafico.drawImage(getImagen(), getX(), getY(), getAncho(), getAlto(), imoObserver);
    }
    /**
     * intersecta
     * 
     * Metodo que checa si un objeto animal intersecta  a otro animal
     * 
     * @param objAnimal es un objeto de la clase <code>Object</code>
     * @return un valor <code>boolean</code> indicando si se intersecta con este
     * 
     */
    public boolean intersecta (Object objBase) {
        // Valido si el objeto es animal
        if (objBase instanceof Base) {
            // creo los rectangulos de ambos
            Rectangle rctEste = new Rectangle(this.getX(), this.getY(), this.getAlto(), this.getAncho());
            Base baseTemp = (Base) objBase;
            Rectangle rctParam = new Rectangle(baseTemp.getX(), baseTemp.getY(), baseTemp.getAlto(),
                                                    baseTemp.getAncho());
            return rctEste.intersects(rctParam);
        }
        // si no entro entonces no es un Animal
        return false;
    }
    
        /**
     * intersecta
     * 
     * Metodo para checar si un objeto animal choca con otro objeto animal
     * 
     * @param iX es un <code>Entero</code> que da la posicion en X del mouse.
     * @param iY es un <code>Entero</code> que da la posicion en Y del mouse.
     * @return 
     * 
     */
    
    public boolean intersecta(int iX, int iY){
        Rectangle rctEste = new Rectangle(this.getX(), this.getY(), 
                this.getAlto(), this.getAncho());
        return rctEste.contains(iX, iY);
        
    }

    /**
     * equals
     * 
     * Metodo para checar igualdad con otro objeto
     * 
     * @param objObjeto    objeto de la clase <code>Object</code> para comparar
     * @return un valor <code>boleano</code> que sera verdadero si el objeto
     *   que invoca es igual al objeto recibido como parámetro
     * 
     */
    public boolean equals(Object objObjeto) {
        // si el objeto parametro es una instancia de la clase Base
        if (objObjeto instanceof Base) {
            // se regresa la comparación entre este objeto que invoca y el
            // objeto recibido como parametro
            Base basParam = (Base) objObjeto;
            return this.getX() ==  basParam.getX() && 
                    this.getY() == basParam.getY() &&
                    this.getAncho() == basParam.getAncho() &&
                    this.getAlto() == basParam.getAlto() &&
                    this.getImagen() == basParam.getImagen();
        }
        else {
            // se regresa un falso porque el objeto recibido no es tipo Base
            return false;
        }
    }

    /**
     * toString
     * 
     * Metodo para obtener la interfaz del objeto
     * 
      * @return un valor <code>String</code> que representa al objeto
     * 
     */
    public String toString() {
        return " x: " + this.getX() + " y: "+ this.getY() +
                " ancho: " + this.getAncho() + " alto: " + this.getAlto();
    }
}