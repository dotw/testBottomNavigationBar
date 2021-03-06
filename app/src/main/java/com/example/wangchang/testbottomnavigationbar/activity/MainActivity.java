package com.example.wangchang.testbottomnavigationbar.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.animation.AlphaAnimation;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.wangchang.testbottomnavigationbar.R;
import com.example.wangchang.testbottomnavigationbar.base.ActivityLifeCycleEvent;
import com.example.wangchang.testbottomnavigationbar.base.BaseActivity;
import com.example.wangchang.testbottomnavigationbar.fragment.BookFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.GameFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.HomeFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.MusicFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.TvFragment;
import com.example.wangchang.testbottomnavigationbar.util.NetUtil;
import com.example.wangchang.testbottomnavigationbar.view.SimpleLoadDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subjects.PublishSubject;

@SuppressWarnings("ResourceAsColor")
public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.toolBar_home)
    Toolbar toolBarHome;
    private ArrayList<Fragment> fragments;
    private Context mContext;

    private HomeFragment homeFragment;
    private BookFragment bookFragment;
    private MusicFragment musicFragment;
    private TvFragment tvFragment;
    private GameFragment gameFragment;

    private BadgeItem numberBadgeItem;
    private SimpleLoadDialog dialogHandler;
    public static MainActivity activity;

    public static MainActivity getInstance() {
        if (activity != null) {
            return activity;
        }
        return activity;
    }

    public PublishSubject<ActivityLifeCycleEvent> getLifeSubject() {
        return lifecycleSubject;
    }

    BottomNavigationBar bottomNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        mContext = this;
        activity = this;
        ButterKnife.bind(this);
        initToolBar("清纯妹纸","Home",0);
        dialogHandler = new SimpleLoadDialog(MainActivity.this, null, true);
         bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar.setBarBackgroundColor(R.color.page_bg);
//        toolBarHome.setBackgroundColor(ContextCompat.getColor(mContext,R.color.page_bg));
        numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColor(Color.RED)
                .setText("5")
                .setHideOnSelect(true);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp, "Home").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "Books").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.mipmap.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.white))
                .addItem(new BottomNavigationItem(R.mipmap.ic_videogame_asset_white_24dp, "Games").setActiveColorResource(R.color.white).setBadgeItem(numberBadgeItem))
                .setFirstSelectedPosition(0)
                .initialise();
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(500);
        bottomNavigationBar.setAnimation(alphaAnimation);

        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
        if(!NetUtil.isConnected(mContext)){
            bottomNavigationBar.unHide();
        }

    }

    private void initToolBar(String title,String subtitle,int resId) {
        toolBarHome.setTitle(title);
        toolBarHome.setSubtitle(subtitle);
        toolBarHome.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolBarHome);
    }
    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = HomeFragment.newInstance("Home");
        transaction.replace(R.id.layFrame, homeFragment);
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        homeFragment = (HomeFragment.newInstance("Home"));
        fragments.add(homeFragment);
        bookFragment = BookFragment.newInstance("Books");
        fragments.add(bookFragment);
        musicFragment = MusicFragment.newInstance("Music");
        fragments.add(musicFragment);
        tvFragment = TvFragment.newInstance("Movies & TV");
        fragments.add(tvFragment);
        gameFragment = GameFragment.newInstance("Games");
        fragments.add(gameFragment);
        return fragments;
    }


    @Override
    public void onTabSelected(int position) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                initToolBar("清纯妹纸","Home",0);
                if (homeFragment == null) {
                    homeFragment = (HomeFragment.newInstance("Home"));
                }

                transaction.replace(R.id.layFrame, homeFragment);
                transaction.commit();
                break;
            case 1:
                initToolBar("豆瓣电影Top250","Books",0);
                if (bookFragment == null) {
                    bookFragment = BookFragment.newInstance("Books");
                }
                transaction.replace(R.id.layFrame, bookFragment);
                transaction.commit();
                break;
            case 2:
                initToolBar("梦幻音乐","Music",0);
                if (musicFragment == null) {
                    musicFragment = MusicFragment.newInstance("Music");
                }
                transaction.replace(R.id.layFrame, musicFragment);
                transaction.commit();
                break;
            case 3:
                initToolBar("眼观世界","Movies & TV",0);
                if (tvFragment == null) {
                    tvFragment = TvFragment.newInstance("Movies & TV");
                }
                transaction.replace(R.id.layFrame, tvFragment);
                transaction.commit();
                break;
            case 4:
                initToolBar("游戏频道","Games",0);
                if (gameFragment == null) {
                    gameFragment = GameFragment.newInstance("Games");
                }
                transaction.replace(R.id.layFrame, gameFragment);
                transaction.commit();
                break;
            default:
                break;
        }


    }


    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}
