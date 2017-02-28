package com.github.insanusmokrassar.iobject;

import com.github.insanusmokrassar.iobject.exceptions.ReadException;
import com.github.insanusmokrassar.iobject.exceptions.WriteException;
import com.github.insanusmokrassar.iobject.interfaces.IObject;
import com.github.insanusmokrassar.iobject.realisations.ConcurrentSimpleIObject;
import com.github.insanusmokrassar.iobject.realisations.SimpleIObject;

import java.util.concurrent.CountDownLatch;

public class Example {
    public static void main(String[] args) throws InterruptedException {
        IObject<String> exampleSimpleIObject = new SimpleIObject<>();
        try {
            exampleSimpleIObject.put("One", "One");
            System.out.println(exampleSimpleIObject.get("One"));
            System.out.println(exampleSimpleIObject.get("Two"));
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (ReadException e) {
            System.out.println("Can't get object: " + e.getMessage());
        }
        IObject<Integer> synchronisedSimpleIObject = new ConcurrentSimpleIObject<>();

        Integer sumOfThreads = 200;
        CountDownLatch startCountDown = new CountDownLatch(1);
        CountDownLatch stopCountDown = new CountDownLatch(sumOfThreads);
        for (int i = 0; i < sumOfThreads; i++) {
            new ExampleThread("Thread " + i, synchronisedSimpleIObject, i, i + sumOfThreads, startCountDown, stopCountDown).start();
        }
        startCountDown.countDown();
        stopCountDown.await();
        for (int i = 0; i < sumOfThreads * 2 - 1; i++) {
            try {
                System.out.println(
                        String.format(
                                "%s: %s",
                                "" + i,
                                synchronisedSimpleIObject.get("" + i)
                        )
                );
            } catch (ReadException e) {
                System.err.println("Error when try get value by key: " + i);
            }
        }
    }

    public static class ExampleThread extends Thread {
        protected IObject<Integer> targetIObject;
        protected Integer startNum;
        protected Integer endNum;
        protected CountDownLatch startCountDown;
        protected CountDownLatch stopCountDown;

        public ExampleThread(String name, IObject<Integer> targetIObject, Integer startNum, Integer endNum, CountDownLatch startCountDown, CountDownLatch stopCountDown) {
            super(name);
            this.targetIObject = targetIObject;
            this.startNum = startNum;
            this.endNum = endNum;
            this.startCountDown = startCountDown;
            this.stopCountDown = stopCountDown;
        }

        @Override
        public void run() {
            super.run();
            try {
                startCountDown.await();
            } catch (InterruptedException e) {
                return;
            }
            String name = Thread.currentThread().getName();
            for (int i = startNum; i < endNum; i++) {
                try {
                    targetIObject.put("" + i, targetIObject.get("" + i) + 1);
                    System.out.println(name + " put " + i);
                } catch (WriteException e) {
                    e.printStackTrace();
                } catch (ReadException e) {
                    try {
                        targetIObject.put("" + i, 1);
                    } catch (WriteException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            stopCountDown.countDown();
        }
    }
}
