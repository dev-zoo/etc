package com.joo.models;

public class Models {

    public long[] recordInfoModel;
    public long eventTS;
    public String account;
    public long eventCost;
    public boolean isFirst;


    /**
     * 현재 Fraud Detect 할 특정 Model-Rule의 기록된 정보를 Set
     * @param recordInfoModel
     */
    public void setRecordInfoModel(long[] recordInfoModel) {
        this.recordInfoModel = recordInfoModel;
    }

    /**
     * 현재 거래(Event)의 발생시간을 Set
     * @param eventTS
     */
    public void setEventTS(long eventTS) {
        this.eventTS = eventTS;
    }

    /**
     * 현재 거래(Event)의 계정을 Set
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 현재 거래(Event)의 거래금액을 Set
     * @param eventCost
     */
    public void setEventCost(long eventCost) {
        this.eventCost = eventCost;
    }

    /**
     * 현재 거래(Event)가 최초 계정개설 거래인지 아닌지를 Set
     * @param isFirst
     */
    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }
}
