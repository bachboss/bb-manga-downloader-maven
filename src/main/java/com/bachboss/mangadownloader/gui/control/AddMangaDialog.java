/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.gui.control;

import com.bachboss.mangadownloader.database.Database;
import com.bachboss.mangadownloader.entity.Chapter;
import com.bachboss.mangadownloader.entity.Manga;
import com.bachboss.mangadownloader.entity.Server;
import com.bachboss.mangadownloader.faces.ServerManager;
import com.bachboss.mangadownloader.gui.model.ChapterModel;
import com.bachboss.mangadownloader.gui.model.MyColumnSorter;
import com.bachboss.mangadownloader.gui.model.MyTableModelSortable;
import com.bachboss.mangadownloader.gui.model.Watcher;
import com.bachboss.mangadownloader.ult.GUIUtilities;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Bach
 */
public class AddMangaDialog extends javax.swing.JDialog {

    private class RunnableImp implements Runnable {

        private Manga manga;

        public RunnableImp(Manga manga) {
            this.manga = manga;
        }

        @Override
        public void run() {
            modelChapter.clear();

            List<Chapter> lstChapter;
            try {
                lstChapter = manga.getServer().getMangaServer().getAllChapters(manga);
                modelChapter.addChapters(lstChapter);
                modelChapter.fireTableDataChanged();
                System.out.println("Loaded: " + lstChapter.size());
//                if (lstChapter == null || lstChapter.isEmpty()) {
//                    GUIUtilities.showLog("Loaded: 0 record(s) !");
//                }
                tblChapters.setEnabled(true);
            } catch (Exception ex) {
                Logger.getLogger(MangaDownloadGUI.class.getName()).log(Level.SEVERE, null, ex);
                GUIUtilities.showException(null, ex);
            }
            btnAddManga.setEnabled(true);
            btnCheckSupport.setEnabled(true);
        }
    }
    private ChapterModel modelChapter;
    private Watcher currentWatcher;
    private Manga currentManga;

    /**
     * Creates new form AddMangaDialog
     */
    public AddMangaDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Add Manga (Manually)");
        initComponents();
        init();
        initPopup();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popChapters = new javax.swing.JPopupMenu();
        mnWatcherViewInBroser = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        txtMangaUrl = new javax.swing.JTextField();
        btnCheckSupport = new javax.swing.JButton();
        lblSupport = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnAddManga = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtMangaName = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        sclChapter = new javax.swing.JScrollPane();
        tblChapters = new javax.swing.JTable();

        mnWatcherViewInBroser.setText("View In Browser");
        mnWatcherViewInBroser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnWatcherViewInBroserActionPerformed(evt);
            }
        });
        popChapters.add(mnWatcherViewInBroser);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Manga URL");

        txtMangaUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMangaUrlActionPerformed(evt);
            }
        });

        btnCheckSupport.setText("Test");
        btnCheckSupport.setPreferredSize(new java.awt.Dimension(100, 23));
        btnCheckSupport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckSupportActionPerformed(evt);
            }
        });

        lblSupport.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSupport.setForeground(new java.awt.Color(255, 0, 0));
        lblSupport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSupport.setText("Support");
        lblSupport.setToolTipText("");

        btnAddManga.setText("Add Manga");
        btnAddManga.setEnabled(false);
        btnAddManga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMangaActionPerformed(evt);
            }
        });
        jPanel1.add(btnAddManga);

        jLabel3.setText("Manga name");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Chapters"));

        tblChapters.setModel(new javax.swing.table.DefaultTableModel(
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
        sclChapter.setViewportView(tblChapters);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sclChapter, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sclChapter, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtMangaUrl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCheckSupport, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSupport, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMangaName)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMangaName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMangaUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckSupport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSupport))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMangaUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMangaUrlActionPerformed
        doCheckSupport();
    }//GEN-LAST:event_txtMangaUrlActionPerformed

    private void btnCheckSupportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckSupportActionPerformed
        doCheckSupport();
    }//GEN-LAST:event_btnCheckSupportActionPerformed

    private void mnWatcherViewInBroserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnWatcherViewInBroserActionPerformed
        int row = tblChapters.getSelectedRow();
        Chapter c = (Chapter) tblChapters.getValueAt(row, -1);
        if (c != null) {
            GUIUtilities.openLink(c.getUrl());
        }
    }//GEN-LAST:event_mnWatcherViewInBroserActionPerformed

    private void btnAddMangaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMangaActionPerformed
        addMangaToWatcher(currentManga);
    }//GEN-LAST:event_btnAddMangaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddManga;
    private javax.swing.JButton btnCheckSupport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblSupport;
    private javax.swing.JMenuItem mnWatcherViewInBroser;
    private javax.swing.JPopupMenu popChapters;
    private javax.swing.JScrollPane sclChapter;
    private javax.swing.JTable tblChapters;
    private javax.swing.JTextField txtMangaName;
    private javax.swing.JTextField txtMangaUrl;
    // End of variables declaration//GEN-END:variables

    private void doCheckSupport() {
        btnAddManga.setEnabled(false);
        btnCheckSupport.setEnabled(false);
        modelChapter.clear();
        String url = txtMangaUrl.getText();
        final Server mangaServer = ServerManager.getServerByUrl(url);
        if (mangaServer != null) {
            lblSupport.setText(mangaServer.getMangaServer().getSupportType().toString());
            currentManga = new Manga(mangaServer, txtMangaName.getText(), txtMangaUrl.getText());
            new Thread(new RunnableImp(currentManga)).start();
        } else {
            btnCheckSupport.setEnabled(true);
            lblSupport.setText("Not Support");
            GUIUtilities.showDialog(this, "Host not supported !");
        }
    }

    public void clearSelection() {
        currentManga = null;
        modelChapter.clear();
        btnCheckSupport.setEnabled(true);
        txtMangaName.setText("");
        txtMangaUrl.setText("");
    }

    void setWatcher(Watcher watcher) {
        this.currentWatcher = watcher;
    }

    private void init() {
        this.modelChapter = new ChapterModel();
        tblChapters.setModel(modelChapter);
        //<editor-fold>
        tblChapters.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblChapters.getColumnModel().getColumn(0).setMaxWidth(100);
        tblChapters.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblChapters.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblChapters.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblChapters.getColumnModel().getColumn(4).setPreferredWidth(350);
        tblChapters.setAutoCreateColumnsFromModel(false);
        //</editor-fold>
        addHeaderListener(tblChapters);
    }

    private void initPopup() {
        tblChapters.setComponentPopupMenu(popChapters);
        tblChapters.setInheritsPopupMenu(true);
    }

    private void addHeaderListener(JTable table) {
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JTable table = ((JTableHeader) evt.getSource()).getTable();
                TableColumnModel colModel = table.getColumnModel();

                // The index of the column whose header was clicked
                int vColIndex = colModel.getColumnIndexAtX(evt.getX());
                int mColIndex = table.convertColumnIndexToModel(vColIndex);

                // Return if not clicked on any column header
                if (vColIndex == -1) {
                    return;
                }

                // Determine if mouse was clicked between column heads
                Rectangle headerRect = table.getTableHeader().getHeaderRect(vColIndex);
                if (vColIndex == 0) {
                    headerRect.width -= 3;    // Hard-coded constant
                } else {
                    headerRect.grow(-3, 0);   // Hard-coded constant
                }
                if (!headerRect.contains(evt.getX(), evt.getY())) {
                    // Mouse was clicked between column heads
                    // vColIndex is the column head closest to the click

                    // vLeftColIndex is the column head to the left of the click
                    int vLeftColIndex = vColIndex;
                    if (evt.getX() < headerRect.x) {
                        vLeftColIndex--;
                    }
                    //System.out.println("Click on the middle");
                } else {
                    sortAllRowsBy((MyTableModelSortable) table.getModel(), mColIndex);
                }
            }
        });
    }

    private void sortAllRowsBy(MyTableModelSortable model, int colIndex) {
        if (model.isSortable(colIndex)) {
            List data = model.getData();
            model.swithSortOrder();
            Collections.sort(data, new MyColumnSorter(colIndex, model));
            ((AbstractTableModel) model).fireTableStructureChanged();
        }
    }

    private void addMangaToWatcher(Manga m) {
        List<Manga> lstManga = currentWatcher.getLstManga();
        System.out.println(m.getMangaName() + "\t" + m.getHashId());
        if (!lstManga.contains(m)) {
            Database.createManga(m);
            Database.addMangaToWatcher(m.getHashId(), currentWatcher.getId());
        }
        System.out.println("Current manga in watcher " + currentWatcher.getName() + " = " + currentWatcher.getMangaCount());
        this.setVisible(false);
    }
}
