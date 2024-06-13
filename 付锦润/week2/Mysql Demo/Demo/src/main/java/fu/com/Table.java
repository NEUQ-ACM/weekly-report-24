package fu.com;

import javax.swing.*;

public class Table extends JTable {
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
