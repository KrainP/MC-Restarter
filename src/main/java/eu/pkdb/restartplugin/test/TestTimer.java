package eu.pkdb.restartplugin.test;

import eu.pkdb.restartplugin.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TestTimer extends TimerTask {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        long dd = TimeUnit.MINUTES.toMinutes(1);
        LocalDateTime executionTime = now.plusMinutes(dd);
        System.out.println("Will be executed at: " + executionTime);
        Timer timer = new Timer();
        timer.schedule(new TestTimer(), DateUtil.convertToDateViaInstant(executionTime));
    }

    @Override
    public void run() {
        System.out.println("RESTARTER WAS EXECUTED");
    }

}
