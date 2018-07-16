package com.lucky.sweet.model.qrcodescan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.lucky.sweet.R;
import com.lucky.sweet.model.qrcodescan.camera.CameraManager;
import com.lucky.sweet.model.qrcodescan.decoding.CaptureActivityHandler;
import com.lucky.sweet.model.qrcodescan.decoding.RGBLuminanceSource;
import com.lucky.sweet.model.qrcodescan.utils.InactivityTimer;
import com.lucky.sweet.model.qrcodescan.utils.Utils;
import com.lucky.sweet.model.qrcodescan.view.ViewfinderView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.Vector;

public class ScanningQR extends Fragment {
    private static final int REQUEST_CODE = 234;
    private ViewfinderView viewfinderView;
    private SurfaceView surfaceView;
    private boolean lightUp = true;
    private boolean hasSurface = false;
    private boolean playBeep;
    private boolean vibrate;
    private String photo_path;
    private String characterSet;
    private Vector<BarcodeFormat> decodeFormats;
    private CaptureActivityHandler handler;
    private MediaPlayer mediaPlayer;
    private InactivityTimer inactivityTimer;
    private Bitmap scanBitmap;
    private Context context;
    private ImageView mo_scanner_back;
    private static final float BEEP_VOLUME = 0.10f;
    private BackOnClickListener backOnClickListener;
    private GetDecode getDecode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.framelayout_qrcodesacn, container, false);

        initialize();

        initView(inflate);

        initEvent(inflate);

        return inflate;

    }

    @Override
    public void onResume() {
        SurfaceHolder holder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(holder);
        } else {
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    if (!hasSurface) {
                        hasSurface = true;
                        initCamera(surfaceHolder);
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    hasSurface = false;
                }
            });
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
        playBeep = true;
        AudioManager audioService = (AudioManager) context.getSystemService
                (Context.AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
        super.onResume();
    }

    @Override
    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initialize() {
        context = getActivity();
        CameraManager.init(context);
        inactivityTimer = new InactivityTimer((Activity) context);
    }

    private void initEvent(View view) {
        view.findViewById(R.id.mo_scanner_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbum();
            }
        });
        view.findViewById(R.id.mo_scanner_light).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFlash();
            }
        });
        mo_scanner_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != backOnClickListener)
                    backOnClickListener.setonBackClickListener(view);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });
    }

    private void initView(View view) {
        viewfinderView = (ViewfinderView) view.findViewById(R.id
                .mo_scanner_viewfinder_view);
        surfaceView = (SurfaceView) view.findViewById(R.id
                .mo_scanner_preview_view);
        mo_scanner_back = (ImageView) view.findViewById(R.id
                .mo_scanner_back);

    }

    protected void openFlash() {
        if (lightUp == true) {
            lightUp = false;
            // 开闪光灯
            CameraManager.get().openLight();

        } else {
            lightUp = true;
            // 关闪光灯
            CameraManager.get().offLight();

        }

    }

    private void openAlbum() {

        Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
        if (Build.VERSION.SDK_INT < 19) {
            innerIntent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        innerIntent.setAction(Intent.ACTION_GET_CONTENT);

        innerIntent.setType("image/*");

        Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");

        startActivityForResult(wrapperIntent, REQUEST_CODE);
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            ((Activity) context).setVolumeControlStream(AudioManager
                    .STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.mo_scanner_beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) ((Activity) context).getSystemService
                    (Activity.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    public CaptureActivityHandler getHandler() {
        return handler;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    public void handleDecode(final Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String recode = recode(result.toString());
        // 数据返回
        if (null!=getDecode) getDecode.getDecodeString(recode);
    }

    /**
     * 中文乱码
     * <p>
     * 暂时解决大部分的中文乱码 但是还有部分的乱码无法解决 .
     */
    private String recode(String str) {
        String formart = "";

        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
                    .canEncode(str);
            if (ISO) {
                formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
                Log.i("1234      ISO8859-1", formart);
            } else {
                formart = str;
                Log.i("1234      stringExtra", str);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return formart;

    }

    public void decodeLocalPhoto(Intent data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        // 获取选中图片的路径
        Cursor cursor = context.getContentResolver().query(data.getData(),
                proj, null, null, null);

        if (cursor.moveToFirst()) {

            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            photo_path = cursor.getString(column_index);

            if (photo_path == null) {
                photo_path = Utils.getPath(context,
                        data.getData());
                Log.i("123path  Utils", photo_path);
            }
            Log.i("123path", photo_path);

        }

        cursor.close();

        new Thread(new Runnable() {

            @Override
            public void run() {
                Result result = scanningImage(photo_path);
                /*从图片变成字符串
			加密用之前要生成cdKey
			String cdKey = AesUtils.generateKey();
			加密用，把字符串改成加密版
			String s = AesUtils.encrypt(cdKey, "生成二维码所用得字符串");
			解码用，cdkey从生成的地方调取
			String result = AesDecode.decrypt(cdKey, s);*/
                // String result = decode(photo_path);
                if (result == null) {
                    Log.i("123", "   -----------");
                    Looper.prepare();
                    Toast.makeText(context, "未找到图中二维码", Toast.LENGTH_SHORT)
                            .show();
                    Looper.loop();
                } else {
                    Log.i("123result", result.toString());
                    // Log.i("123result", result.getText());
                    // 数据返回
                    String recode = recode(result.toString());
                    if (null!=getDecode) getDecode.getDecodeString(recode);

                }
            }
        }).start();
    }

    // TODO: 解析部分图片
    protected Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;

        }
        // DecodeHintType 和EncodeHintType
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小

        int sampleSize = (int) (options.outHeight / (float) 200);

        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);

        // --------------测试的解析方法---PlanarYUVLuminanceSource-这几行代码对project没作功----------

//		LuminanceSource source1 = new PlanarYUVLuminanceSource(
//				rgb2YUV(scanBitmap), scanBitmap.getWidth(),
//				scanBitmap.getHeight(), 0, 0, scanBitmap.getWidth(),
//				scanBitmap.getHeight(), false);
//		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
//				source1));
//		MultiFormatReader reader1 = new MultiFormatReader();
//		Result result1;
//		try {
//			result1 = reader1.decode(binaryBitmap);
//			String content = result1.getText();
//			Log.i("123content", content);
//		} catch (NotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

        // ----------------------------

        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {

            return reader.decode(bitmap1, hints);

        } catch (NotFoundException e) {

            e.printStackTrace();

        } catch (ChecksumException e) {

            e.printStackTrace();

        } catch (FormatException e) {

            e.printStackTrace();

        }

        return null;

    }

    public interface BackOnClickListener {
        void setonBackClickListener(View view);
    }

    public interface GetDecode {
        void getDecodeString(String decode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {

                case REQUEST_CODE:
                    decodeLocalPhoto(data);
                    break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setBackOnClickListener(BackOnClickListener backOnClickListener) {
        this.backOnClickListener = backOnClickListener;
    }
    public void setObtainResultCodeListener(GetDecode getDecode) {
        this.getDecode=getDecode;
    }

}
