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
        item1.add("如何增加資料？");//修改子項目的內容
        item1.add("1.選取你要新增飲食資料的日期 \n\n2.空白處輸入您要新增的食物內容\n\n3.資料確定後按下確認");

        List<String> item2 = new ArrayList<String>();
        item2.add("如何刪除資料？");
        item2.add("1.選取飲食資料的日期 \n\n2.按下搜索，當日飲食資料會列出在下方\n\n3.對要刪掉的資料點一下\n\n4.確認是否刪除");

        List<String> item3 = new ArrayList<String>();
        item3.add("如何搜索？");
        item3.add("1.選取飲食資料的日期 \n\n2.按下搜索，當日飲食資料會被輸入到GPT\n\n3.下方會顯示您吃了哪些食物，並攝取了哪部分的飲養");


        List<String> item4 = new ArrayList<String>();
        item4.add("何謂飲食檢查?");
        item4.add("1.勾選您缺乏的飲食營養 \n\n2.按下搜索，資料會被傳到GPT\n\n3.下方會顯示飲食營養可從那些部分攝取");


        expandableListDetail.put("增加資料", item1);//父項目的內容設定
        expandableListDetail.put("刪除資料", item2);
        expandableListDetail.put("搜索", item3);
        expandableListDetail.put("飲食檢查", item4);

        return expandableListDetail;
    }
}
