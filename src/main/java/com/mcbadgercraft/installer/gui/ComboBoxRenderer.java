package com.mcbadgercraft.installer.gui;

import com.mcbadgercraft.installer.packs.PacksFile;
import io.github.thefishlive.minecraft.profiles.AuthProfile;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class ComboBoxRenderer extends BasicComboBoxRenderer {
    public ComboBoxRenderer() {
        super();
        setOpaque(false);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        if (value instanceof AuthProfile) {
            setText(((AuthProfile) value).getUsername());
        } else if (value instanceof PacksFile.PackInfo) {
            setText(((PacksFile.PackInfo) value).getName());
        }

        return this;
    }
}
