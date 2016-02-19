package com.rise.shop.web.validator.base;

import com.rise.shop.domain.ano.FieldMeta;
import com.rise.shop.domain.ano.info.FieldMetaInfo;
import com.rise.shop.domain.art.mongo.Artist;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdi on 15-1-18.
 */
public class BaseValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Artist.class == clazz;//表示只对UserModel类型的目标对象实施验证
    }

    @Override
    public void validate(Object target, Errors errors) {
        //这个表示如果目标对象的username属性为空，则表示错误（简化我们手工判断是否为空）
        bean2FieldMetaInfoValidator(target, errors);
    }

    public void bean2FieldMetaInfoValidator(Object target, Errors errors) {
        List<FieldMetaInfo> list = new ArrayList<FieldMetaInfo>();
        Class<? extends Object> clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field[] superFields = clazz.getSuperclass().getDeclaredFields();
        fields = (Field[]) ArrayUtils.addAll(fields, superFields);
        for (Field field : fields) {
            String fieldName = field.getName();
            FieldMeta meta = field.getAnnotation(FieldMeta.class);
            if (meta != null) {
                if (meta.notnull()) {
                    ValidationUtils.rejectIfEmpty(errors, fieldName, fieldName + "不能为空");
                }
            }
        }
    }
}
