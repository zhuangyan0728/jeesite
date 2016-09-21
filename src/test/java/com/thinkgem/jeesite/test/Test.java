package com.thinkgem.jeesite.test;

public class Test {

	public static void main(String[] args) {
		String test = "type=1&param=谷歌";
		String url = "type=1&param=sdfas谷dff";
		//url =url.substring(0,url.indexOf("param")+6)+ java.net.URLEncoder.encode(url.substring(url.indexOf("param")+6));
		String aa = "宿舍";
		
		String a= java.net.URLEncoder.encode("宿舍");
		 System.out.println(a);
		 a= java.net.URLEncoder.encode(a);
		 System.out.println(a);
		 a= java.net.URLDecoder.decode(a);
		 System.out.println(a);
		 a= java.net.URLDecoder.decode(a);
		 System.out.println(a);
		 a= java.net.URLDecoder.decode(a);
		 System.out.println(a);
	}
}
