/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alpha;

/**
 *
 * @author javo
 */

import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;


public class Gizmos {
    
    private double iX;     //posicion en x.       
    private double iY;     //posicion en y.
    private int iWidth; //ancho del objeto
    private int iHeight; //largo del objeto
    private Image imgImage;	//imagen.
    private int iPoints;
    private AudioClip auSound;
    
    public Gizmos (double iX, double iY, int iWidth, int iHeight, Image imgImage, int iPoints, AudioClip auSound) {
            this.iX = iX;
            this.iY = iY;
            this.iWidth = iWidth;
            this.iHeight = iHeight;
            this.imgImage = imgImage;
            this.iPoints = iPoints;
            this.auSound = auSound;
    }
    
     /**
     * getAuSound
     * 
     * Metodo de acceso que regresa el sonido del Gizmo 
     * 
     * @return auSound es el <code>sonido</code> del Gizmo.
     * 
     */
    
     public AudioClip getAuSound() {
        return auSound;
    }

       /**
     * setAuSound
     * 
     * Metodo modificador usado para cambiar el sonido del Gizmo
     * 
     * @param auSound es el <code>sonido x</code> del Gizmo.
     * 
     */
     
    public void setAuSound(AudioClip auSound) {
        this.auSound = auSound;
    }
    
    
    /**
     * getPoints
     * 
     * Metodo de acceso que regresa la posicion en x del Gizmo 
     * 
     * @return iPoints es la <code>posicion en x</code> del ogIZMO.
     * 
     */
    public int getPoints() {
            return iPoints;
    }
    
    /**
     * setX
     * 
     * Metodo modificador usado para cambiar la posicion en x del Gizmo
     * 
     * @param iX es la <code>posicion en x</code> del Gizmo.
     * 
     */
    public void setX(double iX) {
        this.iX = iX;
    }

    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en x del Gizmo 
     * 
     * @return iX es la <code>posicion en x</code> del ogIZMO.
     * 
     */
    public double getX() {
            return iX;
    }

    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en y del Gizmo 
     * 
     * @param iY es la <code>posicion en y</code> del Gizmo.
     * 
     */
    public void setY(double iY) {
            this.iY = iY;
    }

    /**
     * getY
     * 
     * Metodo de acceso que regresa la posicion en y del Gizmo 
     * 
     * @return posY es la <code>posicion en y</code> del Gizmo.
     * 
     */
    public double getY() {
        return iY;
    }

    /**
     * setImagen
     * 
     * Metodo modificador usado para cambiar el icono de imagen del Gizmo
     * tomandolo de un objeto imagen
     * 
     * @param imgImage es la <code>imagen</code> del objeto.
     * 
     */
    public void setImagen(Image imgImage) {
        this.imgImage = imgImage;
    }

    /**
     * getImagen
     * 
     * Metodo de acceso que regresa la imagen que representa el icono del objeto
     * 
     * @return la imagen a partide del <code>icono</code> Gizmo.
     * 
     */
    public Image getImagen() {
        return imgImage;
    }

    /**
     * getWidth
     * 
     * Metodo de acceso que regresa el ancho del icono 
     * 
     * @return un <code>entero</code> que es el ancho de la imagen del Gizmo.
     * 
     */
    public int getWidth() {
        return iWidth;
    }

    /**
     * getHeight
     * 
     * Metodo que  da el alto del icono 
     * 
     * @return un <code>entero</code> que es el alto de la imagen del gIZMO.
     * 
     */
    public int getHeight() {
        return iHeight;
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
        graGrafico.drawImage(getImagen(), (int) getX(), (int) getY(), getWidth(), getHeight(), imoObserver);
    }

    
}
