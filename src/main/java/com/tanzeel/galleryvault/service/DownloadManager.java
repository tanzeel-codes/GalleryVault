package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.exception.DownloadFailedException;
import com.tanzeel.galleryvault.exception.DownloadJobNotFoundException;
import com.tanzeel.galleryvault.model.DownloadJob;
import com.tanzeel.galleryvault.model.DownloadJobStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.*;


@Service
public class DownloadManager {

    private final DownloadService downloadService;
    private final ConcurrentHashMap<UUID, DownloadJob> jobs;
    private final ExecutorService executorService;
    private final ScheduledExecutorService cleanupScheduler;

    public DownloadManager(DownloadService downloadService) {
        this.downloadService = downloadService;
        this.jobs = new ConcurrentHashMap<>();
        this.executorService = Executors.newCachedThreadPool();
        this.cleanupScheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public DownloadJob startDownload(String url) {
        DownloadJob job = new DownloadJob(url);

        jobs.put(job.getId() , job);

        executorService.submit(() -> {

            job.setStatus(DownloadJobStatus.DOWNLOADING);

            try{

                downloadService.download(url, line -> {
                    // For future progress/status updates.
                });

                job.setStatus(DownloadJobStatus.COMPLETED);

            }
            catch (DownloadFailedException e) {

                job.setStatus(DownloadJobStatus.FAILED);
            }
            finally {
                job.setCompletedAt(LocalDateTime.now());

                scheduleCleanup(job);
            }

        });

        return job;
    }

    private void scheduleCleanup(DownloadJob job) {

        cleanupScheduler.schedule(() ->
                jobs.remove(job.getId()),
                5,
                TimeUnit.MINUTES
        );
    }

    public DownloadJob getJob(UUID jobId) {

        DownloadJob job = jobs.get(jobId);

        if(job == null)  {
            throw new DownloadJobNotFoundException("Download job not found.");
        }

        return job;
    }
}
