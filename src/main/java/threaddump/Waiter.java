// Keeps a few threads in different states, so that a thread dump has
// something to show: jcmd <pid> Thread.dump_to_file -format=json <file>
void main() throws InterruptedException {
  Object lock = new Object();
  ExecutorService pool = Executors.newFixedThreadPool(2);
  pool.submit(() -> {
    synchronized (lock) {
      lock.wait();
    }
    return null;
  });
  Thread.ofVirtual().name("sleeper").start(() -> sleepTenMinutes());
  sleepTenMinutes();
}

void sleepTenMinutes() {
  try {
    Thread.sleep(Duration.ofMinutes(10));
  } catch (InterruptedException e) {
    Thread.currentThread().interrupt();
  }
}
