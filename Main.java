package a;

import javax.swing.*;

class Slide extends JSlider {
    public Slide() {
        super(0,500);
    }
    
    synchronized public void Increase(int increment){
        setValue((int)getValue() + increment);
    }
}

class myThread extends Thread {
    private int increment;
    private Slide my_slider;
    private int count;
    private static int SLIDER_BOUND = 1000000;
    private static int THREAD_COUNTER = 0;
    private int current_thread_num;

    public myThread(Slide my_slider, int increment, int priority) {
        this.my_slider = my_slider;
        this.increment = increment;
        current_thread_num = ++THREAD_COUNTER;
        setPriority(priority);
    }

    @Override
    public void run() {
        while(!interrupted()){
            int val = (int)(my_slider.getValue());
            ++count;
            if(count > SLIDER_BOUND){
                my_slider.Increase(increment);
                count = 0;
            }
        }
    }
anywa
    public JPanel GetJPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Thread Number " + current_thread_num + ", increment is " + increment);
        SpinnerModel sModel = new SpinnerNumberModel(getPriority(), Thread.MIN_PRIORITY, Thread.MAX_PRIORITY, 1);
        JSpinner Spinner = new JSpinner(sModel);
        Spinner.addChangeListener(e->{setPriority((int)(Spinner.getValue()));});
        panel.add(label);
        panel.add(Spinner);
        return panel;
    }
}

public class Main {
    public static int SEMAPHORE = 0;
    static Thread T1, T2;
    public static void main(String[] args) {
        JFrame Lab1_JFrame = new JFrame();
        Lab1_JFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Lab1_JFrame.setSize(600, 600);
        Slide mySSlider = new Slide();

        myThread Tthread1 = new myThread(mySSlider, +1, Thread.NORM_PRIORITY);
        myThread TThread2 = new myThread(mySSlider, -1, Thread.NORM_PRIORITY);

        JButton startBTTN = new JButton("ПУСК");
        startBTTN.addActionListener(e -> {
            Tthread1.start();
            TThread2.start();
            startBTTN.setEnabled(false);
        });
        JPanel Lab1_JFramePanel = new JPanel();
        Lab1_JFramePanel.setLayout(new BoxLayout(Lab1_JFramePanel, BoxLayout.Y_AXIS));

        Lab1_JFramePanel.add(Tthread1.GetJPanel());
        Lab1_JFramePanel.add(TThread2.GetJPanel());
        Lab1_JFramePanel.add(mySSlider);

        JPanel jPanel = new JPanel();
        jPanel.add(startBTTN);
        Lab1_JFramePanel.add(jPanel);

        Lab1_JFrame.setContentPane(Lab1_JFramePanel);
        Lab1_JFrame.setVisible(true);

    }
}