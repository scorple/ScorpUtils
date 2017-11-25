package com.scorpius_enterprises.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

/**
 * Created by rickm on 7/8/2017.
 */
public class Dialog
    extends JFrame
{
    private JPanel      parentView;
    private JScrollPane outputScrollView;
    private JTextArea   outputView;
    private JTextField  inputView;

    private IDialogListener dialogListener;

    public Dialog(final IDialogListener dialogListener)
    {
        super("View");

        this.dialogListener = dialogListener;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                close();
            }
        };
        addWindowListener(exitListener);

        parentView = new JPanel(new GridBagLayout());
        GridBagConstraints panelConstraints = new GridBagConstraints();

        panelConstraints.insets = new Insets(20,
                                             20,
                                             20,
                                             20);

        outputView = new JTextArea(16,
                                   32);
        outputView.setEditable(false);
        outputView.setRequestFocusEnabled(false);
        outputScrollView = new JScrollPane(outputView);
        outputScrollView.setHorizontalScrollBarPolicy(ScrollPaneConstants
                                                          .HORIZONTAL_SCROLLBAR_NEVER);

        inputView = new JTextField(32);

        panelConstraints.gridy = 0;

        parentView.add(outputScrollView,
                       panelConstraints);

        panelConstraints.gridy = 1;

        parentView.add(inputView,
                       panelConstraints);

        add(parentView);
        pack();

        setVisible(true);
        requestFocus();
        parentView.requestFocus();
        inputView.requestFocus();

        setupInputView();
    }

    private void setupInputView()
    {
        Action exit = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                close();
            }
        };

        Action submit = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                processInput(inputView.getText());
            }
        };

        inputView.getInputMap()
                 .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
                                             0),
                      "exit");
        inputView.getActionMap()
                 .put("exit",
                      exit);
        inputView.getInputMap()
                 .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
                                             0),
                      "submit");
        inputView.getActionMap()
                 .put("submit",
                      submit);
    }

    private void processInput(final String input)
    {
        if ((!inputView.getText()
                       .equals("")) && (dialogListener != null))
        {
            dialogListener.processInput(input);
        }
    }

    public void clearInput()
    {
        inputView.setText("");
    }

    public void appendText(final String text)
    {
        outputView.append(text + "\n");
    }

    public void close()
    {
        dialogListener.dialogClosed();
        dispose();
    }
}
