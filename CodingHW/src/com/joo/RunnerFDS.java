package com.joo;

import com.joo.data.AgentSomeDB;
import com.joo.data.MemoryPot;
import com.joo.models.Model;
import com.joo.models.ModelA;
import com.joo.models.ModelB;

import java.util.HashMap;

/**
 * FDS 를 실행하는 Runner Class
 */
public class RunnerFDS {

    private MemoryPot mp;

    private long eventTS;
    private String account;
    private long eventCost;
    private boolean isFirst;

    public RunnerFDS() {
        mp = new MemoryPot();
    }

    public void initInfo(long eventTS, String account, long eventCost, boolean isFirst) {
        // 1. Event 정보들이 들어온다
        this.eventTS = eventTS;
        this.account = account;
        this.eventCost = eventCost;
        this.isFirst = isFirst;
    }

    public void process() {

        // 2. MemoryPot 에서 해당 Data 얻는다 -> recordInfo
        HashMap<String, long[]> recordInfo = mp.getRecordInfo(account);
        if (recordInfo == null) recordInfo = new HashMap<>();

        // 3. Rule 들을 실행한다
        executeModel("A", recordInfo);
        executeModel("B", recordInfo); // Rule 추가의 확장성

        // 7. MemoryPot 에 updatedRecordInfo 을 갱신한다
        mp.updatedRecordInfo(recordInfo, account);

        // 8. DB 에 Event를 Update 한다.
        AgentSomeDB someDB = new AgentSomeDB();
        someDB.addDB(account, eventTS, eventCost);
    }

    /**
     * 해당 Model-Rule을 실행
     * @param modelName
     * @param recordInfo
     */
    private void executeModel(String modelName, HashMap<String, long[]> recordInfo) {

        long[] recordInfoModel = null;
        if (recordInfo.containsKey(modelName)) recordInfoModel = recordInfo.get(modelName);

        switch (modelName) {
            case "A": {
                // 3. recordInfo + Event 를 Model 에 날린다
                // 4. Model 에서 판단
                // 5. Model 에서 이상징후여부 얻고, 판단하여 Warning
                // 6. Model 에서 새로 갱신된 updatedRecordInfo 얻는다
                ModelA modelA = new ModelA(recordInfoModel, eventTS, account, eventCost, isFirst);
                runCheckFDS(modelA); // modelA.checkFDS();
                warningFDS("A", feedDetectedFraud(modelA), account, (System.currentTimeMillis() - eventTS));
                recordInfoModel = feedUpdateRecordInfoModel(modelA);
                recordInfo.put(modelName, recordInfoModel);
                break;
            }
            case "B": {
                // Sample
                ModelB modelB = new ModelB(recordInfoModel, eventTS, account, eventCost, isFirst);
                runCheckFDS(modelB);
                warningFDS("B", feedDetectedFraud(modelB), account, (System.currentTimeMillis() - eventTS));
                recordInfoModel = feedUpdateRecordInfoModel(modelB);
                recordInfo.put(modelName, recordInfoModel);
                break;
            }
        }
    }

    /**
     * Fraud 발생시 화면에 경고.
     * @param modelName
     * @param isFDS
     * @param account
     * @param duration Fraud 검출하는데 걸린 시간
     */
    private void warningFDS(String modelName, boolean isFDS, String account, long duration) {
        if (isFDS) {
            System.out.format("FDS: WARNING: DETECTED FRAUD!!! : MODEL-RULE-%s : Account:%s , DurationOfDetection[%dms]", modelName, account, duration);
            System.out.println();
        }
    }


    private void runCheckFDS(Model ma) {
        ma.checkFDS();
    }

    private boolean feedDetectedFraud(Model ma) {
        return ma.getIsDetectedFraud();
    }

    private long[] feedUpdateRecordInfoModel(Model ma) {
        return ma.getUpdateRecordInfoModel();
    }

}
