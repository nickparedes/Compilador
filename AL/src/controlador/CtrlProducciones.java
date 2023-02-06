/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelo.util.Excel;
import vista.VistaProducciones;

/**
 *
 * @author axell
 */
public class CtrlProducciones {
    VistaProducciones vs;
    DefaultTableModel modelo;
    
    public CtrlProducciones(VistaProducciones vs, DefaultTableModel modelo){
        this.vs = vs;
        this.modelo = modelo;
        
        this.vs.table.setModel(modelo);
        propiedades();
        eventos();
    }
    
    private void propiedades(){
        
        vs.setVisible(true);
        vs.setLocationRelativeTo(null);
        vs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vs.setTitle("Tabla de producciones");
        vs.exportar.setBackground(new Color(21,73, 46));
        TableColumn columna;
        columna = vs.table.getColumnModel().getColumn(0);
        columna.setPreferredWidth(2);
        //columna.setMaxWidth(2);
        //columna.setMinWidth(2);
        vs.table.getColumnModel().getColumn(1);
        columna.setPreferredWidth(50);
        columna.setMaxWidth(50);
        columna.setMinWidth(50);
    }
    
    private void eventos(){
        vs.exportar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Excel xlsx = new Excel("tabla de producciones", new File("."));
                    xlsx.generar(vs.table);
                    JOptionPane.showMessageDialog(null, "Excel generado con exito");
                    Desktop.getDesktop().open(new File(".\\tabla de producciones.xlsx"));
                } catch (IOException ex) {
                    
                }
            }
            
        });
    }
    
    
}
