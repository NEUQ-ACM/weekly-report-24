package pers.stream.jdbcandswingtest.ui;

import lombok.val;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.Color.*;
import static javax.swing.BorderFactory.createCompoundBorder;
import static javax.swing.BorderFactory.createLineBorder;

public class CommandUi
{
    private final JFrame frame = new JFrame("CommandUi");
    private final JSplitPane splitPane = new JSplitPane();
    private final JScrollPane showSpaceScrollPane = new JScrollPane();
    private final JTextArea showSpace = new JTextArea();
    private final JPanel commandPanel = new JPanel();
    private final JScrollPane inputSpaceScrollPane = new JScrollPane();
    private final JTextArea inputSpace = new JTextArea();
    private final JButton runButton = new JButton("Run");

    public CommandUi()
    {
        initUi();
    }

    private void initUi()
    {
        initShowSpaceScrollPane();
        initCommandPanel();

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(0.9);
        splitPane.setResizeWeight(0.9);
        splitPane.setTopComponent(showSpaceScrollPane);
        splitPane.setBottomComponent(commandPanel);
        frame.add(splitPane);

        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initShowSpaceScrollPane()
    {
        initShowSpace();

        showSpaceScrollPane.setBackground(DARK_GRAY);
        showSpaceScrollPane.setForeground(WHITE);
        showSpaceScrollPane.setBorder(createTitledBorder("History"));
        JScrollBar myScrollBar = createMyScrollBar();
        showSpaceScrollPane.setVerticalScrollBar(myScrollBar);
        showSpaceScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        showSpaceScrollPane.setViewportView(showSpace);
    }

    private void initShowSpace()
    {
        showSpace.setBackground(DARK_GRAY);
        showSpace.setForeground(Color.white);
        showSpace.setEditable(false);
        showSpace.setDragEnabled(true);
        showSpace.setFont(new Font("Consolas", Font.PLAIN, 20));
    }

    private void initCommandPanel()
    {
        initInputSpaceScrollPane();
        initRunButton();

        commandPanel.setBorder(createTitledBorder("Input command"));
        commandPanel.setLayout(new GridBagLayout());
        commandPanel.setBackground(DARK_GRAY);

        val constraints0 = new GridBagConstraints();
        constraints0.fill = GridBagConstraints.BOTH;
        constraints0.anchor = GridBagConstraints.WEST;
        constraints0.gridheight = 1;
        constraints0.weightx = 1;
        constraints0.weighty = 1;
        commandPanel.add(inputSpaceScrollPane, constraints0);

        val constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0.1;
        commandPanel.add(runButton, constraints);
    }

    private void initInputSpaceScrollPane()
    {
        initInputSpace();

        inputSpaceScrollPane.setBackground(DARK_GRAY);
        inputSpaceScrollPane.setForeground(WHITE);
        inputSpaceScrollPane.setBorder(createDoubleLinedBorder(1));
        inputSpaceScrollPane.setVerticalScrollBar(createMyScrollBar());
        inputSpaceScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        inputSpaceScrollPane.setViewportView(inputSpace);
    }

    private void initInputSpace()
    {
        inputSpace.setBackground(DARK_GRAY);
        inputSpace.setForeground(WHITE);
        inputSpace.setAlignmentX(1.5f);
        inputSpace.setCaretColor(WHITE);
        inputSpace.setFont(new Font("Consolas", Font.PLAIN, 20));
        inputSpace.addKeyListener(new InputSpaceKeyListener());
    }

    private void initRunButton()
    {
        runButton.setFont(new Font("Consolas", Font.PLAIN, 20));
        runButton.setBackground(DARK_GRAY);
        runButton.setForeground(WHITE);
        runButton.setBorder(createDoubleLinedBorder(2));
        runButton.addActionListener(actionEvent ->
                                    {
                                        inputSpace.append("\n");
                                        passText();
                                        inputSpace.requestFocus();
                                    });
    }

    private static JScrollBar createMyScrollBar()
    {
        JScrollBar myScrollBar = new JScrollBar();
        myScrollBar.setBorder(createLineBorder(WHITE));
        myScrollBar.setUI(new MyScrollBarUI());
        myScrollBar.setBorder(createDoubleLinedBorder(1));
        myScrollBar.setUnitIncrement(32);
        return myScrollBar;
    }

    private static TitledBorder createTitledBorder(String title)
    {
        return BorderFactory.createTitledBorder(createDoubleLinedBorder(2),
                                                title,
                                                0,
                                                0,
                                                new Font("Consolas", Font.PLAIN, 20),
                                                WHITE);
    }

    private static CompoundBorder createDoubleLinedBorder(int thickness)
    {
        return createCompoundBorder(createLineBorder(WHITE, thickness, true),
                                    createLineBorder(BLUE, thickness, true));
    }

    private class InputSpaceKeyListener implements KeyListener
    {
        @Override public void keyTyped(KeyEvent e)
        {
            if(e.getKeyChar() == '\n')
            {
                if(e.isShiftDown())
                {
                    inputSpace.setText(inputSpace.getText() + "\n");
                }
                else
                {
                    passText();
                }
            }
        }

        @Override public void keyPressed(KeyEvent e)
        {
        }

        @Override public void keyReleased(KeyEvent e)
        {
        }
    }

    private void passText()
    {
        showSpace.setText(showSpace.getText() + inputSpace.getText());
        inputSpace.setText("");
    }
}
