public class Puntaje {
                
                private String nombre;
                private int puntaje;
                
                public Puntaje() {
                        nombre = "";
                        puntaje = 0;
                }
                
                public Puntaje(String nombre, int puntaje) {
                        this.nombre = nombre;
                        this.puntaje = puntaje;
                }
                
                public void setNombre(String nombre) {
                        this.nombre = nombre;
                }
                public void setPuntaje(int puntaje) {
                        this.puntaje = puntaje;
                }
                
                public String getNombre() {
                        return nombre;
                }
                
                public int getPuntaje() {
                        return puntaje;
                }
                
                @Override
                public String toString(){
                        return "" + getPuntaje() + "," + getNombre();
                }
        }