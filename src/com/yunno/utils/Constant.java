package com.yunno.utils;

import android.os.Environment;

public class Constant {
	public static String ACTIVE_UID;
	public static final String APPKEY = "appkey";
	public static final String APPKEYVALUE = "2011060847";
	public static final String APPSECRET = "appSecret";
	public static final String APPSECRETVALUE = "2d0debf0d3828a08751f2de0a7f82f21";
	public static final int HOME = 1;
	public static final int CATEGORY = 2;
	public static final int ORDER = 3;
	public static final int PERSONAL = 4;
	public static final String CLIENT_VERSION = "clientv";
	public static final String LANGUAGE = "language";
	public static final String PAGESIZE = "10";
	public static final String SDPATH;
	public static int SHOPCAR_NUM = 0;
	public static final String SIGN = "sign";
	public static final String SINGMETHOD = "sign_method";
	public static final String SINGMETHODVALUE = "md5";
	public static final int TIMEOUT_TIME = 30000;
	public static final String UID = "uid";
	public static final String USER = "user";
	public static final String USERTOKEN = "usertoken";
	public static final String USER_ID = "userId";
	public static final String VER = "ver";
	public static final String VERVALUE = "1.0";
	public static final String WELCOME_IMG_NAME = "welcomeImgName";
	public final static int FAILED = 1;
	public final static int SUCCESS = 1;
	public final static int NET_FAILED = 2;
	public final static int TIME_OUT = 3;
	public static int defaultIndex;
	public static boolean exit = true;
	public static String FLAG;

	static {
		int i = 0;
		defaultIndex = 1;
		SDPATH = Environment.getExternalStorageDirectory().getPath();
		ACTIVE_UID = "uid";
		SHOPCAR_NUM = i;
	}
}