package app.view;

import app.control.CasesController;
import app.control.DateUtils;
import app.control.NotificationController;
import app.control.ResourcesLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * MainFrame
 * extends JFrame
 */
public class MainFrame extends JFrame {

    /** Frame/Window Title */
    private static final String TITLE = "Relógio de Ponto";
    /** Text Default Font */
    private static final String FONT  = "Monospaced";
    /** Background Color */
    private static final Color COLOR_BACKGROUND = Color.DARK_GRAY;

    /** Font */
    private static final Font FONT_SMALL = new Font(FONT, Font.BOLD, 16);
    private static final Font FONT_MID   = new Font(FONT, Font.BOLD, 22);

    /** Notification Controller */
    private final NotificationController mNotificationController;
    /** Cases Controller */
    private final CasesController mCasesController;

    /** Time Pickers */
    private JSpinner mE1Spinner;
    private JSpinner mS1Spinner;
    private JSpinner mE2Spinner;
    private JSpinner mS2Spinner;
    private JSpinner mE3Spinner;
    private JSpinner mS3Spinner;
    private JSpinner mHourSpinner;

    /**
     * Constructor
     * Build Frame
     */
    public MainFrame() {
        super();

        this.setSize(900, 320);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ResourcesLoader().loadIcon());
        this.setTitle(TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.getContentPane().setBackground(COLOR_BACKGROUND);

        mNotificationController = new NotificationController(this);
        mCasesController = new CasesController(mNotificationController);

        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boxPanel.setBackground(COLOR_BACKGROUND);

        boxPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        boxPanel.add(new JSeparator());

        JLabel titleLabel = buildLabel(TITLE, null);
        titleLabel.setFont(FONT_MID);
        boxPanel.add(titleLabel);

        boxPanel.add(new JSeparator());
        boxPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        boxPanel.add(buildHoursPanel());
        boxPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        boxPanel.add(new JSeparator());
        boxPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        boxPanel.add(buildButtonsPanel());
        boxPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        boxPanel.add(new JSeparator());
        boxPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        this.getContentPane().add(boxPanel);
    }

    /**
     * buildHoursPanel
     * @return Hour Panel
     */
    private JPanel buildHoursPanel() {
        /*
         * Panel 1
         * - Entrada
         */
        JPanel panel1 = buildSingleTimePickerPanel(
                "<html><p text-align: center>Horário<br>Entrada</p></html>",
                "Horário de chegada");
        panel1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        mE1Spinner = buildTimePicker();
        panel1.add(buildPanel(mE1Spinner));

        /*
         * Panel 2
         * - Almoço
         * - Volta
         */
        JPanel panel2 = new JPanel();
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setBackground(COLOR_BACKGROUND);
        panel2.setLayout(new GridLayout(1, 2, 10, 20));
        panel2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));

        JPanel s1Panel = buildSingleTimePickerPanel(
                "<html><p text-align: center>Pausa<br>Almoço</p></html>",
                "Saída para o almoço");
        mS1Spinner = buildTimePicker();
        s1Panel.add(buildPanel(mS1Spinner));

        JPanel e2Panel = buildSingleTimePickerPanel(
                "<html><p text-align: center>Pausa<br>Almoço</p></html>",
                "Saída para o almoço");
        mE2Spinner = buildTimePicker();
        e2Panel.add(buildPanel(mE2Spinner));

        panel2.add(s1Panel);
        panel2.add(e2Panel);

        /*
         * Panel 3
         * - Intervalo
         * - Volta
         */
        JPanel panel3 = new JPanel();
        panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel3.setBackground(COLOR_BACKGROUND);
        panel3.setLayout(new GridLayout(1, 2, 10, 20));
        panel3.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));

        JPanel s2Panel = buildSingleTimePickerPanel(
                "<html><p text-align: center>Pausa<br>Intervalo</p></html>",
                "Horário de saída para o intervalo");
        mS2Spinner = buildTimePicker();
        mS2Spinner.setEnabled(false);
        s2Panel.add(buildPanel(mS2Spinner));

        JPanel e3Panel = buildSingleTimePickerPanel(
                "<html><p text-align: center>Volta<br>Intervalo</p></html>",
                "Horário de volta do intervalo");
        mE3Spinner = buildTimePicker();
        mE3Spinner.setEnabled(false);
        e3Panel.add(buildPanel(mE3Spinner));

        panel3.add(s2Panel);
        panel3.add(e3Panel);

        /*
         * Panel 4
         * - Tempo
         */
        JPanel panel4 = buildSingleTimePickerPanel(
                "<html><p text-align: center>Tempo de<br>Trabalho</p></html>",
                "Tempo total de trabalho");
        panel4.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        mHourSpinner = buildTimePicker();
        panel4.add(buildPanel(mHourSpinner));

        /*
         * Panel 5
         * - Saída
         */
        JPanel panel5 = buildSingleTimePickerPanel(
                "<html><p text-align: center>Horário<br>Saída</p></html>",
                "Horário de saída");
        panel5.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        mS3Spinner = buildTimePicker();
        panel5.add(buildPanel(mS3Spinner));

        /*
         * Hours Panel
         * - Panel 1
         * - Panel 2
         * - Panel 3
         * - Panel 4
         * - Panel 5
         */
        JPanel hoursPanel = new JPanel();
        hoursPanel.setLayout(new BoxLayout(hoursPanel, BoxLayout.X_AXIS));
        hoursPanel.setBackground(COLOR_BACKGROUND);

        hoursPanel.add(panel1);
        hoursPanel.add(Box.createRigidArea(new Dimension(8, 0)));
        hoursPanel.add(panel2);
        hoursPanel.add(Box.createRigidArea(new Dimension(8, 0)));
        hoursPanel.add(panel3);
        hoursPanel.add(Box.createRigidArea(new Dimension(8, 0)));
        hoursPanel.add(panel4);
        hoursPanel.add(Box.createRigidArea(new Dimension(8, 0)));
        hoursPanel.add(panel5);

        return hoursPanel;
    }

    /**
     * buildButtonsPanel
     * @return Buttons Panel
     */
    private JPanel buildButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.setLayout(new GridLayout(1, 3, 10, 10));
        buttonsPanel.setBackground(COLOR_BACKGROUND);

        buttonsPanel.add(buildHelpButton());
        buttonsPanel.add(buildCalcButton());
        buttonsPanel.add(buildResetButton());

        return buttonsPanel;
    }

    /**
     * buildLabel
     * @param text Label text
     * @param toolTipText Tool Tip Text
     * @return Label
     */
    private static JLabel buildLabel(String text, String toolTipText) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setToolTipText(toolTipText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(FONT_SMALL);
        label.setForeground(Color.WHITE);
        label.setVisible(true);
        return label;
    }

    /**
     * buildTimePickerPanel
     * @param label label
     * @param toolTip toolTip
     * @return TimerPicker Panel
     */
    private JPanel buildSingleTimePickerPanel(String label, String toolTip) {
        JPanel panel = new JPanel();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBackground(COLOR_BACKGROUND);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(buildLabel(label, toolTip));
        return panel;
    }

    /**
     * buildTimePicker
     * @return TimePicker
     */
    private JSpinner buildTimePicker() {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        spinner.setVisible(true);
        spinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinner.setFont(FONT_MID);
        spinner.setValue(DateUtils.buildZeroDate());

        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinner, "HH:mm");
        timeEditor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
        timeEditor.getTextField().setAlignmentX(Component.CENTER_ALIGNMENT);
        spinner.setEditor(timeEditor);

        return spinner;
    }

    /**
     * buildCalcButton
     * @return Calc Button
     */
    private JButton buildCalcButton() {
        JButton button = buildButton("Agendar Saída");
        button.addActionListener(e -> mCasesController.calc(
                mE1Spinner,
                mS1Spinner,
                mE2Spinner,
                mS3Spinner,
                mHourSpinner));

        return button;
    }

    /**
     * buildHelpButton
     * @return Help Button
     */
    private JButton buildHelpButton() {
        JButton button = buildButton("Ajuda");
        button.addActionListener(e -> spawnHelpDialog());
        return button;
    }

    /**
     * buildResetButton
     * @return Reset Button
     */
    private JButton buildResetButton() {
        JButton button = buildButton("Reset");
        button.addActionListener(e -> resetTimePickers());
        return button;
    }

    /**
     * buildButton
     * @param text text
     * @return Button
     */
    private JButton buildButton(String text) {
        JButton button = new JButton();
        button.setText(text);
        button.setBackground(Color.LIGHT_GRAY);
        button.setBorderPainted(false);
        button.setFont(FONT_MID);
        return button;
    }

    /**
     * buildPanel
     * @param component component
     * @return Panel
     */
    private JPanel buildPanel(Component component) {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_BACKGROUND);
        panel.add(component);
        return panel;
    }

    /**
     * resetTimePickers
     */
    private void resetTimePickers() {
        Date zeroDate = DateUtils.buildZeroDate();

        setSpinnerInfo(mE1Spinner,   zeroDate, Color.BLACK);
        setSpinnerInfo(mS1Spinner,   zeroDate, Color.BLACK);
        setSpinnerInfo(mE2Spinner,   zeroDate, Color.BLACK);
        setSpinnerInfo(mS2Spinner,   zeroDate, Color.BLACK);
        setSpinnerInfo(mE3Spinner,   zeroDate, Color.BLACK);
        setSpinnerInfo(mS3Spinner,   zeroDate, Color.BLACK);
        setSpinnerInfo(mHourSpinner, zeroDate, Color.BLACK);

        mNotificationController.stopNotification();
    }

    /**
     * spawnErrorDialog
     */
    public static void spawnErrorDialog(String text) {
        JOptionPane.showMessageDialog(null, text, TITLE, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * spawnHelpDialog
     */
    public static void spawnHelpDialog() {
        String helpText =
                "<html>" +
                    "<h1>Relógio de Ponto v1.1.0.0</h1>" +
                    "<pre>" +
                        "+-------+---------+--------+-------+-------+--------+-------------------------------+<br>" +
                        "|   #   | Chegada | Almoço | Volta | Saída | Tempo  |             Ação              |<br>" +
                        "| Caso  |  (e1)   |  (s1)  | (e2)  | (s3)  | (hour) |   ( Calcular saída para: )    |<br>" +
                        "+-------+---------+--------+-------+-------+--------+-------------------------------+<br>" +
                        "|   1   |    1    |   0    |   0   |   0   |   0    | 1h de almoço e 8h de trabalho |<br>" +
                        "|   2   |    1    |   0    |   0   |   0   |   1    | 1h de almoço e Xh de trabalho |<br>" +
                        "|   3   |    1    |   1    |   0   |   0   |   0    | 1h de almoço e 8h de trabalho |<br>" +
                        "|   4   |    1    |   1    |   0   |   0   |   1    | 1h de almoço e Xh de trabalho |<br>" +
                        "|   5   |    1    |   1    |   1   |   0   |   0    | Xh de almoço e 8h de trabalho |<br>" +
                        "|   6   |    1    |   1    |   1   |   0   |   1    | Xh de almoço e Xh de trabalho |<br>" +
                        "+-------+---------+--------+-------+-------+--------+-------------------------------+<br>" +
                    "</pre>" +
                    "<br><br>" +
                    "<pre>" +
                        "           Available on: https://github.com/gustavocelani/relogio_de_ponto<br><br>" +
                        "                Copyright (C) Gustavo Celani - All Rights Reserved<br>" +
                        "           Written by Gustavo Celani &lt;gustavo_celani@hotmail.com&gt;, 2021<br>" +
                    "</pre>" +
                "</html>";

        JLabel label = new JLabel(helpText);
        label.setFont(FONT_SMALL);
        JOptionPane.showMessageDialog(null, label, TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * setSpinnerInfo
     * @param spinner spinner
     * @param date date
     * @param color color
     */
    public static void setSpinnerInfo(JSpinner spinner, Date date, Color color) {
        JFormattedTextField textField = ((JSpinner.DateEditor) spinner.getEditor()).getTextField();

        if (date != null) {
            spinner.setValue(date);
        }

        if (color != null) {
            textField.setForeground(color);
        }
    }
}
