/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jugar;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author 21a03
 */
public class Backgrounds extends JPanel{
      Backgrounds(int num){
        String ubicacionFoto ="";
        switch(num){  
            case 1:    
                ubicacionFoto = "images/bgLogin.jpeg";
            break;
            
            case 2:
                ubicacionFoto = "images/bgTienda.jpg";
                
                break;
            case 3:
                ubicacionFoto = "images/bgWallpaper.jpg";
                
                break;
            default:
                ubicacionFoto = "images/bgWallpaper.jpg";
                break;
        }
        ImageIcon background = new ImageIcon(ubicacionFoto);
        Image img = background.getImage();
        Image temp=img.getScaledInstance(1024,768,Image.SCALE_SMOOTH);
        background = new ImageIcon(temp);
        JLabel back=new JLabel(background);
        back.setBounds(0,0,1024, 768);
      }
}
