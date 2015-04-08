/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package breakingbricks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.JFrame;

/**
 *
 * @author Natalia Garcia & Any Landeros
 */
public class BreakingBricks extends JFrame implements Runnable, KeyListener {
        
        /**
         *
         * @globalVariables
         */
        private Image imgFondo;
        private Image imgVida;
        private Base basBala;
        private Base basBarrita;
        private LinkedList<Base> lklMeth;
        private boolean bGameOver;
        private boolean bPausa;
        private int iVidas;
        private double iX;
        private double iY;
        private int iContador;
        private boolean bEmpieza;
        
        private int iPintar;
        
        /* objetos para manejar el buffer del Applet y este no parpadee */
        private Image    imaImagenApplet;   // Imagen a proyectar en Applet
        private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
        private int iTiempo;
        private boolean bPintaChoca;
        
        
        BreakingBricks() {
                init();
                start();
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        
        /**
         * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
         * En este metodo se inizializan las variables o se crean los objetos
         * y se ejecuta una sola vez cuando inicia el <code>Applet</code>.
         **/
        public void init() {
                iPintar = 0;
                iTiempo = 1;
                bPintaChoca = false;
                
                iX = 4;
                iY = -4;
                
                //el tamaño de la ventana es de 1000 x 700
                setSize (1000, 700);
                
                lklMeth = new LinkedList();
                iContador = 0;
                bEmpieza = false;
                
                //se cargan las imagenes de los personajes, vidas, y fondo
                Image imgBala, imgMeth, imgBar;
                imgBala = Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource ("hank.png"));
                imgBar = Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource ("bar.png"));
                imgVida = Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource ("vida.png"));
                imgFondo = Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource ("fondo.jpg"));
                
                //se inicializan las variables del tipo Base
                basBala = new Base (getWidth()/2, (getHeight() - 90 - 120), 60, 75, imgBala);
                basBarrita = new Base ((getWidth()/2) - (200/2), getHeight() - 90, 200, 100, imgBar);
                
                //se incializa la lista encadenada
                Base basTemp;
                for (int iI = 1; iI <= 4; iI++) {
                        
                        if (iI <= 4) {
                                imgMeth = Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource (iI+".png"));
                        }
                        else {
                                imgMeth = Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource (iI-4+".png"));
                        }
                        for (int iJ = 1; iJ <= 10; iJ++) {
                                basTemp = new Base (50 + iJ * 75, 30 + iI * 75, 60, 70, imgMeth);
                                lklMeth.add(basTemp);
                                iContador++;
                        }
                }
                iVidas = 5;
                bGameOver = false;
                bPausa = false;
                
                addKeyListener(this);
        }
        /**
         * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
         * En este metodo se crea e inicializa el hilo para la animacion este metodo es
         * llamado despues del init o cuando el usuario visita otra pagina y luego
         * regresa a la pagina en donde este <code>Applet</code>.
         **/
        private void start () {
                // Declaras un hilo
                Thread th = new Thread (this);
                // Empieza el hilo
                th.start ();
        }
        
        @Override
        public void paint (Graphics graGrafico){
                // Inicializan el DoubleBuffer
                if (imaImagenApplet == null){
                        imaImagenApplet = createImage (this.getSize().width, this.getSize().height);
                        graGraficaApplet = imaImagenApplet.getGraphics ();
                }
                
                // Actualiza la imagen de fondo.
                graGraficaApplet.setColor(getBackground());
                graGraficaApplet.fillRect(0, 0, this.getSize().width, this.getSize().height);
                
                // Actualiza el Foreground.
                graGraficaApplet.setColor (getForeground());
                paint1(graGraficaApplet);
                
                // Dibuja la imagen actualizada
                graGrafico.drawImage (imaImagenApplet, 0, 0, this);
        }
        
        public void paint1 (Graphics graDibujo) {
                
                if(imgFondo != null) {
                        graDibujo.drawImage (imgFondo, 0, 0, this);
                }
                
                if (basBala != null && basBarrita != null && lklMeth != null) {
                        //Dibuja la imagen de la pelotita en el JFrame
                        basBala.paint(graDibujo, this);
                        
                        //Dibuja la imagen de la barrita en el JFrame
                        basBarrita.paint(graDibujo, this);
                        int iI = 0;
                        for(Base basMeth: lklMeth) {
                                iI++;
                                basMeth.paint(graDibujo, this);
                                if (bPintaChoca) {
                                        System.out.println(iTiempo);
                                        if (iPintar == iI) {
                                                int iTemp = (iI/10) + 1;
                                                        
                                                        if (iTemp <= 4) {
                                                                basMeth.setImagen(Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource ("1-2.png")));
                                                        }
                                                        else {
                                                                basMeth.setImagen(Toolkit.getDefaultToolkit ().getImage (this.getClass ().getResource ("1-2.png")));
                                                                }
                                                if (iTiempo < 20) {
                                                        basMeth.paint(graDibujo, this);
                                                }
                                                //cuando termine se cambia la direccion del personaje y se apaga el boleano para decir que
                                                //ya no se esta pintando la imagen de colision
                                                else {
                                                        bPintaChoca = false;
                                                }
                                        }
                                }
                                
                                
                        }
                }
                // sino se ha cargado se dibuja un mensaje
                else {
                        //Da un mensaje mientras se carga el dibujo
                        System.out.println("No se cargo la imagen..");
                }
                if (imgVida != null) {
                        if(iVidas >= 5) {
                                //Dibuja la imagen de las vidas
                                graDibujo.drawImage (imgVida, 200, 25, this);
                        }
                        if(iVidas >= 4) {
                                //Dibuja la imagen de las vidas
                                graDibujo.drawImage (imgVida, 300, 25, this);
                        }
                        if(iVidas >= 3) {
                                //Dibuja la imagen de las vidas
                                graDibujo.drawImage (imgVida, 400, 25, this);
                        }
                        if(iVidas >= 2) {
                                //Dibuja la imagen de las vidas
                                graDibujo.drawImage (imgVida, 500, 25, this);
                        }
                        if(iVidas >= 1) {
                                //Dibuja la imagen de las vidas
                                graDibujo.drawImage (imgVida, 600, 25, this);
                        }
                        else {
                                bGameOver = true;
                        }
                }
                
                if (bGameOver) {
                        //si tiene cero vidas se dibuja la imagen de game over
                        Image imgGameOver;
                        imgGameOver = Toolkit.getDefaultToolkit ().getImage
                                                                        (this.getClass ().getResource ("over.jpg"));
                        
                        graDibujo.drawImage(imgGameOver, (this.getWidth()/2) - (imgGameOver.getWidth(this)/2),
                                (this.getHeight()/2) - (imgGameOver.getHeight(this)/2), this);
                }
                
                if (bPausa) {
                        Image imgPausa;
                        imgPausa = Toolkit.getDefaultToolkit ().getImage
                                                                                (this.getClass ().getResource ("pausa.jpg"));
                        
                        graDibujo.drawImage(imgPausa, (this.getWidth()/2) - (imgPausa.getWidth(this)/2),
                                (this.getHeight()/2) - (imgPausa.getHeight(this)/2), this);
                }
                
                if (!bEmpieza) {
                        Image imgInstr;
                        imgInstr = Toolkit.getDefaultToolkit ().getImage
                                                                                (this.getClass ().getResource ("instr.jpg"));
                        
                        graDibujo.drawImage(imgInstr, (this.getWidth()/2) - (imgInstr.getWidth(this)/2),
                                (this.getHeight()/2) - (imgInstr.getHeight(this)/2), this);
                }
                
                
        }
        
        public void actualiza () {
                basBala.setX(basBala.getX() + iX);
                basBala.setY(basBala.getY() + iY);
        }
        
        public void angulo() {
                iY *= -1;
                double iPosX = basBala.getX() - basBarrita.getX();
                double iPercent = (iPosX / (basBarrita.getAncho() - basBala.getAncho())) - 0.5;
                iX = iPercent * 5;
        }
        
        public void checaColision () {
                if (basBala.intersecta(basBarrita)) {
                        angulo();
                }
                
                boolean bColisione = false;
                
                int iI = 0;
                for(Base basMeth: lklMeth) {
                        iI++;
                        if (basBala.intersecta(basMeth)) {
                                if(!bPintaChoca) {
                                        bPintaChoca = true;
                                        iTiempo = 0;
                                }
                                iPintar = iI;
                                basMeth.setX (getWidth());
                                basMeth.setY (getHeight());
                                iContador--;
                                if (!bColisione) {
                                        iY *= -1;
                                        iX *= -1;
                                        bColisione = true;
                                }
                        }
                        if (iContador == 0) {
                                bGameOver = true;
                        }
                }
                
                if (basBala.getX() <= 0) {
                        iX *= -1;
                }
                
                if (basBala.getX() >= (getWidth() - basBala.getAncho())) {
                        iX *= -1;
                }
                
                if (basBala.getY() <= 20) {
                        iY *= -1;
                }
                
                if (basBala.getY() >= (getHeight() - basBala.getAlto())) {
                        iY *= -1;
                        iVidas--;
                }
                
                if (basBarrita.getX() < 0) {
                        basBarrita.setX (basBarrita.getX() + 10);
                }
                
                if(basBarrita.getX() > (getWidth () - basBarrita.getAncho())) {
                        basBarrita.setX (basBarrita.getX() - 10);
                }
        }
        
        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) {
                // TODO code application logic here
                BreakingBricks juego = new BreakingBricks();
                juego.setVisible(true);
        }
        
        @Override
        /**
         * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
         * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se incrementa
         * la posicion en x, se repinta el <code>Applet</code> y luego manda a dormir
         * el hilo.
         **/
        public void run() {
                /* mientras dure el juego, se actualizan posiciones de jugadores
                se checa si hubo colisiones para desaparecer jugadores o corregir
                movimientos y se vuelve a pintar todo
                */
                while (true) {
                        while (!bGameOver) {
                                if(bEmpieza) {
                                        iTiempo ++;
                                        if(!bPausa) {
                                                actualiza();
                                                checaColision();
                                        }
                                }
                                
                                repaint();
                                try	{
                                        // El thread se duerme.
                                        Thread.sleep (20);
                                }
                                catch (InterruptedException iexError) {
                                        System.out.println("Hubo un error en el juego " +
                                                iexError.toString());
                                }
                        }
                        repaint();
                }
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        basBarrita.setX(basBarrita.getX() - 20);
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        basBarrita.setX(basBarrita.getX() + 20);
                }
                
                else if (e.getKeyCode() == KeyEvent.VK_P) {
                        bPausa = !bPausa;
                }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (bGameOver) {
                                init();
                        }
                        if (!bEmpieza) {
                                bEmpieza = true;
                        }
                }
        }
        
}
