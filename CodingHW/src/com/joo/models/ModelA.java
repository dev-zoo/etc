package com.joo.models;

public class ModelA extends Models implements Model {

    private boolean isDetectedFraud = false;

    private final long MIN_COST_RANGE_MODEL_A = 900000; // 90만원
    private final long MAX_COST_RANGE_MODEL_A = 1000000; // 100만원

    private final long MIN_COST_WARNING_FRAUD_MODEL_A = 10000; // 1만원

    // private final long DAY7_MILLISECONDS = 1000 * 60 * 60 * 24 * 7; // 7일
    // private final long HOUR2_MILLISECONDS = 1000 * 60 * 60 * 2; // 2일
    private final long DAY7_MILLISECONDS = 2000; // Test를 위해 2000ms 로 설정
    private final long HOUR2_MILLISECONDS = 100; // Test를 위해 100ms 로 설정


    public ModelA(long[] recordInfoModelA, long eventTS, String account, long eventCost, boolean isFirst) {
        setRecordInfoModel(recordInfoModelA);
        setEventTS(eventTS);
        setAccount(account);
        setEventCost(eventCost);
        setIsFirst(isFirst);
    }


    /**
     * Model-Rule-A의 Core부분.
     */
    public void checkFDS() {

        /*
        recordInfoModel
        [0] : 최초 계정개설 발생 TS
        [1] : 최신 90만~100만사이 입금 거래 발생 TS .
              만약 -1 값이면 90만~100만 입금 발생이 안되었거나,
              90만~100만 입금 발생 후 2일이 지나도 Fraud 감지 안될시 -1값으로 초기화
        [2] : 거래 발생 마다 최신화 된 계죄내 총금액

        만약 recordInfoModel == null 이면,
        최초 계정개설 발생일 보다 7일이 지나 Fraud 감지 후보군에서 제외됨을 의미
         */

        // isFirst: 계정개설 거래 여부 확인
        if (isFirst) {
            eventCost = Math.max(eventCost, 0l); // 최초 거래시 출금이 되면 0원 입금한 것으로 처리. 이유는 이 역할은 FDS가 아니라 판단.
            if ((eventCost >= MIN_COST_RANGE_MODEL_A) && (eventCost <= MAX_COST_RANGE_MODEL_A)) {
                recordInfoModel = new long[]{eventTS, eventTS, eventCost};
            } else {
                recordInfoModel = new long[]{eventTS, -1l, eventCost};
            }
        } else {
            if (recordInfoModel != null) {

                // 7일 이내 계좌 개설 여부
                if ((eventTS - recordInfoModel[0]) <= DAY7_MILLISECONDS) {

                    long sumCost = recordInfoModel[2] + eventCost;
                    recordInfoModel[2] = Math.max(sumCost, 0l); // 총액이 "-"금액이라면 0원으로 처리

                    if (recordInfoModel[1] != -1l) {
                        if (((eventTS - recordInfoModel[1]) <= HOUR2_MILLISECONDS)) {
                            if ((recordInfoModel[2] <= MIN_COST_WARNING_FRAUD_MODEL_A)) {
                                isDetectedFraud = true;
                            } else {
                                if ((eventCost >= MIN_COST_RANGE_MODEL_A) && (eventCost <= MAX_COST_RANGE_MODEL_A)) {
                                    recordInfoModel[1] = eventTS;
                                }
                            }
                        } else {
                            if ((eventCost >= MIN_COST_RANGE_MODEL_A) && (eventCost <= MAX_COST_RANGE_MODEL_A)) {
                                recordInfoModel[1] = eventTS;
                            } else {
                                recordInfoModel[1] = -1l;
                            }
                        }
                    } else {
                        if ((eventCost >= MIN_COST_RANGE_MODEL_A) && (eventCost <= MAX_COST_RANGE_MODEL_A)) {
                            recordInfoModel[1] = eventTS;
                        }
                    }
                }

                // 계좌 개설 7일 지났으면 정보 Null 로 초기화.
                else {
                    recordInfoModel = null;
                }
            }
        }
    }

    public boolean getIsDetectedFraud() {
        return isDetectedFraud;
    }

    public long[] getUpdateRecordInfoModel() {
        return recordInfoModel;
    }

}
