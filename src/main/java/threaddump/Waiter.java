// Keeps a few threads in different states, so that a thread dump has
// something to show: jcmd <pid> Thread.dump_to_file -format=json <file>
final Object lock = new Object();

void main() {
  try (ExecutorService pool = Executors.newFixedThreadPool(2)) {
    pool.submit(this::waitUntilInterrupted);
    Thread.ofVirtual().name("sleeper").start(this::sleepTenMinutes);
    sleepTenMinutes();
  }
}

void waitUntilInterrupted() {
  synchronized (lock) {
    try {
      while (!Thread.currentThread().isInterrupted()) {
        lock.wait();
      }
    } catch (InterruptedException _) {
      Thread.currentThread().interrupt();
    }
  }
}

void sleepTenMinutes() {
  try {
    Thread.sleep(Duration.ofMinutes(10));
  } catch (InterruptedException _) {
    Thread.currentThread().interrupt();
  }
}
