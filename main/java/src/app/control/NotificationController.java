package app.control;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.TimerTask;

/**
 * Notification Controller
 */
public class NotificationController {

    /** Frame */
    private final JFrame mFrame;

    /** Icon Swap */
    private boolean mIconSwap;

    /** Notification Timer */
    private final Timer mNotificationTimer;

    /** Scheduler Timer */
    private java.util.Timer mSchedulerTimer;

    /** Resources Loader */
    private final ResourcesLoader mResourcesLoader;

    /**
     * Constructor
     * @param frame frame
     */
    public NotificationController(JFrame frame) {
        this.mFrame = frame;
        this.mResourcesLoader = new ResourcesLoader();

        final Image onImage = mResourcesLoader.loadIcon();
        final Image offImage = mResourcesLoader.loadGreenIcon();

        mNotificationTimer = new Timer(500, arg0 -> {
            mFrame.setIconImage(mIconSwap ? onImage : offImage);
            mIconSwap = !mIconSwap;
        });
        mNotificationTimer.setRepeats(true);
    }

    /**
     * updateSchedulerTimer
     * @param date date
     */
    public void updateSchedulerTimer(Date date) {
        if (mSchedulerTimer != null) {
            mSchedulerTimer.cancel();
            mSchedulerTimer = null;
        }

        TimerTask schedulerTimerTask = new TimerTask() {
            @Override
            public void run() {
                startNotification();
            }};

        mSchedulerTimer = new java.util.Timer();
        mSchedulerTimer.schedule(schedulerTimerTask, date);
    }

    /**
     * startNotification
     */
    public void startNotification() {
        mNotificationTimer.start();
    }

    /**
     * stopNotification
     */
    public void stopNotification() {
        if (isNotifying()) {
            mNotificationTimer.stop();
        }

        if (mSchedulerTimer != null) {
            mSchedulerTimer.cancel();
            mSchedulerTimer = null;
        }

        mFrame.setIconImage(mResourcesLoader.loadIcon());
    }

    /**
     * isNotifying
     * @return true if is notifying
     */
    public boolean isNotifying() {
        return mNotificationTimer.isRunning();
    }
}
