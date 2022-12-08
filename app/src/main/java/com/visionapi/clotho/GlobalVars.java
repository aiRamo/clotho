package com.visionapi.clotho;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class GlobalVars {

    public static boolean isLoggedIn = false;
    public static String userNameTxt_Global;
    public static String emailTxt_Global;
    public static String phoneTxt_Global;
    public static String genderTxt_Global;
    public static String uri;
    public static String itemColor;
    public static String AmazonLink = null;

    //Settings
    public static String gender;
    public static String topSize;
    public static String waistSize;
    public static String shoeSize;

    public static int savedCount = 0;

    public static ArrayList<DataSnapshot> savedData = new ArrayList<>();


}
