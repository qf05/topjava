package ru.javawebinar.topjava;

//import org.junit.rules.ExternalResource;
//import org.junit.rules.Stopwatch;
//import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class TimingRules {
    private static final Logger log = LoggerFactory.getLogger("result");

    private static StringBuilder results = new StringBuilder();

    private static long time = 0;
    private static String methodName;

    public static void start(){
        results.setLength(0);
    }

    public static void beginTest( String name){
        time = LocalTime.now().toNanoOfDay();
        methodName = name;
    }

    public static void finishTest(){
        time = LocalTime.now().toNanoOfDay() - time;
        String info = String.format("%-95s %7d\n", methodName, TimeUnit.NANOSECONDS.toMillis(time));
        results.append(info);
        log.info(info);
    }

    public static void end(){
        log.info("\n-------------------------------------------------------------------------------------------------------" +
                "\nTest                                                                                       Duration, ms" +
                "\n-------------------------------------------------------------------------------------------------------\n" +
                results +
                "-------------------------------------------------------------------------------------------------------\n");
    }



    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
//    public static final Stopwatch STOPWATCH = new Stopwatch() {
//        @Override
//        protected void finished(long nanos, Description description) {
//            String result = String.format("%-95s %7d", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
//            results.append(result).append('\n');
//            log.info(result + " ms\n");
//        }
//    };
//
//    public static final ExternalResource SUMMARY = new ExternalResource() {
//        @Override
//        protected void before() throws Throwable {
//            results.setLength(0);
//        }
//
//        @Override
//        protected void after() {
//            log.info("\n-------------------------------------------------------------------------------------------------------" +
//                    "\nTest                                                                                       Duration, ms" +
//                    "\n-------------------------------------------------------------------------------------------------------\n" +
//                    results +
//                    "-------------------------------------------------------------------------------------------------------\n");
//        }
//    };
}
