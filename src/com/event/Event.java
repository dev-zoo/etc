package com.event;

import com.joo.RunnerFDS;
import java.util.Random;

public class Event {

    /**
     * 테스트 환경을 위해
     * Random 하게 1500만 계정에 대해 100만건의 거래량 발생
     * @param args
     */
    public static void main(String args[]) {

        int accountSize = 15000000; // 1000만사용자 * 평균 1.5계정 = 1500만 계정
        int eventSize = 1000000; // 1일거래량 100만건
        int unitPrint = 10000; // 10000건 단위로 화면에 진행상태 Print

        RunnerFDS runner = new RunnerFDS();

        // Test를 위해 Random으로 1500만 계정에 대해 100만건의 거래량(Event) 발생
        boolean[] accountIsNotFirst = new boolean[accountSize];
        Random r = new Random();
        for (int i = 0; i < eventSize ; i++) {
            if((i % unitPrint) == 0) System.out.println("== Progress : " + i  +" / " + eventSize);
            long currentTS = System.currentTimeMillis();
            int currentAccountIndex = r.nextInt(accountSize);
            String currentAccount = Integer.toString(currentAccountIndex);

            long pm = (r.nextBoolean()) ? -1l : 1l; // 입금:+ 또는 출금:- 발생
            long currentCost = (long) (r.nextDouble() * 1000) * 1000l  * pm;

            runner.initInfo(currentTS, currentAccount, currentCost, !accountIsNotFirst[currentAccountIndex]);
            runner.process();
            accountIsNotFirst[currentAccountIndex] = true;
        }
    }
}
