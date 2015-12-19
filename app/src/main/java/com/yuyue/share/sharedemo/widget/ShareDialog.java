package com.yuyue.share.sharedemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyue.share.sharedemo.R;


public class ShareDialog extends Dialog implements View.OnClickListener{
	
	private ImageView wechatIv,wxcircleIv, sinaIv, qqIv, qzoneIv, txwbIv;
	private TextView cancelTv;
	
	private Window window = null;
	private ShareDialogListener listener;
	
	public enum ShareType{
		WECHAT, WXCIRCLE, SINA, QQ, QZONE,TXWB	
	}
	

	public ShareDialog(Context context, ShareDialogListener listener) {
		super(context);
		this.listener = listener;
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_share);
		initViews();	
		
	}
	
	private void initViews(){
		
		wechatIv = (ImageView) findViewById(R.id.dialog_share_weixin_iv);
		wxcircleIv = (ImageView) findViewById(R.id.dialog_share_wx_circle_iv);
		sinaIv = (ImageView) findViewById(R.id.dialog_share_sina_iv);
		qqIv =  (ImageView) findViewById(R.id.dialog_share_qq_iv);
		qzoneIv =  (ImageView) findViewById(R.id.dialog_share_qzone_iv);
		txwbIv =  (ImageView) findViewById(R.id.dialog_share_txwb_iv);
		cancelTv =  (TextView) findViewById(R.id.dialog_share_cancel_tv);
		
		wechatIv.setOnClickListener(this);
		wxcircleIv.setOnClickListener(this);
		sinaIv.setOnClickListener(this);
		qqIv.setOnClickListener(this);
		qzoneIv.setOnClickListener(this);
		txwbIv.setOnClickListener(this);
		cancelTv.setOnClickListener(this);
	}
	
	
	
	public void showDialog(int x, int y) {
		windowDeploy(x, y);
		// 设置触摸对话框以外的地方取消对话框
		setCanceledOnTouchOutside(true);
		show();
	}
	
	
	// 设置窗口显示
	public void windowDeploy(int x, int y) {
		window = getWindow(); // 得到对话框
		window.setWindowAnimations(R.style.dialog_window_anim); // 设置窗口弹出动画
		// window.setBackgroundDrawableResource(R.color.vifrification);
		// //设置对话框背景为透明
		WindowManager.LayoutParams wl = window.getAttributes();
		// 根据x，y坐标设置窗口需要显示的位置
		wl.x = x; // x小于0左移，大于0右移
		wl.y = y; // y小于0上移，大于0下移
		// wl.alpha = 0.6f; //设置透明度
		// wl.gravity = Gravity.BOTTOM; //设置重力
		window.setAttributes(wl);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.dialog_share_weixin_iv:
			listener.onClick(ShareType.WECHAT);
			break;
		case R.id.dialog_share_wx_circle_iv:
			listener.onClick(ShareType.WXCIRCLE);
			break;
		case R.id.dialog_share_sina_iv:	
			listener.onClick(ShareType.SINA);
			break;
		case R.id.dialog_share_qq_iv:
			listener.onClick(ShareType.QQ);
			break;
		case R.id.dialog_share_qzone_iv:
			listener.onClick(ShareType.QZONE);
			break;
		case R.id.dialog_share_txwb_iv:
			listener.onClick(ShareType.TXWB);
			break;
		case R.id.dialog_share_cancel_tv:
			dismiss();
			break;
		}
		
	}
	
	public interface ShareDialogListener {
		
		public void onClick(ShareType type);
		
	}
	
	

}
