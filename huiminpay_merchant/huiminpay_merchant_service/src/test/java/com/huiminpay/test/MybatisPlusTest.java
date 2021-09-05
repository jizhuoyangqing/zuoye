package com.huiminpay.test;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huiminpay.api.MerchantService;
import com.huiminpay.bean.Merchant;
import com.huiminpay.mapper.MerchantMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusTest {
    @Autowired
    MerchantMapper merchantMapper;

    //mybatis-plus默认主键生成策略：
    //如果不做任何主键策略配置，默认使用的是雪花算法生成主键ID(主键类型为Long或String，对应MySQL数据库
    //                                                    就是BIGINT和VARCHAR)
    //需要Mapper对象继承BaseMapper<Merchant>
    //插入的数据如果不设置主键，插入后再打印此对象，就会自动回显雪花算法默认生成的主键
    //插入的数据如果设置了主键，插入后再打印此对象，就会自动回显自己设置的主键

    //mybatis中进行添加数据时， XML文件的sql中 useGeneratedKeys="true"开启主键回写，才可以进行回显数据库中的主键值
    //mybatis-plus中会自动进行回显数据库中的主键值

    @Test
    public void insertMerchant() {
        Merchant merchant = new Merchant();
        //merchant.setId(3L);
        merchant.setContactsAddress("西湖");
        int insert = merchantMapper.insert(merchant);

        if (insert >= 1) {
            System.out.println("添加成功"+insert+"条");
        }
        System.out.println(merchant);
    }

    //在service的yml文件中可以通过此进行设置java实体类字段驼峰和数据库下划线的映射
        //mybatis-plus:
        //  configuration:
        //    map-underscore-to-camel-case: false
    // 为false后，由于名字不对应就不能自动完成映射,就会报错
    @Test
    public void queryMerchantById() {
        Merchant merchant = merchantMapper.selectById(3);
        System.out.println(merchant);

    }

    //根据名字去查，该怎么查
    @Test
    public void queryByName(){
        //用Wrapper的子类
        QueryWrapper<Merchant> wrapper = new QueryWrapper();

        //设置过滤规则(查询规则)  参数1.要查询的数据库中列的名称 参数2.对应该列中某个值
        //不设置此过滤规则，查询出来的是该表全部的数据
        wrapper.eq("merchant_name","凤凰山");

        //根据名字去查可能是多个,传进去Wrapper<Merchant>的一个对象(wrapper对象用于设置过滤规则(查询规则))
        List<Merchant> merchants= merchantMapper.selectList(wrapper);
        System.out.println(merchants);
    }

    //mybatis-plus分页查询
    @Test
    public void pageQuery(){
        //参数一、当前页 参数二、每页查询的条数 （跟mysql的limit的含义有所不同）
        //limit 0,3 原先sql语句代表开始的下标为： (第1页-1)*每页查询的条数=索引
        //                      每页查询的条数:  3
        Page<Merchant> merchantPage = new Page<>(3,4);

        //分页 参数一、Ipage<Merchant>指定分页信息 参数二、Wrapper<Merchant>指定搜索的条件，不指定为空即可
        //如果指定了条件查询(仿照上面的条件查询)，那么分页是对条件筛选后的数据进行分页的
        IPage<Merchant> merchantIPage = merchantMapper.selectPage(merchantPage, null);
        //取出分页记录
        List<Merchant> records = merchantIPage.getRecords();
        for (Merchant merchant : records) {
            System.out.println(merchant);
        }
    }

}
