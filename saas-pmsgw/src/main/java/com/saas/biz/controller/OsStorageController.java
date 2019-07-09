package com.saas.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.saas.biz.pojo.NodejsStorage;
import com.saas.biz.service.NodejsStorageService;
import com.saas.biz.service.StorageService;
import com.saas.common.BaseResponse;
import com.saas.common.CharUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/storage")
public class OsStorageController {

    @Autowired
    private StorageService storageService;
    @Autowired
    private NodejsStorageService nodejsStorageService;

   /* @Autowired
    private ObjectStorageConfig osConfig;*/
    @Autowired
    private Environment env;

    private String generateUrl(String key){
    	String url=env.getProperty("info.os.url");
        return  url + "/storage/fetch/" + key;
    }

    private String generateKey(String originalFilename){
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);

        String key = null;
        NodejsStorage storageInfo = null;

        do{
            key = CharUtil.getRandomString(20) + suffix;
            storageInfo = nodejsStorageService.selectOneByKey(key);
        }
        while(storageInfo != null);

        return key;
    }

    @PostMapping("/create")
    public BaseResponse<NodejsStorage> create(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
       
        String key = generateKey(originalFilename);
        storageService.store(inputStream, key);

        String url = generateUrl(key);
        NodejsStorage storageInfo = new NodejsStorage();
        storageInfo.setStorageName(originalFilename);
        storageInfo.setStorageSize((int)file.getSize());
        storageInfo.setStorageType(file.getContentType());
        storageInfo.setAdd_time(new Date());
        storageInfo.setModified(new Date());
        storageInfo.setStorageKey(key);
        storageInfo.setUrl(url);
        nodejsStorageService.insert(storageInfo);
        return BaseResponse.ToJsonResult(storageInfo);
    }

   
    @RequestMapping(value = "/createImg", method = RequestMethod.POST,produces="application/json")
    @ResponseBody
    public Map<String, Object> createImg(@RequestParam("upfile") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
       
        String key = generateKey(originalFilename);
        storageService.store(inputStream, key);

        String url = generateUrl(key);
        
        NodejsStorage storageInfo = new NodejsStorage();
        storageInfo.setStorageName(originalFilename);
        storageInfo.setStorageSize((int)file.getSize());
        storageInfo.setStorageType(file.getContentType());
        storageInfo.setAdd_time(new Date());
        storageInfo.setModified(new Date());
        storageInfo.setStorageKey(key);
        storageInfo.setUrl(url);
        nodejsStorageService.insert(storageInfo);
        
        String name =file.getName();
        String filesystemName =file.getOriginalFilename();
        String contentType=file.getContentType();
        
        Map<String, Object> ulfd=new HashMap<String,Object>();
        ulfd.put("parameterName", name);
        ulfd.put("uploadPath", originalFilename);//图片的物理路径
        ulfd.put("url", url);
        ulfd.put("originalFileName", originalFilename);
        ulfd.put("contentType", contentType);
        ulfd.put("state", "SUCCESS");
        
        return ulfd;
    }


    @RequestMapping(value = "/createVideo", method = RequestMethod.POST,produces="application/json")
    @ResponseBody
    public Map<String, Object> createVideo(@RequestParam("upfile") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
       
        String key = generateKey(originalFilename);
        storageService.store(inputStream, key);

        String url = generateUrl(key);
        
        NodejsStorage storageInfo = new NodejsStorage();
        storageInfo.setStorageName(originalFilename);
        storageInfo.setStorageSize((int)file.getSize());
        storageInfo.setStorageType(file.getContentType());
        storageInfo.setAdd_time(new Date());
        storageInfo.setModified(new Date());
        storageInfo.setStorageKey(key);
        storageInfo.setUrl(url);
        nodejsStorageService.insert(storageInfo);
        
        String name =file.getName();
        String filesystemName =file.getOriginalFilename();
        String contentType=file.getContentType();
        
        Map<String, Object> ulfd=new HashMap<String,Object>();
        ulfd.put("parameterName", name);
        ulfd.put("uploadPath", originalFilename);//图片的物理路径
        ulfd.put("url", url);
        ulfd.put("originalFileName", originalFilename);
        ulfd.put("contentType", contentType);
        ulfd.put("state", "SUCCESS");
        
        return ulfd;
    }
    @PostMapping("/read")
    public BaseResponse<NodejsStorage> read(Integer id) {
        
    	NodejsStorage storageInfo = nodejsStorageService.selectOneById(id);
       
    	 return BaseResponse.ToJsonResult(storageInfo);
    }

    @PostMapping("/update")
    public BaseResponse<NodejsStorage> update(@RequestBody NodejsStorage litemallStorage) {

    	nodejsStorageService.update(litemallStorage);
    	 return BaseResponse.ToJsonResult(litemallStorage);
    }

    @PostMapping("/delete")
    public BaseResponse<Integer> delete(@RequestBody NodejsStorage litemallStorage) {
    	nodejsStorageService.deleteById(litemallStorage.getId());
        storageService.delete(litemallStorage.getStorageKey());
        return BaseResponse.ToJsonResult(1);
    }

    @GetMapping("/fetch/{key:.+}")
    public ResponseEntity<Resource> fetch(@PathVariable String key) {
    	NodejsStorage litemallStorage = nodejsStorageService.selectOneByKey(key);
        if(key == null){
            ResponseEntity.notFound();
        }
        String type = litemallStorage.getStorageType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = storageService.loadAsResource(key);
        if(file == null) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.ok().contentType(mediaType).body(file);
    }

    @GetMapping("/download/{key:.+}")
    public ResponseEntity<Resource> download(@PathVariable String key) {
    	NodejsStorage litemallStorage = nodejsStorageService.selectOneByKey(key);
        if(key == null){
            ResponseEntity.notFound();
        }
        String type = litemallStorage.getStorageType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = storageService.loadAsResource(key);
        if(file == null) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.ok().contentType(mediaType).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
