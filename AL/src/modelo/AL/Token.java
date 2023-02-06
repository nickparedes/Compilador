/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.AL;

import modelo.Sistema;
import static modelo.Sistema.cantidadIDs;

/**
 *
 * @author axell
 */
public class Token {
    private String tipo;
    private String lexema;
    private int id;
    private int orden;
    private static int cantTokens = 0;
    private String nivel;
    public static int buffer = 1000;
    private Atributo atributos;
    private String mask;
    
    public Token(String lexemaEspecial){
        this.lexema = lexemaEspecial;
        this.id = -1;
        this.orden = -1;
        this.tipo = "ESPECIAL";
        this.atributos = null;
        this.mask = lexemaEspecial;
    }
    
    public Token(String lexema, int estado){
        this.lexema = lexema;
        this.id = estado;
        this.orden = retornarVal(estado);
        this.tipo = generarTipo(estado);
        this.atributos = null;
    }
    
    private String generarTipo(int estado){
        return Sistema.terminales[estado];
    }

    public String getLexema() {
        return lexema;
    }
    
    
    
    public String toString(){
        return this.lexema;
    }
    
    private int retornarVal(int estado){
          cantidadIDs[estado] = cantidadIDs[estado]+1;
          return cantidadIDs[estado];
    }

    public String getTipo() {
        return tipo;
    }

    public String getPalabra() {
        return lexema;
    }

    public int getId() {
        return id;
    }

    public int getOrden() {
        return orden;
    }

    public static int getCantTokens() {
        return cantTokens;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    
    public static void main(String[] args){
        
    }

    public String getNivel() {
        return nivel;
    }

    public Atributo getAtributos() {
        return atributos;
    }

    public void setAtributos(Atributo atributos) {
        this.atributos = atributos;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public static void setCantTokens(int cantTokens) {
        Token.cantTokens = cantTokens;
    }

    public static void setBuffer(int buffer) {
        Token.buffer = buffer;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getMask() {
        return mask;
    }
    
    public boolean compararNivel(String otroNivel){
        
        String []nivelesActuales = this.nivel.split("\\.");
        String []nivelesComparados = otroNivel.split("\\.");
        int []v1 = {Integer.parseInt(nivelesActuales[0]), Integer.parseInt(nivelesActuales[1]), Integer.parseInt(nivelesActuales[2])};
        int []v2 = {Integer.parseInt(nivelesComparados[0]), Integer.parseInt(nivelesComparados[1]), Integer.parseInt(nivelesComparados[2])};
        if(v1[0] == v2[0] && v1[1] >= v2[1] && v1[2] >= v2[2]){
           return true; 
        }
        return false;
    }

}
