package com.example.userdefinedtypes;

public class DownloadTask {
    private static int sCreatedTaskCount;
    private static int sNextId = 1;

    private final int mId;
    private final String mFileName;
    private final long mTotalBytes;

    private EDownloadStatus mStatus;
    private long mDownloadedBytes;

    public DownloadTask(String fileNameOrNull, long totalBytes) {
        ++sCreatedTaskCount;
        mId = sNextId;
        ++sNextId;

        if (fileNameOrNull == null || fileNameOrNull.isEmpty()) {
            mFileName = "unknown";
        } else {
            mFileName = fileNameOrNull;
        }

        if (totalBytes < 0) {
            mTotalBytes = 0;
        } else {
            mTotalBytes = totalBytes;
        }

        mStatus = EDownloadStatus.QUEUED;
        mDownloadedBytes = 0;
    }

    public int getId() {
        return mId;
    }

    public String getFileName() {
        return mFileName;
    }

    public long getTotalBytes() {
        return mTotalBytes;
    }

    public long getDownloadedBytes() {
        return mDownloadedBytes;
    }

    public EDownloadStatus getStatus() {
        return mStatus;
    }

    public void start() {
        if (mStatus != EDownloadStatus.QUEUED) {
            return;
        }

        mStatus = EDownloadStatus.DOWNLOADING;
    }

    public void fail() {
        if (mStatus == EDownloadStatus.COMPLETED) {
            return;
        }

        mStatus = EDownloadStatus.FAILED;
    }

    public void addProgress(long bytes) {
        if (mStatus != EDownloadStatus.DOWNLOADING) {
            return;
        }

        if (bytes <= 0) {
            return;
        }

        mDownloadedBytes += bytes;

        if (mDownloadedBytes >= mTotalBytes) {
            mDownloadedBytes = mTotalBytes;
            mStatus = EDownloadStatus.COMPLETED;
        }
    }

    public static int getCreatedTaskCount() {
        return sCreatedTaskCount;
    }
}
