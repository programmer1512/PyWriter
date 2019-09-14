package com.tobyjones.pywriter.contextmenus;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClassTxtClickListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e) {
        ClassTxtContextMenu menu = new ClassTxtContextMenu();
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}