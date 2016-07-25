package com.joo.models;


public interface Model {

    /**
     * 약속되어진 Model-Rule을 통해 FDS 감지
     */
    void checkFDS();

    /**
     * checkFDS() 통해 갱신된 장애발생여부 얻는다.
     * @return 장애발생 여부
     */
    boolean getIsDetectedFraud();

    /**
     * checkFDS() 과정 중 특정 Model-Rule 에 의해 Update된 정보 얻는다.
     * @return 특정 Model-Rule 에 의해 Update된 정보
     */
    long[] getUpdateRecordInfoModel(); // checkFDS() 과정 중 Update된 Model정보 얻는다.
}
