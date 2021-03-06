package com.rise.shop.view.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.rise.shop.common.ano.info.ViewMetaInfo;
import com.rise.shop.common.utils.CopyPropertyUtils;
import com.rise.shop.persistence.page.PaginatedArrayList;
import com.rise.shop.persistence.page.PaginatedList;
import com.rise.shop.persistence.query.Query;
import com.rise.shop.persistence.service.EntityService;
import com.rise.shop.view.enumtype.viewtype.AuthCodeTypeEnum;
import com.rise.shop.view.utils.FieldDetailTools;
import com.rise.shop.view.validator.BaseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangdi on 14-12-21.
 */
public abstract class BaseController<D, T, Q extends Query> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected EntityService<T> baseService;
    protected D domainView;
    protected T domainPersistence;
    protected String namespace;
    protected String chineseName;
    protected Validator validator;

    final static int PAGE_SIZE = 10;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd"), true));
    }

    @ModelAttribute("domain")
    public D getDomain() {
        return domainView;
    }

    @RequestMapping(value = {"/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpSession session) {
        session.setAttribute("domainname", namespace);
        session.setAttribute("zhname", chineseName);
        return "redirect:/" + namespace + "/list";
    }

    @RequestMapping(value = {"/addsave"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView addsave(D obj, Errors errors) {
        ModelAndView mv = new ModelAndView("redirect:/" + namespace + "/index");
        try {
            if (validator != null) {
                validator.validate(obj, errors);  //1 调用UserModelValidator的validate方法进行验证
                if (errors.hasErrors()) { //2如果有错误再回到表单展示页面
                    logger.warn(errors.getAllErrors().toString());
                    mv.addObject("msg", toErrorMsg(errors));
                    mv.setViewName("/validatorerror");
                    return mv;
                }
            }
            baseService.insert(convertDtoT(obj));
            return mv;
        } catch (Exception e) {
            logger.error("addsave", e);
            mv.setViewName("error");
            return mv;
        }
    }

    @RequestMapping(value = {"/editsave"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView editsave(D obj, Errors errors) {
        ModelAndView mv = new ModelAndView("redirect:/" + namespace + "/index");
        try {
            if (validator != null) {
                validator.validate(obj, errors);  //1 调用UserModelValidator的validate方法进行验证
                if (errors.hasErrors()) { //2如果有错误再回到表单展示页面
                    logger.warn(errors.getAllErrors().toString());
                    mv.addObject("msg", toErrorMsg(errors));
                    mv.setViewName("/validatorerror");
                    return mv;
                }
            }
            baseService.update(convertDtoT(obj));
            return mv;
        } catch (Exception e) {
            logger.error("editsave error", e);
            mv.setViewName("error");
            return mv;
        }
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView("pages/add");
        mv.addObject("domain", FieldDetailTools.bean2FieldMetaInfoList(domainView, false));
        mv.addObject("isAdd", true);
        mv.addObject("saveUrl", "/" + namespace + "/addsave");
        return mv;
    }

    @RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ModelAndView editById(@PathVariable("id") long id, HttpSession session) {
        ModelAndView mv = new ModelAndView("pages/edit");
        Integer sessionAuthCode = (Integer) session.getAttribute("sessionAuthCode");
        if (AuthCodeTypeEnum.AUTH_EDIT.getType() != sessionAuthCode && AuthCodeTypeEnum.AUTH_ALL.getType() != sessionAuthCode) {
            logger.warn("edit auth fail");
            mv.setViewName("/error");
            return mv;
        }
        try {
            T t = baseService.get(id);
            if (t != null) {
                mv.addObject("domain", FieldDetailTools.bean2FieldMetaInfoList(convertTtoD(t), true));
                mv.addObject("isAdd", false);
                mv.addObject("saveUrl", "/" + namespace + "/editsave");
            }
            return mv;
        } catch (Exception e) {
            logger.error("editById error", e);
            mv.setViewName("/error");
            return mv;
        }
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView list(Q q) {
        ModelAndView mv = new ModelAndView("pages/list");
        try {
            if (q.getPageNo() == null) {
                q.setPageNo(1);
            }
            q.setPageSize(PAGE_SIZE);
            mv.addObject("list", convertTListtoDList(baseService.findByPage(q)));
            mv.addObject("domains", FieldDetailTools.bean2FieldMetaInfoList(domainView, false));
            mv.addObject("queryDomains", FieldDetailTools.bean2FieldMetaInfoList(q, true));
        } catch (Exception e) {
            logger.error("list error", e);
        }
        return mv;
    }

    @RequestMapping(value = "/domain", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<ViewMetaInfo> domain() {
        return FieldDetailTools.bean2FieldMetaInfoList(domainView, false);
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public int delete(@PathVariable("id") long id, HttpSession session) {
        try {
            Integer sessionAuthCode = (Integer) session.getAttribute("sessionAuthCode");
            if (AuthCodeTypeEnum.AUTH_ALL.getType() == sessionAuthCode) {
                T t = baseService.get(id);
                Preconditions.checkArgument(t != null, "cant find by id " + id);
                return baseService.delete(t);
            } else {
                logger.warn("delete auth fail");
                return -1;
            }
        } catch (Exception e) {
            logger.error("delete error", e);
            return -1;
        }
    }

    @RequestMapping(value = "/detail/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ModelAndView detailById(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("pages/detail");
        try {
            T t = baseService.get(id);
            if (t != null) {
                mv.addObject("domain", FieldDetailTools.bean2FieldMetaInfoList(convertTtoD(t), true));
            }
            return mv;
        } catch (Exception e) {
            logger.error("delete error", e);
            mv.setViewName("/error");
            return mv;
        }
    }

    @RequestMapping(value = "/getAjax/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public D getAjax(@PathVariable("id") long id) {
        try {
            return convertTtoD(baseService.get(id));
        } catch (Exception e) {
            logger.error("getAjax error", e);
            return null;
        }
    }

    /**
     * ajax查询多条by Ids
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/queryByIds", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<D> queryByIdsAjax(String ids) {
        try {
            logger.info("queryByIdsAjax ids=" + ids);
            List<D> objList = new ArrayList<D>();
            JSONArray jsonArray = JSONArray.parseArray(ids);
            for (int i = 0; i < jsonArray.size(); i++) {
                Long id = jsonArray.getObject(i, Long.class);
                T t = baseService.get(id);
                objList.add(convertTtoD(t));
            }
            return objList;
        } catch (Exception e) {
            logger.error("queryByIds error", e);
            return null;
        }
    }

    /**
     * ajax模糊查询
     *
     * @param jsonParams
     * @return
     */
    @RequestMapping(value = "/likeQueryByName", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<D> likeQueryByNames(String jsonParams) {
        try {
            PaginatedList<T> list = new PaginatedArrayList();
            list.setIndex(1);
            list.setPageSize(PAGE_SIZE);
            Map<String, String> map = new HashMap<String, String>();
            map = JSONObject.parseObject(jsonParams, map.getClass());
            return convertTListtoDList(baseService.findByPageLike(list, map));
        } catch (Exception e) {
            logger.error("queryByIds error", e);
            return null;
        }
    }

    @PostConstruct
    protected abstract void setBaseService();

    @PostConstruct
    protected abstract void setBaseEntity();

    @PostConstruct
    protected abstract void setNamespace();

    @PostConstruct
    protected abstract void setChineseName();

    @PostConstruct
    protected void setValidator() {
        this.validator = new BaseValidator();
    }

    /**
     * tools
     */

    private String toErrorMsg(Errors errors) {
        if (errors != null) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : errors.getAllErrors()) {
                sb.append(error.getCode() + ",");
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    private T convertDtoT(D d) {
        if (d == null) {
            return null;
        }
        return (T) CopyPropertyUtils.copyPropertiesAndInstance(d, domainPersistence.getClass());
    }

    private D convertTtoD(T t) {
        return (D) CopyPropertyUtils.copyPropertiesAndInstance(t, domainView.getClass());
    }

    private PaginatedList<D> convertTListtoDList(PaginatedList<T> ts) {
        PaginatedList<D> list = new PaginatedArrayList<D>();
        for (T t : ts) {
            list.add((D) CopyPropertyUtils.copyPropertiesAndInstance(t, domainView.getClass()));
        }
        list.setTotalItem(ts.getTotalItem());
        list.setPageSize(ts.getPageSize());
        list.setIndex(ts.getIndex());
        return list;
    }

}
