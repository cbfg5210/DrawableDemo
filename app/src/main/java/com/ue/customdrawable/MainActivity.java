package com.ue.customdrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv_image = (ImageView) findViewById(R.id.iv_image);
        ImageView iv_image1 = (ImageView) findViewById(R.id.iv_image1);
        ImageView iv_image2 = (ImageView) findViewById(R.id.iv_image2);

        iv_image.setImageDrawable(getShapeDrawable(40, Color.YELLOW, 0));

        iv_image1.setImageDrawable(getShapeStateListDrawable());
        iv_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = v.isSelected();
                v.setSelected(!isSelected);
            }
        });

        iv_image2.setImageDrawable(getShapeStateListDrawable());

        iv_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = v.isSelected();
                v.setSelected(!isSelected);
            }
        });
    }

    private StateListDrawable getShapeStateListDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        int selected = android.R.attr.state_selected;
        stateListDrawable.addState(new int[]{selected}, getShapeLayerDrawable(40, 10, Color.YELLOW, Color.WHITE));
        stateListDrawable.addState(new int[]{}, getShapeDrawable(40, Color.YELLOW, 0));//normal状态要放在后面
        return stateListDrawable;
    }

    private LayerDrawable getShapeLayerDrawable(int size, int inset, int fillColor, int strokeColor) {
        ShapeDrawable gd = getShapeDrawable(size, fillColor, 0);
        ShapeDrawable gd1 = getShapeDrawable(size, strokeColor, size / 8);
        Drawable[] drawables = new Drawable[]{gd, gd1};
        LayerDrawable layerDrawable = new LayerDrawable(drawables);
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

//    private LayerDrawable getLayerDrawable() {
//        GradientDrawable gd = getCircleDrawable(40, Color.WHITE, 5, Color.YELLOW);
//        GradientDrawable gd1 = getCircleDrawable(40, Color.YELLOW, 0, 0);
//        Drawable[] drawables = new Drawable[]{gd, gd1};
//        LayerDrawable layerDrawable = new LayerDrawable(drawables);
//        layerDrawable.setLayerInset(1, 10, 10, 10, 10);//size=40+10+10
//        return layerDrawable;
//    }

//    private GradientDrawable getCircleDrawable(int size, int fillColor, int strokeWidth, @ColorInt int strokeColor) {
//        GradientDrawable gd = new GradientDrawable();//创建drawable
//        gd.setColor(fillColor);
//        gd.setCornerRadius(100);
//        if (size > 0) {
//            gd.setSize(size, size);
//        }
//        gd.setStroke(strokeWidth, strokeColor);
//        return gd;
//    }

    public Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opts);
    }
}
