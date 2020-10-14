/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.widget;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Herinoid
 */
public class ButtonPane extends JPanel {

        private JButton hapus;
        private JButton edit;
        private String state;

        public ButtonPane() {
            setLayout(new GridBagLayout());
            hapus = new JButton("Hapus");
            hapus.setActionCommand("hapus");
            edit = new JButton("Edit");
            edit.setActionCommand("edit");

            add(hapus);
            add(edit);

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                }
            };

            hapus.addActionListener(listener);
            edit.addActionListener(listener);
        }

        public void addActionListener(ActionListener listener) {
            hapus.addActionListener(listener);
            edit.addActionListener(listener);
        }

        public String getState() {
            return state;
        }
    }
