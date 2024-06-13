package fu.com.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectDataWindow extends JFrame {
    private MainWindow object;
    private SelectDataWindow self = this;
    private JPanel panel;
    private JButton queryButton;
    private JButton cancelButton;
    private JPanel buttonPanel;
    private JPanel imfPanel;
    private JTextArea textArea;

    public SelectDataWindow(MainWindow object, String title, int width, int height) {
        this.object = object;
        init(title, width, height);

        int selectedIndex = object.getTabbedPanel().getSelectedIndex();
        String pageTitle = object.getTabbedPanel().getTitleAt(selectedIndex);
        Connection connection = object.getMysqlConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(self, "请先连接数据库！", "提示", JOptionPane.PLAIN_MESSAGE);
            dispose();
            return;
        }
        try {
            ResultSet resultSet = connection.getMetaData().getTables(null, null, pageTitle, null);
            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(self, "请先选择表格！", "提示", JOptionPane.PLAIN_MESSAGE);
                dispose();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 参数
        textArea.setText("select * from " + pageTitle);
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(self, "若要修改查询后的表，请确保查询列表中有主键或含unique属性的列！", "警告", JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    object.selectData(pageTitle, textArea.getText());
                    self.dispose();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
            }
        });
        setVisible(true);
    }

    public void init(String title, int width, int height) {
        setContentPane(panel);
        setTitle(title);
        setSize(width, height);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screensize.width - width) >> 1, (screensize.height - height) >> 1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Font font = new Font("微软雅黑", Font.PLAIN, 28);
        textArea.setFont(font);
    }
}
