package alpha;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gabriel Berlanga
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class Character {

    private double iPosX;
    private double iPosY;
    private double dVelocity;
    private double dGravity;
    private int iAncho;
    private int iAlto;

    private Animation aniPlayer;
    
    /**
     * Chaacter
     * 
     * Metodo constructor usado para crear el objeto principal
     * creando el icono a partir de una imagen
     * 
     * @param iPosX es la <code>posicion en x</code> del objeto.
     * @param iPosY es la <code>posicion en y</code> del objeto.
     * @param iAncho es el <code>ancho</code> del objeto.
     * @param iAlto es el <code>Largo</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    
    public Character (int iPosX, int iPosY, int iAncho, int iAlto, 
            Animation aniPlayer, double dGrav, double dVel){
        this.iPosX = (double)iPosX;
        this.iPosY = (double)iPosY;
        this.iAncho = iAncho;
        this.iAlto = iAlto;
        this.aniPlayer = aniPlayer;
        this.dGravity = dGrav;
        this.dVelocity = dVel;
    }
    
    /**
     * setX
     * 
     * Metodo modificador usado para cambiar la posicion en x del character
     * 
     * @param iPosX es la <code>posicion en x</code> del character.
     * 
     */
    public void setX(int iPosX) {
        this.iPosX = (double)iPosX;
    }
    
    public void setX(double iPosX) {
        this.iPosX = iPosX;
    }

    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en x del character 
     * 
     * @return iPosX es la <code>posicion en x</code> del character.
     * 
     */
    public double getX() {
            return iPosX;
    }

    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en y del character 
     * 
     * @param iPosY es la <code>posicion en y</code> del character.
     * 
     */
    public void setY(int iPosY) {
            this.iPosY = (double)iPosY;
    }
    
    public void setY(double iPosY) {
            this.iPosY = iPosY;
    }

    /**
     * getY
     * 
     * Metodo de acceso que regresa la posicion en y del character 
     * 
     * @return iPosY es la <code>posicion en y</code> del character.
     * 
     */
    public double getY() {
        return iPosY;
    }

    /**
     * setImagen
     * 
     * Metodo modificador usado para cambiar el icono de imagen del character
     * tomandolo de un objeto imagen
     * 
     * @param imaImagen es la <code>imagen</code> del character.
     * 
     */
    public void setAnimation(Animation aniPlayer) {
        this.aniPlayer = aniPlayer;
    }

    /**
     * getImagen
     * 
     * Metodo de acceso que regresa la imagen que representa el icono del character
     * 
     * @return la imagen a partide del <code>icono</code> del objeto.
     * 
     */
    public Animation getAnimation() {
        return aniPlayer;
    }

    /**
     * getAncho
     * 
     * Metodo de acceso que regresa el ancho del character 
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

    public double getdVelocity() {
        return dVelocity;
    }

    public void setdVelocity(double dVelocity) {
        this.dVelocity = dVelocity;
    }

    public double getdGravity() {
        return dGravity;
    }

    public void setdGravity(double dGravity) {
        this.dGravity = dGravity;
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
        graGrafico.drawImage(aniPlayer.getImagen(), (int)iPosX, (int)iPosY, imoObserver);
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
        if (objObjeto instanceof Character) {
            // se regresa la comparación entre este objeto que invoca y el
            // objeto recibido como parametro
            Character charParam = (Character) objObjeto;
            return this.getX() ==  charParam.getX() && 
                    this.getY() == charParam.getY() &&
                    this.getAncho() == charParam.getAncho() &&
                    this.getAlto() == charParam.getAlto() &&
                    this.getAnimation() == charParam.getAnimation();
        }
        else {
            // se regresa un falso porque el objeto recibido no es tipo Base
            return false;
        }
    }
    
    /**
     * intersecta
     * 
     * Metodo que regresa true si los objetos intersectan
     * 
      * @return un valor <code>String</code> que representa al objeto
     * 
     */
    public boolean intersecta (Object objGizmo) {
        //valido si el objeto es Character
        if (objGizmo instanceof Gizmos) {
            //creo los rectangulos de ambos
            Rectangle rctEste = new Rectangle((int) this.getX(), (int) this.getY(), this.getAncho(), this.getAlto());
            Gizmos gzmTemp = (Gizmos) objGizmo;
            Rectangle rctParam = new Rectangle((int) gzmTemp.getX(), (int) gzmTemp.getY(), gzmTemp.getWidth(),
                    gzmTemp.getHeight());
            
            return rctEste.intersects(rctParam);
        }
        //si no entro entonces no es Character
        return false;
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
