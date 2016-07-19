package com.softgarden.garden.view.shopcar.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.entity.OrderCommitEntity;
import com.softgarden.garden.entity.TempDataBean;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.other.ShoppingCart;
import com.softgarden.garden.utils.StringUtils;
import com.softgarden.garden.view.shopcar.adapter.ShopcartExpandableListViewAdapter;
import com.softgarden.garden.view.shopcar.entity.GroupInfo;
import com.softgarden.garden.view.start.entity.MessageBean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class ShopcarActivity extends BaseActivity implements ShopcartExpandableListViewAdapter
        .CheckInterface,Observer{

    private CheckBox cb_all;
    private ExpandableListView exListView;
    private TextView tv_totalprice;
    private TextView tv_amount;
    private TextView tv_right;
    private TextView tv_date;
    private ShopcartExpandableListViewAdapter adapter;
    private RelativeLayout rl_date;
    private Button btn_delete;
    private LinearLayout ll_commit_order;
    private LinearLayout ll_check_all;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopcar);
        // 设置监听
        ShoppingCart.getInstance().addObserver(this);

        ll_check_all = getViewById(R.id.ll_check_all);
        tv_right = getViewById(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("编辑");

        tv_date = getViewById(R.id.tv_date);
        tv_date.setText(StringUtils.getCurrDate());

        cb_all = getViewById(R.id.cb_all);
        tv_totalprice = getViewById(R.id.tv_totalprice);
        tv_amount = getViewById(R.id.tv_amount);
        rl_date = getViewById(R.id.rl_date);
        btn_delete = getViewById(R.id.btn_delete);
        ll_commit_order = getViewById(R.id.ll_commit_order);
    }

    private ArrayList<GroupInfo>groups = new ArrayList<>();
    private Map<String, List<OrderCommitEntity.ZstailBean>> children = new HashMap<String, List<OrderCommitEntity.ZstailBean>>();//
    // 子元素数据列表
    private void getData() {
        children.clear();
        // 遍历所有数据，只要团购数和数量两个字段不为0就为购物车的数据
        List<IndexEntity.DataBean.ShopBean> shop = BaseApplication.indexEntity.getData()
                .getShop();
        int classSize = shop.size();
        for(int i = 0;i<classSize;i++){// 一级分类
            int groupSize = shop.get(i).getChild().size();
            for(int j = 0;j<groupSize;j++){// 二级分类
                int goodsSize = shop.get(i).getChild().get(j).getGoods()
                        .size();
                ArrayList<OrderCommitEntity.ZstailBean> products = new ArrayList<>();
                for(int k = 0;k<goodsSize;k++){// 所有子项
                    IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean goodsBean = shop.get(i)
                            .getChild().get(j).getGoods().get(k);
                    boolean isInTempData = false;
                    for (TempDataBean item: BaseApplication.tempDataBeans){
                        if(goodsBean.getItemNo().equals(item.getIetmNo())){
                            // tempDataBeans存在，即用户操作过此数据，判断此bean的tuangou和shuliang字段如果有一个不为0，则为购物车数据
                            if(item.getTuangou() != 0 || item.getShuliang() != 0) {
                                addGroup(goodsBean);
                                OrderCommitEntity.ZstailBean productinfo = generateProductInfo(goodsBean, item
                                        .getTuangou(),item.getShuliang());
                                products.add(productinfo);
                            }
                            isInTempData = true;
                            break;
                        }
                    }
                    if(!isInTempData){
                        int count = Integer.parseInt(goodsBean.getProQty());
                        if(count!=0) {
                            addGroup(goodsBean);
                            OrderCommitEntity.ZstailBean productinfo = generateProductInfo
                                    (goodsBean,0,Integer.parseInt(goodsBean.getProQty()));
                            products.add(productinfo);
                        }
                    }
                }
                if (products.size()>0) {
                    children.put(shop.get(i).getChild().get(j).getItemgroupcdoe(),products);
                }
            }
        }
    }

    private OrderCommitEntity.ZstailBean generateProductInfo(IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean
                                                goodsBean, int tuangou, int shuliang) {
        // 计算总价
        float price = goodsBean.getIsSpecial() == 0?Float.parseFloat
                (goodsBean.getBzj()): Float.parseFloat( goodsBean.getPrice());
        int count = tuangou + shuliang;
        OrderCommitEntity.ZstailBean productinfo = new OrderCommitEntity
                .ZstailBean(count*price, goodsBean.getIsSpecial(),
                goodsBean.getItemGroupName(), goodsBean.getItemName(), goodsBean.getItemNo(), goodsBean
                .getItemgroupcdoe(), goodsBean.getPrice(), shuliang, goodsBean
                .getUnit(),
                goodsBean.getBzj(), false, goodsBean.getItemclassCode(), goodsBean
                .getItemclassName(), goodsBean.getPicture(), Integer.parseInt(goodsBean.getProQty
                ()), Double.parseDouble(goodsBean.getReturnrate()), goodsBean.getSpec(), tuangou);
        return productinfo;
    }

    private void addGroup(IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean goodsBean) {
        boolean hasInGroups = false;
        for(GroupInfo group: groups) {
            if(group.getGroupName().equals(goodsBean.getItemGroupName())) {
                hasInGroups = true;
                break;
            }
        }
        if (!hasInGroups) {
            GroupInfo groupInfo = new GroupInfo(goodsBean.getItemgroupcdoe(),goodsBean.getItemGroupName());
            groups.add(groupInfo);
        }
    }



    @Override
    protected void setListener() {
        getViewById(R.id.btn_commit_order).setOnClickListener(this);
        rl_date.setOnClickListener(this);
        cb_all.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 获取数据
        getData();
        exListView = getViewById(R.id.exListView);
        adapter = new ShopcartExpandableListViewAdapter(groups, children, this);
        adapter.setCheckInterface(this);
        exListView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++)
        {
            exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }

        int totalNum = ShoppingCart.getInstance().getTotalNum();
        double totalPrice = ShoppingCart.getInstance().getTotal();
        tv_amount.setText(totalNum+"");
        tv_totalprice.setText(totalPrice+"");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_commit_order:// 提交订单
                getData();
                //将购物车数据转换成数组
                ArrayList<OrderCommitEntity.ZstailBean> productInfos = new ArrayList<>();
                for (Map.Entry<String,List<OrderCommitEntity.ZstailBean>> entry: children.entrySet()){
                    productInfos.addAll(entry.getValue());
                }
                OrderCommitEntity orderCommitEntity = new OrderCommitEntity();
                orderCommitEntity.setCustomerNo(BaseApplication.userInfo.getData().getCustomerNo());
                orderCommitEntity.setOrderDate(tv_date.getText().toString());
                orderCommitEntity.setZstail(productInfos);

                orderCommitEntity.setZffs(BaseApplication.userInfo.getData().getJsfs());
                Intent intent = new Intent(this, ConfirmOrderActivity.class);
                intent.putExtra("data",orderCommitEntity);
                startActivity(intent);

                break;
            case R.id.cb_all:
                doCheckAll();
                break;

            case R.id.rl_date:
                showDialog(0);
                break;
            case R.id.tv_right:// 切换视图
                if(tv_right.getText().equals("编辑")){
                    // 隐藏日期视图
                    rl_date.setVisibility(View.GONE);
                    ll_commit_order.setVisibility(View.GONE);
                    btn_delete.setVisibility(View.VISIBLE);
                    tv_right.setText("完成");
                    ll_check_all.setVisibility(View.VISIBLE);
                    adapter.setIsEditMode(true);
                    adapter.notifyDataSetChanged();
                }else if(tv_right.getText().equals("完成")){
                    rl_date.setVisibility(View.VISIBLE);
                    ll_commit_order.setVisibility(View.VISIBLE);
                    btn_delete.setVisibility(View.GONE);
                    tv_right.setText("编辑");
                    ll_check_all.setVisibility(View.GONE);
                    adapter.setIsEditMode(false);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_delete:
                if (!hasProduct())
                {
                    showToast("请选择要移除的商品");
                    return;
                }
                AlertDialog alert = new AlertDialog.Builder(this).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        return;
                    }
                });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        doDelete();
                    }
                });
                alert.show();
                break;
        }
    }

    private boolean hasProduct() {
        boolean hasProdut = false;
        for(Map.Entry<String,List<OrderCommitEntity.ZstailBean>> entry:children.entrySet()){
            for(OrderCommitEntity.ZstailBean item: entry.getValue()){
                if(item.isIsChoosed()){
                    hasProdut = true;
                    break;
                }
            }
        }
        return hasProdut;
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        GroupInfo group = groups.get(groupPosition);
        List<OrderCommitEntity.ZstailBean> childs = children.get(group.getGroupId());
        for (int i = 0; i < childs.size(); i++)
        {
            childs.get(i).setIsChoosed(isChecked);
        }
        if (isAllCheck())
            cb_all.setChecked(true);
        else
            cb_all.setChecked(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        GroupInfo group = groups.get(groupPosition);
        List<OrderCommitEntity.ZstailBean> childs = children.get(group.getGroupId());
        for (int i = 0; i < childs.size(); i++)
        {
            if (childs.get(i).isIsChoosed() != isChecked)
            {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState)
        {
            group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else
        {
            group.setChoosed(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck())
            cb_all.setChecked(true);
        else
            cb_all.setChecked(false);
        adapter.notifyDataSetChanged();
    }


    private boolean isAllCheck()
    {

        for (GroupInfo group : groups)
        {
            if (!group.isChoosed())
                return false;
        }

        return true;
    }

    /** 全选与反选 */
    private void doCheckAll()
    {
        for (int i = 0; i < groups.size(); i++)
        {
            groups.get(i).setChoosed(cb_all.isChecked());
            GroupInfo group = groups.get(i);
            List<OrderCommitEntity.ZstailBean> childs = children.get(group.getGroupId());
            for (int j = 0; j < childs.size(); j++)
            {
                childs.get(j).setIsChoosed(cb_all.isChecked());
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete()
    {
        List<GroupInfo> toBeDeleteGroups = new ArrayList<GroupInfo>();// 待删除的组元素列表
        for (int i = 0; i < groups.size(); i++)
        {
            GroupInfo group = groups.get(i);
            if (group.isChoosed())
            {
                toBeDeleteGroups.add(group);
            }
            List<OrderCommitEntity.ZstailBean> toBeDeleteProducts = new ArrayList<OrderCommitEntity.ZstailBean>();// 待删除的子元素列表
            List<OrderCommitEntity.ZstailBean> childs = children.get(group.getGroupId());
            for (int j = 0; j < childs.size(); j++)
            {
                if (childs.get(j).isIsChoosed())
                {
                    OrderCommitEntity.ZstailBean productInfo = childs.get(j);
                    TempDataBean bean = new TempDataBean(0, 0, productInfo.getItemNo(), true);
                    ShoppingCart.getInstance().changeItem(bean);

                    toBeDeleteProducts.add(productInfo);
                }
            }
            childs.removeAll(toBeDeleteProducts);

        }

        groups.removeAll(toBeDeleteGroups);
        // 更新首页数据
        EventBus.getDefault().post(new MessageBean("mr.simple"), "notifyDataSetChange");
        adapter.notifyDataSetChanged();
        // 判断购物车是否为空，如果为空则关闭当前页面
        ShoppingCart instance = ShoppingCart.getInstance();
        int totalNum = instance.getTotalNum();
        if(totalNum == 0) finish();
    }


    @Override
    public void update(Observable observable, Object data) {
        int totalNum = ShoppingCart.getInstance().getTotalNum();
        double totalPrice = ShoppingCart.getInstance().getTotal();
        tv_amount.setText(totalNum+"");
        tv_totalprice.setText(totalPrice+"");
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        Calendar calendar=Calendar.getInstance();
        Dialog dateDialog=new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        showToast(year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日");
                        tv_date.setText(year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日");
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        return dateDialog;
    }
}
