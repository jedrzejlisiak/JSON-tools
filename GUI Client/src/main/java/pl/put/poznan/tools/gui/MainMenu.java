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
        clear.setBounds(350, 425, 100, 30);
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
        transforms.setBounds(500, 200, 180, 20);

        checkBoxes.put("minify", new JCheckBox("Minify"));
        checkBoxes.get("minify").setBounds(500, 230, 180, 20);
        checkBoxes.get("minify").addActionListener(this);

        checkBoxes.put("deminify", new JCheckBox("Deminify"));
        checkBoxes.get("deminify").setBounds(500, 260, 180, 20);
        checkBoxes.get("deminify").addActionListener(this);

        checkBoxes.put("filterPos", new JCheckBox("Positive filtering"));
        checkBoxes.get("filterPos").setBounds(500, 290, 180, 20);
        checkBoxes.get("filterPos").addActionListener(this);

        checkBoxes.put("filterNeg", new JCheckBox("Negative filtering"));
        checkBoxes.get("filterNeg").setBounds(500, 320, 180, 20);
        checkBoxes.get("filterNeg").addActionListener(this);

        checkBoxes.put("compare", new JCheckBox("Compare"));
        checkBoxes.get("compare").setBounds(500, 350, 180, 20);
        checkBoxes.get("compare").addActionListener(this);

        JLabel pos = new JLabel("Fields to be filtered");
        pos.setBounds(500, 400, 180, 20);

        filter = new JTextField();
        filter.setBounds(500, 430, 180, 20);
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
        add(checkBoxes.get("compare"));
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
            if(checkBoxes.get("compare").isSelected()) {
                checkBoxes.get("filterPos").setSelected(false);
            }

            filter.setEnabled(checkBoxes.get("filterPos").isSelected());
        }
        else if(source == checkBoxes.get("filterNeg")){
            if(checkBoxes.get("filterPos").isSelected()) {
                checkBoxes.get("filterPos").setSelected(false);
            }
            if(checkBoxes.get("compare").isSelected()) {
                checkBoxes.get("filterNeg").setSelected(false);
            }

            filter.setEnabled(checkBoxes.get("filterNeg").isSelected());
        }
        else if(source == checkBoxes.get("minify")){
            if(checkBoxes.get("deminify").isSelected()) {
                checkBoxes.get("deminify").setSelected(false);
            }
            if(checkBoxes.get("compare").isSelected()) {
                checkBoxes.get("minify").setSelected(false);
            }
        }
        else if(source == checkBoxes.get("deminify")) {
            if(checkBoxes.get("minify").isSelected()) {
                checkBoxes.get("minify").setSelected(false);
            }
            if(checkBoxes.get("compare").isSelected()) {
                checkBoxes.get("deminify").setSelected(false);
            }
        }
        else if(source == checkBoxes.get("compare")) {
            if(checkBoxes.get("compare").isSelected()) {
                input.setText("First file here");
                output.setText("Second file here");
                output.setEnabled(true);
                for (JCheckBox i : checkBoxes.values()) {
                    if (i != source) {
                        i.setSelected(false);
                    }
                }
                jsonIn.setEnabled(false);
                jsonOut.setEnabled(false);
                xmlIn.setEnabled(false);
                xmlOut.setEnabled(false);

                filter.setEnabled(false);
            }
            else {
                output.setEnabled(false);

                jsonIn.setEnabled(true);
                jsonOut.setEnabled(true);
                xmlIn.setEnabled(true);
                xmlOut.setEnabled(true);
            }
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

            jsonIn.setEnabled(true);
            jsonOut.setEnabled(true);
            xmlIn.setEnabled(true);
            xmlOut.setEnabled(true);

            filter.setEnabled(false);
        }
        else if(source == accept) {
            String in = input.getText();
            String in2;
            if(checkBoxes.get("compare").isSelected()) {
                in2 = output.getText();
                connection.uploadFile(in2, 1);
            }

            connection.uploadFile(in, 0);

            String args = "transforms=";
            int count = 0;
            for(String i : checkBoxes.keySet()) {
                if(checkBoxes.get(i).isSelected()) {
                    if(count > 0) args += ',';
                    args += i;
                    count++;
                }
            }
            if(xmlOut.isSelected()) {
                if(count > 0) args += ',';
                args += "toXml";
                count++;
            }
            if(jsonOut.isSelected()) {
                if(xmlIn.isSelected()) {
                    if(count > 0) args += ',';
                    args += "toJson";
                    count++;
                }
            }

            if(count == 0) args = "";
            else args += '&';



            if(filter.isEnabled()) {
                args+="fields=";
                args+=filter.getText();
            }

            HttpResponse<String> response = connection.sendRequest(args, 0,
                    checkBoxes.get("compare").isSelected());
            if(!checkBoxes.get("compare").isSelected()) {
                output.setText(response.body());
                output.setEnabled(true);
            }
            else {
                JFrame result = new JFrame("Comparison");

                result.setSize(500, 700);

                JTextArea field = new JTextArea(response.body());
                field.setBounds(10, 10, 480, 600);
                field.setWrapStyleWord(true);
                field.setLineWrap(true);

                JButton button = new JButton("Ok");
                button.setBounds(200,650,100,20);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        result.dispose();
                    }
                });

                result.add(field);
                result.add(button);

                result.setLayout(null);
                result.setVisible(true);
            }
        }
    }
}
