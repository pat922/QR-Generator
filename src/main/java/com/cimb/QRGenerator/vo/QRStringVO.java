package com.cimb.QRGenerator.vo;

public class QRStringVO {

	

	private String payloadFormatIndicator;
	private String initMethod;
	private String merchantAccInfoId;
	private String merchantAccInfoStrLength;
	private String globalUniqueId;
	private String payNowIdTypePrefix;
	private String payNowIdType;
	private String proxyValueId;
	private String proxyValueLength;
	private String proxyValue;
	private String editableAmountFlag;
	//customize qr string
	private String qrValidTill;
	//merchant reference number
	private String endToEndReference;
	private String merchantCategory;
	private String transactionCurrency;
	//customize qr amount
	private String amountQr;
	private String countryCode;
	private String merchantNameCode;
	private String merchantNameLength;
	private String merchantName;
	private String merchantNameStr;
	private String merchantCity;
	private String additionalDataId;
	private String additionalDataLength;
	private String qrBillNum;
	private String crcChecksumId;
	private String crcChecksumLength;
	private String crcChecksum;
	//private String qrPaynowType;
	
	public QRStringVO(String payloadFormatIndicator, String initMethod,
			String merchantAccInfoId, String merchantAccInfoStrLength, String globalUniqueId, String payNowIdTypePrefix,
			String payNowIdType, String proxyValueId, String proxyValueLength, String proxyValue,
			String editableAmountFlag, String qrValidTill, String endToEndReference, 
			String merchantCategory,
			String transactionCurrency,
			String amountQr,
			String countryCode,
			String merchantNameStr, String merchantCity, String additionalDataId, String additionalDataLength,
			String qrBillNum,
			String crcChecksumId, String crcChecksumLength, String crcChecksum) {
		this.payloadFormatIndicator = payloadFormatIndicator;
		this.initMethod = initMethod;
		this.merchantAccInfoId = merchantAccInfoId;
		this.merchantAccInfoStrLength = merchantAccInfoStrLength;
		this.globalUniqueId = globalUniqueId;
		this.payNowIdTypePrefix = payNowIdTypePrefix;
		this.payNowIdType = payNowIdType;
		this.proxyValueId = proxyValueId;
		this.proxyValueLength = proxyValueLength;
		this.proxyValue = proxyValue;
		this.editableAmountFlag = editableAmountFlag;
		this.qrValidTill = qrValidTill;
		this.endToEndReference = endToEndReference;
		this.merchantCategory = merchantCategory;
		this.transactionCurrency = transactionCurrency;
		this.amountQr = amountQr;
		this.countryCode = countryCode;
		this.merchantNameStr = merchantNameStr;
		this.merchantCity = merchantCity;
		this.additionalDataId = additionalDataId;
		this.additionalDataLength = additionalDataLength;
		this.qrBillNum = qrBillNum;
		this.crcChecksumId = crcChecksumId;
		this.crcChecksumLength = crcChecksumLength;
		this.crcChecksum = crcChecksum;
	}
	
	public String getAdditionalDataId() {
		return additionalDataId;
	}

	public void setAdditionalDataId(String additionalDataId) {
		this.additionalDataId = additionalDataId;
	}

	public String getAdditionalDataLength() {
		return additionalDataLength;
	}

	public void setAdditionalDataLength(String additionalDataLength) {
		this.additionalDataLength = additionalDataLength;
	}

	public String getMerchantNameCode() {
		return merchantNameCode;
	}

	public void setMerchantNameCode(String merchantNameCode) {
		this.merchantNameCode = merchantNameCode;
	}

	public String getMerchantNameLength() {
		return merchantNameLength;
	}

	public void setMerchantNameLength(String merchantNameLength) {
		this.merchantNameLength = merchantNameLength;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getQrBillNum() {
		return qrBillNum;
	}

	public void setQrBillNum(String qrBillNum) {
		this.qrBillNum = qrBillNum;
	}

	public String getPayloadFormatIndicator() {
		return payloadFormatIndicator;
	}

	public void setPayloadFormatIndicator(String payloadFormatIndicator) {
		this.payloadFormatIndicator = payloadFormatIndicator;
	}

	public String getInitMethod() {
		return initMethod;
	}

	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
	}
	
	public String getMerchantAccInfoId() {
		return merchantAccInfoId;
	}

	public void setMerchantAccInfoId(String merchantAccInfoId) {
		this.merchantAccInfoId = merchantAccInfoId;
	}
	
	
	public String getMerchantAccInfoStrLength() {
		return merchantAccInfoStrLength;
	}

	public void setMerchantAccInfoStrLength(String merchantAccInfoStrLength) {
		this.merchantAccInfoStrLength = merchantAccInfoStrLength;
	}
	
	public String getGlobalUniqueId() {
		return globalUniqueId;
	}
	
	public void setGlobalUniqueId(String globalUniqueId) {
		this.globalUniqueId = globalUniqueId;
	}
	
	public String getPayNowIdTypePrefix() {
		return payNowIdTypePrefix;
	}
	
	public void setPayNowIdTypePrefix(String payNowIdTypePrefix) {
		this.payNowIdTypePrefix = payNowIdTypePrefix;
	}
	
	public String getPayNowIdType() {
		return payNowIdType;
	}
	
	public void setPayNowIdType(String payNowIdType) {
		this.payNowIdType = payNowIdType;
	}
	
	public String getProxyValueId() {
		return proxyValueId;
	}
	
	public void setProxyValueId(String proxyValueId) {
		this.proxyValueId = proxyValueId;
	}
	
	public String getProxyValueLength() {
		return proxyValueLength;
	}
	
	public void setProxyValueLength(String proxyValueLength) {
		this.proxyValueLength = proxyValueLength;
	}
	
	public String getProxyValue() {
		return proxyValue;
	}
	
	public void setProxyValue(String proxyValue) {
		this.proxyValue = proxyValue;
	}
	
	public String getEditableAmountFlag() {
		return editableAmountFlag;
	}
	
	public void setEditableAmountFlag(String editableAmountFlag) {
		this.editableAmountFlag = editableAmountFlag;
	}
	
	public String getQrValidTill() {
		return qrValidTill;
	}

	public void setQrValidTill(String qrValidTill) {
		this.qrValidTill = qrValidTill;
	}

	public String getEndToEndReference() {
		return endToEndReference;
	}

	public void setEndToEndReference(String endToEndReference) {
		this.endToEndReference = endToEndReference;
	}

	public String getAmountQr() {
		return amountQr;
	}

	public void setAmountQr(String amountQr) {
		this.amountQr = amountQr;
	}


	public String getMerchantCategory() {
		return merchantCategory;
	}

	public void setMerchantCategory(String merchantCategory) {
		this.merchantCategory = merchantCategory;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMerchantNameStr() {
		return merchantNameStr;
	}

	public void setMerchantNameStr(String merchantNameStr) {
		this.merchantNameStr = merchantNameStr;
	}

	public String getMerchantCity() {
		return merchantCity;
	}

	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}
	
	public String getCrcChecksumId() {
		return crcChecksumId;
	}

	public void setCrcChecksumId(String crcChecksumId) {
		this.crcChecksumId = crcChecksumId;
	}
	
	public String getCrcChecksumLength() {
		return crcChecksumLength;
	}

	public void setCrcChecksumLength(String crcChecksumLength) {
		this.crcChecksumLength = crcChecksumLength;
	}
	
	public String getCrcChecksum() {
		return crcChecksum;
	}

	public void setCrcChecksum(String crcChecksum) {
		this.crcChecksum = crcChecksum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QRStringVO [payloadFormatIndicator=");
		builder.append(payloadFormatIndicator);
		builder.append(", initMethod=");
		builder.append(initMethod);
		builder.append(", merchantAccInfoId=");
		builder.append(merchantAccInfoId);
		builder.append(", merchantAccInfoStrLength=");
		builder.append(merchantAccInfoStrLength);
		builder.append(", globalUniqueId=");
		builder.append(globalUniqueId);
		builder.append(", payNowIdTypePrefix=");
		builder.append(payNowIdTypePrefix);
		builder.append(", payNowIdType=");
		builder.append(payNowIdType);
		builder.append(", proxyValueId=");
		builder.append(proxyValueId);
		builder.append(", proxyValueLength=");
		builder.append(proxyValueLength);
		builder.append(", proxyValue=");
		builder.append(proxyValue);
		builder.append(", editableAmountFlag=");
		builder.append(editableAmountFlag);
		//qrValidTill
		builder.append(", qrValidTill=");
		builder.append(qrValidTill);
		//endToEndReference
		builder.append(", endToEndReference=");
		builder.append(endToEndReference);		
		builder.append(", merchantCategory=");
		builder.append(merchantCategory);
		builder.append(", transactionCurrency=");
		builder.append(transactionCurrency);
		//amountQr
		builder.append(", amountQr=");
		builder.append(amountQr);		
		builder.append(", countryCode=");
		builder.append(countryCode);
		builder.append(", merchantNameCode=");
		builder.append(merchantNameCode);
		builder.append(", merchantNameLength=");
		builder.append(merchantNameLength);
		builder.append(", merchantName=");
		builder.append(merchantName);
		builder.append(", merchantNameStr=");
		builder.append(merchantNameStr);
		builder.append(", merchantCity=");
		builder.append(merchantCity);
		//qrBillNum
		builder.append(", qrBillNum=");
		builder.append(qrBillNum);
		builder.append(", crcChecksumId=");
		builder.append(crcChecksumId);
		builder.append(", crcChecksumLength=");
		builder.append(crcChecksumLength);
		builder.append(", crcChecksum=");
		builder.append(crcChecksum);
		builder.append("]");
		return builder.toString();
	}
	
}
