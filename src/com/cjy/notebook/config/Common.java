package com.cjy.notebook.config;

/**
 * 常量
 * */
public class Common {
	
	final public static String DEFAUT_CJY_STORAGEPATH = "CJY_default_storage_path";
	final public static boolean LOG_FLAG = false;//输出日志开关

	final public static int POSITIVEBUTTON = 301;//确定按钮
	final public static int NEGATIVEBUTTON = 302;//取消按钮
	final public static int CMB_DELETETABLE = 0;//清空数据库命令
	
	final public static String DATABASE_DELTETABLE_FROM_NOTES = "deleteTableFromNotes";
	final public static String ACTION_SERVICE = "cjy.intent.action.service";
	
	final public static int UPDATE = 2001;
	final public static int DELETE = 2002;
	final public static int DELETE_ON_BACKGROUND = 2003;
	final public static int REFRESH = 2004;
	final public static int LOADER_SUCCESS = 2005;
	
	final public static String loaderfromWeb = "http://";
	final public static String loaderfromSDCard = "file:///";
	final public static String loaderfromProvider = "content://media/external/audio/albumart/13";
	final public static String loaderfromAssets = "assets://image.png";
	final public static String loaderfromDrawables = "drawable://";
	
	final public static String VIDEO_START_RECORDING = "video.start.recording";//开始录制视频
	final public static String VIDEO_STOP_RECORDING = "video.stop.recording";//停止录制视频
	final public static String VIDEO_START_PLAYER = "video.start.player";//开始播放视频
	final public static String VIDEO_STOP_PLAYER = "video.stop.player";//停止视频播放
	
}
