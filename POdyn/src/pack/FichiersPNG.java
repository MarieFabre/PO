package pack;
import java.awt.*; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.Locale;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * P2I 2012/2013 <br>
 * =====================================================
 *
 * <p>
 * @author Romuald Debruyne
 *
 * @version 1.0
 */
public class FichiersPNG extends FileFilter {
	// Classe servant a n'afficher que les fichiers PNG lors de la sauvegarde de l'image	
	public static boolean accepts(File f) {
		String n=f.getName().toLowerCase();
		int l=n.length();
		char ex0=n.charAt(l-4);
		char ex1=n.charAt(l-3);
		char ex2=n.charAt(l-2);
		char ex3=n.charAt(l-1);
		return (ex0=='.' && ex1=='p' && ex2=='n' && ex3=='g');
	}
	public boolean 	accept(File f) {
		if (f.isDirectory()) {
			return true; 
		}
		String n=f.getName().toLowerCase();
		int l=n.length();

		char ex0=n.charAt(l-4);
		char ex1=n.charAt(l-3);
		char ex2=n.charAt(l-2);
		char ex3=n.charAt(l-1);
		return (ex0=='.' && ex1=='p' && ex2=='n' && ex3=='g');
	}
	public String 	getDescription() {
		return "fichiers PNG";
	}
}
