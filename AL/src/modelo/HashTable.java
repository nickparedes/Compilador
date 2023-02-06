/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;






/**
 *
 * @author axell
 */
public class HashTable{
    private Nodo[] tabla;
    private int numeroElementos;
    private int dimension;
    private int numeroColisiones;
    
    public HashTable(int dimension){
        tabla = new Nodo[dimension];
        numeroElementos = 0;
        numeroColisiones = 0;
        this.dimension = dimension;
    }

    public int getNumeroElementos() {
        return numeroElementos;
    }
    
    
    
    public int hash(String clave){
        int c = 0;
        for(int i=0; i<clave.length(); i++)
            c += clave.charAt(i);
        return (int)(Math.PI*c)%dimension;
    }
    
    private boolean isInLista(Nodo inicial, String identificador){
        Nodo aux = inicial;
        while(aux != null){
            //System.out.println("====FUNCUION AUXILIAR");
            //System.out.println("Comparando: " + aux.getDato().getIdentificador() + "   ==   " + identificador);
            if(aux.getDato().getIdentificador().equals(identificador)){
                return true;
            }
            aux = aux.siguiente;
        }
        return false;
    }
    
    public boolean addDato(String tipo, String identificador,  String atributo, String acceso, String nivel){
        //System.out.println("dato agregado: " + identificador);
        
        int pos = hash(identificador);
        //System.out.println("Hash generado: " + pos);
        Nodo nuevo = new Nodo(new Dato(tipo, identificador,  atributo, acceso, nivel));
        if(tabla[pos] == null){
            tabla[pos] = nuevo;
            numeroElementos++;
        }else{
            //Caso en el que existe colision
            System.out.println("Colision: ");
            System.out.println("comparando: " + tabla[pos].valor.getIdentificador() + "   ==   " + identificador);
            if(isInLista(tabla[pos], identificador))
                return false;
            else{
                //REsolucion se colisiones
                numeroColisiones++;
                //Resolución de colisión por encadenamiento
                Nodo aux = tabla[pos], anterior;
                while(aux.siguiente != null){
                    aux = aux.siguiente;
                }
                nuevo.anterior = aux;
                aux.siguiente = nuevo;
                numeroElementos++;
                
            }
        }
        
        return true;
    }
    
    
    public Nodo searchDato(String identificador){
        Nodo result = null;
        int pos = hash(identificador);
        Nodo aux = tabla[pos];
        
        if(aux != null){
            if(aux.valor.getIdentificador().equals(identificador)){
                result = aux;
            }else{
                //System.out.println("Buscando:  " + codigo);
                while(aux != null){
                    if(aux.valor.getIdentificador().equals(identificador)){
                        result = aux;
                        break;
                    }
                    aux = aux.siguiente;
                }
            }
        }
        
        return result;
    }
    

    public void imprimirDatos(){
        for(int i=0; i<this.tabla.length; i++){
            imprimirListaEnlazada(tabla[i]);
        }
    }
    
    private void imprimirListaEnlazada(Nodo inicial){
        while(inicial != null){
            //System.out.println(inicial.valor + "    " + inicial.valor.nivel + "    " +inicial.valor.tipo + "    acceso: " + inicial.valor.acceso + "    atributo: "  + inicial.valor.atributo);
            inicial = inicial.siguiente;
        }
    }
    
    
    public class Dato{
        private String tipo;
        private String atributo;
        private String identificador;
        private String nivel;
        private String acceso;
        public Dato(String tipo, String identificador, String nivel){
            this.identificador = identificador;
            this.tipo = tipo;
            this.nivel = nivel;
            this.atributo = "local";
            
        }
        
        public Dato(String tipo, String identificador,  String atributo, String acceso, String nivel){
            this(tipo, identificador, nivel);
            this.atributo = atributo;
            this.acceso = acceso;
        }

        public String getTipo() {
            return tipo;
        }

        public String getAtributo() {
            return atributo;
        }

        public String getIdentificador() {
            return identificador;
        }

        public String getNivel() {
            return nivel;
        }
        
        public String toString(){
            return identificador;                                                   
        }

        public String getAcceso() {
            return acceso;
        }
        
        
    }
    
    public class Nodo{
        Nodo siguiente;
        Nodo anterior;
        Dato valor;
        
        public Nodo(Dato valor){
            anterior = null;
            siguiente = null;
            this.valor = valor;
        }
        
        public Dato getDato(){
            return valor;
        }
    }

}
