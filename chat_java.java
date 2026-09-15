package practice;

class Threadchat extends Thread {

    private boolean suspended = false;
    private boolean running = true;

    // Constructor
    Threadchat(String name, int priority) {
        super(name);
        setPriority(priority);
    }

    // Code executed by the thread
    public void run() {

        String[] messages = {
            "Hello babes",
            "How are you, I am fine",
            "Would you like to have a cup of coffee with me",
            "Bye",
            "Good night"
        };

        for (String message : messages) {

            synchronized (this) {

                // Suspend thread
                while (suspended) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        System.out.println(getName() + " interrupted");
                    }
                }

                // Stop thread
                if (!running) {
                    System.out.println(getName() + " Stopped");
                    break;
                }
            }

            // Send message
            System.out.println(
                getName() + " sends: " + message +
                " | Priority: " + getPriority()
            );

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(getName() + " Interrupted");
            }
        }

        System.out.println(getName() + " Finished Chatting");
    }

    // Suspend thread
    public synchronized void suspendChat() {
        suspended = true;
        System.out.println(getName() + " is Suspended");
    }

    // Resume thread
    public synchronized void resumeChat() {
        suspended = false;
        notify();
        System.out.println(getName() + " is Resumed");
    }

    // Stop thread
    public synchronized void stopChat() {
        running = false;
        suspended = false;
        notify();
        System.out.println(getName() + " is Stopped");
    }
}


public class chat_java {

    public static void main(String[] args) {

        // Create three users with different priorities
        Threadchat user1 =
            new Threadchat("User-1", Thread.NORM_PRIORITY);

        Threadchat user2 =
            new Threadchat("User-2", Thread.MAX_PRIORITY);

        Threadchat user3 =
            new Threadchat("User-3", Thread.MIN_PRIORITY);

        // Start all threads
        user1.start();
        user2.start();
        user3.start();

        // Check whether threads are alive
        System.out.println("\nThread Status:");

        System.out.println(
            "User-1 alive: " + user1.isAlive()
        );

        System.out.println(
            "User-2 alive: " + user2.isAlive()
        );

        System.out.println(
            "User-3 alive: " + user3.isAlive()
        );

        try {

            // Allow threads to run
            Thread.sleep(1000);

            // Suspend User-1
            user1.suspendChat();

            Thread.sleep(1000);

            // Resume User-1
            user1.resumeChat();

            Thread.sleep(1000);

            // Stop User-3
            user3.stopChat();

            // Wait for all threads to finish
            user1.join();
            user2.join();
            user3.join();

        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        // Check final status
        System.out.println("\nFinal Thread Status:");

        System.out.println(
            "User-1 alive: " + user1.isAlive()
        );

        System.out.println(
            "User-2 alive: " + user2.isAlive()
        );

        System.out.println(
            "User-3 alive: " + user3.isAlive()
        );

        System.out.println("\nChat application ended.");
    }
}