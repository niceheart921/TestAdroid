package com.yuyue.share.sharedemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.yuyue.share.sharedemo.contact.YuYueGlobalVariable;
import com.yuyue.share.sharedemo.widget.ShareDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ShareDialog.ShareDialogListener{
    private TextView clickShare;

    private static UMSocialService mController;
    private Activity mActivity;
    private String mShareUrl = "http://baidu.com", shareContent = "yuyue", shareTitle = "share", mSharePic = "";// 分享URL
    private Bitmap shareBitmap = null;
    private String mTitle = "";
    private String mTitle1 = "";
    private String mTitle33 = "sdfaadsf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        initData();

        initYoumengShare();
    }

    private void initData() {
        clickShare = (TextView) findViewById(R.id.clickShare);
        clickShare.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clickShare:
                shareWeb();
                break;
        }
    }

    /**
     * 分享入口
     *
     * @return
     */
    public void shareWeb() {
        ShareDialog sDialog = new ShareDialog(mActivity, MainActivity.this);
        sDialog.showDialog(0, 0);
    }

    private void initYoumengShare() {
        shareBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);
        mController = UMServiceFactory.getUMSocialService("myshare");
        SocializeConfig config = SocializeConfig.getSocializeConfig();
        config.closeToast();
        mController.setGlobalConfig(config);
        initSocialSDK();

    }

    private void initSocialSDK() {

        // // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(mActivity, YuYueGlobalVariable.APP_ID,
                YuYueGlobalVariable.APP_SECRET);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(mActivity, YuYueGlobalVariable.APP_ID,
                YuYueGlobalVariable.APP_SECRET);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        //QQ/空间
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity, YuYueGlobalVariable.APP_ID_QQ,
                YuYueGlobalVariable.APP_KEY_QQ);
        qqSsoHandler.addToSocialSDK();

        // 设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        //腾讯微博
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
    }


    private void shareEntry(SHARE_MEDIA platform, boolean isDirectShare) {

        // 设置文字分享内容
        mController.setShareContent(shareContent);
        // 图片分享内容   R.drawable.share_head
        mController.setShareMedia(new UMImage(mActivity,
                shareBitmap));
        if (platform == SHARE_MEDIA.WEIXIN) {
            // 设置微信好友分享内容
            WeiXinShareContent weixinContent = new WeiXinShareContent();

            // 设置分享文字
            weixinContent.setShareContent(shareContent);
            // 设置title
            weixinContent.setTitle(shareTitle);
            weixinContent.setShareImage(new UMImage(
                    mActivity, shareBitmap));
            // 设置分享内容跳转URL
            weixinContent.setTargetUrl(mShareUrl);
            mController.setShareMedia(weixinContent);

        } else if (platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
            CircleShareContent circleMedia = new CircleShareContent();
            circleMedia.setShareContent(shareContent);
            // 设置朋友圈title
            circleMedia.setTitle(shareTitle);
            circleMedia.setTargetUrl(mShareUrl);
            circleMedia.setShareImage(new UMImage(
                    mActivity, shareBitmap));
            mController.setShareMedia(circleMedia);
        } else if (platform == SHARE_MEDIA.SINA) {

            SinaShareContent sinaContent = new SinaShareContent();
            sinaContent.setTargetUrl(mShareUrl);
        } else if (platform == SHARE_MEDIA.QQ) {
            // QQ写法必须如此才能保证优先级，不然分享出去会被友盟的官网覆盖
            QQShareContent qqContent = new QQShareContent();
            // 图片分享内容
            qqContent.setShareMedia(new UMImage(
                    mActivity, shareBitmap));
            qqContent.setTitle(shareTitle);
            qqContent.setShareContent(shareContent);
            qqContent.setTargetUrl(mShareUrl);
            mController.setShareMedia(qqContent);
        } else if (platform == SHARE_MEDIA.QZONE) {

            QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
                    mActivity, "100424468",
                    "c7394704798a158208a74ab60104f0ba");
            qZoneSsoHandler.setTargetUrl(mShareUrl);
            qZoneSsoHandler.addToSocialSDK();

        } else if (platform == SHARE_MEDIA.TENCENT) {
            TencentWbShareContent txwbContent = new TencentWbShareContent();
            txwbContent.setTargetUrl(mShareUrl);
            txwbContent.setTitle(shareTitle);
        }

        if (isDirectShare) {
            // 调用直接分享
            mController.directShare(mActivity, platform,
                    mShareListener);
        } else {
            // 调用直接分享, 但是在分享前用户可以编辑要分享的内容
            mController.postShare(mActivity, platform,
                    mShareListener);
        }
    }

    /**
     * 分享监听器
     */
    SocializeListeners.SnsPostListener mShareListener = new SocializeListeners.SnsPostListener() {

        @Override
        public void onStart() {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int stCode,
                               SocializeEntity entity) {
            if (stCode == 200) {
                Toast.makeText(mActivity, "分享成功",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, "分享失败",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public void onClick(ShareDialog.ShareType type) {
        switch (type) {
            case WECHAT:
                shareEntry(SHARE_MEDIA.WEIXIN, true);
                // UMWXHandler wxHandler = new
                // UMWXHandler(mActivity, appId, appSecret);
                // wxHandler.addToSocialSDK();
                break;
            case WXCIRCLE:
                shareEntry(SHARE_MEDIA.WEIXIN_CIRCLE, true);
                break;
            case SINA:
                shareEntry(SHARE_MEDIA.SINA, true);
                break;
            case QQ:
                shareEntry(SHARE_MEDIA.QQ, true);
                break;
            case QZONE:
                shareEntry(SHARE_MEDIA.QZONE, true);
                break;
            case TXWB:
                shareEntry(SHARE_MEDIA.TENCENT, true);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);

        }
    }
}
