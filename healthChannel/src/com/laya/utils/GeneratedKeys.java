package com.laya.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneratedKeys {
static	List<Integer> list=new ArrayList<Integer>();
	public static void main(String[] args) {
		   // TODO Auto-generated method stub
		   System.out.println(genRandomNum(16));
		    keyId();
		  int[] t=  randomCommon(1,10,3);
		  for (int i : t) {
			System.out.println(i);
		}
		}
		/**
		* 生成随即密码
		* @param pwd_len 生成的密码的总长度
		* @return 密码的字符串
		*/
		public static String genRandomNum(int pwd_len){
		   //35是因为数组是从0开始的，26个字母+10个数字
		   final int maxNum = 36;
		   int i; //生成的随机数
		   int count = 0; //生成的密码的长度
		   char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		     'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		     'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		   StringBuffer pwd = new StringBuffer("");
		   Random r = new Random();
		   while(count < pwd_len){
		    //生成随机数，取绝对值，防止生成负数，
		   
		    i = Math.abs(r.nextInt(maxNum)); //生成的数最大为36-1
		   
		    if (i >= 0 && i < str.length) {
		     pwd.append(str[i]);
		     count ++;
		    }
		   }

		   return pwd.toString();
		}
		
		public static void keyId(){
		
		      int[] intRandom = new int[50];
		        List<Integer> mylist = new ArrayList<Integer>();  //生成数据集，用来保存随即生成数，并用于判断
		        Random rd = new Random();
		        while(mylist.size() <50) {
		        	
		            int num = rd.nextInt(51);
		          
		            if(!mylist.contains(num)) {  
		               mylist.add(num);  //往集合里面添加数据。
		            }
		        }
		      
		       for(int i = 0;i <mylist.size();i++) {
		    	   
		          intRandom[i] = (Integer)(mylist.get(i));
		       }
		    }
		
		
		/** 
		 * 随机指定范围内N个不重复的数 
		 * 最简单最基本的方法 
		 * @param min 指定范围最小值 
		 * @param max 指定范围最大值 
		 * @param n 随机数个数 
		 */  
		public static int[] randomCommon(int min, int max, int n){  
		    if (n > (max - min + 1) || max < min) {  
		           return null;  
		       }  
		    int[] result = new int[n];  
		    int count = 0;  
		    while(count < n) {  
		        int num = (int) (Math.random() * (max - min)) + min;  
		        boolean flag = true;  
		        for (int j = 0; j < n; j++) {  
		            if(num == result[j]){  
		                flag = false;  
		                break;  
		            }  
		        }  
		        if(flag){  
		            result[count] = num;  
		            count++;  
		        }  
		    }  
		    return result;  
		}  
		}
		
		
		
	


