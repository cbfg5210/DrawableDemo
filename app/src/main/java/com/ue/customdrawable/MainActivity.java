package com.ue.customdrawable;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv_image = (ImageView) findViewById(R.id.iv_image);
        ImageView iv_image1 = (ImageView) findViewById(R.id.iv_image1);
        ImageView iv_image2 = (ImageView) findViewById(R.id.iv_image2);

//        iv_image.setImageDrawable(getShapeDrawable(40, Color.YELLOW, 0));
        iv_image.setImageDrawable(getTwoImgLayerDrawable(R.mipmap.ic_pill_1_2_color, Color.YELLOW, R.mipmap.ic_pill_1_color, Color.WHITE));

        iv_image1.setImageDrawable(getShapeStateListDrawable(40, Color.YELLOW, Color.WHITE));
        iv_image1.setOnClickListener(this);

        iv_image2.setImageDrawable(getShapeStateListDrawable(40, Color.YELLOW, Color.WHITE));
        iv_image2.setOnClickListener(this);
    }

    private LayerDrawable getOneImgLayerDrawable(int imgRes, int imgColor) {
        return getTwoImgLayerDrawable(imgRes, imgColor, -1, -1);
    }

    private LayerDrawable getTwoImgLayerDrawable(int imgRes1, int imgColor1, int imgRes2, int imgColor2) {
        if (imgRes1 <= 0) {
            return null;
        }
        Drawable drawable1 = ContextCompat.getDrawable(this, imgRes1);
        drawable1.setColorFilter(imgColor1, PorterDuff.Mode.MULTIPLY);
        if (imgRes2 <= 0) {
            return new LayerDrawable(new Drawable[]{drawable1});
        }
        Drawable drawable2 = ContextCompat.getDrawable(this, imgRes2);
        drawable2.setColorFilter(imgColor2, PorterDuff.Mode.MULTIPLY);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{drawable1, drawable2});
        return layerDrawable;
    }

    private StateListDrawable getShapeStateListDrawable(int size, int fillColor, int strokeColor) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        int selected = android.R.attr.state_selected;
        stateListDrawable.addState(new int[]{selected}, getShapeLayerDrawable(size, fillColor, strokeColor));
        stateListDrawable.addState(new int[]{}, getShapeDrawable(size, fillColor, -1));//normal状态要放在后面
        return stateListDrawable;
    }

    private LayerDrawable getShapeLayerDrawable(int size, int fillColor, int strokeColor) {
        ShapeDrawable gd = getShapeDrawable(size, fillColor, 0);
        ShapeDrawable gd1 = getShapeDrawable(size, strokeColor, size / 8);
        Drawable[] drawables = new Drawable[]{gd, gd1};
        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        int inset = size / 4;
        layerDrawable.setLayerInset(1, inset, inset, inset, inset);//size=40+10+10
        return layerDrawable;
    }

    private ShapeDrawable getShapeDrawable(int size, int fillColor, int strokeWidth) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(fillColor);
        shapeDrawable.setIntrinsicWidth(size);
        shapeDrawable.setIntrinsicHeight(size);
        if (strokeWidth > 0) {
            shapeDrawable.getPaint().setStrokeWidth(strokeWidth);
            shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
        }
        return shapeDrawable;
    }

    @Override
    public void onClick(View v) {
        boolean isSelected = v.isSelected();
        v.setSelected(!isSelected);
    }
}
