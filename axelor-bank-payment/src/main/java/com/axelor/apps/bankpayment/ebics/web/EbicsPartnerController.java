/**
 * Axelor Business Solutions
 *
 * Copyright (C) 2018 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.apps.bankpayment.ebics.web;

import java.util.List;

import com.axelor.apps.bankpayment.db.BankStatement;
import com.axelor.apps.bankpayment.db.EbicsPartner;
import com.axelor.apps.bankpayment.db.repo.EbicsPartnerRepository;
import com.axelor.apps.bankpayment.ebics.service.EbicsPartnerService;
import com.axelor.exception.AxelorException;
import com.axelor.exception.service.TraceBackService;
import com.axelor.i18n.I18n;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class EbicsPartnerController {
	
	@Inject
	EbicsPartnerService ebicsPartnerService;
	
	@Inject
	EbicsPartnerRepository ebicsPartnerRepository;
	
	public void getBankStatement(ActionRequest request, ActionResponse response )  {
		
		try {
			EbicsPartner ebicsPartner = request.getContext().asType(EbicsPartner.class);
		
			List<BankStatement> bankStatementList = ebicsPartnerService.getBankStatements(ebicsPartnerRepository.find(ebicsPartner.getId()));
			response.setFlash(String.format(I18n.get("%s bank statements get."), bankStatementList.size()));
		}
		catch(Exception e)  {
			TraceBackService.trace(response, e);
		}
		response.setReload(true);
		
	}

	public void checkBankDetailsSet(ActionRequest request, ActionResponse response) {
		EbicsPartner ebicsPartner = request.getContext().asType(EbicsPartner.class);
	    try {
	    	ebicsPartnerService.checkBankDetailsMissingCurrency(ebicsPartner);
		} catch (AxelorException e) {
	    	response.setFlash(e.getMessage());
		}
	}
	
}
