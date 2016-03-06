package com.youno.test;

import java.util.ArrayList;

import com.youno.R;
import com.youno.model.OrderItemInfo;
import com.youno.model.shopItemInfo;

public class TestData {
	private static ArrayList<shopItemInfo> mShopItemInfoList;
	
	private static ArrayList<OrderItemInfo> mOrderItemListInfo;

	public static ArrayList<shopItemInfo> getShopList() {
		mShopItemInfoList = new ArrayList<shopItemInfo>();
		shopItemInfo sii = new shopItemInfo("代官山（五角场店）", 4.5, "[五角场街道] 2km", "http://p8.qhimg.com/dr/_390_/t01fd7c4a737968d621.jpg", "2店通用，不限使用张数", 69.9, 80.9, 50);
		mShopItemInfoList.add(sii);
		shopItemInfo sii1 = new shopItemInfo("小肥羊（徐家汇店）", 4.5, "[徐家汇] 1.5km", "http://pic7.nipic.com/20100602/2786179_203600069016_2.jpg", "本店通用，每单可使用五张", 40.9, 50.9, 39);
		mShopItemInfoList.add(sii1);
		shopItemInfo sii2 = new shopItemInfo("傣妹（南京东路店）", 4.5, "[南京东路街道] 1km", "http://g.hiphotos.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=9299a81797dda144ce0464e0d3debbc7/0ff41bd5ad6eddc4cf94b8ff39dbb6fd52663346.jpg", "3店通用，不限使用张数", 89.9, 100.9, 20);
		mShopItemInfoList.add(sii2);
		shopItemInfo sii3 = new shopItemInfo("甜舌诱惑特色餐厅（五角场店）", 4.5, "[人民广场] 2。5km", "http://img3.imgtn.bdimg.com/it/u=37682100,2867050575&fm=21&gp=0.jpg", "本店通用，不限使用张数", 32.9, 60.9, 100);
		mShopItemInfoList.add(sii3);
		shopItemInfo sii4 = new shopItemInfo("晶丽酒店（黄埔点）", 4.5, "[黄浦区] 0.5km", "http://b.hiphotos.baidu.com/lbc/w%3D448%3Bq%3D90/sign=eaa690888501a18bf0eb134ba6147635/b8389b504fc2d562e447c649e51190ef77c66cc2.jpg", "2店通用，不限使用张数", 59.9, 70.9, 80);
		mShopItemInfoList.add(sii4);
		return mShopItemInfoList;
	}
	
	public static ArrayList<OrderItemInfo> getOrderList(){
		mOrderItemListInfo = new ArrayList<OrderItemInfo>();
		OrderItemInfo oii = new OrderItemInfo("代官山", "http://p8.qhimg.com/dr/_390_/t01fd7c4a737968d621.jpg", 1, "待付款", "2015-06-06 12：34", 80);
		mOrderItemListInfo.add(oii);
		OrderItemInfo oii1 = new OrderItemInfo("甜舌诱惑特色餐厅", "http://img3.imgtn.bdimg.com/it/u=37682100,2867050575&fm=21&gp=0.jpg", 2, "超时关闭", "2015-06-06 12：34", 120);
		mOrderItemListInfo.add(oii1);
		return mOrderItemListInfo;
	}
}
