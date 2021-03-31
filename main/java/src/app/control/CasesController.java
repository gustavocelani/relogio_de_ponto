package app.control;

import app.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Cases Controller
 *
 * +-------+---------+--------+-------+-------+--------+-------------------------------+
 * |   #   | Chegada | Almoço | Volta | Saída | Tempo  |             Ação              |
 * | Caso  |  (e1)   |  (s1)  | (e2)  | (s2)  | (hour) |   ( Calcular saída para: )    |
 * +-------+---------+--------+-------+-------+--------+-------------------------------+
 * |   1   |    1    |   0    |   0   |   0   |   0    | 1h de almoço e 8h de trabalho |
 * |   2   |    1    |   0    |   0   |   0   |   1    | 1h de almoço e Xh de trabalho |
 * |   3   |    1    |   1    |   0   |   0   |   0    | 1h de almoço e 8h de trabalho |
 * |   4   |    1    |   1    |   0   |   0   |   1    | 1h de almoço e Xh de trabalho |
 * |   5   |    1    |   1    |   1   |   0   |   0    | Xh de almoço e 8h de trabalho |
 * |   6   |    1    |   1    |   1   |   0   |   1    | Xh de almoço e Xh de trabalho |
 * +-------+---------+--------+-------+-------+--------+-------------------------------+
 */
public class CasesController {

    /**
     * Time Picker Indexes
     */
    private static final int INDEX_E1   = 0;
    private static final int INDEX_S1   = 1;
    private static final int INDEX_E2   = 2;
    private static final int INDEX_S2   = 3;
    private static final int INDEX_HOUR = 4;

    /**
     * Dates BitMasks
     */
    private static final int BITMASK_E1   = 0b10000;
    private static final int BITMASK_S1   = 0b01000;
    private static final int BITMASK_E2   = 0b00100;
    private static final int BITMASK_S2   = 0b00000; // Ignored
    private static final int BITMASK_HOUR = 0b00001;

    /**
     * Cases BitMaps
     */
    private static final int BITMAP_CASE_1 = BITMASK_E1;
    private static final int BITMAP_CASE_2 = BITMASK_E1|BITMASK_HOUR;
    private static final int BITMAP_CASE_3 = BITMASK_E1|BITMASK_S1;
    private static final int BITMAP_CASE_4 = BITMASK_E1|BITMASK_S1|BITMASK_HOUR;
    private static final int BITMAP_CASE_5 = BITMASK_E1|BITMASK_S1|BITMASK_E2;
    private static final int BITMAP_CASE_6 = BITMASK_E1|BITMASK_S1|BITMASK_E2|BITMASK_HOUR;

    /** Notification Controller */
    private final NotificationController mNotificationController;

    /**
     * Constructor
     * @param notificationController notificationController
     */
    public CasesController(NotificationController notificationController) {
        this.mNotificationController = notificationController;
    }

    /**
     * calc
     * @param e1Spinner   Entrada 1 (Entrada)
     * @param s1Spinner   Saída   1 (Almoço)
     * @param e2Spinner   Entrada 2 (Volta)
     * @param s2Spinner   Saída   2 (Saída)
     * @param hourSpinner Tempo
     */
    public void calc(
            JSpinner e1Spinner,
            JSpinner s1Spinner,
            JSpinner e2Spinner,
            JSpinner s2Spinner,
            JSpinner hourSpinner) {

        ArrayList<JSpinner> spinners = new ArrayList<>(Arrays.asList(
                e1Spinner,
                s1Spinner,
                e2Spinner,
                s2Spinner,
                hourSpinner));

        ArrayList<Date> dates = spinnerArrayToDate(spinners);
        if (dates.size() != 5) {
            return;
        }

        int case_bitmap = caseTriage(dates);
        switch (case_bitmap) {
            case BITMAP_CASE_1:
                case1(dates, spinners);
                break;
            case BITMAP_CASE_2:
                case2(dates, spinners);
                break;
            case BITMAP_CASE_3:
                case3(dates, spinners);
                break;
            case BITMAP_CASE_4:
                case4(dates, spinners);
                break;
            case BITMAP_CASE_5:
                case5(dates, spinners);
                break;
            case BITMAP_CASE_6:
                case6(dates, spinners);
                break;
            default:
                MainFrame.spawnErrorDialog("Caso não mapeado...");
                MainFrame.spawnHelpDialog();
                break;
        }
    }

    /**
     * case1
     * @param dates dates
     * @param spinners spinners
     *
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   #   | Chegada | Almoço | Volta | Saída | Tempo  |             Ação              |
     * |  Caso |  (e1)   |  (s1)  | (e2)  | (s2)  | (hour) |   ( Calcular saída para: )    |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   1   |    1    |   0    |   0   |   0   |   0    | 1h de almoço e 8h de trabalho |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     */
    private void case1(ArrayList<Date> dates, ArrayList<JSpinner> spinners) {
        JSpinner s1Spinner   = spinners.get(INDEX_S1);
        JSpinner e2Spinner   = spinners.get(INDEX_E2);
        JSpinner s2Spinner   = spinners.get(INDEX_S2);
        JSpinner hourSpinner = spinners.get(INDEX_HOUR);

        Date e1Date = dates.get(INDEX_E1);

        Date s1Date = Date.from(e1Date.toInstant().plus(Duration.ofHours(4)));
        Date e2Date = Date.from(s1Date.toInstant().plus(Duration.ofHours(1)));
        Date s2Date = Date.from(e2Date.toInstant().plus(Duration.ofHours(4)));
        Date hourDate = DateUtils.buildDate(8, 0);

        MainFrame.setSpinnerInfo(s1Spinner,   s1Date,   Color.BLUE);
        MainFrame.setSpinnerInfo(e2Spinner,   e2Date,   Color.BLUE);
        MainFrame.setSpinnerInfo(s2Spinner,   s2Date,   Color.GREEN);
        MainFrame.setSpinnerInfo(hourSpinner, hourDate, Color.BLUE);

        mNotificationController.updateSchedulerTimer(s2Date);
    }

    /**
     * case2
     * @param dates dates
     * @param spinners spinners
     *
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   #   | Chegada | Almoço | Volta | Saída | Tempo  |             Ação              |
     * |  Caso |  (e1)   |  (s1)  | (e2)  | (s2)  | (hour) |   ( Calcular saída para: )    |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   2   |    1    |   0    |   0   |   0   |   1    | 1h de almoço e Xh de trabalho |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     */
    private void case2(ArrayList<Date> dates, ArrayList<JSpinner> spinners) {
        JSpinner s1Spinner = spinners.get(INDEX_S1);
        JSpinner e2Spinner = spinners.get(INDEX_E2);
        JSpinner s2Spinner = spinners.get(INDEX_S2);

        Date e1Date   = dates.get(INDEX_E1);
        Date hourDate = dates.get(INDEX_HOUR);

        int halfMinWork = (60 * DateUtils.extractHourFromDate(hourDate) + DateUtils.extractMinFromDate(hourDate)) / 2;

        Date s1Date = Date.from(e1Date.toInstant().plus(Duration.ofMinutes(halfMinWork)));
        Date e2Date = Date.from(s1Date.toInstant().plus(Duration.ofHours(1)));
        Date s2Date = Date.from(e2Date.toInstant().plus(Duration.ofMinutes(halfMinWork)));

        MainFrame.setSpinnerInfo(s1Spinner, s1Date, Color.BLUE);
        MainFrame.setSpinnerInfo(e2Spinner, e2Date, Color.BLUE);
        MainFrame.setSpinnerInfo(s2Spinner, s2Date, Color.GREEN);

        mNotificationController.updateSchedulerTimer(s2Date);
    }

    /**
     * case3
     * @param dates dates
     * @param spinners spinners
     *
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   #   | Chegada | Almoço | Volta | Saída | Tempo  |             Ação              |
     * |  Caso |  (e1)   |  (s1)  | (e2)  | (s2)  | (hour) |   ( Calcular saída para: )    |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   3   |    1    |   1    |   0   |   0   |   0    | 1h de almoço e 8h de trabalho |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     */
    private void case3(ArrayList<Date> dates, ArrayList<JSpinner> spinners) {
        JSpinner e2Spinner   = spinners.get(INDEX_E2);
        JSpinner s2Spinner   = spinners.get(INDEX_S2);
        JSpinner hourSpinner = spinners.get(INDEX_HOUR);

        Date e1Date = dates.get(INDEX_E1);
        Date s1Date = dates.get(INDEX_S1);

        if (!e1Date.before(s1Date)) {
            MainFrame.spawnErrorDialog("Horários incoerentes...");
            return;
        }

        Date hourDate = DateUtils.buildDate(8, 0);
        long workedMinutes  = DateUtils.diffInMinutes(e1Date, s1Date);

        if (workedMinutes > (8 * 60)) {
            MainFrame.spawnErrorDialog("Horários incoerentes...");
            return;
        }

        long missingMinutes = DateUtils.diffInMinutes(
                Date.from(DateUtils.buildZeroDate().toInstant().plus(Duration.ofMinutes(workedMinutes))),
                hourDate);

        Date e2Date = Date.from(s1Date.toInstant().plus(Duration.ofHours(1)));
        Date s2Date = Date.from(e2Date.toInstant().plus(Duration.ofMinutes(missingMinutes)));

        MainFrame.setSpinnerInfo(e2Spinner,   e2Date,   Color.BLUE);
        MainFrame.setSpinnerInfo(s2Spinner,   s2Date,   Color.GREEN);
        MainFrame.setSpinnerInfo(hourSpinner, hourDate, Color.BLUE);

        mNotificationController.updateSchedulerTimer(s2Date);
    }

    /**
     * case4
     * @param dates dates
     * @param spinners spinners
     *
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   #   | Chegada | Almoço | Volta | Saída | Tempo  |             Ação              |
     * |  Caso |  (e1)   |  (s1)  | (e2)  | (s2)  | (hour) |   ( Calcular saída para: )    |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   4   |    1    |   1    |   0   |   0   |   1    | 1h de almoço e Xh de trabalho |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     */
    private void case4(ArrayList<Date> dates, ArrayList<JSpinner> spinners) {
        JSpinner e2Spinner = spinners.get(INDEX_E2);
        JSpinner s2Spinner = spinners.get(INDEX_S2);

        Date e1Date   = dates.get(INDEX_E1);
        Date s1Date   = dates.get(INDEX_S1);
        Date hourDate = dates.get(INDEX_HOUR);

        if (!e1Date.before(s1Date)) {
            MainFrame.spawnErrorDialog("Horários incoerentes...");
            return;
        }

        long hourMinutes   = DateUtils.extractHourFromDate(hourDate) * 60L + DateUtils.extractMinFromDate(hourDate);
        long workedMinutes = DateUtils.diffInMinutes(e1Date, s1Date);

        if (workedMinutes > hourMinutes) {
            MainFrame.spawnErrorDialog("Horários incoerentes...");
            return;
        }

        long missingMinutes = DateUtils.diffInMinutes(
                Date.from(DateUtils.buildZeroDate().toInstant().plus(Duration.ofMinutes(workedMinutes))),
                hourDate);

        Date e2Date = Date.from(s1Date.toInstant().plus(Duration.ofHours(1)));
        Date s2Date = Date.from(e2Date.toInstant().plus(Duration.ofMinutes(missingMinutes)));

        MainFrame.setSpinnerInfo(e2Spinner, e2Date, Color.BLUE);
        MainFrame.setSpinnerInfo(s2Spinner, s2Date, Color.GREEN);

        mNotificationController.updateSchedulerTimer(s2Date);
    }

    /**
     * case5
     * @param dates dates
     * @param spinners spinners
     *
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   #   | Chegada | Almoço | Volta | Saída | Tempo  |             Ação              |
     * |  Caso |  (e1)   |  (s1)  | (e2)  | (s2)  | (hour) |   ( Calcular saída para: )    |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   5   |    1    |   1    |   1   |   0   |   0    | Xh de almoço e 8h de trabalho |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     */
    private void case5(ArrayList<Date> dates, ArrayList<JSpinner> spinners) {
        JSpinner s2Spinner   = spinners.get(INDEX_S2);
        JSpinner hourSpinner = spinners.get(INDEX_HOUR);

        Date e1Date = dates.get(INDEX_E1);
        Date s1Date = dates.get(INDEX_S1);
        Date e2Date = dates.get(INDEX_E2);

        if (!e1Date.before(s1Date) || !s1Date.before(e2Date)) {
            MainFrame.spawnErrorDialog("Horários incoerentes...");
            return;
        }

        Date hourDate = DateUtils.buildDate(8, 0);

        long workedMinutes  = DateUtils.diffInMinutes(e1Date, s1Date);
        long missingMinutes = DateUtils.diffInMinutes(
                Date.from(DateUtils.buildZeroDate().toInstant().plus(Duration.ofMinutes(workedMinutes))),
                hourDate);

        Date s2Date = Date.from(e2Date.toInstant().plus(Duration.ofMinutes(missingMinutes)));

        MainFrame.setSpinnerInfo(s2Spinner,   s2Date,   Color.GREEN);
        MainFrame.setSpinnerInfo(hourSpinner, hourDate, Color.BLUE);

        mNotificationController.updateSchedulerTimer(s2Date);
    }

    /**
     * case6
     * @param dates dates
     * @param spinners spinners
     *
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   #   | Chegada | Almoço | Volta | Saída | Tempo  |             Ação              |
     * |  Caso |  (e1)   |  (s1)  | (e2)  | (s2)  | (hour) |   ( Calcular saída para: )    |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     * |   6   |    1    |   1    |   1   |   0   |   1    | Xh de almoço e Xh de trabalho |
     * +-------+---------+--------+-------+-------+--------+-------------------------------+
     */
    private void case6(ArrayList<Date> dates, ArrayList<JSpinner> spinners) {
        JSpinner s2Spinner = spinners.get(INDEX_S2);

        Date e1Date   = dates.get(INDEX_E1);
        Date s1Date   = dates.get(INDEX_S1);
        Date e2Date   = dates.get(INDEX_E2);
        Date hourDate = dates.get(INDEX_HOUR);

        if (!e1Date.before(s1Date) || !s1Date.before(e2Date)) {
            MainFrame.spawnErrorDialog("Horários incoerentes...");
            return;
        }

        long hourMinutes   = DateUtils.extractHourFromDate(hourDate) * 60L + DateUtils.extractMinFromDate(hourDate);
        long workedMinutes = DateUtils.diffInMinutes(e1Date, s1Date);

        if (hourMinutes <= workedMinutes) {
            MainFrame.spawnErrorDialog("Horários incoerentes...");
            return;
        }

        long missingMinutes = DateUtils.diffInMinutes(
                Date.from(DateUtils.buildZeroDate().toInstant().plus(Duration.ofMinutes(workedMinutes))),
                hourDate);

        Date s2Date = Date.from(e2Date.toInstant().plus(Duration.ofMinutes(missingMinutes)));
        MainFrame.setSpinnerInfo(s2Spinner, s2Date, Color.GREEN);

        mNotificationController.updateSchedulerTimer(s2Date);
    }

    /**
     * caseTriage
     * @param dates [e1, s1, e2, s2, hour]
     * @return Case bitmap
     */
    private static int caseTriage(ArrayList<Date> dates) {
        Date e1Date   = dates.get(INDEX_E1);
        Date s1Date   = dates.get(INDEX_S1);
        Date e2Date   = dates.get(INDEX_E2);
        Date s2Date   = dates.get(INDEX_S2);
        Date hourDate = dates.get(INDEX_HOUR);

        Date zeroDate = DateUtils.buildZeroDate();
        int case_bitmap = 0;

        if (!e1Date.equals(zeroDate)) {
            case_bitmap |= BITMASK_E1;
        }
        if (!s1Date.equals(zeroDate)) {
            case_bitmap |= BITMASK_S1;
        }
        if (!e2Date.equals(zeroDate)) {
            case_bitmap |= BITMASK_E2;
        }
        if (!s2Date.equals(zeroDate)) {
            case_bitmap |= BITMASK_S2;
        }
        if (!hourDate.equals(zeroDate)) {
            case_bitmap |= BITMASK_HOUR;
        }

        return case_bitmap;
    }

    /**
     * spinnerArrayToDate
     * @param spinners spinners
     * @return Dates
     */
    private static ArrayList<Date> spinnerArrayToDate(ArrayList<JSpinner> spinners) {
        ArrayList<Date> dates = new ArrayList<>();

        for (JSpinner spinner : spinners) {
            Date date = spinnerToDate(spinner);
            if (date == null) {
                MainFrame.setSpinnerInfo(spinner, null, Color.RED);
                break;
            }

            dates.add(spinnerToDate(spinner));
        }

        return dates;
    }

    /**
     * spinnerToDate
     * @param spinner spinner
     * @return Date
     */
    private static Date spinnerToDate(JSpinner spinner) {
        String time = ((JSpinner.DateEditor) spinner.getEditor()).getTextField().getText();
        String[] split = time.split(":");

        if (split.length != 2) {
            return null;
        }

        int hour;
        int min;

        try {
            hour = Integer.parseInt(split[0]);
            min  = Integer.parseInt(split[1]);

        } catch (NumberFormatException ex) {
            System.out.println("Fail to parse: " + time);
            return null;
        }

        return DateUtils.buildDate(hour, min);
    }
}
