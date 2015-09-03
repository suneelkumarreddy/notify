package in.notwork.notify.server;

import in.notwork.notify.client.queues.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Hello world!
 */
public final class ServerApp {

    private static final Logger LOG = LoggerFactory.getLogger(ServerApp.class);

    private static int defaultCount = 5;

    public static void main(final String... args) {
        checkAllPropertiesAreConfigured();
        if (validate(args)) {
            final int threadCount = getConsumerCount(args[0]);
            final List<Future<Queue>> futureList = new ArrayList<>();
            final ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            for (int i = 0; i < threadCount; i++) {
                final Callable<Queue> runnable = getTasks();
                final Future<Queue> future = executor.submit(runnable);
                futureList.add(future);
            }
            addShutdownHook(futureList, executor);
        }
    }

    private static void checkAllPropertiesAreConfigured() {
        throw new java.lang.UnsupportedOperationException("All properties are not configured!");
    }

    private static void addShutdownHook(final List<Future<Queue>> futureList, final ExecutorService executor) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shutdown(futureList, executor);
            }
        });
    }

    private static void shutdown(List<Future<Queue>> futureList, ExecutorService executor) {
        if (LOG.isWarnEnabled()) {
            LOG.warn("Interrupt received, killing server...");
        }
        try {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Attempting to shutdown queues...");
            }
            for (final Future<Queue> future : futureList) {
                final Queue queue = future.get();
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Disconnecting queue...");
                }
                queue.disconnect();
            }
            if (LOG.isWarnEnabled()) {
                LOG.warn("Now attempting to shutdown executor in next 10 seconds...");
            }
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Tasks interrupted by shutdown process. You may have an inconsistent state!");
            }
        } catch (ExecutionException e) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Error while retrieving the result of the tasks.", e.getCause());
            }
        } catch (TimeoutException e) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Timed out while waiting on the tasks to complete.", e.getCause());
            }
        } catch (IOException e) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Error while disconnecting the queues.", e.getCause());
            }
        } finally {
            if (!executor.isTerminated() && LOG.isWarnEnabled()) {
                LOG.warn("Forcing shutdown with non-finished tasks.");
            }
            executor.shutdownNow();
            if (LOG.isInfoEnabled()) {
                LOG.info("Shutdown finished.");
            }
        }
    }

    private static int getConsumerCount(final String arg) {
        int threadCount = Integer.parseInt(arg);
        threadCount = threadCount > defaultCount ? defaultCount : threadCount;
        if (LOG.isInfoEnabled()) {
            LOG.info("Creating " + threadCount + " consumers only.");
        }
        return threadCount;
    }

    private static boolean validate(final String... args) {
        if (null != args && args.length == 1) {
            return true;
        }
        System.out.println("Usage: ");
        System.out.println("    java -jar notify-server.jar <consumer_count>");
        System.out.println("Where: ");
        System.out.println("    consumer_count: Number of parallel consumers to run." +
                " Maximum allowed/default value is " + defaultCount + ".");
        return false;
    }

    private static Callable<Queue> getTasks() {
        return new Task();
    }
}
