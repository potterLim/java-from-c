package com.example.userdefinedtypes;

public final class UserDefinedTypesExample {
    private UserDefinedTypesExample() {
    }

    public static void main(String[] args) {
        printHeader("User-Defined Types");

        demoMagicNumbers();
        demoEnums();
        demoClasses();
        demoReferenceTypes();
        demoDefaultValues();
        demoConstructors();
        demoAccessModifiers();
        demoStaticMembers();
    }

    private static void demoMagicNumbers() {
        printSectionTitle("Primitives + magic numbers");

        int status = 0; // 0: queued, 1: downloading, 2: completed
        long downloadedBytes = 0;
        long totalBytes = 10_000;

        status = 999;          // Unknown status becomes possible.
        downloadedBytes = -50; // Impossible progress becomes possible.

        System.out.println("status           = " + status);
        System.out.println("downloadedBytes  = " + downloadedBytes);
        System.out.println("totalBytes       = " + totalBytes);
        System.out.println("When meaning and rules are not part of the type, misuse becomes easy.");
    }

    private static void demoEnums() {
        printSectionTitle("enum: a named type for related constants");

        EDownloadStatus status = EDownloadStatus.QUEUED;
        System.out.println("status = " + status);

        status = EDownloadStatus.DOWNLOADING;
        System.out.println("status = " + status);

        // The following line does not compile:
        // status = 1;

        printStatusMessage(status);
    }

    private static void printStatusMessage(EDownloadStatus status) {
        switch (status) {
            case QUEUED:
                System.out.println("The task is waiting to start.");
                break;
            case DOWNLOADING:
                System.out.println("The task is in progress.");
                break;
            case COMPLETED:
                System.out.println("The task finished successfully.");
                break;
            case FAILED:
                System.out.println("The task finished with an error.");
                break;
            default:
                assert (false) : "Unexpected download status: " + status;
        }
    }

    private static void demoClasses() {
        printSectionTitle("class: state + behavior in one type");

        DownloadTask task = new DownloadTask("notes.pdf", 10_000);

        task.start();
        task.addProgress(3_000);
        task.addProgress(7_000);

        System.out.println("fileName         = " + task.getFileName());
        System.out.println("status           = " + task.getStatus());
        System.out.println("downloadedBytes  = " + task.getDownloadedBytes());
        System.out.println("totalBytes       = " + task.getTotalBytes());
    }

    private static void demoReferenceTypes() {
        printSectionTitle("Objects are reference types");

        DownloadTask primaryTask = new DownloadTask("slide.pptx", 25_000);
        DownloadTask aliasTask = primaryTask; // Two variables refer to the same object.

        aliasTask.start();
        aliasTask.addProgress(5_000);

        System.out.println("primaryTask downloadedBytes = " + primaryTask.getDownloadedBytes());
        System.out.println("aliasTask downloadedBytes   = " + aliasTask.getDownloadedBytes());

        primaryTask = null; // This variable now refers to no object.
        System.out.println("primaryTask is null: " + (primaryTask == null));

        // Calling a method through null causes a runtime error (NullPointerException):
        // primaryTask.start();
    }

    private static void demoDefaultValues() {
        printSectionTitle("Default values can hide missing initialization");

        UserProfile profile = new UserProfile();
        profile.printSummary();

        System.out.println("A newly created object already has values (0, false, null), even if nothing was set.");
        System.out.println("Those defaults may not match the meaning you want.");
    }

    private static void demoConstructors() {
        printSectionTitle("Constructors and constructor overloading");

        UserProfile guest = new UserProfile("guest");
        guest.printSummary();

        UserProfile subscriber = new UserProfile("alice", ESubscriptionTier.PRO);
        subscriber.printSummary();
    }

    private static void demoAccessModifiers() {
        printSectionTitle("Access modifiers + public methods");

        DownloadTask task = new DownloadTask("video.mp4", 100_000);

        // The following line does not compile (private field access):
        // task.downloadedBytes = 123;

        task.start();
        task.addProgress(50_000);

        long progressSnapshot = task.getDownloadedBytes();
        System.out.println("progressSnapshot = " + progressSnapshot);
    }

    private static void demoStaticMembers() {
        printSectionTitle("static members belong to the class");

        int createdTaskCountBefore = DownloadTask.getCreatedTaskCount();

        DownloadTask firstTask = new DownloadTask("a.txt", 10);
        DownloadTask secondTask = new DownloadTask("b.txt", 20);

        int createdTaskCountAfter = DownloadTask.getCreatedTaskCount();

        System.out.println("createdTaskCount (before) = " + createdTaskCountBefore);
        System.out.println("createdTaskCount (after)  = " + createdTaskCountAfter);
        System.out.println("created in this section   = " + (createdTaskCountAfter - createdTaskCountBefore));

        System.out.println("firstTask id = " + firstTask.getId());
        System.out.println("secondTask id = " + secondTask.getId());
        System.out.println("A static field is shared by all objects of the class.");
    }

    private static void printHeader(String title) {
        System.out.println(title);
        System.out.println("==================================================");
    }

    private static void printSectionTitle(String title) {
        System.out.println();
        System.out.println("[" + title + "]");
        System.out.println("--------------------------------------------------");
    }
}
