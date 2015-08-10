/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachboss.mangadownloader.manager;

import com.bachboss.mangadownloader.manager.entity.History;
import com.bachboss.mangadownloader.manager.entity.ListHistory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Bach
 */
public class HistoryManager {

    private static ListHistory listHistory;
    private static final LazyUpdateTask updateTask = new LazyUpdateTask();

    private static boolean IS_LOADED = false;

    public static synchronized void lazyLoad() {
        if (!IS_LOADED) {
            System.out.println("Load History...");
            IS_LOADED = true;
            try {
                File fileXml = new File(ConfigManager.getCurrentInstance().getHistoryFile());
                if (!fileXml.exists() || fileXml.length() == 0) {
                    listHistory = new ListHistory();
                    saveToFile();
                } else {
                    // Load from file
                    JAXBContext jc = JAXBContext.newInstance(ListHistory.class);
                    Unmarshaller reader = jc.createUnmarshaller();
                    listHistory = (ListHistory) reader.unmarshal(fileXml);
                }
                //
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(updateTask, 1500, 1500);
            } catch (JAXBException ex) {
                Logger.getLogger(HistoryManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HistoryManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void saveToFile() throws JAXBException, IOException {
        File fileXml = new File(ConfigManager.getCurrentInstance().getHistoryFile());
        JAXBContext jc = JAXBContext.newInstance(ListHistory.class);
        Marshaller writer = jc.createMarshaller();

        writer.marshal(listHistory, new FileWriter(fileXml));
        System.out.println("Save History to file !");
    }

    public static List<History> getHistories() {
        lazyLoad();
        return listHistory;
    }

    public static boolean addHistory(History history) {
        lazyLoad();
        listHistory.add(history);
        markUpdate();
        return true;
    }

    public static void removeHistory(History history) {
        lazyLoad();
        boolean isEdited = false;
        for (int i = listHistory.size() - 1; i >= 0; i--) {
            History b = listHistory.get(i);
            if (b.getChapterUrl().equals(history.getChapterUrl())) {
                listHistory.remove(i);
                isEdited = true;
            }
        }
        if (isEdited) {
            markUpdate();
        }
    }

    public static void markUpdate() {
        lazyLoad();
        updateTask.isUpdate = true;
    }

    private static class LazyUpdateTask extends TimerTask {

        private boolean isUpdate = false;

        @Override
        public void run() {
            synchronized (this) {
                if (isUpdate) {
                    try {
                        saveToFile();
                    } catch (JAXBException ex) {
                        Logger.getLogger(HistoryManager.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(HistoryManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    isUpdate = false;
                }
            }
        }
    }
}
