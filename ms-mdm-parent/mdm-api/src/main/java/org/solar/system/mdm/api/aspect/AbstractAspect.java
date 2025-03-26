package org.solar.system.mdm.api.aspect;

import org.solar.system.mdm.model.mapper.GlobalMapper;
import org.solar.system.mdm.service.base.AbstractCommon;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAspect extends AbstractCommon {

    @Autowired
    protected GlobalMapper mapper;

}
