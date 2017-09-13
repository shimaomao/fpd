/*
 * Copyright (C) 2012 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wanfin.fpd.core;

import com.wanfin.fpd.common.persistence.ApiEntity;
import com.wanfin.fpd.core.Link.LinkKey;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.entity.WOrder;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.sys.entity.Dict;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.Role;
import com.wanfin.fpd.modules.sys.entity.User;

public enum LinkResPath {
    User(LinkKey.User, User.class),
    Role(LinkKey.Role, Role.class),
    Office(LinkKey.Office, Office.class),
    Dict(LinkKey.Dict, Dict.class),

    TProduct(LinkKey.TProduct, TProduct.class),
    TCompany(LinkKey.TCompany, TCompany.class),
    TEmployee(LinkKey.TEmployee, TEmployee.class),

    RepayRecord(LinkKey.RepayRecord, RepayRecord.class),
    //TRepayPlan(LinkKey.TRepayPlan, TRepayPlan.class),
    TLoanContract(LinkKey.TLoanContract, TLoanContract.class),
    WOrder(LinkKey.WOrder, WOrder.class);

    final String path;
    @SuppressWarnings("rawtypes")
	final Class<? extends ApiEntity> associatedClass;

    private LinkResPath(String elt, @SuppressWarnings("rawtypes") Class<? extends ApiEntity> clazz) {
        path = elt;
        associatedClass = clazz;
    }

    public static LinkResPath forClass(@SuppressWarnings("rawtypes") Class<? extends ApiEntity> clazz) {
        for (LinkResPath rp : values()) {
            //Cannot use equals because of hibernate proxied object
            //Cannot use instanceof because type not fixed at compile time
            if (rp.associatedClass.isAssignableFrom(clazz)) {
                return rp;
            }
        }
        throw new IllegalArgumentException("No ResourcePath for class '" + clazz.getName() + "'");
    }

    public String getPath() {
        return path;
    }

    @SuppressWarnings("rawtypes")
	public Class<? extends ApiEntity> getAssociatedClass() {
        return associatedClass;
    }
}
