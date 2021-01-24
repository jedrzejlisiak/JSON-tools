package pl.put.poznan.tools.gui;

import pl.put.poznan.tools.connection.ServerConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.http.HttpResponse;
import java.security.KeyPair;
import java.util.Hashtable;

public class MainMenu extends JFrame implements ActionListener {
    private final JButton accept;
    private final JButton clear;
    private Hashtable<String, JCheckBox> checkBoxes;
    private final JTextArea input;
    private final JTextArea output;
    private final JRadioButton jsonIn;
    private final JRadioButton xmlIn;
    private final JRadioButton jsonOut;
    private final JRadioButton xmlOut;
    private final JTextField filter;

    private ServerConnection connection;

    public MainMenu() {
        connection = new ServerConnection("http://localhost:8080/json");

        setSize(700, 500);

        accept = new JButton("Convert");
        accept.setBounds(150, 425, 100, 30);
        accept.addActionListener(this);

        clear = new JButton("Clear");
        clear.setBounds(450, 425, 100, 30);
        clear.addActionListener(this);

        input = new JTextArea("Place your file here");
        input.setBounds(20,20, 200, 300);
        input.setLineWrap(true);
        input.setWrapStyleWord(true);

        output = new JTextArea("Here will be your transformed file");
        output.setBounds(250, 20, 200, 300);
        output.setEnabled(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);


        JLabel docTypeIn = new JLabel("Input type");
        docTypeIn.setBounds(500, 20, 180, 20);



        jsonIn = new JRadioButton("JSON");
        jsonIn.setBounds(500, 50, 180, 20);
        jsonIn.addActionListener(this);

        xmlIn = new JRadioButton("XML");
        xmlIn.setBounds(500, 80, 180, 20);
        xmlIn.addActionListener(this);



        JLabel docTypeOut = new JLabel("Output type");
        docTypeOut.setBounds(500, 110, 180, 20);

        jsonOut = new JRadioButton("JSON");
        jsonOut.setBounds(500, 140, 180, 20);
        jsonOut.addActionListener(this);

        xmlOut = new JRadioButton("XML");
        xmlOut.setBounds(500, 170, 180, 20);
        xmlOut.addActionListener(this);


        checkBoxes = new Hashtable<>();

        JLabel transforms = new JLabel("Transformations");
        transforms.setBounds(500, 220, 180, 20);

        checkBoxes.put("minify", new JCheckBox("Minify"));
        checkBoxes.get("minify").setBounds(500, 250, 180, 20);

        checkBoxes.put("deminify", new JCheckBox("Deminify"));
        checkBoxes.get("deminify").setBounds(500, 280, 180, 20);

        checkBoxes.put("filterPos", new JCheckBox("Positive filtering"));
        checkBoxes.get("filterPos").setBounds(500, 310, 180, 20);
        checkBoxes.get("filterPos").addActionListener(this);

        checkBoxes.put("filterNeg", new JCheckBox("Negative filtering"));
        checkBoxes.get("filterNeg").setBounds(500, 340, 180, 20);
        checkBoxes.get("filterNeg").addActionListener(this);

        JLabel pos = new JLabel("Fields to be filtered");
        pos.setBounds(500, 370, 180, 20);

        filter = new JTextField();
        filter.setBounds(500, 400, 180, 20);
        filter.setEnabled(false);



        add(accept);
        add(clear);
        add(input);
        add(output);
        add(docTypeIn);
        add(docTypeOut);
        add(transforms);
        add(jsonIn);
        add(xmlIn);
        add(jsonOut);
        add(xmlOut);
        add(checkBoxes.get("minify"));
        add(checkBoxes.get("deminify"));
        add(checkBoxes.get("filterPos"));
        add(checkBoxes.get("filterNeg"));
        add(pos);
        add(filter);


        setLayout(null);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == jsonIn) {
            jsonIn.setSelected(true);
            xmlIn.setSelected(false);
        }
        else if(source == xmlIn) {
            jsonIn.setSelected(false);
            xmlIn.setSelected(true);
        }
        else if(source == jsonOut) {
            jsonOut.setSelected(true);
            xmlOut.setSelected(false);
        }
        else if(source == xmlOut) {
            jsonOut.setSelected(false);
            xmlOut.setSelected(true);
        }
        else if(source == checkBoxes.get("filterPos")){
            if(checkBoxes.get("filterNeg").isSelected()) {
                checkBoxes.get("filterNeg").setSelected(false);
            }

            filter.setEnabled(checkBoxes.get("filterPos").isSelected());
        }
        else if(source == checkBoxes.get("filterNeg")){
            if(checkBoxes.get("filterPos").isSelected()) {
                checkBoxes.get("filterPos").setSelected(false);
            }

            filter.setEnabled(checkBoxes.get("filterNeg").isSelected());
        }
        else if(source == clear) {
            input.setText("");
            output.setText("");
            output.setEnabled(false);
            for(JCheckBox i : checkBoxes.values()) {
                i.setSelected(false);
            }
            jsonIn.setSelected(false);
            jsonOut.setSelected(false);
            xmlIn.setSelected(false);
            xmlOut.setSelected(false);

            filter.setEnabled(false);
        }
        else if(source == accept) {
            String in = input.getText();

            connection.uploadFile(in, 0);

            String args = "transforms=";
            int count = 0;
            for(String i : checkBoxes.keySet()) {
                if(checkBoxes.get(i).isSelected()) {
                    if(count > 0) args+= ',';
                    args += i;
                    count++;
                }
            }
            if(xmlOut.isSelected()) {
                if(count > 0) args += ',';
                args += "toXml";
                count++;
            }
            if(count == 0) args = "";
            else args += '&';

            args+="fields=";

            if(filter.isEnabled()) {
                args+=filter.getText();
            }

            HttpResponse<String> response = connection.sendRequest(args, 0);

            output.setText(response.body());
            output.setEnabled(true);
        }
    }
}
