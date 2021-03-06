package com.example.mp2021_2_9;

import java.util.HashMap;
import java.util.Stack;

// 모든 프래그먼트와 액티비티 정보들 저장
// 현재 화면에 나와있는 액티비티 혹은 프래그먼트 정보 저장
// 이전 액티비티 혹은 프래그먼트 정보 저장
// 로딩페이지를 화면에 보낼지 말지 결정하는 boolean 값 설정
public class app_info {
    private static String nowPage;
    private static HashMap<String, Integer> pageMap = new HashMap<String, Integer>();
    private static HashMap<Integer, String> keyMap = new HashMap<Integer, String>();
    private static String prevPage;
    private static boolean loading = false;

    public static String getNowPage() {
        return nowPage;
    }

    public static void setNowPage(String nowPage) {
        app_info.nowPage = nowPage;
    }

    public static void setPageMap(){
        pageMap.put("부스메인페이지", R.layout.promotion_main_screen);
        pageMap.put("굿즈메인페이지", R.layout.shopping_main_screen);
        pageMap.put("개인페이지", R.layout.activity_userpage);
        pageMap.put("개인정보수정페이지", R.layout.activity_seeprofile);
        pageMap.put("상품등록페이지", R.layout.activity_additem);
        pageMap.put("부스등록페이지", R.layout.activity_addpromotion);
        pageMap.put("굿즈세부페이지", R.layout.activity_goodsdetailed);
        pageMap.put("부스세부페이지", R.layout.activity_promtdetailed);
        pageMap.put("등록상품관리페이지", R.layout.activity_managegoods);
   }

   public static void setKeyMap(){
        keyMap.put(R.layout.promotion_main_screen, "대동대동");
        keyMap.put(R.layout.shopping_main_screen, "대동대동");
        keyMap.put(R.layout.activity_goodsdetailed, "대동대동");
        keyMap.put(R.layout.activity_promtdetailed, "대동대동");
        keyMap.put(R.layout.activity_addpromotion, "부스 홍보글 등록");
        keyMap.put(R.layout.activity_additem, "상품 등록");
        keyMap.put(R.layout.activity_managegoods, "등록 상품 관리");
        keyMap.put(R.layout.activity_userpage, "대동대동");
        keyMap.put(R.layout.activity_seeprofile, "개인정보 수정");
   }

   public static int getPageMap(String pageName){
        return pageMap.get(pageName);
   }

   public static String getKeyMap(int la){
        return keyMap.get(la);
   }

    public static String getPrevPage() {
        return prevPage;
    }

    public static void setPrevPage(String prevPage) {
        app_info.prevPage = prevPage;
    }

    public static boolean isLoading() {
        return loading;
    }

    public static void setLoading(boolean loading) {
        app_info.loading = loading;
    }
}
