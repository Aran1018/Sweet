package com.lucky.sweet.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lucky.sweet.R;
import com.lucky.sweet.adapter.DisLeftListAdapter;
import com.lucky.sweet.adapter.MainSectionedAdapter;
import com.lucky.sweet.entity.ShopCarInfo;
import com.lucky.sweet.model.OrderManager;
import com.lucky.sweet.service.CommunicationService;
import com.lucky.sweet.views.PinnedHeaderListView;
import com.lucky.sweet.views.ShopCarPopWindow;
import com.lucky.sweet.widgets.Title;
import com.lucky.sweet.widgets.ToolBar;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity {

    private Title title = null;

    @Bind(R.id.left_listview)
    ListView leftListview;
    @Bind(R.id.pinnedListView)
    PinnedHeaderListView pinnedListView;
    private boolean isScroll = true;
    private DisLeftListAdapter adapter;

    private String[] leftStr;
    private boolean[] flagArray = {true, false, false, false, false, false, false, false, false};
    private String[][] rightStr;
    private ArrayList<ShopCarInfo> shopCarInfoList = new ArrayList<>();
    private HashMap<String, Integer> menuDirectory = new HashMap<String, Integer>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initTitle();

        leftStr = OrderManager.getDishesType();
        rightStr = OrderManager.getDishesList();

        ButterKnife.bind(this);
        pinnedListView =  findViewById(R.id.pinnedListView);
        final MainSectionedAdapter sectionedAdapter = new MainSectionedAdapter(this, leftStr, rightStr);

        pinnedListView.setAdapter(sectionedAdapter);
        sectionedAdapter.setOrderNumListener(new MainSectionedAdapter.OrderNumListener() {
            @Override
            public void onDataChange(int section, int position, int num) {
                try {
                    Integer integer = menuDirectory.get(rightStr[section][position]);
                    if (integer == null) {

                        ShopCarInfo shopCarInfo = new ShopCarInfo(rightStr[section][position], num, 0);
                        shopCarInfoList.add(shopCarInfo);
                        menuDirectory.put(rightStr[section][position], shopCarInfoList.size() - 1);

                    } else {

                        shopCarInfoList.get(integer).setNum(num);

                    }
                } catch (NullPointerException e) {
                    throw e;
                }

            }
        });
        adapter = new DisLeftListAdapter(this, leftStr, flagArray);
        leftListview.setAdapter(adapter);
        leftListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                isScroll = false;

                for (int i = 0; i < leftStr.length; i++) {
                    if (i == position) {
                        flagArray[i] = true;
                    } else {
                        flagArray[i] = false;
                    }
                }
                adapter.notifyDataSetChanged();
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += sectionedAdapter.getCountForSection(i) + 1;
                }
                pinnedListView.setSelection(rightSection);

            }

        });


        pinnedListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView arg0, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (pinnedListView.getLastVisiblePosition() == (pinnedListView.getCount() - 1)) {
                            leftListview.setSelection(ListView.FOCUS_DOWN);
                        }

                        // 判断滚动到顶部
                        if (pinnedListView.getFirstVisiblePosition() == 0) {
                            leftListview.setSelection(0);
                        }

                        break;
                }
            }

            int y = 0;
            int x = 0;
            int z = 0;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < rightStr.length; i++) {
                        if (i == sectionedAdapter.getSectionForPosition(pinnedListView.getFirstVisiblePosition())) {
                            flagArray[i] = true;
                            x = i;
                        } else {
                            flagArray[i] = false;
                        }
                    }
                    if (x != y) {
                        adapter.notifyDataSetChanged();
                        y = x;
                        if (y == leftListview.getLastVisiblePosition()) {
//                            z = z + 3;
                            leftListview.setSelection(z);
                        }
                        if (x == leftListview.getFirstVisiblePosition()) {
//                            z = z - 1;
                            leftListview.setSelection(z);
                        }
                        if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                            leftListview.setSelection(ListView.FOCUS_DOWN);
                        }
                    }
                } else {
                    isScroll = true;
                }
            }
        });

    }

    @Override
    void onServiceBind(CommunicationService.MyBinder myBinder) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initTitle() {
        ToolBar toolBar = new ToolBar(this);
        toolBar.setColorNewBar(getResources().getColor(R.color.white), 0);
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("点餐列表");

        Title.ButtonInfo buttonLeft = new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.drawable.selector_btn_titleback, null);
        final Title.ButtonInfo buttonRigt = new Title.ButtonInfo(true, Title
                .BUTTON_RIGHT1, R.mipmap.shop_car, null);
        title.setOnTitleButtonClickListener(new Title
                .OnTitleButtonClickListener() {
            @Override
            public void onClick(int id, Title.ButtonViewHolder viewHolder) {
                switch (id) {
                    case Title.BUTTON_LEFT:
                        finish();
                        break;
                    case Title.BUTTON_RIGHT1:

                        System.out.println(viewHolder.image);

                        new ShopCarPopWindow(OrderActivity.this, viewHolder.image, shopCarInfoList);
                        break;


                }
            }
        });

        title.mSetButtonInfo(buttonLeft);
        title.mSetButtonInfo(buttonRigt);

    }

}
