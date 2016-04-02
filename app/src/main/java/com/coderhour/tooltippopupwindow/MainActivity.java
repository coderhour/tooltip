package com.coderhour.tooltippopupwindow;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.coderhour.tooltip.TooltipPopupWindow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnLeft, mBtnTop, mBtnRight, mBtnBottom;

    private TextView mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnLeft = (Button) findViewById(R.id.btnLeft);
        mBtnTop = (Button) findViewById(R.id.btnTop);
        mBtnRight = (Button) findViewById(R.id.btnRight);
        mBtnBottom = (Button) findViewById(R.id.btnBottom);

        mBtnLeft.setOnClickListener(this);
        mBtnTop.setOnClickListener(this);
        mBtnRight.setOnClickListener(this);
        mBtnBottom.setOnClickListener(this);

        mContentView = new TextView(this);
        mContentView.setText("Happy Friday, everyone! What better way to enjoy the start of your weekend" +
                "\n than by getting a bunch of paid iPhone apps that are on sale for free right now for a " +
                "\nlimited time? Today we've rounded up 12, yes 12, iPhone and iPad apps that would normally" +
                "\n cost $41 but are on sale for free if you act right now. Let's check them out!" +
                "\n" +
                "\nMUST SEE: Videos offer first look at what it’s like driving Tesla’s Model 3" +
                "\n" +
                "\nThese are paid iPhone and iPad apps that have been made available for free for a limited" +
                "\n time by their developers. There is no way to tell how long they will be free. These sales " +
                "\ncould end an hour from now or a week from now — obviously, the only thing we can guarantee " +
                "\nis that they were free at the time this post was written. If you click on a link and see a" +
                "\n price listed next to an app instead of the word “get,” it is no longer free. The sale has ended." +
                "\n If you download the app, you will be charged.");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLeft) {
            new TooltipPopupWindow(mContentView, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                    .setWindowPosition(TooltipPopupWindow.POS_LEFT)
                    .setAnchorView(mBtnLeft)
                    .setOutsideTouchableChain(true)
                    .show(getWindow().getDecorView());
        } else if (view.getId() == R.id.btnTop) {
            new TooltipPopupWindow(mContentView, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                    .setWindowPosition(TooltipPopupWindow.POS_TOP)
                    .setAnchorView(mBtnTop)
                    .setOutsideTouchableChain(true)
                    .show(getWindow().getDecorView());
        } else if (view.getId() == R.id.btnRight) {
            new TooltipPopupWindow(mContentView, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                    .setWindowPosition(TooltipPopupWindow.POS_RIGHT)
                    .setAnchorView(mBtnRight)
                    .setOutsideTouchableChain(true)
                    .show(getWindow().getDecorView());
        } else if (view.getId() == R.id.btnBottom) {
            new TooltipPopupWindow(mContentView, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                    .setWindowPosition(TooltipPopupWindow.POS_BOTTOM)
                    .setAnchorView(mBtnBottom)
                    .setOutsideTouchableChain(true)
                    .setBackgroundColor(Color.CYAN)
                    .show(getWindow().getDecorView());
        }

    }

}
