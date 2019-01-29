//project by nitish s. prajapati

package com.nitish.slateinteractivewhiteboardv3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener{

    MenuItem eraserItem;

    android.support.v7.app.ActionBar bar;
    private Paint glowBrush;
    private Paint eraser;
    int currentBackgroundColor=Color.BLACK;
    boolean glow = false;
    boolean eraserEnabled = false;

    static ExampleDialog exampleDialog;


    private Paint mPaint;
    Slate slate;/*SLATE*/
    static Socket socket = null;/*for client*/
    public static MainActivity context;
    static ObjectOutputStream stream = null;/*for client*/
    //static ObjectInputStream stream_incoming_for_client = null;/*for client*/
    public static boolean isClient = false;
    public static boolean isServer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        slate = new Slate(this);
        slate.setBackgroundColor(Color.parseColor("#000000"));
        setContentView(slate);
        bar = getSupportActionBar();/*action bar/title bar*/
        bar.setElevation(0);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));/*action bar color*/
        bar.setTitle(Html.fromHtml("<font color='#ffffff'>SLATE</font>"));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);/*hide notification bar*/

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);

        glowBrush = new Paint();
        glowBrush.set(mPaint);
        glowBrush.setColor(Color.parseColor("#f91f73"));
        glowBrush.setStrokeWidth(50f);
        glowBrush.setMaskFilter(new BlurMaskFilter(2, BlurMaskFilter.Blur.NORMAL));
        glowBrush.setAntiAlias(true);
        glowBrush.setDither(true);
        glowBrush.setStyle(Paint.Style.STROKE);
        glowBrush.setStrokeJoin(Paint.Join.ROUND);
        glowBrush.setStrokeCap(Paint.Cap.ROUND);

        eraser = new Paint();
        eraser.set(mPaint);
        eraser.setColor(currentBackgroundColor);
        eraser.setStrokeWidth(80f);
        eraser.setMaskFilter(new BlurMaskFilter(2, BlurMaskFilter.Blur.NORMAL));
        eraser.setAntiAlias(true);
        eraser.setDither(true);
        eraser.setStyle(Paint.Style.STROKE);
        eraser.setStrokeJoin(Paint.Join.ROUND);
        eraser.setStrokeCap(Paint.Cap.ROUND);

        {
            openDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder)menu;
            m.setOptionalIconsVisible(true);
            eraserItem = menu.findItem(R.id.erase);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()){
                case R.id.bw:
                    glow = false;
                    slate = new Slate(this);
                    slate.setBackgroundColor(Color.parseColor("#000000"));
                    currentBackgroundColor = Color.BLACK;
                    mPaint.setColor(Color.WHITE);
                    setContentView(slate);
                    eraser.setColor(currentBackgroundColor);

                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#ffffff'>SLATE</font>"));

                    if(stream!=null)
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                stream.writeObject(new Action(-1, -1, Action.THEME_DEEPNIGHT));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case R.id.aqua:
                    glow = false;
                    slate = new Slate(this);
                    slate.setBackgroundColor(Color.parseColor("#76cdba"));
                    currentBackgroundColor = Color.parseColor("#76cdba");
                    mPaint.setColor(Color.parseColor("#ffffff"));
                    eraser.setColor(currentBackgroundColor);

                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#76cdba")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#ffffff'>SLATE</font>"));
                    setContentView(slate);

                    if(stream!=null)
                        new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                stream.writeObject(new Action(-1, -1, Action.THEME_AQUA));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case R.id.solarized:
                    glow = false;
                    slate = new Slate(this);
                    slate.setBackgroundColor(Color.parseColor("#002B37"));
                    currentBackgroundColor = Color.parseColor("#002B37");
                    mPaint.setColor(Color.parseColor("#006064"));
                    setContentView(slate);
                    eraser.setColor(currentBackgroundColor);

                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#002B37")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#006064'>SLATE</font>"));

                    if(stream!=null)
                        new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                stream.writeObject(new Action(-1, -1, Action.THEME_SOLARIZED));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();                    break;
                case R.id.zombie:
                    glow = false;
                    slate = new Slate(this);
                    slate.setBackgroundColor(Color.parseColor("#2F0039"));
                    currentBackgroundColor = Color.parseColor("#2F0039");
                    mPaint.setColor(Color.parseColor("#00CC5D"));
                    setContentView(slate);
                    eraser.setColor(currentBackgroundColor);

                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2F0039")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#00CC5D'>SLATE</font>"));

                    if(stream!=null)
                        new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                stream.writeObject(new Action(-1, -1, Action.THEME_ZOMBIE));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();                    break;
                case R.id.valentine:
                    glow = false;
                    slate = new Slate(this);
                    slate.setBackgroundColor(Color.parseColor("#F47590"));
                    currentBackgroundColor = Color.parseColor("#F47590");
                    mPaint.setColor(Color.parseColor("#ffffff"));
                    setContentView(slate);
                    eraser.setColor(currentBackgroundColor);

                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F47590")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#ffffff'>SLATE</font>"));

                    if(stream!=null)
                        new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                stream.writeObject(new Action(-1, -1, Action.THEME_VALENTINE));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();                    break;
                case R.id.glow:
                    glow = true;
                    slate = new Slate(this);
                    slate.setBackgroundColor(Color.parseColor("#141d31"));
                    currentBackgroundColor = Color.parseColor("#141d31");
                    mPaint.setColor(Color.parseColor("#18ffff"));
                    setContentView(slate);
                    eraser.setColor(currentBackgroundColor);

                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#141d31")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#f91f73'>SLATE</font>"));

                    if(stream!=null)
                        new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                stream.writeObject(new Action(-1, -1, Action.THEME_NEON));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;


                case R.id.erase:
                    eraser.setColor(currentBackgroundColor);
                    if(eraserEnabled == false)
                    {
                        eraserEnabled = true;
                        item.setTitle("PEN");
                        item.setIcon(R.drawable.pen_click);
                    }
                    else {
                        eraserEnabled = false;
                        item.setTitle("ERASER");
                        item.setIcon(R.drawable.erase2);
                    }

                    if(stream!=null)
                        new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if(eraserEnabled)
                                stream.writeObject(new Action(-1, -1, Action.ERASE_BY_CLIENT));
                                else
                                stream.writeObject(new Action(-1, -1, Action.PEN_BY_CLIENT));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case R.id.clear:
                    slate = new Slate(this);
                    slate.setBackgroundColor(currentBackgroundColor);
                    //slate.clear(currentBackgroundColor);
                    setContentView(slate);

                    if(stream!=null)
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                stream.writeObject(new Action(-1, -1, Action.CLEAR));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;

            }
        return super.onOptionsItemSelected(item);
    }

    public static MainActivity getContext(){
        return context;
    }

    public void openDialog(){
        exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void startServer(){
        isServer = true;
        new ServerThread().execute();
    }

    @Override
    public void applyText(String ip) {
        isClient = true;
        new ClientThread().execute(ip);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isClient) {
            try {
                if(!(socket == null))
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //close server
        }
    }

    /*-----------------------------------------------------------------------------------------------------------------------------------------
    CUSTOM DRAWING VIEW     CUSTOM DRAWING VIEW     CUSTOM DRAWING VIEW     CUSTOM DRAWING VIEW     CUSTOM DRAWING VIEW     CUSTOM DRAWING VIEW
    -----------------------------------------------------------------------------------------------------------------------------------------*/

    public class Slate extends View {

        public int width;
        public  int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;


        public Slate(Context c) {
            super(c);
            context=c;                                      // Context
            mPath = new Path();                             // Path (mpath)
            mBitmapPaint = new Paint();                     // Paint
            mBitmapPaint.setDither(false);                  // Dither
            circlePaint = new Paint();                      // Paint
            circlePath = new Path();                        // Path (circle)
            circlePaint.setAntiAlias(true);                 // smoothening
            circlePaint.setColor(Color.GREEN);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);

        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);   // Bitmap
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
            if(eraserEnabled){
                canvas.drawPath( mPath, eraser);
            }else {
                canvas.drawPath(mPath, mPaint);
                if(glow)
                    canvas.drawPath( mPath, glowBrush);
            }
            canvas.drawPath( circlePath,  circlePaint);

        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            if(!eraserEnabled) {
                mCanvas.drawPath(mPath, mPaint);
                if(glow)
                    mCanvas.drawPath(mPath, glowBrush);
            }
            else{
                mCanvas.drawPath(mPath, eraser);
            }

            // kill this so we don't double draw
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final float x = event.getX();
            final float y = event.getY();
            final int ACTION;

            switch (ACTION = event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            if(isClient) {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            stream.writeObject(new Action(x, y, ACTION));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }

            return true;
        }

        public void draw(Action action)
        {
            try {
                switch (action.type) {
                    case 0:
                        touch_start(action.x, action.y);
                        invalidate();
                        break;

                    case 2:
                        touch_move(action.x, action.y);
                        invalidate();
                        break;

                    case 1:
                        touch_up();
                        invalidate();
                        break;
                }
            }catch (Exception e){e.printStackTrace();}
        }

        public void clear(final int currentBackgroundColor){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mCanvas.drawColor(currentBackgroundColor);
                }
            }).start();

        }
    }



    /*------------------------------------------------------------------------------------------------------------------------------------------

    BACKGROUND THREADS      BACKGROUND THREADS      BACKGROUND THREADS      BACKGROUND THREADS      BACKGROUND THREADS      BACKGROUND THREADS

    ------------------------------------------------------------------------------------------------------------------------------------------*/
    /*Background Thread*/
    /*Client aka sender*/
    class ClientThread extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            try {
                socket = new Socket(strings[0], 3107);
                stream = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /*Toast.makeText(getApplicationContext(),
                    "CONNECTED",
                    Toast.LENGTH_SHORT)
                    .show();*/
            FancyToast.makeText(getApplicationContext(),"CONNECTED",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

        }

    }


    /*Background Task*/
    /*Server aka receiver*/
    private class ServerThread extends AsyncTask<Void, Action, Void>
    {
        ServerSocket server;
        Socket       socket;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            server = null;
            socket = null;
            /*Toast.makeText(getApplicationContext(),
                    "LISTENING SOCKET...",
                    Toast.LENGTH_LONG).show();*/
            FancyToast.makeText(getApplicationContext(),"LISTENING SOCKET...",FancyToast.LENGTH_SHORT,FancyToast.INFO,true).show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Action action = null;
            ObjectInputStream stream = null;

            try {
                        server = new ServerSocket(3107);
                        socket = server.accept();
                        stream = new ObjectInputStream(socket.getInputStream());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(MainActivity.getContext(), "CONNECTED", Toast.LENGTH_SHORT).show();
                            FancyToast.makeText(getApplicationContext(),"CONNECTED",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                        }
                    });

                        while (true) {
                            if (isCancelled()) {
                                break;
                            }
                            action = (Action) stream.readObject();
                            publishProgress(action);
                        }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Action... actions) {
            super.onProgressUpdate(actions);
            if(actions[0].type==Action.DOWN || actions[0].type==Action.MOVE || actions[0].type==Action.UP)
            {
                slate.draw(actions[0]);
            }
            else if(actions[0].type == Action.CLEAR){
                slate = new Slate(MainActivity.this);
                slate.setBackgroundColor(currentBackgroundColor);
                setContentView(slate);
            }
            else if(actions[0].type==Action.ERASE_BY_CLIENT){
                    eraser.setColor(currentBackgroundColor);
                    eraserEnabled = true;
                    eraserItem.setTitle("PEN");
                    eraserItem.setIcon(R.drawable.pen_click);

            }
            else if(actions[0].type==Action.PEN_BY_CLIENT){
                    eraser.setColor(currentBackgroundColor);
                    eraserEnabled = false;
                    eraserItem.setTitle("ERASER");
                    eraserItem.setIcon(R.drawable.erase2);

            }
            else
            {
                glow = false;
                slate = new Slate(MainActivity.this);

                if(actions[0].type==Action.THEME_DEEPNIGHT){

                    slate.setBackgroundColor(Color.parseColor("#000000"));
                    currentBackgroundColor = Color.BLACK;
                    mPaint.setColor(Color.WHITE);
                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#ffffff'>SLATE</font>"));
                }
                else if(actions[0].type==Action.THEME_AQUA){

                    slate.setBackgroundColor(Color.parseColor("#76cdba"));
                    currentBackgroundColor = Color.parseColor("#76cdba");
                    mPaint.setColor(Color.parseColor("#ffffff"));
                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#76cdba")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#ffffff'>SLATE</font>"));

                }
                else if(actions[0].type==Action.THEME_SOLARIZED){

                    slate.setBackgroundColor(Color.parseColor("#002B37"));
                    currentBackgroundColor = Color.parseColor("#002B37");
                    mPaint.setColor(Color.parseColor("#006064"));
                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#002B37")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#006064'>SLATE</font>"));
                }
                else if(actions[0].type==Action.THEME_ZOMBIE){

                    slate.setBackgroundColor(Color.parseColor("#2F0039"));
                    currentBackgroundColor = Color.parseColor("#2F0039");
                    mPaint.setColor(Color.parseColor("#00CC5D"));
                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2F0039")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#00CC5D'>SLATE</font>"));
                }
                else if(actions[0].type==Action.THEME_VALENTINE){

                    slate.setBackgroundColor(Color.parseColor("#F47590"));
                    currentBackgroundColor = Color.parseColor("#F47590");
                    mPaint.setColor(Color.parseColor("#ffffff"));
                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F47590")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#ffffff'>SLATE</font>"));
                }
                else if(actions[0].type==Action.THEME_NEON){

                    glow = true;
                    slate.setBackgroundColor(Color.parseColor("#141d31"));
                    currentBackgroundColor = Color.parseColor("#141d31");
                    mPaint.setColor(Color.parseColor("#18ffff"));
                    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#141d31")));/*action bar color*/
                    bar.setTitle(Html.fromHtml("<font color='#f91f73'>SLATE</font>"));
                }
                eraser.setColor(currentBackgroundColor);
                setContentView(slate);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                socket.close();
                /*Toast.makeText(getApplicationContext(),
                        "BYE!",
                        Toast.LENGTH_SHORT).show();*/
                FancyToast.makeText(getApplicationContext(),"BYE!",FancyToast.LENGTH_SHORT,FancyToast.WARNING,true).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
