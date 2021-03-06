package com.example.wangchang.testbottomnavigationbar.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangchang.testbottomnavigationbar.R;
import com.example.wangchang.testbottomnavigationbar.base.BaseActivity;
import com.example.wangchang.testbottomnavigationbar.base.SystemBarTintManager;
import com.example.wangchang.testbottomnavigationbar.fragment.OneFragment;
import com.example.wangchang.testbottomnavigationbar.listen.AppBarStateChangeListener;
import com.example.wangchang.testbottomnavigationbar.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 电影详情页
 */
@SuppressWarnings("ResourceAsColor")
public class BookShowActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(android.R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tablayout_btn)
    Button tablayoutBtn;


    private Context mContext;

    /*
    * 改变状态栏
    */
    @TargetApi(19)
    public void initSystemBar(int resColorId) {
        if (Build.VERSION.SDK_INT >= 19) {

            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            // 透明状态栏
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 打开系统状态栏控制
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(resColorId);// 设置背景

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_show);
        mContext = this;

        ButterKnife.bind(this);
//        toolbar.setTitle("飞机大作战");
        toolbar.setNavigationIcon(R.drawable.ic_back);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewPager.setAdapter(new MyFragmentPagerAdaper(getSupportFragmentManager()));

        tabs.setBackgroundColor(ContextCompat.getColor(this, R.color.title_color));
        tabs.setupWithViewPager(viewPager);

        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                LogUtil.e("State", state.name());

                if (state == State.EXPANDED) {
                    //展开状态
                    toolbarTitle.setVisibility(View.INVISIBLE);
                    tablayoutBtn.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= 21) {
                        getWindow().setStatusBarColor(Color.TRANSPARENT);
                    }
                    toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                   /* Button button = new Button(mContext);
                    tabs.addView(button,3);*/
                    tablayoutBtn.setVisibility(View.VISIBLE);
                    collapsingToolbar.setCollapsedTitleGravity(Gravity.LEFT);

                    toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.page_bg));
                } else if(state == State.MIDLLE){
//                    ToastUtils.show(mContext,"image不可见了");
                }else {
                    //中间状态
                    toolbarTitle.setVisibility(View.VISIBLE);
                    tablayoutBtn.setVisibility(View.GONE);
                    toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                }
            }
        });
//        initSystemBar(R.color.transparent);
        /*if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
           actionBar.hide();*/
        setImage(this);
    }

    /**
     * 当顶部是图片时，是图片显示到状态栏上
     * @param activity
     */
    private void setImage(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0及以上，不设置透明状态栏，设置会有半透明阴影
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //是activity_main。xml中的图片可以沉浸到状态栏上
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置状态栏颜色透明。
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            //。。。。
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * Viewpager监听
     */
    class MyFragmentPagerAdaper extends FragmentPagerAdapter {

        public MyFragmentPagerAdaper(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new OneFragment();
                case 1:
                    return new OneFragment();
                case 2:
                    return new OneFragment();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_tapdetail);
                case 1:
                    return getString(R.string.title_taprevaluate);
                case 2:
                    return getString(R.string.title_tapdiscuss);
                default:
                    return "基本";
            }
        }
    }


}
