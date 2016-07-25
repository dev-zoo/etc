package com.joo.data;

/**
 * FDS 와는 별개로 들어오는 거래에 대해 DB에 저장(append) 해주는 Class
 * 테스트를 위한 구현이 아닌, System의 구조적인 측면에서 포함시킴
 */
public class AgentSomeDB {
    /**
     * 거래(event)에 대해 DB에 저장(append)
     * @param account 사용자 계정
     * @param eventTS 거래 발생 시점
     * @param eventCost 거래금
     */
    public void addDB(String account, long eventTS, long eventCost) {
        // Add DB
        /*
        System.out.format("FDS: data: AgentSomeDB: Add DB - Account[%s], EventTS[%d], EventCost[%d]", account, eventTS, eventCost);
        System.out.println();
        */
    }
}
