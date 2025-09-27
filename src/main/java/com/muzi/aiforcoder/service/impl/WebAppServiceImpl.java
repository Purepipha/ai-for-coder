package com.muzi.aiforcoder.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.muzi.aiforcoder.model.entity.WebApp;
import com.muzi.aiforcoder.mapper.WebAppMapper;
import com.muzi.aiforcoder.service.WebAppService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author <a href="https://github.com/Purepipha">muzi</a>
 */
@Service
public class WebAppServiceImpl extends ServiceImpl<WebAppMapper, WebApp>  implements WebAppService{

}
