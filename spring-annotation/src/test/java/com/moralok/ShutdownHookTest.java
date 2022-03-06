package com.moralok;

/**
 * 程序正常结束 1-4-2-5
 * 程序阻塞期间 1-2-3-4-5
 *
 * @author moralok
 * @date 2022/3/4 9:44 上午
 */
public class ShutdownHookTest {

    public static void main(String[] args) {
        System.out.println("1: Main start");
        Thread main = Thread.currentThread();

        ShutdownHook shutDownHook = new ShutdownHook(main);
        Runtime.getRuntime().addShutdownHook(shutDownHook);

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            System.out.println("3: mainThread get interrupt signal.");
        }

        System.out.println("4: Main end");
    }

    public static class ShutdownHook extends Thread {

        private Thread main;

        public ShutdownHook(Thread mainThread) {
            this.main = mainThread;
        }

        @Override
        public void run() {
            // 进程结束时会运行
            System.out.println("2: Shut down signal received.");
            // main 没有阻塞的话，没有作用
            main.interrupt();
            try {
                // Thread.sleep(5 * 1000);
                // main结束了再join也无所谓
                main.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("5: Shut down complete.");
        }
    }
}
