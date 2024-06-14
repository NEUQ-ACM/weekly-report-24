package fu.com.Window;

import fu.com.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends JFrame{
    private MainWindow self = this;
    private OptionsWindow optionsWindow;

    private JPanel MainWindow;
    private JTabbedPane tabbedPanel;
    private JMenuBar menuBar;
    private JMenu[] menu;
    private JMenuItem[] menuItems;
    private Map<String, Table> map;
    private Connection mysqlConnection;
    private Statement mysqlStatement;
    private String showTableSQL;

    public MainWindow(String title, int width, int height) {
        init(title, width, height);
        setVisible(true);
    }

    public void init(String title, int width, int height) {
        setContentPane(MainWindow);
        setTitle(title);
        setSize(width, height);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screensize.width - width) >> 1, (screensize.height - height) >> 1);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(self,
                        "确定要关闭吗？",
                        "关闭程序",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    disconnectFromMysql(false);
                    optionsWindow.dispose();
                    self.dispose();
                    System.exit(0);
                }
            }
        });
        menuBar = new JMenuBar();
        menu = new JMenu[2];
        menu[0] = new JMenu("菜单");
        menu[1] = new JMenu("查询");
        menuItems = new JMenuItem[3];
        menuItems[0] = new JMenuItem("连接数据库");
        menuItems[1] = new JMenuItem("断开数据库");
        menuItems[2] = new JMenuItem("查询当前表格");
        setMenuLock(false);
        menu[0].add(menuItems[0]);
        menu[0].add(menuItems[1]);
        menu[1].add(menuItems[2]);
        menuBar.add(menu[0]);
        menuBar.add(menu[1]);
        setJMenuBar(menuBar);
        optionsWindow = new OptionsWindow(self, "设置", 300, 150);
        menuItems[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsWindow.display();
            }
        });
        menuItems[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnectFromMysql(true);
            }
        });
        menuItems[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectDataWindow selectDataWindow = new SelectDataWindow(self, "查询语句", 600, 500);
            }
        });
        tabbedPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu jPopupMenu = new JPopupMenu();
                    JMenuItem[] jMenuItems = new JMenuItem[2];
                    jMenuItems[0] = new JMenuItem("关闭");
                    jMenuItems[1] = new JMenuItem("取消");
                    jMenuItems[0].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // 获取选择的标签页及其名字并删除
                            int index = tabbedPanel.getSelectedIndex();
                            String name = tabbedPanel.getTitleAt(index);
                            tabbedPanel.remove(index);
                            map.remove(name);
                        }
                    });
                    jMenuItems[1].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jPopupMenu.setVisible(false);
                        }
                    });
                    jPopupMenu.add(jMenuItems[0]);
                    jPopupMenu.add(jMenuItems[1]);
                    // 获取标签页
                    int index = tabbedPanel.indexAtLocation(e.getX(), e.getY());
                    // 判断是否存在标签页
                    if (0 <= index && index < tabbedPanel.getTabCount()) {
                        // 定位标签页
                        tabbedPanel.setSelectedIndex(index);
                        String title = tabbedPanel.getTitleAt(index);
                        if (title.equals("数据库集")) {
                            jMenuItems[0].setEnabled(false);
                        }
                        jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });
        map = new HashMap<>();
        map.put("databaseList", createNewPage("数据库集", "databaseList", true));
        map.get("databaseList").addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    Point point = e.getPoint();
                    Table table = map.get("databaseList");
                    int row = table.rowAtPoint(point);
                    int column = table.columnAtPoint(point);
                    table.setRowSelectionInterval(row, row);
                    table.setColumnSelectionInterval(column, column);
                    Object object = table.getValueAt(row, column);

                    JPopupMenu jPopupMenu = new JPopupMenu();
                    JMenuItem[] jMenuItems = new JMenuItem[3];
                    jMenuItems[0] = new JMenuItem("查看");
                    jMenuItems[1] = new JMenuItem("删除");
                    jMenuItems[2] = new JMenuItem("取消");
                    jMenuItems[0].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (row != -1 && column != -1) {
                                String name = object.toString();
                                clearPage();
                                selectDatabase(name);
                            }
                        }
                    });
                    jMenuItems[1].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (row != -1 && column != -1) {
                                String name = object.toString();
                                dropDatabase(name);
                            }
                        }
                    });
                    jMenuItems[2].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jPopupMenu.setVisible(false);
                        }
                    });
                    jPopupMenu.add(jMenuItems[0]);
                    jPopupMenu.add(jMenuItems[1]);
                    jPopupMenu.add(jMenuItems[2]);
                    jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public JTabbedPane getTabbedPanel() {
        return tabbedPanel;
    }

    public Connection getMysqlConnection() {
        return mysqlConnection;
    }

    public void setMenuLock(boolean isConnected) {
        if (isConnected) {
            menuItems[0].setEnabled(false);
            menuItems[1].setEnabled(true);
        } else {
            menuItems[0].setEnabled(true);
            menuItems[1].setEnabled(false);
        }
    }

    public boolean connectToMysql() {
        String mysqlURL = "jdbc:mysql://localhost:3306/";
        String username = optionsWindow.getUsername();
        String password = optionsWindow.getPassword();
        // 加载驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 访问数据库连接对象
        try {
            mysqlConnection = DriverManager.getConnection(mysqlURL, username, password);
            mysqlStatement = mysqlConnection.createStatement();
            String sql = "show databases";
            executeSQL("databaseList", "databaseList", sql, true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void disconnectFromMysql(boolean show) {
        try {
            setMenuLock(false);
            if (mysqlStatement != null) {
                mysqlStatement.close();
            }
            if (mysqlConnection != null) {
                mysqlConnection.close();
            }
            if (show) {
                JOptionPane.showMessageDialog(self, "已与数据库断开连接！", "连接提示", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void executeSQL(String executor, String type, String sql, boolean show) {
        if (!show) {
            try {
                mysqlStatement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        try (ResultSet mysqlResultSet = mysqlStatement.executeQuery(sql)) {
            if (!map.containsKey(executor)) {
                map.put(executor, createNewPage(executor, type, true));
                if (type.equals("database")) {
                    map.get(executor).addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                Point point = e.getPoint();
                                Table table = map.get(executor);
                                int row = table.rowAtPoint(point);
                                int column = table.columnAtPoint(point);
                                table.setRowSelectionInterval(row, row);
                                Object object = table.getValueAt(row, column);

                                JPopupMenu jPopupMenu = new JPopupMenu();
                                JMenuItem[] jMenuItems = new JMenuItem[4];
                                jMenuItems[0] = new JMenuItem("查看");
//                                jMenuItems[1] = new JMenuItem("修改");
                                jMenuItems[1] = new JMenuItem("删除");
                                jMenuItems[2] = new JMenuItem("取消");
                                jMenuItems[0].addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (row != -1 && column != -1) {
                                            String name = object.toString();
                                            showTable(name, true);
                                        }
                                    }
                                });
//                                jMenuItems[1].addActionListener(new ActionListener() {
//                                    @Override
//                                    public void actionPerformed(ActionEvent e) {
//                                        if (row != -1 && column != -1) {
//                                            String name = object.toString();
//                                            alterTable(executor, name);
//                                        }
//                                    }
//                                });
                                jMenuItems[1].addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (row != -1 && column != -1) {
                                            String name = object.toString();
                                            dropTable(executor, name);
                                        }
                                    }
                                });
                                jMenuItems[2].addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        jPopupMenu.setVisible(false);
                                    }
                                });
                                jPopupMenu.add(jMenuItems[0]);
                                jPopupMenu.add(jMenuItems[1]);
                                jPopupMenu.add(jMenuItems[2]);
//                                jPopupMenu.add(jMenuItems[3]);
                                jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                            }
                        }
                    });
                } else if (type.equals("data")) {
                    map.get(executor).addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                Point point = e.getPoint();
                                Table table = map.get(executor);
                                int row = table.rowAtPoint(point);
                                int column = table.columnAtPoint(point);
                                int minSelectionIndex = table.getSelectionModel().getMinSelectionIndex();
                                int maxSelectionIndex = table.getSelectionModel().getMaxSelectionIndex();
                                if (row < minSelectionIndex || row > maxSelectionIndex) {
                                    table.setRowSelectionInterval(row, row);
                                    Object object = table.getValueAt(row, column);
                                }

                                JPopupMenu jPopupMenu = new JPopupMenu();
                                JMenuItem[] jMenuItems = new JMenuItem[3];
                                jMenuItems[0] = new JMenuItem("修改");
                                jMenuItems[1] = new JMenuItem("删除");
                                jMenuItems[2] = new JMenuItem("取消");
                                jMenuItems[0].addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        // 获取字段名及需要修改的数据
                                        UpdateDataWindow updateDataWindow = new UpdateDataWindow(self, "修改数据", 500, 200, table, executor);
                                    }
                                });
                                jMenuItems[1].addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        deleteData(executor, table);
                                    }
                                });
                                jMenuItems[2].addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        jPopupMenu.setVisible(false);
                                    }
                                });
                                jPopupMenu.add(jMenuItems[0]);
                                jPopupMenu.add(jMenuItems[1]);
                                jPopupMenu.add(jMenuItems[2]);
                                jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                            }
                        }
                    });
                }
            }
            Table table = map.get(executor);
            // 获取表头
            ResultSetMetaData metaData = mysqlResultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; ++i) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }
            // DefaultTableModel
            DefaultTableModel defaultTableModel = new DefaultTableModel(columnNames, 0);
            // 获取每行数据并追加至DefaultTableModel
            while (mysqlResultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; ++i) {
                    row[i] = mysqlResultSet.getObject(i + 1);
                }
                defaultTableModel.addRow(row);
            }
            // 设置DefaultTableModel
            table.setModel(defaultTableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Table createNewPage(String title, String type) {
        int tabCount = tabbedPanel.getTabCount();
        for (int i = 0; i < tabCount; ++i) {
            String selectedTitle = tabbedPanel.getTitleAt(i);
            if (title.equals(selectedTitle)) {
                // 已经存在
                return null;
            }
        }
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON3) {
                    return;
                }
                // 根据类型设置弹出的菜单
                if (type.equals("databaseList")) {
                    JPopupMenu jPopupMenu = new JPopupMenu();
                    JMenuItem[] jMenuItems = new JMenuItem[2];
                    jMenuItems[0] = new JMenuItem("新建数据库");
                    jMenuItems[1] = new JMenuItem("取消");
                    jMenuItems[0].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (mysqlStatement == null) {
                                JOptionPane.showMessageDialog(self, "请先连接数据库！", "提示", JOptionPane.PLAIN_MESSAGE);
                                return;
                            }
                            createDatabase();
                        }
                    });
                    jMenuItems[1].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jPopupMenu.setVisible(false);
                        }
                    });
                    jPopupMenu.add(jMenuItems[0]);
                    jPopupMenu.add(jMenuItems[1]);
                    jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                } else if (type.equals("database")) {
                    JPopupMenu jPopupMenu = new JPopupMenu();
                    JMenuItem[] jMenuItems = new JMenuItem[2];
                    jMenuItems[0] = new JMenuItem("新建表格");
                    jMenuItems[1] = new JMenuItem("取消");
                    jMenuItems[0].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            CreateTableWindow createTableWindow = new CreateTableWindow(self, "创建表格", 1080, 720, title);
                        }
                    });
                    jMenuItems[1].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jPopupMenu.setVisible(false);
                        }
                    });
                    jPopupMenu.add(jMenuItems[0]);
                    jPopupMenu.add(jMenuItems[1]);
                    jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                } else if (type.equals("data")) {
                    JPopupMenu jPopupMenu = new JPopupMenu();
                    JMenuItem[] jMenuItems = new JMenuItem[2];
                    jMenuItems[0] = new JMenuItem("插入数据");
                    jMenuItems[1] = new JMenuItem("取消");
                    jMenuItems[0].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            InsertDataWindow insertDataWindow = new InsertDataWindow(self, "插入数据", 1080, 720, title, mysqlStatement);
                        }
                    });
                    jMenuItems[1].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jPopupMenu.setVisible(false);
                        }
                    });
                    jPopupMenu.add(jMenuItems[0]);
                    jPopupMenu.add(jMenuItems[1]);
                    jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        Table table = new Table();
        jScrollPane.setViewportView(table);
        tabbedPanel.addTab(title, jScrollPane);
        return table;
    }

    public void clearPage() {
        int tabCount = tabbedPanel.getTabCount();
        for (int i = tabCount - 1; i >= 0; --i) {
            String selectedTitle = tabbedPanel.getTitleAt(i);
            if (!selectedTitle.equals("数据库集")) {
                tabbedPanel.remove(i);
                map.remove(selectedTitle);
            }
        }
    }

    public Table createNewPage(String title, String type, boolean selected) {
        // 创建新标签页
        Table table = createNewPage(title, type);
        if (selected) {
            // 选中该标签页
            int tabCount = tabbedPanel.getTabCount();
            for (int i = 0; i < tabCount; ++i) {
                String selectedTitle = tabbedPanel.getTitleAt(i);
                if (title.equals(selectedTitle)) {
                    tabbedPanel.setSelectedIndex(i);
                    return table;
                }
            }
        }
        return table;
    }

    public void updateDatabaseList() {
        String sql = "show databases";
        executeSQL("databaseList", "databaseList", sql, true);
    }

    public void createDatabase() {
        String name = JOptionPane.showInputDialog(self, "数据库名");
        String sql = "create database if not exists " + name + ";";
        executeSQL(name, "database", sql, false);
        updateDatabaseList();
    }

    public void dropDatabase(String name) {
        int result = JOptionPane.showConfirmDialog(self, "确认要删除这个数据库吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            String sql = "drop database if exists " + name + ";";
            executeSQL(name, "database", sql, false);
            updateDatabaseList();
        }
    }

    public void selectDatabase(String name) {
        String sql = "use " + name + ";";
        executeSQL(name, "database", sql, false);
        updateDatabase(name);
    }

    public void updateDatabase(String name) {
        String sql = "show tables;";
        executeSQL(name, "database", sql, true);
    }

    public void createTable(CreateTableWindow object) {
        StringBuilder sql = new StringBuilder("create table " + object.getName() + "(");
        Map<String, String> field = object.getField();
        boolean first = true;
        for (Map.Entry<String, String> entry : field.entrySet()) {
            String fieldName = entry.getKey();
            String fieldTypeName = entry.getValue();
            if (!first) {
                sql.append(",");
            } else {
                first = false;
            }
            sql.append(fieldName).append(" ").append(fieldTypeName);
        }
        sql.append(");");
        executeSQL(object.getName(), "table", sql.toString(), false);
        updateDatabase(object.getDatabaseName());
    }

    public void dropTable(String databaseName, String name) {
        int result = JOptionPane.showConfirmDialog(self, "确认要删除这个表格吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            String sql = "drop table if exists " + name + ";";
            executeSQL(name, "database", sql, false);
            updateDatabase(databaseName);
        }
    }

    public void showTable(String name, boolean defaultSQL) {
        if (defaultSQL) {
            showTableSQL = "select * from " + name + ";";
        }
        executeSQL(name, "data", showTableSQL, true);
    }

    public void insertData(String tableName, JTable table) {
        StringBuilder sql = new StringBuilder("insert into " + tableName + " (");
        int columnCount = table.getColumnCount();
        for (int i = 0; i < columnCount; ++i) {
            sql.append(table.getColumnName(i));
            if (i < columnCount - 1) {
                sql.append(",");
            }
        }
        sql.append(") values ");
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; ++i) {
            sql.append("(");
            for (int j = 0; j < columnCount; ++j) {
                Object content = table.getValueAt(i, j);
                if (content == null) {
                    sql.append("null");
                } else {
                    sql.append(content.toString());
                }
                if (j < columnCount - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
            if (i < rowCount - 1) {
                sql.append(",");
            }
        }
        sql.append(";");
        executeSQL(tableName, "data", sql.toString(), false);
        showTable(tableName, false);
    }

    public void deleteData(String tableName, Table table) {
        int result = JOptionPane.showConfirmDialog(self, "确认要删除这些数据吗吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // 获取主键
            String uniqueName = null;
            try {
                DatabaseMetaData databaseMetaData = mysqlConnection.getMetaData();
                ResultSet primaryKeyResultSet = databaseMetaData.getPrimaryKeys(null, null, tableName);
                if (primaryKeyResultSet == null) {
                    ResultSet columnsResultSet = databaseMetaData.getColumns(null, null, tableName, null);
                    while (columnsResultSet.next()) {
                        String columnName = columnsResultSet.getString("COLUMN_NAME");
                        String isUnique = columnsResultSet.getString("IS_UNIQUE");
                        if ("YES".equalsIgnoreCase(isUnique)) {
                            // ignoreCase忽略大小写
                            uniqueName = columnName;
                            break;
                        }
                    }
                } else {
                    primaryKeyResultSet.next();
                    uniqueName = primaryKeyResultSet.getString("COLUMN_NAME");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (uniqueName == null) {
                JOptionPane.showMessageDialog(self, "无法操作没有unique属性列的表格", "拒绝操作", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // 根据主键删除
            int[] selectedRows = table.getSelectedRows();
            int length = selectedRows.length;
            int uniqueColumnIndex = table.getColumnModel().getColumnIndex(uniqueName);
            StringBuilder sql = new StringBuilder("delete from " + tableName + " where " + uniqueName + " in (");
            for (int i = 0; i < length; ++i) {
                String uniqueContent = table.getValueAt(selectedRows[i], uniqueColumnIndex).toString();
                sql.append(uniqueContent);
                if (i < length - 1) {
                    sql.append(",");
                }
            }
            sql.append(");");
            executeSQL(tableName, "data", sql.toString(), false);
            showTable(tableName, false);
        }
    }

    public void updateData(String tableName, Table table, JTable updateTable) {
        // 获取主键
        String uniqueName = null;
        try {
            DatabaseMetaData databaseMetaData = mysqlConnection.getMetaData();
            ResultSet primaryKeyResultSet = databaseMetaData.getPrimaryKeys(null, null, tableName);
            if (primaryKeyResultSet == null) {
                ResultSet columnsResultSet = databaseMetaData.getColumns(null, null, tableName, null);
                while (columnsResultSet.next()) {
                    String columnName = columnsResultSet.getString("COLUMN_NAME");
                    String isUnique = columnsResultSet.getString("IS_UNIQUE");
                    if ("YES".equalsIgnoreCase(isUnique)) {
                        // ignoreCase忽略大小写
                        uniqueName = columnName;
                        break;
                    }
                }
            } else {
                primaryKeyResultSet.next();
                uniqueName = primaryKeyResultSet.getString("COLUMN_NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (uniqueName == null) {
            JOptionPane.showMessageDialog(self, "无法操作没有unique属性列的表格", "拒绝操作", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        // 修改
        int[] selectedRows = table.getSelectedRows();
        int length = selectedRows.length;
        int uniqueColumnIndex = table.getColumnModel().getColumnIndex(uniqueName);
        StringBuilder sql = new StringBuilder("update " + tableName + " set ");
        int columnCount = updateTable.getColumnCount();
        for (int i = 0; i < columnCount; ++i) {
            Object cell = updateTable.getValueAt(0, i);
            if (cell != null) {
                String fn = updateTable.getColumnName(i);
                String value = cell.toString();
                sql.append(fn).append(" = ").append(value).append(",");
            }
        }
        int sqlLength = sql.length();
        if (sql.charAt(sqlLength - 1) == ',') {
            sql.deleteCharAt(sqlLength - 1);
        }
        // 获取需要修改的行
        sql.append(" where " + uniqueName + " in (");
        for (int i = 0; i < length; ++i) {
            String uniqueContent = table.getValueAt(selectedRows[i], uniqueColumnIndex).toString();
            sql.append(uniqueContent);
            if (i < length - 1) {
                sql.append(",");
            }
        }
        sql.append(");");
        executeSQL(tableName, "data", sql.toString(), false);
        showTable(tableName,false);
    }

    public void selectData(String tableName, String sql) {
        showTableSQL = sql;
        showTable(tableName, false);
    }
}
