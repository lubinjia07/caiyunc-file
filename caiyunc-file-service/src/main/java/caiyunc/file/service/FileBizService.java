package caiyunc.file.service;

import caiyunc.file.db.domain.LitemallStorage;
import caiyunc.file.db.mapper.LitemallStorageMapper;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class FileBizService {


    @Resource
    private LitemallStorageMapper litemallStorageMapper;


    public boolean saveFileUrl(String key, String name, String type, Integer size, String url) {

        LitemallStorage input = new LitemallStorage();
        input.setKey(key);
        input.setName(name);
        input.setType(type);
        input.setSize(size);
        input.setUrl(url);
        input.setAddTime(LocalDateTime.now());
        input.setUpdateTime(LocalDateTime.now());
        input.setDeleted(false);
        return litemallStorageMapper.insert(input) > 0;
    }
}
