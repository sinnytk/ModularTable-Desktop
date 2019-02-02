/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sinny
 */
import mdlaf.MaterialLookAndFeel;
import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialColors;
import mdlaf.utils.MaterialFonts;
import mdlaf.shadows.DropShadowBorder;
import java.io.*;
import java.util.*;
import java.awt.Color;
import javax.swing.*;
public class MainWindow extends javax.swing.JFrame {
private Timetable timetable;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(53,59,72));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileSelector = new javax.swing.JButton();
        logo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(53, 59, 72));
        setFont(new java.awt.Font("Consolas", 0, 10)); // NOI18N
        setMinimumSize(new java.awt.Dimension(900, 600));
        setName("Starting"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        fileSelector.setFont(new java.awt.Font("Coolvetica Rg", 0, 48)); // NOI18N
        fileSelector.setForeground(new java.awt.Color(245, 246, 250));
        fileSelector.setText("Open Timetable");
        fileSelector.setToolTipText("Click and select the timetable file");
        fileSelector.setBorderPainted(false);
        fileSelector.setContentAreaFilled(false);
        fileSelector.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fileSelector.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                fileSelectorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                fileSelectorMouseExited(evt);
            }
        });
        fileSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSelectorActionPerformed(evt);
            }
        });

        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo.setIcon(new javax.swing.ImageIcon("D:\\University\\Semester 3\\DSA\\Project\\GUI\\mainwindow.png")); // NOI18N
        logo.setToolTipText("");
        logo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(logo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(273, 273, 273)
                        .addComponent(fileSelector)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(fileSelector)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
                .addComponent(logo))
        );

        fileSelector.getAccessibleContext().setAccessibleName("FileSelector");
        logo.getAccessibleContext().setAccessibleName("background");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSelectorActionPerformed

        final JFileChooser fc = new JFileChooser("D:\\University\\Semester 3\\DSA\\Project\\ModularTable");
        //In response to a button click:
        int returnVal = fc.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            try
            {
                timetable = new Timetable(new FileInputStream(file));
                this.dispose();
                new Menu(timetable).setVisible(true);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
            
        }
    }//GEN-LAST:event_fileSelectorActionPerformed

    private void fileSelectorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileSelectorMouseEntered
        fileSelector.setForeground(new Color(113, 128, 147));        // TODO add your handling code here:
    }//GEN-LAST:event_fileSelectorMouseEntered

    private void fileSelectorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileSelectorMouseExited
        fileSelector.setForeground(new Color(245, 246, 250)); // TODO add your handling code here:
    }//GEN-LAST:event_fileSelectorMouseExited

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
                UIManager.setLookAndFeel(new MaterialLookAndFeel());
            }
        catch(UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fileSelector;
    private javax.swing.JLabel logo;
    // End of variables declaration//GEN-END:variables
}