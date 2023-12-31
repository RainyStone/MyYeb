package com.example.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.NationMapper;
import com.example.server.pojo.Nation;
import com.example.server.service.INationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}
