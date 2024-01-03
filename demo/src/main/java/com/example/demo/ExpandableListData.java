package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abc on 22-Mar-18.
 */

public class ExpandableListData {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> item1 = new ArrayList<String>();
        item1.add("增加資料");//修改子項目的內容

        List<String> item2 = new ArrayList<String>();
        item2.add("刪除資料");

        List<String> item3 = new ArrayList<String>();
        item3.add("搜索");

        List<String> item4 = new ArrayList<String>();
        item4.add("飲食檢查");

        List<String> item5 = new ArrayList<String>();
        item5.add("數據");

        expandableListDetail.put("增加資料", item1);//父項目的內容設定
        expandableListDetail.put("刪除資料", item2);
        expandableListDetail.put("搜索", item3);
        expandableListDetail.put("飲食檢查", item4);
        expandableListDetail.put("數據", item5);

        return expandableListDetail;
    }
}
