package com.softgarden.garden.entity;


import com.softgarden.garden.base.BaseDao;
import com.softgarden.garden.dao.ProductDao;
import com.softgarden.garden.dao.annotation.ColmanName;
import com.softgarden.garden.dao.annotation.TableName;
import com.softgarden.garden.dao.annotation.TablePrimaryKey;

@TableName("product")
public class ProductItem extends BaseDao {


    @ColmanName("_id")
    @TablePrimaryKey(isautocurment = true)
    public int _id;
    //一级分类码
    @ColmanName("itemclassCode")
    public String itemclassCode;
    //一级分类名字
    @ColmanName("itemclassName")
    public String itemclassName;
    //二级分类码
    @ColmanName("Itemgroupcdoe")
    public String Itemgroupcdoe;
    //二级分类名字
    @ColmanName("ItemGroupName")
    public String ItemGroupName;
    //产品编号
    @ColmanName("IetmNo")
    public String IetmNo;
    //产品名称
    @ColmanName("ItemName")
    public String ItemName;
    // 重量
    @ColmanName("spec")
    public String spec;
    // 单位
    @ColmanName("Unit")
    public String Unit;
    // 标准价
    @ColmanName("bzj")
    public String bzj;
    // 图片url
    @ColmanName("picture")
    public String picture;
    // 预估数
    @ColmanName("proQty")
    public String proQty;
    // 特价
    @ColmanName("Price")
    public String Price;

    // 是否特价
    @ColmanName("IsSpecial")
    public String IsSpecial;

    // 退货率
    @ColmanName("returnrate")
    public String returnrate;

    // 团购
    @ColmanName("tuangou")
    public String tuangou;

    // 数量
    @ColmanName("shuliang")
    public String shuliang;

    public ProductItem() {
    }

    public ProductItem( String itemclassCode, String itemclassName, String itemgroupcdoe,
                       String itemGroupName, String ietmNo, String itemName, String spec, String
                               unit, String bzj, String picture, String proQty, String price,
                       String isSpecial, String returnrate, String tuangou, String shuliang) {
        this.itemclassCode = itemclassCode;
        this.itemclassName = itemclassName;
        Itemgroupcdoe = itemgroupcdoe;
        ItemGroupName = itemGroupName;
        IetmNo = ietmNo;
        ItemName = itemName;
        this.spec = spec;
        Unit = unit;
        this.bzj = bzj;
        this.picture = picture;
        this.proQty = proQty;
        Price = price;
        IsSpecial = isSpecial;
        this.returnrate = returnrate;
        this.tuangou = tuangou;
        this.shuliang = shuliang;
    }

    /**
     * 从数据库删除
     */
    public void del() {
        ProductDao dao = ProductDao.getDao();
        dao.delete(this);
    }

}
