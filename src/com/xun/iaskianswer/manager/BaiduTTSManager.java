package com.xun.iaskianswer.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Environment;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.DataInfoUtils;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.baidu.speechsynthesizer.publicutility.SpeechLogger;
import com.xun.iaskianswer.R;
import com.xun.iaskianswer.app.IAskIAnswerApp;

/**
 * @author xunwang
 * 
 *         2015-6-15
 */
public class BaiduTTSManager implements SpeechSynthesizerListener {
	private static BaiduTTSManager instance = null;

	protected static final int UI_LOG_TO_VIEW = 0;
	protected static final int UI_TOAST = 1;
	private SpeechSynthesizer speechSynthesizer;

	private Context context;
	private Activity activity;

	/** 指定license路径，需要保证该路径的可读写权限 */
	private static final String LICENCE_FILE_NAME = Environment.getExternalStorageDirectory() + "/tts/baidu_tts_licence.dat";

	private BaiduTTSManager(Activity activity) {
		this.context = IAskIAnswerApp.AppContext;
		this.activity = activity;
		init();
	}

	public static BaiduTTSManager getInstance(Activity activity) {
		if (instance == null) {
			syncInit(activity);
		}
		return instance;
	}

	private static synchronized void syncInit(Activity activity) {
		if (instance == null) {
			instance = new BaiduTTSManager(activity);
		}
	}

	public static synchronized void destoryInstance() {
		if (instance != null) {
			instance = null;
		}
	}

	private void init() {
		// 部分版本不需要BDSpeechDecoder_V1
		try {
			System.loadLibrary("BDSpeechDecoder_V1");
		} catch (UnsatisfiedLinkError e) {
			SpeechLogger.logD("load BDSpeechDecoder_V1 failed, ignore");
		}
		System.loadLibrary("bd_etts");
		System.loadLibrary("bds");

		if (!new File(LICENCE_FILE_NAME).getParentFile().exists()) {
			new File(LICENCE_FILE_NAME).getParentFile().mkdirs();
		}
		// 复制license到指定路径
		InputStream licenseInputStream = context.getResources().openRawResource(R.raw.trial_license_20150530);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(LICENCE_FILE_NAME);
			byte[] buffer = new byte[1024];
			int size = 0;
			while ((size = licenseInputStream.read(buffer, 0, 1024)) >= 0) {
				SpeechLogger.logD("size written: " + size);
				fos.write(buffer, 0, size);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				licenseInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		speechSynthesizer = SpeechSynthesizer.newInstance(SpeechSynthesizer.SYNTHESIZER_AUTO, context, "holder", this);
		// 请替换为开放平台上申请的apikey和secretkey
		speechSynthesizer.setApiKey("0tjQObGjpw3qLLewul7OiFLG", "2uNZ9FzVuGqrK5HfmlQRQS3lXRczC5gG");
		// 设置授权文件路径
		speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, LICENCE_FILE_NAME);
		// TTS所需的资源文件，可以放在任意可读目录，可以任意改名
		String ttsTextModelFilePath = context.getApplicationInfo().dataDir + "/lib/libbd_etts_text.dat.so";
		String ttsSpeechModelFilePath = context.getApplicationInfo().dataDir + "/lib/libbd_etts_speech_female.dat.so";
		speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, ttsTextModelFilePath);
		speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, ttsSpeechModelFilePath);
		DataInfoUtils.verifyDataFile(ttsTextModelFilePath);
		DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_DATE);
		DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_SPEAKER);
		DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_GENDER);
		DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_CATEGORY);
		DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_LANGUAGE);
		speechSynthesizer.initEngine();
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}
	
	public void startTTS(String ttString){
		 int ret = speechSynthesizer.speak(ttString);
         if (ret != 0) {
        	 
         }
	}

	@Override
	public void onBufferProgressChanged(SpeechSynthesizer arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancel(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(SpeechSynthesizer arg0, SpeechError arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNewDataArrive(SpeechSynthesizer arg0, byte[] arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSpeechFinish(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSpeechPause(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSpeechProgressChanged(SpeechSynthesizer arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSpeechResume(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSpeechStart(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartWorking(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSynthesizeFinish(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub

	}
}
