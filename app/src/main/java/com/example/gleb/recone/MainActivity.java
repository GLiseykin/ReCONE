package com.example.gleb.recone;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.content.pm.ActivityInfo;

import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraAccessException;
import android.content.Context;
import org.opencv.android.JavaCameraView;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.MatOfPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import org.opencv.imgproc.Moments;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static String TAG = "MainActivity";
    JavaCameraView javaCameraView;
    Mat mRgba;

    Mat img_hsv;
    Mat img_bin;

  //  ArrayList<MatOfPoint> contours;
    Mat hierarchy;
    BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status)
            {
                case BaseLoaderCallback.SUCCESS:{
                    javaCameraView.enableView();
                    break;
                }
                default:{
                    super.onManagerConnected(status);
                    break;
                }
            }

        }
    };

    static{
        if (OpenCVLoader.initDebug())
        {
            Log.i(TAG, "OpenCV loaded!");
        }
        else
        {
            Log.i(TAG, "OpenCV not loaded!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String[] cameraIdList=new String[10];
        try
        {
            cameraIdList = cameraManager.getCameraIdList();
        } catch (CameraAccessException e)
        {
            e.printStackTrace();
        }

        String cam_id = cameraIdList[0];


        javaCameraView = (JavaCameraView)findViewById(R.id.java_camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(javaCameraView!=null)
            javaCameraView.disableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (javaCameraView != null)
            javaCameraView.disableView();
    }

    @Override
    protected void onResume() {
        super.onResume();
            if (OpenCVLoader.initDebug())
            {
                Log.i(TAG, "OpenCV loaded!");
                mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            }
            else
            {
                Log.i(TAG, "OpenCV not loaded!");
                OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
            }
        }
    @Override
    public void onCameraViewStarted(int width, int height)
    {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        img_hsv = new Mat();
        img_bin = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
        img_hsv.release();
        img_bin.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        mRgba = inputFrame.rgba();

        Imgproc.cvtColor(mRgba,img_hsv,Imgproc.COLOR_BGR2HSV_FULL);
        Core.inRange(img_hsv, new Scalar(137,60,203), new Scalar(180,255,250),img_bin);


     //   Imgproc.dilate(img_bin, img_bin, new Mat());
      //  List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
      //  List<MatOfPoint> mContours = new ArrayList<MatOfPoint>();

     //   Imgproc.blur(img_bin, img_bin, new Size(5,5));

        //contours = new ArrayList<MatOfPoint>();
    //    hierarchy = new Mat();
     //   Imgproc.findContours(img_bin, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
     //   Iterator<MatOfPoint> each = contours.iterator();

    //    while (each.hasNext()) {
     //       MatOfPoint contour = each.next();
     //       if (Imgproc.contourArea(contour) > 100) {
    //            mContours.add(contour);
     //       }
    //    }
          //      Imgproc.drawContours(mRgba, mContours, -1, new Scalar(255,0,0));
   //     Imgproc.findContours(img_bin, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));



     //   Imgproc.drawContours(mRgba, contours, -1, new Scalar(255,0,0));
    //    Vector<Moments> mu = new Vector<Moments>(contours.size());
      //  for (int i = 0; i < contours.size(); i++)
      //  {
      //      mu.add(i,Imgproc.moments(contours.get(i), false));
     //  }

        ///  Get the mass centers:
//        Vector<Point> mc = new Vector<Point>(contours.size());
//        for (int i = 0; i < contours.size(); i++)
//        {
//            mc.set(i, new Point(mu.get(i).get_m10() / mu.get(i).get_m00(), mu.get(i).get_m01() / mu.get(i).get_m00()));
//        }

     //   for (int i = 0; i < contours.size(); i++)
     //   {
      //      if (mu.get(i).get_m00() > 500)//100 для 640х480
      //      {
       //         Scalar color =  new Scalar(255, 0, 0);
       //         Imgproc.drawContours(mRgba, contours, i, color);
            //    circle(img_src_undist, mc[i], 4, color, -1, 8, 0);
         //   }
    //    }

       // Imgproc.circle(mRgba, new Point(300,400),50,new Scalar(144,125,200),3);
       // Mat mRgbaT = new Mat(mRgba.height(), mRgba.width(), mRgba.type());
       // Mat mRgbaF = new Mat(mRgba.height(), mRgba.width(), mRgba.type());
       // Core.transpose(mRgba, mRgbaT);
       // Imgproc.resize(mRgbaT, mRgbaF, mRgbaF.size(),0,0,0);
       // Core.flip(mRgbaF, mRgba, 1);
       // Log.i(TAG, String.valueOf(contours.size()));
     //   Imgproc.putText(mRgba, "Contours"+contours.size(), new Point(300,400), 3, 1, new Scalar(255, 0, 0, 255), 1);
    //    return mRgba;
        return img_bin;
    }
}

