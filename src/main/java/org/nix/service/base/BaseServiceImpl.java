package org.nix.service.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @author 11723
 */
public abstract class BaseServiceImpl<M extends Object,ID extends Serializable> implements BaseService<M,ID>{
    protected JpaRepository<M,ID> jpaRepository;

    /**
     * 初始化
     * */
    @PostConstruct
    protected abstract void init();

    @Override
    public M save(M o) {
        return jpaRepository.save(o);
    }

    @Override
    public M delete(M o) {
        try {
            jpaRepository.delete(o);
            return o;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public M update(M o) {
        return jpaRepository.saveAndFlush(o);
    }

    @Override
    public M findById(ID id) {
        return jpaRepository.findOne(id);
    }

    @Override
    public List<M> findAll() {
        return jpaRepository.findAll();
    }
}
