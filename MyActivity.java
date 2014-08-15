package com.eka.Flash;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MyActivity extends Activity
 {

    private static Camera camera;
    private static Parameters parameters;
    private ImageButton OnOff;
    private Boolean onoff=false;
    private boolean hasFlash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        yatayekran();
        setContentView(R.layout.main);
/*
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
// device doesn't support flash
// Show alert message and close the application
            AlertDialog alert = new AlertDialog.Builder(MyActivity.this)
                    .create();
            alert.setTitle(getString(R.string.hata));
            alert.setMessage(getString(R.string.sorry));
            alert.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
// closing the application
                    finish();
                }
            });
            alert.show();
            return;
        }
        */

   // Ekran döndüğünde ampul yanıyorsa, başlangıç ayarı olan ekranda sönük ampul olmiyacak.





        OnOff =(ImageButton) this.findViewById(R.id.btnimage_on_off);
        OnOff.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(view.getId()){
                    case R.id.btnimage_on_off:
                        if( onoff == false ) {
                            setFlashOn();
                        }else {
                            setFlashOff();
                        }
                        break;
                }
            }
        });
    }

     private void yatayekran() {
         if (onoff==true&& OnOff.equals(getResources().getDrawable(R.drawable.ampul_on)))
         {
             OnOff.setImageResource(R.drawable.ampul_on);
         }else {
             OnOff.setImageResource(R.drawable.ampul_off);
         }

     }

     private void setFlashOn(){

        if (camera == null)
            camera = Camera.open();
        parameters = camera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);

//        OnOff.setDrawingCacheBackgroundColor(R.color.siyah);
        findViewById(R.id.zemin).setBackgroundResource(R.drawable.beyaz);
        OnOff.setImageResource(R.drawable.ampul_on);
        onoff=true;
    }

    private void setFlashOff(){

        parameters = camera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();

//        OnOff.setDrawingCacheBackgroundColor(R.color.siyah);
        findViewById(R.id.zemin).setBackgroundResource(R.drawable.siyah);
        OnOff.setImageResource(R.drawable.ampul_off);
        onoff=false;
    }





     @Override
     protected void onPause() {
         super.onPause();

//        Release Camera

         if (camera != null) {
             camera.release();
             camera = null;
         }

     }

     @Override
     protected void onResume() {
         super.onResume();

         //  Check if light was on when App was paused. If so, turn back on.

         if(parameters != null && parameters.getFlashMode()==Parameters.FLASH_MODE_TORCH){
             if (camera == null)
                 camera = Camera.open();
             camera.setParameters(parameters);

         }

     }

}

