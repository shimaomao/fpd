/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.files.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


//import org.json.JSONArray;
import org.json.JSONException;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.FileUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.files.dao.TContractFilesDao;
import com.wanfin.fpd.modules.files.entity.FileBytesVo;
import com.wanfin.fpd.modules.files.entity.TContractFiles;
import com.wanfin.fpd.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 附件管理Service
 * 
 * @author zzm
 * @version 2016-03-21
 */
@Service
@Transactional(readOnly = true)
public class TContractFilesService extends
        CrudService<TContractFilesDao, TContractFiles> {
    @Autowired
    private TContractFilesDao tContractFilesDao;

    public TContractFiles get(String id) {
        return super.get(id);
    }

    public List<TContractFiles> findList(TContractFiles tContractFiles) {
        return super.findList(tContractFiles);
    }

    public Page<TContractFiles> findPage(Page<TContractFiles> page,
            TContractFiles tContractFiles) {
        return super.findPage(page, tContractFiles);
    }

    @Transactional(readOnly = false)
    public void save(TContractFiles tContractFiles) {
        super.save(tContractFiles);
    }

    @Transactional(readOnly = false)
    public void delete(TContractFiles tContractFiles) {
        super.delete(tContractFiles);

    }

    /**
     * 批量删除 只是把附件信息置为逻辑删除，没有删掉真实的附件
     * 
     * @param ids
     */
    @Transactional(readOnly = false)
    public void delete(String[] ids) {
        TContractFiles tContractFiles = new TContractFiles();
        for (String id : ids) {
            tContractFiles.setId(id);
            delete(tContractFiles);
        }
    }

    /**
     * 单文件上传
     * 
     * @param file
     *            要上传的文件
     * @param title
     *            文件标题
     * @param taskId
     *            关联数据表的主键
     * @param type
     *            文件在业务上的类型
     * @param request
     * @throws IOException
     */
    @Transactional(readOnly = false)
    public void uploadFile(MultipartFile file, String title, String taskId,
            String type, HttpServletRequest request) throws IOException {
        uploadFiles(new MultipartFile[] { file }, new String[] { title },
                taskId, type, request);
    }

    /**
     * 多文件上传
     * 
     * @param files
     *            要上传的文件
     * @param title
     *            文件标题
     * @param taskId
     *            关联数据表的主键
     * @param type
     *            文件在业务上的类型
     * @param request
     * @throws IOException
     */
    @Transactional(readOnly = false)
    public void uploadFiles(MultipartFile[] files, String[] title,String taskId, 
    		String type, HttpServletRequest request)throws IOException {
        if (files != null && files.length > 0) {
            Principal principal = UserUtils.getPrincipal();
            ///userfiles/hdbkhjl/credit_apply_sign/2017-05-23
            String root = Global.USERFILES_BASE_URL + principal.getLoginName()
                    + "/" + (StringUtils.isBlank(type) ? "defalut" : type)
                    + "/" + DateUtils.getDate();
            //E:\work\DEV_Tomcat\apache-tomcat-7.0.76\webapps\fpd\//userfiles/hdbkhjl/credit_apply_sign/2017-05-23
            String realPath = Global.getUserfilesBaseDir() + root;

            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                if (file.isEmpty()){
                    continue;
                }
                //合同.doc
                String fileName = file.getOriginalFilename();
                //20170523095611073_568.doc
                fileName = DateUtils.getDate("yyyyMMddHHmmssSSS")
                        + "_"
                        + (int) (Math.random() * 1000)
                        + fileName.substring(fileName.lastIndexOf("."),
                                fileName.length());
                // 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                FileUtils.copyInputStreamToFile(file.getInputStream(),
                        new File(FileUtils.path(realPath), fileName));
                //数据库中保存的  地址
                TContractFiles contractFile = new TContractFiles();
                // 文件保存的相对路径  		/userfiles/hdbkhjl/credit_apply_sign/2017-05-23/20170523100450371_233.doc
                contractFile.setFilePath(root + "/" + fileName);
                // 文件的原名字			合同.doc
                contractFile.setSourceName(file.getOriginalFilename());
                contractFile.setTaskId(taskId);
                // 如果附件标题为空则取文件名(原名)作为标题			合
                contractFile
                        .setTitle((title == null || title[i] == null || title[i]
                                .equals("")) ? file.getOriginalFilename()
                                .substring(
                                        0,
                                        file.getOriginalFilename().lastIndexOf(
                                                ".") - 1) : title[i]);
                // 重命名之后的文件名
                contractFile.setFileName(fileName);
                contractFile.setType(type);
                super.save(contractFile);

            }

        }

    }

    /**
     * 更改附件的关联id，主要用于业务表新增时用临时id进行附件添加，此方法是把临时id替换为业务真实的主键
     * 
     * @param oldTaskId
     *            更新前id
     * @param newTaskId
     *            更新后id
     */
    @Transactional(readOnly = false)
    public void updateFileTaskId(String oldTaskId, String newTaskId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("taskId", newTaskId);
        map.put("oldTaskId", oldTaskId);
        tContractFilesDao.updateFileTaskId(map);
    }

    /**
     * taskId查记录
     * 
     * @param taskId
     * @return
     */
    public List<TContractFiles> getByTaskId(String taskId) {
        return tContractFilesDao.getByTaskId(taskId);
    }

    /**
     * 上传文件到文件服务器接口
     * 
     * @param file
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, Object> dealUploadFile(MultipartFile file, String title,
            String taskId, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String url = Cons.Ips.IP_WFW_UPLOAND_PATH;
            RestTemplate restTemplate = new RestTemplate();
            FileBytesVo fileBytesVo = new FileBytesVo();
            fileBytesVo.setBytes(file.getBytes());
            // 文件后缀名
            String suffix = file.getOriginalFilename().substring(
                    file.getOriginalFilename().lastIndexOf(".") + 1);
            System.out.println("suffix================================="
                    + suffix);
            fileBytesVo.setExtFile(suffix);
            // fileBytesVo.setAuthUserId("34bb841428714174b2b19106bedf726c");
            HttpEntity<FileBytesVo> entity = new HttpEntity<FileBytesVo>(
                    fileBytesVo, headers);
            String result = restTemplate.postForObject(url, entity,
                    String.class);
            System.out.println("result=============================" + result);
            JSONObject resultJson = JSON.parseObject(result);
            if (resultJson.get("istrue") != null
                    && (boolean) resultJson.get("istrue")) {// 请求成功
                JSONObject resultJsonMap = JSON.parseObject(resultJson
                        .getString("entity"));
                if (resultJson != null) {
                    if (resultJsonMap != null) {
                        map.put("filePath", resultJsonMap.getString("filePath")
                                .toString());
                        map.put("fileId", resultJsonMap.getString("fileId")
                                .toString());
                        map.put("isTrue", true);
                        map.put("msg", "上传成功");
                        TContractFiles contractFile = new TContractFiles();
                        // 文件保存的相对路径
                        contractFile
                                .setFilePath(map.get("filePath").toString());
                        String fileName = map
                                .get("filePath")
                                .toString()
                                .substring(
                                        map.get("filePath").toString()
                                                .lastIndexOf("\\") + 1);
                        // 文件的原名字
                        contractFile.setSourceName(file.getOriginalFilename());
                        contractFile.setTaskId(taskId);
                        // 如果附件标题为空则取文件名(原名)作为标题
                        contractFile
                                .setTitle((title == null || title == null || title
                                        .equals("")) ? file
                                        .getOriginalFilename().substring(
                                                0,
                                                file.getOriginalFilename()
                                                        .lastIndexOf(".") - 1)
                                        : title);
                        // 重命名之后的文件名
                        contractFile.setFileName(fileName);
                        contractFile.setType(type);
                        super.save(contractFile);
                    }
                }
            }
            else{
                map.put("msg", resultJson.get("msg").toString());
                map.put("isTrue", false);
            } 
            }catch (IOException e){
               e.printStackTrace();
            }
        return map;
    }

    /**
     * 根据文件类型及业务ID,后缀名获取文件列表
     * 
     * @param file
     * @return { "type":"1_1", "taskId":
     *         "'c4cf8f88e624409b879c2e837fd6868a','d7361e596f6a473ea8cd5f5fd55b5506'"
     *         , "extName":"jpg,npg" }
     */
        public Map<String, Object> findFileByTaskId(Map<String, String> paramMap)
            throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = Cons.Ips.IP_WFW_SELECT_PATH;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(
                paramMap, headers);
        String result = restTemplate.postForObject(url, entity, String.class);
        System.out.println("result=============================" + result);
        JSONObject resultJson = JSON.parseObject(result);
        if ((boolean) resultJson.get("istrue")) {// 请求成功
            List<TContractFiles> list = JSONObject.parseArray(
            resultJson.get("entity").toString(), TContractFiles.class);
        
            map.put("list", list);
            }
        else {// 请求失败
            map.put("msg", resultJson.get("msg").toString());
        }
        return map;
    }

    /**
     * 文件转为二进制
     * 
     * @param filePath
     * @return
     */
    public byte[] filebyte(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public List<TContractFiles> findListByTaskIds(String taskIds) {
        return dao.findListByTaskIds(taskIds);
    }

    public TContractFiles checkFile(String financialProductId, String filePath) {
        return dao.checkFile(financialProductId, filePath);
    }
    
    /**
	 * taskId查记录
	 * @param taskId
	 * @return
	 */
	public List<TContractFiles> getByTaskId(String taskId, String type){
		return tContractFilesDao.getByTaskId(taskId, type);
	}
    
}