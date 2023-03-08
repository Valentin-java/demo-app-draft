package com.example.demo.draft.test;

import org.apache.logging.log4j.util.Strings;

import java.util.List;

public class GetSqlString {

    public static void main(String[] args) {
        var statusIdList = List.of(396L, 402L);

        System.out.println(getSql(statusIdList, null, null));
    }

    private static String getSql(Iterable<Long> statusIdList, String excludeRegistryWithWord, Long searchDepth) {
        StringBuilder result = new StringBuilder().append(
                        "SELECT " +
                                "r.reestr_status_id AS status, " +
                                "round(avg((sysdate - rr.update_date)* 24 * 3600)) AS durationAvg," +
                                "count(*) AS count " +
                                "FROM " +
                                "t7_rb.PA_REESTR r " +
                                "JOIN " +
                                "t7_rb.pa_reestr_response rr ON " +
                                "r.last_response_id = rr.reestr_response_id " +
                                "JOIN t7_rb.pa_reestr_response rr2 ON " +
                                "r.FIRST_RESPONSE_ID = rr2.reestr_response_id " +
                                "WHERE " +
                                "r.reestr_status_id IN (")
                .append(Strings.join(statusIdList, ','))
                .append(") ")
                .append("AND (r.deleted IS NULL OR r.deleted <> 1) ")
                .append("AND (r.REESTR_TYPE='M' OR r.REESTR_TYPE IS NULL) ");
                result.append("GROUP BY r.reestr_status_id");
        return result.toString();
    }
}
