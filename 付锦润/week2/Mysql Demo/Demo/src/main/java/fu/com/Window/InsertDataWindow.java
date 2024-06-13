package fu.com.Window;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertDataWindow extends JFrame {
    private InsertDataWindow self = this;
    private MainWindow object;
    private Statement mysqlStatement;

    private String tableName;
    private JPanel panel;
    private JButton addButton;
    private JButton submitButton;
    private JButton cancelButton;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private int columnCount;

    public InsertDataWindow(MainWindow object, String title, int width, int height, String name, Statement mysqlStatement) {
        this.object = object;
        this.tableName = name;
        this.mysqlStatement = mysqlStatement;
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

        String sql = "select * from " + tableName + ";";
        try (ResultSet resultSet = mysqlStatement.executeQuery(sql)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; ++i) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }
            defaultTableModel = new DefaultTableModel(columnNames, 0);
            table.setModel(defaultTableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; ++i) {
                    rowData[i] = null;
                }
                defaultTableModel.addRow(rowData);
                table.setModel(defaultTableModel);
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getRowCount() != 0) {
                    if (table.getCellEditor() != null) {
                        table.getCellEditor().stopCellEditing();
                    }
                    object.insertData(tableName, table);
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
    }
}
