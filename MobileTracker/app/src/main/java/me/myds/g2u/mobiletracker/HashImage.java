package me.myds.g2u.mobiletracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class HashImage extends View {

    private String hashString;

    public HashImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HashImage(Context context) {
        super(context);
    }

    public void setHashString(String hashString) {
        if (hashString.length() < 9 * 6) {
            hashString += new String(new char[9 * 6 - hashString.length()]).replace("\0", "0");
        }
        this.hashString = hashString.substring(0, 9 * 6);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (hashString == null) return;

        int h = canvas.getHeight() / 3;
        int w = canvas.getWidth() / 3;

        Paint pnt = new Paint();
        pnt.setAntiAlias(true);

        for (int i = 0; 9 > i; i ++) {
            String hexColor = hashString.substring(i * 6, (i + 1) * 6);
            int r = Integer.valueOf(hexColor.substring(0, 2), 16);
            int g = Integer.valueOf(hexColor.substring(2, 4), 16);
            int b = Integer.valueOf(hexColor.substring(4, 6), 16);
            pnt.setARGB(255, r, g, b);
            int x = i % 3;
            int y = i / 3;

            canvas.drawRect(x * w, y * h, (x+1) * w, (y+1) * h, pnt);
        }
    }
}
