package com.wemo.medical.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

@SuppressLint("DrawAllocation")
public class DrawView extends View {

	public DrawView(Context context) {
		super(context);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint p = new Paint();
		p.setColor(Color.BLACK);// 设置红色

		canvas.drawLine(100, 100, 100, 500, p);
		canvas.drawLine(100, 500, 300, 500, p);

		p.setColor(Color.GRAY);// 设置红色
		canvas.drawText("高风险", 50, 150, p);
		canvas.drawLine(90, 145, 100, 145, p);

		canvas.drawText("较高风险", 38, 220, p);
		canvas.drawLine(90, 215, 100, 215, p);

		canvas.drawText("中等风险", 38, 290, p);
		canvas.drawLine(90, 285, 100, 285, p);

		canvas.drawText("较低风险", 38, 360, p);
		canvas.drawLine(90, 355, 100, 355, p);

		canvas.drawText("低风险", 50, 450, p);
		canvas.drawLine(90, 445, 100, 445, p);

		canvas.drawText("风险等级", 115, 520, p);
		canvas.drawText("当前风险", 180, 520, p);
		canvas.drawText("理想风险", 240, 520, p);

		// 第一条柱状图
		p.setStrokeWidth(40);

		//
		p.setColor(Color.rgb(232, 115, 98));
		canvas.drawLine(140, 100, 140, 180, p);

		p.setColor(Color.rgb(255, 103, 38));
		canvas.drawLine(140, 180, 140, 250, p);

		p.setColor(Color.rgb(255, 124, 46));
		canvas.drawLine(140, 250, 140, 320, p);

		p.setColor(Color.rgb(36, 189, 71));
		canvas.drawLine(140, 320, 140, 390, p);

		p.setColor(Color.rgb(51, 204, 102));
		canvas.drawLine(140, 390, 140, 500, p);

		p.setColor(Color.BLUE);
		canvas.drawLine(200, 200, 200, 500, p);

		p.setColor(Color.BLACK);

		canvas.drawLine(260, 410, 260, 500, p);

	}
}
