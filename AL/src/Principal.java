
import controlador.CtrlVista;
import modelo.ADP.Parser;
import modelo.Sistema;
import vista.Vista;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author axell
 */
public class Principal {
    public static void main(String [] args){
        Vista vs = new Vista();
        CtrlVista ctrl= new CtrlVista(vs);
        
    }
}
