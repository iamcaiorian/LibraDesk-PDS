/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 *
 * @author CAIO
 */
public class EntrarController implements IController {
    
    @FXML
    protected void btEntrar(ActionEvent e) throws Exception {
        Main.changeScreen("acervo");
    }
    
    @FXML
    protected void btNovoCadastro(ActionEvent e)throws Exception {
        Main.changeScreen("novoUsuario");
    
    }
}
