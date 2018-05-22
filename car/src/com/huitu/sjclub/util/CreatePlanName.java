package com.huitu.sjclub.util;

/**
 * Created by Administrator on 2017/4/11.
 *
 * @author cys
 */
public class CreatePlanName {
    /**
     * 计划名称组合（根据时间+数据）
     * @return
     */
    public static String createName(int i){
        String suffix="";
        if(i==0){
            suffix="001";
        }else{
            if(i<9){
                suffix="00"+(i+1);
            }else if(i>=9&&i<=99){
                suffix="0"+(i+1);
            }else{
                suffix= String.valueOf(i+1);
            }
        }
        return "_"+suffix;
    }
}
