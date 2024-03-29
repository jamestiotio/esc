import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

// Parallelize the crawl method with the help of a thread pool.
// Implemented a multi-threaded executor just for fun, along with the required compulsory blocking
// queue specification.
public class GDesktopWithThreadPool {
    private final static ExecutorService executor = new ScheduledThreadPoolExecutor(100);

    private final static int BOUND = 20;
    private final static int N_CONSUMERS = 4;

    public static void startIndexing(File[] roots) {
        // Linked blocking queue here.
        final BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
        final FileFilter filter = new FileFilter() {
            public boolean accept(File file) {
                return true;
            }
        };
    }

    public void ParallelRecursiveFileCrawler(File[] roots, final FileFilter filter,
            final BlockingQueue<File> queue) {
        // Parallelizing the FileCrawler.
        for (final File root : roots) {
            executor.execute(new Runnable() {
                public void run() {
                    File[] entries = root.listFiles(filter);

                    if (entries != null) {
                        for (File entry : entries) {
                            if (entry.isDirectory()) {
                                try {
                                    crawl(entry, filter, queue);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    queue.put(entry);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private void crawl(File root, final FileFilter fileFilter, final BlockingQueue<File> fileQueue)
            throws InterruptedException {
        File[] entries = root.listFiles(fileFilter);

        if (entries != null) {
            for (File entry : entries) {
                if (entry.isDirectory()) {
                    crawl(entry, fileFilter, fileQueue);
                } else {
                    fileQueue.put(entry);
                }
            }
        }
    }

    // Parallelizing the Indexer.
    public void ParallelRecursiveIndexer(File[] roots, final BlockingQueue<File> queue) {
        // Parallelizing the FileCrawler.
        for (final File root : roots) {
            executor.execute(new Runnable() {
                public void run() {
                    try {
                        while (true) {
                            indexFile(queue.take());
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
    }

    private void indexFile(File file) {
        // Code for analyzing the context of the file is skipped for simplicity.
    }
}


class FileCrawler extends Thread {
    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    FileCrawler(BlockingQueue<File> queue, FileFilter filter, File root) {
        this.fileQueue = queue;
        this.fileFilter = filter;
        this.root = root;
    }

    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void crawl(File root) throws InterruptedException {
        File[] entries = root.listFiles(fileFilter);

        if (entries != null) {
            for (File entry : entries) {
                if (entry.isDirectory()) {
                    crawl(entry);
                } else {
                    fileQueue.put(entry);
                }
            }
        }
    }
}


class Indexer extends Thread {
    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                indexFile(queue.take());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void indexFile(File file) {
        // Code for analyzing the context of the file is skipped for simplicity.
    }
}
