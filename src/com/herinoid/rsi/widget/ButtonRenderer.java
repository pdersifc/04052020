/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.widget;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Herinoid
 */
public class ButtonRenderer extends DefaultTableCellRenderer {

        private ButtonPane buttonPane;

        public ButtonRenderer() {
            buttonPane = new ButtonPane();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                buttonPane.setBackground(table.getSelectionBackground());
            } else {
                buttonPane.setBackground(table.getBackground());
            }
            return buttonPane;
        }
    }
