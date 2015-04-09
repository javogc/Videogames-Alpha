/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package alpha;

import java.awt.Graphics;
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
        
        int iLevel;
        
        Image imgBackground;
        Image imgTree;
        Image imgApple;
        Image imgPig;
        Image imgBullet;
        Image imgEgg;
        Image imgRock;
        Image imgWaterDrop;
        Image imgGasoline;
        Image imgTrash;
        Image imgInstructions;
        Image imgMenu;
        Image imgLogo;
        Image imgSlogan;
        
        private Animation aniPlayer1;
        private Animation aniPlayer2;
        private Animation aniPlayer3;
        private Animation aniPlayer4;
        private long actualTime;
        private long initialTime;
        int posX;
        int posY;
        
        Character ctrPlayer;
        Gizmos gzmToken;
        Gizmos gzmObstacle;
        
        /* objetos para manejar el buffer del Applet y este no parpadee */
        private Image    imaImagenApplet;   // Imagen a proyectar en Applet
        private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
        
        Alpha() {
                init();
                start();
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        
        public void init() {
                
                setSize (1000, 700);
                
                iLevel = 1;
                
                imgBackground = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("background1.jpg"));
                
                aniPlayer1 = new Animation();
                
                Image [] imgCharacter1 = new Image [8];
                
                for (int i = 0; i < 8; i++) {
                        imgCharacter1[i] = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                                getResource ("monitohacha" + (i+1) + ".png"));
                        aniPlayer1.sumaCuadro(imgCharacter1[i], 100);
                }
                
                aniPlayer2 = new Animation();
                
                Image [] imgCharacter2 = new Image [8];
                
                for (int i = 0; i < 8; i++) {
                        imgCharacter2[i] = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                                getResource ("monitopistola" + (i+1) + ".png"));
                        aniPlayer2.sumaCuadro(imgCharacter2[i], 100);
                }
                
                aniPlayer3 = new Animation();
                
                Image [] imgCharacter3 = new Image [8];
                
                for (int i = 0; i < 8; i++) {
                        imgCharacter3[i] = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                                getResource ("monitocubeta" + (i+1) + ".png"));
                        aniPlayer3.sumaCuadro(imgCharacter3[i], 100);
                }
                
                aniPlayer4 = new Animation();
                
                Image [] imgCharacter4 = new Image [8];
                
                for (int i = 0; i < 8; i++) {
                        imgCharacter4[i] = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                                getResource ("monitomoto" + (i+1) + ".png"));
                        aniPlayer4.sumaCuadro(imgCharacter4[i], 100);
                }
                
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
                
                imgLogo = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("logo.png"));
                
                imgSlogan = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("slogan.png"));
                
                imgMenu = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("mundo.png"));
                
                imgInstructions = Toolkit.getDefaultToolkit ().getImage (this.getClass ().
                        getResource ("instrucciones.png"));
                
                gzmObstacle = new Gizmos (400, 400, 200, 250, imgTree);
                
                gzmToken = new Gizmos (800, 500, 100, 100, imgApple);
                
                posX = 50;
                posY = 400;
                
                
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
        /**
         * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
         * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se incrementa
         * la posicion en x, se repinta el <code>Applet</code> y luego manda a dormir
         * el hilo.
         **/
        public void run() {
                actualTime = System.currentTimeMillis();
                /* mientras dure el juego, se actualizan posiciones de jugadores
                se checa si hubo colisiones para desaparecer jugadores o corregir
                movimientos y se vuelve a pintar todo
                */
                while (true) {
                        update();
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
        }
        
        public void update() {
                long elapsedTime = System.currentTimeMillis() - actualTime;
                actualTime += elapsedTime;
                aniPlayer1.actualiza(elapsedTime);
                aniPlayer2.actualiza(elapsedTime);
                aniPlayer3.actualiza(elapsedTime);
                aniPlayer4.actualiza(elapsedTime);
                
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
        
        public void paint1(Graphics g) {
                
                switch(iLevel){
                        case 1:
                                if(imgLogo != null){
                                        g.drawImage(imgLogo, 0, 0, this);
                                }
                                break;
                        case 2:
                                if(imgSlogan != null){
                                        g.drawImage(imgSlogan, 0, 0, this);
                                }
                                break;
                        case 3:
                                if(imgMenu != null){
                                        g.drawImage(imgMenu, 0, 0, this);
                                }
                                break;
                        case 4:
                                if(imgInstructions != null){
                                        g.drawImage(imgInstructions, 0, 0, this);
                                }
                                break;
                                
                        case 5:
                                if(imgBackground != null){
                                        g.drawImage(imgBackground, 0, 0, this);
                                }
                                // Muestra en pantalla el cuadro actual de la animación
                                if (aniPlayer1 != null) {
                                        g.drawImage(aniPlayer1.getImagen(), posX, posY, this);
                                }
                                if(gzmToken != null && gzmObstacle != null){
                                        gzmToken.paint(g, this);
                                        gzmObstacle.paint(g, this);
                                }
                                break;
                                
                        case 6:
                                gzmObstacle.setImagen(imgPig);
                                gzmToken.setImagen(imgEgg);
                                if(imgBackground != null){
                                        g.drawImage(imgBackground, 0, 0, this);
                                }
                                // Muestra en pantalla el cuadro actual de la animación
                                if (aniPlayer2 != null) {
                                        g.drawImage(aniPlayer2.getImagen(), posX, posY, this);
                                }
                                if(gzmToken != null && gzmObstacle != null){
                                        gzmToken.paint(g, this);
                                        gzmObstacle.paint(g, this);
                                }
                                break;
                        case 7:
                                gzmObstacle.setImagen(imgRock);
                                gzmToken.setImagen(imgWaterDrop);
                                gzmToken.setY(200);
                                if(imgBackground != null){
                                        g.drawImage(imgBackground, 0, 0, this);
                                }
                                if (aniPlayer3 != null) {
                                        g.drawImage(aniPlayer3.getImagen(), posX, posY, this);
                                }
                                if(gzmToken != null && gzmObstacle != null){
                                        gzmToken.paint(g, this);
                                        gzmObstacle.paint(g, this);
                                }
                                break;
                        case 8:
                                gzmObstacle.setImagen(imgGasoline);
                                gzmToken.setImagen(imgTrash);
                                gzmToken.setY(350);
                                if(imgBackground != null){
                                        g.drawImage(imgBackground, 0, 0, this);
                                }
                                if (aniPlayer3 != null) {
                                        g.drawImage(aniPlayer3.getImagen(), posX, posY, this);
                                }
                                if(gzmToken != null && gzmObstacle != null){
                                        gzmToken.paint(g, this);
                                        gzmObstacle.paint(g, this);
                                }
                                break;
                                
                        case 9:
                                iLevel--;
                                break;
                }
                
                // if (bGameOver) {
                //         //si tiene cero vidas se dibuja la imagen de game over
                //         Image imgGameOver;
                //         imgGameOver = Toolkit.getDefaultToolkit ().getImage
                //                                                         (this.getClass ().getResource ("over.jpg"));
                
                //         g.drawImage(imgGameOver, (this.getWidth()/2) - (imgGameOver.getWidth(this)/2),
                //                 (this.getHeight()/2) - (imgGameOver.getHeight(this)/2), this);
                // }
                
                // if (bPausa) {
                //         Image imgPausa;
                //         imgPausa = Toolkit.getDefaultToolkit ().getImage
                //                                                                 (this.getClass ().getResource ("pausa.jpg"));
                
                //         g.drawImage(imgPausa, (this.getWidth()/2) - (imgPausa.getWidth(this)/2),
                //                 (this.getHeight()/2) - (imgPausa.getHeight(this)/2), this);
                // }
                
                // if (!bEmpieza) {
                //         Image imgInstr;
                //         imgInstr = Toolkit.getDefaultToolkit ().getImage
                //                                                                 (this.getClass ().getResource ("instr.jpg"));
                
                //         g.drawImage(imgInstr, (this.getWidth()/2) - (imgInstr.getWidth(this)/2),
                //                 (this.getHeight()/2) - (imgInstr.getHeight(this)/2), this);
                // }
        }
        @Override
        public void keyTyped(KeyEvent e) {
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        iLevel ++;
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
