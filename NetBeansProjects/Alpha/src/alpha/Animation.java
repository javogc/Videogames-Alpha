/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alpha;

import java.awt.Image;
import java.util.ArrayList;

/**
      La clase Animation maneja una serie de imágenes (frames) 
      y la cantidad de tiempo que se muestra cada cuadro.
*/
public class Animation{

         // Arreglo de objetos que guardará los frames de la animación
         private ArrayList frames;
         //Índice del cuadro en el que actualmente se encuentra la animación
         private int indexOfCurrentFrame;
         //Tiempo que la animación lleva corriendo.
         private long AnimationTime;
         //Duración total de la animación
         private long totalTime;

         /**
               Crea una nueva Animacion vacía
         */
         public Animation(){

                   //Crea el arreglo de objetos que guarda los frames.
                   frames = new ArrayList();
                   //Inicializa el tiempo total en 0.
                   totalTime = 0;
                   //Llama al método iniciar()
                   iniciar();
         }

         /**
               Añade un cuadro a la animación con la duración 
               indicada (tiempo que se muestra la imagen).
         */
         public synchronized void sumaCuadro(Image imagen, long duracion){

                   //Agrega la duración del cuadro a la duración de la animación
                   totalTime += duracion;
                   //Agrega el cuadro a la animación
                   frames.add(new cuadroDeAnimacion(imagen, totalTime));
         }

         // Inicializa la animación 
         public synchronized void iniciar(){

                  //Inicializa el tiempo de la animación en 0
                  AnimationTime = 0;
                  //Coloca el índice en el primer cuadro de la animación
                  indexOfCurrentFrame = 0;
         }

         /**
               Actualiza la imagen (cuadro) actual de la animación, si es necesario.
         */
         public synchronized void actualiza(long tiempoTranscurrido){
                   //Si la animación tiene más de un cuadro, se actualiza
                   if (frames.size() > 1){

                        //Se suma el tiempo transcurrido al tiempo total
                        AnimationTime += tiempoTranscurrido;
                        /**
                              Si el tiempo transcurrido es mayor al tiempo de la animación.
                        */
                        if (AnimationTime >= totalTime){

                            //Resetea el tiempo transcurrido
                            AnimationTime = AnimationTime % totalTime;
                            /**
                                  Posicional el índice en el primer cuadro
                            */                                
                            indexOfCurrentFrame = 0; 
                        }
                        /**
                              Cuando el tiempo transcurrido es mayor al tiempo que dura el cuadro, 
                              el índice aumenta y señala al siguiente cuadro
                        */
                        while (AnimationTime > getCuadro(indexOfCurrentFrame).tiempoFinal){
                                indexOfCurrentFrame++;
                        }
                   }
         }

         /**
               Captura la imagen actual de la animación. Regresa null si la animación 
               no tiene imágenes.
         */
         public synchronized Image getImagen(){

                    //Si la animación esta vacía
                    if (frames.size() == 0){

                        //Retorna nulo
                        return null;
                    }
                    //De lo contrario
                    else {

                         /**
                              Llama a método getCuadro para obtener la imagen 
                              del cuadro deseado
                         */
                         return getCuadro(indexOfCurrentFrame).imagen;
                    }
         }

         /**
               Retorna el cuadro que se encuentra en el índice indicado de la 
               animación
         */
         private cuadroDeAnimacion getCuadro(int i){

                     //Retorna el cuadro deseado
                     return (cuadroDeAnimacion)frames.get(i);
         }

         /**
               Clase cuadroDeAnimacion que permite crear los frames que 
               conformarán una animación.
         */
         public class cuadroDeAnimacion{

                   /**
                         Imagen que se mostrará en pantalla cuando el cuadro este activo
                   */
                   Image imagen;
                   /**
                         Tiempo en el que se realiza la transición del cuadro actual al siguiente
                   */
                   long tiempoFinal;

                   //Constructor vacío de objetos cuadroDeAnimacion
                   public cuadroDeAnimacion(){
                             //Guarda valor nulo en la imagen
                             this.imagen = null;
                             //Guarda en 0 el tiempo
                             this.tiempoFinal = 0;
                   }

                   /**
                          Constructor con parámetros de objetos cuadroDeAnimacion 
                          que recibe la imagen y el tiempo del cuadro.
                   */
                   public cuadroDeAnimacion(Image imagen, long tiempoFinal){

                             //Guarda la imagen del cuadro
                             this.imagen = imagen;
                             //Guarda el tiempo en que se da la transición al 
                             //siguiente cuadro
                             this.tiempoFinal = tiempoFinal;
                   }

                   //Obtiene la imagen de un cuadro
                   public Image getImagen(){

                             //Retorna la imagen
                             return imagen;
                   }

                   //Obtiene el tiempo en el que se realiza la transición al siguiente 
                   //cuadro
                   public long getTiempoFinal(){

                             //Retorna el tiempo
                             return tiempoFinal;
                   }

                   //Cambia la imagen del cuadro por una que recibe como parámetro
                   public void setImagen (Image imagen){

                             //Guarda la nueva imagen
                             this.imagen = imagen;
                   }

                   //Cambia el tiempo en el que cambia al siguiente cuadro
                   public void setTiempoFinal(long tiempoFinal){

                             //Guarda el nuevo tiempo
                             this.tiempoFinal = tiempoFinal;
                   }
         }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


