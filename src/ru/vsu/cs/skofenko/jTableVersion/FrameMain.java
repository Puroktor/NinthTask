package ru.vsu.cs.skofenko.jTableVersion;

import ru.vsu.cs.skofenko.CommonModule;
import ru.vsu.cs.util.JTableUtils;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FrameMain extends JFrame {

    private JPanel panelMain;
    private JTable table1;
    private JButton loadInputButton;
    private JButton writeInputButton;
    private JButton solveButton;
    private JButton saveButton;
    private JTable tableOut;

    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;

    public FrameMain() {
        this.setTitle("Task 8");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(table1, 40, false, true, false, true);
        JTableUtils.initJTableForArray(tableOut, 40, false, true, false, false);
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserOpen.addChoosableFileFilter(filter);

        fileChooserSave = new JFileChooser();
        fileChooserSave.setCurrentDirectory(new File("."));
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        loadInputButton.addActionListener(e -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    List<Integer> list = CommonModule.readListFromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(table1, list.stream().mapToInt(i -> i).toArray());
                }
            } catch (Exception exception) {
                SwingUtils.showErrorMessageBox(exception);
            }
        });
        solveButton.addActionListener(e -> {
            try {
                int[] inArray = JTableUtils.readIntArrayFromJTable(table1);
                List<Integer> list = CommonModule.createNewList(CommonModule.intArrayToList(inArray));
                JTableUtils.writeArrayToJTable(tableOut, CommonModule.intListToArray(list));
            } catch (Exception exception) {
                SwingUtils.showErrorMessageBox(exception);
            }
        });
        saveButton.addActionListener(e -> {
            try {
                if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    String file = fileChooserSave.getSelectedFile().getPath();
                    if (!file.toLowerCase().endsWith(".txt")) {
                        file += ".txt";
                    }
                    int[] outArray = JTableUtils.readIntArrayFromJTable(tableOut);
                    CommonModule.writeListToFile(file, CommonModule.intArrayToList(outArray));
                }
            } catch (Exception exception) {
                SwingUtils.showErrorMessageBox(exception);
            }
        });
        writeInputButton.addActionListener(e -> {
            try {
                if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    String file = fileChooserSave.getSelectedFile().getPath();
                    if (!file.toLowerCase().endsWith(".txt")) {
                        file += ".txt";
                    }
                    int[] outArray = JTableUtils.readIntArrayFromJTable(table1);
                    CommonModule.writeListToFile(file, CommonModule.intArrayToList(outArray));
                }
            } catch (Exception exception) {
                SwingUtils.showErrorMessageBox(exception);
            }
        });
    }
}
