/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.gui.control;

import com.bachboss.mangadownloader.database.Database;
import com.bachboss.mangadownloader.entity.Manga;
import com.bachboss.mangadownloader.gui.model.MangaTableModel;
import com.bachboss.mangadownloader.gui.model.Watcher;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.sort.RowFilters;

/**
 *
 * @author Bach
 */
public class SearchMangaDialog extends javax.swing.JDialog {

    private TableRowSorter<MangaTableModel> sorter;
    private Watcher watcher;
    private MangaTableModel tableModel;

    /**
     * Creates new form SearchMangaDialog
     */
    public SearchMangaDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Search Manga");
        initComponents();
        {
            List<Manga> lstTemp;
            lstTemp = Database.getAllMangaFromFile();
            tableModel = new MangaTableModel(lstTemp);
            tblMangas.setModel(tableModel);
            sorter = new TableRowSorter<MangaTableModel>(tableModel);
            addListenerToSearchBox();
        }
    }

    private void addListenerToSearchBox() {
        Component[] arrC = pnlSearch.getComponents();
        for (Component c : arrC) {
            if (c instanceof JTextField) {
                c.addKeyListener(new KeyListener() {
//<editor-fold>

                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        applySearch();
                    }
//</editor-fold>                    
                });
            }
        }

    }

    private void applySearch() {
        Pattern pattern = pnlSearch.getPattern();
        if (pattern != null) {
            tblMangas.setRowSorter(sorter);
            sorter.setRowFilter(RowFilters.regexFilter(pattern, 0));
        } else {
            tblMangas.setRowSorter(null);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlgLoading = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMangas = new org.jdesktop.swingx.JXTable();
        pnlSearch = new org.jdesktop.swingx.JXSearchPanel();
        jPanel1 = new javax.swing.JPanel();
        btnAddManga = new javax.swing.JButton();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Loading Data from local Database...");

        javax.swing.GroupLayout dlgLoadingLayout = new javax.swing.GroupLayout(dlgLoading.getContentPane());
        dlgLoading.getContentPane().setLayout(dlgLoadingLayout);
        dlgLoadingLayout.setHorizontalGroup(
            dlgLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgLoadingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        dlgLoadingLayout.setVerticalGroup(
            dlgLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgLoadingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblMangas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblMangas);

        btnAddManga.setText("Add Manga");
        btnAddManga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMangaActionPerformed(evt);
            }
        });
        jPanel1.add(btnAddManga);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 283, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddMangaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMangaActionPerformed
        List<Manga> lstManga = watcher.getLstManga();
        int[] arrTemp = tblMangas.getSelectedRows();
        ArrayList<Manga> lstTemp = new ArrayList<Manga>(arrTemp.length);
        for (int i : arrTemp) {
            Manga m = (Manga) tblMangas.getValueAt(i, -1);
            System.out.println(m.getMangaName() + "\t" + m.getHashId());
            if (!lstManga.contains(m)) {
                lstTemp.add(m);
            }
        }
        if (!lstTemp.isEmpty()) {
            Database.addMangasToWatcher(lstTemp, watcher.getId());
        }
        System.out.println("Current manga in watcher " + watcher.getName() + " = " + watcher.getMangaCount());
        this.setVisible(false);
    }//GEN-LAST:event_btnAddMangaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddManga;
    private javax.swing.JDialog dlgLoading;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXSearchPanel pnlSearch;
    private org.jdesktop.swingx.JXTable tblMangas;
    // End of variables declaration//GEN-END:variables

    public void setWatcher(Watcher currentWatcher) {
        this.watcher = currentWatcher;
    }

    void clearSelection() {
        tblMangas.clearSelection();
        Component[] arrC = pnlSearch.getComponents();
        for (Component c : arrC) {
            if (c instanceof JTextField) {
                ((JTextField) c).setText("");
            }
        }
    }
}
