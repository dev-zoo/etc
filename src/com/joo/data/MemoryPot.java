package com.joo.data;

import java.util.HashMap;

/**
 * 실제 FDS 에서 Fraud 후보 값들을 메모리에 올려놓는 구조를 가정해서 만든 Class
 */
public class MemoryPot {

    public HashMap<String, HashMap<String, long[]>> candidateFrauds = null;
    public MemoryPot() {
        candidateFrauds = new HashMap<>();
    }

    /**
     * 기록되어진 특정 계정에 해당하는 Fraud 후보값들의 정보 얻기
     * @param account 계정
     * @return 계정에 해당하는 Fraud 후보값들의 정보
     */
    public HashMap<String, long[]> getRecordInfo(String account) {
        return candidateFrauds.get(account);
    }

    /**
     * 갱신된 특정 계정에 해당하는 Fraud 후보값들의 정보를 갱신
     * @param updateRecordInfo 계정에 해당하는 갱신된 Fraud 후보값들의 정보
     * @param account 계정
     */
    public void updatedRecordInfo(HashMap<String, long[]> updateRecordInfo, String account) {
        candidateFrauds.put(account, updateRecordInfo);
    }
}
