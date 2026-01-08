package com.example.userdefinedtypes;

public class DownloadTask {
    private static int createdTaskCount;
    private static int nextId = 1;

    private final int id;
    private final String fileName;
    private final long totalBytes;

    private DownloadStatus status;
    private long downloadedBytes;

    public DownloadTask(String fileName, long totalBytes) {
        ++createdTaskCount;
        id = nextId;
        ++nextId;

        if (fileName == null || fileName.isEmpty()) {
            this.fileName = "unknown";
        } else {
            this.fileName = fileName;
        }

        if (totalBytes < 0) {
            this.totalBytes = 0;
        } else {
            this.totalBytes = totalBytes;
        }

        status = DownloadStatus.QUEUED;
        downloadedBytes = 0;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public long getDownloadedBytes() {
        return downloadedBytes;
    }

    public DownloadStatus getStatus() {
        return status;
    }

    public void start() {
        if (status != DownloadStatus.QUEUED) {
            return;
        }

        status = DownloadStatus.DOWNLOADING;
    }

    public void fail() {
        if (status == DownloadStatus.COMPLETED) {
            return;
        }

        status = DownloadStatus.FAILED;
    }

    public void addProgress(long bytes) {
        if (status != DownloadStatus.DOWNLOADING) {
            return;
        }

        if (bytes <= 0) {
            return;
        }

        downloadedBytes += bytes;

        if (downloadedBytes >= totalBytes) {
            downloadedBytes = totalBytes;
            status = DownloadStatus.COMPLETED;
        }
    }

    public static int getCreatedTaskCount() {
        return createdTaskCount;
    }
}
