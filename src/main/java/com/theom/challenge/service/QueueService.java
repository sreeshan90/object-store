package com.theom.challenge.service;

import com.theom.challenge.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/**
 * Class that mimics a queuing service
 */
public class QueueService implements Runnable {
    private final DBService dbService;
    private BlockingQueue<User> queue;
    private static Logger logger = LoggerFactory.getLogger(QueueService.class);

    public QueueService(BlockingQueue<User> queue, DBService dbService) {
        this.queue = queue;
        this.dbService = dbService;
    }
    // Long running thread that inserts users into DB
    public void run() {
        try {
                while (true) {

                    while(queue.isEmpty()) { // no users to insert, sleep
                        Thread.sleep(1000);
                    }
                    //get new user to insert from the queue
                    User user = queue.take();
                    logger.info("Processing User for insert : " + user.getUuid());
                    this.dbService.insert(user.getUuid(), user);
                }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}