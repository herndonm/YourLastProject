import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AlarmClock {
    //fields that set up the main frame and time
    private JLabel time;
    private JFrame setFrame;
    private String alarmHour;
    private String alarmMin;

    //fields that set up the secondary frame
    private JButton enter;
    private boolean enterHasBeen;
    private JTextField enterHour;
    private JTextField enterMin;

    //fields that set up the alarm
    private boolean alarmSet;
    private String alarmTime;

    public AlarmClock() {
        //main constructor
        set(); //method that initializes the alarm clock
        driver(); //method the calls the loop. Also updates the time and
        // checks to see when the alarm should sound

    }

    public void set() {

        JFrame frame = new JFrame(); //frame

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); //sets the time format to HH:mm
        LocalTime local = LocalTime.now(); //gets the local time (my computers time)
        String localString = local.format(formatter); //formats the local time
        time = new JLabel(localString, JLabel.CENTER); //adds the time to a label

        setUpAlarm(); //method sets up the add alarm window

        time.setFont(new Font("Time", Font.PLAIN, 50)); //sets the font of the time label
        //to be plain and be size 50

        JButton stop = new JButton("Stop");
        JButton set = new JButton("Set Alarm");
        JButton quit = new JButton("Quit");
        //creates the three buttons on the main frame

        time.setBounds(100, 100, 200, 100);
        quit.setBounds(125, 250, 150, 50);
        set.setBounds(100, 200, 100, 50);
        stop.setBounds(200, 200, 100, 50);
        //sets the bounds and positions of the label and buttons

        //creates a new actionListener for the quit button
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //closes the clock
            }
        });
        //creates a new actionListener for the set button
        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFrame.setVisible(true);
                //creates a new action listener for the enter button
                enter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!enterHasBeen) { //checks if the button has been pressed before
                            alarmHour = enterHour.getText(); //gets the text in the enterHour field
                            alarmMin = enterMin.getText(); // gets the text iin the enterMin field
                            if (alarmHour.equals("") || alarmMin.equals("")) { //if those fields are empty
                                alarmHour = "00";
                                alarmMin = "00"; //sets the alarm to midnight by default
                                enterHasBeen = true; //protects against resetting the alarm to 00:00 without wanting to
                            } else {
                                setFrame.setVisible(false); //hides the frame
                                int alarmHourInt = Integer.parseInt(alarmHour); //changes the strings to ints
                                int alarmMinInt = Integer.parseInt(alarmMin);
                                if ((alarmHourInt < 25) && alarmMinInt < 61) { //if the ints are with in normal bounds
                                    LocalTime time = LocalTime.of(alarmHourInt, alarmMinInt);
                                    setAlarm(time); //sets the time to the alarm
                                }
                                enterHasBeen = false; //resets the alarm frame
                                alarmHour = null;
                                alarmMin = null;
                                enterHour.setText("");
                                enterMin.setText("");
                            }
                        }
                    }
                });
            }
        });
        stop.addActionListener(new ActionListener() { //adds an action listener to the stop button
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmSet = false; //turns off the alarm
                alarmHour = null;
                alarmMin = null;
                enterHour.setText("");
                enterMin.setText("");
                //resets the alarm frame just in case
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); //sets the time format to HH:mm
                LocalTime local = LocalTime.now(); //gets the local time (my computers time)
                String localTimeString = local.format(formatter); //formats the local time
                time.setText(localTimeString); //sets the alarm to the local time. Has to be different as this button
                //may be pressed much later than the other localString variable.
                time.setForeground(Color.black); //sets the color back to black
            }
        });

        frame.add(quit);
        frame.add(time);
        frame.add(set);
        frame.add(stop); //adds everything to the frame

        frame.setResizable(false);
        frame.setSize(400, 500);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void setUpAlarm() { //sets up alarm frame
        setFrame = new JFrame();
        enter = new JButton("Enter");

        JLabel label = new JLabel("Set Alarm in Hours and Minutes");
        enterHour = new JTextField(5);
        enterMin = new JTextField(5);
        //initializes everything

        enter.setBounds(87, 117, 75, 25);
        enterHour.setBounds(77, 87, 50, 25);
        enterMin.setBounds(127, 87, 50, 25);
        label.setBounds(27, 25, 200, 25);
        //sets position and size

        setFrame.add(label);
        setFrame.add(enter);
        setFrame.add(enterHour);
        setFrame.add(enterMin);
        //adds everything


        setFrame.setLayout(null);
        setFrame.setSize(250, 200);
        setFrame.setResizable(false);
        setFrame.setVisible(false);
    }

    private void setAlarm(LocalTime time) { //takes the time from what the user entered
        //sets the alarm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String localString = time.format(formatter);
        alarmTime = localString; //sets the alarm
        alarmSet = true; //turns the alarm on
        localString = null; //resets localString just in case
    }


    private void driver() {
        do { //continuous loop
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime local = LocalTime.now();
            String localString = local.format(formatter); //finds the current local time
            if (alarmSet) { //checks to see if the alarm is on
                if (alarmTime.equals(time.getText())) { //if the alarm equals the time
                    time.setText("ALARM"); //change the time to alarm
                    time.setForeground(Color.red); //changes the font color to red
                }
            }
            else if (!localString.equals(time.getText())) { //sees if the local time and the time on the clock match
                time.setText(localString); //changes the clock to the local time
                time.setForeground(Color.black); //changes the color back to black after the alarm ends
            }

        } while (true); //end of continuous loop
    }
}

