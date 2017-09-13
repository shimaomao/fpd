/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.merchant.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.Base64Utils;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.dao.MerchantDao;

/**
 * 商户数据信息Service
 * @author cjp
 * @version 2017-07-03
 */
@Service
@Transactional(readOnly = true)
public class MerchantService extends CrudService<MerchantDao, Merchant> {

	public Merchant get(String id) {
		return super.get(id);
	}
	
	public List<Merchant> findList(Merchant merchant) {
		return super.findList(merchant);
	}
	
	public Page<Merchant> findPage(Page<Merchant> page, Merchant merchant) {
		return super.findPage(page, merchant);
	}
	
	@Transactional(readOnly = false)
	public void save(Merchant merchant) {
		if(merchant == null){
			return;
		}
		//将base64图片保存到项目中
		try{
			String root = Global.USERFILES_BASE_URL 
					+ "certificatemap/" + DateUtils.getDate() + "/";
			String realPath = Global.getUserfilesBaseDir() + root;//实际保存路径
			//身份证图片
			if(StringUtils.isNotBlank(merchant.getIdPhoto())){
				String mapName = DateUtils.getDate("yyyyMMddHHmmss") + IdGen.shortUUId() + "id" + Global.getApiConfig("payeco.maptype");
				Base64Utils.decodeToFile(realPath + mapName, merchant.getIdPhoto());
				merchant.setIdPhotoPath(root + mapName);
			}
			//营业执照图片
			if(StringUtils.isNotBlank(merchant.getBusinessLicense())){
				String mapName = DateUtils.getDate("yyyyMMddHHmmss") + IdGen.shortUUId() + "bl" + Global.getApiConfig("payeco.maptype");
				Base64Utils.decodeToFile(realPath + mapName, merchant.getBusinessLicense());
				merchant.setBusinessLicensePath(root + mapName);
			}
			//法人身份证图片
			if(StringUtils.isNotBlank(merchant.getLegalPersonPhoto())){
				String mapName = DateUtils.getDate("yyyyMMddHHmmss") + IdGen.shortUUId() + "lpp" + Global.getApiConfig("payeco.maptype");
				Base64Utils.decodeToFile(realPath + mapName, merchant.getLegalPersonPhoto());
				merchant.setLegalPersonPhotoPath(root + mapName);
			}
		}catch(Exception e){
			System.out.println("===保存图片文件失败："+merchant.getUserId()+"--"+merchant.getMerchantId());
		}
		super.save(merchant);
	}
	/**
	 * 商铺信息更新前备份
	 * @param merchant
	 */
	@Transactional(readOnly = false)
	public void saveLog(Merchant merchant) {
		merchant.setCreateDate(new Date());//日志备份时间设置
		dao.insertLog(merchant);
	}
	
	@Transactional(readOnly = false)
	public void delete(Merchant merchant) {
		super.delete(merchant);
	}

	public Merchant getByUserNum(String userId) {
		Merchant merchant=new Merchant();
		merchant.setUserId(userId);
		return dao.getByUserNum(merchant);
	}

	@Transactional(readOnly = false)
	public void updateByUserId(Merchant merchant) {
		dao.updateByUserId(merchant);
		
	}

	public Merchant getByMerchantId(Merchant merchant) {
		return dao.getByMerchantId(merchant);
	}
	
}