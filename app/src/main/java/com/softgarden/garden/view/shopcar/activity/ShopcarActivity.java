package com.softgarden.garden.view.shopcar.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.utils.ScreenUtils;
import com.softgarden.garden.view.shopcar.entity.GroupInfo;
import com.softgarden.garden.view.shopcar.OverTimePromptDialog;
import com.softgarden.garden.view.shopcar.entity.ProductInfo;
import com.softgarden.garden.view.shopcar.adapter.ShopcartExpandableListViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopcarActivity extends BaseActivity implements ShopcartExpandableListViewAdapter
        .CheckInterface,ShopcartExpandableListViewAdapter.ModifyCountInterface{

    private List<GroupInfo> groups = new ArrayList<GroupInfo>();// 组元素数据列表
    private Map<String, List<ProductInfo>> children = new HashMap<String, List<ProductInfo>>();// 子元素数据列表

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

        // 模拟数据,事实上的数据应该是从本地数据库中获取的
        virtualData();
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
                if(System.currentTimeMillis()<time.getTime()){// 4点内
                    startActivity(new Intent(this,ConfirmOrderActivity.class));
                }else{// 4点后不允许提交订单
                    showDialog();
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

    /**
     * 模拟数据<br>
     * 遵循适配器的数据列表填充原则，组元素被放在一个List中，对应的组元素下辖的子元素被放在Map中，<br>
     * 其键是组元素的Id(通常是一个唯一指定组元素身份的值)
     */
    private void virtualData()
    {

        for (int i = 0; i < 6; i++)
        {

            groups.add(new GroupInfo(i + "", "第" + (i + 1) + "种面包"));

            List<ProductInfo> products = new ArrayList<ProductInfo>();
            for (int j = 0; j <= i; j++)
            {

                products.add(new ProductInfo(j + "", groups.get(i).getName() + "的第" + (j + 1) +
                        "个商品",30,j+1));
            }
            children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
        }
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getId());
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
        List<ProductInfo> childs = children.get(group.getId());
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
        int currentCount = product.getCount();
        currentCount++;
        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");

        adapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        ProductInfo product = (ProductInfo) adapter.getChild(groupPosition, childPosition);
        int currentCount = product.getCount();
        if (currentCount == 1)
            return;
        currentCount--;

        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");

        adapter.notifyDataSetChanged();
        calculate();
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
            List<ProductInfo> childs = children.get(group.getId());
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
            List<ProductInfo> childs = children.get(group.getId());
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
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++)
            {
                ProductInfo product = childs.get(j);
                if (product.isChoosed())
                {
                    totalCount++;
                    totalPrice += product.getPrice() * product.getCount();
                }
            }
        }
        tv_totalprice.setText("￥" + totalPrice);
        tv_amount.setText(totalCount + "件");
    }

    private void showDialog() {
        OverTimePromptDialog dialog = new OverTimePromptDialog(this, R.style.CustomDialog);
        dialog.show();
        // 设置宽，高可在xml布局中写上,但宽度默认是match_parent，所以需要在代码中设置
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(this)*0.8);
        attributes.height = ScreenUtils.getScreenWidth(this);
        dialog.getWindow().setAttributes(attributes);
    }

}
