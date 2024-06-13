package fu.com.Window;

import fu.com.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateDataWindow extends JFrame {
    private UpdateDataWindow self = this;
    private MainWindow object;

    private JPanel panel;
    private JButton confirmButton;
    private JButton cancelButton;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private Table originalTable;
    private String tableName;

    public UpdateDataWindow(MainWindow object, String title, int width, int height, Table table, String tableName) {
        this.object = object;
        this.tableName = tableName;
        this.originalTable = table;
        init(title, width, height);
        setVisible(true);
    }

    public void init(String title, int width, int height) {
        setContentPane(panel);
        setTitle(title);
        setSize(width, height);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screensize.width - width) >> 1, (screensize.height - height) >> 1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getRowCount() != 0) {
                    if (table.getCellEditor() != null) {
                        table.getCellEditor().stopCellEditing();
                    }
                    object.updateData(tableName, originalTable, table);
                }
                self.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
            }
        });

        int columnCount = originalTable.getColumnCount();
        String[] headers = new String[columnCount];
        for (int i = 0; i < columnCount; ++i) {
            headers[i] = originalTable.getColumnName(i);
        }
        defaultTableModel = new DefaultTableModel(headers, 0);
        Object[] newData = new Object[columnCount];
        defaultTableModel.addRow(newData);
        table.setModel(defaultTableModel);
    }
}
