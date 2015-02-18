/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareajf;

import java.applet.Applet;
import java.applet.AudioClip;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;
/**
 *
 * @author javo
 */
public class TareaJF extends JFrame implements KeyListener {
         private final int iMAXANCHO = 10; // maximo numero de personajes por ancho
    private final int iMAXALTO = 8;  // maxuimo numero de personajes por alto
    private Base basPrincipal;         // Objeto principal
    private Base basMalo;         // Objeto malo
    private int iDireccion;         //Variable que actualiza la posicion. 
    private int iPuntos;            //Variable para acumular puntos
    private int iVidas;            //Variable que acumula vidas.
    
    private boolean bColision;     //Variable que ayuda a checar colisiones
    private boolean bPausa;        //Variable que checa si el juego esta pausad
    private boolean bGameOver;
    
    /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
    private AudioClip adcSonidoChimpy;   // Objeto sonido de Chimpy
    
    private LinkedList<Base> lklChimpy;
    private LinkedList<Base> lklDiddy;
    
       public TareaJF(){
             //Define el título de la ventana
                setTitle("JFrame Ultravionce");
                //Define la operación que se llevará acabo cuando la ventana sea cerrada.
                // Al cerrar, el programa terminará su ejecución
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
                // hago el applet de un tamaño 500,500
        setSize(800,500);
        
        //poner valor inicial de vidas y score
        iVidas = 0;
        iPuntos = 0;
             
	URL urlImagenPrincipal = this.getClass().getResource("juanito.gif");
                
        // se crea el objeto para principal 
	basPrincipal = new Base(0, 0, getWidth() / iMAXANCHO,
                getHeight() / iMAXALTO,
                Toolkit.getDefaultToolkit().getImage(urlImagenPrincipal));

        // se posiciona a principal  en la esquina superior izquierda del Applet 
        basPrincipal.setX(getWidth() / 2);
        basPrincipal.setY(getHeight() / 2);
        
        lklChimpy = new LinkedList();
        
        URL urlImagenChimpy = this.getClass().getResource("chimpy.gif");
        
        URL urlImagenDiddy = this.getClass().getResource("diddy.gif");
        
         // se crean muchos changos
        for (int iI = 1; iI <= 5; iI++) {
            
            // se crea un chango
            basMalo = new Base(0,0,0,0,
                    Toolkit.getDefaultToolkit().getImage(urlImagenChimpy));   
            
            //Se posiciona al chimpy en algun lugar random de la derecha
            basMalo.setX(getWidth() / 2);
            basMalo.setY(getHeight() / 2);
            
            //Definir velocidad de lente de un numero entre 2 y 4
            basMalo.setVelocidad((int)(Math.random() * (3) + 2));
            
            //se agrega a la lista
            lklChimpy.add(basMalo);
        }
        
        lklDiddy = new LinkedList();
        
        for (int iI = 1; iI <= 5; iI++){
            
            basMalo = new Base(0,0,0,0,
                    Toolkit.getDefaultToolkit().getImage(urlImagenDiddy));
            
            //Se posiciona al diddy en algun lugar random de la izq
            basMalo.setX((int) (Math.random() * (getWidth() - basMalo.getAncho())));
            basMalo.setY((-1)*((int) (Math.random() * 401)));
            
            //Definir velocidad de lente de un numero entre 2 y 4
            basMalo.setVelocidad((int)(Math.random() * (3) + 2));
            
            //se agrega a la lista
            lklChimpy.add(basMalo);
        }
        
        //se inicializa la variable de colisiones
        bColision = false;
        
        
        
        // defino la imagen del malo
	URL urlImagenMalo = this.getClass().getResource("chimpy.gif");
        
        // se crea el objeto para malo 
        int iPosX = (iMAXANCHO - 1) * getWidth() / iMAXANCHO;
        int iPosY = (iMAXALTO - 1) * getHeight() / iMAXALTO;        
	basMalo = new Base(iPosX,iPosY, getWidth() / iMAXANCHO,
                getHeight() / iMAXALTO,
                Toolkit.getDefaultToolkit().getImage(urlImagenMalo));
        
        URL urlSonidoChimpy = this.getClass().getResource("monkey2.wav");
        adcSonidoChimpy = Applet.newAudioClip (urlSonidoChimpy);
        adcSonidoChimpy.play();
        
        addKeyListener(this);
    }
        public void checaColision(){

        if(basPrincipal.getX()<= 0 || basPrincipal.getX() >= this.getWidth() - basPrincipal.getAncho()){
            
            bColision = true;
        }
        
        else if (basPrincipal.getY() <= 0 || basPrincipal.getY() >= 
                this.getHeight() - basPrincipal.getAlto()){
            bColision = true;
        }
        else{
            bColision = false;
        }
          
        
        
        int iPosX;
        int iPosY;
        for(Base basMalo : lklChimpy){
            
            iPosX = -(int)(Math.random())* getWidth();
            iPosY = (int)(Math.random())*
                    (this.getHeight() - basMalo.getAlto());
            
            
            
            if(basMalo.intersecta(basPrincipal)){
                
                iPuntos--;
                
                basMalo.setX(iPosX);
                basMalo.setY(iPosY);
                
                if(iPuntos == 0){
                    bGameOver = true;
                }
            }
            if(basMalo.getX()+ basMalo.getAncho() >= this.getWidth()){
                iPuntos++;
                
                basMalo.setX(iPosX);
                basMalo.setY(iPosY);
            }
        }
    }
	
       
       public void paint(Graphics g){

                //Color blanco
                g.setColor(Color.WHITE);
                //Dibuja un rectángulo blanco para el fondo
                g.fillRect(0, 0, getWidth(), getHeight());
                //Color negro
                g.setColor(Color.BLACK);
                //Define la fuente con la cual se desplegará el mensaje
                g.setFont(new Font("Serif", Font.BOLD, 18));
                //Dibuja el mensaje en la ventana
                g.drawString("ULTRAVIOLENCE", this.getSize().width / 2 -60, 
                                                this.getSize().height / 2 + 9);
                
                if (imaImagenApplet == null){
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }

        // Actualiza la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("Ciudad.png");
        Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
         graGraficaApplet.drawImage(imaImagenFondo, 0, 0, getWidth(), getHeight(), this);

        // Actualiza el Foreground.
        graGraficaApplet.setColor (getForeground());
        paint(graGraficaApplet);

        // Dibuja la imagen actualizada
        g.drawImage (imaImagenApplet, 0, 0, this);
        }
       
    public void paint1(Graphics g){
        // si la imagen ya se cargo
        if (basPrincipal != null) {
                //Dibuja la imagen de principal en el Applet
                basPrincipal.paint(g, this);
                
                  for (Object objBase: lklChimpy) {
                    Base basObjeto = (Base) objBase;
                    
                    //Dibuja la imagen de mickey en la posicion actualizada
                    g.drawImage(basObjeto.getImagen(), basObjeto.getX(),
                    basObjeto.getY(), this);
                }
                  
                 for (Object objBase: lklDiddy) {
                    Base basObjeto = (Base) objBase;
                    
                    //Dibuja la imagen de mickey en la posicion actualizada
                    g.drawImage(basObjeto.getImagen(), basObjeto.getX(),
                    basObjeto.getY(), this);
                }
                
                
        } // sino se ha cargado se dibuja un mensaje 
        else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
        }
        
        g.setColor(Color.yellow);
        g.drawString(""+iPuntos+" pts", 670 , 100);
        g.setFont(new Font("Helvetica", Font.ITALIC, 28));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Crea un nuevo objeto JFrameHolaMundo
                TareaJF holaMundo = new TareaJF();
                //Despliega la ventana en pantalla al hacerla visible
                holaMundo.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
