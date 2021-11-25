package com.example.mp2021_2_9;

import java.util.HashMap;
import java.util.Stack;

public class app_info {
    private static Stack<String> stack = new Stack<>();
    private static String nowPage;
    private static HashMap<String, Integer> pageMap = new HashMap<String, Integer>();
    private static HashMap<Integer, String> keyMap = new HashMap<Integer, String>();
    private static String prevPage;

    public static String getNowPage() {
        return nowPage;
    }

    public static void setNowPage(String nowPage) {
        app_info.nowPage = nowPage;
    }

    public static void setPageMap(){
        pageMap.put("메인페이지", R.layout.activity_main);
        pageMap.put("부스메인페이지", R.layout.promotion_main_screen);
        pageMap.put("로그인페이지", R.layout.activity_login);
        pageMap.put("회원가입페이지", R.layout.activity_join);
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
        keyMap.put(R.layout.activity_main, "대동대동");
        keyMap.put(R.layout.activity_login, "로그인");
        keyMap.put(R.layout.activity_join, "회원가입");
   }

   public static int getPageMap(String pageName){
        return pageMap.get(pageName);
   }

   public static String getKeyMap(int la){
        return keyMap.get(la);
   }

   public static void putStack(String page){
        stack.push(page);
   }

   public static String getStack(){
        return stack.pop();
   }

   public static boolean isEmptyStack(){
        return stack.isEmpty();
   }

    public static String getPrevPage() {
        return prevPage;
    }

    public static void setPrevPage(String prevPage) {
        app_info.prevPage = prevPage;
    }
}
