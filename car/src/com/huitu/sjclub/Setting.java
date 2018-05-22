/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@ScriptAssert(lang = "javascript", script = "_this.usernameMaxLength >= _this.usernameMinLength && _this.passwordMaxLength >= _this.passwordMinLength")
public class Setting implements Serializable {

	private static final long serialVersionUID = -1478999889661796840L;

	public static final String CACHE_NAME = "setting";

	private static final String SEPARATOR = ",";

	public enum WatermarkPosition {

		no,

		topLeft,

		topRight,

		center,

		bottomLeft,

		bottomRight
	}

	public enum RoundType {

		roundHalfUp,

		roundUp,

		roundDown
	}

	public enum CaptchaType {

		memberLogin,

		memberRegister,

		adminLogin,

		review,

		consultation,

		findPassword,

		resetPassword,

		other
	}

	public enum AccountLockType {

		member,

		admin
	}

	public enum StockAllocationTime {

		order,

		payment,

		ship
	}

	public enum ReviewAuthority {

		anyone,

		member,

		purchased
	}

	public enum ConsultationAuthority {

		anyone,

		member
	}

	public enum Locale {

		zh_CN,

		zh_TW,

		en_US
	}

	private String defaultName;//默认账号

	private String defaultPassword;//默认密码

	private String siteName;

	private String siteUrl;

	private String logo;

	private String hotSearch;

	private String address;

	private String phone;

	private String zipCode;

	private String email;

	private String certtext;

	private Boolean isSiteEnabled;

	private String siteCloseMessage;

	private Integer largeProductImageWidth;

	private Integer largeProductImageHeight;

	private Integer mediumProductImageWidth;

	private Integer mediumProductImageHeight;

	private Integer thumbnailProductImageWidth;

	private Integer thumbnailProductImageHeight;

	private String defaultLargeProductImage;

	private String defaultMediumProductImage;

	private String defaultThumbnailProductImage;

	private Integer watermarkAlpha;

	private String watermarkImage;

	private Setting.WatermarkPosition watermarkPosition;

	private Integer priceScale;

	private Setting.RoundType priceRoundType;

	private Boolean isShowMarketPrice;

	private Double defaultMarketPriceScale;

	private Boolean isRegisterEnabled;

	private Boolean isDuplicateEmail;

	private String disabledUsername;

	private Integer usernameMinLength;

	private Integer usernameMaxLength;

	private Integer passwordMinLength;

	private Integer passwordMaxLength;

	private Long registerPoint;

	private String registerAgreement;

	private Boolean isEmailLogin;

	private Setting.CaptchaType[] captchaTypes;

	private Setting.AccountLockType[] accountLockTypes;

	private Integer accountLockCount;

	private Integer accountLockTime;

	private Integer safeKeyExpiryTime;

	private Integer uploadMaxSize;

	private String uploadImageExtension;

	private String uploadMediaExtension;

	private String uploadFileExtension;

	private String imageUploadPath;

	private String mediaUploadPath;

	private String fileUploadPath;

	private String smtpHost;

	private Integer smtpPort;

	private String smtpUsername;

	private String smtpPassword;

	private Boolean smtpSSLEnabled;

	private String smtpFromMail;

	private String currencySign;

	private String currencyUnit;

	private Integer stockAlertCount;

	private Setting.StockAllocationTime stockAllocationTime;

	private Double defaultPointScale;

	private Boolean isDevelopmentEnabled;

	private Boolean isReviewEnabled;

	private Boolean isReviewCheck;

	private Setting.ReviewAuthority reviewAuthority;

	private Boolean isConsultationEnabled;

	private Boolean isConsultationCheck;

	private Setting.ConsultationAuthority consultationAuthority;

	private Boolean isInvoiceEnabled;

	private Boolean isTaxPriceEnabled;

	private Double taxRate;

	private String cookiePath;

	private String cookieDomain;

	private String kuaidi100Key;

	private Boolean isCnzzEnabled;

	private String cnzzSiteId;

	private String cnzzPassword;

	private String smsSn;

	private String smsKey;

	private Setting.Locale locale;

	private String theme;

	@NotEmpty
	@Length(max = 200)
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/).*$")
	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = StringUtils.removeEnd(siteUrl, "/");
	}

	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Length(max = 200)
	public String getHotSearch() {
		return hotSearch;
	}

	public void setHotSearch(String hotSearch) {
		if (hotSearch != null) {
			hotSearch = hotSearch.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		}
		this.hotSearch = hotSearch;
	}

	@Length(max = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(max = 200)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(max = 200)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Email
	@Length(max = 200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(max = 200)
	public String getCerttext() {
		return certtext;
	}

	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}

	@NotNull
	public Boolean getIsSiteEnabled() {
		return isSiteEnabled;
	}

	public void setIsSiteEnabled(Boolean isSiteEnabled) {
		this.isSiteEnabled = isSiteEnabled;
	}

	@NotEmpty
	public String getSiteCloseMessage() {
		return siteCloseMessage;
	}

	public void setSiteCloseMessage(String siteCloseMessage) {
		this.siteCloseMessage = siteCloseMessage;
	}

	@NotNull
	@Min(1)
	public Integer getLargeProductImageWidth() {
		return largeProductImageWidth;
	}

	public void setLargeProductImageWidth(Integer largeProductImageWidth) {
		this.largeProductImageWidth = largeProductImageWidth;
	}

	@NotNull
	@Min(1)
	public Integer getLargeProductImageHeight() {
		return largeProductImageHeight;
	}

	public void setLargeProductImageHeight(Integer largeProductImageHeight) {
		this.largeProductImageHeight = largeProductImageHeight;
	}

	@NotNull
	@Min(1)
	public Integer getMediumProductImageWidth() {
		return mediumProductImageWidth;
	}

	public void setMediumProductImageWidth(Integer mediumProductImageWidth) {
		this.mediumProductImageWidth = mediumProductImageWidth;
	}

	@NotNull
	@Min(1)
	public Integer getMediumProductImageHeight() {
		return mediumProductImageHeight;
	}

	public void setMediumProductImageHeight(Integer mediumProductImageHeight) {
		this.mediumProductImageHeight = mediumProductImageHeight;
	}

	@NotNull
	@Min(1)
	public Integer getThumbnailProductImageWidth() {
		return thumbnailProductImageWidth;
	}

	public void setThumbnailProductImageWidth(Integer thumbnailProductImageWidth) {
		this.thumbnailProductImageWidth = thumbnailProductImageWidth;
	}

	@NotNull
	@Min(1)
	public Integer getThumbnailProductImageHeight() {
		return thumbnailProductImageHeight;
	}

	public void setThumbnailProductImageHeight(Integer thumbnailProductImageHeight) {
		this.thumbnailProductImageHeight = thumbnailProductImageHeight;
	}

	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	public String getDefaultLargeProductImage() {
		return defaultLargeProductImage;
	}

	public void setDefaultLargeProductImage(String defaultLargeProductImage) {
		this.defaultLargeProductImage = defaultLargeProductImage;
	}

	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	public String getDefaultMediumProductImage() {
		return defaultMediumProductImage;
	}

	public void setDefaultMediumProductImage(String defaultMediumProductImage) {
		this.defaultMediumProductImage = defaultMediumProductImage;
	}

	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	public String getDefaultThumbnailProductImage() {
		return defaultThumbnailProductImage;
	}

	public void setDefaultThumbnailProductImage(String defaultThumbnailProductImage) {
		this.defaultThumbnailProductImage = defaultThumbnailProductImage;
	}

	@NotNull
	@Min(0)
	@Max(100)
	public Integer getWatermarkAlpha() {
		return watermarkAlpha;
	}

	public void setWatermarkAlpha(Integer watermarkAlpha) {
		this.watermarkAlpha = watermarkAlpha;
	}

	public String getWatermarkImage() {
		return watermarkImage;
	}

	public void setWatermarkImage(String watermarkImage) {
		this.watermarkImage = watermarkImage;
	}

	@NotNull
	public Setting.WatermarkPosition getWatermarkPosition() {
		return watermarkPosition;
	}

	public void setWatermarkPosition(Setting.WatermarkPosition watermarkPosition) {
		this.watermarkPosition = watermarkPosition;
	}

	@NotNull
	@Min(0)
	@Max(3)
	public Integer getPriceScale() {
		return priceScale;
	}

	public void setPriceScale(Integer priceScale) {
		this.priceScale = priceScale;
	}

	@NotNull
	public Setting.RoundType getPriceRoundType() {
		return priceRoundType;
	}

	public void setPriceRoundType(Setting.RoundType priceRoundType) {
		this.priceRoundType = priceRoundType;
	}

	@NotNull
	public Boolean getIsShowMarketPrice() {
		return isShowMarketPrice;
	}

	public void setIsShowMarketPrice(Boolean isShowMarketPrice) {
		this.isShowMarketPrice = isShowMarketPrice;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	public Double getDefaultMarketPriceScale() {
		return defaultMarketPriceScale;
	}

	public void setDefaultMarketPriceScale(Double defaultMarketPriceScale) {
		this.defaultMarketPriceScale = defaultMarketPriceScale;
	}

	@NotNull
	public Boolean getIsRegisterEnabled() {
		return isRegisterEnabled;
	}

	public void setIsRegisterEnabled(Boolean isRegisterEnabled) {
		this.isRegisterEnabled = isRegisterEnabled;
	}

	@NotNull
	public Boolean getIsDuplicateEmail() {
		return isDuplicateEmail;
	}

	public void setIsDuplicateEmail(Boolean isDuplicateEmail) {
		this.isDuplicateEmail = isDuplicateEmail;
	}

	@Length(max = 200)
	public String getDisabledUsername() {
		return disabledUsername;
	}

	public void setDisabledUsername(String disabledUsername) {
		if (disabledUsername != null) {
			disabledUsername = disabledUsername.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		}
		this.disabledUsername = disabledUsername;
	}

	@NotNull
	@Min(1)
	@Max(117)
	public Integer getUsernameMinLength() {
		return usernameMinLength;
	}

	public void setUsernameMinLength(Integer usernameMinLength) {
		this.usernameMinLength = usernameMinLength;
	}

	@NotNull
	@Min(1)
	@Max(117)
	public Integer getUsernameMaxLength() {
		return usernameMaxLength;
	}

	public void setUsernameMaxLength(Integer usernameMaxLength) {
		this.usernameMaxLength = usernameMaxLength;
	}

	@NotNull
	@Min(1)
	@Max(117)
	public Integer getPasswordMinLength() {
		return passwordMinLength;
	}

	public void setPasswordMinLength(Integer passwordMinLength) {
		this.passwordMinLength = passwordMinLength;
	}

	@NotNull
	@Min(1)
	@Max(117)
	public Integer getPasswordMaxLength() {
		return passwordMaxLength;
	}

	public void setPasswordMaxLength(Integer passwordMaxLength) {
		this.passwordMaxLength = passwordMaxLength;
	}

	@NotNull
	@Min(0)
	public Long getRegisterPoint() {
		return registerPoint;
	}

	public void setRegisterPoint(Long registerPoint) {
		this.registerPoint = registerPoint;
	}

	@NotEmpty
	public String getRegisterAgreement() {
		return registerAgreement;
	}

	public void setRegisterAgreement(String registerAgreement) {
		this.registerAgreement = registerAgreement;
	}

	@NotNull
	public Boolean getIsEmailLogin() {
		return isEmailLogin;
	}

	public void setIsEmailLogin(Boolean isEmailLogin) {
		this.isEmailLogin = isEmailLogin;
	}

	public Setting.CaptchaType[] getCaptchaTypes() {
		return captchaTypes;
	}

	public void setCaptchaTypes(Setting.CaptchaType[] captchaTypes) {
		this.captchaTypes = captchaTypes;
	}

	public Setting.AccountLockType[] getAccountLockTypes() {
		return accountLockTypes;
	}

	public void setAccountLockTypes(Setting.AccountLockType[] accountLockTypes) {
		this.accountLockTypes = accountLockTypes;
	}

	@NotNull
	@Min(1)
	public Integer getAccountLockCount() {
		return accountLockCount;
	}

	public void setAccountLockCount(Integer accountLockCount) {
		this.accountLockCount = accountLockCount;
	}

	@NotNull
	@Min(0)
	public Integer getAccountLockTime() {
		return accountLockTime;
	}

	public void setAccountLockTime(Integer accountLockTime) {
		this.accountLockTime = accountLockTime;
	}

	@NotNull
	@Min(0)
	public Integer getSafeKeyExpiryTime() {
		return safeKeyExpiryTime;
	}

	public void setSafeKeyExpiryTime(Integer safeKeyExpiryTime) {
		this.safeKeyExpiryTime = safeKeyExpiryTime;
	}

	@NotNull
	@Min(0)
	public Integer getUploadMaxSize() {
		return uploadMaxSize;
	}

	public void setUploadMaxSize(Integer uploadMaxSize) {
		this.uploadMaxSize = uploadMaxSize;
	}

	@Length(max = 200)
	public String getUploadImageExtension() {
		return uploadImageExtension;
	}

	public void setUploadImageExtension(String uploadImageExtension) {
		if (uploadImageExtension != null) {
			uploadImageExtension = uploadImageExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		}
		this.uploadImageExtension = uploadImageExtension;
	}

	@Length(max = 200)
	public String getUploadMediaExtension() {
		return uploadMediaExtension;
	}

	public void setUploadMediaExtension(String uploadMediaExtension) {
		if (uploadMediaExtension != null) {
			uploadMediaExtension = uploadMediaExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		}
		this.uploadMediaExtension = uploadMediaExtension;
	}

	@Length(max = 200)
	public String getUploadFileExtension() {
		return uploadFileExtension;
	}

	public void setUploadFileExtension(String uploadFileExtension) {
		if (uploadFileExtension != null) {
			uploadFileExtension = uploadFileExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		}
		this.uploadFileExtension = uploadFileExtension;
	}

	@NotEmpty
	@Length(max = 200)
	public String getImageUploadPath() {
		return imageUploadPath;
	}

	public void setImageUploadPath(String imageUploadPath) {
		if (imageUploadPath != null) {
			if (!imageUploadPath.startsWith("/")) {
				imageUploadPath = "/" + imageUploadPath;
			}
			if (!imageUploadPath.endsWith("/")) {
				imageUploadPath += "/";
			}
		}
		this.imageUploadPath = imageUploadPath;
	}

	@NotEmpty
	@Length(max = 200)
	public String getMediaUploadPath() {
		return mediaUploadPath;
	}

	public void setMediaUploadPath(String mediaUploadPath) {
		if (mediaUploadPath != null) {
			if (!mediaUploadPath.startsWith("/")) {
				mediaUploadPath = "/" + mediaUploadPath;
			}
			if (!mediaUploadPath.endsWith("/")) {
				mediaUploadPath += "/";
			}
		}
		this.mediaUploadPath = mediaUploadPath;
	}

	@NotEmpty
	@Length(max = 200)
	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		if (fileUploadPath != null) {
			if (!fileUploadPath.startsWith("/")) {
				fileUploadPath = "/" + fileUploadPath;
			}
			if (!fileUploadPath.endsWith("/")) {
				fileUploadPath += "/";
			}
		}
		this.fileUploadPath = fileUploadPath;
	}

	@NotEmpty
	@Length(max = 200)
	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	@NotNull
	@Min(0)
	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	@NotEmpty
	@Length(max = 200)
	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	@Length(max = 200)
	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	@NotNull
	public Boolean getSmtpSSLEnabled() {
		return smtpSSLEnabled;
	}

	public void setSmtpSSLEnabled(Boolean smtpSSLEnabled) {
		this.smtpSSLEnabled = smtpSSLEnabled;
	}

	@NotEmpty
	@Email
	@Length(max = 200)
	public String getSmtpFromMail() {
		return smtpFromMail;
	}

	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}

	@NotEmpty
	@Length(max = 200)
	public String getCurrencySign() {
		return currencySign;
	}

	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}

	@NotEmpty
	@Length(max = 200)
	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	@NotNull
	@Min(0)
	public Integer getStockAlertCount() {
		return stockAlertCount;
	}

	public void setStockAlertCount(Integer stockAlertCount) {
		this.stockAlertCount = stockAlertCount;
	}

	@NotNull
	public Setting.StockAllocationTime getStockAllocationTime() {
		return stockAllocationTime;
	}

	public void setStockAllocationTime(Setting.StockAllocationTime stockAllocationTime) {
		this.stockAllocationTime = stockAllocationTime;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	public Double getDefaultPointScale() {
		return defaultPointScale;
	}

	public void setDefaultPointScale(Double defaultPointScale) {
		this.defaultPointScale = defaultPointScale;
	}

	@NotNull
	public Boolean getIsDevelopmentEnabled() {
		return isDevelopmentEnabled;
	}

	public void setIsDevelopmentEnabled(Boolean isDevelopmentEnabled) {
		this.isDevelopmentEnabled = isDevelopmentEnabled;
	}

	@NotNull
	public Boolean getIsReviewEnabled() {
		return isReviewEnabled;
	}

	public void setIsReviewEnabled(Boolean isReviewEnabled) {
		this.isReviewEnabled = isReviewEnabled;
	}

	@NotNull
	public Boolean getIsReviewCheck() {
		return isReviewCheck;
	}

	public void setIsReviewCheck(Boolean isReviewCheck) {
		this.isReviewCheck = isReviewCheck;
	}

	@NotNull
	public Setting.ReviewAuthority getReviewAuthority() {
		return reviewAuthority;
	}

	public void setReviewAuthority(Setting.ReviewAuthority reviewAuthority) {
		this.reviewAuthority = reviewAuthority;
	}

	@NotNull
	public Boolean getIsConsultationEnabled() {
		return isConsultationEnabled;
	}

	public void setIsConsultationEnabled(Boolean isConsultationEnabled) {
		this.isConsultationEnabled = isConsultationEnabled;
	}

	@NotNull
	public Boolean getIsConsultationCheck() {
		return isConsultationCheck;
	}

	public void setIsConsultationCheck(Boolean isConsultationCheck) {
		this.isConsultationCheck = isConsultationCheck;
	}

	@NotNull
	public Setting.ConsultationAuthority getConsultationAuthority() {
		return consultationAuthority;
	}

	public void setConsultationAuthority(Setting.ConsultationAuthority consultationAuthority) {
		this.consultationAuthority = consultationAuthority;
	}

	@NotNull
	public Boolean getIsInvoiceEnabled() {
		return isInvoiceEnabled;
	}

	public void setIsInvoiceEnabled(Boolean isInvoiceEnabled) {
		this.isInvoiceEnabled = isInvoiceEnabled;
	}

	@NotNull
	public Boolean getIsTaxPriceEnabled() {
		return isTaxPriceEnabled;
	}

	public void setIsTaxPriceEnabled(Boolean isTaxPriceEnabled) {
		this.isTaxPriceEnabled = isTaxPriceEnabled;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	@NotEmpty
	@Length(max = 200)
	public String getCookiePath() {
		return cookiePath;
	}

	public void setCookiePath(String cookiePath) {
		if (cookiePath != null && !cookiePath.endsWith("/")) {
			cookiePath += "/";
		}
		this.cookiePath = cookiePath;
	}

	@Length(max = 200)
	public String getCookieDomain() {
		return cookieDomain;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	@Length(max = 200)
	public String getKuaidi100Key() {
		return kuaidi100Key;
	}

	public void setKuaidi100Key(String kuaidi100Key) {
		this.kuaidi100Key = kuaidi100Key;
	}

	@Null
	public Boolean getIsCnzzEnabled() {
		return isCnzzEnabled;
	}

	public void setIsCnzzEnabled(Boolean isCnzzEnabled) {
		this.isCnzzEnabled = isCnzzEnabled;
	}

	@Null
	public String getCnzzSiteId() {
		return cnzzSiteId;
	}

	public void setCnzzSiteId(String cnzzSiteId) {
		this.cnzzSiteId = cnzzSiteId;
	}

	@Null
	public String getCnzzPassword() {
		return cnzzPassword;
	}

	public void setCnzzPassword(String cnzzPassword) {
		this.cnzzPassword = cnzzPassword;
	}

	@Length(max = 200)
	public String getSmsSn() {
		return smsSn;
	}

	public void setSmsSn(String smsSn) {
		this.smsSn = smsSn;
	}

	@Length(max = 200)
	public String getSmsKey() {
		return smsKey;
	}

	public void setSmsKey(String smsKey) {
		this.smsKey = smsKey;
	}

	@NotNull
	public Setting.Locale getLocale() {
		return locale;
	}

	public void setLocale(Setting.Locale locale) {
		this.locale = locale;
	}

	@Null
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String[] getHotSearches() {
		return StringUtils.split(hotSearch, SEPARATOR);
	}

	public String[] getDisabledUsernames() {
		return StringUtils.split(disabledUsername, SEPARATOR);
	}

	public String[] getUploadImageExtensions() {
		return StringUtils.split(uploadImageExtension, SEPARATOR);
	}

	public String[] getUploadMediaExtensions() {
		return StringUtils.split(uploadMediaExtension, SEPARATOR);
	}

	public String[] getUploadFileExtensions() {
		return StringUtils.split(uploadFileExtension, SEPARATOR);
	}

	public BigDecimal setScale(BigDecimal amount) {
		if (amount != null && getPriceScale() != null && getPriceRoundType() != null) {
			switch (getPriceRoundType()) {
			case roundUp:
				return amount.setScale(getPriceScale(), BigDecimal.ROUND_UP);
			case roundDown:
				return amount.setScale(getPriceScale(), BigDecimal.ROUND_DOWN);
			case roundHalfUp:
				return amount.setScale(getPriceScale(), BigDecimal.ROUND_HALF_UP);
			}
		}
		return amount;
	}

}