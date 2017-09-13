package com.wanfin.fpd.modules.gen.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.gen.dao.GenTableColumnDao;
import com.wanfin.fpd.modules.gen.entity.GenTableColumn;

@Service
@Transactional(readOnly = true)
public class GenTableColumnService extends CrudService<GenTableColumnDao, GenTableColumn>  {
}
