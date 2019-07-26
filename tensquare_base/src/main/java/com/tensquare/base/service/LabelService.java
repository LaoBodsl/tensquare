package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部标签
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 增加标签
     * @param label
     */
    public void add(Label label){
        label.setId(idWorker.nextId()+"");//设置ID
        labelDao.save(label);
    }

    /**
     * 修改标签
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * 删除标签
     * @param id
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    /**
     * 查询
     * @param label
     * @return
     */
    private Specification<Label> createSpecification(Label label){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?>
                    criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList=new ArrayList<>();
                if(label.getLabelname()!=null &&
                        !"".equals(label.getLabelname())){
                    predicateList.add(cb.like(
                            root.get("labelname").as(String.class), "%"+
                                    label.getLabelname()+"%" ) );
                }
                if(label.getState()!=null &&
                        !"".equals(label.getState())){
                    predicateList.add(cb.equal(
                            root.get("state").as(String.class), label.getState() ) );
                }
                if(label.getRecommend()!=null &&
                        !"".equals(label.getRecommend())){
                    predicateList.add(cb.equal(
                            root.get("recommend").as(String.class),
                            label.getRecommend() ) );
                }
                return cb.and( predicateList.toArray( new
                        Predicate[predicateList.size()]) );
            }
        };
    }

    public List<Label> findSearch(Label label){
        Specification specification = createSpecification(label);
        return labelDao.findAll(specification);
    }

    public Page<Label> pageQuery(Label label, int page, int size){
        Specification specification = createSpecification(label);
        Pageable pageable = PageRequest.of(page-1,size);
        PageRequest pageRequest = PageRequest.of(page,size);
        return labelDao.findAll(specification,pageable);
    }
}
