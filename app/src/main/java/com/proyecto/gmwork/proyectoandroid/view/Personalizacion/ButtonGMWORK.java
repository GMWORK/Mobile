package com.proyecto.gmwork.proyectoandroid.view.Personalizacion;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

import com.proyecto.gmwork.proyectoandroid.R;

/**
 * Created by Matthew on 21/05/2015.
 */
public class ButtonGMWORK extends Button {


    public ButtonGMWORK(Context context) {
        super(context);
        this.setBackgroundResource(R.drawable.botones_fondo);
    }

    public ButtonGMWORK(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundResource(R.drawable.botones_fondo);
    }

    public ButtonGMWORK(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackgroundResource(R.drawable.botones_fondo);
    }

   /* public ButtonGMWORK(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        this.setBackgroundResource(R.drawable.botones_fondo);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        this.setBackgroundResource(R.drawable.botones_fondo);
    }
}
