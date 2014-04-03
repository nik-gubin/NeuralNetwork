/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.File;

/**
 *
 * @author Николай
 */
public class MyFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public String getDescription() {
            // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "Нейронная сеть (*.cvs, *.xml)";
    }

    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getAbsolutePath().endsWith(".csv")|| f.getAbsolutePath().endsWith(".xml");
    }
}
