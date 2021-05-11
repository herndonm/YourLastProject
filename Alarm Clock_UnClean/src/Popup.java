import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Popup {
    private JButton quit;
    private JButton stop;
    private JButton set;
    private JLabel time;
    private JFrame setFrame;
    private String alarmHour;
    private String alarmMin;

    private JButton enter;
    private boolean enterHasBeen;
    private JLabel label;
    private JTextField hour;
    private JTextField min;

    private boolean alarmSet;
    private String alarmTime;

    public Popup() {
        set();
        driver();

    }

    public void set() {
        JFrame frame = new JFrame();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime local = LocalTime.now();
        String localString = local.format(formatter);
        time = new JLabel(localString, JLabel.CENTER);

        setUpAlarm();

        time.setFont(new Font("Time", Font.PLAIN, 50));

        stop = new JButton("Stop");
        set = new JButton("Set Alarm");
        quit = new JButton("Quit");

        time.setBounds(100, 100, 200, 100);
        quit.setBounds(125, 250, 150, 50);
        set.setBounds(100, 200, 100, 50);
        stop.setBounds(200, 200, 100, 50);

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFrame.setVisible(true);
                enter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!enterHasBeen) {
                            alarmHour = hour.getText();
                            alarmMin = min.getText();
                            if (alarmHour.equals("") || alarmMin.equals("")) {
                                alarmHour = "00";
                                alarmMin = "00";
                                enterHasBeen = true;
                            } else {
                                setFrame.setVisible(false);
                                int alarmHourInt = Integer.parseInt(alarmHour);
                                int alarmMinInt = Integer.parseInt(alarmMin);
                                if ((alarmHourInt < 25) && alarmMinInt < 61) {
                                    LocalTime time = LocalTime.of(alarmHourInt, alarmMinInt);
                                    setAlarm(time);
                                }
                                enterHasBeen = false;
                                alarmHour = null;
                                alarmMin = null;
                                hour.setText("");
                                min.setText("");
                            }
                        }
                    }
                });
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmSet = false;
                alarmHour = null;
                alarmMin = null;
                hour.setText("");
                min.setText("");
                time.setText(localString);
                time.setForeground(Color.black);
            }
        });

        frame.add(quit);
        frame.add(time);
        frame.add(set);
        frame.add(stop);

        frame.setResizable(false);
        frame.setSize(400, 500);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void setUpAlarm() {
        setFrame = new JFrame();
        enter = new JButton("Enter");

        label = new JLabel("Set Alarm in Hours and Minutes");
        hour = new JTextField(5);
        min = new JTextField(5);

        enter.setBounds(87, 117, 75, 25);
        hour.setBounds(77, 87, 50, 25);
        min.setBounds(127, 87, 50, 25);
        label.setBounds(27, 25, 200, 25);

        setFrame.add(label);
        setFrame.add(enter);
        setFrame.add(hour);
        setFrame.add(min);


        setFrame.setLayout(null);
        setFrame.setSize(250, 200);
        setFrame.setResizable(false);
        setFrame.setVisible(false);
    }

    private void setAlarm(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String localString = time.format(formatter);
        alarmTime = localString;
        System.out.print(alarmTime);
        alarmSet = true;
        localString = null;
    }


    private void driver() {
        do {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime local = LocalTime.now();
            String localString = local.format(formatter);
            if (alarmSet) {
                if (alarmTime.equals(time.getText())) {
                    time.setText("ALARM");
                    time.setForeground(Color.red);
                }
            }
            else if (!localString.equals(time.getText())) {
                time.setText(localString);
                time.setForeground(Color.black);
            }

        } while (true);
    }
}

