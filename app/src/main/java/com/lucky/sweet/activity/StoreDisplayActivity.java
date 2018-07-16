package com.lucky.sweet.activity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.adapter.CircleCommentAdapter;
import com.lucky.sweet.adapter.SearchSpinnerAdapter;
import com.lucky.sweet.adapter.ShowInfoListViewAdapter;
import com.lucky.sweet.entity.CollectStoreEntitiy;
import com.lucky.sweet.entity.StoreDisplayInfo;
import com.lucky.sweet.entity.StoreDisplaySearchEntity;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.MyToast;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Qiuyue on 2017/12/8.
 */

// ╭︿︿︿╮  ╭︿︿︿╮  ╭︿︿︿╮
// {/ o o /} {/ . . /} {/ ︿︿ /}
// ( (oo) )  ( (oo) )  ( (oo) )
//   ︶︶︶     ︶︶︶     ︶︶︶

public class StoreDisplayActivity extends BaseActivity {

    private Title title = null;
    @Bind(R.id.sp_businessarea)
    Spinner sp_BusinessArea;
    @Bind(R.id.sp_recreationtype)
    Spinner sp_RecreationType;
    @Bind(R.id.sp_rankyype)
    Spinner sp_RankType;

    private ListView lv_storeInfo;
    private SwipyRefreshLayout sw_store_info;
    private ShowInfoListViewAdapter adapter;
    private CommunicationService.MyBinder myBinder;
    private List<StoreDisplayInfo.MerListBean> displayList;
    private String businessArea = "";
    private String rankType = "";
    private String recreationType = "";
    private ArrayList<String> circle ;
    private ArrayList<List<String>> classify ;
    private List<String> order = new ArrayList<>();
    private int num = 0;
    private int bussinessAre = 0;
    private boolean addFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storedisplay);
        setIsBindEventBus();
        ButterKnife.bind(this);

        initViews();

        initTitle();

    }


    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {
        this.myBinder = myBinder;
        myBinder.requestStoreSearch();
    }

    private void initTitle() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        title = findViewById(R.id.title);

        Intent intent = getIntent();
        if (intent.getStringExtra("tv_moreFood") != null && intent.getStringExtra("tv_moreFood").equals("Food")) {
            title.setTitleNameStr("餐厅");
        } else if (intent.getStringExtra("tv_moreRelax") != null && intent.getStringExtra("tv_moreRelax").equals("Relax")) {
            title.setTitleNameStr("休闲");
        }

        intent.getStringExtra("city");
        Title.ButtonInfo buttonLeft = new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.drawable.selector_btn_titleback, null);
        title.setOnTitleButtonClickListener((id, viewHolder) -> {
            switch (id) {
                case Title.BUTTON_LEFT:
                    finish();
                    goPreAnim();
                    break;
            }
        });
        title.mSetButtonInfo(buttonLeft);
    }

    private void initViews() {
        lv_storeInfo = findViewById(R.id.lv_storeInfo);
        sw_store_info = findViewById(R.id.sw_store_info);
    }


    //设置监听事件，将来商家列表的排序都在这里面处理
    private void setListener() {
        sp_BusinessArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                bussinessAre = position;
                sp_RecreationType.setAdapter(new SearchSpinnerAdapter(classify.get(position), StoreDisplayActivity.this));
                recreationType = classify.get(position).get(0);
                businessArea = circle.get(position);
                requestSearchItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_RecreationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                recreationType = classify.get(bussinessAre).get(position);
                requestSearchItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_RankType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                rankType = order.get(position);
                requestSearchItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lv_storeInfo.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent = new Intent(StoreDisplayActivity.this, StoreParticularInfoActivity.class);
            intent.putExtra("shopid", displayList.get(position).getMer_id());
            startActivity(intent);
            goNextAnim();
        });
        sw_store_info.setOnRefreshListener(direction -> {
            switch (direction.name()) {
                case "TOP":
                    num = 0;
                    requestSearchItem();
                    break;
                case "BOTTOM":
                    num++;
                    addFlag = true;
                    myBinder.requestStoreDisplayInfo(recreationType, businessArea, rankType, num + "");
                    MyToast.showShort("加载更多");
                    break;
            }

        });


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(StoreDisplayInfo info) {
        if (sw_store_info.isRefreshing()) {
            sw_store_info.setRefreshing(false);
        }
        if (addFlag) {
            if (info.getMer_list().size() != 0) {
                displayList.addAll(info.getMer_list());
                adapter.notifyDataSetChanged();
            } else {
                MyToast.showShort("暂时无数据");
            }
        } else {
            displayList=info.getMer_list();
            adapter = new ShowInfoListViewAdapter(displayList, this);
            lv_storeInfo.setAdapter(adapter);

        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CollectStoreEntitiy collectStoreEntitiy) {
        if (collectStoreEntitiy.getAttr()) {
            String log;
            if (collectStoreEntitiy.isCollect()) {
                log = "收藏成功";
            } else {
                log = "取关成功";

            }

            MyToast.showShort(log);

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(StoreDisplaySearchEntity info) {
        circle = new ArrayList<>();
        classify = new ArrayList<>();
        StoreDisplaySearchEntity.CircleListBean list = info.getCircle_list();
        for (StoreDisplaySearchEntity.CircleListBean.SelectBean selectBean :  list.getSelect()) {
            circle.add(selectBean.getName());
            classify.add(selectBean.getList());
        }

        order = list.getOrder();
        businessArea = classify.get(0).get(0);
        rankType = circle.get(0);
        recreationType = order.get(0);

        sp_RecreationType.setAdapter(new SearchSpinnerAdapter(classify.get(0), this));
        sp_BusinessArea.setAdapter(new SearchSpinnerAdapter(circle, this));
        sp_RankType.setAdapter(new SearchSpinnerAdapter(order, this));

        setListener();

        myBinder.requestStoreDisplayInfo(businessArea, rankType, recreationType, num + "");

    }


    private void requestSearchItem() {
        num = 0;
        addFlag = false;
        myBinder.requestStoreDisplayInfo(recreationType, businessArea, rankType, num + "");
    }


}
