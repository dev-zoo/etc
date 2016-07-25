package com.joo.models;


/**
 * Model B.. C.. 등등 으로 확장할 수 있는 구조를 제시하는 Class
 */
public class ModelB extends Models implements Model {

    private boolean isDetectedFraud = false;

    public ModelB(long[] recordInfoModelB, long eventTS, String account, long eventCost, boolean isFirst) {
        setRecordInfoModel(recordInfoModelB);
        setEventTS(eventTS);
        setAccount(account);
        setEventCost(eventCost);
        setIsFirst(isFirst);
    }

    public void checkFDS() {
        /*
            Implement Rule-B Model
            ...
        */
    }

    public boolean getIsDetectedFraud() {
        return isDetectedFraud;
    }

    public long[] getUpdateRecordInfoModel() {
        return null;
    }
}
