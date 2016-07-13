package com.softgarden.garden.view.shopcar.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softgarden.garden.base.BaseActivity;
import com.softgarden.garden.base.BaseApplication;
import com.softgarden.garden.entity.IndexEntity;
import com.softgarden.garden.entity.TempDataBean;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.shopcar.CommitOrderDialog;
import com.softgarden.garden.view.shopcar.OverTimePromptDialog;
import com.softgarden.garden.view.shopcar.adapter.ShopcartExpandableListViewAdapter;
import com.softgarden.garden.view.shopcar.entity.GroupInfo;
import com.softgarden.garden.view.shopcar.entity.ProductInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopcarActivity extends BaseActivity implements ShopcartExpandableListViewAdapter
        .CheckInterface,ShopcartExpandableListViewAdapter.ModifyCountInterface{

    private CheckBox cb_all;
    private ExpandableListView exListView;
    private TextView tv_totalprice;
    private TextView tv_amount;
    private TextView tv_right;
    private ShopcartExpandableListViewAdapter adapter;
    private RelativeLayout rl_date;
    private Button btn_delete;
    private LinearLayout ll_commit_order;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopcar);

        tv_right = getViewById(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("编辑");

        // 获取数据
        getData();
        cb_all = getViewById(R.id.cb_all);
        tv_totalprice = getViewById(R.id.tv_totalprice);
        tv_amount = getViewById(R.id.tv_amount);
        rl_date = getViewById(R.id.rl_date);
        btn_delete = getViewById(R.id.btn_delete);
        ll_commit_order = getViewById(R.id.ll_commit_order);

        exListView = getViewById(R.id.exListView);
        adapter = new ShopcartExpandableListViewAdapter(groups, children, this);
        adapter.setCheckInterface(this);
        adapter.setModifyCountInterface(this);
        exListView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++)
        {
            exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }
    }

    private ArrayList<GroupInfo>groups = new ArrayList<>();
    private Map<String, List<ProductInfo>> children = new HashMap<String, List<ProductInfo>>();//
    // 子元素数据列表
    private void getData() {
        // 遍历所有数据，只要团购数和数量两个字段不为0就为购物车的数据
        List<IndexEntity.DataBean.ShopBean> shop = BaseApplication.indexEntity.getData()
                .getShop();
        int classSize = shop.size();
        for(int i = 0;i<classSize;i++){// 一级分类
            int groupSize = shop.get(i).getChild().size();
            for(int j = 0;j<groupSize;j++){// 二级分类
                int goodsSize = shop.get(i).getChild().get(j).getGoods()
                        .size();
                ArrayList<ProductInfo> products = new ArrayList<>();
                for(int k = 0;k<goodsSize;k++){// 所有子项
                    IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean goodsBean = shop.get(i)
                            .getChild().get(j).getGoods().get(k);
                    boolean isInTempData = false;
                    for (TempDataBean item: BaseApplication.tempDataBeans){
                        if(goodsBean.getIetmNo().equals(item.getIetmNo())){
                            // tempDataBeans存在，即用户操作过此数据，判断此bean的tuangou和shuliang字段如果有一个不为0，则为购物车数据
                            if(item.getTuangou() != 0 || item.getShuliang() != 0) {
                                addGroup(goodsBean);
                                ProductInfo productinfo = generateProductInfo(goodsBean, item
                                        .getTuangou(),item.getShuliang());
                                products.add(productinfo);
                            }
                            isInTempData = true;
                            break;
                        }
                    }
                    if(!isInTempData){
                        int count = goodsBean.getProQty();
                        if(count!=0) {
                            addGroup(goodsBean);
                            ProductInfo productinfo = generateProductInfo(goodsBean,0,goodsBean.getProQty());
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

    private ProductInfo generateProductInfo(IndexEntity.DataBean.ShopBean.ChildBean.GoodsBean
                                                goodsBean, int tuangou, int shuliang) {
        ProductInfo productinfo = new ProductInfo(goodsBean.getItemclassCode(),
                goodsBean
                .getItemclassName(), goodsBean.getItemgroupcdoe(),
                goodsBean.getItemGroupName(), goodsBean.getIetmNo(),
                goodsBean.getItemName(), goodsBean.getSpec(), goodsBean
                .getUnit(), goodsBean.getBzj(), goodsBean.getPicture(),
                goodsBean.getProQty(), goodsBean.getPrice(), goodsBean
                .getIsSpecial(),
                goodsBean.getReturnrate(), false, tuangou, shuliang);
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
        cb_all.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_commit_order:// 提交订单
                // 需要验证时间
                Calendar instance = Calendar.getInstance();
                instance.set(Calendar.HOUR_OF_DAY,16);
                instance.set(Calendar.MINUTE,0);
                instance.set(Calendar.SECOND,0);
                Date time = instance.getTime();
                if(System.currentTimeMillis()<time.getTime()){// 4点内,弹出对话框提示输入登录密码
                    showCommitDialog();
                }else{// 4点后不允许提交订单
                    showOverTimeDialog();
                }
                break;
            case R.id.cb_all:
                doCheckAll();
                break;
            case R.id.tv_right:// 切换视图
                if(tv_right.getText().equals("编辑")){
                    // 隐藏日期视图
                    rl_date.setVisibility(View.GONE);
                    ll_commit_order.setVisibility(View.GONE);
                    btn_delete.setVisibility(View.VISIBLE);
                    tv_right.setText("完成");
                }else if(tv_right.getText().equals("完成")){
                    rl_date.setVisibility(View.VISIBLE);
                    ll_commit_order.setVisibility(View.VISIBLE);
                    btn_delete.setVisibility(View.GONE);
                    tv_right.setText("编辑");
                }
                break;
            case R.id.btn_delete:
                if (totalCount == 0)
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

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getGroupId());
        for (int i = 0; i < childs.size(); i++)
        {
            childs.get(i).setChoosed(isChecked);
        }
        if (isAllCheck())
            cb_all.setChecked(true);
        else
            cb_all.setChecked(false);
        adapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getGroupId());
        for (int i = 0; i < childs.size(); i++)
        {
            if (childs.get(i).isChoosed() != isChecked)
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
        calculate();
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean
            isChecked) {
        ProductInfo product = (ProductInfo) adapter.getChild(groupPosition, childPosition);
        int currentCount = Integer.parseInt(((EditText)showCountView).getText().toString().trim());
        currentCount++;
//        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
//        adapter.notifyDataSetChanged();
//        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        ProductInfo product = (ProductInfo) adapter.getChild(groupPosition, childPosition);
        int currentCount = Integer.parseInt(((EditText)showCountView).getText().toString().trim());
        if (currentCount <=0)
            return;
        currentCount--;

//        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");

//        adapter.notifyDataSetChanged();
//        calculate();
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
            List<ProductInfo> childs = children.get(group.getGroupId());
            for (int j = 0; j < childs.size(); j++)
            {
                childs.get(j).setChoosed(cb_all.isChecked());
            }
        }
        adapter.notifyDataSetChanged();
        calculate();
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
            List<ProductInfo> toBeDeleteProducts = new ArrayList<ProductInfo>();// 待删除的子元素列表
            List<ProductInfo> childs = children.get(group.getGroupId());
            for (int j = 0; j < childs.size(); j++)
            {
                if (childs.get(j).isChoosed())
                {
                    toBeDeleteProducts.add(childs.get(j));
                }
            }
            childs.removeAll(toBeDeleteProducts);

        }

        groups.removeAll(toBeDeleteGroups);

        adapter.notifyDataSetChanged();
        calculate();
    }

    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate()
    {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < groups.size(); i++)
        {
            GroupInfo group = groups.get(i);
            List<ProductInfo> childs = children.get(group.getGroupId());
            for (int j = 0; j < childs.size(); j++)
            {
                ProductInfo product = childs.get(j);
                if (product.isChoosed())
                {
                    totalCount++;
//                    totalPrice += product.getPrice() * product.getCount();
                }
            }
        }
        tv_totalprice.setText("￥" + totalPrice);
        tv_amount.setText(totalCount + "件");
    }

    private void showOverTimeDialog() {
        OverTimePromptDialog dialog = new OverTimePromptDialog(this, R.style.CustomDialog);
        dialog.show();
        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(this)*0.8);
        attributes.height = ScreenUtils.getScreenWidth(this);
        dialog.getWindow().setAttributes(attributes);
    }

    private void showCommitDialog() {
        CommitOrderDialog dialog = new CommitOrderDialog(this, R.style.CustomDialog);
        dialog.show();
        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(this)*0.9);
        attributes.height =(int) (ScreenUtils.getScreenWidth(this)*0.9);
        dialog.getWindow().setAttributes(attributes);
    }

}
