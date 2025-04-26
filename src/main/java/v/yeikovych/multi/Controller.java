package v.yeikovych.multi;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

public class Controller extends Device {

    private boolean connected;
    private LocalDateTime lastActionTime;
    private final Map<String, Thread> scheduledActionThreads = new ConcurrentHashMap<>();
    private final List<ActionRecord> actionHistory = new CopyOnWriteArrayList<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private static class ActionRecord {
        private final String actionName;
        private final LocalDateTime executionTime;
        private final Object result;

        public ActionRecord(String actionName, LocalDateTime executionTime, Object result) {
            this.actionName = actionName;
            this.executionTime = executionTime;
            this.result = result;
        }

        @Override
        public String toString() {
            return String.format("Action: %s, Time: %s, Result: %s",
                    actionName, executionTime, result != null ? result.toString() : "void");
        }
    }

    public Controller(String name, String manufacturer, PowerSource powerSource, LocalDate installationDate) {
        super(name, manufacturer, powerSource, installationDate);
        this.connected = false;
    }

    @Override
    public void connect() {
        this.connected = true;
    }

    @Override
    public void disconnect() {
        this.connected = false;
    }

    @Override
    public String getStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Controller Status:\n");
        status.append("Name: ").append(getName()).append("\n");
        status.append("Manufacturer: ").append(getManufacturer()).append("\n");
        status.append("Power Source: ").append(getPowerSource()).append("\n");
        status.append("Installation Date: ").append(getInstallationDate()).append("\n");
        status.append("Connection Status: ").append(connected ? "Connected" : "Disconnected").append("\n");

        if (lastActionTime != null) {
            status.append("Last Action Time: ").append(lastActionTime).append("\n");
        } else {
            status.append("Last Action Time: None\n");
        }

        status.append("Scheduled Actions: ").append(scheduledActionThreads.size()).append("\n");

        status.append("Action History:\n");
        if (actionHistory.isEmpty()) {
            status.append("  No actions recorded\n");
        } else {
            actionHistory.forEach(job -> status.append("  ").append(job).append("\n"));
        }

        return status.toString();
    }

    public <T> T executeAction(String actionName, Callable<T> action) throws Exception {
        checkConnection();

        Future<T> future = executor.submit(action);
        T result = future.get();

        lastActionTime = LocalDateTime.now();
        actionHistory.add(new ActionRecord(actionName, lastActionTime, result));

        return result;
    }

    public void executeAction(String actionName, Runnable action) {
        checkConnection();

        Thread actionThread = new Thread(() -> {
            action.run();
            lastActionTime = LocalDateTime.now();
            actionHistory.add(new ActionRecord(actionName, lastActionTime, null));
        });

        actionThread.start();
    }

    public String scheduleAction(String actionName, Runnable action, LocalDateTime executionTime) {
        checkConnection();

        String taskId = UUID.randomUUID().toString();

        CountDownLatch waitComplete = new CountDownLatch(1);

        Thread.startVirtualThread(() -> {
            try {
                long delayMillis = Duration.between(LocalDateTime.now(), executionTime).toMillis();
                delayMillis = Math.max(0, delayMillis);

                if (delayMillis > 0) {
                    Thread.sleep(delayMillis);
                }
                waitComplete.countDown();
            } catch (InterruptedException e) {
                waitComplete.countDown();
            }
        });

        Thread actionThread = new Thread(() -> {
            try {
                waitComplete.await();

                action.run();
                lastActionTime = LocalDateTime.now();
                actionHistory.add(new ActionRecord(actionName, lastActionTime, null));

                scheduledActionThreads.remove(taskId);
            } catch (InterruptedException e) {
                actionHistory.add(new ActionRecord(actionName + " (CANCELLED)",
                        LocalDateTime.now(), "Cancelled"));
            }
        });

        actionThread.start();
        scheduledActionThreads.put(taskId, actionThread);

        return taskId;
    }

    public void cancelScheduledAction(String scheduledActionId) {
        Thread actionThread = scheduledActionThreads.get(scheduledActionId);
        if (actionThread == null) {
            throw new InvalidActionIdException("Invalid function ID provided: " + scheduledActionId +
                    ". No such scheduled action exists.");
        }

        actionThread.interrupt();
        scheduledActionThreads.remove(scheduledActionId);
    }

    public List<String> getActionHistory() {
        return actionHistory.stream().map(ActionRecord::toString).toList();
    }

    private void checkConnection() {
        if (!connected) {
            throw new ControllerNotConnectedException("Controller is not connected. Please connect before performing actions.");
        }
    }

    public boolean isConnected() {
        return connected;
    }
}
