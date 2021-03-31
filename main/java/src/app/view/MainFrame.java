package app.view;

import app.control.CasesController;
import app.control.NotificationController;
import app.control.ResourcesLoader;
import app.control.DateUtils;

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

    /** Notification Controller */
    private final NotificationController mNotificationController;
    /** Cases Controller */
    private final CasesController mCasesController;

    /** Time Pickers */
    private JSpinner mE1Spinner;
    private JSpinner mS1Spinner;
    private JSpinner mE2Spinner;
    private JSpinner mS2Spinner;
    private JSpinner mHourSpinner;

    /**
     * Constructor
     * Build Frame
     */
    public MainFrame() {
        super();

        this.setSize(320, 520);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ResourcesLoader().loadIcon());
        this.setTitle(TITLE);
        this.setResizable(false);
        this.setVisible(true);

        mNotificationController = new NotificationController(this);
        mCasesController = new CasesController(mNotificationController);

        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boxPanel.add(buildHoursPanel());
        boxPanel.add(buildCalcButton());
        boxPanel.add(buildExtraButtonsPanel());

        getContentPane().add(boxPanel);
    }

    /**
     * buildHoursPanel
     * @return Hour Panel
     */
    private JPanel buildHoursPanel() {
        JPanel hoursPanel = new JPanel();
        hoursPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hoursPanel.setLayout(new GridLayout(5, 2, 10, 20));
        hoursPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        hoursPanel.add(buildLabel("Chegada", "Horário de chegada"));
        mE1Spinner = buildTimePicker();
        hoursPanel.add(mE1Spinner);

        hoursPanel.add(buildLabel("Almoço", "Saída para o almoço"));
        mS1Spinner = buildTimePicker();
        hoursPanel.add(mS1Spinner);

        hoursPanel.add(buildLabel("Volta", "Horário de volta do almoço"));
        mE2Spinner = buildTimePicker();
        hoursPanel.add(mE2Spinner);

        hoursPanel.add(buildLabel("Saída", "Horário de saída"));
        mS2Spinner = buildTimePicker();
        hoursPanel.add(mS2Spinner);

        hoursPanel.add(buildLabel("Tempo", "Tempo total de trabalho"));
        mHourSpinner = buildTimePicker();
        hoursPanel.add(mHourSpinner);

        return hoursPanel;
    }

    /**
     * buildButtonsPanel
     * @return Buttons Panel
     */
    private JPanel buildExtraButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.setLayout(new GridLayout(1, 2, 20, 0));

        buttonsPanel.add(buildHelpButton());
        buttonsPanel.add(buildResetButton());

        return buttonsPanel;
    }

    /**
     * buildLabel
     * @param text Label text
     * @param toolTipText Tool Tip Text
     * @return Label
     */
    private JLabel buildLabel(String text, String toolTipText) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setToolTipText(toolTipText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setFont(new Font(FONT, Font.BOLD, 26));
        label.setVisible(true);
        return label;
    }

    /**
     * buildTimePicker
     * @return TimePicker
     */
    private JSpinner buildTimePicker() {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        spinner.setVisible(true);
        spinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinner.setFont(new Font(FONT, Font.BOLD, 32));
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
    private JPanel buildCalcButton() {
        Button button = new Button();
        button.setLabel("Agendar Saída");
        button.setBackground(Color.LIGHT_GRAY);
        button.setFont(new Font(FONT, Font.BOLD, 32));

        button.addActionListener(e -> mCasesController.calc(
                mE1Spinner,
                mS1Spinner,
                mE2Spinner,
                mS2Spinner,
                mHourSpinner));

        JPanel panel = new JPanel();
        panel.add(button);

        return panel;
    }

    /**
     * buildHelpButton
     * @return Help Button
     */
    private JPanel buildHelpButton() {
        Button button = new Button();
        button.setLabel("Ajuda");
        button.setBackground(Color.LIGHT_GRAY);
        button.setFont(new Font(FONT, Font.BOLD, 32));

        button.addActionListener(e -> spawnHelpDialog());

        JPanel panel = new JPanel();
        panel.add(button);

        return panel;
    }

    /**
     * buildResetButton
     * @return Reset Button
     */
    private JPanel buildResetButton() {
        Button button = new Button();
        button.setLabel("Reset");
        button.setBackground(Color.LIGHT_GRAY);
        button.setFont(new Font(FONT, Font.BOLD, 32));

        button.addActionListener(e -> resetTimePickers());

        JPanel panel = new JPanel();
        panel.add(button);

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
                    "<h1>Relógio de Ponto v1.0.0.0</h1>" +
                    "<pre>" +
                        "+-------+---------+--------+-------+-------+--------+-------------------------------+<br>" +
                        "|   #   | Chegada | Almoço | Volta | Saída | Tempo  |             Ação              |<br>" +
                        "| Caso  |  (e1)   |  (s1)  | (e2)  | (s2)  | (hour) |   ( Calcular saída para: )    |<br>" +
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
        label.setFont(new Font(FONT, Font.BOLD, 20));
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
