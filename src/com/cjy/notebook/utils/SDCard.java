package com.cjy.notebook.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.cjy.notebook.global.CJYApplication;
import com.cjy.notebook.utils.StorageUtils.StorageInfo;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

public class SDCard {

	private static final String TAG = "SDCard";  
	      /**  
	       * 是否存在SDCard  
	       */ 
	      public static boolean isExistSDCard(){  
	    	  if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
	    		  return true;
	    	  }else{
	    		  return false;
	    	  }

	      } 
	 	 /**
	 	  * 获取可用最适合SD卡可用剩余空间大小
	 	 * @return
	 	 */
	 	public static long getAvailableExternalStorageSize(Context mContext) {
	 		 String state = Environment.getExternalStorageState();
	 		 long AvailableExternalStorageSize = 0;
	 		 if(Environment.MEDIA_MOUNTED.equals(state)) {
//	 			 File sdcardDir = Environment.getExternalStorageDirectory();
	 			 StatFs sf = new StatFs(CJYApplication.getHelperUtis().getFitSdPath(mContext));
	 			 long blockSize = sf.getBlockSize();
	 			 long availCount = sf.getAvailableBlocks();
	 			 return availCount*blockSize/1024/1024;
	 		 }else{
	 			 return AvailableExternalStorageSize;
	 		 }
	     }
	 	
	      public static long getSDFreeSize(String path)
	      {
	          StatFs sf = new StatFs(path);
	          long blockSize = sf.getBlockSize();
	          long freeBlocks = sf.getAvailableBlocks();
	          return (freeBlocks * blockSize) / 1024 / 1024; 
	      }

	      public static long getSDAllSize(String path)
	      {
	          StatFs sf = new StatFs(path);
	          long blockSize = sf.getBlockSize();
	          long allBlocks = sf.getBlockCount();
	          return (allBlocks * blockSize) / 1024 / 1024; 
	      }
	      
	      @SuppressWarnings("unchecked")
		public static void initFitSdcard(Context context) {
//				1.获取所有外部存储器的存储空间，注意不是可用的存储空间
	    	  List<StorageInfo> list = new ArrayList<StorageInfo>();
				for (StorageUtils.StorageInfo stinfo : StorageUtils.getStorageList()) {
					stinfo.size = getSDAllSize(stinfo.path);
					 LogOut.logI(TAG, "path:"+stinfo.path + 
							 "  size:"+stinfo.size+"M"+
							 "  internal:"+stinfo.internal+
							 "  readonly:"+stinfo.readonly+
							 "  DisplayName:"+stinfo.getDisplayName()
				                                       );
					 list.add(stinfo);
				}
				 Collections.sort(list);  
//			2.	通过比较选取最大的外部存储空间的那个SDcard
					for (StorageUtils.StorageInfo stinfo :list) {
						stinfo.size = getSDAllSize(stinfo.path);
//						 LogOut.logI(TAG, "path:"+stinfo.path + 
//								 "  size:"+stinfo.size+"M"+
//								 "  internal:"+stinfo.internal+
//								 "  readonly:"+stinfo.readonly+
//								 "  DisplayName:"+stinfo.getDisplayName()
//					                                       );
					}
					if (list.size() >= 1) {
						 CJYApplication.getHelperUtis().saveFitSdPath(context, list.get(list.size()-1).path);
					}			
				 LogOut.logI(TAG, "fit_path:"+CJYApplication.getHelperUtis().getFitSdPath(context)+"\ndefault_sdcard:"+
						 Environment.getExternalStorageDirectory().getPath());
//				3.存储这个最大外部存储空间的路劲，以后就以这个sdcard为最适合的sdcard使用
		}

}
