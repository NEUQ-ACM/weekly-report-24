package fu.com.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsWindow extends JFrame{
    private OptionsWindow self = this;
    private MainWindow mainWindow;

    private JPanel panel;
    private JTextField username;
    private JPasswordField password;
    private JPanel buttonPanel;
    private JButton confirmButton;
    private JButton cancelButton;

    public OptionsWindow(MainWindow mainWindow, String title, int width, int height) {
        this.mainWindow = mainWindow;
        init(title, width, height);
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
                if (username.getText().isEmpty() || password.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(self, "账户或密码不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (mainWindow.connectToMysql()) {
                    JOptionPane.showMessageDialog(self, "连接成功！", "连接提示", JOptionPane.PLAIN_MESSAGE);
                    self.dispose();
                    mainWindow.setMenuLock(true);
                } else {
                    JOptionPane.showMessageDialog(self, "连接失败！", "连接提示", JOptionPane.PLAIN_MESSAGE);
                    mainWindow.setMenuLock(false);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
            }
        });
    }

    public void display() {
        setVisible(true);
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        char[] passwordArrays = password.getPassword();
        StringBuilder passwordString = new StringBuilder();
        for (char i : passwordArrays) {
            passwordString.append(i);
        }
        return passwordString.toString();
    }
}
