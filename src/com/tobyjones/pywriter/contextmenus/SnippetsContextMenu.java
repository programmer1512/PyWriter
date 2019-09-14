package com.tobyjones.pywriter.contextmenus;

import com.tobyjones.pywriter.PyWriter;

import javax.swing.*;
import java.io.File;

/**
 * Created by Toby Jones on 07/09/2019 for PyWriter.
 */
public class SnippetsContextMenu extends JPopupMenu {

    private JMenuItem deleteBtn;

    public SnippetsContextMenu() {
        initDecMenus();
    }

    private void initDecMenus() {
        deleteBtn = new JMenuItem("Delete Snippet");
        deleteBtn.addActionListener(e -> {
            for (int i = 0; i < PyWriter.codeSnippet.filesToListArrayList.size(); i++) {
                File file = PyWriter.codeSnippet.filesToListArrayList.get(PyWriter.codeSnippet.displayList.getSelectedIndex());
                file.delete();

                PyWriter.codeSnippet.snips.remove(PyWriter.codeSnippet.displayList.getSelectedIndex());
                PyWriter.codeSnippet.filesToListArrayList.remove(PyWriter.codeSnippet.displayList.getSelectedIndex());
            }
        });
    }
}
