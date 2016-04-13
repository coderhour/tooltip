package com.coderhour.tooltippopupwindow;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.coderhour.tooltip.TooltipPopupWindow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnLeft, mBtnTop, mBtnRight, mBtnBottom, mBtnTopLeft, mBtnRightBottom;

    private TextView mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnLeft = (Button) findViewById(R.id.btnLeft);
        mBtnTop = (Button) findViewById(R.id.btnTop);
        mBtnRight = (Button) findViewById(R.id.btnRight);
        mBtnBottom = (Button) findViewById(R.id.btnBottom);
        mBtnTopLeft = (Button) findViewById(R.id.btnTopLeft);
        mBtnRightBottom = (Button) findViewById(R.id.btnRightBottom);

        mBtnLeft.setOnClickListener(this);
        mBtnTop.setOnClickListener(this);
        mBtnRight.setOnClickListener(this);
        mBtnBottom.setOnClickListener(this);
        mBtnTopLeft.setOnClickListener(this);
        mBtnRightBottom.setOnClickListener(this);

        mContentView = new TextView(this);
        mContentView.setText("Happy every day! Cool! It's a lonnnnnnnnnnnnnnnnnnnng line" +
        "\nsdfffffffffffl" +
        "\nsdfffffffffffl" +
        "\nsdfffffffffffl" +
        "\nsdfffffffffffl" +
        "\nsdfffffffffffl"
        );
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLeft) {
            new TooltipPopupWindow(mContentView, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                    .setWindowPosition(TooltipPopupWindow.POS_LEFT)
                    .setAnchorView(mBtnLeft)
                    .setOutsideTouchableChain(true)
                    .setBackgroundColor(Color.YELLOW)
                    .show(getWindow().getDecorView());
        } else if (view.getId() == R.id.btnTop) {
            new TooltipPopupWindow(mContentView, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                    .setWindowPosition(TooltipPopupWindow.POS_ABOVE)
                    .setAnchorView(mBtnTop)
                    .setOutsideTouchableChain(true)
                    .setBackgroundColor(Color.GREEN)
                    .show(getWindow().getDecorView());
        } else if (view.getId() == R.id.btnRight) {
            new TooltipPopupWindow(mContentView, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                    .setWindowPosition(TooltipPopupWindow.POS_RIGHT)
                    .setAnchorView(mBtnRight)
                    .setOutsideTouchableChain(true)
                    .setBackgroundColor(Color.GRAY)
                    .show(getWindow().getDecorView());
        } else if (view.getId() == R.id.btnBottom) {
            new TooltipPopupWindow(mContentView, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                    .setWindowPosition(TooltipPopupWindow.POS_BELOW)
                    .setAnchorView(mBtnBottom)
                    .setOutsideTouchableChain(true)
                    .setBackgroundColor(Color.CYAN)
                    .show(getWindow().getDecorView());
        } else if (view.getId() == R.id.btnTopLeft) {
            new TooltipPopupWindow(mContentView, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                    .setWindowPosition(TooltipPopupWindow.POS_AUTO)
                    .setAnchorView(mBtnTopLeft)
                    .setOutsideTouchableChain(true)
                    .setBackgroundColor(Color.CYAN)
                    .show(getWindow().getDecorView());
        } else if (view.getId() == R.id.btnRightBottom) {
            new TooltipPopupWindow(mContentView, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                    .setWindowPosition(TooltipPopupWindow.POS_AUTO)
                    .setAnchorView(mBtnRightBottom)
                    .setOutsideTouchableChain(true)
                    .setBackgroundColor(Color.CYAN)
                    .show(getWindow().getDecorView());
        }

    }

}
