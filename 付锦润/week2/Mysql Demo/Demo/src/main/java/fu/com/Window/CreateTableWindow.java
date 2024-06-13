package fu.com.Window;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateTableWindow extends JFrame {
    private CreateTableWindow self = this;
    private MainWindow object;

    private Map<String, String> field;
    private JPanel panel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton submitButton;
    private JTextField tabelName;
    private JTextField fieldName;
    private JComboBox fieldType;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private String databaseName;

    public CreateTableWindow(MainWindow object, String title, int width, int height, String name) {
        this.object = object;
        this.databaseName = name;
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
        table.setRowSelectionAllowed(true);
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(new Object[]{"字段名", "字段类型"});
        Object[] unique = {"id", "int auto_increment primary key not null"};
        defaultTableModel.addRow(unique);
        table.setModel(defaultTableModel);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fn = fieldName.getText();
                String ft = (String) fieldType.getSelectedItem();
                Object[] rowData = {fn, ft};
                defaultTableModel.addRow(rowData);
                table.setModel(defaultTableModel);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                defaultTableModel.removeRow(row);
                table.setModel(defaultTableModel);
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabelName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(self, "表格名不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (defaultTableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(self, "字段列表不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                object.createTable(self);
                self.dispose();
            }
        });
    }

    public String getName() {
        return tabelName.getText();
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public Map<String, String> getField() {
        field = new HashMap<>();
        int count = defaultTableModel.getRowCount();
        for (int i = 0; i < count; ++i) {
            String fn = defaultTableModel.getValueAt(i, 0).toString();
            String ft = defaultTableModel.getValueAt(i, 1).toString();
            field.put(fn, ft);
        }
        return field;
    }
}
